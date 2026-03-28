package zipgo.model;

public class Auto extends Vehicle {

    public Auto(String vehicleNumber) {
        super(vehicleNumber, 10.0);
    }

    @Override
    public double calculateFare(double distance) {
        return distance * getBaseFarePerKm();
    }
}