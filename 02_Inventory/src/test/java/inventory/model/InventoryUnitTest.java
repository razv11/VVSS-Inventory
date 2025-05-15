package inventory.model;

import javafx.collections.FXCollections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class InventoryUnitTest {

    private Inventory inventory;
    private Product testProduct;
    private InhousePart testPart;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
        testProduct = new Product(1, "Test Product", 19.99, 5, 1, 10, FXCollections.observableArrayList());
        testPart = new InhousePart(1, "Test Part", 9.99, 5, 1, 10, 1234);
    }

    @Test
    void testAddProduct() {
        inventory.addProduct(testProduct);
        assertEquals(1, inventory.getProducts().size(), "The product list should have exactly 1 element.");
        assertEquals("Test Product", inventory.getProducts().get(0).getName(), "The name of the added product is incorrect.");
    }

    @Test
    void testLookupProductByName() {
        inventory.addProduct(testProduct);
        Product foundProduct = inventory.lookupProduct("Test Product");
        assertNotNull(foundProduct, "The product should be found by name.");
        assertEquals(1, foundProduct.getProductId(), "The ID of the found product is incorrect.");
    }

    @Test
    void testAutoPartIdIncrement() {
        int firstId = inventory.getAutoPartId();
        int secondId = inventory.getAutoPartId();
        assertEquals(1, firstId, "The first generated ID should be 1.");
        assertEquals(2, secondId, "The second generated ID should be 2.");
    }

    @Test
    void testAddPart() {
        inventory.addPart(testPart);
        assertEquals(1, inventory.getAllParts().size(), "The part list should have exactly 1 element.");
        assertEquals("Test Part", inventory.getAllParts().get(0).getName(), "The name of the added part is incorrect.");
    }

    @Test
    void testLookupPartByName() {
        inventory.addPart(testPart);
        InhousePart foundPart = (InhousePart) inventory.lookupPart("Test Part");
        assertNotNull(foundPart, "The part should be found by name.");
        assertEquals(1234, foundPart.getMachineId(), "The machine ID of the found part is incorrect.");
    }

    @Test
    void testLookupPartById() {
        inventory.addPart(testPart);
        InhousePart foundPart = (InhousePart) inventory.lookupPart("1");
        assertNotNull(foundPart, "The part should be found by ID.");
        assertEquals("Test Part", foundPart.getName(), "The name of the found part is incorrect.");
    }
}
