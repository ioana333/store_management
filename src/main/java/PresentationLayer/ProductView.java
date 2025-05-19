package PresentationLayer;

import javax.swing.*;
import java.awt.*;

/** UI for adding/editing/viewing products. */

public class ProductView extends JFrame
{
    private final JTextField nameField = new JTextField(20);
    private final JTextField priceField = new JTextField(10);
    private final JTextField stockField = new JTextField(10);

    private final JButton addButton = new JButton("Add");
    private final JButton editButton = new JButton("Edit");
    private final JButton deleteButton = new JButton("Delete");
    private final JButton viewAllButton = new JButton("View All Products");

    private final JTable productTable = new JTable();
    private final JScrollPane tableScroll = new JScrollPane(productTable);

    private final JPanel buttonPanel = new JPanel();
    private final JPanel formPanel = new JPanel(new GridLayout(4, 2));

    public ProductView()
    {
        formPanel.add(new JLabel("Product Name"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Product Price"));
        formPanel.add(priceField);
        formPanel.add(new JLabel("Product Stock"));
        formPanel.add(stockField);

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewAllButton);

    }

    public JPanel getMainPanel()
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(tableScroll, BorderLayout.SOUTH);
        return panel;
    }

    public JTable getProductTable() {
        return productTable;
    }

    public JButton getViewAllButton() {
        return viewAllButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JTextField getStockField() {
        return stockField;
    }

    public JTextField getPriceField() {
        return priceField;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JButton getEditButton() {
        return editButton;
    }
}
