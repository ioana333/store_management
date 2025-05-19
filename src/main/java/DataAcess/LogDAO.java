package DataAcess;

import Model.Bill;
import MySQLDatabase.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for managing the Log table, which stores {@link Model.Bill} objects.
 * Only supports read and insert operations due to the immutability of {@code Bill}.
 */

public class LogDAO
{
    private static final String INSERT_QUERY = "INSERT INTO log (orderID, clientName, productName, quantity, totalPrice) VALUES (?,?,?,?,?)";
    private static final String SELECT_QUERY = "SELECT * FROM log";

    public void insertBill(Bill bill)
    {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_QUERY))
        {
            statement.setInt(1, bill.orderId());
            statement.setString(2, bill.clientName());
            statement.setString(3, bill.productName());
            statement.setInt(4, bill.quantity());
            statement.setDouble(5, bill.totalPrice());

            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public List<Bill> findAllBills()
    {
        List<Bill> bills = new ArrayList<>();

        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_QUERY);
            ResultSet result = statement.executeQuery())
        {
            while(result.next())
            {
                int orderID = result.getInt("orderId");
                String clientName = result.getString("clientName");
                String productName = result.getString("productName");
                int quantity = result.getInt("quantity");
                double totalPrice = result.getDouble("totalPrice");

                bills.add(new Bill(orderID, clientName, productName, quantity, totalPrice));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return bills;
    }
}
