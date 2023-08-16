import java.util.List;

public class ProductManager {
    private List<Product> products;

    public ProductManager(List<Product> products) {
        this.products = products;
    }

    public void addProduct(String name, double price, int quantity) {
        Product newProduct = new Product(name, price, quantity);
        products.add(newProduct);
        System.out.println("Product added successfully.");
    }

    public void removeProduct(String productName) {
        Product productToRemove = findProductByName(productName);
        if (productToRemove != null) {
            products.remove(productToRemove);
            System.out.println("Product removed successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    public Product findProductByName(String productName) {
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(productName)) {
                return product;
            }
        }
        return null;
    }
}
