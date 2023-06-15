package Exceptions;

public class MenorDeEdadException extends RuntimeException{
    public MenorDeEdadException() {
        super("La edad minima requerida es de 16 anios o mas. Adios...");
    }
}
