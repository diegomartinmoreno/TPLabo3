import Exceptions.CasoInexistenteException;
import Persona.Persona;
import Persona.Usuario;
import Persona.Administrador;
import model.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        PedidosYa pedidosYa = new PedidosYa();
        Carrito carrito = new Carrito();
        HistorialDeCompras historialDeCompras = new HistorialDeCompras();
        Scanner scanner = new Scanner(System.in);
        Tarjeta tarjeta = new Tarjeta();

        //pedidosYa.registroDeCuentaDeAdmin(scanner);

        Administrador administrador = pedidosYa.iniciarSesionComoAdmin(scanner);
        pedidosYa.modificarContraseniaDeAdministrador(scanner);

        //hola commo va

        pedidosYa.setZonaUsuarioActual(Zonas.ALEM);
        pedidosYa.cargarListaDeEmpresas();
        pedidosYa.mostrarEmpresas();

        clearConsole();

        //clearConsole();

        menuDeCarrito(scanner, pedidosYa, carrito, historialDeCompras, tarjeta);

       /// pedidosYa.mostrarEmpresas();
        //Empresa A= pedidosYa.buscarPorNombreSinSerExacto(scanner);

        /*Usuario usuario = pedidosYa.registroDeCuenta(scanner);
        System.out.println(usuario);
        pedidosYa.modificarContrasenia(scanner);*/


        ////Luego de haber inciiado sesion se muestra.... menuPrincipal()...
        ////QUE DESEA COMER?
        ///MUESTRA TODOS LOS RESTAURANTES, ELIJE UNO
        ///Muestra la empresa elejida con mostrarEmpresa()
        ///mostrarMenuDeCarrito()

        scanner.close();

    }

    public Persona menuDeSeleccionDeModoDeAcceso (Scanner scanner, PedidosYa pedidosYa) throws CasoInexistenteException{
        System.out.println("En que modo va a querer acceder?");
        System.out.println("(1) Administrador.");
        System.out.println("(2) Usuario.");

        int opcion = scanner.nextInt();

        try {
            switch (opcion) {
                case 1 -> {
                    System.out.println("(1) Iniciar Sesion >>");
                    System.out.println("(2) Registrarse >>");
                    int caso = scanner.nextInt();
                    switch (caso) {
                        case 1 -> {
                            return pedidosYa.iniciarSesionComoAdmin(scanner);
                        }
                        case 2 -> {
                            return pedidosYa.registroDeCuentaDeAdmin(scanner);
                        }
                        default -> throw new CasoInexistenteException();
                    }
                }
                case 2 -> {
                    System.out.println("(1) Iniciar Sesion >>");
                    System.out.println("(2) Registrarse >>");
                    int casoUser = scanner.nextInt();
                    switch (casoUser) {
                        case 1 -> {
                            return pedidosYa.iniciarSesionComoUsuario(scanner);
                            //AGREGAR ELEGIR LA ZONA ACTUAL PARA MANEJAR ESO EN EL MENU.
                        }
                        case 2 -> {
                            return pedidosYa.registroDeCuentaDeUsuario(scanner);
                        }
                        default -> throw new CasoInexistenteException();
                    }
                }
                default -> throw new CasoInexistenteException();
            }
        }catch (InputMismatchException e){
            System.out.println("LO INGRESADO NO FUE UN NUMERO. CERRANDO EL PROGRAMA...");
        }
        return null;
    }

    public static void menuPrincipalUsuario(Scanner scanner, Usuario usuario, PedidosYa pedidosYa){
        int opcion=0; char control = 's'; boolean flag = false;
        do {
            System.out.println("Bienvenido a PedidosYa! Que desea hacer? >>");
            System.out.println("(1) Ver perfil >> ");
            System.out.println("(2) Ver Historial de compras >>");
            System.out.println("(3) Comprar >>");
            System.out.println("(4) Modificar perfil >>");
            System.out.println("(5) Salir >>");
            opcion = scanner.nextInt();

            try {
                switch (opcion) {
                    case 1 -> {
                        System.out.println("<< PERFIL >>");
                        System.out.println(usuario.toString());
                    }
                    case 2 -> {
                        System.out.println("<< HISTORIAL DE COMPRAS >>");
                        usuario.getHistorialDeCompras().listarHistorial();
                    }
                    case 3 -> menuDeCarrito(scanner, pedidosYa, usuario.getCarrito(), usuario.getHistorialDeCompras(), usuario.getTarjeta());

                    case 4 -> {
                        System.out.println(" (1) Modificar contrasenia >>");
                        System.out.println(" (2) Modificar nombre y apellido >>");
                        System.out.println(" (3) Modificar numero de telefono >>");
                        System.out.println(" (4) Modificar email >>");
                        System.out.println(" (5) Cambiar tarjeta actual >>");
                        System.out.println("Si no desea ninguna presione 0.");

                        int eleccion = scanner.nextInt();

                        switch (eleccion){
                            case 1 -> {
                                flag = pedidosYa.modificarContraseniaDeUsuario(scanner);
                                if (flag) System.out.println("\nLa modificacion de contrasenia se realizo con exito.");
                                else System.out.println("La modificacion de contrasenia no se realizo exitosamente.");
                            }
                            case 2 ->{
                                flag = pedidosYa.modificarNombreYapellidoDeUsuario(scanner);
                                if (flag) System.out.println("\nLa modificacion de nombre y apellido se realizo con exito.");
                                else System.out.println("La modificacion de nombre y apellido no se realizo exitosamente.");
                            }
                            case 3 ->{
                                flag = pedidosYa.modificarNroTelefonoDeUsuario(scanner);
                                if (flag) System.out.println("\nLa modificacion de numero telefonico se realizo con exito.");
                                else System.out.println("La modificacion de numero telefonico no se realizo exitosamente.");
                            }
                            case 4-> {
                                flag = pedidosYa.modificarEmailDeUsuario(scanner);
                                if (flag) System.out.println("\nLa modificacion de email se realizo con exito.");
                                else System.out.println("La modificacion de email no se realizo exitosamente.");
                            }
                            case 5-> {
                                flag = pedidosYa.cambiarTarjetaDeUsuario(scanner);
                                if (flag) System.out.println("\nLa modificacion de tarjeta se realizo con exito.");
                                else System.out.println("La modificacion de tarjeta no se realizo exitosamente.");
                            }
                            default -> System.out.println("Volviendo al menu principal...");
                        }
                    }
                    case 5 -> control = 'n';

                    default -> throw new CasoInexistenteException();
                }
            } catch (InputMismatchException e) {
                System.out.println("LO INGRESADO NO FUE UN NUMERO. VOLVIENDO AL MENU PRINCIPAL...");
            }

        }while (control == 's');


    }

    public static void menuPrincipalAdmin(){

    }

    public static void menuDeCarrito(Scanner scanner, PedidosYa pedidosYa, Carrito carrito, HistorialDeCompras historialDeCompras, Tarjeta tarjeta){ ///CARRITO< HISTORIAL< TARJETA< PERTENECENN A USUARIO.......
        Empresa elegida = pedidosYa.buscarEmpresaConMetodoElegido(scanner);
        do {
            System.out.println("Que desea hacer?");
            System.out.println("(1) Agregar producto al carrito");
            System.out.println("(2) Eliminar producto del carrito");
            System.out.println("(3) Editar cantidad del producto");
            System.out.println("(4) Agregar cupon");
            System.out.println("(5) Ir a pagar");
            System.out.println("(6) Ver carrito");
            System.out.println("(7) salir");

            int decision = scanner.nextInt();

            switch (decision) {
                case 1:
                    Producto prodAux = new Producto();
                    elegida.mostrarParaComprar();

                    System.out.println("Ingrese el id del producto que desea llevar:");
                    int idProducto = scanner.nextInt();

                    try {
                        prodAux = elegida.buscarProductoPorID(idProducto);
                        System.out.println("Producto elegido: ");
                        prodAux.productoElegido();


                        System.out.println("Cuanto desea llevar del producto?");
                        prodAux.setCantidadPedido(scanner.nextInt());

                        carrito.agregarProductoAlCarrito(elegida, prodAux);
                    } catch (RuntimeException e){
                        System.out.println(e.getMessage());
                    }



                    break;

                case 2:
                    carrito.mostrarProductos();

                    System.out.println("Ingrese el numero del producto que desea eliminar");
                    carrito.eliminarProductoDelCarrito(scanner.nextInt());
                    break;

                case 3:
                    carrito.mostrarProductos();

                    System.out.println("Ingrese el numero del producto que desea editar");
                    int pos = scanner.nextInt();
                    System.out.println("Producto elegido: " + carrito.getProductos().get(pos));

                    System.out.println("Ingrese la nueva cantidad del producto elegido");
                    carrito.editarCantidadDeProducto(scanner.nextInt(), pos);
                    break;

                case 4:
                    System.out.println("Cupones disponibles: " + carrito.getVendedor().getListaDeCupones());

                    System.out.println("Ingresa un cupon de 6 caracteres: ");
                    scanner.nextLine();
                    pedidosYa.agregarDescuento(scanner.nextLine(), carrito);
                    break;

                case 5:
                    System.out.println("Pagar carrito...................");
                    System.out.println("Desea enviarle una nota al repartidor? s/n");

                    if (scanner.next().charAt(0) == 's') {
                        System.out.println("Ingrese la nota que desea enviarle al repartidor:");

                        scanner.nextLine();
                        carrito.setNota(scanner.nextLine());
                    }

                    carrito.pagarCarrito(historialDeCompras, tarjeta);

                case 6:
                    carrito.listarCarrito();
                    break;
            }

            System.out.println("Desea continuar? s/n");
        }while (scanner.next().charAt(0) != 'n');
    }

    public static void clearConsole() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
}