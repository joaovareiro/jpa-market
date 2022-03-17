package services;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import market.application.Program;
import market.model.persistence.Product;
import model.dao.ProductDAO;

public class ProductService {

	private static final Logger LOG = LogManager.getLogger(Program.class);

	private EntityManager entityManager;

	private ProductDAO productDAO;

	public ProductService(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.productDAO = new ProductDAO(entityManager);
	}

	public void create(Product product) {
		this.LOG.info("Preparando para a criação de um produto");
		if (product == null) {
			this.LOG.error("O produto informado está nulo");
			throw new RuntimeException("Product null!");
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
		if(product == null) {
			this.LOG.error("O produto não exitste");
			throw new RuntimeException("Product not found!");
		}
		this.productDAO.delete(product);
		commitAndCloseTransaction();
		this.LOG.info("Produto deletado com sucesso!");
	}

}
