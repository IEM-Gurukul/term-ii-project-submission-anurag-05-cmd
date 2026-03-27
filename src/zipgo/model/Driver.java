package zipgo.model;

public class Driver extends User {
    private boolean isAvailable;

    public Driver(String userId, String name, String phone) {
        super(userId, name, phone);
        this.isAvailable = true; // default available
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    @Override
    public void displayInfo() {
        System.out.println("Driver ID: " + getUserId() +
                           ", Name: " + getName() +
                           ", Phone: " + getPhone() +
                           ", Available: " + isAvailable);
    }
}