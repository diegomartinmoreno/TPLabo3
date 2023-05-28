import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class Empresa {

   private LinkedList <String> listaDeCupones;
    private String Nombre;

    private  double CostoDeEnvio;

    private LinkedHashMap <String, Producto> ProductosEmpresa;

    private HashMap<Integer, String> Zonas;

   public boolean validarCupon(String cupon){
        boolean bool = true;
        return bool;
    }

    public String eliminarCupon(String cupon){
        return cupon;
    }

    public Empresa(String nombre, double costoDeEnvio) {
        Nombre = nombre;
        CostoDeEnvio = costoDeEnvio;
        ProductosEmpresa = new LinkedHashMap<>();
        Zonas = new HashMap<>();
        listaDeCupones = new LinkedList<>();
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    public double getCostoDeEnvio() {
        return CostoDeEnvio;
    }

    public void setCostoDeEnvio(double costoDeEnvio) {
        CostoDeEnvio = costoDeEnvio;
    }

    public LinkedHashMap<String, Producto> getProductosEmpresa() {
        return ProductosEmpresa;
    }

    public void setProductosEmpresa(LinkedHashMap<String, Producto> productosEmpresa) {
        ProductosEmpresa = productosEmpresa;
    }

    public HashMap<Integer, String> getZonas() {
        return Zonas;
    }

    public void setZonas(HashMap<Integer, String> zonas) {
        this.Zonas = Zonas;
    }
}
