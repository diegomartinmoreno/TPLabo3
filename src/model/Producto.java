package model;

import java.util.UUID;

public class Producto {
    private UUID id;
    private String nombreProducto;
    private String tipo;
    private String descripcion;
    private double precio;
    private int cantidadPedido=0;


    public Producto(String nombreProducto, String tipo, String descripcion, double precio) {
        id = UUID.randomUUID();
        this.nombreProducto = nombreProducto;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.precio = precio;

    }

    public double getPrecio() {
        return precio;
    }

    public void setCantidadPedido(int cantidadPedido) {
        this.cantidadPedido = cantidadPedido;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }


    @Override
    public String toString() {
        return "Producto{" +
                "Nombre='" + nombreProducto + '\'' +
                ", tipo='" + tipo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", cantidadPedido=" + cantidadPedido +
                "}\n";
    }
}
