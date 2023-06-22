package model;

import java.util.Objects;

public class Producto {
    private static int ContadorID=0;
    private int Id;
    private String nombreProducto;
    private String tipo;
    private String descripcion;
    private double precio;
    private int cantidadPedido=0;

    public Producto() {
    }

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


    public void mostrarProducto(){
        System.out.println(Id + ". - " + nombreProducto + " $" + precio);
    }
    public void productoElegido(){
        System.out.println(nombreProducto + " - $" + precio + " - '" + tipo + "'");
        System.out.println("'" + descripcion + "'");
    }

    public void setId(int id) {
        Id = id;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
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
