package Exceptions;

public class IntentosMaximosDeInicioSesionAlcanzadoException extends RuntimeException{
    public IntentosMaximosDeInicioSesionAlcanzadoException() {
        super("Usted alcanzo los 3 intentos maximos para iniciar sesion. Hasta luego!.");
    }
}
