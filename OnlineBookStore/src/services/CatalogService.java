package services;

import java.util.Collection;
import models.Book;

public class CatalogService {
    private InventoryService inventoryService;

    public CatalogService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void showCatalog() {
        Collection<Book> books = inventoryService.getAllBooks();
        for (Book book : books) {
            System.out.println(book.getTitle());
        }
    }
}
