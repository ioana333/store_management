package BusinessLogic;

import DataAcess.ClientDAO;
import Model.Client;

import javax.swing.*;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Business logic class managing client-related operations.
 * Works closely with {@link DataAcess.ClientDAO} to abstract database access,
 * and provides safe and structured methods for client manipulation.
 *
 * Capabilities:
 * <ul>
 *     <li>Adds new clients.</li>
 *     <li>Fetches all clients for table view generation.</li>
 *     <li>Updates client records with consistent logic.</li>
 *     <li>Retrieves a single client by ID.</li>
 * </ul>
 */

public class ClientBLL
{
    private final ClientDAO clientDAO;

    /**Contructor initializes the DAO for {@link Model.Client}. */
    public ClientBLL()
    {
        this.clientDAO = new ClientDAO();
    }

    /**
     * Finds a client by ID.
     * @param id the ID of the client
     * @return the found Client object
     * @throws NoSuchElementException if client is not found
     */
    public Client findClientById(int id)
    {
        Client client = clientDAO.findById(id);
        if(client == null)
        {
            JOptionPane.showMessageDialog(null, "Client not found", "Error", JOptionPane.ERROR_MESSAGE);
            throw new NoSuchElementException("Client not found");
        }
        return client;
    }

    /**
     * Inserts a new client into the database.
     * @param client the Client object to be inserted
     * @return the ID of the inserted client
     */
    public int insertClient(Client client)
    {
        return clientDAO.insert(client);
    }

    /**
     * Retrieves all clients from the database.
     * @return list of all clients
     */
    public List<Client> findAllClients()
    {
        return clientDAO.findAll();
    }

    /**
     * Deletes a client by ID.
     * @param id ID of the client to delete
     */
    public void deleteClient(int id)
    {
        clientDAO.delete(id);
    }

    /**
     * Updates a client's data in the database.
     * @param client updated Client object
     */
    public void updateClient(Client client)
    {
        clientDAO.update(client);
    }
}
