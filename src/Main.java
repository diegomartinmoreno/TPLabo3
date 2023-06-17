import Exceptions.IntentosMaximosDeInicioSesionAlcanzadoException;
import Persona.Persona;
import Persona.Usuario;
import Persona.Administrador;
import Persona.UtilidadUserAdm;

import model.Carrito;
import model.PedidosYa;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        PedidosYa pedidosYa = new PedidosYa();
        Carrito carrito = new Carrito();
        Scanner scanner = new Scanner(System.in);

        Usuario usuario = pedidosYa.registroDeCuenta(scanner);
        System.out.println(usuario);
        pedidosYa.modificarContrasenia(scanner);










        scanner.close();

    }












    public static void mostrarMenuDeCarrito(Scanner scanner){
        System.out.println("Que desea hacer?");
        System.out.println("(1) Agregar producto al carrito");
        System.out.println("(2) Eliminar producto del carrito");
        System.out.println("(3) Editar cantidad del producto");
        System.out.println("(4) Agregar cupon");
        System.out.println("(5) Ir a pagar");
        System.out.println("(6) Ver carrito");
        System.out.println("(7) salir");

        int decision = scanner.nextInt();

        switch (decision){
            case 1:
                //agregarProductoAlCarrito()
        }

    }

}