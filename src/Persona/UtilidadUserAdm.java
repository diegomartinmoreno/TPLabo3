package Persona;
import model.Zonas;

public interface UtilidadUserAdm {
    void agregarDinero(double dinero);
    boolean login (String contrasenia);
    void establecerZonaActual (Zonas zonaActual);
}
