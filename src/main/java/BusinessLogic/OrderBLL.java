package BusinessLogic;

import DataAcess.OrderDAO;
import DataAcess.ProductDAO;
import Model.Bill;
import Model.Client;
import Model.Orders;
import Model.Product;

import javax.swing.*;
import java.util.List;

/**
 * Handles order logic, such as placing orders and updating product stock.
 */

public class OrderBLL
{
    private final OrderDAO orderDAO;
    private final ProductDAO productDAO;

    public OrderBLL()
    {
        this.orderDAO = new OrderDAO();
        this.productDAO = new ProductDAO();
    }

    /**
     * Places an order after checking product stock.
     * @param order Order object
     * @param client Client placing the order
     * @param product Product being ordered
     * @return ID of the newly placed order
     * @throws IllegalArgumentException if not enough stock
     */
    public int placeOrder(Orders order, Client client, Product product)
    {
        if(product.getStock() < order.getQuantity())
        {
            JOptionPane.showMessageDialog(null, "Not enough stock for " + product.getName(), "Error", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Not enough stock for " + product.getName());
        }

        //insert order
        int orderID = orderDAO.insert(order);
        if(orderID == -1)
        {
            throw new RuntimeException("Order failed to be placed");
        }

        //update stock
        product.setStock(product.getStock() - order.getQuantity());
        productDAO.update(product);

        //generate bill
        Bill bill = new Bill(orderID, client.getName(), product.getName(), order.getQuantity(), product.getPrice()*order.getQuantity());
        System.out.println("Generated bill: " + bill);

        return orderID;
    }

    /**
     * Gets all orders from the database.
     * @return list of orders
     */
    public List<Orders> findAllOrders()
    {
        return orderDAO.findAll();
    }

}
