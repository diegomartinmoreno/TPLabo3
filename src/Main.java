import model.Usuario;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Set<String> zonas = new HashSet<>();
        zonas.add("Barrio 1");
        zonas.add("Barrio 2");
        Usuario usuario = new Usuario("Lautaro", "Ruiz", "2235601139", 18, "lautaroruiz04", "45905505" , zonas, "Lauti");

    }

}