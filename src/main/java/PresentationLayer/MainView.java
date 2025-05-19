package PresentationLayer;

import javax.swing.*;
import java.awt.*;

/** Main window for the application using Swing. */

public class MainView extends JFrame
{
    private  final JButton clientsButton = new JButton("Clients");
    private  final JButton productsButton = new JButton("Products");
    private  final JButton ordersButton = new JButton("Orders");

    private final JPanel mainPanel = new JPanel(new CardLayout());

    private final ClientView clientView = new ClientView();
    private final ProductView productView = new ProductView();
    private final OrderView orderView = new OrderView();

    public MainView()
    {
        setTitle("Order Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.add(clientsButton);
        topPanel.add(productsButton);
        topPanel.add(ordersButton);
        add(topPanel, BorderLayout.NORTH);

        mainPanel.add(clientView.getMainPanel(), "Clients");
        mainPanel.add(productView.getMainPanel(), "Products");
        mainPanel.add(orderView.getMainPanel(), "Orders");

        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public JButton getClientsButton() {
        return clientsButton;
    }

    public JButton getProductsButton() {
        return productsButton;
    }

    public JButton getOrdersButton() {
        return ordersButton;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public ClientView getClientView() {
        return clientView;
    }

    public ProductView getProductView() {
        return productView;
    }

    public OrderView getOrderView() {
        return orderView;
    }
}
