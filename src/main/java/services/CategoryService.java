package services;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import market.application.Program;
import market.model.persistence.Category;
import model.dao.CategoryDAO;

public class CategoryService {

	private static final Logger LOG = LogManager.getLogger(Program.class);

	private EntityManager entityManager;
	private CategoryDAO categoryDAO;

	public CategoryService(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.categoryDAO = new CategoryDAO(entityManager);
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

	public Category findByName(String name) {
		if (name == null || name.isEmpty()) {
			this.LOG.error("O Name não pode ser nulo");
			throw new RuntimeException("The name is Null");
		}
		try {
			return this.categoryDAO.findByName(name.toLowerCase());
		} catch (NoResultException e) {
			this.LOG.info("Não foi encontrado Categoria, logo ela será criada");
			return null;
		}

	}

	public void delete(Long id) {

	}

	public Category getById(Long id) {
		if (id == null) {
			this.LOG.error("O ID está nulo!");
			throw new RuntimeException("The parameter is null");
		}
		Category category = this.categoryDAO.getById(id);

		if (category == null) {
			this.LOG.error("Não foi encontrardo a categoria de id " + id);
			throw new EntityNotFoundException("category not found");
		}
		return category;
	}

}
