package model;

import java.util.*;

public class Empresa {

   private List <String> listaDeCupones;
    private String Nombre;

    private  double CostoDeEnvio;

    private LinkedHashMap <String, Producto> ProductosEmpresa; ///AAAAA

    private HashSet<Zonas> ListaZonas; /// AAAAAA

    public Empresa() {
    }

    public Empresa(String nombre, double costoDeEnvio) {
        Nombre = nombre;
        CostoDeEnvio = costoDeEnvio;
        ProductosEmpresa = new LinkedHashMap<>();
        listaDeCupones = new LinkedList<>();
        ListaZonas = new HashSet<>();
        generarCupones();
    }

   public boolean validarCupon(String cupon) throws NullPointerException{
       if(cupon==null) throw new NullPointerException("Error! La cadena no puede ser vacia.//***");

       boolean bool = false;

        for (int i = 0; i<listaDeCupones.size() && !bool; i++){
            if (listaDeCupones.get(i).equals(cupon)){
                bool = true;
                listaDeCupones.remove(listaDeCupones.get(i));
            }
        }

        return bool;
    }

    public boolean eliminarCupon(String cupon){
       if (cupon==null) {throw new NullPointerException("Error! La cadena no puede ser vacia.//***");}

        return listaDeCupones.remove(cupon);
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

    public List<String> getListaDeCupones() {
        return listaDeCupones;
    }

    public void setListaDeCupones(List<String> listaDeCupones) {
        this.listaDeCupones = listaDeCupones;
    }

    public HashSet<Zonas> getListaZonas() {
        return ListaZonas;
    }

    public void setListaZonas(HashSet<Zonas> listaZonas) {
        ListaZonas = listaZonas;
    }
}
