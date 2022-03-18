package model.dao;

import javax.persistence.EntityManager;

import market.model.persistence.Category;

public class CategoryDAO {
	private EntityManager entityManager;

	public CategoryDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public Category findByName(String name) {
		String sql = "SELECT * FROM Category WHERE name = :name";
		return (Category) this.entityManager.createNativeQuery(sql, Category.class).setParameter("name", name).getSingleResult();
	}
	
	public void delete(Category category) {
		this.entityManager.remove(category);
	}
	
	public Category getById(Long id) {
		String sql = "SELECT * FROM Category WHERE id = :id";
		return (Category) this.entityManager.createNativeQuery(sql, Category.class).setParameter("id", id).getSingleResult();
	}

}
