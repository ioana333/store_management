package DataAcess;

import Model.Orders;

/** DAO for Order entities. */

public class OrderDAO extends AbstractDAO<Orders>
{
    public OrderDAO()
    {
        super(Orders.class);
    }
}
