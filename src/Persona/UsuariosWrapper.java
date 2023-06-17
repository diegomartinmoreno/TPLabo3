package Persona;

import Persona.Usuario;
import java.util.HashSet;

public class UsuariosWrapper {
    private HashSet<Usuario> usuarios;

    public UsuariosWrapper() {
        usuarios = new HashSet<>();
    }

    // Getter y setter para la colección de usuarios

    public HashSet<Usuario> getUsuarios() {
        return usuarios;
    }
    public void setUsuarios(HashSet<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
