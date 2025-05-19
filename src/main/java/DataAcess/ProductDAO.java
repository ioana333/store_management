package DataAcess;

import Model.Product;

/** DAO for Product entities. */

public class ProductDAO extends AbstractDAO<Product>
{
    public ProductDAO()
    {
        super(Product.class);
    }
}
