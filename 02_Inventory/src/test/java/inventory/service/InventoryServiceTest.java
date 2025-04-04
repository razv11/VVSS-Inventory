package inventory.service;

import inventory.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InventoryServiceTest {
    private InventoryService inventoryService;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;
    private String companyName;

    @BeforeEach
    void setUp() {
        inStock = 10;
        min = 5;
        max = 20;
        companyName = "CompanyA";

        inventoryService = new InventoryService(new InventoryRepository());
    }

    @Nested
    @DisplayName("ECP Tests")
    class ECP {
        @ParameterizedTest
        @Timeout(3)
        @ValueSource(strings = {"Part1", "Part2", "Part3"})
        @DisplayName("Valid name")
        void testAddOutsourcePartValidName(String validName) {
            price = 10.0;

            assertDoesNotThrow(() -> inventoryService.addOutsourcePart(validName, price, inStock, min, max, companyName));
        }

        @Test
        @Timeout(5)
        @DisplayName("Invalid name")
        void testAddOutsourcePartInvalidName() {
            name = "";
            price = 11.0;

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                    inventoryService.addOutsourcePart(name, price, inStock, min, max, companyName));

            assertEquals("name is empty", exception.getMessage());
        }

        @Test
        @Timeout(3)
        @DisplayName("Valid price")
        void testAddOutsourcePartValidPrice() {
            name = "OutsourcedPart";
            price = 15.6;

            assertDoesNotThrow(() -> inventoryService.addOutsourcePart(name, price, inStock, min, max, companyName));
        }

        @Test
        @Timeout(5)
        @DisplayName("Invalid name")
        void testAddOutsourcePartInvalidPrice() {
            name = "Part101";
            price = -11.0;

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                    inventoryService.addOutsourcePart(name, price, inStock, min, max, companyName));

            assertEquals("price is not > 0", exception.getMessage());
        }
    }


    @Nested
    @DisplayName("BVA Tests")
    class BVA {
        @Test
        @EnabledOnOs(OS.WINDOWS)
        @DisplayName("Price = 0")
        void testAddOutsourcePartZeroPrice() {
            name = "OutsourcedPart";
            price = 0.0;

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                    inventoryService.addOutsourcePart(name, price, inStock, min, max, companyName));

            assertEquals("price is not > 0", exception.getMessage());
        }

        @Test
        @EnabledOnOs(OS.LINUX)
        @DisplayName("Price < 0")
        void testAddOutsourcePartNegativePrice() {
            name = "OutsourcedPart";
            price = -1.5;

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                    inventoryService.addOutsourcePart(name, price, inStock, min, max, companyName));

            assertEquals("price is not > 0", exception.getMessage());
        }


        @Test
        @Disabled
        @DisplayName("Price > 0")
        void testAddOutsourcePartPositivePrice() {
            name = "OutsourcedPart";
            price = 0.01;

            assertDoesNotThrow(() -> inventoryService.addOutsourcePart(name, price, inStock, min, max, companyName));
        }

        @Test
        @DisplayName("name.length() = 254")
        void testAddOutsourcePartName254Characters() {
            name = "6zd8VGvLXd6WCm5SN9j2J5qWtgV7YvrJ7g9LxFJi7idDCDLJjJwMJiu7BMaebSTTXfqjTjLvviGgrz0rAtBe60bGveL74W1L5fQhSXY3iweJCXSL7g7Gq8N2DGh8MYi3PXAqgYFfeYUZfSmZtgVWArifuzxTDVQXnn3NZt2DQ5BqcrYxM5yTFSAEBtevd18DvHcQxe5SrKLTnBp01UuCPPatGnXJcbfgg3X0Zm8wY1YU9Thm6W1XAPFYrkjGDc";
            price = 5.5;

            assertDoesNotThrow(() -> inventoryService.addOutsourcePart(name, price, inStock, min, max, companyName));
        }

        @Test
        @DisplayName("name.length() = 255")
        void testAddOutsourcePartName255Characters() {
            name = "96zd8VGvLXd6WCm5SN9j2J5qWtgV7YvrJ7g9LxFJi7idDCDLJjJwMJiu7BMaebSTTXfqjTjLvviGgrz0rAtBe60bGveL74W1L5fQhSXY3iweJCXSL7g7Gq8N2DGh8MYi3PXAqgYFfeYUZfSmZtgVWArifuzxTDVQXnn3NZt2DQ5BqcrYxM5yTFSAEBtevd18DvHcQxe5SrKLTnBp01UuCPPatGnXJcbfgg3X0Zm8wY1YU9Thm6W1XAPFYrkjGDc";
            price = 5.5;

            assertDoesNotThrow(() -> inventoryService.addOutsourcePart(name, price, inStock, min, max, companyName));
        }

        @Test
        @DisplayName("name.length() = 256")
        void testAddOutsourcePartName256CharactersInvalid() {
            name = "996zd8VGvLXd6WCm5SN9j2J5qWtgV7YvrJ7g9LxFJi7idDCDLJjJwMJiu7BMaebSTTXfqjTjLvviGgrz0rAtBe60bGveL74W1L5fQhSXY3iweJCXSL7g7Gq8N2DGh8MYi3PXAqgYFfeYUZfSmZtgVWArifuzxTDVQXnn3NZt2DQ5BqcrYxM5yTFSAEBtevd18DvHcQxe5SrKLTnBp01UuCPPatGnXJcbfgg3X0Zm8wY1YU9Thm6W1XAPFYrkjGDc";
            price = 5.5;

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                    inventoryService.addOutsourcePart(name, price, inStock, min, max, companyName));

            assertEquals("name is too long", exception.getMessage());
        }
    }
}