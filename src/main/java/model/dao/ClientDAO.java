package model.dao;

import java.util.List;
import javax.persistence.EntityManager;

import market.model.persistence.Client;

public class ClientDAO {
	private EntityManager entityManager;

	public ClientDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public void create(Client client) {
		this.entityManager.persist(client);
	}
	
	public void update(Client client) {
		convertToMerge(client);
	}
	

	private Client convertToMerge(Client client) {
		return this.entityManager.merge(client);
	}
	
	public void delete(Client client) {
		this.entityManager.remove(convertToMerge(client));
	}
	
	public Client getByID(long id) {
		return this.entityManager.find(Client.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Client> listAll() {
		String sql = "SELECT * FROM Client";
		return this.entityManager.createNativeQuery(sql, Client.class).getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<Client> listByName(String name) {
		String sql = "SELECT * FROM Client WHERE name=:name";
		return this.entityManager.createNativeQuery(sql, Client.class).setParameter("name", name).getResultList();
	}

}
