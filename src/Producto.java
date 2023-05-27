public class Producto {
    private static int contadorId;
    private int id;
    private String nombreProducto;
    private String tipo;
    private String descripcion;
    private double precio;
    private int cantidadPedido;
    private String nombreDeLaEmpresa;

    public double getPrecio() {
        return precio;
    }

    public void setCantidadPedido(int cantidadPedido) {
        this.cantidadPedido = cantidadPedido;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public String getNombreDeLaEmpresa() {
        return nombreDeLaEmpresa;
    }

    public void setNombreDeLaEmpresa(String nombreDeLaEmpresa) {
        this.nombreDeLaEmpresa = nombreDeLaEmpresa;
    }
}
