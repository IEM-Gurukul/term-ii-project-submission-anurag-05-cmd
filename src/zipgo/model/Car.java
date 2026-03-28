package zipgo.model;

public class Car extends Vehicle {

    public Car(String vehicleNumber) {
        super(vehicleNumber, 15.0); // base fare per km
    }

    @Override
    public double calculateFare(double distance) {
        return distance * getBaseFarePerKm();
    }
}