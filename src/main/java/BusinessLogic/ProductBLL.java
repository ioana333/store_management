package BusinessLogic;

import DataAcess.ProductDAO;
import Model.Product;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Manages business logic related to Product operations.
 */

public class ProductBLL
{
    private final ProductDAO productDAO;

    public ProductBLL()
    {
        this.productDAO = new ProductDAO();
    }

    public Product findProductById(int id)
    {
        Product product = productDAO.findById(id);

        if(product == null)
        {
            throw new NoSuchElementException("Product not found");
        }

        return product;
    }

    public int insertProduct(Product product)
    {
        return productDAO.insert(product);
    }

    public List<Product> findAllProduct()
    {
        return productDAO.findAll();
    }

    public void deleteProduct(int id)
    {
        productDAO.delete(id);
    }

    public void updateProduct(Product product)
    {
        productDAO.update(product);
    }

    /**
     * Checks if product stock is sufficient.
     * @param product the product
     * @param quantity desired quantity
     * @return true if in stock, false otherwise
     */
    public boolean checkStock(Product product, int quantity)
    {
        return product.getStock() >= quantity;
    }
}
