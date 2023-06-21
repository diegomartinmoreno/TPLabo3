package model;

import java.util.*;

public class Empresa {
    private String nombre;
    private LinkedHashMap <TipoDeProductos, HashSet<Producto>> productosEmpresa; ///AAAAA
    private Set<Zonas> zonas; /// AAAAAA
    private List <String> listaDeCupones;
    private double costoDeEnvio;

    private int puntuacion;

    public Empresa() {
        listaDeCupones = new ArrayList<>();
        zonas = new HashSet<>();


        generarCupones();
        setPuntuacion(0);
    }

    public Empresa(String nombre, LinkedHashMap<TipoDeProductos, HashSet<Producto>> productosEmpresa, Set<Zonas> zonas, double costoDeEnvio, int puntuacion) {
        listaDeCupones = new ArrayList<>();

        this.nombre = nombre;
        this.productosEmpresa = productosEmpresa;
        this.zonas = zonas;
        this.costoDeEnvio = costoDeEnvio;
        this.puntuacion = puntuacion;
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

    public void generarCupones(){
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
        return costoDeEnvio;
    }

    public void setCostoDeEnvio(double costoDeEnvio) {
        this.costoDeEnvio = costoDeEnvio;
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
        System.out.println("Cupones: " + listaDeCupones + "\nCostoDeEnvio= " + costoDeEnvio + "\n///////////////////////////////////////////////////////////\n");
    }

    public void mostrarParaComprar(){
        System.out.println("-----------------------------------------------------------------------------------------------------------------------");
        System.out.printf("<<<< " + getNombre() + " >>>>  -- RATING: ");
        mostrarRating();
        System.out.println("\n                            Costo de envio: $" + costoDeEnvio);
        System.out.println("                                                                             ZONAS: " + getZonas());
        mostrarMap();
        System.out.println("\n\n" + listaDeCupones); ////////////NO MOSTRAR
        System.out.println("-----------------------------------------------------------------------------------------------------------------------");

    }

    private void mostrarRating(){
        for(int i=0;i<puntuacion;i++){
            System.out.printf("⭐");
        }
    }

    public Producto buscarProductoPorID(int id) {
        Producto producto = null;

        for (HashSet<Producto> productos : productosEmpresa.values()){
            producto = buscarProductoEnHashSet(id, productos);
            if (producto!=null) break;
        }



        if (producto==null) throw new RuntimeException("No encontro el producto.....//***");

        return producto;
    }

    public Producto buscarProductoEnHashSet(int id, HashSet<Producto> productos){

        for (Producto producto : productos){

            if(producto.getId() == id){
                return producto;
            }
        }

        return null;
    }


    private void mostrarMap() {
        // Obtener la longitud máxima de las claves para alinear correctamente las columnas
        int maxKeyLength = 0;
        for (TipoDeProductos key : productosEmpresa.keySet()) {
            maxKeyLength = Math.max(maxKeyLength, key.name().length());
        }

        // Imprimir la tabla
        for (Map.Entry<TipoDeProductos, HashSet<Producto>> entry : productosEmpresa.entrySet()) {
            TipoDeProductos key = entry.getKey();
            HashSet<Producto> values = entry.getValue();

            // Imprimir la clave alineada a la izquierda
            System.out.print(key + "\n");

            for (Producto value : values) {
                System.out.print("\t\t");
                value.mostrarProducto();
            }

            System.out.println(); // Nueva línea después de cada fila
        }
    }

    public Set<Zonas> getZonas() {
        return zonas;
    }

    public void setZonas(Set<Zonas> zonas) {
        this.zonas = zonas;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }



    @Override
    public String toString() {
        return "Empresa{" +
                "nombre='" + nombre + '\'' +
                ", productosEmpresa=" + productosEmpresa + "\n" +
                ", ListaZonas=" +          "\n"  +



                ", listaDeCupones=" + listaDeCupones + "\n" +
                ",CostoDeEnvio=" + costoDeEnvio +
                "}\n";
    }


}
