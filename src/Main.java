import Persona.Persona;
import Persona.Usuario;
import Persona.UsuariosWrapper;
import Persona.Administrador;
import Persona.UtilidadUserAdm;

import model.PedidosYa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Main {
    public static void main(String[] args) {
        PedidosYa pedidosYa = new PedidosYa();
        pedidosYa.exportarUsuariosToJSON(PedidosYa.ARCHIVO_USUARIOS, pedidosYa.getUsuarios());


    }

}