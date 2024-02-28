package tienda.Vista;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import tienda.Controlador.Valor;

public class Tienda2 {

    public static void main(String[] args) {
               // Variables
        double[] totalesPorDia = new double[7]; // Array para almacenar los totales de cada día
        int[] cantidadPorDia = new int[7]; // Array para almacenar la cantidad de productos vendidos por día

        // Objetos y arrays
        String s = "REPORTE DE LA SEMANA\n======================\n";
        Valor v = new Valor();
        ArrayList<ArrayList<String>> ventasPorDia = new ArrayList<>(); // ArrayList para almacenar las ventas de cada día

        // Mensaje al inicio
        JOptionPane.showMessageDialog(null, "Bienvenido a la tienda. Este programa registrará tus ventas por 7 días.");


               // Proceso de ventas
        try {
            for (int i = 0; i < 7; i++) {
                ArrayList<String> ventasDelDia = new ArrayList<>(); // ArrayList para almacenar las ventas del día actual
                int totalVentasDia = 0;

                String des = "";
                while (!des.equalsIgnoreCase("n")) {
                    int tp = Integer.parseInt(JOptionPane.showInputDialog("Elija un producto: \n1. Manzana\n2. Pera\n3. Banano\n4. Salir"));
                    if (tp == 4) break; // Salir del bucle si se elige la opción 4 (Salir)
                    tp--; // Ajustar índice para el array de productos
                    ventasDelDia.add(v.getProducto(tp)); // Agregar producto seleccionado al registro
                    int can = Integer.parseInt(JOptionPane.showInputDialog("Cantidad:"));
                    totalVentasDia += can; // Actualizar total acumulado de ventas del día
                    double tot = v.calcularTotal(can, tp); // Calcular total
                    totalesPorDia[i] += tot; // Actualizar total acumulado del día
                    des = JOptionPane.showInputDialog("Desea agregar otro producto? (s/n)");
                }
                cantidadPorDia[i] = totalVentasDia; // Guardar la cantidad de productos vendidos en el día

                // Agregar las ventas del día al ArrayList principal
                ventasPorDia.add(ventasDelDia);

                 }

            // Reporte de ventas inicial
            mostrarReporte(s, totalesPorDia, cantidadPorDia);

            // Menú para seleccionar un día o finalizar
            while (true) {
                int opcionDia = Integer.parseInt(JOptionPane.showInputDialog("Seleccione el día que desea modificar (1-7), o ingrese 0 para finalizar:"));
                if (opcionDia < 1 || opcionDia > 7) {
                    // Salir del bucle si se ingresa 0 o un valor fuera de rango
                    break;
                }
                // Modificar ventas del día seleccionado
                modificarVentas(opcionDia, ventasPorDia, totalesPorDia, cantidadPorDia);
                // Mostrar reporte actualizado
                mostrarReporte(s, totalesPorDia, cantidadPorDia);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la entrada de datos.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }

         // Método para mostrar el reporte de ventas
    private static void mostrarReporte(String s, double[] totalesPorDia, int[] cantidadPorDia) {
        double totalGeneral = 0;
        int totalCantidad = 0;
        for (int i = 0; i < totalesPorDia.length; i++) {
            s += "Ventas del día " + (i+1) + ": $" + totalesPorDia[i] + " | Cantidad vendida: " + cantidadPorDia[i] + "\n";
            totalGeneral += totalesPorDia[i];
            totalCantidad += cantidadPorDia[i];
        }
        s += "Total de los 7 días: $" + totalGeneral + " | Cantidad total vendida: " + totalCantidad + "\n";
        JOptionPane.showMessageDialog(null, s);
    }

    // Método para modificar las ventas de un día específico
  private static void modificarVentas(int opcionDia, ArrayList<ArrayList<String>> ventasPorDia, double[] totalesPorDia, int[] cantidadPorDia) {
    ArrayList<String> ventasDelDia = ventasPorDia.get(opcionDia - 1);
    double totalDia = totalesPorDia[opcionDia - 1];
    int cantidadDia = cantidadPorDia[opcionDia - 1];
    Valor v = new Valor(); // Crear una instancia de la clase Valor
    String modificacion = JOptionPane.showInputDialog("Seleccione una opción:\n1. Cambiar cantidad de un producto\n2. Eliminar un producto\n3. Agregar un producto\nIngrese cualquier otro valor para salir.");
    
          switch (modificacion) {
        case "1":
            int nuevaCantidad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la nueva cantidad de productos vendidos en el día:"));
            totalesPorDia[opcionDia - 1] = totalDia / cantidadDia * nuevaCantidad; // Actualizar el total del día
            cantidadPorDia[opcionDia - 1] = nuevaCantidad; // Actualizar la cantidad de productos vendidos en el día
            break;
        case "2":
            String productoAEliminar = JOptionPane.showInputDialog("Ingrese el nombre del producto que desea eliminar:");
            // Eliminar el producto de la lista de ventas del día seleccionado
            ventasDelDia.removeIf(producto -> producto.equalsIgnoreCase(productoAEliminar));
            // Recalcular el total del día y la cantidad de productos vendidos en el día
            double totalActualizado = 0;
            for (String producto : ventasDelDia) {
                int indiceProducto = getIndexProducto(producto);
                totalActualizado += v.calcularTotal(1, indiceProducto);
            }
            totalesPorDia[opcionDia - 1] = totalActualizado;
            cantidadPorDia[opcionDia - 1] = ventasDelDia.size();
        break;
        case "3":
            int tp = Integer.parseInt(JOptionPane.showInputDialog("Elija un producto: \n1. Manzana\n2. Pera\n3. Banano"));
            tp--; // Ajustar índice para el array de productos
            ventasDelDia.add(v.getProducto(tp)); // Agregar producto seleccionado al registro
            int can = Integer.parseInt(JOptionPane.showInputDialog("Cantidad:"));
            totalDia += v.calcularTotal(can, tp); // Actualizar total del día
            cantidadDia += can; // Actualizar cantidad del día
            totalesPorDia[opcionDia - 1] = totalDia;
            cantidadPorDia[opcionDia - 1] = cantidadDia;
            break;
        default:
            break;
    }
}
    // Método para obtener el índice de un producto en el array de productos
    private static int getIndexProducto(String producto) {
        String[] productos = {"Manzana", "Pera", "Banano"};
        for (int i = 0; i < productos.length; i++) {
            if (productos[i].equalsIgnoreCase(producto)) {
                return i;
            }
        }
        return 0;
    }
}