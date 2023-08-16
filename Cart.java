import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Product> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public void addItem(Product product) {
        items.add(product);
    }

    public void removeItem(Product product) {
        items.remove(product);
    }

    public List<Product> getItems() {
        return items;
    }

    public void clear() {
        items.clear();
    }

    public double calculateTotal() {
        double totalAmount = 0;
        for (Product item : items) {
            totalAmount += item.getPrice();
        }
        return totalAmount;
    }
}
