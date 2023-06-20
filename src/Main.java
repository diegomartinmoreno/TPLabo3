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

        try {
            int opcion = scanner.nextInt();
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
            System.out.println("(1) Ver perfil >> "); //NO ES NECESARIO ACTUALIZAR ARCHIVO
            System.out.println("(2) Ver Historial de compras >>"); //NO ES NECESARIO ACTUALIZAR ARCHIVO
            System.out.println("(3) Comprar >>");
            System.out.println("(4) Modificar perfil >>"); //SE ACTUALIZA EN ARCHIVO
            System.out.println("(5) Limpiar historial de compras."); //SE ACTUALIZA EN ARCHIVO
            System.out.println("(6) Cargar Tarjeta"); //SE ACTUALIZA EN ARCHIVO
            System.out.println("(7) Salir >>"); //NO ES NECESARIO ACTUALIZAR ARCHIVO

            try {
                opcion = scanner.nextInt();
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
                        System.out.println("Que desea modificar de su perfil? ");
                        System.out.println(" (1) Modificar contrasenia >>"); //SE ACTUALIZA EN ARCHIVO
                        System.out.println(" (2) Modificar nombre y apellido >>"); //SE ACTUALIZA EN ARCHIVO
                        System.out.println(" (3) Modificar numero de telefono >>"); //SE ACTUALIZA EN ARCHIVO
                        System.out.println(" (4) Modificar email >>"); //SE ACTUALIZA EN ARCHIVO
                        System.out.println(" (5) Cambiar tarjeta actual >>"); //SE ACTUALIZA EN ARCHIVO
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
                                if (flag) System.out.println("\nLa modificacion del email se realizo con exito.");
                                else System.out.println("La modificacion del email no se realizo exitosamente.");
                            }
                            case 5-> {
                                if (usuario.getTarjeta().isActiva()) {
                                    flag = pedidosYa.cambiarTarjetaDeUsuario(scanner);
                                    if (flag) System.out.println("\nLa modificacion de tarjeta se realizo con exito.");
                                    else System.out.println("La modificacion de tarjeta no se realizo exitosamente.");
                                }else{
                                    System.out.println("Su tarjeta no esta activa, deberia cargar una.");
                                }
                            }
                            default -> System.out.println("Volviendo al menu principal...");
                        }
                    }

                    case 5 -> {
                        System.out.println("Esta seguro que desea limpiar el historial de compras?  (s/n):");
                        char decision = scanner.next().charAt(0);

                        if (decision == 's'){
                            Set <Usuario> usuarios = pedidosYa.extraerUsuariosFromJSON(PedidosYa.ARCHIVO_USUARIOS);
                            Usuario aux = pedidosYa.buscarUserPorDNI(usuario.getDni(), usuarios);
                            if (aux!=null) {
                                aux.getHistorialDeCompras().limpiarHistorialDeCompras();
                                pedidosYa.exportarUsuariosToJSON(PedidosYa.ARCHIVO_USUARIOS, usuarios);
                            }else System.out.println("No se ha podido limpiar el historial de busqueda.");
                        }
                    }

                    case 6 -> {
                        if (usuario.getTarjeta().isActiva()) System.out.println("Su tarjeta se encuentra cargada, si desea modificarla vaya al menu 3 para modificar su perfil");
                        else{
                            flag = usuario.getTarjeta().cargarTarjeta(scanner);
                            if (flag){
                                System.out.println("Tarjeta cargada correctamente!");
                                Set<Usuario> usuarioSet = pedidosYa.extraerUsuariosFromJSON(PedidosYa.ARCHIVO_USUARIOS);
                                Usuario user = pedidosYa.buscarUserPorDNI(usuario.getDni(), usuarioSet);
                                if (user != null) {
                                    user.setTarjeta(usuario.getTarjeta());
                                }
                                pedidosYa.exportarUsuariosToJSON(PedidosYa.ARCHIVO_ADMINISTRADORES, usuarioSet);
                                //PRIMERO LO QUE TENGO QUE HACER ES OBTENER EL SET DE USUARIOS DEL ARCHIVO, MODIFICAR LA TARJETA DEL QUE TENGA EL MISMO DNI, Y LUEGO ESO VOLVER  A MODIFICARLO EN EL ARCHIVO.
                            }
                            else System.out.println("Tarjeta cargada incorrectamente!");
                        }
                    }

                    case 7 -> control = 'n';

                    default -> throw new CasoInexistenteException();
                }
            } catch (InputMismatchException e) {
                System.out.println("LO INGRESADO NO FUE UN NUMERO. VOLVIENDO AL MENU PRINCIPAL...");
            }
        }while (control == 's');
    }

    public static void menuPrincipalAdmin(Scanner scanner, Administrador administrador, PedidosYa pedidosYa) throws CasoInexistenteException{
        int opcion=0; char control = 's'; boolean flag = false;

        System.out.println("Bienvenido a PedidosYa administrador! Que desea hacer? >>");
        System.out.println("(1) Ver perfil de administrador >> "); //NO ES NECESARIO ACTUALIZAR ARCHIVO
        System.out.println("(2) Ver Historial de compras >>"); //NO ES NECESARIO ACTUALIZAR ARCHIVO
        System.out.println("(3) Limpiar historial de compras"); //SE ACTUALIZA EN ARCHIVO
        System.out.println("(4) Comprar >>");
        System.out.println("(5) Modificar perfil de administrador >>"); //SE ACTUALIZA EN ARCHIVO
        System.out.println("(6) Modificar empresas pertenecientes al programa >>");
        System.out.println("(7) Modificar productos >>"); //SE ACTUALIZA EN ARCHIVO
        System.out.println("(8) Eliminar un usuario >>"); //SE ACTUALIZA EN ARCHIVO
        System.out.println("(9) Cargar Tarjeta"); //SE ACTUALIZA EN ARCHIVO
        System.out.println("(10) Salir >>"); //NO ES NECESARIO ACTUALIZAR ARCHIVO
        do {
            try {
                opcion = scanner.nextInt();

                switch (opcion) {
                    case 1 -> {
                        System.out.println("<< PERFIL >>");
                        System.out.println(administrador.toString());
                    }

                    case 2 -> {
                        System.out.println("<< HISTORIAL DE COMPRAS >>");
                        administrador.getHistorialDeCompras().listarHistorial();
                    }

                    case 3 -> {
                        System.out.println("Esta seguro que desea limpiar el historial de compras?  (s/n):");
                        char decision = scanner.next().charAt(0);

                        if (decision == 's') {
                            Set<Administrador> administradores = pedidosYa.extraerAdministradoresFromJSON(PedidosYa.ARCHIVO_ADMINISTRADORES);
                            Administrador aux = pedidosYa.buscarAdministradorPorDNI(administrador.getDni(), administradores);
                            if (aux != null) {
                                aux.getHistorialDeCompras().limpiarHistorialDeCompras();
                                pedidosYa.exportarAdministradoresToJSON(PedidosYa.ARCHIVO_ADMINISTRADORES, administradores);
                            } else System.out.println("No se ha podido limpiar el historial de busqueda.");
                        }
                    }

                    case 4 -> menuDeCarrito(scanner, pedidosYa, administrador.getCarrito(), administrador.getHistorialDeCompras(), administrador.getTarjeta());

                    case 5 -> {
                        try {
                            System.out.println("Que desea modificar de su perfil? ");
                            System.out.println(" (1) Modificar contrasenia de administrador >>"); //SE ACTUALIZA EN ARCHIVO
                            System.out.println(" (2) Modificar nombre y apellido >>"); //SE ACTUALIZA EN ARCHIVO
                            System.out.println(" (3) Modificar numero de telefono >>"); //SE ACTUALIZA EN ARCHIVO
                            System.out.println(" (4) Modificar email >>"); //SE ACTUALIZA EN ARCHIVO
                            System.out.println(" (5) Cambiar tarjeta actual >>"); //SE ACTUALIZA EN ARCHIVO
                            System.out.println("Si no desea ninguna presione 0.");

                            int eleccion = scanner.nextInt();

                            switch (eleccion) {
                                case 1 -> {
                                    flag = pedidosYa.modificarContraseniaDeAdministrador(scanner);
                                    if (flag)
                                        System.out.println("\nLa modificacion de contrasenia se realizo con exito.");
                                    else
                                        System.out.println("La modificacion de contrasenia no se realizo exitosamente.");
                                }
                                case 2 -> {
                                    flag = pedidosYa.modificarNombreYapellidoDeAdministrador(scanner);
                                    if (flag)
                                        System.out.println("\nLa modificacion de nombre y apellido se realizo con exito.");
                                    else
                                        System.out.println("La modificacion de nombre y apellido no se realizo exitosamente.");
                                }
                                case 3 -> {
                                    flag = pedidosYa.modificarNroTelefonoDeUsuario(scanner);
                                    if (flag)
                                        System.out.println("\nLa modificacion del numero telefonico se realizo con exito.");
                                    else
                                        System.out.println("La modificacion del numero telefonico no se realizo exitosamente.");
                                }
                                case 4 -> {
                                    flag = pedidosYa.modificarEmailDeAdministrador(scanner);
                                    if (flag) System.out.println("\nLa modificacion del email se realizo con exito.");
                                    else System.out.println("La modificacion del email no se realizo exitosamente.");
                                }
                                case 5 -> {
                                    flag = pedidosYa.cambiarTarjetaDeAdministrador(scanner);
                                    if (flag)
                                        System.out.println("\nLa modificacion de la tarjeta se realizo con exito.");
                                    else
                                        System.out.println("La modificacion de la tarjeta no se realizo exitosamente.");
                                }
                                default -> throw new CasoInexistenteException();
                            }
                        } catch (CasoInexistenteException e) {
                            System.out.println(e.getMessage());
                        }
                    }

                    case 6 -> {
                        System.out.println("Que desea hacer? ");
                        System.out.println("(1) Agregar una empresa >>");
                        System.out.println("(2) Eliminar una empresa >>");

                        int opcionConEmpresas = scanner.nextInt();

                        if (opcionConEmpresas == 1) {
                            //METODO DE AGREGAR UNA EMPRESA CARGANDO POR TECLADO.
                            pedidosYa.agregarEmpresa(null); //LE PASAMOS ALGO POR PARAMETRO.
                        } else if (opcionConEmpresas == 2) {
                            //METODO DE BUSCAR UNA EMPRESA POR ALGUN CAMPO, POR EJ NOMBRE Y LA ELIMINAMOS.
                            pedidosYa.eliminarEmpresa(null); //LE PASAMOS ALGO POR PARAMETRO.
                        } else {
                            System.out.println("No ha seleccionado ningun caso. Regresando al menu principal.");
                        }
                    }

                    case 7 -> {
                        System.out.println("Que desea hacer? ");
                        System.out.println("(1) Agregar un producto a una empresa >>");
                        System.out.println("(2) Eliminar un producto >>");

                        int opcionConProductos = scanner.nextInt();
                        if (opcionConProductos == 1) {
                            //LLAMAMOS METODO DE AGREGAR PRODUCTO Y DEMAS.
                        } else if (opcionConProductos == 2) {
                            //BUSCAMOS EL PRODUCTO POR ALGUN CAMPO.
                            pedidosYa.eliminarProductos(null, null); //PASAMOS ALGO POR PARAMETRO EN BASE A LO INGRESADO.
                        } else {
                            System.out.println("No ha seleccionado ningun caso. Regresando al menu principal.");
                        }
                    }

                    case 8 -> {
                        System.out.println("Ingrese el DNI del usuario que desea eliminar (Si ingreso por equivocacion presione cero): ");
                        String dni = scanner.nextLine();
                        if (dni.equals("0")) {
                            try {
                                flag = pedidosYa.eliminarUnUsuarioComoAdmin(dni);
                                if (flag) System.out.println("La eliminacion se ha realizado con exito.");
                                else System.out.println("La eliminacion no se ha realizado con exito.");
                            } catch (NullPointerException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }

                    case 9 -> {
                        if (administrador.getTarjeta().isActiva()) System.out.println("Su tarjeta se encuentra cargada, si desea modificarla vaya al menu 3 para modificar su perfil");
                        else{
                            flag = administrador.getTarjeta().cargarTarjeta(scanner);
                            if (flag){
                                System.out.println("Tarjeta cargada correctamente!");
                                Set<Administrador> administradorSet = pedidosYa.extraerAdministradoresFromJSON(PedidosYa.ARCHIVO_ADMINISTRADORES);
                                Administrador adm = pedidosYa.buscarAdministradorPorDNI(administrador.getDni(), administradorSet);
                                if (adm != null) {
                                    adm.setTarjeta(administrador.getTarjeta());
                                }
                                pedidosYa.exportarAdministradoresToJSON(PedidosYa.ARCHIVO_ADMINISTRADORES, administradorSet);
                                //PRIMERO LO QUE TENGO QUE HACER ES OBTENER EL SET DE ADMINISTRADORES DEL ARCHIVO, MODIFICAR LA TARJETA DEL QUE TENGA EL MISMO DNI, Y LUEGO ESO VOLVER  A MODIFICARLO EN EL ARCHIVO.
                            }
                            else System.out.println("Tarjeta cargada incorrectamente!");
                        }
                    }

                    case 10 -> control='n';

                    default -> throw new CasoInexistenteException();
                }
            } catch (InputMismatchException e) {
                System.out.println("LO INGRESADO NO FUE UN NUMERO. REGRESANDO AL MENU PRINCIPAL...");
            }
        }while (control == 's');

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