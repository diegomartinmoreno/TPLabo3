package model;

import java.util.*;

public class Empresa {
    private String nombre;
    private LinkedHashMap <TipoDeProductos, HashSet<Producto>> productosEmpresa; ///AAAAA
    private Set<Zonas> zonas; /// AAAAAA
    private List <String> listaDeCupones;
    private  double CostoDeEnvio;

    public Empresa() {
    }

    public Empresa(String nombre, LinkedHashMap<TipoDeProductos, HashSet<Producto>> productosEmpresa, Set<Zonas> zonas, double costoDeEnvio) {
        listaDeCupones = new ArrayList<>();

        this.nombre = nombre;
        this.productosEmpresa = productosEmpresa;
        this.zonas = zonas;
        CostoDeEnvio = costoDeEnvio;
        generarCupones();
    }





    public boolean validarCupon(String cupon) throws NullPointerException{
       if(cupon==null) throw new NullPointerException("Error! La cadena no puede ser vacia.//***");

       boolean bool = false;

        for (int i = 0; i<listaDeCupones.size() && !bool; i++){
            if (listaDeCupones.get(i).equals(cupon)){
                bool = true;
                eliminarCupon(listaDeCupones.get(i));
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
           listaDeCupones.add(generarCuponAleatorio(6));
       }
    }

    public static String generarCuponAleatorio(int longitud) {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(longitud);

        for (int i = 0; i < longitud; i++) {
            int indice = random.nextInt(caracteres.length());
            char caracterAleatorio = caracteres.charAt(indice);
            sb.append(caracterAleatorio);
        }

        return sb.toString();
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCostoDeEnvio() {
        return CostoDeEnvio;
    }

    public void setCostoDeEnvio(double costoDeEnvio) {
        CostoDeEnvio = costoDeEnvio;
    }

    public LinkedHashMap<TipoDeProductos, HashSet<Producto>> getProductosEmpresa() {
        return productosEmpresa;
    }

    public void setProductosEmpresa(LinkedHashMap<TipoDeProductos, HashSet<Producto>> productosEmpresa) {
        this.productosEmpresa = productosEmpresa;
    }

    public List<String> getListaDeCupones() {
        return listaDeCupones;
    }

    public void setListaDeCupones(List<String> listaDeCupones) {
        this.listaDeCupones = listaDeCupones;
    }

    private void mostrarProductosEmpresa(){
        for (Map.Entry<TipoDeProductos, HashSet<Producto>> entry : productosEmpresa.entrySet()) {
            TipoDeProductos tipoDeProducto = entry.getKey();
            HashSet<Producto> listaDeProductos = entry.getValue();

            System.out.println("Producto: " +  tipoDeProducto.name());
            System.out.println("Lista de productos: \n" + listaDeProductos + "\n");
        }
    }

    public void mostrarEmpresa(){
        System.out.println("Empresa = " + nombre + '\'');
        System.out.println("Productos de la empresa:.... \n");

        mostrarProductosEmpresa();
        System.out.println("Zonas: " + zonas);
        System.out.println("Cupones: " + listaDeCupones + "\nCostoDeEnvio= " + CostoDeEnvio + "\n///////////////////////////////////////////////////////////\n");
    }

    public Producto buscarProductoPorID(int id) {
        Producto producto = null;

        for (HashSet<Producto> productos : productosEmpresa.values()){
            producto = buscarProductoEnHashSet(id, productos);
        }

        if (producto==null) throw new RuntimeException("No encontro el producto.....//***");

        return producto;
    }

    public Producto buscarProductoEnHashSet(int id, HashSet<Producto> productos){
        Producto auxiliar = null;

        for (Producto producto : productos){
            System.out.println(producto);
            if(producto.getId() == id){
                auxiliar = producto;
            }
        }

        return auxiliar;
    }

    @Override
    public String toString() {
        return "Empresa{" +
                "nombre='" + nombre + '\'' +
                ", productosEmpresa=" + productosEmpresa + "\n" +
                ", ListaZonas=" +          "\n"  +



                ", listaDeCupones=" + listaDeCupones + "\n" +
                ",CostoDeEnvio=" + CostoDeEnvio +
                "}\n";
    }
}
