package model;

import java.util.ArrayList;
import java.util.List;

public class HistorialDeCompras {
    private List<Carrito> pedidos;

    public HistorialDeCompras() {
        pedidos = new ArrayList<>();
    }

    public void listarHistorial(){
        for (Carrito carrito : pedidos){
            carrito.listarCarrito();
        }
    }

    public boolean agregarPedido(Carrito carrito){
        return pedidos.add(carrito);
    }

    public void limpiarHistorialDeCompras (){
        pedidos.clear();
    }
}
