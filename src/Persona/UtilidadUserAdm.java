package Persona;
import Exceptions.IntentosMaximosDeInicioSesionAlcanzadoException;
import Exceptions.MenorDeEdadException;

import java.util.Scanner;

public interface UtilidadUserAdm {
    void agregarDinero(double dinero);
    boolean login (String contrasenia);
}
