package main;

import java.util.Scanner;
import models.*;
import services.*;

public class Main {
    public static void main(String[] args) {
        InventoryService inventory = new InventoryService();
        UserService userService = new UserService();
        CatalogService catalog = new CatalogService(inventory);

        // Add books with prices
        inventory.addBook(new Book("Java 101", "Sun Dev", 2, 399.00));
        inventory.addBook(new Book("DSA Made Easy", "Algo Guru", 3, 499.00));
        inventory.addBook(new Book("Clean Code", "Robert C. Martin", 4, 799.00));
        inventory.addBook(new Book("Head First Java", "Kathy Sierra", 5, 699.00));
        inventory.addBook(new Book("Effective Java", "Joshua Bloch", 3, 899.00));
        inventory.addBook(new Book("Java Concurrency", "Brian Goetz", 2, 749.00));
        inventory.addBook(new Book("Algorithms", "Sedgewick", 3, 599.00));
        inventory.addBook(new Book("Spring in Action", "Craig Walls", 4, 829.00));
        inventory.addBook(new Book("Design Patterns", "GoF", 2, 899.00));

        // User input
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String username = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your phone number: ");
        String phone = scanner.nextLine();

        User user = userService.registerUser(username, email, phone);

        // Show catalog
        System.out.println("\nAvailable Books:");
        for (Book book : inventory.getAllBooks()) {
            System.out.println(book.getTitle() + " — ₹" + book.getPrice());
        }

        // Add books to cart
        System.out.print("\nEnter the title of the book to add to your cart (or type 'done'): ");
        String input;
        while (!(input = scanner.nextLine()).equalsIgnoreCase("done")) {
            Book selected = inventory.getBook(input);
            if (selected != null) {
                user.getCart().addBook(selected);
                System.out.println("Added to cart: " + selected.getTitle() + " — ₹" + selected.getPrice());
            } else {
                System.out.println("Book not found.");
            }
            System.out.print("Enter another title or type 'done': ");
        }

        // View cart
        System.out.println("\nYour Cart:");
        double total = 0;
        for (Book book : user.getCart().getBooks()) {
            System.out.println(book.getTitle() + " — ₹" + book.getPrice());
            total += book.getPrice();
        }
        System.out.println("Total: ₹" + total);

        // Checkout
        System.out.println("\nProcessing purchase...");
        for (Book book : user.getCart().getBooks()) {
            synchronized (book) {
                if (book.purchase()) {
                    System.out.println(user.getUsername() + " bought: " + book.getTitle() + " — ₹" + book.getPrice());
                } else {
                    System.out.println("Out of stock: " + book.getTitle());
                }
            }
        }

        scanner.close();
    }
}
