package Persona;

import model.Tarjeta;

import java.util.UUID;

public class Administrador extends Persona implements UtilidadUserAdm {
    private UUID idAdmin;
    private String claveInicio;
    private Tarjeta tarjeta;

    public Administrador() {
        super();
        idAdmin = UUID.randomUUID();
    }
    public Administrador(String nombre, String apellido, String nroDeTelefono, int edad, String email, String dni) {
        super(nombre, apellido, nroDeTelefono, edad, email, dni);
        idAdmin = UUID.randomUUID();
    }
    public Administrador(String nombre, String apellido, String nroDeTelefono, int edad, String email, String dni, String claveInicio) {
        super(nombre, apellido, nroDeTelefono, edad, email, dni);
        idAdmin = UUID.randomUUID();
        this.claveInicio = claveInicio;
    }

    public UUID getIdAdmin() {
        return idAdmin;
    }
    public void setIdAdmin(UUID idAdmin) {
        this.idAdmin = idAdmin;
    }

    @Override
    public void agregarDinero(double dinero) {
        System.out.println("ADMIN.....//***...//");
        tarjeta.setSaldo(999999999);

    }

    @Override
    public boolean verificarClave() {
        return false;
    }

    //el toString no deberia mostrar ni la clave ni la id dado que no es necesario.








}

