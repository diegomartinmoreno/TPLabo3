package Persona;

import model.Tarjeta;

import java.util.UUID;

public class Administrador extends Persona implements UtilidadUserAdm {
    private UUID idAdmin;
    private Password password;
    private Tarjeta tarjeta;

    public Administrador() {
        super();
        idAdmin = UUID.randomUUID();
    }
    public Administrador(String nombre, String apellido, String nroDeTelefono, int edad, String email, String dni) {
        super(nombre, apellido, nroDeTelefono, edad, email, dni);
        idAdmin = UUID.randomUUID();
    }
    public Administrador(String nombre, String apellido, String nroDeTelefono, int edad, String email, String dni, Password password) {
        super(nombre, apellido, nroDeTelefono, edad, email, dni);
        idAdmin = UUID.randomUUID();
        this.password = password;
    }

    public UUID getIdAdmin() {
        return idAdmin;
    }
    public void setIdAdmin(UUID idAdmin) {
        this.idAdmin = idAdmin;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }
    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    public void setPassword(Password password) {
        this.password = password;
        System.out.println(this.password.toString());
    }
    public Password getPassword() {
        return password;
    }

    @Override
    public void agregarDinero(double dinero) {
        System.out.println("ADMIN.....//***...//");
        tarjeta.setSaldo(999999999);
    }

    @Override
    public boolean login(String contrasenia) {
        return password.validate(contrasenia);
    }


}

