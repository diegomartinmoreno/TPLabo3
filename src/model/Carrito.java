package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.crypto.spec.DESedeKeySpec;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Carrito {
    @JsonProperty("Productos")
    private ColeccionGenerica<Producto> productos;
    @JsonIgnore
    private Empresa vendedor;
	private String nota;
    private String fechaPedido;
    private static final double PORCENTAJE_DESCUENTO = 0.85;
    private boolean tieneCupon=false;
    private Repartidor repartidor;

    ///////////////////////////////////
    private double montoTotal=0;
    private Zonas destino=null;
    /////////////////////////////////// campos esenciales para el historial

    public Carrito() {
        productos = new ColeccionGenerica<>();
        fechaPedido = LocalDate.now().toString();
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
    public void pagarCarrito (HistorialDeCompras historial, Tarjeta tarjeta, Scanner scanner) throws NullPointerException{
        if(historial==null || tarjeta==null) throw new NullPointerException("Error! Los parametros no pueden ser nulos.//***");

        repartidor = generarRepartidorAleatorio();
        repartidor.llevarPedido();

        montoTotal += vendedor.getCostoDeEnvio();


        System.out.println("////////////////////////////////////////////////////////");
        System.out.printf("Encargado de llevar el pedido: ");
        repartidor.mostrarRepartidor();
        System.out.println("Costo de envio: " + vendedor.getCostoDeEnvio());



        boolean flag = false;

        do {
            flag = tarjeta.RealizarPago(montoTotal);
            if (flag && tarjeta.isActiva()) {
                System.out.println("La compra ha sido realizada!!!");
                System.out.print("Aguarde, en unos instantes le va a llegar un pedido por manos de ");
                repartidor.mostrarRepartidor();

                System.out.println("Su pedido es el siguiente:");
                listarCarrito();

                Pedido pedido = new Pedido(fechaPedido, vendedor.getNombre());
                pasarProductosAlPedido(pedido);

                historial.agregarPedido(pedido);

                System.out.println("//////////////////////////////////////////////////////////////////");
            } else {
                if (!tarjeta.isActiva()){
                    System.out.println("La tarjeta se encuentra inhabilitada..// Debe cargar una nueva...");
                    tarjeta.cargarTarjeta(scanner);
                }

                System.out.println("Saldo insuficiente... \n Saldo total: $" + tarjeta.getSaldo());
                System.out.println("Ingrese saldo: ");
                tarjeta.setSaldo(tarjeta.getSaldo() + scanner.nextDouble());
            }
        } while (!flag);
    }

    public void pasarProductosAlPedido(Pedido pedido){
        for(Producto producto : productos.getProductos()){
            pedido.agregarProducto(producto);
        }
    }

    public Repartidor generarRepartidorAleatorio(){
        return new Repartidor("Leo", "Messi", "2235212344", 17, "liomessi@gmail.com", "45621234");
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

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public double calcularMontoTotalDeLaCompra(){
        double montoTotal =0;
        for (Producto producto : productos.getProductos()){
            montoTotal = montoTotal + (producto.getPrecio() * producto.getCantidadPedido());
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
        System.out.println("Fecha del model.Pedido: " + fechaPedido + "\n");
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

    public String getFechaPedido() {
        return fechaPedido;
    }

    public Empresa getVendedor() {
        return vendedor;
    }

    public void setTieneCupon(boolean tieneCupon) {
        this.tieneCupon = tieneCupon;
    }

    public Zonas getDestino() {
        return destino;
    }

    public void setDestino(Zonas destino) {
        this.destino = destino;
    }

    public void setProductos(ColeccionGenerica<Producto> productos) {
        this.productos = productos;
    }

    public void setVendedor(Empresa vendedor) {
        this.vendedor = vendedor;
    }

    public String getNota() {
        return nota;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public boolean isTieneCupon() {
        return tieneCupon;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public Repartidor getRepartidor() {
        return repartidor;
    }

    public void setRepartidor(Repartidor repartidor) {
        this.repartidor = repartidor;
    }
}
