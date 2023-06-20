package Persona;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Password {
    private static final int LONGITUD_MINIMA = 7;
    private static final int CANTIDAD_MINIMA_DE_CARACTERES = 5;
    private static final int CANTIDAD_MINIMA_DE_NUMEROS = 2;
    private String password;

    public Password() {
    }
    public Password(String password) {
        if (password == null) throw new IllegalArgumentException("La contraseña no puede ser nula");
        if (!verificarContraseniaSegura(password)) throw new IllegalArgumentException("La contraseña no es segura");
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    private boolean verificarContraseniaSegura(String contrasenia) {
        if (contrasenia.length() < LONGITUD_MINIMA) return false;
        return contarLetrasCadena(contrasenia) >= CANTIDAD_MINIMA_DE_CARACTERES && contarNumerosDeCadena(contrasenia) >= CANTIDAD_MINIMA_DE_NUMEROS;
    }

    private int contarLetrasCadena(String cadena) {
        int contador = 0;
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            if (Character.isAlphabetic(c))
                contador++;
        }
        return contador;
    }

    private int contarNumerosDeCadena(String cadena) {
        int contador = 0;
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            if (Character.isDigit(c))
                contador++;
        }
        return contador;
    }

    public boolean validate(String contrasenia) {
        return password.equals(contrasenia);
    }

    @Override
    public String toString() {
        return "Password{" +
                "password=" + password +
                '}';
    }
}