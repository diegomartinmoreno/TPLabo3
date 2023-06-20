package model;

import javax.crypto.spec.DESedeKeySpec;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Carrito {
    private ColeccionGenerica<Producto> productos;
    private Empresa vendedor;
	private String nota;
    private LocalDate fechaPedido;
    private static final double PORCENTAJE_DESCUENTO = 0.85;
    private boolean tieneCupon=false;

    public Carrito() {
        productos = new ColeccionGenerica<>();
        fechaPedido = LocalDate.now();
    }

    


    //1

    public boolean agregarProductoAlCarrito(Empresa vendedor, Producto producto) throws NullPointerException{
        if(producto==null) throw new NullPointerException("Error! El producto no puede ser nulo.//***");

        this.vendedor = vendedor;

        return productos.add(producto);
    }


    //2


    public void eliminarProductoDelCarrito(int pos) throws RuntimeException {
        if (pos > -1 && pos <= productos.getProductos().size()) {
            productos.remove(pos);
        } else {
            throw new RuntimeException("Numero no valido.//***");
        }
    }


    //3
    public void editarCantidadDeProducto(int cantidad, int pos) throws RuntimeException, NullPointerException{

        if(pos > -1 && pos <= productos.getProductos().size()){
            productos.get(pos).setCantidadPedido(cantidad);
        } else {
            throw new RuntimeException("Numero no valido.///***");
        }

    }



    //5
    public void pagarCarrito (HistorialDeCompras historial, Tarjeta tarjeta) throws NullPointerException{
        if(historial==null || tarjeta==null) throw new NullPointerException("Error! Los parametros no pueden ser nulos.//***");


        System.out.println("Monto total: " + calcularMontoTotalDeLaCompra());


        historial.agregarPedido(this);

        if(tarjeta.RealizarPago(calcularMontoTotalDeLaCompra())) clear();
    }

    public void mostrarProductos(){
        int i=0;
        System.out.println("Productos del carrito: ");

        for (Producto producto : productos.getProductos()){
            System.out.println(i + ".///  " + producto);
            i++;
        }
    }

    public List<Producto> getProductos() {
        return productos.getProductos();
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public double calcularMontoTotalDeLaCompra(){
        double montoTotal =0;
        for (Producto producto : productos.getProductos()){
            montoTotal = montoTotal + producto.getPrecio() * producto.getCantidadPedido();
        }

        if (tieneCupon){
            System.out.println("Usted posee un descuento del 15%!!");
            montoTotal *= PORCENTAJE_DESCUENTO;
        }

        return montoTotal;
    }

    //6
    public void listarCarrito(){
        System.out.println("PRODUCTOS:");
        System.out.println(productos.getProductos());
        System.out.println("Monto total: " + calcularMontoTotalDeLaCompra());
        System.out.println("Nota: " + nota);
        System.out.println("Fecha del Pedido: " + fechaPedido + "\n");
    }

    public int buscarProductoPorNombre(String nombre) throws NullPointerException, RuntimeException{
        if(nombre==null) throw new NullPointerException("Error! La cadena no puede ser nula.//***");

        int aux=-1;
        for (Producto prod : productos.getProductos()){
            if (nombre.equals(prod.getNombreProducto())){
                aux=productos.getProductos().indexOf(prod);
            }
        }

        if (aux==-1){
            throw new RuntimeException("Error! El producto no se encuentra.//***");
        }

        return aux;
    }

    public void clear(){
        productos.getProductos().clear();
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
