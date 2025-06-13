package models;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public void viewCart() {
        for (Book book : books) {
            System.out.println(book.getTitle());
        }
    }

    public List<Book> getBooks() {
        return books;
    }
}
