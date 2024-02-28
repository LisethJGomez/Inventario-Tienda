package tienda.Controlador;

public class Valor {
    private String[] producto = {"Manzana", "Pera", "Banano"};
    private double[] precios = {1000, 1500, 1200};

    public double calcularTotal(int can, int i) {
        double valor = precios[i];
        return can * valor;
    }

    public String getProducto(int i) {
        return producto[i];
    }

    public void cambiarPrecio(int indice, double nuevoPrecio) {
        precios[indice] = nuevoPrecio;
    }  
}