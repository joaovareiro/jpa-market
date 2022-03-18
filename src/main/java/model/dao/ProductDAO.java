package model.dao;

import java.util.List;

import javax.persistence.EntityManager;

import market.model.persistence.Product;

public class ProductDAO {

	private EntityManager entityManager;

	public ProductDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void create(Product product) {
		this.entityManager.persist(product);
	}

	public void delete(Product product) {

		this.entityManager.remove(convertToMerge(product));

	}

	private Product convertToMerge(Product product) {
		return this.entityManager.merge(product);

	}

	public Product getByID(long id) {
		return this.entityManager.find(Product.class, id);
	}

	public void update(Product product) {
		convertToMerge(product);
	}

	@SuppressWarnings("unchecked")
	public List<Product> listAll() {
		String sql = "SELECT * FROM Product";
		return this.entityManager.createNativeQuery(sql, Product.class).getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<Product> listByName(String name) {
		String sql = "SELECT * FROM Product WHERE name=:name";
		return this.entityManager.createNativeQuery(sql, Product.class).setParameter("name", name).getResultList();
	}

}
