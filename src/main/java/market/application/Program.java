package market.application;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Scanner;
import connection.JpaConnectionFactory;
import market.application.menu.MenuClient;
import market.application.menu.MenuProduct;
import market.model.persistence.Category;
import market.model.persistence.Client;
import market.model.persistence.Product;
import model.dao.ClientDAO;
import services.CategoryService;
import services.ClientService;
import services.ProductService;

public class Program {

	private static final Logger LOG = LogManager.getLogger(Program.class);

	public static void main(String[] args) {
		EntityManager entityManager = new JpaConnectionFactory().geEntityManager();

		Scanner sc = new Scanner(System.in);
		boolean a = true;
		int op;
		while (a) {
			System.out.println("""
					Selecione uma opcao:\s
					1 - Menu Produtos
					2 - Menu Clientes
					3 - Sair da aplicação""");
			op = sc.nextInt();
			switch (op) {
			case 1: {
				MenuProduct.Menu(entityManager);
				break;
			}
			case 2: {
				MenuClient.Menu(entityManager);
				break;
			}
			case 3: {
				System.exit(0);

			}
			}
		}
	}
}
