import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class ShoppingCartApp 
{
    private static List<User> users = new ArrayList<>();
    private static List<Product> products = new ArrayList<>(); 
    private static Cart cart = new Cart();    
    
    private static ProductManager productManager;
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Online Shopping Cart!");
        productManager = new ProductManager(products);
       
        
        registerUser("VilasAdmane", "0000");
        registerUser("Vilas","12345");
      

       
        User loggedInUser = loginUser(scanner);
        if (loggedInUser != null) {
            System.out.println("User " + loggedInUser.getUsername() + " logged in.");
            showMenu(scanner, loggedInUser);
        } else {
            System.out.println("Login failed.");
        }
    }

    public static void registerUser(String username, String password) {
        User newUser = new User(username, password);
        users.add(newUser);
        //System.out.println("User registered successfully.");
    }

    public static User loginUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.verifyPassword(password)) {
                return user;
            }
        }
        return null; 
    }
    
    
    private static boolean offerSelected = false;
    private static Offer selectedOffer = null;
    public static void showMenu(Scanner scanner, User user) {
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Product to Company Inventory");
            System.out.println("2. Add Product to Cart");
            System.out.println("3. Remove Product from Cart");
            System.out.println("4. View Cart");
            System.out.println("5. Generate Bill");
            System.out.println("6. Offer");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                      addProductToCompanyInventory(scanner);
                      break;
                case 2:
                    addProductToCart(scanner);
                    break;
                case 3:
                    removeProductFromCart(scanner);
                    break;
                case 4:
                    viewCart();
                    break;
                case 5:
                	List<Product> cartItems = cart.getItems();

                    if (cartItems.isEmpty()) {
                        System.out.println("Cart is empty. Unable to generate bill.");
                       
                    }
                    else if (!offerSelected) {
                        System.out.println("Please choose an offer before calculating the bill.");
                        showOffers();
                        System.out.print("Enter the number of the offer to apply: ");
                        int offerNumber = Integer.parseInt(scanner.nextLine());
                        if (offerNumber >= 1 && offerNumber <= 2) { // Update the range according to the number of offers
                            selectedOffer = getSelectedOffer(offerNumber);
                            if (selectedOffer != null) {
                                offerSelected = true;
                            } else {
                                System.out.println("Invalid offer number.");
                            }
                        } else {
                            System.out.println("Invalid option.");
                        }
                    } else {
                        calculateBillWithOffer(user, selectedOffer);
                        offerSelected = false; 
                    }
                    break;

                case 6:
                    showOffers();
                    break;
                case 7:
                    System.out.println("Thank you for using the Online Shopping Cart!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
            }
        }
    }

    public static void addProductToCart(Scanner scanner) {
    	if (products.isEmpty()) {
            System.out.println("No products available to add to the cart.");
            return;
        }
    	
        System.out.println("Available Products:");
        for (Product product : products) {
            System.out.println(product.getName() + " - Price: " + product.getPrice() + " - Quantity" +product.getQuantity());
        }
        System.out.print("Enter the name of the product to add to cart: ");
        String productName = scanner.nextLine();

        Product selectedProduct = findProductByName(productName);

        if (selectedProduct != null) {
            cart.addItem(selectedProduct);
            System.out.println("Product added to cart.");
        } else {
            System.out.println("Product not found.");
        }
    }

    public static void removeProductFromCart(Scanner scanner) {
        List<Product> cartItems = cart.getItems();

        if (cartItems.isEmpty()) {
            System.out.println("Cart is empty. Nothing to remove.");
            return;
        }

        System.out.println("Items in Cart:");
        for (int i = 0; i < cartItems.size(); i++) {
            Product item = cartItems.get(i);
            System.out.println(i + 1 + ". " + item.getName() + " - Price: " + item.getPrice());
        }

        System.out.print("Enter the number of the item to remove: ");
        int itemNumber = Integer.parseInt(scanner.nextLine()) - 1;

        if (itemNumber >= 0 && itemNumber < cartItems.size()) {
            Product removedProduct = cartItems.remove(itemNumber);
            System.out.println(removedProduct.getName() + " removed from cart.");
        } else {
            System.out.println("Invalid item number.");
        }
    }

    public static void viewCart() {
    	
        List<Product> cartItems = cart.getItems();
        if (cartItems.isEmpty()) {
            System.out.println("Cart is empty. Nothing is available in the cart.");
            return;
        }
        System.out.println("\nItems in Cart:");
        for (Product item : cartItems) {
            System.out.println(item.getName() + " - Price: " + item.getPrice());
        }
    }

    
    public static Product findProductByName(String productName) {
        for (Product product : products) {
            if (product.getName().equalsIgnoreCase(productName)) {
                return product;
            }
        }
        return null;
    }

   
    public static void addProduct(String name, double price, int quantity) {
        Product newProduct = new Product(name, price, quantity);
        products.add(newProduct);
        System.out.println("Product added successfully.");
    }

    public static void removeProduct(String productName) {
        Product productToRemove = findProductByName(productName);
        if (productToRemove != null) {
            products.remove(productToRemove);
            System.out.println("Product removed successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }
    private static void calculateBillWithOffer(User user, Offer selectedOffer) {
    	
    	
        
        double totalAmount = cart.calculateTotal();
        double discount = totalAmount * selectedOffer.getDiscountPercentage();
        double finalAmount = totalAmount - discount;

        System.out.println("Total Bill: " + totalAmount);
        System.out.println("Offer Applied: " + selectedOffer.getOfferName());
        System.out.println("Discount: " + discount);
        System.out.println("Final Amount: " + finalAmount);

       
        Order order = new Order(user, cart.getItems(), finalAmount);
      
    }

    public static void showOffers() {
       
        Offer offer1 = new Offer("10% Discount on Electronics", 0.10);
        Offer offer2 = new Offer("5% Discount on Clothing", 0.05);

        System.out.println("\nAvailable Offers:");
        System.out.println("1. " + offer1.getOfferName() + " - Discount: " + (offer1.getDiscountPercentage() * 100) + "%");
        System.out.println("2. " + offer2.getOfferName() + " - Discount: " + (offer2.getDiscountPercentage() * 100) + "%");
    }
     
    private static Offer getSelectedOffer(int offerNumber) {
        
        Offer offer1 = new Offer("10% Discount on Electronics", 0.10);
        Offer offer2 = new Offer("5% Discount on Clothing", 0.05);

        switch (offerNumber) {
            case 1:
                return offer1;
            case 2:
                return offer2;
            default:
                return null;
        }
    }
    public static void addProductToCompanyInventory(Scanner scanner) {
        System.out.print("Enter the name of the product: ");
        String name = scanner.nextLine();
        System.out.print("Enter the price of the product: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter the quantity of the product: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        productManager.addProduct(name, price, quantity);
    }

    
   
}
