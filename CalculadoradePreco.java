interface CalculadoraPreco {
    double calcular();
}

class PrecoBase implements CalculadoraPreco {
    private double valor;

    public PrecoBase(double valor) {
        this.valor = valor;
    }

    public double calcular() {
        return valor;
    }
}

class DescontoDecorator implements CalculadoraPreco {
    private CalculadoraPreco preco;
    private double percentual;

    public DescontoDecorator(CalculadoraPreco preco, double percentual) {
        this.preco = preco;
        this.percentual = percentual;
    }

    public double calcular() {
        double valorOriginal = preco.calcular();
        return valorOriginal - (valorOriginal * percentual / 100);
    }
}