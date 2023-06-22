import Exceptions.CasoInexistenteException;
import Persona.Persona;
import Persona.Usuario;
import Persona.Administrador;
import model.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        PedidosYa pedidosYa = new PedidosYa();

        pedidosYa.cargarListaDeEmpresas();
        Scanner scanner = new Scanner(System.in);


        Persona persona = menuDeSeleccionDeModoDeAcceso(scanner, pedidosYa);

        if (persona instanceof Administrador){
            menuPrincipalAdmin(scanner, (Administrador) persona, pedidosYa);
            //(Scanner scanner, Administrador administrador, PedidosYa pedidosYa)
        } else{
            menuPrincipalUsuario(scanner, (Usuario) persona, pedidosYa);
        }

        scanner.close();
    }

    public static Persona menuDeSeleccionDeModoDeAcceso (Scanner scanner, PedidosYa pedidosYa) throws CasoInexistenteException{
        Usuario usuarioRetornar = null;
        Administrador administradorRetornar = null;
        do {
        System.out.println("En que modo va a querer acceder?");
        System.out.println("(1) Administrador.");
        System.out.println("(2) Usuario.");

            try {
                int opcion = scanner.nextInt();
                switch (opcion) {

                    ///MODO ADMIN
                    case 1 -> {
                        System.out.println("(1) Iniciar Sesion >>");
                        System.out.println("(2) Registrarse >>");
                        int caso = scanner.nextInt();

                        switch (caso) {
                            case 1 -> administradorRetornar = pedidosYa.iniciarSesionComoAdmin(scanner);
                            case 2 -> administradorRetornar = pedidosYa.registroDeCuentaDeAdmin(scanner);
                            default -> throw new CasoInexistenteException();
                        }

                        System.out.println("ELEGIR TU ZONA ACTUAL: ");
                        System.out.println(Arrays.toString(Zonas.values()));
                        boolean flag = true;
                        Zonas elegida = null;
                        do {
                            try {
                                String zona1= scanner.nextLine();
                                elegida = Zonas.valueOf(zona1.toUpperCase());
                                flag = false;
                            } catch (IllegalArgumentException e) {
                                System.out.println("La zona ingresada no es valida. Intente nuevamente.");
                            }
                        } while (flag);
                        administradorRetornar.establecerZonaActual(elegida);////se agrega a la zona elegida
                        return administradorRetornar;
                    }

                    ///MODO USUARIO
                    case 2 -> {
                        System.out.println("(1) Iniciar Sesion >>");
                        System.out.println("(2) Registrarse >>");
                        int casoUser = scanner.nextInt();
                        switch (casoUser) {
                            case 1 -> //AGREGAR ELEGIR LA ZONA ACTUAL PARA MANEJAR ESO EN EL MENU. ////////////////////////////////////////////////
                                    usuarioRetornar = pedidosYa.iniciarSesionComoUsuario(scanner);
                            case 2 -> usuarioRetornar = pedidosYa.registroDeCuentaDeUsuario(scanner);
                            default -> throw new CasoInexistenteException();
                        }

                        System.out.println("ELEGIR UNA ZONA: ");
                        boolean flag = true;
                        Zonas elegida = null;
                        System.out.println(Arrays.toString(Zonas.values()));
                        do {
                            try {
                                String zona1 = scanner.nextLine();
                                elegida=Zonas.valueOf(zona1.toUpperCase());
                                flag = false;
                            } catch (IllegalArgumentException e) {
                                System.out.println("La zona ingresada no es valida. Intente nuevamente.");
                            }
                        } while (flag);
                        usuarioRetornar.setZonaActual(elegida);
                        return usuarioRetornar;
                    }
                    ////////////////////////////////////////////////////////////////////
                    default -> throw new CasoInexistenteException();
                }


            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("LO INGRESADO NO FUE UN NUMERO. CERRANDO EL PROGRAMA...");

            } catch (CasoInexistenteException E) {
                scanner.nextLine();
                System.out.println(E.getMessage());
            }
        }while (usuarioRetornar==null || administradorRetornar==null);
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
            System.out.println("(7) Tus zonas"); ///AGREGAR, ELIMINAR, VER ZONAS y ELEGIR  ZONA ACTUAL
            System.out.println("(8) Tarjeta");
            System.out.println("(9) Salir >>"); //NO ES NECESARIO ACTUALIZAR ARCHIVO

            try {
                opcion = scanner.nextInt();
                switch (opcion) {
                    case 1 -> {
                        System.out.println("<< PERFIL >>");
                        System.out.println(usuario.toString());
                        usuario.getTarjeta().mostrarTarjeta();
                        System.out.println("Ingrese enter para continuar...");
                        scanner.nextLine();
                        scanner.nextLine();
                    }
                    case 2 -> {
                        System.out.println("<< HISTORIAL DE COMPRAS >>");

                        if(usuario.getHistorialDeCompras().getPedidos().isEmpty()){
                            System.out.println("HISTORIAL DE COMPRAS VACIO");
                        } else {
                            Set <Usuario> usuarios = pedidosYa.extraerUsuariosFromJSON(PedidosYa.ARCHIVO_USUARIOS);
                            usuario = pedidosYa.buscarUserPorDNI(usuario.getDni(), usuarios);

                            usuario.getHistorialDeCompras().listarHistorial();
                            System.out.println("////////////////////////////////////////////////////////////");
                            System.out.println("Ingrese enter para continuar...");
                            scanner.nextLine();
                            scanner.nextLine();
                        }

                    }

                    case 3 -> menuDeCarrito(scanner, pedidosYa, usuario);

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
                        usuario = pedidosYa.buscarUserPorDNI(usuario.getDni(), pedidosYa.getUsuarios());
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
                                aux = pedidosYa.buscarUserPorDNI(aux.getDni(), pedidosYa.getUsuarios());

                            } else System.out.println("No se ha podido limpiar el historial de busqueda.");
                        }
                    }

                    case 6 -> {
                        if (usuario.getTarjeta().isActiva()) System.out.println("Su tarjeta se encuentra cargada, si desea modificarla vaya al menu 4 para modificar su perfil");
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
                    case 7 -> {
                        do {
                            System.out.println("TUS ZONAS:"); //cambiarenmenuadministradorzonaactual
                            System.out.println("(1) Cambiar zona actual");
                            System.out.println("(2) Salir");
                            int opcionZona = scanner.nextInt();
                            scanner.nextLine();

                            switch (opcionZona) {
                                case 1 -> {
                                    System.out.println("Tu zona actual: " + usuario.getZonaActual());
                                    System.out.println("Ingrese el nombre de tu nueva zona actual:");
                                    System.out.println(Arrays.toString(Zonas.values()));
                                    String zonaNueva =scanner.nextLine();
                                    try {
                                        Zonas zona = Zonas.valueOf(zonaNueva.toUpperCase());
                                        usuario.setZonaActual(zona);
                                    }catch (IllegalArgumentException e){
                                        System.out.println("Zona inexistente. Volviendo al menu principal.");
                                    }
                                }
                                case 2 -> System.out.println("Volviendo al menu principal...");
                            }

                            System.out.println("Desea continuar? s/n");
                    }while (scanner.next().charAt(0) != 'n');
                    }

                    case 8 -> {
                        do{
                            System.out.println("Tarjeta actual");
                            System.out.println("(1) Ver datos de tarjeta");
                            System.out.println("(2) Agregar saldo");
                            System.out.println("(3) Salir");
                            int decision = scanner.nextInt();
                            scanner.nextLine();

                            usuario = pedidosYa.buscarUserPorDNI(usuario.getDni(), pedidosYa.getUsuarios());

                                switch (decision){
                                    case 1 -> {
                                        System.out.println(usuario.getTarjeta().mostrarTarjeta());
                                        System.out.println("Ingrese enter para continuar.....");
                                        scanner.nextLine();
                                        break;
                                    }

                                    case 2 -> {
                                        Set<Usuario> usuarioSet = pedidosYa.extraerUsuariosFromJSON(PedidosYa.ARCHIVO_USUARIOS);

                                        System.out.println("Saldo actual: $" + usuario.getTarjeta().getSaldo());
                                        System.out.println("Ingrese el saldo que desea agregar");

                                        usuario.agregarDinero(scanner.nextInt());
                                        System.out.println("Nuevo saldo: $" + usuario.getTarjeta().getSaldo());

                                        pedidosYa.exportarUsuariosToJSON(PedidosYa.ARCHIVO_USUARIOS, usuarioSet);
                                        break;
                                    }
                                }

                                System.out.println("Desea continuar? s/n");
                        }while (scanner.next().charAt(0) != 'n');


                    }

                    case 9 -> control = 'n';

                    default -> throw new CasoInexistenteException();
                }
            } catch (InputMismatchException e) {
                System.out.println("LO INGRESADO NO FUE UN NUMERO. VOLVIENDO AL MENU PRINCIPAL...");
            }
        }while (control == 's');
    }

    public static void menuPrincipalAdmin(Scanner scanner, Administrador administrador, PedidosYa pedidosYa) throws CasoInexistenteException{
        int opcion=0; char control = 's'; boolean flag = false;
        do {
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

            try {
                opcion = scanner.nextInt();

                switch (opcion) {
                    case 1 -> {
                        System.out.println("<< PERFIL >>");
                        System.out.println(administrador.toString());
                        administrador.getTarjeta().mostrarTarjeta();
                        System.out.println("Ingrese enter para continuar...");
                        scanner.nextLine();
                        scanner.nextLine();
                    }

                    case 2 -> {
                        System.out.println("<< HISTORIAL DE COMPRAS >>");
                        if(administrador.getHistorialDeCompras().getPedidos().isEmpty()){
                            System.out.println("HISTORIAL DE COMPRAS VACIO");
                        } else{
                            administrador.getHistorialDeCompras().listarHistorial();
                            System.out.println("////////////////////////////////////////////////////////////");
                            System.out.println("Ingrese enter para continuar...");
                            scanner.nextLine();
                            scanner.nextLine();
                        }


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

                    case 4 -> menuDeCarrito(scanner, pedidosYa, administrador);

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
                                    flag = pedidosYa.modificarNroTelefonoDeAdministrador(scanner);
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

                            administrador=pedidosYa.buscarAdministradorPorDNI(administrador.getDni(), pedidosYa.getAdministradores());
                        } catch (CasoInexistenteException e) {
                            System.out.println(e.getMessage());
                        }
                    }

                    case 6 -> {
                        System.out.println("ESTAS SON LAS EMPRESAS DISPONIBLES:");

                        System.out.println("Que desea hacer? ");
                        System.out.println("(1) Agregar una empresa >>");
                        System.out.println("(2) Eliminar una empresa >>");

                        int opcionConEmpresas = scanner.nextInt();

                        if (opcionConEmpresas == 1) {
                            //METODO DE AGREGAR UNA EMPRESA CARGANDO POR TECLADO.
                            pedidosYa.agregarEmpresa(pedidosYa.cargarEmpresaPorTeclado(scanner));
                            pedidosYa.exportarEmpresasToJSON(PedidosYa.ARCHIVO_EMPRESAS);
                        } else if (opcionConEmpresas == 2) {
                            //METODO DE BUSCAR UNA EMPRESA POR ALGUN CAMPO, POR EJ NOMBRE Y LA ELIMINAMOS.
                            pedidosYa.eliminarEmpresa(pedidosYa.retornarUnaEmpresa(scanner)); //LE PASAMOS ALGO POR PARAMETRO.
                            pedidosYa.exportarEmpresasToJSON(PedidosYa.ARCHIVO_EMPRESAS);
                        } else {
                            System.out.println("No ha seleccionado ningun caso. Regresando al menu principal.");
                        }

                        System.out.println("ASI HAN QUEDADO LAS EMPRESAS LUEGO DE LA MODIFICACION");
                        pedidosYa.mostrarEmpresasSoloNombre(pedidosYa.getListaDeEmpresas());

                    }

                    case 7 -> {
                        System.out.println("Que desea hacer? ");
                        System.out.println("(1) Agregar un tipo de producto a una empresa >>");
                        System.out.println("(2) Eliminar un producto >>");

                        int opcionConProductos = scanner.nextInt();
                        if (opcionConProductos == 1) {
                            //LLAMAMOS METODO DE AGREGAR PRODUCTO Y DEMAS.
                            Empresa A=pedidosYa.retornarUnaEmpresa(scanner);
                            System.out.println("Tipos de producto disponibles: " + Arrays.toString(TipoDeProductos.values()));
                            System.out.println("Ingrese el tipo de producto que desea cargar");
                            String tipo= scanner.nextLine();
                            TipoDeProductos producto= TipoDeProductos.valueOf(tipo.toUpperCase());

                            Empresa aux = pedidosYa.cargarUnTipoProducto(A,producto);
                            System.out.println("La empresa " + aux.getNombre() + " ahora posee los siguientes productos: \n" + aux.getProductosEmpresa());
                            pedidosYa.exportarEmpresasToJSON(PedidosYa.ARCHIVO_EMPRESAS);

                        } else if (opcionConProductos == 2) {
                            //BUSCAMOS EL PRODUCTO POR ALGUN CAMPO.

                            Empresa empresa=pedidosYa.retornarUnaEmpresa(scanner);
                            System.out.println("Productos de la empresa " + empresa.getNombre() + "= " + empresa.getProductosEmpresa());
                            System.out.println("Ingrese el tipo de producto que desea eliminar");
                            String tipo= scanner.nextLine();
                            TipoDeProductos producto= TipoDeProductos.valueOf(tipo.toUpperCase());

                            pedidosYa.eliminarProductos(producto, empresa); //PASAMOS POR PARAMETRO EL TIPO DE PRODUCTO QUE DESEA ELIMINAR Y EN QUE EMPRESA
                            System.out.println("La empresa " + empresa.getNombre() + " ahora posee los siguientes productos: \n" + empresa.getProductosEmpresa());
                            pedidosYa.exportarEmpresasToJSON(PedidosYa.ARCHIVO_EMPRESAS);
                        } else {
                            System.out.println("No ha seleccionado ningun caso. Regresando al menu principal.");
                        }
                    }

                    case 8 -> {
                        System.out.println("Ingrese el DNI del usuario que desea eliminar (Si ingreso por equivocacion presione cero): ");
                        scanner.nextLine();
                        String dni = scanner.nextLine();
                        if (!dni.equals("0")) {
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
                        if (administrador.getTarjeta().isActiva()) System.out.println("Su tarjeta se encuentra cargada, si desea modificarla vaya al menu 5 para modificar su perfil");
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

    public static void clearConsole() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    private static void menuDeCarrito(Scanner scanner, PedidosYa pedidosYa, Usuario usuario){ ///CARRITO< HISTORIAL< TARJETA< PERTENECENN A USUARIO.......
        int decision=-1;
        Empresa elegida = pedidosYa.buscarEmpresaConMetodoElegido(scanner, usuario.getZonaActual());

        Set<Usuario> usuarioSet = pedidosYa.extraerUsuariosFromJSON(PedidosYa.ARCHIVO_USUARIOS);
        usuario = pedidosYa.buscarUserPorDNI(usuario.getDni(), usuarioSet);

        usuario.setCarrito(new Carrito());
        usuario.getCarrito().setVendedor(elegida);


        do {
            System.out.println("Que desea hacer?");
            System.out.println("(1) Agregar producto al carrito");
            System.out.println("(2) Eliminar producto del carrito");
            System.out.println("(3) Editar cantidad del producto");
            System.out.println("(4) Agregar cupon");
            System.out.println("(5) Ir a pagar");
            System.out.println("(6) Ver carrito");
            System.out.println("(7) salir");

            decision = scanner.nextInt();

            
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

                        usuario.getCarrito().agregarProductoAlCarrito(elegida, prodAux);
                    } catch (RuntimeException e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    usuario.getCarrito().mostrarProductos();

                    System.out.println("Ingrese el numero del producto que desea eliminar");
                    usuario.getCarrito().eliminarProductoDelCarrito(scanner.nextInt());
                    break;

                case 3:
                    usuario.getCarrito().mostrarProductos();

                    System.out.println("Ingrese el numero del producto que desea editar");
                    int pos = scanner.nextInt();
                    System.out.println("Producto elegido: " + usuario.getCarrito().getProductos().get(pos));

                    System.out.println("Ingrese la nueva cantidad del producto elegido");
                    usuario.getCarrito().editarCantidadDeProducto(scanner.nextInt(), pos);
                    break;

                case 4:
                    System.out.println("Cupones disponibles: " +  usuario.getCarrito().getVendedor().getListaDeCupones());

                    System.out.println("Ingresa un cupon de 6 caracteres: ");
                    scanner.nextLine();
                    try{
                        pedidosYa.agregarDescuento(scanner.nextLine(), usuario.getCarrito());
                    }catch (RuntimeException e){
                        System.out.println(e.getMessage());
                    }

                    break;

                case 5:
                    System.out.println("Pagar carrito...................");
                    System.out.println("Desea enviarle una nota al repartidor? s/n");

                    if (scanner.next().charAt(0) == 's') {
                        System.out.println("Ingrese la nota que desea enviarle al repartidor:");

                        scanner.nextLine();
                        usuario.getCarrito().setNota(scanner.nextLine());
                    }


                    usuario.getCarrito().setMontoTotal(usuario.getCarrito().calcularMontoTotalDeLaCompra());
                    usuario.getCarrito().setDestino(usuario.getZonaActual());

                    usuario.getCarrito().pagarCarrito(usuario.getHistorialDeCompras(), usuario.getTarjeta(), scanner); ////SUMAR AL MONTO TOTAL EL COSTO DE ENVIO


                    pedidosYa.exportarUsuariosToJSON(PedidosYa.ARCHIVO_USUARIOS, usuarioSet);
                    pedidosYa.setUsuarios(usuarioSet);                     ///PERSISTENCIA EN ARCHIVO EL HISTORIAL DE COMPRAS
                    usuario = pedidosYa.buscarUserPorDNI(usuario.getDni(), pedidosYa.getUsuarios());

                    System.out.println("Pulse enter para salir....");
                    scanner.nextLine();
                    scanner.nextLine();

                    System.out.println("Saliendo.....");
                    decision=7;
                    break;
                case 6:
                    usuario.getCarrito().listarCarrito();
                    System.out.println("Pulse enter para continuar....");
                    scanner.nextLine();
                    scanner.nextLine();
                    break;
            }
        }while (decision != 7);

        usuario.setCarrito(new Carrito());
    }
////////////////////////////////////////////////////////////////////////////////////////////////////
    private static void menuDeCarrito(Scanner scanner, PedidosYa pedidosYa, Administrador administrador){ ///CARRITO< HISTORIAL< TARJETA< PERTENECENN A USUARIO.......
        int decision = -1;
        Empresa elegida = pedidosYa.buscarEmpresaConMetodoElegido(scanner, administrador.getZonaActual());
        if (elegida!=null){

            administrador.getCarrito().setVendedor(elegida);
            do {
                System.out.println("Que desea hacer?");
                System.out.println("(1) Agregar producto al carrito");
                System.out.println("(2) Eliminar producto del carrito");
                System.out.println("(3) Editar cantidad del producto");
                System.out.println("(4) Agregar cupon");
                System.out.println("(5) Ir a pagar");
                System.out.println("(6) Ver carrito");
                System.out.println("(7) salir");

                decision = scanner.nextInt();

                Carrito carritoAdministrador = administrador.getCarrito();
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

                            carritoAdministrador.agregarProductoAlCarrito(elegida, prodAux);
                        } catch (RuntimeException e){
                            System.out.println(e.getMessage());
                        }
                        break;

                    case 2:
                        carritoAdministrador.mostrarProductos();

                        System.out.println("Ingrese el numero del producto que desea eliminar o un numero mayor a los mostrados para salir...");
                        int posEliminar = scanner.nextInt();

                        if(posEliminar > carritoAdministrador.getProductos().size()){
                            break;
                        }

                        try {
                            carritoAdministrador.eliminarProductoDelCarrito(posEliminar);
                        }catch (RuntimeException e){
                            System.out.println(e.getMessage());
                        }

                        break;

                    case 3:
                        carritoAdministrador.mostrarProductos();

                        System.out.println("Ingrese el numero del producto que desea editar");
                        int pos = scanner.nextInt();
                        System.out.println("Producto elegido: " + carritoAdministrador.getProductos().get(pos));

                        System.out.println("Ingrese la nueva cantidad del producto elegido");
                        carritoAdministrador.editarCantidadDeProducto(scanner.nextInt(), pos);
                        break;

                    case 4:
                        System.out.println("Cupones disponibles: " + carritoAdministrador.getVendedor().getListaDeCupones());

                        System.out.println("Ingresa un cupon de 6 caracteres: ");
                        scanner.nextLine();

                        try{
                            pedidosYa.agregarDescuento(scanner.nextLine(), carritoAdministrador);
                        }catch (RuntimeException e){
                            System.out.println(e.getMessage());
                        }
                        break;

                    case 5:
                        System.out.println("Pagar carrito...................");
                        System.out.println("Desea enviarle una nota al repartidor? s/n");

                        if (scanner.next().charAt(0) == 's') {
                            System.out.println("Ingrese la nota que desea enviarle al repartidor:");
                            scanner.nextLine();
                            String nota = scanner.nextLine();
                            carritoAdministrador.setNota(nota);
                        }

                        carritoAdministrador.setMontoTotal(carritoAdministrador.calcularMontoTotalDeLaCompra());
                        carritoAdministrador.setDestino(administrador.getZonaActual());

                        carritoAdministrador.pagarCarrito(administrador.getHistorialDeCompras(), administrador.getTarjeta(), scanner);

                        System.out.println("Pulse enter para continuar....");
                        scanner.nextLine();
                        scanner.nextLine();

                        System.out.println("Saliendo.....");
                        decision=7;
                        break;
                    case 6:
                        carritoAdministrador.listarCarrito();
                        System.out.println("Pulse enter para continuar....");
                        scanner.nextLine();
                        scanner.nextLine();
                        break;
                }
            }while (decision != 7);

            administrador.setCarrito(new Carrito());
        }
    }
}