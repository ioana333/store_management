package DataAcess;

import Model.Client;

/**
 * DAO for Client entities. Inherits basic operations from AbstractDAO.
 */
public class ClientDAO extends AbstractDAO<Client>
{

    public ClientDAO()
    {
        super(Client.class);
    }

}
