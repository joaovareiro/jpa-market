package services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import market.application.Program;
import market.model.persistence.Category;
import market.model.persistence.Product;
import model.dao.ProductDAO;

public class ProductService {

	private static final Logger LOG = LogManager.getLogger(Program.class);

	private EntityManager entityManager;

	private ProductDAO productDAO;

	private CategoryService categoryService;

	public ProductService(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.productDAO = new ProductDAO(entityManager);
		this.categoryService = new CategoryService(entityManager);
	}


	public void create(Product product) {
		this.LOG.info("Preparando para a criação de um produto");
		if (product == null) {
			this.LOG.error("O produto informado está nulo");
			throw new RuntimeException("Product null!");
		}
		String categoryName = product.getCategory().getName();
		this.LOG.info("Buscando se já existe a categoria: " + categoryName);
		Category category = this.categoryService.findByName(categoryName);

		if (category != null) {
			product.setCategory(category);
		}

		try {
			getBeginTransaction();
			this.productDAO.create(product);
			commitAndCloseTransaction();
			this.LOG.info("Produto criado com sucesso");
		} catch (Exception e) {
			this.LOG.error("Erro ao criar um produto, causado por" + e.getMessage());
		}
	}

	private void commitAndCloseTransaction() {
		this.LOG.info("Commitando e Fechando transação com o banco de dados");
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	private void getBeginTransaction() {
		this.LOG.info("Abrindo transação com o banco de dados");
		entityManager.getTransaction().begin();
	}

	public void delete(Long id) {
		this.LOG.info("Produto encontrado com sucesso");
		if (id == null) {
			this.LOG.error("O id do produto informado está nulo");
			throw new RuntimeException("The ID is null");
		}
		Product product = this.productDAO.getByID(id);
		this.LOG.info("Produto encontrado com sucesso");
		getBeginTransaction();
		if (product == null) {
			this.LOG.error("O produto não exitste");
			throw new RuntimeException("Product not found!");
		}
		this.productDAO.delete(product);
		commitAndCloseTransaction();
		this.LOG.info("Produto deletado com sucesso!");
	}

	public void update(Product newProduct, Long productId) {

		this.LOG.info("Preparando para atualizar produto");
		if (newProduct == null || productId == null) {
			this.LOG.error("Um dos parametros está nulo!");
			throw new RuntimeException("The parameter is null");
		}

		Product product = this.productDAO.getByID(productId);

		validateProductIsNull(product);
		this.LOG.info("Produto encontrado no banco");
		getBeginTransaction();
		product.setName(newProduct.getName());
		product.setDescription(newProduct.getDescription());
		product.setPrice(newProduct.getPrice());
		product.setCategory(this.categoryService.findByName(newProduct.getCategory().getName()));
		this.LOG.info("Produto atualizado com sucesso");
		commitAndCloseTransaction();

	}

	private void validateProductIsNull(Product product) {
		if (product == null) {
			this.LOG.error("O produto não existe");
			throw new EntityNotFoundException("Product not Found!");
		}
	}

	public List<Product> listAll() {
		this.LOG.info("Preparando para listar produtos");
		List<Product> products = this.productDAO.listAll();

		if (products == null) {
			this.LOG.info("Não foram encontrados produtos");
			return new ArrayList<Product>();
		}
		this.LOG.info("Foram encontrados " + products.size() + " produtos.");
		return products;
	}

	
	public List<Product> listByName(String name) {
		if (name == null || name.isEmpty()) {
			this.LOG.info("O parametro nome está vazio");
			throw new RuntimeException("The parameter name is null");
		}
		
		this.LOG.info("Preparando para buscar os produtos com o nome: " + name);
		List<Product> products = this.productDAO.listByName(name.toLowerCase());
		
		if (products == null) {
			this.LOG.info("Não foram encontrados Produtos");
			return new ArrayList<Product>();
		}
		
		this.LOG.info("Foram encontrados " + products.size() + " produtos.");
		return products;
	}

}
