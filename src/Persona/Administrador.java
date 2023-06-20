package Persona;

import model.Carrito;
import model.HistorialDeCompras;
import model.Tarjeta;

import java.util.UUID;

public class Administrador extends Persona implements UtilidadUserAdm {
    private UUID idAdmin;
    private HistorialDeCompras historialDeCompras;
    private Carrito carrito;
    private Password password;
    private Tarjeta tarjeta;

    public Administrador() {
        super();
        idAdmin = UUID.randomUUID();
        tarjeta = new Tarjeta();
        carrito = new Carrito();
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
    }
    public Password getPassword() {
        return password;
    }

    public HistorialDeCompras getHistorialDeCompras() {
        return historialDeCompras;
    }
    public void setHistorialDeCompras(HistorialDeCompras historialDeCompras) {
        this.historialDeCompras = historialDeCompras;
    }

    public Carrito getCarrito() {
        return carrito;
    }
    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
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

