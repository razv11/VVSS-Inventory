package inventory.integration;

import inventory.model.Inventory;
import inventory.model.InhousePart;
import inventory.model.OutsourcedPart;
import inventory.model.Part;
import inventory.model.Product;
import inventory.repository.InventoryRepository;
import inventory.service.InventoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class FullIntegrationTest {
    private Inventory inventory;
    private InventoryRepository repository;
    private InventoryService service;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
        repository = new InventoryRepository();
        repository.setInventory(inventory);
        service = new InventoryService(repository);
    }

    @Test
    void testAddInhousePartIntegration() {
        // Act
        service.addInhousePart("Inhouse Part", 9.99, 10, 1, 20, 1234);

        // Assert
        Part part = inventory.lookupPart("Inhouse Part");
        assertNotNull(part, "The part should have been added.");
        assertEquals("Inhouse Part", part.getName());
        assertEquals(9.99, part.getPrice());
        assertEquals(10, part.getInStock());
        assertEquals(1234, ((InhousePart) part).getMachineId());
    }

    @Test
    void testAddOutsourcePartIntegration() {
        // Act
        service.addOutsourcePart("Outsourced Part", 19.99, 5, 1, 10, "ACME Corp");

        // Assert
        Part part = inventory.lookupPart("Outsourced Part");
        assertNotNull(part, "The part should have been added.");
        assertEquals("Outsourced Part", part.getName());
        assertEquals(19.99, part.getPrice());
        assertEquals(5, part.getInStock());
        assertEquals("ACME Corp", ((OutsourcedPart) part).getCompanyName());
    }

    @Test
    void testAddProductIntegration() {
        // Arrange
        ObservableList<Part> parts = FXCollections.observableArrayList();

        // Act
        service.addProduct("Test Product", 29.99, 10, 1, 20, parts);

        // Assert
        Product product = inventory.lookupProduct("Test Product");
        assertNotNull(product, "The product should have been added.");
        assertEquals("Test Product", product.getName());
        assertEquals(29.99, product.getPrice());
        assertEquals(10, product.getInStock());
        assertEquals(parts, product.getAssociatedParts());
    }

    @Test
    void testLookupPartIntegration() {
        // Arrange
        service.addInhousePart("Test Part", 9.99, 10, 1, 20, 1234);

        // Act
        Part part = service.lookupPart("Test Part");

        // Assert
        assertNotNull(part, "The part should be found.");
        assertEquals("Test Part", part.getName());
    }

    @Test
    void testLookupProductIntegration() {
        // Arrange
        service.addProduct("Test Product", 29.99, 10, 1, 20, FXCollections.observableArrayList());

        // Act
        Product product = service.lookupProduct("Test Product");

        // Assert
        assertNotNull(product, "The product should be found.");
        assertEquals("Test Product", product.getName());
    }

    @Test
    void testDeletePartIntegration() {
        // Arrange
        service.addInhousePart("Test Part", 9.99, 10, 1, 20, 1234);
        Part part = inventory.lookupPart("Test Part");

        // Act
        service.deletePart(part);

        // Assert
        Part deletedPart = inventory.lookupPart("Test Part");
        assertNull(deletedPart, "The part should have been deleted.");
    }

    @Test
    void testDeleteProductIntegration() {
        // Arrange
        service.addProduct("Test Product", 29.99, 10, 1, 20, FXCollections.observableArrayList());
        Product product = inventory.lookupProduct("Test Product");

        // Act
        service.deleteProduct(product);

        // Assert
        Product deletedProduct = inventory.lookupProduct("Test Product");
        assertNull(deletedProduct, "The product should have been deleted.");
    }
}
