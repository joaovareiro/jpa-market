package services;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import market.application.Program;
import market.model.persistence.Category;
import market.model.persistence.Client;
import market.model.persistence.Product;
import model.dao.ClientDAO;

public class ClientService {
	

	private static final Logger LOG = LogManager.getLogger(Program.class);

	private EntityManager entityManager;
	
	private ClientDAO clientDAO;
	
	public ClientService(EntityManager entityManager) {
		this.entityManager = entityManager;
		this.clientDAO = new ClientDAO(entityManager);
	}
	
	public void create(Client client) {
		this.LOG.info("Preparando para a criação de um cliente");
		if (client == null) {
			this.LOG.error("O cliente informado está nulo");
			throw new RuntimeException("Product null!");
		}
		
		if(!validarCPF(client.getCpf())){
			this.LOG.error("O cpf está em um formato inválido");
			throw new RuntimeException("Invalid format");
		}
		
		try {
			getBeginTransaction();
			this.clientDAO.create(client);
			commitAndCloseTransaction();
			this.LOG.info("Cliente criado com sucesso");
		} catch (Exception e) {
			this.LOG.error("Erro ao criar um cliente, causado por" + e.getMessage());
		}
	}
	
    public static boolean validarCPF(String cpfTeste){
        boolean cpfvalido = false;
        String regx = "(\\d{3})+.+(\\d{3})+.+(\\d{3})+-+(\\d{2})";
        Pattern padrao = Pattern.compile(regx);
        Matcher validador = padrao.matcher(cpfTeste);
        if(validador.matches()){
            cpfvalido = true;
        }
        return cpfvalido;
    }
    
	public void delete(Long id) {
		if (id == null) {
			this.LOG.error("O id do cliente informado está nulo");
			throw new RuntimeException("The ID is null");
		}
		
		Client client = this.clientDAO.getByID(id);
		this.LOG.info("Cliente encontrado com sucesso");
		getBeginTransaction();
		if (client == null) {
			this.LOG.error("O cliente não existe");
			throw new RuntimeException("Client not found!");
		}
		this.clientDAO.delete(client);
		commitAndCloseTransaction();
		this.LOG.info("Cliente deletado com sucesso!");
	}
	
	public void update(Client newClient, Long clientId) {

		this.LOG.info("Preparando para atualizar produto");
		if (newClient == null || clientId == null) {
			this.LOG.error("Um dos parametros está nulo!");
			throw new RuntimeException("The parameter is null");
		}

		Client client = this.clientDAO.getByID(clientId);

		validateClientIsNull(client);
		this.LOG.info("Cliente encontrado no banco");
		getBeginTransaction();
		client.setName(newClient.getName());
		if(!validarCPF(newClient.getCpf())) {
			this.LOG.info("O formato do CPF está inválido");
			throw new RuntimeException("Invalid format");
			
		}
		client.setCpf(newClient.getCpf());
		client.setBirthDate(newClient.getBirthDate());
		this.LOG.info("Cliente atualizado com sucesso");
		commitAndCloseTransaction();

	}
	
	private void validateClientIsNull(Client client) {
		if (client == null) {
			this.LOG.error("O cliente não existe");
			throw new EntityNotFoundException("Client not Found!");
		}
	}
	
	public List<Client> listAll() {
		this.LOG.info("Preparando para listar produtos");
		List<Client> products = this.clientDAO.listAll();

		if (products == null) {
			this.LOG.info("Não foram encontrados produtos");
			return new ArrayList<Client>();
		}
		this.LOG.info("Foram encontrados " + products.size() + " produtos.");
		return products;
	}

	
	public List<Client> listByName(String name) {
		if (name == null || name.isEmpty()) {
			this.LOG.info("O parametro nome está vazio");
			throw new RuntimeException("The parameter name is null");
		}
		
		this.LOG.info("Preparando para buscar os produtos com o nome: " + name);
		List<Client> products = this.clientDAO.listByName(name.toLowerCase());
		
		if (products == null) {
			this.LOG.info("Não foram encontrados Produtos");
			return new ArrayList<Client>();
		}
		
		this.LOG.info("Foram encontrados " + products.size() + " produtos.");
		return products;
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

}
