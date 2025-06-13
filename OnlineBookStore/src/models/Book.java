package models;

public class Book {
    private String title;
    private String author;
    private int stock;
    private double price;

    public Book(String title, String author, int stock, double price) {
        this.title = title;
        this.author = author;
        this.stock = stock;
        this.price = price;
    }

    public synchronized boolean purchase() {
        if (stock > 0) {
            stock--;
            return true;
        }
        return false;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getStock() {
        return stock;
    }

    public double getPrice() {
        return price;
    }
}
