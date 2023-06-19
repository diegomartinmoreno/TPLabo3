package model;

import java.util.Objects;
import java.util.UUID;

public class Producto {
    private static int ContadorID=0;
    private int Id;
    private String nombreProducto;
    private String tipo;
    private String descripcion;
    private double precio;
    private int cantidadPedido=0;


    public Producto(String nombreProducto, String tipo, String descripcion, double precio) {
        ContadorID++;
        Id=ContadorID;
        this.nombreProducto = nombreProducto;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.precio = precio;

    }

    public double getPrecio() {
        return precio;
    }

    public int getId() {
        return Id;
    }

    public void setCantidadPedido(int cantidadPedido) {
        this.cantidadPedido = cantidadPedido;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public int getCantidadPedido() {
        return cantidadPedido;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "Id: " + Id +
                ", Nombre='" + nombreProducto + '\'' +
                ", tipo='" + tipo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", cantidadPedido=" + cantidadPedido +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producto producto)) return false;
        return Id == producto.Id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id);
    }
}
