package market.application.menu;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;

import market.model.persistence.Category;
import market.model.persistence.Client;
import market.model.persistence.Product;
import services.CategoryService;
import services.ProductService;

public class MenuProduct {
	public static void Menu(EntityManager entityManager) {
		ProductService productService = new ProductService(entityManager);
		CategoryService categoryService = new CategoryService(entityManager);
		Scanner sc = new Scanner(System.in);
		boolean a = true;
		int op;
		while (a) {
			System.out.println("""
					Selecione uma opcao:\s
					1 - Adicionar um produto
					2 - Atualizar os dados de um produto
					3 - Deletar produto
					4 - Listar todos os produtos do banco
					5 - Listar produto por nome
					6 - Sair""");
			op = sc.nextInt();
			switch (op) {
			case 1: {
				System.out.println("Digite o nome do produto");
				sc.nextLine();
				String nomeProduto = sc.nextLine();
				System.out.println("Digite o descricao do produto");
				String descricaoProduto = sc.nextLine();
				System.out.println("Digite o preco do produto");
				BigDecimal precoProduto = sc.nextBigDecimal();
				System.out.println("Digite a categoria do produto");
				sc.nextLine();
				String categoriaProduto = sc.nextLine();
				Category categoria = new Category(categoriaProduto);
				Product product = new Product(nomeProduto, descricaoProduto, precoProduto, categoria);
				productService.create(product);
				break;
			}
			case 2: {
				System.out.println("Digite o id do produto");
				Long id = sc.nextLong();
				System.out.println("Digite o nome do produto");
				sc.nextLine();
				String nomeProduto = sc.nextLine();
				System.out.println("Digite o descricao do produto");
				String descricaoProduto = sc.nextLine();
				System.out.println("Digite o preco do produto");
				BigDecimal precoProduto = sc.nextBigDecimal();
				System.out.println("Digite a categoria do produto");
				sc.nextLine();
				String categoriaProduto = sc.nextLine();
				Category categoria = new Category(categoriaProduto);
				Product product = new Product(nomeProduto, descricaoProduto, precoProduto, categoria);
				productService.update(product, id);
				break;
			}
			case 3: {
				System.out.println("Digite o id do produto");
				Long id = sc.nextLong();
				productService.delete(id);
				break;
			}case 4:{
				List<Product>produtos = productService.listAll();
				produtos.forEach(p -> System.out.println(p.toString()));
				break;
			}case 5:{
				System.out.println("Digite o nome do produto");
				sc.nextLine();
				String nomeProduto = sc.nextLine();
				List<Product>produtos = productService.listByName(nomeProduto);
				produtos.forEach(p -> System.out.println(p.toString()));
				break;
			}case 6:{
				a = false;
				break;
			}

			}
		}
	}
}
