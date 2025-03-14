
package inventory.model;

import javafx.collections.ObservableList;

public class Product {
    
    // Declare fields
    private ObservableList<Part> associatedParts;
    private int productId;
    private String name;
    private double price;
    private int inStock;
    private int min;
    private int max;

    // Constructor
    public Product(int productId, String name, double price, int inStock, int min, int max, ObservableList<Part> partList) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.min = min;
        this.max = max;
        this.associatedParts= partList;
    }
    
    // Getters
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getInStock() {
        return inStock;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
    
    // Setters
    public void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMax(int max) {
        this.max = max;
    }
    
    // Other methods
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    
    public void removeAssociatedPart(Part part) {
        associatedParts.remove(part);
    }
    
    public Part lookupAssociatedPart(String searchItem) {
        for(Part p:associatedParts) {
            if(p.getName().contains(searchItem) || Integer.toString(p.getPartId()).equals(searchItem)) return p;
        }
        return null;
    }

    @Override
    public String toString() {
        return "P,"+this.productId+","+this.name+","+this.price+","+this.inStock+","+
                this.min+","+this.max;
    }
}