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
}

