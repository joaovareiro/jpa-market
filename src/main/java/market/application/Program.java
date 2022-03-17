package market.application;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import connection.JpaConnectionFactory;
import market.model.persistence.Category;
import market.model.persistence.Product;
import services.CategoryService;
import services.ProductService;

public class Program {

	private static final Logger LOG = LogManager.getLogger(Program.class);

	public static void main(String[] args) {
		EntityManager entityManager = new JpaConnectionFactory().geEntityManager();
		ProductService productService = new ProductService(entityManager);
		CategoryService categoryService  = new CategoryService(entityManager);
		Product product = new Product("Cheetos","Requeijao 180g",new BigDecimal(12.99), new Category("Alimentos"));
		productService.create(product);
		productService.delete(1L);
	}

}
