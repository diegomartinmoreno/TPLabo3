package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Carrito {
    private List<Producto> productos;
    private double montoTotal=0;
    private String nota;
    private LocalDate fechaPedido;
    private static final double PORCENTAJE_DESCUENTO = 0.85;

    public Carrito() {
        productos = new ArrayList<>();
        fechaPedido = LocalDate.now();
    }

    public void mostrarMenuDeCarrito(){
        System.out.println("Que desea hacer?");
        System.out.println("(1) Agregar producto al carrito");
        System.out.println("(2) Eliminar producto del carrito");
        System.out.println("(3) Editar cantidad del producto");
        System.out.println("(4) Agregar cupon");
        System.out.println("(5) Ir a pagar");
        System.out.println("(6) Ver carrito");
        System.out.println("(7) salir");
    }

    //1
    public boolean agregarProductoAlCarrito(Producto producto, String nota) throws NullPointerException{
        if (producto==null || nota==null) throw new NullPointerException("Error! El producto o la nota no puede ser nula.//***");

        montoTotal += producto.getPrecio();
        this.nota = nota;
        return productos.add(producto);
    }
    public boolean agregarProductoAlCarrito(Producto producto) throws NullPointerException{
        if(producto==null) throw new NullPointerException("Error! El producto no puede ser nulo.//***");

        montoTotal += producto.getPrecio();
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

    //4
    public void agregarDescuento(String cupon, PedidosYa pedidosYa) throws NullPointerException, RuntimeException{
        if(pedidosYa==null || cupon==null) throw new NullPointerException("Error! Los parametros no pueden ser nulos.//***");

        Empresa empresa = pedidosYa.buscarEmpresaSegunNombre(productos.get(0).getNombreDeLaEmpresa());

        if (empresa.validarCupon(cupon)){ ///Busca el cupon y lo elimina de la empresa.
            montoTotal *= PORCENTAJE_DESCUENTO;
        } else {
            throw new RuntimeException("Error! Cupon no valido.//***");
        }
    }

    //5
    public void pagarCarrito (HistorialDeCompras historial, Tarjeta tarjeta) throws NullPointerException{
        if(historial==null || tarjeta==null) throw new NullPointerException("Error! Los parametros no pueden ser nulos.//***");

        tarjeta.RealizarPago(montoTotal);

        historial.agregarPedido(this);

        clear();
    }

    //6
    public void listarCarrito(){
        System.out.println("PRODUCTOS:");
        System.out.println(productos);

        System.out.println("Monto total: " + montoTotal);
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
        montoTotal=0;
        nota="";
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }
}
