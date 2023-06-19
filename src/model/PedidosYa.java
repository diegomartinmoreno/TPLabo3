package model;
import Exceptions.IntentosMaximosDeInicioSesionAlcanzadoException;
import Exceptions.MenorDeEdadException;
import Persona.Administrador;
import Persona.Password;
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
                Password password = new Password(contrasenia);
                usuario.setContrasenia(password);
                flag = true;
            }catch (NullPointerException e){
                System.out.println(e.getMessage());
                flag =false;
            }
        } while (!flag);

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

    public Usuario iniciarSesion (Scanner scanner) throws IntentosMaximosDeInicioSesionAlcanzadoException {
        String email = null, contrasenia = null; //VARIABLES PARA GUARDAR LOS DATOS IMPORTANTE POR SEPARADO.
        Usuario usuario = null; //DECLARO UN USUARIO, PARA QUE SI LO INGRESADO ES CORRECTO, EN EL MAIN ESTE COMO USUARIO ACTUAL.
        int i = 0; //REPRESENTA LOS INTENTOS DE INICIAR SESION.

        System.out.println("Bienvenido! Ingrese los datos correspondientes para iniciar sesion >> ");
        boolean login = false;
        do {
            System.out.println("1) Ingrese el email: ");
            email = scanner.nextLine();
            System.out.println("2) Ingrese su contrasenia: ");
            contrasenia = scanner.nextLine();

            usuario = getUsuarioByEmail(email);
            login = usuario.login(contrasenia);
            if (!login) i++;
            if (i == CANTIDAD_INTENTOS_INICIO_SESION) throw new IntentosMaximosDeInicioSesionAlcanzadoException();
        } while (!login);

        return usuario;
    }

    private Usuario getUsuarioByEmail(String email) {
        Set<Usuario> usuarios = extraerUsuariosFromJSON(ARCHIVO_USUARIOS);
        for (Usuario user : usuarios) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        throw new RuntimeException("Usuario no existe");
    }

    public Usuario buscarUserPorDNI (String dni, Set <Usuario> usuarios) throws NullPointerException{
        Usuario user = null;
        for (Usuario aux : usuarios) {
            if (aux.getDni().equals(dni))
                user = aux;
        }
        if (user == null) throw new NullPointerException();
        return user;
    }

    public boolean modificarContrasenia (Scanner scanner){
        System.out.println("Desea modificar su contrasenia? (s/n): ");
        char c = scanner.next().charAt(0);

        if (c == 's'){
            Set <Usuario> usuarios = extraerUsuariosFromJSON(ARCHIVO_USUARIOS); //OBTENGO EL ARCHIVO DADO QUE ES NECESARIO PARA VERIFICAR SI LA NUEVA CONTRASENIA QUE QUIERE AGREGAR LA PERSONA NO EXISTA.

            System.out.println("Ingrese su DNI para cambiar su contrasenia >>");
            String dni = scanner.nextLine();
            Usuario user=null;
            try {
                user = buscarUserPorDNI(dni, usuarios);
            }catch (NullPointerException e){
                System.out.println(e.getMessage());
            }

            if (user != null){
                System.out.println("Ingrese su nueva contrasenia >>");
                String contraseniaNueva = scanner.nextLine();
                try {
                    Password password = new Password(contraseniaNueva);
                    user.setContrasenia(password);
                    exportarUsuariosToJSON(ARCHIVO_USUARIOS, usuarios); //aca se modifica la contrasenia de esa persona en el archivo.
                    return true;
                }catch (IllegalArgumentException e) {
                    System.out.println("Error con la contrasenia! Ingrese una nueva.");
                }
            }else{
                System.out.println("No se encontraron resultados...");
            }
        }
        return false;
    }

    public boolean modificarEmail (Scanner scanner){
        System.out.println("Desea modificar su email? (s/n): ");
        char c = scanner.next().charAt(0);

        if (c == 's'){
            Set <Usuario> usuarios = extraerUsuariosFromJSON(ARCHIVO_USUARIOS); //OBTENGO EL ARCHIVO PORQUE ES NECESARIO PARA BUSCAR POR DNI Y LUEGO APLICAR LOS CAMBIOS.

            System.out.println("Ingrese su dni para el cambio de email >>");
            String dni = scanner.nextLine();
            Usuario user=null;
            try {
                user = buscarUserPorDNI(dni, usuarios);
            }catch (NullPointerException e){
                System.out.println(e.getMessage());
            }

            if (user!=null){
                System.out.println("Ingrese su nuevo email para su cuenta de PedidosYa >>");
                String emailNuevo = scanner.nextLine();
                user.setEmail(emailNuevo); //SETEO EL NUEVO MAIL.
                exportarUsuariosToJSON(ARCHIVO_USUARIOS, usuarios); //APLICO LOS CAMBIOS EN EL ARCHIVO.
                return true;
            }else{
                System.out.println("No se han encontrado resultados...");
            }
        }
        return false;
    }

    public boolean modificarNroTelefono (Scanner scanner){
        System.out.println("Desea modificar su numero de telefono? (s/n): ");
        char c = scanner.next().charAt(0);

        if (c == 's'){
            Set <Usuario> usuarios = extraerUsuariosFromJSON(ARCHIVO_USUARIOS); //OBTENGO EL ARCHIVO PORQUE ES NECESARIO PARA BUSCAR POR DNI Y LUEGO APLICAR LOS CAMBIOS.

            System.out.println("Ingrese su dni para el cambio de numero de telefono >>");
            String dni = scanner.nextLine();
            Usuario user = null;

            try {
                user = buscarUserPorDNI (dni, usuarios);
            }catch (NullPointerException e){
                System.out.println(e.getMessage());
            }

            if (user != null) {
                System.out.println("Ingrese su nuevo numero de telefono para su cuenta de PedidosYa >>");
                String telefono = scanner.nextLine();

                try {
                    if (!Usuario.verificarCodigoDeArea(telefono) && Usuario.verificarEsNumero(telefono) && Usuario.verificarLongitudTelefono(telefono))
                        System.out.println("Error en el telefono ingresado. Verifique el numero de area, si ha ingresado todos numeros o si la longitud es de 8 digitos.");
                    else {
                        user.setNroDeTelefono(telefono);
                        exportarUsuariosToJSON(ARCHIVO_USUARIOS, usuarios);
                        return true;
                    }
                }catch (NullPointerException e){
                    System.out.println(e.getMessage());
                }
            }else{
                System.out.println("No se han encontrado resultados...");
            }
        }
        return false;
    }

    public boolean modificarNombre (Scanner scanner){
        System.out.println("Desea modificar su nombre de cuenta? (s/n): ");
        char c = scanner.next().charAt(0);

        if (c == 's'){
            Set <Usuario> usuarios = extraerUsuariosFromJSON(ARCHIVO_USUARIOS); //OBTENGO EL ARCHIVO PORQUE ES NECESARIO PARA BUSCAR POR DNI Y LUEGO APLICAR LOS CAMBIOS.

            System.out.println("Finalmente ingrese su dni para el cambio de nombre >>");
            String dni = scanner.nextLine();
            Usuario user = null;

            try{
                user = buscarUserPorDNI(dni, usuarios);
            }catch (NullPointerException e){
                System.out.println(e.getMessage());
            }

            if (user!=null) {
                System.out.println("Ingrese su nuevo nombre para su cuenta de PedidosYa >>");
                String nombreNuevo = scanner.nextLine();
                try {
                    if (!Persona.verificarEsLetra(nombreNuevo))
                        System.out.println("Error! El nombre no son todas letras.");
                    else {
                        user.setNombre(nombreNuevo);
                        exportarUsuariosToJSON(ARCHIVO_USUARIOS, usuarios);
                        return true;
                    }
                }catch (NullPointerException e){
                    System.out.println(e.getMessage());
                }
            }
        }
        return false;
    }

    //EL METODO DE CAMBIAR TARJETA SE VA A MOSTRAR Y PODER ACCEDER SOLO SI HAY ACTIVA UNA TARJETA.
    public boolean cambiarTarjeta (Scanner scanner){
        System.out.println("Desea sacar su tarjeta actual y cargar una distinta? (s/n): ");
        char c = scanner.next().charAt(0);

        if (c == 's'){
            Set <Usuario> usuarios = extraerUsuariosFromJSON(ARCHIVO_USUARIOS); //OBTENGO EL ARCHIVO PORQUE ES NECESARIO PARA BUSCAR POR DNI Y LUEGO APLICAR LOS CAMBIOS.
            System.out.println("Ingrese su dni para luego poder cargar la nueva tarjeta >>");
            String dni = scanner.nextLine();

            try {
                Usuario user = buscarUserPorDNI(dni, usuarios);
                return user.getTarjeta().cargarTarjeta(scanner);
            }catch (NullPointerException e){
                System.out.println("Error! No se ha encontrado ningun resultado.");
            }
        }
        return false;
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
        listaDeEmpresas.add(new Empresa("La Musa", crearListaDeProductos(Set.of(BEBIDAS, EMPANADAS, PAPAS, PIZZA)), Set.of(PUERTO, BOSQUE),250));
        listaDeEmpresas.add(new Empresa("Hamburgo", crearListaDeProductos(Set.of(BEBIDAS, HAMBURGUESAS, PAPAS)), Set.of(CENTRO,RUMENCO, MOGOTES),250));
        listaDeEmpresas.add(new Empresa("Konichiwa", crearListaDeProductos(Set.of(BEBIDAS, ENSALADAS, PASTAS, SUSHI)), Set.of(PUERTO,CONSTITUCION, MOGOTES),250));
        listaDeEmpresas.add(new Empresa("DeDiez", crearListaDeProductos(Set.of(BEBIDAS, EMPANADAS, PAPAS, PIZZA)), Set.of(ALEM,RUMENCO, LOS_TRONCOS),250));
        listaDeEmpresas.add(new Empresa("Grido", crearListaDeProductos(Set.of(HELADOS)), Set.of(ALEM,RUMENCO),250));
        listaDeEmpresas.add(new Empresa("Banderita", crearListaDeProductos(Set.of(BEBIDAS, CARNES, EMPANADAS, ENSALADAS, PAPAS, PARRILLA, POLLO)), Set.of(CENTRO,ALEM, LOS_TRONCOS),250));
        listaDeEmpresas.add(new Empresa("La Hamburgueseria", crearListaDeProductos(Set.of(BEBIDAS, HAMBURGUESAS, PAPAS)), Set.of(PUERTO,ALEM, COLINAS, MOGOTES),250));
        listaDeEmpresas.add(new Empresa("Italia", crearListaDeProductos(Set.of(HELADOS, POSTRES)), Set.of(ALEM, CENTRO, LOS_TRONCOS),250));
        listaDeEmpresas.add(new Empresa("Mandinga", crearListaDeProductos(Set.of(BEBIDAS, CARNES, EMPANADAS, ENSALADAS, PAPAS, PARRILLA, POLLO)), Set.of(CENTRO,CONSTITUCION, BOSQUE),250));
        listaDeEmpresas.add(new Empresa("Kiosco Da", crearListaDeProductos(Set.of(KIOSCO)), Set.of(ALEM,INDEPENDENCIA),250));
        listaDeEmpresas.add(new Empresa("Lo de Mario", crearListaDeProductos(Set.of(BEBIDAS, ENSALADAS, MILANESAS, PAPAS, PASTAS, POSTRES, POSTRES)), Set.of(PUERTO,RUMENCO),250));
        listaDeEmpresas.add(new Empresa("Antares", crearListaDeProductos(Set.of(CERVEZA, ENSALADAS, HAMBURGUESAS, PAPAS, PIZZA)), Set.of(CENTRO,CONSTITUCION, MOGOTES),250));
        listaDeEmpresas.add(new Empresa("Baum", crearListaDeProductos(Set.of(CERVEZA, EMPANADAS, HAMBURGUESAS, PAPAS, PIZZA)), Set.of(CENTRO, COLINAS, MOGOTES),250));
        listaDeEmpresas.add(new Empresa("Cheverry", crearListaDeProductos(Set.of(CERVEZA, ENSALADAS, HAMBURGUESAS, PAPAS, PIZZA)), Set.of(CENTRO,RUMENCO,INDEPENDENCIA, BOSQUE),250));
        listaDeEmpresas.add(new Empresa("Gianelli", crearListaDeProductos(Set.of(HELADOS, POSTRES)), Set.of(PUERTO,CONSTITUCION),250));
        listaDeEmpresas.add(new Empresa("El Club de la Milanesa", crearListaDeProductos(Set.of(BEBIDAS, MILANESAS, PAPAS)), Set.of(CENTRO,INDEPENDENCIA, BOSQUE),250));
    }

    public Producto buscarProductoPorID(int id){
        Producto producto = null;

        for(Empresa empresa : listaDeEmpresas){
            producto = empresa.buscarProductoPorID(id);
        }

        return producto;
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

        switch (tipoDeProducto){

            case BEBIDAS -> {
                listaDeProductos.add(new Producto("Coca Cola", "Gaseosa", "Gaseosa sabor Cola", 420));
                listaDeProductos.add(new Producto("Fanta Naranja", "Gaseosa", "Gaseosa sabor naranja", 420));
                listaDeProductos.add(new Producto("Seven Up", "Gaseosa", "Gaseosa Lima Limon", 500));
                listaDeProductos.add(new Producto("Agua sin gas", "", "Agua mineral sin gas", 500));
                listaDeProductos.add(new Producto("Agua con gas", "", "Agua mineral con gas", 500));
                listaDeProductos.add(new Producto("Quilmes clasica 473cm", "Cerveza", "Lata de cerveza Quilmes rubia", 600));
                listaDeProductos.add(new Producto("Quilmes roja 473cm", "Cerveza", "Lata de cerveza Quilmes roja", 600));
                listaDeProductos.add(new Producto("Quilmes Black Stout 473cm", "Cerveza", "Lata de cerveza Quilmes negra", 600));
                break;
            }case CERVEZA -> {
                listaDeProductos.add(new Producto("IPA", "Cerveza Artesanal", "Cerveza India Pale Ale con notas cítricas y amargas", 200));
                listaDeProductos.add(new Producto("Stout", "Cerveza Artesanal", "Cerveza negra de estilo stout con sabor a café y chocolate", 220));
                listaDeProductos.add(new Producto("Amber Ale", "Cerveza Artesanal", "Cerveza de color ámbar con notas caramelizadas y suaves", 190));
                listaDeProductos.add(new Producto("APA", "Cerveza Artesanal", "Cerveza American Pale Ale con equilibrio entre malta y lúpulo", 210));
                listaDeProductos.add(new Producto("Golden Ale", "Cerveza Artesanal", "Cerveza dorada y refrescante de estilo ale", 180));
                listaDeProductos.add(new Producto("Witbier", "Cerveza Artesanal", "Cerveza belga de trigo con especias y sabor cítrico", 200));
                listaDeProductos.add(new Producto("Barley Wine", "Cerveza Artesanal", "Cerveza de alta graduación alcohólica y sabor intenso", 250));
                listaDeProductos.add(new Producto("Red Ale", "Cerveza Artesanal", "Cerveza roja de cuerpo medio y sabor maltoso", 190));
            }
            case CARNES -> {
                listaDeProductos.add(new Producto("Bife de Costilla", "Bife", "Fantatico Bife Costilla Gourmet", 1100));
                listaDeProductos.add(new Producto("Chorizo", "Achura", "Chorizo de cancha", 600));
                listaDeProductos.add(new Producto("Morcilla", "Achura", "Morcilla salada", 600));
                listaDeProductos.add(new Producto("Asado", "Plato para compartir", "", 1700));
                listaDeProductos.add(new Producto("Vacio", "Plato para compartir", "", 1800));
                break;
            }
            case EMPANADAS -> {
                listaDeProductos.add(new Producto("Emapanada de carne", "Porcion", "Empanada de carne, ciruela y huevo", 500));
                listaDeProductos.add(new Producto("Empanada de pollo", "Porcion", "Empanada de pollo, morron y cebolla", 600));
                listaDeProductos.add(new Producto("Empanada de Jamon y Queso", "Porcion", "Gran empanada con mucho queso", 200));
                listaDeProductos.add(new Producto("Empanada de cebolla y queso", "Porcion", "El mejor sabor de empanada", 600));
                listaDeProductos.add(new Producto("Empanada de matambre", "Porcion", "Empanada clasica con morron, cebolla y salsa", 500));
                listaDeProductos.add(new Producto("Caprese", "Porcion", "Empanada con tomate, queso y albahaca", 600));
                break;
            }
            case ENSALADAS -> {
                listaDeProductos.add(new Producto("Ensalada Caesar", "Plato principal", "Deliciosa ensalada con pollo, lechuga, nuez y salsa Caesar", 1100));
                listaDeProductos.add(new Producto("Ensalada Griega", "Entrante", "Deliciosa ensalada con tomate, pepino, cebolla, aceitunas y queso feta", 950));
                listaDeProductos.add(new Producto("Ensalada Caprese", "Entrante", "Fresca ensalada con tomate, mozzarella y albahaca", 850));
                listaDeProductos.add(new Producto("Ensalada de Pollo", "Plato principal", "Ensalada con pollo a la parrilla, lechuga, croutones y aderezo", 1100));
                listaDeProductos.add(new Producto("Ensalada Mediterránea", "Plato principal", "Ensalada con ingredientes mediterráneos como aceitunas, tomate y queso feta", 1050));
                break;
            }
            case HAMBURGUESAS -> {
                listaDeProductos.add(new Producto("CheeseBurger", "Hamburguesa simple", "Hamburguesa con cheddar, panceta, y salsa mil islas", 2000));
                listaDeProductos.add(new Producto("Bacon Burger", "Hamburguesa gourmet", "Hamburguesa con queso cheddar, panceta crujiente y salsa barbacoa", 2200));
                listaDeProductos.add(new Producto("Mushroom Burger", "Hamburguesa vegetariana", "Hamburguesa de champiñones con queso suizo y salsa especial", 2500));
                listaDeProductos.add(new Producto("Spicy Burger", "Hamburguesa picante", "Hamburguesa con jalapeños, queso pepper jack y salsa picante", 2100));
                listaDeProductos.add(new Producto("Chicken Burger", "Hamburguesa de pollo", "Hamburguesa de pollo crujiente con lechuga, tomate y mayonesa", 1900));
                break;
            }
            case HELADOS -> {
                listaDeProductos.add(new Producto("Helado Banana Split", "Postre", "Helado sabor banana, con dulce de leche y pedazos de chocolate", 600));
                listaDeProductos.add(new Producto("Helado de Vainilla", "Helado", "Clásico helado de vainilla cremoso", 500));
                listaDeProductos.add(new Producto("Helado de Chocolate", "Helado", "Delicioso helado de chocolate con trozos de chocolate negro", 550));
                listaDeProductos.add(new Producto("Helado de Fresa", "Helado", "Refrescante helado de fresa con trocitos de fruta", 550));
                listaDeProductos.add(new Producto("Helado de Menta", "Helado", "Helado de menta con chispas de chocolate", 550));
                listaDeProductos.add(new Producto("Helado de Dulce de Leche", "Helado", "Irresistible helado de dulce de leche con nueces caramelizadas", 600));
                listaDeProductos.add(new Producto("Helado de Cookies & Cream", "Helado", "Helado de vainilla con trozos de galleta de chocolate", 550));
                break;
            }
            case MILANESAS -> {
                listaDeProductos.add(new Producto("Milanesa a caballo", "Milanesa de Carne", "Milanesa de carne o pollo con huevo frito", 1700));
                listaDeProductos.add(new Producto("Milanesa Napolitana", "Milanesa de Carne", "Milanesa de carne cubierta con salsa de tomate, jamón y queso", 1900));
                listaDeProductos.add(new Producto("Milanesa de Pollo", "Milanesa de Pollo", "Milanesa de pollo empanizada y frita", 1500));
                listaDeProductos.add(new Producto("Milanesa Hawaiana", "Milanesa de Carne", "Milanesa de carne con piña, jamón, queso y salsa barbacoa", 2000));
                listaDeProductos.add(new Producto("Milanesa Exótica", "Milanesa de Pollo", "Milanesa de pollo con especias exóticas y salsa de mango picante", 1800));
                listaDeProductos.add(new Producto("Milanesa Infernal", "Milanesa de Carne", "Milanesa de carne empanizada con jalapeños, queso picante y salsa buffalo", 2100));
                listaDeProductos.add(new Producto("Milanesa Fantástica", "Milanesa de Pollo", "Milanesa de pollo rellena de queso crema y espinacas, envuelta en panceta", 2200));
                listaDeProductos.add(new Producto("Milanesa Explosiva", "Milanesa de Carne", "Milanesa de carne con chiles jalapeños, cebolla caramelizada y salsa de queso picante", 2300));
                break;
            }
            case PAPAS -> {
                listaDeProductos.add(new Producto("Papas bomba", "Acompañamientos", "Papas con cheddar y panceta", 1400));
                listaDeProductos.add(new Producto("Papas Deluxe", "Acompañamientos", "Papas con queso cheddar, tocino crujiente y crema agria", 1500));
                listaDeProductos.add(new Producto("Papas Rancheras", "Acompañamientos", "Papas sazonadas con especias rancheras y salsa de queso", 1300));
                listaDeProductos.add(new Producto("Papas Picantes", "Acompañamientos", "Papas fritas con salsa picante, jalapeños y queso fundido", 1600));
                listaDeProductos.add(new Producto("Papas Gourmet", "Acompañamientos", "Papas con trufa negra, queso parmesano y aceite de oliva", 1800));
                break;
            }
            case PARRILLA -> {
                listaDeProductos.add(new Producto("Bife de Chorizo", "Parrilla", "Sabroso bife de chorizo jugoso y tierno", 1800));
                listaDeProductos.add(new Producto("Asado de Tira", "Parrilla", "Deliciosas tiras de carne asada a la parrilla", 1700));
                listaDeProductos.add(new Producto("Churrasco de Res", "Parrilla", "Corte de carne de res tierno y jugoso, asado a la parrilla", 1900));
                listaDeProductos.add(new Producto("Costillas de Cerdo", "Parrilla", "Costillas de cerdo marinadas y asadas a la parrilla con salsa barbacoa", 1600));
                listaDeProductos.add(new Producto("Brochetas Mixtas", "Parrilla", "Brochetas de carne de res, pollo y vegetales asadas a la parrilla", 1700));
                listaDeProductos.add(new Producto("Mollejas de Pollo", "Parrilla", "Deliciosas mollejas de pollo asadas a la parrilla", 1500));
                listaDeProductos.add(new Producto("Choripán", "Parrilla", "Clásico sándwich argentino con chorizo a la parrilla", 1400));
                break;
            }
            case POSTRES -> {
                listaDeProductos.add(new Producto("Tarta de Manzana", "Postre", "Tarta casera de manzana con crujiente de canela", 1200));
                listaDeProductos.add(new Producto("Brownie con Helado", "Postre", "Brownie de chocolate caliente servido con helado de vainilla", 1300));
                listaDeProductos.add(new Producto("Cheesecake de Frutos Rojos", "Postre", "Cheesecake cremoso con salsa de frutos rojos", 1400));
                listaDeProductos.add(new Producto("Coulant de Chocolate", "Postre", "Delicioso coulant de chocolate con centro líquido y helado", 1500));
                listaDeProductos.add(new Producto("Crepas de Nutella", "Postre", "Crepas rellenas de Nutella y espolvoreadas con azúcar glas", 1100));
                break;
            }
            case SUSHI -> {
                listaDeProductos.add(new Producto("Sushi Nigiri de Salmón", "Sushi", "Salmón fresco sobre arroz de sushi", 1800));
                listaDeProductos.add(new Producto("Sushi Maki de Aguacate", "Sushi", "Rollos de sushi rellenos de aguacate y envueltos en alga nori", 1500));
                listaDeProductos.add(new Producto("Sushi California Roll", "Sushi", "Rollos de sushi con cangrejo, pepino y aguacate", 1700));
                listaDeProductos.add(new Producto("Sushi Sashimi de Atún", "Sushi", "Finas láminas de atún fresco servido sin arroz", 2000));
                listaDeProductos.add(new Producto("Sushi Temaki de Camarón", "Sushi", "Cono de alga nori relleno de camarones tempura y vegetales", 1600));
                listaDeProductos.add(new Producto("Sushi Uramaki Philadelphia", "Sushi", "Rollos de sushi invertidos rellenos de salmón, queso crema y pepino", 1900));
                listaDeProductos.add(new Producto("Sushi Nigiri de Pulpo", "Sushi", "Pulpo tierno sobre arroz de sushi", 2100));
                break;
            }
            case KIOSCO -> {
                listaDeProductos.add(new Producto("Chocolinas", "Galletas", "Clásicas galletas de chocolate", 100));
                listaDeProductos.add(new Producto("Alfajor de Maicena", "Dulces", "Delicioso alfajor relleno de dulce de leche y cubierto de coco rallado", 70));
                listaDeProductos.add(new Producto("Papas Fritas", "Snacks", "Crunchy papas fritas de bolsa", 120));
                listaDeProductos.add(new Producto("Agua Mineral", "Bebidas", "Botella de agua mineral natural", 80));
                listaDeProductos.add(new Producto("Coca-Cola", "Bebidas", "Refresco de cola carbonatado", 150));
                listaDeProductos.add(new Producto("KitKat", "Chocolates", "Barrita de chocolate con obleas crujientes", 110));
                listaDeProductos.add(new Producto("Chicle Trident", "Chicles", "Paquete de chicles sabor menta", 60));
                listaDeProductos.add(new Producto("Cigarrillos Marlboro", "Tabaco", "Paquete de cigarrillos Marlboro", 250));
                listaDeProductos.add(new Producto("Revista", "Prensa", "Revista de entretenimiento y noticias", 80));
                listaDeProductos.add(new Producto("Caramelos Sugus", "Dulces", "Caramelos masticables de distintos sabores", 50));
                listaDeProductos.add(new Producto("Pancho", "Comida Rápida", "Hot dog con salchicha, pan y condimentos", 180));
                listaDeProductos.add(new Producto("Barrita de Cereal", "Snacks", "Barrita de cereal con frutas y nueces", 90));
                break;
            }
            case PIZZA -> {
                listaDeProductos.add(new Producto("Pizza Margarita", "Pizza", "Clásica pizza italiana con salsa de tomate, mozzarella y albahaca", 1500));
                listaDeProductos.add(new Producto("Pizza Pepperoni", "Pizza", "Pizza con salsa de tomate, mozzarella y abundantes rodajas de pepperoni", 1600));
                listaDeProductos.add(new Producto("Pizza Hawaiana", "Pizza", "Pizza con salsa de tomate, mozzarella, jamón y piña", 1700));
                listaDeProductos.add(new Producto("Pizza Cuatro Quesos", "Pizza", "Pizza con una deliciosa combinación de quesos: mozzarella, gorgonzola, parmesano y provolone", 1800));
                listaDeProductos.add(new Producto("Pizza Vegetariana", "Pizza", "Pizza con salsa de tomate, mozzarella y una variedad de vegetales frescos", 1700));
                listaDeProductos.add(new Producto("Pizza BBQ Chicken", "Pizza", "Pizza con salsa barbacoa, pollo desmenuzado, cebolla roja y queso", 1800));
                listaDeProductos.add(new Producto("Pizza Diavola", "Pizza", "Pizza picante con salsa de tomate, mozzarella, salami y aceitunas", 1600));
                break;
            }
            case POLLO -> {
                listaDeProductos.add(new Producto("Pollo a la Parrilla", "Pollo", "Pechuga de pollo a la parrilla con especias y limón", 1500));
                listaDeProductos.add(new Producto("Alitas de Pollo BBQ", "Pollo", "Alitas de pollo adobadas y asadas con salsa barbacoa", 1400));
                listaDeProductos.add(new Producto("Pollo Frito Crujiente", "Pollo", "Trozos de pollo empanizados y fritos hasta obtener una textura crujiente", 1600));
                listaDeProductos.add(new Producto("Pollo al Curry", "Pollo", "Pollo en salsa de curry con especias y leche de coco", 1700));
                listaDeProductos.add(new Producto("Pechuga de Pollo Rellena", "Pollo", "Pechuga de pollo rellena de espinacas y queso, acompañada de salsa de champiñones", 1800));
                listaDeProductos.add(new Producto("Pollo Teriyaki", "Pollo", "Pollo a la parrilla con salsa teriyaki, servido con arroz y vegetales salteados", 1600));
                listaDeProductos.add(new Producto("Brochetas de Pollo", "Pollo", "Brochetas de pollo marinadas y asadas con vegetales", 1700));
                break;
            }
            case PASTAS -> {
                listaDeProductos.add(new Producto("Spaghetti Bolognese", "Pasta", "Spaghetti con salsa bolognesa de carne y tomate", 1200));
                listaDeProductos.add(new Producto("Fettuccine Alfredo", "Pasta", "Fettuccine en salsa cremosa de queso parmesano", 1300));
                listaDeProductos.add(new Producto("Penne Arrabiata", "Pasta", "Penne con salsa picante de tomate y ajo", 1250));
                listaDeProductos.add(new Producto("Lasagna Tradicional", "Pasta", "Lasagna de carne con capas de pasta, salsa de tomate y queso", 1500));
                listaDeProductos.add(new Producto("Ravioli de Espinacas y Ricotta", "Pasta", "Ravioli relleno de espinacas y queso ricotta, en salsa de tomate", 1400));
                listaDeProductos.add(new Producto("Tortellini con Salsa Pesto", "Pasta", "Tortellini relleno de queso en salsa de pesto de albahaca", 1350));
                listaDeProductos.add(new Producto("Tagliatelle con Salsa Carbonara", "Pasta", "Tagliatelle con salsa cremosa de huevo, panceta y queso parmesano", 1400));
                break;
            } default->{
                System.out.println("Dato no valido");
            }

        }

        return listaDeProductos;
    }

    public void agregarDescuento(String cupon, Carrito carrito) throws NullPointerException, RuntimeException{
        if(cupon==null) throw new NullPointerException("Error! El cupon no puede ser nulo.//***");
        if (!carrito.getVendedor().validarCupon(cupon)) throw new RuntimeException("Error! Cupon no valido.//***");

        carrito.setTieneCupon(true);
    }
}


