package Utility;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Utility class for generating {@link javax.swing.JTable} components dynamically using reflection.
 * Accepts a list of any object type and introspects its fields to generate both table headers and rows.
 *
 * Key functionality:
 * <ul>
 *     <li>Uses {@link java.lang.reflect.Field} to extract column names.</li>
 *     <li>Populates rows by calling {@link Field#get} on each field for each object in the list.</li>
 *     <li>Provides a universal table creation mechanism for all models (Client, Product, Order, etc.).</li>
 * </ul>
 *
 * Example usage:
 * <pre>
 *     List&lt;Product&gt; products = productBLL.findAll();
 *     JTable table = TabelGenerator.generatedTable(products);
 * </pre>
 */

public class TabelGenerator
{
    public static JTable generatedTable(List<?> objectList)
    {
        if(objectList == null || objectList.isEmpty())
        {
            return new JTable();
        }

        Object first = objectList.get(0);
        Class<?> clazz = first.getClass();
        Field[] fields = clazz.getDeclaredFields();

        String[] columnNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++)
        {
            columnNames[i] = fields[i].getName();
        }

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for(Object object : objectList)
        {
            Object[] rowData = new Object[fields.length];
            for (int i = 0; i < fields.length; i++)
            {
                fields[i].setAccessible(true);
                try
                {
                    rowData[i] = fields[i].get(object);
                }
                catch (IllegalAccessException e)
                {
                    rowData[i] = "ERROR";
                }
            }
            model.addRow(rowData);
        }

        return new JTable(model);
    }
}
