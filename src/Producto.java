public class Producto {
    private static int contadorId=0;
    private int id;
    private String nombreProducto;
    private String tipo;
    private String descripcion;
    private double precio;
    private int cantidadPedido=0;
    private String nombreDeLaEmpresa;

    public Producto(String nombreProducto, String tipo, String descripcion, double precio, String nombreDeLaEmpresa) {
        contadorId++;
        this.id = contadorId;
        this.nombreProducto = nombreProducto;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.nombreDeLaEmpresa = nombreDeLaEmpresa;
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

    public String getNombreDeLaEmpresa() {
        return nombreDeLaEmpresa;
    }

    public void setNombreDeLaEmpresa(String nombreDeLaEmpresa) {
        this.nombreDeLaEmpresa = nombreDeLaEmpresa;
    }
}
