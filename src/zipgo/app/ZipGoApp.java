package zipgo.app;

import zipgo.model.*;
import zipgo.service.RideManager;
import zipgo.storage.FileManager;

import java.util.*;

public class ZipGoApp {

    private static volatile boolean running = true;

    public static void main(String[] args) {

        RideManager rm = new RideManager();
        Scanner sc = new Scanner(System.in);

        Vehicle car = new Car("WB01AB1234");

        Rider rider = new Rider("R1", "Anurag", "9000000001");
        Driver driver = new Driver("D1", "Rahul", "9000000002", car);

        rm.addRider(rider);
        rm.addDriver(driver);

        System.out.println("ZipGo started");

        while (running) {
            System.out.println("\nLogin as:");
            System.out.println("1. Rider");
            System.out.println("2. Driver");
            System.out.println("3. Exit");

            int choice = sc.nextInt();

            if (choice == 1) {
                riderSession(rm, rider);
            } else if (choice == 2) {
                driverSession(rm, driver);
            } else {
                shutdown(rm);
            }
        }

        sc.close();
    }

    private static void riderSession(RideManager rm, Rider rider) {
        Scanner sc = new Scanner(System.in);

        while (running) {
            System.out.println("\nRider Menu");
            System.out.println("1. Request Ride");
            System.out.println("2. View My Trips");
            System.out.println("3. Logout");

            int ch = sc.nextInt();

            if (ch == 1) {
                System.out.print("Enter distance: ");
                double dist = sc.nextDouble();

                Ride ride = rm.requestRide("R" + System.currentTimeMillis(), rider.getUserId(), dist);

                new Thread(() -> {
                    while (running) {
                        try {
                            Thread.sleep(1000);

                            if (ride.getStatus() == RideStatus.ACCEPTED) {
                                System.out.println("\nDriver assigned for " + ride.getRideId());
                            }

                            if (ride.getStatus() == RideStatus.IN_PROGRESS) {
                                System.out.println("\nRide started: " + ride.getRideId());
                            }

                            if (ride.getStatus() == RideStatus.COMPLETED) {
                                System.out.println("\nRide completed:");
                                ride.displayRideDetails();
                                break;
                            }

                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                }).start();

            } else if (ch == 2) {
                for (Ride r : rm.getRides()) {
                    if (r.getRider().getUserId().equals(rider.getUserId())) {
                        r.displayRideDetails();
                    }
                }
            } else {
                break;
            }
        }
    }

    private static void driverSession(RideManager rm, Driver driver) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\nDriver logged in");

        while (running) {
            System.out.println("\nDriver Menu");
            System.out.println("1. Go Online");
            System.out.println("2. Go Offline");
            System.out.println("3. Start Listening");
            System.out.println("4. View Earnings");
            System.out.println("5. Logout");

            int ch = sc.nextInt();

            if (ch == 1) {
                driver.setAvailable(true);
                System.out.println("Driver is ONLINE");

            } else if (ch == 2) {
                driver.setAvailable(false);
                System.out.println("Driver is OFFLINE");

            } else if (ch == 3) {

                new Thread(() -> {
                    while (running) {
                        try {
                            Ride ride = rm.assignRideToDriver(driver);

                            if (ride != null) {
                                System.out.println("\nNew ride:");
                                ride.displayRideDetails();

                                rm.startRide(ride.getRideId());
                                Thread.sleep(3000);

                                rm.completeRide(ride.getRideId());

                                System.out.println("\nRide completed:");
                                ride.displayRideDetails();
                            }

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }).start();

                System.out.println("Listening for rides...");

            } else if (ch == 4) {
                double earnings = rm.getDriverEarnings(driver.getUserId());
                System.out.println("Total earnings: ₹" + earnings);

            } else {
                break;
            }
        }
    }

    private static void shutdown(RideManager rm) {
        running = false;

        System.out.println("\nSaving rides to file...");
        FileManager.saveRides(rm.getRides());

        System.out.println("System shutting down");
        System.exit(0);
    }
}