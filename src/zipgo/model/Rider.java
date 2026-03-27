package zipgo.model;

public class Rider extends User {

    public Rider(String userId, String name, String phone) {
        super(userId, name, phone);
    }

    @Override
    public void displayInfo() {
        System.out.println("Rider ID: " + getUserId() +
                           ", Name: " + getName() +
                           ", Phone: " + getPhone());
    }
}