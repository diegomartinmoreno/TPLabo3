package Persona;

import Exceptions.IntentosMaximosDeInicioSesionAlcanzadoException;
import Exceptions.MenorDeEdadException;
import model.HistorialDeCompras;
import model.PedidosYa;
import model.Tarjeta;
import model.Zonas;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class Usuario extends Persona implements UtilidadUserAdm{
    private Set<String> zonas;
    private Tarjeta tarjeta;
    private HistorialDeCompras historialDeCompras;
    private Password contrasenia; // La contrasenia debe tener un minimo de 7 caracteres, donde al menos hayan 2 numeros y 5 letras.
    private Zonas zonaActual; //este atributo se vera modificado cada vez que se inicie el programa y el user elija su posicion actual. En caso
    //de ser nueva, se agregara al arraylist de zonas.

    public Usuario() {
        super();
        zonas = new HashSet<>();
        tarjeta = new Tarjeta();
    }
    public Usuario(String nombre, String apellido, String nroDeTelefono, int edad, String email, String dni, Set<String> zonas, Password contrasenia) {
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

    public void setContrasenia(Password contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }
    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    public HistorialDeCompras getHistorialDeCompras() {
        return historialDeCompras;
    }
    public void setHistorialDeCompras(HistorialDeCompras historialDeCompras) {
        this.historialDeCompras = historialDeCompras;
    }

    @Override
    public String toString() {
        return super.toString()
                + "Zonas disponibles: " + zonas + contrasenia +
                "\n------------------------\n";
    }

    public boolean agregarUnaZona (String zonaNueva) throws NullPointerException{
        if (zonaNueva == null) throw new NullPointerException("Error! La nueva zona es nula.");
        return zonas.add(zonaNueva);
    }

    public void establecerZonaActual (Zonas zonaActual) throws NullPointerException {
        if (zonaActual == null) throw new NullPointerException("Error! La zona es nula.");
        this.zonaActual = zonaActual;
    }

    @Override
    public boolean login (String contrasenia) {
        return this.contrasenia.validate(contrasenia);
    }

    @Override
    public void agregarDinero(double dinero) {
        tarjeta.setSaldo(tarjeta.getSaldo() + dinero);
    }
}
