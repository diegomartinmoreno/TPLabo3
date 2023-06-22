package model;

import model.Producto;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private List<Producto> productos;
    private String fechaPedido;
    private String nombreEmpresaVendedor;

    public Pedido() {
        productos = new ArrayList<>();
    }

    public Pedido(String fechaPedido, String nombreEmpresaVendedor) {
        productos = new ArrayList<>();
        this.fechaPedido = fechaPedido;
        this.nombreEmpresaVendedor = nombreEmpresaVendedor;
    }

    public void mostrarPedido(){
        System.out.println("Productos= " + productos);
        System.out.println("Fecha= " + fechaPedido);
        System.out.println("Empresa= " + nombreEmpresaVendedor);
    }

    public boolean agregarProducto(Producto producto){
        return productos.add(producto);
    }









    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getNombreEmpresaVendedor() {
        return nombreEmpresaVendedor;
    }

    public void setNombreEmpresaVendedor(String nombreEmpresaVendedor) {
        this.nombreEmpresaVendedor = nombreEmpresaVendedor;
    }
}
