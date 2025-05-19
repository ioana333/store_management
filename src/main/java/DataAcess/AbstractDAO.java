package DataAcess;

import MySQLDatabase.ConnectionFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.sql.*;

/**
 * Generic Data Access Object using Java Reflection.
 * Supports CRUD operations for all entities except immutable {@code Log}.
 *
 * Methods implemented dynamically:
 * <ul>
 *     <li>{@code findAll()}</li>
 *     <li>{@code findById(int id)}</li>
 *     <li>{@code insert(T t)}</li>
 *     <li>{@code update(T t)}</li>
 *     <li>{@code delete(int id)}</li>
 * </ul>
 *
 * The SQL queries are generated using the entity class' fields at runtime.
 *
 * @param <T> The type of object this DAO manages.
 */

public class AbstractDAO<T>
{
    private final Class<T> type;

    public AbstractDAO(Class<T> type)
    {
        this.type = type;
    }

    public List<T> findAll()
    {
        List<T> list = new ArrayList<>();
        String query = "SELECT * FROM " + type.getSimpleName().toLowerCase();

        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet result = statement.executeQuery())
        {
            while (result.next())
            {
                T instance = type.getDeclaredConstructor().newInstance();

                for(Field field : type.getDeclaredFields())
                {
                    String name = field.getName();
                    Object value = result.getObject(name);
                    String setter = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
                    Method setterMethod = type.getMethod(setter, field.getType());
                    setterMethod.invoke(instance, value);
                }

                list.add(instance);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public int insert(T t)
    {
        Field[] fields = type.getDeclaredFields();
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for(Field field : fields)
        {
            if(!field.getName().equalsIgnoreCase("id"))
            {
                columns.append(field.getName()).append(",");
                values.append("?,");
            }
        }

        String query = "INSERT INTO " + type.getSimpleName().toLowerCase() +
                " (" + columns.substring(0, columns.length()-1) +
                " ) VALUES (" + values.substring(0, values.length()-1) + " )";
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
        {
            int i = 1;
            for (Field field : fields)
            {
                field.setAccessible(true);
                if(!field.getName().equalsIgnoreCase("id"))
                {
                    statement.setObject(i, field.get(t));
                    i++;
                }
            }

            statement.executeUpdate();
            ResultSet result = statement.getGeneratedKeys();
            if(result.next())
            {
                return result.getInt(1);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return -1;
    }

    public T findById(int id)
    {
        T instance = null;
        String query = "SELECT * FROM " + type.getSimpleName().toLowerCase() + " WHERE id = ?";

        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if(result.next())
            {
                instance = type.getDeclaredConstructor().newInstance();
                for(Field field : type.getDeclaredFields())
                {
                    Object value = result.getObject(field.getName());
                    String setter = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                    Method method = type.getMethod(setter, field.getType());
                    method.invoke(instance, value);
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return instance;
    }

    public void delete(int id)
    {
        String query = "DELETE FROM " + type.getSimpleName().toLowerCase() + " WHERE id = ?";

        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void update(T t)
    {
        Field[] fields = type.getDeclaredFields();
        StringBuilder setClause = new StringBuilder();
        Object idValue = null;

        for(Field field : fields)
        {
            field.setAccessible(true);
            try
            {
                if(field.getName().equalsIgnoreCase("id"))
                {
                    idValue = field.get(t);
                }
                else
                {
                    setClause.append(field.getName()).append(" = ?, ");
                }
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }

        if(setClause.length() > 0)
        {
            setClause.setLength(setClause.length()-2);
        }

        String query = "UPDATE " + type.getSimpleName().toLowerCase() + " SET " + setClause + " WHERE id = ?";

        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement  statement = connection.prepareStatement(query))
        {
            int i = 1;
            for(Field field : fields)
            {
                if(!field.getName().equalsIgnoreCase("id"))
                {
                    statement.setObject(i, field.get(t));
                    i++;
                }
            }

            statement.setObject(i, idValue);

            statement.executeUpdate();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
