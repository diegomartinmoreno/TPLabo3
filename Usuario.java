import java.util.ArrayList;
import java.util.List;

public class Usuario extends Persona{
    private List <String> zonas;
    //TARJETA...
    //HISTORIAL DE COMPRA...
    private String contrasenia;
    /*
        La contrasenia no deberia tener un getter, es un campo privado al usuario, sino perderia seguridad el sistema.
        Debe tener un minimo de 7 caracteres, donde al menos hayan 2 numeros y 5 letras.
    */

    private String zonaActual; //este atributo se vera modificado cada vez que se inicie el programa y el user elija su posicion actual. En caso
    //de ser nueva, se agregara al arraylist de zonas.


    public Usuario() {
        super();
        zonas = new ArrayList<>();
    }
    public Usuario(String nombre, String apellido, String nroDeTelefono, int edad, String email, String dni, List<String> zonas, String contrasenia) {
        super(nombre, apellido, nroDeTelefono, edad, email, dni);
        this.zonas = zonas;
        this.contrasenia = contrasenia;
    }
    public Usuario(String nombre, String apellido, String nroDeTelefono, int edad, String email, String dni, List<String> zonas) {
        super(nombre, apellido, nroDeTelefono, edad, email, dni);
        this.zonas = zonas;
    }

    public List<String> getZonas() {
        return zonas;
    }
    public void setZonas(List<String> zonas) {
        this.zonas = zonas;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    @Override
    public String toString() {
        return super.toString() + "\nZonas disponibles: " + zonas;
    }

    public boolean agregarUnaZona (String zonaNueva){
        return zonas.add(zonaNueva);
    }

    public void establecerZonaActual (String zonaActual){
        this.zonaActual = zonaActual;
    }

    public int contarNumerosDeCadena (String cadena){
        int contador=0;
        for (int i=0; i<cadena.length();i++){
            char c = cadena.charAt(i);
            if (Character.isDigit(c))
                contador++;
        }
        return contador;
    }

    public int contarLetrasCadena (String cadena){
        int contador=0;
        for (int i=0; i<cadena.length();i++){
            char c = cadena.charAt(i);
            if (Character.isAlphabetic(c))
                contador++;
        }
        return contador;
    }

    public boolean verificarContraseniaSegura (String contrasenia){
        if (contrasenia.length()<7) //verificamos primero la longitud, ya que si esta ya es menor a la solicitada, es incorrecta.
            return false;
        return contarLetrasCadena(contrasenia)>=5 && contarNumerosDeCadena(contrasenia)>=2;
    }


}
