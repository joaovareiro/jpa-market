package services;

import javax.persistence.EntityManager;

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
	
	public Category findByName(String name) {
		if(name == null || name.isEmpty()) {
			this.LOG.error("O Name não pode ser nulo");
			throw new RuntimeException("The name is Null");
		}
		
		
		return this.categoryDAO.findByName(name.toLowerCase());
	}

}
