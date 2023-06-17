package model;
import java.util.Scanner;

public class Repartidor {

	private Carrito pedidoAtrasladar;
	final double gananciaPorPedido = 500;
	private double TotalCobradoPorRepartidor;
	private double montoACobrar;
	private String nombreRepartidor;
	
	public Repartidor() {
		super();
	}
	
	public boolean cargarRepartidor(Carrito pedidoATrasladar, Scanner scan) {
		boolean flag=true;
		do {
			System.out.println("Ingrese nombre del repartidor:");
			String lectura;
			lectura=scan.nextLine();
			if (!Tarjeta.verificaEsNumero(lectura)) {
				this.nombreRepartidor=lectura;
				flag=true;
			}
		}while (flag);
		
		this.pedidoAtrasladar=pedidoATrasladar;
		
		this.montoACobrar= this.calcularMontoACobrar();
		
		this.TotalCobradoPorRepartidor=0;
		
		return flag;
	}
	
	public Carrito getPedidoAtrasladar() {
		return pedidoAtrasladar;
	}
	
	public void setPedidoAtrasladar(Carrito pedidoAtrasladar) {
		this.pedidoAtrasladar = pedidoAtrasladar;
		this.montoACobrar = this.calcularMontoACobrar();
	}
	
	public double getTotalCobradoPorRepartidor() {
		return TotalCobradoPorRepartidor;
	}
	
	public double getMontoACobrar() {
		return this.calcularMontoACobrar();
	}
	
	public String getNombreRepartidor() {
		return nombreRepartidor;
	}
	public void setNombreRepartidor(String nombreRepartidor) {
		this.nombreRepartidor = nombreRepartidor;
	}

	public double calcularMontoACobrar() {
		return this.pedidoAtrasladar.calcularMontoTotalDeLaCompra()+this.gananciaPorPedido;
	}
	
	public void llevarPedido(){
		this.TotalCobradoPorRepartidor+=this.gananciaPorPedido;
	}
	
	
}
	
	

