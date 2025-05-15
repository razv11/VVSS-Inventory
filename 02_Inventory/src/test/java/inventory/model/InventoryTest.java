package inventory.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class InventoryTest {
    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
        inventory.addPart(new InhousePart(1, "Bolt", 120.55, 5, 1, 10, 14));
        inventory.addPart(new OutsourcedPart(2, "any", 120.55, 5, 1, 10, "Company X"));
    }

    @Test
    void testLookupPart_ValidName() {
        Part result = inventory.lookupPart("bolt");
        assertNotNull(result);
        assertEquals("Bolt", result.getName());
        assertEquals(1, result.getPartId());
    }

    @Test
    void testLookupPart_InvalidName() {
        Part result = inventory.lookupPart("Brake");
        assertNull(result);
    }
}