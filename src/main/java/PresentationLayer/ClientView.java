package PresentationLayer;

import javax.swing.*;
import java.awt.*;

/** Swing UI panel for client operations. */

public class ClientView extends JPanel
{
    private final JTextField nameField = new JTextField(20);
    private final JTextField adressField = new JTextField(20);

    private final JButton addButton = new JButton("Add");
    private final JButton editButton = new JButton("Edit");
    private final JButton deleteButton = new JButton("Delete");
    private final JButton viewAllButton = new JButton("View All");

    private final JTable clientTable = new JTable();
    private final JScrollPane tableScroll = new JScrollPane(clientTable);

    private final JPanel formPanel = new JPanel(new GridLayout(3, 2));
    private final JPanel buttonPanel = new JPanel();

    public ClientView()
    {
        formPanel.add(new JLabel("Name"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Adress"));
        formPanel.add(adressField);

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

    public JTextField getNameField() {
        return nameField;
    }

    public JTextField getAdressField() {
        return adressField;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getViewAllButton() {
        return viewAllButton;
    }

    public JTable getClientTable() {
        return clientTable;
    }

    public JButton getEditButton() {
        return editButton;
    }
}
