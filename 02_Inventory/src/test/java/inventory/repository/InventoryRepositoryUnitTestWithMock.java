package inventory.repository;

import inventory.model.InhousePart;
import inventory.model.Inventory;
import inventory.model.Part;
import inventory.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class InventoryRepositoryUnitTestWithMock {

    @Mock
    private Inventory mockInventory;

    private InventoryRepository inventoryRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        inventoryRepository = new InventoryRepository();
        inventoryRepository.setInventory(mockInventory);

        when(mockInventory.getAllParts()).thenReturn(FXCollections.observableArrayList());
        when(mockInventory.getProducts()).thenReturn(FXCollections.observableArrayList());
    }

    @Test
    void testAddPart() {
        // Arrange
        InhousePart part = new InhousePart(1, "Test Part", 9.99, 10, 1, 20, 1234);

        // Act
        inventoryRepository.addPart(part);

        // Assert
        verify(mockInventory, times(1)).addPart(part);
    }

    @Test
    void testAddProduct() {
        // Arrange
        ObservableList<Part> parts = FXCollections.observableArrayList();
        Product product = new Product(1, "Test Product", 29.99, 10, 1, 20, parts);

        // Act
        inventoryRepository.addProduct(product);

        // Assert
        verify(mockInventory, times(1)).addProduct(product);
    }

    @Test
    void testGetAutoPartId() {
        // Arrange
        when(mockInventory.getAutoPartId()).thenReturn(100);

        // Act
        int autoPartId = inventoryRepository.getAutoPartId();

        // Assert
        assertEquals(100, autoPartId);
        verify(mockInventory, times(1)).getAutoPartId();
    }

    @Test
    void testGetAutoProductId() {
        // Arrange
        when(mockInventory.getAutoProductId()).thenReturn(200);

        // Act
        int autoProductId = inventoryRepository.getAutoProductId();

        // Assert
        assertEquals(200, autoProductId);
        verify(mockInventory, times(1)).getAutoProductId();
    }

    @Test
    void testGetAllParts() {
        // Arrange
        ObservableList<Part> mockParts = FXCollections.observableArrayList();
        when(mockInventory.getAllParts()).thenReturn(mockParts);

        // Act
        ObservableList<Part> parts = inventoryRepository.getAllParts();

        // Assert
        assertEquals(mockParts, parts);
        verify(mockInventory, times(1)).getAllParts();
    }

    @Test
    void testGetAllProducts() {
        // Arrange
        ObservableList<Product> mockProducts = FXCollections.observableArrayList();
        when(mockInventory.getProducts()).thenReturn(mockProducts);

        // Act
        ObservableList<Product> products = inventoryRepository.getAllProducts();

        // Assert
        assertEquals(mockProducts, products);
        verify(mockInventory, times(1)).getProducts();
    }

    @Test
    void testLookupPart() {
        // Arrange
        Part mockPart = mock(Part.class);
        when(mockInventory.lookupPart("Part A")).thenReturn(mockPart);

        // Act
        Part foundPart = inventoryRepository.lookupPart("Part A");

        // Assert
        assertEquals(mockPart, foundPart);
        verify(mockInventory, times(1)).lookupPart("Part A");
    }

    @Test
    void testLookupProduct() {
        // Arrange
        Product mockProduct = mock(Product.class);
        when(mockInventory.lookupProduct("Product A")).thenReturn(mockProduct);

        // Act
        Product foundProduct = inventoryRepository.lookupProduct("Product A");

        // Assert
        assertEquals(mockProduct, foundProduct);
        verify(mockInventory, times(1)).lookupProduct("Product A");
    }
}
