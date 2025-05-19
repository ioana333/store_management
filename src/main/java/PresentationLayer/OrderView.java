package PresentationLayer;

import javax.swing.*;
import java.awt.*;

/** UI panel for managing and placing orders. */

public class OrderView extends JPanel
{
    private final JTextField clientField = new JTextField(10);
    private final JTextField quantityField = new JTextField(10);

    private final JButton orderButton = new JButton("Place Order");

    private final JTable productTable = new JTable();
    private final JScrollPane productTableScrollPane = new JScrollPane(productTable);

    private final JTable orderTable = new JTable();
    private final JScrollPane orderTableScrollPane = new JScrollPane(orderTable);

    public OrderView()
    {
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Client ID"));
        inputPanel.add(clientField);
        inputPanel.add(new JLabel("Quantity"));
        inputPanel.add(quantityField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(orderButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, productTableScrollPane, orderTableScrollPane);
        splitPane.setDividerLocation(200);

        add(topPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);

    }

    public JPanel getMainPanel() { return this;}

    public JTextField getClientField() {
        return clientField;
    }

    public JTextField getQuantityField() {
        return quantityField;
    }

    public JButton getOrderButton() {
        return orderButton;
    }

    public JTable getProductTable() {
        return productTable;
    }

    public JTable getOrderTable() {
        return orderTable;
    }
}
