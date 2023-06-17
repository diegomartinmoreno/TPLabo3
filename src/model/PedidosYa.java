package model;
import Exceptions.MenorDeEdadException;
import Persona.Persona;
import Persona.Usuario;
import Persona.Administrador;
import Persona.UsuariosWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class PedidosYa {

    private List<Empresa>listaDeEmpresas;
    private Set <Usuario> usuarios;
    private Set <Administrador> administradores;
    public static final String ARCHIVO_USUARIOS = "Users.json";

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

    public static Usuario registroDeCuenta (Scanner scanner) throws MenorDeEdadException {
        Usuario usuario = new Usuario();
        String cadenaAux=null;
        boolean flag=false;

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
            String contrasenia = scanner.nextLine();
            try {
                flag = Usuario.verificarContraseniaSegura(contrasenia);
            }catch (NullPointerException e){
                System.out.println(e.getMessage());
            }
        } while (!flag);

        usuario.setContrasenia(cadenaAux);

        //FALTA PARTE DE TARJETA.



        return usuario;
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
            UsuariosWrapper usuariosWrapper = mapper.readValue(file, UsuariosWrapper.class);
            usuarioHashSet = usuariosWrapper.getUsuarios();
        }catch (IOException e){
            System.out.println("Error en la lectura del archivo.");
        }
        return usuarioHashSet;
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

    public void CargarListaDeEmpresas(){ ///Los precios varian, hay empresas que no cobran envio al estar a cargo de la misma.
        listaDeEmpresas.add(new Empresa("La Musa", 310));
        listaDeEmpresas.add(new Empresa("Lolapapusa", 420));
        listaDeEmpresas.add(new Empresa("La Hamburgueseria", 0));
        listaDeEmpresas.add(new Empresa("Torombolo", 310));
        listaDeEmpresas.add(new Empresa("Antares", 219));
        listaDeEmpresas.add(new Empresa("DeDiez", 310));
        listaDeEmpresas.add(new Empresa("Loris", 0));
        listaDeEmpresas.add(new Empresa("McDonalds", 310));
        listaDeEmpresas.add(new Empresa("El Club de la Milanesa", 420));
        listaDeEmpresas.add(new Empresa("Ogham", 310));
        listaDeEmpresas.add(new Empresa("Grido", 219));
        listaDeEmpresas.add(new Empresa("La Cremerie", 0));
    }

    public LinkedList<Producto> CrearListaDeProductos (){
        LinkedList <Producto> lista= new LinkedList<>();
        //lista.add(new model.Producto("La Musa Gold", "Pizza", "Pizza de Mozzarella premium", 2000,"La Musa"));
        //lista.add(new model.Producto("La Musa Gold", "Pizza", "Pizza de Mozzarella premium", 2000,"La Musa"));
        //lista.add(new model.Producto("La Musa Gold", "Pizza", "Pizza de Mozzarella premium", 2000,"La Musa"));
        //lista.add(new model.Producto("La Musa Gold", "Pizza", "Pizza de Mozzarella premium", 2000,"La Musa"));
        //lista.add(new model.Producto("La Musa Gold", "Pizza", "Pizza de Mozzarella premium", 2000,"La Musa"));

        //lista.add(new model.Producto("Big Popa", "Hamburguesa", "Hamburguesa con cheddar, panceta, cebolla caramelizada y salsa mil islas", 1800,"Lolapapusa"));


        return lista;
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

