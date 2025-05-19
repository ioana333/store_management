package PresentationLayer;

import BusinessLogic.ClientBLL;
import BusinessLogic.OrderBLL;
import Utility.PDFGenerator;
import BusinessLogic.ProductBLL;
import DataAcess.LogDAO;
import Model.Bill;
import Model.Client;
import Model.Orders;
import Model.Product;
import Utility.TabelGenerator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Controller class that handles all logic for creating and managing orders.
 * It links the {@link OrderView} GUI to the business logic via {@link OrderBLL}, {@link ProductBLL}, and {@link ClientBLL}.
 *
 * Key responsibilities:
 * <ul>
 *     <li>Validates user input (client ID, quantity, product selection).</li>
 *     <li>Places a product order using {@link OrderBLL#placeOrder}.</li>
 *     <li>Updates product stock automatically after an order.</li>
 *     <li>Generates a PDF invoice using {@link PDFGenerator} and stores the bill in the {@link DataAcess.LogDAO}.</li>
 *     <li>Populates both product and order tables using reflection-based utilities.</li>
 * </ul>
 *
 * Features:
 * <ul>
 *     <li>Ensures the product list is filtered (e.g., only items with stock > 0) using streams.</li>
 *     <li>Handles exceptions and displays friendly messages using {@link JOptionPane}.</li>
 * </ul>
 *
 */

public class OrderController
{
    private final OrderView view;
    private final ClientBLL clientBLL;
    private final ProductBLL productBLL;
    private final OrderBLL orderBLL;
    private final LogDAO logDAO;

    private List<Product> products;
    private List<Orders> orders;

    public OrderController(OrderView view)
    {
        this.view = view;
        clientBLL = new ClientBLL();
        productBLL = new ProductBLL();
        orderBLL = new OrderBLL();
        logDAO = new LogDAO();

        loadData();

        view.getOrderButton().addActionListener(this::handlePlaceOrder);
    }

    public void loadData()
    {
        products = productBLL.findAllProduct();
        JTable productTable = TabelGenerator.generatedTable(products.stream().filter(p->p.getStock() > 0).toList());
        view.getProductTable().setModel(productTable.getModel());

        orders = orderBLL.findAllOrders();
        JTable ordersTable = TabelGenerator.generatedTable(orders);
        view.getOrderTable().setModel(ordersTable.getModel());
    }

    private void handlePlaceOrder(ActionEvent e)
    {
        try
        {
            if(view.getClientField().getText().isEmpty() || view.getQuantityField().getText().isEmpty())
            {
                JOptionPane.showMessageDialog(view, "Please specify at least one client and quantity");
                return;
            }

            int selectedRow = view.getProductTable().getSelectedRow();
            if(selectedRow == -1)
            {
                JOptionPane.showMessageDialog(null, "Please select a product first");
                return;
            }

            if(products == null || products.size() <= selectedRow)
            {
                JOptionPane.showMessageDialog(null, "Product list is not initialized or invalid");
                return;
            }

            int clientId = Integer.parseInt(view.getClientField().getText());
            int quantity = Integer.parseInt(view.getQuantityField().getText());

            Product selectedProduct = products.get(selectedRow);
            Client client = clientBLL.findClientById(clientId);
            if(client == null)
            {
                JOptionPane.showMessageDialog(view, "Client ID " + clientId + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Orders order = new Orders(client.getId(), selectedProduct.getId(), quantity);
            int orderId = orderBLL.placeOrder(order, client, selectedProduct);

            Bill bill = new Bill(orderId, client.getName(), selectedProduct.getName(), quantity , selectedProduct.getPrice()*quantity);
            logDAO.insertBill(bill);
            PDFGenerator.generateBillPDF(bill, "Bill_" + orderId + ".pdf");

            JOptionPane.showMessageDialog(null, "Order Placed Successfully");

            JTable billTable = TabelGenerator.generatedTable(logDAO.findAllBills());
            view.getOrderTable().setModel(billTable.getModel());

            loadData();
        }
        catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(view, "Client ID and Quantity must be numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "The order could not be placed", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
