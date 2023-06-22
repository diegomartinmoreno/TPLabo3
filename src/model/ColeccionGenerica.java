package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ColeccionGenerica<T> implements Serializable {
    private List<T> productos;

    public ColeccionGenerica() {
        productos = new ArrayList<>();
    }

    public boolean add(T t){
        return productos.add(t);
    }
    public boolean remove(int pos){
        if(productos.remove(pos)!=null) {
            return true;
        }

        return false;
    }

    public void listar(){
        System.out.println(productos);
    }

    public T get(int pos){
        return productos.get(pos);
    }


    public T buscar(T t){
        for (T elemento : productos){
            if (t.equals(elemento)){
                return elemento;
            }
        }
        return null;
    }



    public List<T> getProductos() {
        return productos;
    }

    public void setProductos(List<T> productos) {
        this.productos = productos;
    }

}
