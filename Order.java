import java.util.List;

public class Order {
    private User user;
    private List<Product> items;
    private double totalAmount;
    private boolean isCompleted;

    public Order(User user, List<Product> items, double totalAmount) {
        this.user = user;
        this.items = items;
        this.totalAmount = totalAmount;
        this.isCompleted = false;
    }

    public User getUser() {
        return user;
    }

    public List<Product> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void markAsCompleted() {
        isCompleted = true;
    }
}
