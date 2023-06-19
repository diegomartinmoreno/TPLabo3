import Persona.Persona;
import model.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        PedidosYa pedidosYa = new PedidosYa();
        Carrito carrito = new Carrito();
        HistorialDeCompras historialDeCompras = new HistorialDeCompras();
        Scanner scanner = new Scanner(System.in);
        Tarjeta tarjeta = new Tarjeta();

        pedidosYa.cargarListaDeEmpresas();
       /// pedidosYa.mostrarEmpresas();
        Empresa A= pedidosYa.buscarEmpresaConMetodoElegido(scanner);
        System.out.println(A);


        pedidosYa.mostrarEmpresas();
        pedidosYa.cargarListaDeEmpresas();

        //mostrarMenuDeCarrito(scanner, pedidosYa, carrito, historialDeCompras, tarjeta);

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

    public static void menuPrincipalUsuario(){

    }

    public static void menuPrincipalAdmin(){

    }

    public static void mostrarMenuDeCarrito(Scanner scanner, PedidosYa pedidosYa, Carrito carrito, HistorialDeCompras historialDeCompras, Tarjeta tarjeta){
        Empresa elegida = pedidosYa.buscarEmpresaSegunQueQuiereComer(scanner);
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

                    elegida.mostrarEmpresa();

                    System.out.println("Ingrese el id del producto que desea llevar:");
                    Producto prodAux = elegida.buscarProductoPorID(scanner.nextInt());
                    System.out.println("Producto elegido: " + prodAux);


                    System.out.println("Cuanto desea llevar del producto?");
                    prodAux.setCantidadPedido(scanner.nextInt());

                    carrito.agregarProductoAlCarrito(elegida, prodAux);

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
                    break;

                case 6:
                    carrito.listarCarrito();
                    break;
            }

            System.out.println("Desea continuar? s/n");
        }while (scanner.next().charAt(0) != 'n');
    }

}