package services;

import java.util.*;
import models.Book;

public class InventoryService {
    private HashMap<String, Book> books = new HashMap<>();

    public void addBook(Book book) {
        books.put(book.getTitle(), book);
    }

    public Book getBook(String title) {
        return books.get(title);
    }

    public Collection<Book> getAllBooks() {
        return books.values();
    }

    // ✅ New: Remove a book by title
    public boolean removeBook(String title) {
        if (books.containsKey(title)) {
            books.remove(title);
            return true;
        }
        return false;
    }
}
