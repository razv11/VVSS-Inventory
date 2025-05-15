package inventory.integration;

import inventory.model.InhousePart;
import inventory.model.Inventory;
import inventory.model.OutsourcedPart;
import inventory.model.Part;
import inventory.model.Product;
import inventory.repository.InventoryRepository;
import inventory.service.InventoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class InventoryServiceIntegrationTest {
    @Mock
    private Inventory mockInventory;

    private InventoryRepository inventoryRepository;
    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        inventoryRepository = new InventoryRepository();
        inventoryRepository.setInventory(mockInventory);

        inventoryService = new InventoryService(inventoryRepository);

        when(mockInventory.getAllParts()).thenReturn(FXCollections.observableArrayList());
        when(mockInventory.getProducts()).thenReturn(FXCollections.observableArrayList());
    }

    @Test
    void testAddInhousePart() {
        // Arrange
        when(mockInventory.getAutoPartId()).thenReturn(1);

        // Act
        inventoryService.addInhousePart("Test Inhouse Part", 9.99, 10, 1, 20, 1234);

        // Assert
        ArgumentCaptor<InhousePart> partCaptor = ArgumentCaptor.forClass(InhousePart.class);
        verify(mockInventory).addPart(partCaptor.capture());

        InhousePart capturedPart = partCaptor.getValue();
        assertEquals(1, capturedPart.getPartId());
        assertEquals("Test Inhouse Part", capturedPart.getName());
        assertEquals(9.99, capturedPart.getPrice());
        assertEquals(10, capturedPart.getInStock());
        assertEquals(1, capturedPart.getMin());
        assertEquals(20, capturedPart.getMax());
        assertEquals(1234, capturedPart.getMachineId());
    }

    @Test
    void testAddOutsourcePart() {
        // Arrange
        when(mockInventory.getAutoPartId()).thenReturn(2);

        // Act
        inventoryService.addOutsourcePart("Test Part", 19.99, 5, 1, 10, "ACME Corp");

        // Assert
        ArgumentCaptor<OutsourcedPart> partCaptor = ArgumentCaptor.forClass(OutsourcedPart.class);
        verify(mockInventory).addPart(partCaptor.capture());

        OutsourcedPart capturedPart = partCaptor.getValue();
        assertEquals(2, capturedPart.getPartId());
        assertEquals("Test Part", capturedPart.getName());
        assertEquals(19.99, capturedPart.getPrice());
        assertEquals(5, capturedPart.getInStock());
        assertEquals(1, capturedPart.getMin());
        assertEquals(10, capturedPart.getMax());
        assertEquals("ACME Corp", capturedPart.getCompanyName());
    }

    @Test
    void testAddProduct() {
        // Arrange
        when(mockInventory.getAutoProductId()).thenReturn(3);
        ObservableList<Part> parts = FXCollections.observableArrayList();

        // Act
        inventoryService.addProduct("Test Product", 29.99, 10, 1, 20, parts);

        // Assert
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(mockInventory).addProduct(productCaptor.capture());

        Product capturedProduct = productCaptor.getValue();
        assertEquals(3, capturedProduct.getProductId());
        assertEquals("Test Product", capturedProduct.getName());
        assertEquals(29.99, capturedProduct.getPrice());
        assertEquals(10, capturedProduct.getInStock());
        assertEquals(1, capturedProduct.getMin());
        assertEquals(20, capturedProduct.getMax());
        assertEquals(parts, capturedProduct.getAssociatedParts());
    }

    @Test
    void testGetAllParts() {
        // Arrange
        ObservableList<Part> mockParts = FXCollections.observableArrayList();
        when(mockInventory.getAllParts()).thenReturn(mockParts);

        // Act
        ObservableList<Part> parts = inventoryService.getAllParts();

        // Assert
        assertEquals(mockParts, parts);
    }

    @Test
    void testGetAllProducts() {
        // Arrange
        ObservableList<Product> mockProducts = FXCollections.observableArrayList();
        when(mockInventory.getProducts()).thenReturn(mockProducts);

        // Act
        ObservableList<Product> products = inventoryService.getAllProducts();

        // Assert
        assertEquals(mockProducts, products);
    }

    @Test
    void testLookupPart() {
        // Arrange
        Part mockPart = mock(Part.class);
        when(mockInventory.lookupPart("Test Part")).thenReturn(mockPart);

        // Act
        Part foundPart = inventoryService.lookupPart("Test Part");

        // Assert
        assertEquals(mockPart, foundPart);
    }

    @Test
    void testLookupProduct() {
        // Arrange
        Product mockProduct = mock(Product.class);
        when(mockInventory.lookupProduct("Test Product")).thenReturn(mockProduct);

        // Act
        Product foundProduct = inventoryService.lookupProduct("Test Product");

        // Assert
        assertEquals(mockProduct, foundProduct);
    }
}
