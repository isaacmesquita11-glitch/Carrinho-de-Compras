interface EstrategiaFrete {
   double calcularFrete(double subtotal);
}

class FretePadrao implements EstrategiaFrete {
    public double calcularFrete(double subtotal) {
        return 15.00;
    }
}

class FreteExpresso implements EstrategiaFrete {
    public double calcularFrete(double subtotal) {
        return 25.00;
    }
}

class FreteGratis implements EstrategiaFrete {
    public double calcularFrete(double subtotal) {
        return 0.00;
    } 
}