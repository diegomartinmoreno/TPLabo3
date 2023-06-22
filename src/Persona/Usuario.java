package Persona;

import Exceptions.CasoInexistenteException;
import Exceptions.IntentosMaximosDeInicioSesionAlcanzadoException;
import Exceptions.MenorDeEdadException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import model.*;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class Usuario extends Persona implements UtilidadUserAdm{
    private Tarjeta tarjeta;
    @JsonIgnore
    private Carrito carrito;
    private HistorialDeCompras historialDeCompras;
    private Password contrasenia; // La contrasenia debe tener un minimo de 7 caracteres, donde al menos hayan 2 numeros y 5 letras.
    private Zonas zonaActual=null; //este atributo se vera modificado cada vez que se inicie el programa y el user elija su posicion actual. En caso
    //de ser nueva, se agregara al arraylist de zonas.

    public Usuario() {
        super();
        tarjeta = new Tarjeta();
        carrito=new Carrito();
        historialDeCompras = new HistorialDeCompras();
    }
    public Usuario(String nombre, String apellido, String nroDeTelefono, int edad, String email, String dni, Password contrasenia) {
        super(nombre, apellido, nroDeTelefono, edad, email, dni);
        this.contrasenia = contrasenia;
    }
    public Usuario(String nombre, String apellido, String nroDeTelefono, int edad, String email, String dni) {
        super(nombre, apellido, nroDeTelefono, edad, email, dni);
    }

    public void setContrasenia(Password contrasenia) {
        this.contrasenia = contrasenia;
    }
    public Password getContrasenia() {
        return contrasenia;
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

    public Carrito getCarrito() {
        return carrito;
    }
    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public Zonas getZonaActual() {
        return zonaActual;
    }
    public void setZonaActual(Zonas zonaActual) {
        this.zonaActual = zonaActual;
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
