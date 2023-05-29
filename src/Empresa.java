import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class Empresa {

   private List <String> listaDeCupones;
    private String Nombre;

    private  double CostoDeEnvio;

    private LinkedHashMap <String, Producto> ProductosEmpresa;

    private HashMap<Integer, String> Zonas;

   public boolean validarCupon(String cupon){///Hay que verificar
       boolean bool = false;
        for (int i=0;i<listaDeCupones.size() && bool == false;i++){
            if (listaDeCupones.get(i).equals(cupon)){
                bool = true;
                listaDeCupones.remove(listaDeCupones.get(i));
            }
        }
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
        generarCupones();
    }

    private void generarCupones(){
       for(int i=0;i<6;i++){
           listaDeCupones.add(" ");
       }
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
