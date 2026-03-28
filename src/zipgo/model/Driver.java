package zipgo.model;

public class Driver extends User {
    private boolean isAvailable;
    private Vehicle vehicle;

    public Driver(String userId, String name, String phone, Vehicle vehicle) {
        super(userId, name, phone);
        this.vehicle = vehicle;
        this.isAvailable = true;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public void displayInfo() {
        System.out.println("Driver ID: " + getUserId() +
                ", Name: " + getName() +
                ", Phone: " + getPhone() +
                ", Available: " + isAvailable +
                ", Vehicle: " + (vehicle != null ? vehicle.getVehicleNumber() : "None"));
    }
}