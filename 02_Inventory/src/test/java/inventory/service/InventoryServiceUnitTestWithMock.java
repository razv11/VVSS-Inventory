package inventory.service;

import inventory.model.InhousePart;
import inventory.model.OutsourcedPart;
import inventory.model.Part;
import inventory.model.Product;
import inventory.repository.InventoryRepositoryInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class InventoryServiceUnitTestWithMock {
    @Mock
    private InventoryRepositoryInterface mockRepo;

    @InjectMocks
    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddInhousePart() {
        // Arrange
        when(mockRepo.getAutoPartId()).thenReturn(1);

        // Act
        inventoryService.addInhousePart("Part A", 9.99, 5, 1, 10, 1234);

        // Assert
        ArgumentCaptor<InhousePart> partCaptor = ArgumentCaptor.forClass(InhousePart.class);
        verify(mockRepo, times(1)).addPart(partCaptor.capture());

        InhousePart capturedPart = partCaptor.getValue();
        assertEquals("Part A", capturedPart.getName());
        assertEquals(1, capturedPart.getPartId());
        assertEquals(1234, capturedPart.getMachineId());
    }

    @Test
    void testAddOutsourcePart() {
        // Arrange
        when(mockRepo.getAutoPartId()).thenReturn(2);

        // Act
        inventoryService.addOutsourcePart("Part B", 19.99, 5, 1, 10, "Outsourced Part");

        // Assert
        ArgumentCaptor<OutsourcedPart> partCaptor = ArgumentCaptor.forClass(OutsourcedPart.class);
        verify(mockRepo, times(1)).addPart(partCaptor.capture());

        OutsourcedPart capturedPart = partCaptor.getValue();
        assertEquals("Part B", capturedPart.getName());
        assertEquals(2, capturedPart.getPartId());
        assertEquals("Outsourced Part", capturedPart.getCompanyName());
    }

    @Test
    void testAddProduct() {
        // Arrange
        when(mockRepo.getAutoProductId()).thenReturn(1);
        ObservableList<Part> parts = FXCollections.observableArrayList();

        // Act
        inventoryService.addProduct("Product A", 29.99, 10, 1, 20, parts);

        // Assert
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(mockRepo, times(1)).addProduct(productCaptor.capture());

        Product capturedProduct = productCaptor.getValue();
        assertEquals("Product A", capturedProduct.getName());
        assertEquals(1, capturedProduct.getProductId());
        assertEquals(parts, capturedProduct.getAssociatedParts());
    }

    @Test
    void testGetAllParts() {
        // Arrange
        ObservableList<Part> mockParts = FXCollections.observableArrayList();
        when(mockRepo.getAllParts()).thenReturn(mockParts);

        // Act
        ObservableList<Part> parts = inventoryService.getAllParts();

        // Assert
        assertEquals(mockParts, parts);
    }

    @Test
    void testLookupPart() {
        // Arrange
        Part mockPart = mock(Part.class);
        when(mockRepo.lookupPart("Part C")).thenReturn(mockPart);

        // Act
        Part foundPart = inventoryService.lookupPart("Part C");

        // Assert
        assertEquals(mockPart, foundPart);
    }
}
