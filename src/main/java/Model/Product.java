package Model;

/**
 * Data model representing a product entity.
 * Each product has a unique ID, a name, a unit price, and a stock quantity.
 *
 * Fields:
 * <ul>
 *     <li>{@code id} — primary key for the product.</li>
 *     <li>{@code name} — descriptive name of the product.</li>
 *     <li>{@code price} — unit price of the product.</li>
 *     <li>{@code stock} — available stock quantity.</li>
 * </ul>
 */

public class Product
{
    private int id;
    private String name;
    private double price;
    private int stock;

    public Product(int id, String name, double price, int stock)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public Product(String name, double price, int stock)
    {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Product() {
    }
}
