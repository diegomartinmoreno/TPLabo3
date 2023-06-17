package model;

import javax.crypto.spec.DESedeKeySpec;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Carrito {
    private List<Producto> productos;
    private Empresa vendedor;
	private String nota;
    private LocalDate fechaPedido;
    private static final double PORCENTAJE_DESCUENTO = 0.85;
    private boolean tieneCupon=false;

    public Carrito() {
        productos = new ArrayList<>();
        fechaPedido = LocalDate.now();
    }

    


    //1
    public boolean agregarProductoAlCarrito(Empresa vendedor, Producto producto, String nota) throws NullPointerException{
        if (producto==null || nota==null) throw new NullPointerException("Error! El producto o la nota no puede ser nula.//***");

        this.vendedor = vendedor;
        this.nota = nota;

        return productos.add(producto);
    }
    public boolean agregarProductoAlCarrito(Empresa vendedor, Producto producto) throws NullPointerException{
        if(producto==null) throw new NullPointerException("Error! El producto no puede ser nulo.//***");

        this.vendedor = vendedor;

        return productos.add(producto);
    }


    //2
    public void eliminarProductoDelCarrito(String nombre) throws RuntimeException, NullPointerException{
        if (nombre==null) throw new NullPointerException("Error! La cadena no puede ser nula.//***");

        int posicion = buscarProductoPorNombre(nombre);

        if(posicion > -1){
            productos.remove(posicion);
        } else {
            throw new RuntimeException("El producto no se encuentra en el carrito.//***");
        }

    }

    //3
    public void editarCantidadDeProducto(int cantidad, String nombreProducto) throws RuntimeException, NullPointerException{
        if(nombreProducto==null) throw new NullPointerException("Error! La cadena no puede ser nula.//***");

        int pos = buscarProductoPorNombre(nombreProducto);
        if(pos > -1){
            productos.get(pos).setCantidadPedido(cantidad);
        } else {
            throw new RuntimeException("El producto no se encuentra en el carrito");
        }

    }



    //5
    public void pagarCarrito (HistorialDeCompras historial, Tarjeta tarjeta) throws NullPointerException{
        if(historial==null || tarjeta==null) throw new NullPointerException("Error! Los parametros no pueden ser nulos.//***");



        tarjeta.RealizarPago(calcularMontoTotalDeLaCompra());

        historial.agregarPedido(this);

        clear();
    }

    public double calcularMontoTotalDeLaCompra(){
        double montoTotal =0;
        for (Producto producto : productos){
            montoTotal += producto.getPrecio();
        }

        if (tieneCupon){
            montoTotal *= PORCENTAJE_DESCUENTO;
        }

        return montoTotal;
    }

    //6
    public void listarCarrito(){
        System.out.println("PRODUCTOS:");
        System.out.println(productos);
        System.out.println("Monto total: " + calcularMontoTotalDeLaCompra());
        System.out.println("Nota: " + nota);
        System.out.println("Fecha del Pedido: " + fechaPedido + "\n");
    }

    public int buscarProductoPorNombre(String nombre) throws NullPointerException, RuntimeException{
        if(nombre==null) throw new NullPointerException("Error! La cadena no puede ser nula.//***");

        int aux=-1;
        for (Producto prod : productos){
            if (nombre.equals(prod.getNombreProducto())){
                aux=productos.indexOf(prod);
            }
        }

        if (aux==-1){
            throw new RuntimeException("Error! El producto no se encuentra.//***");
        }

        return aux;
    }

    public void clear(){
        productos.clear();
        nota="";
        vendedor=null;
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }

    public Empresa getVendedor() {
        return vendedor;
    }

    public void setTieneCupon(boolean tieneCupon) {
        this.tieneCupon = tieneCupon;
    }
}
