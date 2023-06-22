package model;

import java.util.ArrayList;
import java.util.List;

public class HistorialDeCompras {
    private List<Pedido> pedidos;

    public HistorialDeCompras() {
        pedidos = new ArrayList<>();
    }

    public void listarHistorial(){
        for (Pedido pedido : pedidos){
            pedido.mostrarPedido();
            System.out.println("////////////////////////////////////////////////////////////");
        }
    }

    public boolean agregarPedido(Pedido pedido){
        return pedidos.add(pedido);
    }

    public void limpiarHistorialDeCompras (){
        pedidos.clear();
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

}
