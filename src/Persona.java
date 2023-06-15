import java.security.PublicKey;
import java.util.Objects;

public abstract class Persona {
    private String nombre;
    private String apellido;
    private String nroDeTelefono;
    private int edad;
    private String email;
    private String dni;
    private final int MAYOR_EDAD = 18;
    private final int LONGITUD_DNI = 8;
    private final int LONGITUD_TELEFONO_MODERNA = 10;
    private final int LONGITUD_TELEFONO_ANTERIOR = 9; //EN EL CASO DE PERSONAS MAYORES, DONDE SU NUMERO ES DE 9 DIGITOS.

    public Persona() {
    }
    public Persona(String nombre, String apellido, String nroDeTelefono, int edad, String email, String dni) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nroDeTelefono = nroDeTelefono;
        this.edad = edad;
        this.email = email;
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNroDeTelefono() {
        return nroDeTelefono;
    }
    public void setNroDeTelefono(String nroDeTelefono) {
        this.nroDeTelefono = nroDeTelefono;
    }

    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Persona persona)) return false;
        return dni.equals(persona.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    @Override
    public String toString() {
        return  "------------------------\n"
                + nombre + "\n" +
                apellido + "\n" +
                nroDeTelefono + "\n" +
                edad + " anios" + "\n" +
                email + "\n" +
                "DNI: " + dni + "\n";
    }

    public boolean verificarEsLetra (String cadena) throws NullPointerException{
        if(cadena==null)throw new NullPointerException("Error! La cadena no puede ser nula.");
        return cadena.matches("[a-zA-Z]+");
    }

    public boolean verificarEdad (int edad){
        return edad>=MAYOR_EDAD;
    }

    public boolean verificarLongitudDNI (String dni) throws NullPointerException{
        if(dni==null) throw new NullPointerException("Error! El DNI no puede ser nulo.");
        return dni.length()==LONGITUD_DNI;
    }

    public boolean verificarEsNumero (String cadena) throws NullPointerException{
        if(cadena==null) throw new NullPointerException("Error! La cadena no puede ser nula.");
        return cadena.matches("[0-9]+");
    }

    public boolean verificarLongitudTelefono (String nroDeTelefono) throws NullPointerException{
        if(nroDeTelefono==null) throw new NullPointerException("Error! El telefono no puede ser nulo.");
        return nroDeTelefono.length()==LONGITUD_TELEFONO_MODERNA || nroDeTelefono.length()==LONGITUD_TELEFONO_ANTERIOR;
    }

    public boolean verificarCodigoDeArea (String codigoArea){
        if(codigoArea==null) throw new NullPointerException("Error! El codigo de area no puede ser nulo.");
        return codigoArea.substring(0, 3).equals("223");
    }


}
