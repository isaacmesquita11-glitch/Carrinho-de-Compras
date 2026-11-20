import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    static ArrayList<Produto> produtos = new ArrayList<>();

    static Carrinho carrinho = new Carrinho();

    public static void main(String[] args) {

        cadastrarProdutos();

        int opcao;

        do {
            System.out.println("\n================================");
            System.out.println("     CARRINHO DE COMPRAS");
            System.out.println("================================");
            System.out.println("1 - Listar produtos");
            System.out.println("2 - Adicionar produto");
            System.out.println("3 - Remover produto");
            System.out.println("4 - Consultar carrinho");
            System.out.println("5 - Finalizar compra");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opcao: ");

            opcao = lerInteiro();

            switch (opcao) {
                case 1:
                    listarCatalogo();
                    break;

                case 2:
                    adicionarProduto();
                    break;

                case 3:
                    removerProduto();
                    break;

                case 4:
                    carrinho.listarProdutos();
                    break;

                case 5:
                    finalizarCompra();
                    break;

                case 0:
                    System.out.println("Programa encerrado!");
                    break;

                default:
                    System.out.println("Opcao invalida!");
            }

        } while (opcao != 0);

        scanner.close();
    }

    // Cadastra produtos de exemplo
    public static void cadastrarProdutos() {
        produtos.add(new Produto(1, "Camisa", 50.00));
        produtos.add(new Produto(2, "Calca", 100.00));
        produtos.add(new Produto(3, "Tenis", 200.00));
    }

    // Mostra o catalogo
    public static void listarCatalogo() {
        System.out.println("\n===== CATALOGO DE PRODUTOS =====");

        for (Produto produto : produtos) {
            System.out.printf(
                "Codigo: %d | %s | R$ %.2f%n",
                produto.getCodigo(),
                produto.getNome(),
                produto.getPreco()
            );
        }
    }

    // Adiciona produto ao carrinho
    public static void adicionarProduto() {
        listarCatalogo();

        System.out.print("\nDigite o codigo do produto: ");
        int codigo = lerInteiro();

        Produto produtoEncontrado = null;

        for (Produto produto : produtos) {
            if (produto.getCodigo() == codigo) {
                produtoEncontrado = produto;
                break;
            }
        }

        if (produtoEncontrado == null) {
            System.out.println("Produto nao encontrado!");
            return;
        }

        System.out.print("Digite a quantidade: ");
        int quantidade = lerInteiro();

        if (quantidade <= 0) {
            System.out.println("Quantidade invalida!");
            return;
        }

        carrinho.adicionarProduto(produtoEncontrado, quantidade);
    }

    // Remove produto do carrinho
    public static void removerProduto() {
        if (carrinho.estaVazio()) {
            System.out.println("Carrinho vazio!");
            return;
        }

        carrinho.listarProdutos();

        System.out.print("\nDigite o codigo do produto: ");
        int codigo = lerInteiro();

        carrinho.removerProduto(codigo);
    }

    // Finaliza a compra
    public static void finalizarCompra() {
        if (carrinho.estaVazio()) {
            System.out.println("Adicione produtos antes de finalizar!");
            return;
        }

        double subtotal = carrinho.calcularSubtotal();

        System.out.println("\n===== ESCOLHA O FRETE =====");
        System.out.println("1 - Padrao: R$ 15,00");
        System.out.println("2 - Expresso: R$ 25,00");
        System.out.println("3 - Gratis: R$ 0,00");
        System.out.print("Escolha: ");

        int opcaoFrete = lerInteiro();

        EstrategiaFrete estrategia;

        switch (opcaoFrete) {
            case 1:
                estrategia = new FretePadrao();
                break;

            case 2:
                estrategia = new FreteExpresso();
                break;

            case 3:
                estrategia = new FreteGratis();
                break;

            default:
                System.out.println("Opcao invalida! Compra nao finalizada.");
                return;
        }

        double valorFrete = estrategia.calcularFrete(subtotal);

        System.out.print("\nPossui cupom DESCONTO10? (S/N): ");
        String resposta = scanner.nextLine();

        CalculadoraPreco calculadora = new PrecoBase(subtotal);

        if (resposta.equalsIgnoreCase("S")) {
            calculadora = new DescontoDecorator(calculadora, 10);
            System.out.println("Desconto de 10% aplicado!");
        }

        double valorProdutos = calculadora.calcular();
        double total = valorProdutos + valorFrete;

        System.out.println("\n========== RESUMO DA COMPRA ==========");
        carrinho.listarProdutos();

        System.out.printf("Subtotal: R$ %.2f%n", subtotal);
        System.out.printf("Valor com desconto: R$ %.2f%n", valorProdutos);
        System.out.printf("Frete: R$ %.2f%n", valorFrete);
        System.out.printf("TOTAL A PAGAR: R$ %.2f%n", total);
        System.out.println("======================================");
        System.out.println("Compra simulada com sucesso!");
    }

    // Leitura de numeros inteiros
    public static int lerInteiro() {
        while (!scanner.hasNextInt()) {
            System.out.print("Digite um numero valido: ");
            scanner.nextLine();
        }

        int numero = scanner.nextInt();
        scanner.nextLine();

        return numero;
    }
}
