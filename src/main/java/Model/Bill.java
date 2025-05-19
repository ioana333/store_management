package Model;

/**
 * Immutable data model representing a bill (invoice) generated for each order.
 * This class is a Java {@code record}, which ensures:
 * <ul>
 *     <li>Immutable structure by default.</li>
 *     <li>Compact syntax with built-in getters and equals/hashCode/toString.</li>
 * </ul>
 *
 * Stored in the {@code Log} table. Supports only insertion and retrieval; no updates allowed.
 *
 * Fields:
 * <ul>
 *     <li>{@code orderId} — the ID of the order.</li>
 *     <li>{@code clientName} — the name of the client.</li>
 *     <li>{@code productName} — the name of the product.</li>
 *     <li>{@code quantity} — quantity of the ordered product.</li>
 *     <li>{@code totalPrice} — final price calculated as quantity × unit price.</li>
 * </ul>
 */

public record Bill(int orderId, String clientName, String productName, int quantity, double totalPrice)
{

}