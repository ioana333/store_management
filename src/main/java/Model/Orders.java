package Model;

/**
 * Data model for representing an order.
 * Orders connect a client with a product and include a quantity.
 *
 * Fields:
 * <ul>
 *     <li>{@code id} — primary key of the order.</li>
 *     <li>{@code clientId} — foreign key referencing a client.</li>
 *     <li>{@code productId} — foreign key referencing a product.</li>
 *     <li>{@code quantity} — number of units ordered.</li>
 * </ul>
 */

public class Orders
{
    private int id;
    private int clientId;
    private int productId;
    private int quantity;

    public Orders(int id, int clientId, int productId, int quantity)
    {
        this.id = id;
        this.clientId = clientId;
        this.productId = productId;
        this.quantity = quantity;
    }
    public Orders(int clientId, int productId, int quantity)
    {
        this.clientId = clientId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public int getClientId() {
        return clientId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Orders() {
    }
}
