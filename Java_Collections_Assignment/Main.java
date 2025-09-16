package shopcart;

import shopcart.model.Product;
import shopcart.service.ShoppingCartService;
import java.math.BigDecimal;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ShoppingCartService cart = new ShoppingCartService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("VINAY SHOPPING CART MENU");
            System.out.println("1. Add a product");
            System.out.println("2. Update product quantity");
            System.out.println("3. Remove a product");
            System.out.println("4. View the cart");
            System.out.println("5. Exit from shopping");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        System.out.print("Please Please enter product id: ");
                        int id = Integer.parseInt(scanner.nextLine());
                        System.out.print("Please enter product name: ");
                        String name = scanner.nextLine();
                        System.out.print("Please enter product price: ");
                        BigDecimal price = new BigDecimal(scanner.nextLine());
                        System.out.print("Please enter quantity: ");
                        int qty = Integer.parseInt(scanner.nextLine());
                        cart.addProduct(new Product(id, name, price), qty);
                        System.out.println("Product added.");
                        break;
                    case "2":
                        System.out.print("Please enter product id to update: ");
                        int updateId = Integer.parseInt(scanner.nextLine());
                        System.out.print("Please enter new quantity: ");
                        int newQty = Integer.parseInt(scanner.nextLine());
                        cart.updateQuantity(updateId, newQty);
                        System.out.println("Quantity of items updated.");
                        break;
                    case "3":
                        System.out.print("Please enter product id to remove: ");
                        int removeId = Integer.parseInt(scanner.nextLine());
                        cart.removeProduct(removeId);
                        System.out.println("Product has been removed.");
                        break;
                    case "4":
                        printCart(cart);
                        break;
                    case "5":
                        System.out.println("EXIT :(");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid option entered. Please Try again.");
                }
            } catch (Exception e) {
                System.out.println("Error in shopping : " + e.getMessage());
            }
        }
    }

    private static void printCart(ShoppingCartService cart) {
        System.out.println("Items in the cart:");
        if (cart.getItems().isEmpty()) {
            System.out.println("Empty cart");
        } else {
            cart.getItems().forEach(item ->
                    System.out.println("  " + item.getProduct().getName() +
                            " x " + item.getQuantity() +
                            " = " + item.getSubtotal())
            );
        }
        System.out.println("Total amount : " + cart.getTotal());
    }
}

