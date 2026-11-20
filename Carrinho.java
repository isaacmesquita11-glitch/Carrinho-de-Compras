import java.util.ArrayList;

public class Carrinho {
    private ArrayList<ItemCarrinho> itens = new ArrayList<>();

    public void adicionarProduto(Produto produto, int quantidade) {
        for (ItemCarrinho item : itens) {
            if (item.getProduto().getCodigo() == produto.getCodigo()) {
                item.aumentarQuantidade(quantidade);
                System.out.println("Quantidade atualizada!");
                return;
            }
        }

        itens.add(new ItemCarrinho(produto, quantidade));
        System.out.println("Produto adicionado!");
    }

    public void removerProduto(int codigo) {
        boolean removido = itens.removeIf(
            item -> item.getProduto().getCodigo() == codigo
        );

        if (removido) {
            System.out.println("Produto removido!");
        } else {
            System.out.println("Produto nao encontrado no carrinho.");
        }
    }

    public void listarProdutos() {
        if (itens.isEmpty()) {
            System.out.println("Carrinho vazio!");
            return;
        }

        System.out.println("\n===== PRODUTOS NO CARRINHO =====");

        for (ItemCarrinho item : itens) {
            System.out.printf(
                "%s | Quantidade: %d | Total: R$ %.2f%n",
                item.getProduto().getNome(),
                item.getQuantidade(),
                item.calcularTotal()
            );
        }

        System.out.printf("Subtotal: R$ %.2f%n", calcularSubtotal());
    }

    public double calcularSubtotal() {
        double total = 0;

        for (ItemCarrinho item : itens) {
            total += item.calcularTotal();
        }

        return total;
    }

    public boolean estaVazio() {
        return itens.isEmpty();
    }
}
