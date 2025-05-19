package PresentationLayer;

import BusinessLogic.ProductBLL;
import Model.Product;
import Utility.TabelGenerator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Controller class responsible for managing product-related operations in the GUI.
 * This class connects the {@link ProductView} with the business logic in {@link ProductBLL}.
 *
 * Features:
 * <ul>
 *     <li>Handles the creation, update, deletion, and listing of products.</li>
 *     <li>Automatically fills in text fields when a product is selected in the table.</li>
 *     <li>Updates the database using {@link ProductBLL} which communicates with {@link DataAcess.ProductDAO}.</li>
 *     <li>Applies input validation and user feedback using dialogs.</li>
 * </ul>
 *
 * Usage:
 * <p>Bound to buttons: Add, Edit, Delete, and View All. On selection in the JTable, form fields are updated.</p>
 */

public class ProductController
{
    private final ProductView view;
    private final ProductBLL productBLL;

    public ProductController(ProductView view)
    {
        this.view = view;
        productBLL = new ProductBLL();

        view.getAddButton().addActionListener(this::handleAdd);
        view.getEditButton().addActionListener(this::handleEdit);
        view.getDeleteButton().addActionListener(this::handleDelete);
        view.getViewAllButton().addActionListener(this::handleViewAll);

        view.getProductTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = view.getProductTable().getSelectedRow();
                if (selectedRow != -1) {
                    try {
                        int productId = Integer.parseInt(view.getProductTable().getValueAt(selectedRow, 0).toString());
                        Product product = productBLL.findProductById(productId);
                        if (product != null) {
                            view.getNameField().setText(product.getName());
                            view.getPriceField().setText(String.valueOf(product.getPrice()));
                            view.getStockField().setText(String.valueOf(product.getStock()));
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
        Double price = Double.parseDouble(view.getPriceField().getText());
        int stock = Integer.parseInt(view.getStockField().getText());

        Product product = new Product(name, price, stock);
        productBLL.insertProduct(product);

        JOptionPane.showMessageDialog(view, "Product Added");
        handleViewAll(e);
    }

    private void handleDelete(ActionEvent e)
    {
        int selectedRow = view.getProductTable().getSelectedRow();
        if (selectedRow == -1)
        {
            JOptionPane.showMessageDialog(view, "Please select a product to delete");
            return;
        }

        try
        {
            int id = Integer.parseInt(view.getProductTable().getValueAt(selectedRow, 0).toString());
            productBLL.deleteProduct(id);

            handleViewAll(e);
        }
        catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(view, "Please select a product to delete");
            return;
        }
        JOptionPane.showMessageDialog(view, "Product Deleted");
    }

    private void handleViewAll(ActionEvent e)
    {
        List<Product> products = productBLL.findAllProduct();
        JTable table = TabelGenerator.generatedTable(products);
        view.getProductTable().setModel(table.getModel());
    }

    private void handleEdit(ActionEvent e)
    {
        int selectedRow = view.getProductTable().getSelectedRow();
        if(selectedRow == -1)
        {
            JOptionPane.showMessageDialog(view, "Please select a product to edit");
            return;
        }
        try
        {
            int id = Integer.parseInt(view.getProductTable().getValueAt(selectedRow, 0).toString());
            String newName = view.getNameField().getText();
            double newPrice = Double.parseDouble(view.getPriceField().getText());
            int newStock = Integer.parseInt(view.getStockField().getText());

            Product product = productBLL.findProductById(id);
            if(product == null)
            {
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(view, "Do you want to update this product?", "Confirm Edit", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;

            Product newProduct = new Product(id, newName, newPrice, newStock);
            productBLL.updateProduct(newProduct);

            JOptionPane.showMessageDialog(view, "Product Updated Successfully");
            handleViewAll(e);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Please select a product to edit");
        }
    }
}
