import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Usuario extends Persona{
    private Set<String> zonas;
    private Tarjeta tarjeta;
    private HistorialDeCompras historialDeCompras;
    private String contrasenia;
    /*
        La contrasenia no deberia tener un getter, es un campo privado al usuario, sino perderia seguridad el sistema.
        Debe tener un minimo de 7 caracteres, donde al menos hayan 2 numeros y 5 letras.
    */

    private String zonaActual; //este atributo se vera modificado cada vez que se inicie el programa y el user elija su posicion actual. En caso
    //de ser nueva, se agregara al arraylist de zonas.


    public Usuario() {
        super();
        zonas = new HashSet<>();
    }
    public Usuario(String nombre, String apellido, String nroDeTelefono, int edad, String email, String dni, Set<String> zonas, String contrasenia) {
        super(nombre, apellido, nroDeTelefono, edad, email, dni);
        this.zonas = zonas;
        this.contrasenia = contrasenia;
    }
    public Usuario(String nombre, String apellido, String nroDeTelefono, int edad, String email, String dni, Set<String> zonas) {
        super(nombre, apellido, nroDeTelefono, edad, email, dni);
        this.zonas = zonas;
    }

    public Set<String> getZonas() {
        return zonas;
    }
    public void setZonas(Set<String> zonas) {
        this.zonas = zonas;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    @Override
    public String toString() {
        return super.toString()
                + "Zonas disponibles: " + zonas +
                "\n------------------------\n";
    }

    public boolean agregarUnaZona (String zonaNueva) throws NullPointerException{
        if (zonaNueva == null) throw new NullPointerException("Error! La nueva zona es nula.");
        return zonas.add(zonaNueva);
    }

    public void establecerZonaActual (String zonaActual) throws NullPointerException {
        if (zonaActual == null) throw new NullPointerException("Error! La zona es nula.");
        this.zonaActual = zonaActual;
    }

    private static int contarNumerosDeCadena (String cadena){
        int contador=0;
        for (int i=0; i<cadena.length();i++){
            char c = cadena.charAt(i);
            if (Character.isDigit(c))
                contador++;
        }
        return contador;
    }

    private static int contarLetrasCadena (String cadena){
        int contador=0;
        for (int i=0; i<cadena.length();i++){
            char c = cadena.charAt(i);
            if (Character.isAlphabetic(c))
                contador++;
        }
        return contador;
    }

    public static boolean verificarContraseniaSegura (String contrasenia) throws NullPointerException{
        if (contrasenia == null) throw new NullPointerException("Error! La contrasenia enviada es nula.");
        if (contrasenia.length()<7) //verificamos primero la longitud, ya que si esta ya es menor a la solicitada, es incorrecta.
            return false;
        return contarLetrasCadena(contrasenia)>=5 && contarNumerosDeCadena(contrasenia)>=2;
    }


}
