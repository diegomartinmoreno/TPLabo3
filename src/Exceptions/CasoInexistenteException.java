package Exceptions;

public class CasoInexistenteException extends RuntimeException{
    public CasoInexistenteException() {
        super("Se ha equivocado de caso, no existe! Reinicie el programa.\"");
    }
}
