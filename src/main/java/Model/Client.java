package Model;

/**
 * Data model representing a client in the system.
 * Each client has an ID, a name, and an address.
 *
 * Fields:
 * <ul>
 *     <li>{@code id} — primary key for the client.</li>
 *     <li>{@code name} — name of the client.</li>
 *     <li>{@code address} — address of the client.</li>
 * </ul>
 */

public class Client
{
    private int id;
    private String name;
    private String address;

    public Client(int id, String name, String address)
    {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Client(String name, String address)
    {
        this.name = name;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Client() {
    }
}
