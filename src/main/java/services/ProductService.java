package services;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import market.application.Program;

public class ProductService {

	private static final Logger LOG = LogManager.getLogger(Program.class);

	private EntityManager entityManager;

	public ProductService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
