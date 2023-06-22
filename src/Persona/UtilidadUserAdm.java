package Persona;
import Exceptions.IntentosMaximosDeInicioSesionAlcanzadoException;
import Exceptions.MenorDeEdadException;
import model.Zonas;

import java.util.Scanner;

public interface UtilidadUserAdm {
    void agregarDinero(double dinero);
    boolean login (String contrasenia);
    void establecerZonaActual (Zonas zonaActual);
}
