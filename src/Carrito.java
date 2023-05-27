import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Carrito {
    private List<Producto> productos;
    private double montoTotal=0;
    private double porcentajeDeDescuento; //SE DETERMIAN A PARTIR DE LAS COMPRAS REALIZADAS
    private String nota;
    private LocalDate fechaPedido;

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
        System.out.println("(6) salir");
    }

    public void pagarCarrito (HistorialDeCompras historial){ ///SE DEBE PASAR TARJETA POR PARAMETRO
        //
        //
        //
        //
        //
        //
        //
        //
        historial.agregarPedido(this);
        clear();


    }
    public void agregarProductoAlCarrito(Producto producto, String nota){

        productos.add(producto);
        montoTotal += producto.getPrecio();
        this.nota = nota;
    }

    public void agregarDescuento(String cupon, PedidosYa pedidosYa){
        int pos = pedidosYa.buscarPosEmpresaSegunNombre(productos.get(0).getNombreDeLaEmpresa());
        Empresa empresa = pedidosYa.getListaDeEmpresas().get(pos);

        if (empresa.validarCupon(cupon)){
            porcentajeDeDescuento = 0.15;
            pedidosYa.getListaDeEmpresas().get(pos).eliminarCupon(cupon);
            montoTotal *= porcentajeDeDescuento;
            ////////////////////ELIMINA EL CUPON UTILIZADO DE LA EMPRESA CORRESPONDIENTE
        } else {
            System.out.println("El cupon no es valido");
        }
    }

    public void editarCantidadDeProducto(int cantidad, String nombreProducto){
        int pos = buscarProductoPorNombre(nombreProducto);
        productos.get(pos).setCantidadPedido(cantidad);
    }

    public void eliminarProductoDelCarrito(String nombre){
        int posicion = buscarProductoPorNombre(nombre);
        productos.remove(posicion);
    }

    public int buscarProductoPorNombre(String nombre){
        int aux=-1;
        for (Producto prod : productos){
            if (nombre.equals(prod.getNombreProducto())){
                aux=productos.indexOf(prod);
            }
        }

        if (aux==-1){
            System.out.println("ERROR/////////PRODUCTO NO ENCONTRADO/////////");
        }

        return aux;
    }

    public void clear(){
        productos.clear();
        montoTotal=0;
        porcentajeDeDescuento=1;
        nota="";
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }
}
