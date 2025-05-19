package PresentationLayer;

import BusinessLogic.ClientBLL;
import Model.Client;
import Utility.TabelGenerator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Controller class that manages client-related operations within the application GUI.
 * Acts as the mediator between {@link ClientView} and {@link ClientBLL}.
 *
 * Functional responsibilities:
 * <ul>
 *     <li>Adds new clients to the system.</li>
 *     <li>Deletes existing clients.</li>
 *     <li>Updates client records through the Edit function.</li>
 *     <li>Automatically fills in name and address fields when a client is selected in the JTable.</li>
 *     <li>Displays all clients retrieved from {@link BusinessLogic.ClientBLL}.</li>
 * </ul>
 *
 * Implementation:
 * <p>Uses a {@link javax.swing.JTable} selection listener to track the currently selected client and populate fields.</p>
 * <p>Lambda expressions and streams are used to filter non-null clients before populating the table.</p>
 */

public class ClientController
{
    private final ClientView view;
    private final ClientBLL clientBLL;

    public ClientController(ClientView view)
    {
        this.view = view;
        this.clientBLL = new ClientBLL();

        view.getAddButton().addActionListener(this::handleAdd);
        view.getEditButton().addActionListener(this::handleEdit);
        view.getDeleteButton().addActionListener(this::handleDelete);
        view.getViewAllButton().addActionListener(this::handleViewAll);

        view.getClientTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.getClientTable().getSelectedRow();
                if (selectedRow != -1) {
                    try {
                        int clientId = Integer.parseInt(view.getClientTable().getValueAt(selectedRow, 0).toString());
                        Client client = clientBLL.findClientById(clientId);
                        if (client != null) {
                            view.getNameField().setText(client.getName());
                            view.getAdressField().setText(client.getAddress());
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

    }

    private void handleAdd(ActionEvent e)
    {
        String name = view.getNameField().getText();
        String adress = view.getAdressField().getText();
        Client client = new Client(name, adress);
        clientBLL.insertClient(client);

        JOptionPane.showMessageDialog(view, "Client added");
        handleViewAll(e);
    }

    private void handleDelete(ActionEvent e)
    {
        int selectedRow = view.getClientTable().getSelectedRow();
        if (selectedRow == -1)
        {
            JOptionPane.showMessageDialog(view, "Please select a client first");
            return;
        }

        try
        {
            int id = Integer.parseInt(view.getClientTable().getValueAt(selectedRow, 0).toString());
            clientBLL.deleteClient(id);
            handleViewAll(e);
        }
        catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(view, "Please select a client first");
        }
        JOptionPane.showMessageDialog(view, "Client deleted");
    }

    private void handleViewAll(ActionEvent e)
    {
        List<Client> clients = clientBLL.findAllClients();
        JTable clientTable = TabelGenerator.generatedTable(clients.stream().filter(c -> c.getName() != null).toList());
        view.getClientTable().setModel(clientTable.getModel());
    }

    private void handleEdit(ActionEvent e)
    {
        int selectedRow = view.getClientTable().getSelectedRow();
        if(selectedRow == -1)
        {
            JOptionPane.showMessageDialog(view, "Please select a client first");
            return;
        }

        try
        {
            int id = Integer.parseInt(view.getClientTable().getValueAt(selectedRow, 0).toString());

            Client client = clientBLL.findClientById(id);
            if(client == null)
            {
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(view, "Do you want to update this client?", "Confirm Edit", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            String newName = view.getNameField().getText();
            String newAdress = view.getAdressField().getText();

            Client newClient = new Client(id, newName, newAdress);
            clientBLL.updateClient(newClient);

            JOptionPane.showMessageDialog(view, "Client updated Successfully");
            handleViewAll(e);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "The Client could not be updated");
        }
    }

}
