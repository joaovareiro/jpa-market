package model.dao;

import javax.persistence.EntityManager;

public class ClientDAO {
	private EntityManager entityManager;

	public ClientDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
