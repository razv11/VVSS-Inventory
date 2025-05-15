package inventory.repository;

import inventory.model.Part;
import inventory.model.Product;
import javafx.collections.ObservableList;

public interface InventoryRepositoryInterface {

    int getAutoPartId();
    int getAutoProductId();
    ObservableList<Part> getAllParts();
    ObservableList<Product> getAllProducts();
    void addPart(Part part);
    void addProduct(Product product);
    Part lookupPart(String search);
    Product lookupProduct(String search);
    void updatePart(int partIndex, Part part);
    void updateProduct(int productIndex, Product product);
    void deletePart(Part part);
    void deleteProduct(Product product);
}
