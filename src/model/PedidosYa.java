package model;
import Exceptions.IntentosMaximosDeInicioSesionAlcanzadoException;
import Exceptions.MenorDeEdadException;
import Persona.Persona;
import Persona.Usuario;
import Persona.Administrador;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static model.TipoDeProductos.*;
import static model.Zonas.*;

public class PedidosYa {

    private List<Empresa>listaDeEmpresas;
    private Set <Usuario> usuarios;
    private Set <Administrador> administradores;
    public static final String ARCHIVO_USUARIOS = "Users.json";
    public static final int CANTIDAD_INTENTOS_INICIO_SESION = 3;

    /*
        En esta clase decidimos manejarlo por separado a los usuarios y administradores ya que interactuan en ambitos distintos,
        el admin tiene acceso a todo y el user a lo que pueda ver del programa y no vimos conveniente tener que instanciar los metodos
        para verificar que sea una u otra instancia.

    */

    public PedidosYa() {
        listaDeEmpresas = new ArrayList<>();
        usuarios = new HashSet<>();
        administradores = new HashSet<>();
    }

    public PedidosYa(List<Empresa> listaDeEmpresas, Set<Usuario> usuarios) {
        this.listaDeEmpresas = listaDeEmpresas;
        this.usuarios = usuarios;
    }

    public List<Empresa> getListaDeEmpresas() {
        return listaDeEmpresas;
    }
    public void setListaDeEmpresas(List<Empresa> listaDeEmpresas) {
        this.listaDeEmpresas = listaDeEmpresas;
    }

    public Set<Usuario> getUsuarios() {
        return usuarios;
    }
    public void setUsuarios(Set<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Set<Administrador> getAdministradores() {
        return administradores;
    }
    public void setAdministradores(Set<Administrador> administradores) {
        this.administradores = administradores;
    }

    public void exportarUsuariosToJSON (String path, Set <Usuario> usuarios){
        File file = new File(path);
        ObjectMapper mapper = new ObjectMapper();

        try{
            mapper.writeValue(file, usuarios);
        }catch (IOException e){
            System.out.println("Error en la escritura del archivo.");
        }
    }

    public Set<Usuario> extraerUsuariosFromJSON (String path){
        File file = new File(path);
        ObjectMapper mapper = new ObjectMapper();
        Set<Usuario>usuarioHashSet = new HashSet<>();

        try{
            Usuario[] usuariosArray = mapper.readValue(file, Usuario[].class);
            usuarioHashSet.addAll(Arrays.asList(usuariosArray));
        }catch (IOException e){
            System.out.println("Error en la lectura del archivo.");
            System.out.println(e.getMessage());
        }
        return usuarioHashSet;
    }



    private boolean verificarContraseniaExistente (String cadenaArevisar, Set <Usuario> users){
        boolean flag = true;

        for (Usuario user : users) {
            if (user.getContrasenia().equals(cadenaArevisar))
                flag = false;
        }

        return flag;
    }

    public Usuario registroDeCuenta (Scanner scanner) throws MenorDeEdadException {
        Usuario usuario = new Usuario();
        String cadenaAux =null, contrasenia = null;
        boolean flag=false;
        Set <Usuario> usuarioHashSet = extraerUsuariosFromJSON(ARCHIVO_USUARIOS);

        System.out.println("Bienvenido! Ingrese los datos correspondientes >> ");

        do {
            System.out.println("1) Ingrese su nombre: ");
            cadenaAux = scanner.nextLine();
            try {
                flag = Persona.verificarEsLetra(cadenaAux);
                if (!flag)
                    System.out.println("Recuerde que su nombre son solo letras!.");
            }catch (NullPointerException e){
                System.out.println(e.getMessage());
            }
        }while (!flag);

        usuario.setNombre(cadenaAux);

        do {
            System.out.println("2) Ingrese su apellido: ");
            cadenaAux = scanner.nextLine();
            try {
                flag = Persona.verificarEsLetra(cadenaAux);
                if (!flag)
                    System.out.println("Recuerde que su apellido son solo letras!.");
            }catch (NullPointerException e){
                System.out.println(e.getMessage());
            }
        }while (!flag);

        usuario.setApellido(cadenaAux);

        System.out.println("3) Ingrese su edad");
        int edad = scanner.nextInt();
        try {
            flag = Persona.verificarEdad(edad);
            if (!flag)
                throw new MenorDeEdadException();
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }

        usuario.setEdad(edad);

        do {
            scanner.nextLine();
            System.out.println("4) Ingrese su DNI: ");
            cadenaAux = scanner.nextLine();
            try{
                flag = Persona.verificarEsNumero(cadenaAux);
                if (flag) {
                    flag = Persona.verificarLongitudDNI(cadenaAux);
                    if (!flag)
                        System.out.println("Error en la longitud del DNI!. Son 8 digitos.");
                }else
                    System.out.println("Error en el dni, no son todos digitos.");
            }catch (NullPointerException e){
                System.out.println(e.getMessage());
            }
        }while (!flag);

        usuario.setDni(cadenaAux);

        do{
            System.out.println("5) Ingrese su numero telefonico: ");
            cadenaAux = scanner.nextLine();
            try {
                flag = Persona.verificarEsNumero(cadenaAux);
                if (flag) {
                    flag = Persona.verificarCodigoDeArea(cadenaAux); //NO HAGO TRY CATCH DE ESTE METODO PQ EN TEORIA VERIFICAR COD AREA SI NO LANZO EXCEPCION, ES QUE SON TODOS DIGITOS.
                    if (!flag)
                        System.out.println("Error en el codigo de area del telefono!.");
                } else
                    System.out.println("Error en el numero telefonico, no son todos digitos.");
            }catch (NullPointerException e){
                System.out.println(e.getMessage());
            }
        }while (!flag);

        usuario.setNroDeTelefono(cadenaAux);

        System.out.println("6) Ingrese su email: ");
        usuario.setEmail(scanner.nextLine());

        do {
            System.out.println("7) Finalmente ingrese su contrasenia (debera recordarla): ");
            contrasenia = scanner.nextLine();
            try {
                flag = Usuario.verificarContraseniaSegura(contrasenia);
                if (flag){
                    flag = verificarContraseniaExistente(contrasenia, usuarioHashSet);
                    if (!flag)
                        System.out.println("Contrasenia en uso. Elija otra.");
                }else{
                    System.out.println("Contrasenia no segura. Elija otra.");
                }
            }catch (NullPointerException e){
                System.out.println(e.getMessage());
            }
        } while (!flag);

        usuario.setContrasenia(contrasenia);

        int decision=0;

        System.out.println("Desea anadir la tarjeta ahora o luego?. ");
        System.out.println("[1] Ahora.\n[2] Mas tarde.");
        decision= scanner.nextInt();

        if(decision == 1){
            usuario.getTarjeta().cargarTarjeta(scanner);
        }else if (decision == 2){
            System.out.println("Cuando desee comprar, debera cargar su tarjeta.");
        }else{
            System.out.println("Se equivoco de boton, no se cargara ningun dato de la tarjeta. Cuando desee comprar, debera cargar su tarjeta.");
        }

        usuarioHashSet.add(usuario);
        exportarUsuariosToJSON(ARCHIVO_USUARIOS, usuarioHashSet);

        return usuario;
    }

    private boolean verificarInicioDeSesion (String email, String contrasenia, Set <Usuario> usuarios){
        boolean flag=false;

        for (Usuario user : usuarios ) {
            if (user.getContrasenia().equals(contrasenia) && user.getEmail().equals(email))
                flag = true;
        }
        return flag;
    }

    private Usuario retornarUserEnInicioSesion (String email, String contrasenia, Set <Usuario> usuarios){
        Usuario user = null;
        for (Usuario usuario : usuarios ) {
            if (usuario.getContrasenia().equals(contrasenia) && usuario.getEmail().equals(email))
                user = usuario;
        }
        return user;
    }

    public Usuario iniciarSesion (Scanner scanner) throws IntentosMaximosDeInicioSesionAlcanzadoException {
        String email = null, contrasenia = null; //VARIABLES PARA GUARDAR LOS DATOS IMPORTANTE POR SEPARADO.
        boolean flag=false; //VARIABLE BOOLEANA PARA EL MANEJO DEL BUCLE DO-WHILE.
        Usuario usuario = null; //DECLARO UN USUARIO, PARA QUE SI LO INGRESADO ES CORRECTO, EN EL MAIN ESTE COMO USUARIO ACTUAL.
        int i=0; //REPRESENTA LOS INTENTOS DE INICIAR SESION.

        System.out.println("Bienvenido! Ingrese los datos correspondientes para iniciar sesion >> ");
        Set <Usuario> usuarioHashSet = extraerUsuariosFromJSON(ARCHIVO_USUARIOS); //OBTENGO LOS DATOS PARA VERIFICAR LUEGO CON LO INGRESADO SI COINCIDE.

        do {
            System.out.println("1) Ingrese el email: ");
            email = scanner.nextLine();
            System.out.println("2) Ingrese su contrasenia: ");
            contrasenia = scanner.nextLine();

            flag = verificarInicioDeSesion(email, contrasenia, usuarioHashSet);
            if (!flag){
                System.out.println("\nError en los datos ingresados!.");
                i++;
            }else{
                usuario = retornarUserEnInicioSesion(email, contrasenia, usuarioHashSet); //OBTENGO EL USUARIO PARA RETORNARLO.
            }

            if (i==CANTIDAD_INTENTOS_INICIO_SESION) throw new IntentosMaximosDeInicioSesionAlcanzadoException();

        }while (i<CANTIDAD_INTENTOS_INICIO_SESION && flag==false);

        return usuario;
    }

    public Empresa buscarEmpresaSegunNombre(String empresa) throws NullPointerException{
        if (empresa==null) throw new NullPointerException("Error! La empresa no puede ser nula.//***");

        for(Empresa auxiliar : listaDeEmpresas){
            if(empresa.equals(auxiliar.getNombre())){
                return auxiliar;
            }
        }

        return null;
    }

    public void mostrarEmpresas(){
        for(Empresa empresa : listaDeEmpresas){
            empresa.mostrarEmpresa();
        }
    }

    public void cargarListaDeEmpresas(){ ///Los precios varian, hay empresas que no cobran envio al estar a cargo de la misma.
        listaDeEmpresas.add(new Empresa("La Musa", crearListaDeProductos(Set.of(PIZZA, BEBIDAS)), Set.of(CENTRO, RUMENCO),250));

        listaDeEmpresas.add(new Empresa("Hamburgo", crearListaDeProductos(Set.of(HAMBURGUESAS, BEBIDAS)), Set.of(BOSQUE, INDEPENDENCIA),250));

        listaDeEmpresas.add(new Empresa("Konichiwa la mejor comida japonesa papi", crearListaDeProductos(Set.of(SUSHI, BEBIDAS)), Set.of(LOS_TRONCOS, COLINAS),250));

    }



    private LinkedHashMap<TipoDeProductos, HashSet<Producto>> crearListaDeProductos(Set<TipoDeProductos> tipoDeProductos){  ///LE PASO UN ARRAYLIST CON LOS TIPOS DE PRODUCTOS QUE POSEE LA EMPRESA
        LinkedHashMap<TipoDeProductos, HashSet<Producto>> productosTotal = new LinkedHashMap<>();

        for (TipoDeProductos tipo : tipoDeProductos){ ///HAMBURGUESA, MILANESA, PAPASFRITAS
            productosTotal.put(tipo, crearHashSetSegunTipoDeProducto(tipo));
        }

        return productosTotal;
    }

    private HashSet<Producto> crearHashSetSegunTipoDeProducto(TipoDeProductos tipoDeProducto){
        HashSet<Producto> listaDeProductos = new HashSet<>();

        /////////////HACER SWITCH

        if(tipoDeProducto.equals(BEBIDAS)){
            listaDeProductos.add(new Producto("cocucha", "bebida", "coca con banda de azucar", 23, "asda"));

        } else if (tipoDeProducto.equals(CARNES)) {

        } else if (tipoDeProducto.equals(EMPANADAS)) {

        } else if (tipoDeProducto.equals(ENSALADAS)) {

        }  else if (tipoDeProducto.equals(HAMBURGUESAS)) {
            listaDeProductos.add(new Producto("doble con queso", "hamburjesita", "alta burger", 111, "aaa"));

        } else if (tipoDeProducto.equals(HELADOS)) {

        } else if (tipoDeProducto.equals(LOMITOS)) {

        } else if (tipoDeProducto.equals(MILANESAS)) {

        } else if (tipoDeProducto.equals(PAPAS)) {

        } else if (tipoDeProducto.equals(PARRILLA)) {

        } else if (tipoDeProducto.equals(PASTAS)) {

        } else if (tipoDeProducto.equals(PIZZA)) {
            listaDeProductos.add(new Producto("Pizza con jyq", "Pizza", "RICA LA PISA", 555, "La musa PERO ES INCORRECTO"));

        } else if (tipoDeProducto.equals(POLLO)) {

        } else if (tipoDeProducto.equals(POSTRES)) {

        } else if (tipoDeProducto.equals(SUSHI)) {
            listaDeProductos.add(new Producto("Sushi", "suhi", "wachiwa", 22354, "asd"));

        } else if (tipoDeProducto.equals(KIOSCO)) {

        }

        return listaDeProductos;
    }

    public void PasarListaDeProductosAHashMapDeEmpresas (){ ///Primero creo una lista de productos, en base al nombre de la empresa lo acomodo.
        ///El primer atributo del hashmap es el tipo, el cual es un atributo del producto, el segundo ya es el producto especifico, de ese modo se los encuentra.










        /* public int buscarPosEmpresaSegunNombre(String nombre){
        int pos=-1;
        for (model.Empresa empresa1 : listaDeEmpresas){
            if (empresa1.getNombre().equals(nombre)){
                pos = listaDeEmpresas.indexOf(empresa1);
            }
        }

        if (pos==-1){
            System.out.println("ERROR///// no se encuentra la empresa");
        }

        return pos;
    }*/
    }
}

