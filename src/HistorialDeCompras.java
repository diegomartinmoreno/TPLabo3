import java.util.ArrayList;
import java.util.List;

public class HistorialDeCompras {
    private List<Carrito> pedidos;

    public HistorialDeCompras() {
        pedidos = new ArrayList<>();
    }

    public void agregarPedido(Carrito carrito){
        pedidos.add(carrito);
    }
}
