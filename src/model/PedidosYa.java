package model;
import Exceptions.IntentosMaximosDeInicioSesionAlcanzadoException;
import Exceptions.MenorDeEdadException;
import Persona.Administrador;
import Persona.Password;
import Persona.Persona;
import Persona.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.source.tree.NewArrayTree;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static model.TipoDeProductos.*;
import static model.Zonas.*;

public class PedidosYa {

    /*
        En esta clase decidimos manejarlo por separado a los usuarios y administradores ya que interactuan en ambitos distintos,
        el admin tiene acceso a todo y el user a lo que pueda ver del programa y no vimos conveniente tener que instanciar los metodos
        para verificar que sea una u otra instancia. Cuando se registre o inicie sesion, dependiendo en que modo se accede, son a los metodos
        que se llaman.
    */

    private List<Empresa> listaDeEmpresas;
    private Set<Usuario> usuarios;
    private Set<Administrador> administradores;
    public static final String ARCHIVO_USUARIOS = "Users.json";
    public static final String ARCHIVO_EMPRESAS = "Empresas.json";
    public static final String ARCHIVO_ADMINISTRADORES = "Administradores.json";
    public static final int CANTIDAD_INTENTOS_INICIO_SESION = 3;

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

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////// PARTE DE USUARIO

    public void exportarUsuariosToJSON(String path, Set<Usuario> usuarios) {
        File file = new File(path);
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(file, usuarios);
        } catch (IOException e) {
            System.out.println("Error en la escritura del archivo.");
        }
    }

    public Set<Usuario> extraerUsuariosFromJSON(String path) {
        File file = new File(path);
        ObjectMapper mapper = new ObjectMapper();
        Set<Usuario> usuarioHashSet = new HashSet<>();

        try {
            Usuario[] usuariosArray = mapper.readValue(file, Usuario[].class);
            usuarioHashSet.addAll(Arrays.asList(usuariosArray));
        } catch (IOException e) {
            System.out.println("Error en la lectura del archivo.");
            System.out.println(e.getMessage());
        }
        return usuarioHashSet;
    }

    public Usuario registroDeCuentaDeUsuario(Scanner scanner) throws MenorDeEdadException {
        Usuario usuario = new Usuario();
        String cadenaAux = null, contrasenia = null;
        boolean flag = false;
        this.usuarios = extraerUsuariosFromJSON(ARCHIVO_USUARIOS);

        System.out.println("Bienvenido! Ingrese los datos correspondientes >> ");

        do {
            System.out.println("1) Ingrese su nombre: ");
            scanner.nextLine();
            cadenaAux = scanner.nextLine();
            try {
                flag = Usuario.verificarEsLetra(cadenaAux);
                if (!flag)
                    System.out.println("Recuerde que su nombre son solo letras!.");
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        } while (!flag);

        usuario.setNombre(cadenaAux);

        do {
            System.out.println("2) Ingrese su apellido: ");
            cadenaAux = scanner.nextLine();
            try {
                flag = Usuario.verificarEsLetra(cadenaAux);
                if (!flag)
                    System.out.println("Recuerde que su apellido son solo letras!.");
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        } while (!flag);

        usuario.setApellido(cadenaAux);

        System.out.println("3) Ingrese su edad");
        int edad = scanner.nextInt();
        try {
            flag = Usuario.verificarEdad(edad);
            if (!flag)
                throw new MenorDeEdadException();
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

        usuario.setEdad(edad);

        do {
            scanner.nextLine();
            System.out.println("4) Ingrese su DNI: ");
            cadenaAux = scanner.nextLine();
            try {
                flag = Usuario.verificarEsNumero(cadenaAux);
                if (flag) {
                    flag = Usuario.verificarLongitudDNI(cadenaAux);
                    if (!flag)
                        System.out.println("Error en la longitud del DNI!. Son 8 digitos.");
                } else
                    System.out.println("Error en el dni, no son todos digitos.");
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        } while (!flag);

        usuario.setDni(cadenaAux);

        do {
            System.out.println("5) Ingrese su numero telefonico: ");
            cadenaAux = scanner.nextLine();
            try {
                flag = Usuario.verificarEsNumero(cadenaAux);
                if (flag) {
                    flag = Persona.verificarCodigoDeArea(cadenaAux); //NO HAGO TRY CATCH DE ESTE METODO PQ EN TEORIA VERIFICAR COD AREA SI NO LANZO EXCEPCION, ES QUE SON TODOS DIGITOS.
                    if (!flag)
                        System.out.println("Error en el codigo de area del telefono!.");
                } else
                    System.out.println("Error en el numero telefonico, no son todos digitos.");
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        } while (!flag);

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
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
                flag = false;
            }catch(IllegalArgumentException ex){
                System.out.println("La contraseña debe ser mas segura");
                flag= false;
            }
        } while (!flag);

        int decision = 0;

        System.out.println("Desea anadir la tarjeta ahora o luego?. ");
        System.out.println("[1] Ahora.\n[2] Mas tarde.");
        decision = scanner.nextInt();

        if (decision == 1) {
            usuario.getTarjeta().cargarTarjeta(scanner);
        } else if (decision == 2) {
            System.out.println("Cuando desee comprar, debera cargar su tarjeta.");
        } else {
            System.out.println("Se equivoco de boton, no se cargara ningun dato de la tarjeta. Cuando desee comprar, debera cargar su tarjeta.");
        }

        this.usuarios.add(usuario);
        exportarUsuariosToJSON(ARCHIVO_USUARIOS, this.usuarios);

        return usuario;
    }

    private Usuario getUsuarioByEmail(String email) {
        this.usuarios = extraerUsuariosFromJSON(ARCHIVO_USUARIOS);
        for (Usuario user : usuarios) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        throw new RuntimeException("Usuario no existe");
    }

    public Usuario iniciarSesionComoUsuario(Scanner scanner) throws IntentosMaximosDeInicioSesionAlcanzadoException {
        String email = null, contrasenia = null; //VARIABLES PARA GUARDAR LOS DATOS IMPORTANTE POR SEPARADO.
        Usuario usuario = null; //DECLARO UN USUARIO, PARA QUE SI LO INGRESADO ES CORRECTO, EN EL MAIN ESTE COMO USUARIO ACTUAL.
        int i = 0; //REPRESENTA LOS INTENTOS DE INICIAR SESION.

        System.out.println("Bienvenido! Ingrese los datos correspondientes para iniciar sesion >> ");
        boolean login = false;
        scanner.nextLine();
        do {
            System.out.println("1) Ingrese el email: ");
            email = scanner.nextLine();
            System.out.println("2) Ingrese su contrasenia: ");
            contrasenia = scanner.nextLine();

            try {
                usuario = getUsuarioByEmail(email);
                login = usuario.login(contrasenia);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }

            if (!login) i++;
            if (i == CANTIDAD_INTENTOS_INICIO_SESION) throw new IntentosMaximosDeInicioSesionAlcanzadoException();

        } while (!login);

        return usuario;
    }

    public Usuario buscarUserPorDNI(String dni, Set<Usuario> usuarios) throws NullPointerException {
        Usuario user = null;
        for (Usuario aux : usuarios) {
            if (aux.getDni().equals(dni))
                user = aux;
        }
        if (user == null) throw new NullPointerException();
        return user;
    }

    public boolean modificarContraseniaDeUsuario(Scanner scanner) {
        System.out.println("Desea modificar su contrasenia? (s/n): ");
        char c = scanner.next().charAt(0);
        scanner.nextLine();

        if (c == 's') {
            this.usuarios = extraerUsuariosFromJSON(ARCHIVO_USUARIOS); //OBTENGO EL ARCHIVO DADO QUE ES NECESARIO PARA VERIFICAR SI LA NUEVA CONTRASENIA QUE QUIERE AGREGAR LA PERSONA NO EXISTA.
            System.out.println("Ingrese su DNI para cambiar su contrasenia >>");
            String dni = scanner.nextLine();
            Usuario user = null;

            try {
                user = buscarUserPorDNI(dni, this.usuarios);
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }

            if (user != null) {
                System.out.println("Ingrese su nueva contrasenia >>");
                String contraseniaNueva = scanner.nextLine();
                try {
                    Password password = new Password(contraseniaNueva);
                    user.setContrasenia(password);
                    exportarUsuariosToJSON(ARCHIVO_USUARIOS, this.usuarios); //aca se modifica la contrasenia de esa persona en el archivo.
                    return true;
                } catch (IllegalArgumentException e) {
                    System.out.println("Error con la contrasenia! Ingrese una nueva.");
                }
            } else {
                System.out.println("No se encontraron resultados...");
            }
        }
        return false;
    }

    public boolean modificarEmailDeUsuario(Scanner scanner) {
        System.out.println("Desea modificar su email? (s/n): ");
        char c = scanner.next().charAt(0);
        scanner.nextLine();

        if (c == 's') {
            this.usuarios = extraerUsuariosFromJSON(ARCHIVO_USUARIOS); //OBTENGO EL ARCHIVO PORQUE ES NECESARIO PARA BUSCAR POR DNI Y LUEGO APLICAR LOS CAMBIOS.

            System.out.println("Ingrese su dni para el cambio de email >>");
            String dni = scanner.nextLine();
            Usuario user = null;
            try {
                user = buscarUserPorDNI(dni, this.usuarios);
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }

            if (user != null) {
                System.out.println("Ingrese su nuevo email para su cuenta de PedidosYa >>");
                String emailNuevo = scanner.nextLine();
                user.setEmail(emailNuevo); //SETEO EL NUEVO MAIL.
                exportarUsuariosToJSON(ARCHIVO_USUARIOS, this.usuarios); //APLICO LOS CAMBIOS EN EL ARCHIVO.
                return true;
            } else {
                System.out.println("No se han encontrado resultados...");
            }
        }
        return false;
    }

    public boolean modificarNroTelefonoDeUsuario(Scanner scanner) {
        System.out.println("Desea modificar su numero de telefono? (s/n): ");
        char c = scanner.next().charAt(0);
        scanner.nextLine();

        if (c == 's') {
            this.usuarios = extraerUsuariosFromJSON(ARCHIVO_USUARIOS); //OBTENGO EL ARCHIVO PORQUE ES NECESARIO PARA BUSCAR POR DNI Y LUEGO APLICAR LOS CAMBIOS.

            System.out.println("Ingrese su dni para el cambio de numero de telefono >>");
            String dni = scanner.nextLine();
            Usuario user = null;

            try {
                user = buscarUserPorDNI(dni, this.usuarios);
            } catch (NullPointerException e) {
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
                        exportarUsuariosToJSON(ARCHIVO_USUARIOS, this.usuarios);
                        return true;
                    }
                } catch (NullPointerException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("No se han encontrado resultados...");
            }
        }
        return false;
    }

    public boolean modificarNombreYapellidoDeUsuario(Scanner scanner) {
        System.out.println("Desea modificar su nombre y apellido de cuenta (si solo desea el nombre por ejemplo, aun asi ingrese el mismo apellido)? (s/n): ");
        char c = scanner.next().charAt(0);
        scanner.nextLine();
        Usuario user = null;
        this.usuarios = extraerUsuariosFromJSON(ARCHIVO_USUARIOS); //OBTENGO EL ARCHIVO PORQUE ES NECESARIO PARA BUSCAR POR DNI Y LUEGO APLICAR LOS CAMBIOS.

        if (c == 's') {
            System.out.println("Ingrese su dni para el cambio de nombre y apellido >>");
            String dni = scanner.nextLine();

            try {
                user = buscarUserPorDNI(dni, this.usuarios);
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }

            if (user != null) {
                System.out.println("Ingrese su nuevo nombre para su cuenta de PedidosYa >>");
                String nombreNuevo = scanner.nextLine();
                System.out.println("Ingrese su nuevo apellido para su cuenta de PedidosYa >>");
                String apellidoNuevo = scanner.nextLine();

                try {
                    if (!Usuario.verificarEsLetra(nombreNuevo) && !Usuario.verificarEsLetra(apellidoNuevo))
                        System.out.println("Error! Lo ingresado no son todas letras.");
                    else {
                        user.setNombre(nombreNuevo);
                        user.setApellido(apellidoNuevo);
                        exportarUsuariosToJSON(ARCHIVO_USUARIOS, this.usuarios);
                        return true;
                    }
                } catch (NullPointerException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return false;
    }

    //EL METODO DE CAMBIAR TARJETA SE VA A MOSTRAR Y PODER ACCEDER SOLO SI HAY ACTIVA UNA TARJETA.
    public boolean cambiarTarjetaDeUsuario(Scanner scanner) {
        System.out.println("Desea sacar su tarjeta actual y cargar una distinta? (s/n): ");
        char c = scanner.next().charAt(0);
        scanner.nextLine();

        if (c == 's') {
            this.usuarios = extraerUsuariosFromJSON(ARCHIVO_USUARIOS); //OBTENGO EL ARCHIVO PORQUE ES NECESARIO PARA BUSCAR POR DNI Y LUEGO APLICAR LOS CAMBIOS.
            System.out.println("Ingrese su dni para luego poder cargar la nueva tarjeta >>");
            String dni = scanner.nextLine();

            try {
                Usuario user = buscarUserPorDNI(dni, this.usuarios);
                if (user != null) {
                    user.getTarjeta().modificarTarjeta(scanner);
                    exportarUsuariosToJSON(ARCHIVO_USUARIOS, this.usuarios);
                    return true;
                }
            } catch (NullPointerException e) {
                System.out.println("Error! No se ha encontrado ningun resultado.");
            }
        }
        return false;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////// FINALIZA PARTE DE USUARIO

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////// PARTE DE ADMIN

    public void exportarAdministradoresToJSON(String path, Set<Administrador> administradores) {
        File file = new File(path);
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(file, administradores);
        } catch (IOException e) {
            System.out.println("Error en la escritura del archivo.");
        }
    }

    public Set<Administrador> extraerAdministradoresFromJSON(String path) {
        File file = new File(path);
        ObjectMapper mapper = new ObjectMapper();
        Set<Administrador> adminsHashSet = new HashSet<>();

        try {
            Administrador[] usuariosArray = mapper.readValue(file, Administrador[].class);
            adminsHashSet.addAll(Arrays.asList(usuariosArray));
        } catch (IOException e) {
            System.out.println("Error en la lectura del archivo.");
            System.out.println(e.getMessage());
        }
        return adminsHashSet;
    }

    public Administrador registroDeCuentaDeAdmin(Scanner scanner) throws MenorDeEdadException {
        Administrador administrador = new Administrador();
        String cadenaAux = null, contrasenia = null;
        boolean flag = false;
        this.administradores = extraerAdministradoresFromJSON(ARCHIVO_ADMINISTRADORES);

        System.out.println("Bienvenido Administrador! Ingrese los datos correspondientes >> ");

        do {
            System.out.println("1) Ingrese su nombre: ");
            cadenaAux = scanner.nextLine();
            try {
                flag = Persona.verificarEsLetra(cadenaAux);
                if (!flag)
                    System.out.println("Recuerde que su nombre son solo letras!.");
                else {
                    administrador.setNombre(cadenaAux);
                }
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        } while (!flag);

        do {
            System.out.println("2) Ingrese su apellido: ");
            cadenaAux = scanner.nextLine();
            try {
                flag = Persona.verificarEsLetra(cadenaAux);
                if (!flag)
                    System.out.println("Recuerde que su apellido son solo letras!.");
                else {
                    administrador.setApellido(cadenaAux);
                }
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        } while (!flag);

        System.out.println("3) Ingrese su edad");
        int edad = scanner.nextInt();
        try {
            flag = Persona.verificarEdad(edad);
            if (!flag)
                throw new MenorDeEdadException();
            else
                administrador.setEdad(edad);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

        do {
            scanner.nextLine();
            System.out.println("4) Ingrese su DNI: ");
            cadenaAux = scanner.nextLine();
            try {
                flag = Persona.verificarEsNumero(cadenaAux);
                if (flag) {
                    flag = Persona.verificarLongitudDNI(cadenaAux);
                    if (!flag)
                        System.out.println("Error en la longitud del DNI!. Son 8 digitos.");
                    else
                        administrador.setDni(cadenaAux);
                } else
                    System.out.println("Error en el dni, no son todos digitos.");
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        } while (!flag);

        do {
            System.out.println("5) Ingrese su numero telefonico: ");
            cadenaAux = scanner.nextLine();
            try {
                flag = Persona.verificarEsNumero(cadenaAux);
                if (flag) {
                    flag = Persona.verificarCodigoDeArea(cadenaAux); //NO HAGO TRY CATCH DE ESTE METODO PQ EN TEORIA VERIFICAR COD AREA SI NO LANZO EXCEPCION, ES QUE SON TODOS DIGITOS.
                    if (!flag)
                        System.out.println("Error en el codigo de area del telefono!.");
                    else
                        administrador.setNroDeTelefono(cadenaAux);
                } else
                    System.out.println("Error en el numero telefonico, no son todos digitos.");
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
        } while (!flag);

        System.out.println("6) Ingrese su email: ");
        administrador.setEmail(scanner.nextLine());

        do {
            System.out.println("7) Finalmente ingrese su clave de administrador (debera recordarla y saberla solo usted): ");
            contrasenia = scanner.nextLine();
            try {
                Password password = new Password(contrasenia);
                administrador.setPassword(password);
                flag = true;
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
                flag = false;
            }catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        } while (!flag);

        int decision = 0;

        System.out.println("Desea anadir su tarjeta ahora o luego?. ");
        System.out.println("[1] Ahora.\n[2] Mas tarde.");
        decision = scanner.nextInt();

        if (decision == 1) {
            administrador.getTarjeta().cargarTarjeta(scanner);
        } else if (decision == 2) {
            System.out.println("Cuando desee comprar, debera cargar su tarjeta.");
        } else {
            System.out.println("Se equivoco de boton, no se cargara ningun dato de la tarjeta. Cuando desee comprar, debera cargar su tarjeta.");
        }

        if(!this.administradores.add(administrador)){
            throw new RuntimeException("No agrego el administrador, ya existe...///***...error.....fatal......");
        }

        exportarAdministradoresToJSON(ARCHIVO_ADMINISTRADORES, this.administradores);

        return administrador;
    }

    private Administrador getAdministradorByEmail(String email) {
        Set<Administrador> administradors = extraerAdministradoresFromJSON(ARCHIVO_ADMINISTRADORES);
        Administrador adminAretornar = null;
        for (Administrador admin : administradors) {
            if (admin.getEmail().equals(email)) {
                adminAretornar = admin;
            }
        }
        if (adminAretornar == null) throw new RuntimeException("Usuario no existe");
        return adminAretornar;
    }

    public Administrador iniciarSesionComoAdmin (Scanner scanner) {
        String email = null, contrasenia = null; //VARIABLES PARA GUARDAR LOS DATOS IMPORTANTE POR SEPARADO.
        Administrador administrador = null; //DECLARO UN USUARIO, PARA QUE SI LO INGRESADO ES CORRECTO, EN EL MAIN ESTE COMO USUARIO ACTUAL.
        int i = 0; //REPRESENTA LOS INTENTOS DE INICIAR SESION.

        System.out.println("Bienvenido! Ingrese los datos correspondientes para iniciar sesion >> ");
        boolean login = false;
        do {
            System.out.println("1) Ingrese su email: ");
            email = scanner.nextLine();
            System.out.println("2) Ingrese su clave de administrador: ");
            contrasenia = scanner.nextLine();

            try {
                administrador = getAdministradorByEmail(email);
                login = administrador.login(contrasenia);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }

            if (!login) i++;
            if (i == CANTIDAD_INTENTOS_INICIO_SESION) throw new IntentosMaximosDeInicioSesionAlcanzadoException();
        } while (!login);

        return administrador;
    }

    public Administrador buscarAdministradorPorDNI(String dni, Set<Administrador> administradores) throws NullPointerException {
        Administrador administrador = null;
        for (Administrador aux : administradores) {
            if (aux.getDni().equals(dni))
                administrador = aux;
        }
        if (administrador == null) throw new NullPointerException();
        return administrador;
    }

    public boolean modificarContraseniaDeAdministrador(Scanner scanner) {
        System.out.println("Desea modificar su contrasenia? (s/n): ");
        char c = scanner.next().charAt(0);
        scanner.nextLine();

        if (c == 's') {
            this.administradores = extraerAdministradoresFromJSON(ARCHIVO_ADMINISTRADORES); //OBTENGO EL ARCHIVO DADO QUE ES NECESARIO PARA VERIFICAR SI LA NUEVA CONTRASENIA QUE QUIERE AGREGAR LA PERSONA NO EXISTA.

            System.out.println("Ingrese su DNI para cambiar su contrasenia >>");
            String dni = scanner.nextLine();
            Administrador administrador = null;

            try {
                administrador = buscarAdministradorPorDNI(dni, this.administradores);
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }

            if (administrador != null) {
                System.out.println("Ingrese su nueva contrasenia >>");
                String contraseniaNueva = scanner.nextLine();
                try {
                    Password password = new Password(contraseniaNueva);
                    administrador.setPassword(password);
                    exportarAdministradoresToJSON(ARCHIVO_ADMINISTRADORES, this.administradores); //aca se modifica la contrasenia de ese admin en el archivo.
                    return true;
                } catch (IllegalArgumentException e) {
                    System.out.println("Error con la contrasenia! Ingrese una nueva.");
                }
            } else {
                System.out.println("No se encontraron resultados...");
            }
        }
        return false;
    }

    public boolean modificarEmailDeAdministrador(Scanner scanner) {
        System.out.println("Desea modificar su email? (s/n): ");
        char c = scanner.next().charAt(0);
        scanner.nextLine();

        if (c == 's') {
            this.administradores = extraerAdministradoresFromJSON(ARCHIVO_ADMINISTRADORES); //OBTENGO EL ARCHIVO PORQUE ES NECESARIO PARA BUSCAR POR DNI Y LUEGO APLICAR LOS CAMBIOS.

            System.out.println("Ingrese su dni para el cambio de email >>");
            String dni = scanner.nextLine();
            Administrador administrador = null;

            try {
                administrador = buscarAdministradorPorDNI(dni, this.administradores);
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }

            if (administrador != null) {
                System.out.println("Ingrese su nuevo email para su cuenta de PedidosYa >>");
                String emailNuevo = scanner.nextLine();
                administrador.setEmail(emailNuevo); //SETEO EL NUEVO MAIL.
                exportarAdministradoresToJSON(ARCHIVO_ADMINISTRADORES, this.administradores); //APLICO LOS CAMBIOS EN EL ARCHIVO.
                return true;
            } else {
                System.out.println("No se han encontrado resultados...");
            }
        }
        return false;
    }

    public boolean modificarNroTelefonoDeAdministrador(Scanner scanner) {
        System.out.println("Desea modificar su numero de telefono? (s/n): ");
        char c = scanner.next().charAt(0);

        if (c == 's') {
            this.administradores = extraerAdministradoresFromJSON(ARCHIVO_ADMINISTRADORES); //OBTENGO EL ARCHIVO PORQUE ES NECESARIO PARA BUSCAR POR DNI Y LUEGO APLICAR LOS CAMBIOS.

            System.out.println("Ingrese su dni para el cambio de numero de telefono >>");
            scanner.nextLine();
            String dni = scanner.nextLine();
            Administrador administrador = null;

            try {
                administrador = buscarAdministradorPorDNI(dni, this.administradores);
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }

            if (administrador != null) {
                System.out.println("Ingrese su nuevo numero de telefono para su cuenta de PedidosYa >>");
                String telefono = scanner.nextLine();

                try {
                    if (!Persona.verificarCodigoDeArea(telefono) && Persona.verificarEsNumero(telefono) && Persona.verificarLongitudTelefono(telefono))
                        System.out.println("Error en el telefono ingresado. Verifique el numero de area, si ha ingresado todos numeros o si la longitud es de 8 digitos.");
                    else {
                        administrador.setNroDeTelefono(telefono);
                        exportarAdministradoresToJSON(ARCHIVO_ADMINISTRADORES, this.administradores);
                        return true;
                    }
                } catch (NullPointerException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("No se han encontrado resultados...");
            }
        }
        return false;
    }

    public boolean modificarNombreYapellidoDeAdministrador(Scanner scanner) {
        System.out.println("Desea modificar su nombre y apellido de cuenta (si solo desea el nombre por ejemplo, aun asi ingrese el mismo apellido)? (s/n): ");
        char c = scanner.next().charAt(0);
        scanner.nextLine();

        if (c == 's') {
            Administrador administrador = null;
            this.administradores = extraerAdministradoresFromJSON(ARCHIVO_ADMINISTRADORES); //OBTENGO EL ARCHIVO PORQUE ES NECESARIO PARA BUSCAR POR DNI Y LUEGO APLICAR LOS CAMBIOS.

            System.out.println("Ingrese su dni para el cambio de nombre y apellido >>");
            String dni = scanner.nextLine();

            try {
                administrador = buscarAdministradorPorDNI(dni, this.administradores);
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }

            if (administrador != null) {
                System.out.println("Ingrese su nuevo nombre para su cuenta de PedidosYa >>");
                String nombreNuevo = scanner.nextLine();
                System.out.println("Ingrese su nuevo apellido para su cuenta de PedidosYa >>");
                String apellidoNuevo = scanner.nextLine();

                try {
                    if (!Persona.verificarEsLetra(nombreNuevo) && !Persona.verificarEsLetra(apellidoNuevo))
                        System.out.println("Error! Lo ingresado no son todas letras.");
                    else {
                        administrador.setNombre(nombreNuevo);
                        administrador.setApellido(apellidoNuevo);
                        exportarAdministradoresToJSON(ARCHIVO_ADMINISTRADORES, this.administradores);
                        return true;
                    }
                } catch (NullPointerException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return false;
    }

    public boolean cambiarTarjetaDeAdministrador(Scanner scanner) {
        System.out.println("Desea sacar su tarjeta actual y cargar una distinta? (s/n): ");
        char c = scanner.next().charAt(0);
        scanner.nextLine();

        if (c == 's') {
            this.administradores = extraerAdministradoresFromJSON(ARCHIVO_ADMINISTRADORES); //OBTENGO EL ARCHIVO PORQUE ES NECESARIO PARA BUSCAR POR DNI Y LUEGO APLICAR LOS CAMBIOS.
            System.out.println("Ingrese su dni para luego poder cargar la nueva tarjeta >>");
            String dni = scanner.nextLine();

            try {
                Administrador administrador = buscarAdministradorPorDNI(dni, this.administradores);
                if (administrador != null) {
                    administrador.getTarjeta().modificarTarjeta(scanner);
                    exportarAdministradoresToJSON(ARCHIVO_ADMINISTRADORES, this.administradores);
                    return true;
                }
            } catch (NullPointerException e) {
                System.out.println("Error! No se ha encontrado ningun resultado.");
            }
        }
        return false;
    }

    public boolean eliminarEmpresa(Empresa eliminar) throws NullPointerException{
        if (eliminar == null) throw new NullPointerException();
        return listaDeEmpresas.remove(eliminar);
    }

    public boolean agregarEmpresa(Empresa empresa) throws NullPointerException {
        if (empresa == null) throw new NullPointerException();
        return listaDeEmpresas.add(empresa);
    }

    public boolean agregarProductos(Set<TipoDeProductos> tipoDeProductos, Empresa empresa){
        if(buscarEmpresaSegunNombre(empresa.getNombre())==null) throw new RuntimeException("La empresa no existe..//***");
        if(tipoDeProductos==null) throw new RuntimeException("Producto vacio..//***");


        listaDeEmpresas.get(buscarEmpresaRetornaPosicion(empresa)).setProductosEmpresa(crearListaDeProductos(tipoDeProductos));
        return true;
    }

    public boolean eliminarProductos(TipoDeProductos tipoDeProductos, Empresa empresa){
        if(tipoDeProductos==null || empresa == null) throw new RuntimeException("Parametros invalidos..//***");

        listaDeEmpresas.get(buscarEmpresaRetornaPosicion(empresa)).getProductosEmpresa().remove(tipoDeProductos);
        return true;
    }

    public boolean eliminarUnUsuarioComoAdmin (String dni) throws NullPointerException{
        if (dni == null)throw new NullPointerException ();
        try {
            this.usuarios = extraerUsuariosFromJSON(ARCHIVO_USUARIOS);
            boolean flag = this.usuarios.remove(buscarUserPorDNI(dni, this.usuarios));
            if (flag) exportarUsuariosToJSON(ARCHIVO_USUARIOS, this.usuarios); //si no se elimino correctamente, no deberia modificar el archivo.
            return flag;
        }catch (NullPointerException e){
            System.out.println("No se encontro ninguna persona con ese dni.");
        }
        return false;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////// FINALIZA PARTE DE ADMIN

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////// PARTE DE EMPRESA

    public int buscarEmpresaRetornaPosicion(Empresa empresa){
        int i=0;
        for(Empresa aux : listaDeEmpresas){
            if (empresa.equals(aux)){
                return i;
            }
            i++;
        }
        throw new RuntimeException("No se encontro la empresa..//***");
    }

    public Empresa buscarEmpresaSegunNombre(String empresa, List<Empresa> listaBuscador) throws NullPointerException {
        if (empresa == null) throw new NullPointerException("Error! La empresa no puede ser nula.//*");
        Empresa buscada = null;
        for (Empresa auxiliar : listaBuscador) {

            if (auxiliar.getNombre().equals(empresa)) {
                buscada = auxiliar;
            }
        }

        if (buscada == null)
            throw new NullPointerException("Error!! La empresa buscada no esta en la lista de opciones");
        return buscada;
    }

    public Empresa buscarEmpresaSegunNombre(String empresa) throws NullPointerException {
        Empresa empresa1 = buscarEmpresaSegunNombre(empresa, listaDeEmpresas);
        return empresa1;
    }

    public void mostrarEmpresasSoloNombre(List<Empresa> listaBuscador) {
        for (Empresa empresa1 : listaBuscador) {
            System.out.println(empresa1.getNombre());
        }
    }

    public void filtrarMostrarEmpresaSegunZona(Zonas zonaActual){
        for(Empresa empresa : listaDeEmpresas){
            mostrarEmpresaSiCoincideConLaZonaActual(empresa, zonaActual);
        }

    }

    private void mostrarEmpresaSiCoincideConLaZonaActual(Empresa empresa, Zonas zonaActual) {
        for (Zonas zona : empresa.getZonas()){
            if (zona.equals(zonaActual)){
                System.out.println(empresa.getNombre() + " - Zonas: " + empresa.getZonas());
            }
        }
    }

    public void mostrarEmpresas() {
        for (Empresa empresa : listaDeEmpresas) {
            System.out.println("Muestro las empresas");
            empresa.mostrarEmpresa();
        }
    }

    public void cargarListaDeEmpresas(){ ///Los precios varian, hay empresas que no cobran envio al estar a cargo de la misma.
        listaDeEmpresas.add(new Empresa("LA MUSA", crearListaDeProductos(Set.of(BEBIDAS, EMPANADAS, PAPAS, PIZZA)), Set.of(PUERTO,CONSTITUCION,INDEPENDENCIA, BOSQUE, MOGOTES),250, 4));
        listaDeEmpresas.add(new Empresa("HAMBURGO", crearListaDeProductos(Set.of(BEBIDAS, HAMBURGUESAS, PAPAS, CARNES, ENSALADAS, PARRILLA)), Set.of(CENTRO,ALEM,RUMENCO, MOGOTES),250, 5));
        listaDeEmpresas.add(new Empresa("KONICHIWA", crearListaDeProductos(Set.of(BEBIDAS, ENSALADAS, PASTAS, SUSHI, CARNES, POSTRES, HAMBURGUESAS, EMPANADAS)), Set.of(PUERTO,INDEPENDENCIA, COLINAS,CONSTITUCION, MOGOTES),250, 3));
        listaDeEmpresas.add(new Empresa("DEDIEZ", crearListaDeProductos(Set.of(BEBIDAS, EMPANADAS, PAPAS, PIZZA, HAMBURGUESAS)), Set.of(ALEM,RUMENCO,CONSTITUCION,INDEPENDENCIA, LOS_TRONCOS, MOGOTES),250, 5));
        listaDeEmpresas.add(new Empresa("GRIDO", crearListaDeProductos(Set.of(HELADOS, POSTRES)), Set.of(ALEM,RUMENCO),250, 2));
        listaDeEmpresas.add(new Empresa("BANDERITA", crearListaDeProductos(Set.of(BEBIDAS, CARNES, EMPANADAS, ENSALADAS, PAPAS, PARRILLA, POLLO)), Set.of(PUERTO,CENTRO,ALEM, COLINAS, LOS_TRONCOS, MOGOTES,RUMENCO),250, 4));
        listaDeEmpresas.add(new Empresa("LA HAMBURGUESERIA", crearListaDeProductos(Set.of(BEBIDAS, HAMBURGUESAS, PAPAS)), Set.of(PUERTO,ALEM, COLINAS,INDEPENDENCIA, MOGOTES,RUMENCO),250, 4));
        listaDeEmpresas.add(new Empresa("ITALIA", crearListaDeProductos(Set.of(HELADOS, POSTRES)), Set.of(ALEM, CENTRO, LOS_TRONCOS),250, 5));
        listaDeEmpresas.add(new Empresa("MANDINGA", crearListaDeProductos(Set.of(BEBIDAS, CARNES, EMPANADAS, ENSALADAS, PAPAS, PARRILLA, POLLO, POSTRES, HAMBURGUESAS)), Set.of(CENTRO,INDEPENDENCIA,CONSTITUCION, LOS_TRONCOS, MOGOTES, BOSQUE,RUMENCO),250, 1));
        listaDeEmpresas.add(new Empresa("KIOSCO DA", crearListaDeProductos(Set.of(KIOSCO, POSTRES)), Set.of(ALEM,INDEPENDENCIA),250, 5));
        listaDeEmpresas.add(new Empresa("LO DE MARIO", crearListaDeProductos(Set.of(BEBIDAS, ENSALADAS, MILANESAS, PAPAS, PASTAS, POSTRES, CARNES,HELADOS, HAMBURGUESAS, PARRILLA)), Set.of(PUERTO,INDEPENDENCIA, LOS_TRONCOS,CONSTITUCION, COLINAS, MOGOTES,RUMENCO),250, 5));
        listaDeEmpresas.add(new Empresa("ANTARES", crearListaDeProductos(Set.of(CERVEZA, ENSALADAS, HAMBURGUESAS, PAPAS, PIZZA, CARNES, EMPANADAS, POLLO)), Set.of(CENTRO,ALEM, LOS_TRONCOS,CONSTITUCION, MOGOTES),250, 4));
        listaDeEmpresas.add(new Empresa("BAUM", crearListaDeProductos(Set.of(CERVEZA, EMPANADAS, HAMBURGUESAS, PAPAS, PIZZA, CARNES, ENSALADAS, POLLO)), Set.of(CENTRO,ALEM, LOS_TRONCOS, COLINAS,INDEPENDENCIA, MOGOTES),250, 4));
        listaDeEmpresas.add(new Empresa("CHEVERRY", crearListaDeProductos(Set.of(CERVEZA, ENSALADAS, HAMBURGUESAS, PAPAS, PIZZA, CARNES, POSTRES, PARRILLA,HELADOS, EMPANADAS)), Set.of(CENTRO,ALEM,RUMENCO,INDEPENDENCIA, BOSQUE),250, 1));
        listaDeEmpresas.add(new Empresa("GIANELLI", crearListaDeProductos(Set.of(HELADOS, POSTRES)), Set.of(PUERTO,CONSTITUCION),250, 1));
        listaDeEmpresas.add(new Empresa("KIOSCO FLOR", crearListaDeProductos(Set.of(KIOSCO, BEBIDAS, POSTRES)), Set.of(CENTRO, CONSTITUCION), 150, 3));
        listaDeEmpresas.add(new Empresa("KIOSCO EXPRESS", crearListaDeProductos(Set.of(KIOSCO, BEBIDAS, POSTRES)), Set.of(ALEM, LOS_TRONCOS, RUMENCO), 200, 5));
        listaDeEmpresas.add(new Empresa("KIOSCO FRESCOS", crearListaDeProductos(Set.of(KIOSCO, BEBIDAS, HELADOS)), Set.of(PUERTO, MOGOTES, CENTRO, INDEPENDENCIA), 180, 4));
        listaDeEmpresas.add(new Empresa("EL CLUB DE LA MILANESA", crearListaDeProductos(Set.of(BEBIDAS, MILANESAS, PAPAS, CARNES, POSTRES, ENSALADAS,HELADOS, POLLO)), Set.of(CENTRO,ALEM, LOS_TRONCOS,INDEPENDENCIA,CONSTITUCION, BOSQUE),250, 5));

        listaDeEmpresas.add(new Empresa("LA PARRILLITA", crearListaDeProductos(Set.of(CERVEZA,CARNES, PARRILLA, ENSALADAS, PAPAS, POSTRES, BEBIDAS, POLLO)), Set.of(CENTRO, ALEM, INDEPENDENCIA, MOGOTES, LOS_TRONCOS), 200, 4));
        listaDeEmpresas.add(new Empresa("PIZZERIA DELFINO", crearListaDeProductos(Set.of(CERVEZA,PIZZA, ENSALADAS, PAPAS, POSTRES, BEBIDAS, EMPANADAS)), Set.of(PUERTO,ALEM, LOS_TRONCOS, CONSTITUCION, RUMENCO), 150, 4));
        listaDeEmpresas.add(new Empresa("SUSHI EXPRESS", crearListaDeProductos(Set.of(SUSHI, ENSALADAS, POSTRES, BEBIDAS, EMPANADAS)), Set.of(CENTRO, PUERTO, MOGOTES, BOSQUE, CONSTITUCION), 180, 5));
        listaDeEmpresas.add(new Empresa("LA CANTINA MEXICANA", crearListaDeProductos(Set.of(CERVEZA,SUSHI, ENSALADAS, BEBIDAS,PIZZA, EMPANADAS, POLLO)), Set.of(CENTRO, ALEM, CONSTITUCION), 120, 4));
        listaDeEmpresas.add(new Empresa("PASTELERIA SWEET DELIGHTS", crearListaDeProductos(Set.of(HELADOS, POSTRES, BEBIDAS)), Set.of(PUERTO,CENTRO, MOGOTES, BOSQUE, CONSTITUCION), 100, 3));
    }

    public Empresa buscarEmpresaConMetodoElegido (Scanner scanner, Zonas zonaActual){
        Empresa buscada=null;
        System.out.println("Bienvenido!! Elija el metodo por el que quiere buscar locales\n 1_Buscar Empresa por nombre\n 2_Buscar por comidas.\n Presione cualquier otra tecla para salir");
        int numero= scanner.nextInt();

        switch (numero){
            case 1->{
                do{
                    System.out.println("Empresas disponibles: ");
                    filtrarMostrarEmpresaSegunZona(zonaActual);
                    buscada=buscarPorNombreSinSerExacto(scanner, zonaActual);
                } while (buscada==null);
            }
            case 2->{
                do{
                    buscada=buscarEmpresaSegunQueQuiereComer(scanner, zonaActual);
                    System.out.println("Aprete enter para continuar.....");
                    scanner.nextLine();
                } while (buscada==null);
            }
            case default ->{
                System.out.println("Saliendo");
                return null;
            }
        }
        return buscada;
    }

    public Empresa buscarEmpresaSegunQueQuiereComer(Scanner scanner, Zonas zonaActual) {
        scanner.nextLine();
        mostrarTodosLosTiposDeProducto();
        boolean flag= false;
        TipoDeProductos dato=null;
        do {
            System.out.println("Que desea comer?");
            try {

                String comida = scanner.nextLine();

                dato = TipoDeProductos.valueOf(comida.toUpperCase());
                flag=true;
            } catch (IllegalArgumentException e) {
                System.out.println("Tipo de producto no valido, ingrese uno de la lista.");
                flag=false;
            }
        }while (!flag);

        List<Empresa> listaBuscador = new ArrayList<>();
        listaBuscador = crearListaEmpresas(dato, zonaActual);

        mostrarEmpresasSoloNombre(listaBuscador);

        Empresa empresa1 = buscarPorNombreSinSerExacto(scanner, listaBuscador, zonaActual);

        return empresa1;
    }

    public Empresa buscarPorNombreSinSerExacto(Scanner scanner, Zonas zonaActual) {
        Empresa buscada = new Empresa();
        List<Empresa> listaEmpresas = new ArrayList<>();
        String nombre=null;
        scanner.nextLine();

        do {
            System.out.println("Ingrese el nombre de la empresa que busca.");
            nombre = scanner.nextLine().toUpperCase();

            listaEmpresas = crearListaEmpresas(nombre, zonaActual);
        } while (listaEmpresas.size() != 1);

        System.out.println("Desea comprar en: " + listaEmpresas.get(0).getNombre() + "? s/n");
        char confirmacion = scanner.nextLine().charAt(0);
        if (confirmacion == 's') return listaEmpresas.get(0);
        else {
            System.out.println("Volviendo al menu principal");
            return null;
        }
    }

    public Empresa buscarPorNombreSinSerExacto(Scanner scanner, List<Empresa> listaDisminuida, Zonas zonaActual) {
        Empresa buscada = new Empresa();
        List<Empresa> listaEmpresas = new ArrayList<>();
        do {
            System.out.println("Ingrese el nombre de la empresa que busca.");
            String nombre = scanner.nextLine().toUpperCase();

            listaEmpresas = crearListaEmpresas(nombre, listaDisminuida, zonaActual);

            if (listaEmpresas.size() > 1) {
                System.out.println("Lista de empresas posibles, elija especificamente la que desea");
                mostrarEmpresasSoloNombre(listaEmpresas);
            }

        } while (listaEmpresas.size() != 1);

        System.out.println("Desea comprar en: " + listaEmpresas.get(0).getNombre() + "? s/n");
        char confirmacion = scanner.nextLine().charAt(0);
        if (confirmacion == 's') return listaEmpresas.get(0);
        else {
            System.out.println("Volviendo al menu principal");
            return null;
        }
    }

    public void mostrarTodosLosTiposDeProducto() {
        // Obtener todos los valores del enum
        TipoDeProductos[] elementos = TipoDeProductos.values();

        // Mostrar los elementos
        for (TipoDeProductos elemento : elementos) {
            System.out.println(elemento);
        }
    }

///Segun el tipo de comida permite buscar por nombre con una lista filtrada
    public List<Empresa> crearListaEmpresas (String nombre, List < Empresa > listaDisminuida, Zonas zonaActual){
            List<Empresa> listaBuscador = new ArrayList<>();
            for (Empresa empresa : listaDisminuida) {
                if (empresa.getNombre().contains(nombre) && empresa.getZonas().contains(zonaActual)) {
                    listaBuscador.add(empresa);
                }
            }
            return listaBuscador;
    }
    ///Crea una lista para el admin
    public List<Empresa> crearListaEmpresas (String nombre, List < Empresa > listaDisminuida){
        List<Empresa> listaBuscador = new ArrayList<>();
        for (Empresa empresa : listaDisminuida) {
            if (empresa.getNombre().contains(nombre)) {
                listaBuscador.add(empresa);
            }
        }
        return listaBuscador;
    }

    ///Permite buscar por nombre entre todas las opciones en la zona
    public List<Empresa> crearListaEmpresas (String nombre, Zonas zonaActual){
          return crearListaEmpresas(nombre,listaDeEmpresas,zonaActual);
    }

    public List crearListaEmpresas (TipoDeProductos comida, Zonas zonaActual){ /// Crea una lista segun el tipo de comida

            List<Empresa> listaBuscador = new ArrayList<>();
            for (Empresa empresa : listaDeEmpresas) {
                if (empresa.getProductosEmpresa().containsKey(comida) && empresa.getZonas().contains(zonaActual)) {
                    listaBuscador.add(empresa);
                }
            }
            return listaBuscador;
    }

    public Empresa cargarUnTipoProducto (Empresa A, TipoDeProductos tipo){
        A.getProductosEmpresa().put(tipo,crearHashSetSegunTipoDeProducto(tipo));
        return A;
    }

    public Empresa EliminarUnTipoProducto (Empresa A, TipoDeProductos tipo){
        A.getProductosEmpresa().remove(tipo);
        return A;
    }


    private LinkedHashMap<TipoDeProductos, HashSet<Producto>> crearListaDeProductos (Set < TipoDeProductos > tipoDeProductos)
    {  ///LE PASO UN ARRAYLIST CON LOS TIPOS DE PRODUCTOS QUE POSEE LA EMPRESA
            LinkedHashMap<TipoDeProductos, HashSet<Producto>> productosTotal = new LinkedHashMap<>();

            for (TipoDeProductos tipo : tipoDeProductos) { ///HAMBURGUESA, MILANESA, PAPASFRITAS
                productosTotal.put(tipo, crearHashSetSegunTipoDeProducto(tipo));
            }

            return productosTotal;
    }

    private HashSet<Producto> crearHashSetSegunTipoDeProducto (TipoDeProductos tipoDeProducto){
            HashSet<Producto> listaDeProductos = new HashSet<>();

            switch (tipoDeProducto) {

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
                }
                case CERVEZA -> {
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
                }
                default -> {
                    System.out.println("Dato no valido");
                }
            }
            return listaDeProductos;
    }

    public void agregarDescuento (String cupon, Carrito carrito) throws NullPointerException, RuntimeException {
            if (cupon == null) throw new NullPointerException("Error! El cupon no puede ser nulo.//***");
            if (!carrito.getVendedor().validarCupon(cupon)) throw new RuntimeException("Error! Cupon no valido.//***");

            carrito.setTieneCupon(true);
    }

    public void exportarEmpresasToJSON(String path) {
        File file = new File(ARCHIVO_EMPRESAS);
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValue(file, listaDeEmpresas);
        } catch (IOException e) {
            System.out.println("Error en la escritura del archivo.");
        }
    }

    public List<Empresa> extraerEmpresasFromJSON(String path) {
        File file = new File(ARCHIVO_EMPRESAS);
        ObjectMapper mapper = new ObjectMapper();
        List<Empresa> listaEmpresas = new LinkedList<>();

        try {
            Empresa [] empresas = mapper.readValue (file, Empresa[].class);
            listaDeEmpresas.addAll(Arrays.asList(empresas));
        } catch (IOException e) {
            System.out.println("Error en la lectura del archivo.");
            System.out.println(e.getMessage());
        }
        return listaEmpresas;
    }

    public Empresa cargarEmpresaPorTeclado (Scanner scanner){
        Empresa A= new Empresa();
        char control = 's';
        System.out.println("Cargue el nombre de la empresa");
        scanner.nextLine();
        A.setNombre(scanner.nextLine());

        System.out.println("Ingrese las zonas que trabaja la empresa. Una a la vez");
        mostrarTodasLasZonas();
        HashSet<Zonas> setDeZonas= new HashSet<>();
        do{
            String zona= scanner.nextLine().toUpperCase();
            setDeZonas.add(Zonas.valueOf(zona));

            System.out.println("Desea ingresar otra zona? s/n");
            control= scanner.nextLine().charAt(0);
        }while (control == 's');
        A.setZonas(setDeZonas);

        System.out.println("Ingrese los tipos de productos que trabaja la empresa. Una a la vez");
        mostrarTodosLosTiposDeProducto();
        LinkedHashMap<TipoDeProductos, HashSet<Producto>> productos= new LinkedHashMap();
        do{
            String prod= scanner.nextLine().toUpperCase();
            TipoDeProductos producto= TipoDeProductos.valueOf(prod);

            crearListaDeProductos(Set.of(producto));

            System.out.println("Desea ingresar otro tipo de producto? s/n");
            control= scanner.nextLine().charAt(0);
        }while (control == 's');
        A.setProductosEmpresa(productos);

        System.out.println("Cual es el costo de envio de sus productos?");
        A.setCostoDeEnvio(scanner.nextDouble());


        return A;
    }

    public void mostrarTodasLasZonas(){
        // Obtener todos los valores del enum
        Zonas[] elementos = Zonas.values();

        // Mostrar los elementos
        for (Zonas elemento : elementos) {
            System.out.println(elemento);
        }
    }

    public Empresa retornarUnaEmpresa(Scanner scanner) {
        Empresa buscada = new Empresa();

        System.out.println("Lista de empresas posibles, elija especificamente la que desea");
        mostrarEmpresasSoloNombre(listaDeEmpresas);

        System.out.println("Ingrese el nombre de la empresa que busca.");
        scanner.nextLine();
        String nombre = scanner.nextLine().toUpperCase();

        buscada = buscarEmpresaSegunNombre(nombre);

        if (buscada!=null){
            System.out.println("Esta seguro que desea elegir: " + buscada.getNombre() + "? s/n");
            char confirmacion = scanner.nextLine().charAt(0);
            if (confirmacion == 's') return buscada;
            else {
                System.out.println("Volviendo al menu principal");
                return null;
            }
        } else {
            System.out.println("La empresa no fue encontrada... error..... fatal.....");
            return null;
        }
    }

    public Zonas elegirZona (Scanner scanner){
        Zonas elegida=null;
        boolean flag=false;

        do {
            try {
                System.out.println("ELEGIR TU ZONA ACTUAL: ");
                System.out.println(Arrays.toString(Zonas.values()));

                //scanner.nextLine();
                String zonaElegida = scanner.nextLine();
                elegida = Zonas.valueOf(zonaElegida.toUpperCase());
                flag=true;
            } catch (IllegalArgumentException e) {
                System.out.println("Zona inexistente, ingrese el nombre exacto de la zona");
                flag=false;
            }
        }while (!flag);
    return elegida;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////// FINALIZA PARTE DE EMPRESA
}



