package market.application.menu;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;

import market.model.persistence.Client;
import services.ClientService;

public class MenuClient {
	public static void Menu(EntityManager entityManager) {
		ClientService clientService = new ClientService(entityManager);
		Scanner sc = new Scanner(System.in);
		boolean a = true;
		int op;
		while (a) {
			System.out.println("""
					Selecione uma opcao:\s
					1 - Adicionar um cliente
					2 - Atualizar os dados de um cliente
					3 - Deletar cliente
					4 - Listar todos os clientes do banco
					5 - Listar clientes por nome
					6 - Sair""");
			op = sc.nextInt();
			switch (op) {
			case 1: {
				System.out.println("Digite o nome do cliente");
				sc.nextLine();
				String nomeCliente = sc.nextLine();
				System.out.println("Digite o cpf do cliente (no formato XXX.XXX.XXX-XX)");
				String cpfCliente = sc.nextLine();
				System.out.println("Digite a data de nascimento do cliente (no formato dd/MM/AAAA)");
				String dataNascCliente = sc.nextLine();
				Client client = new Client(nomeCliente, cpfCliente, dataNascCliente);
				clientService.create(client);
				break;
			}
			case 2: {
				System.out.println("Digite o id do cliente");
				Long id = sc.nextLong();
				System.out.println("Digite o nome do cliente");
				sc.nextLine();
				String nomeCliente = sc.nextLine();
				System.out.println("Digite o cpf do cliente (no formato XXX.XXX.XXX-XX)");
				String cpfCliente = sc.nextLine();
				System.out.println("Digite a data de nascimento do cliente (no formato dd/MM/AAAA)");
				String dataNascCliente = sc.nextLine();
				Client client = new Client(nomeCliente, cpfCliente, dataNascCliente);
				clientService.update(client, id);
				break;
			}
			case 3: {
				System.out.println("Digite o id do cliente");
				Long id = sc.nextLong();
				clientService.delete(id);
				break;
			}
			case 4: {
				List<Client> clients = clientService.listAll();
				clients.forEach(p -> System.out.println(p.toString()));
				break;
			}
			case 5: {
				System.out.println("Digite o nome do cliente");
				sc.nextLine();
				String nomeCliente = sc.nextLine();
				List<Client> clients = clientService.listByName(nomeCliente);
				clients.forEach(p -> System.out.println(p.toString()));
				break;
			}
			case 6: {
				a = false;
				break;

			}
			}
		}

	}
}
