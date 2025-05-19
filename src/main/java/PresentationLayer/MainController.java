package PresentationLayer;

import java.awt.*;

/** Main controller for application views. */

public class MainController
{
    private final  MainView view;
    private final  OrderController orderController;

    public MainController(MainView view)
    {
        this.view = view;
        orderController = new OrderController(view.getOrderView());

        view.getClientsButton().addActionListener(e -> showPanel("Clients"));
        view.getProductsButton().addActionListener(e -> showPanel("Products"));
        view.getOrdersButton().addActionListener(e -> {
            showPanel("Orders");
            orderController.loadData();
        });

        new ClientController(view.getClientView());
        new ProductController(view.getProductView());
    }

    private void showPanel(String name)
    {
        CardLayout cardLayout = (CardLayout) view.getMainPanel().getLayout();
        cardLayout.show(view.getMainPanel(), name);
    }
}
