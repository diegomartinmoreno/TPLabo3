import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class PedidosYa {

    private List<Empresa>listaDeEmpresas;


    public int buscarPosEmpresaSegunNombre(String nombre){
        int pos=-1;
        for (Empresa empresa1 : listaDeEmpresas){
            if (empresa1.getNombre().equals(nombre)){
                pos = listaDeEmpresas.indexOf(empresa1);
            }
        }

        if (pos==-1){
            System.out.println("ERROR///// no se encuentra la empresa");
        }

        return pos;
    }

    public List<Empresa> getListaDeEmpresas() {
        return listaDeEmpresas;
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
        //lista.add(new Producto("La Musa Gold", "Pizza", "Pizza de Mozzarella premium", 2000,"La Musa"));
        //lista.add(new Producto("La Musa Gold", "Pizza", "Pizza de Mozzarella premium", 2000,"La Musa"));
        //lista.add(new Producto("La Musa Gold", "Pizza", "Pizza de Mozzarella premium", 2000,"La Musa"));
        //lista.add(new Producto("La Musa Gold", "Pizza", "Pizza de Mozzarella premium", 2000,"La Musa"));
        //lista.add(new Producto("La Musa Gold", "Pizza", "Pizza de Mozzarella premium", 2000,"La Musa"));

        //lista.add(new Producto("Big Popa", "Hamburguesa", "Hamburguesa con cheddar, panceta, cebolla caramelizada y salsa mil islas", 1800,"Lolapapusa"));


        return lista;
    }

    public void PasarListaDeProductosAHashMapDeEmpresas (){ ///Primero creo una lista de productos, en base al nombre de la empresa lo acomodo.
        ///El primer atributo del hashmap es el tipo, el cual es un atributo del producto, el segundo ya es el producto especifico, de ese modo se los encuentra.


    }
}

