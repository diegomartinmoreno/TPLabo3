package model;
import Persona.Persona;

import java.util.Scanner;

public class Repartidor extends Persona {
	final double gananciaPorPedido = 500;
	private double TotalCobradoPorRepartidor=0;
	
	public Repartidor() {
	}

	public Repartidor(String nombre, String apellido, String nroDeTelefono, int edad, String email, String dni) {
		super(nombre, apellido, nroDeTelefono, edad, email, dni);
	}

	public boolean cargarRepartidor(Carrito pedidoATrasladar, Scanner scan) {
		boolean flag=true;
		do {
			System.out.println("Ingrese nombre del repartidor:");
			String lectura;
			lectura=scan.nextLine();
			if (!Tarjeta.verificaEsNumero(lectura)) {
				flag=true;
			}
		}while (flag);

		
		this.TotalCobradoPorRepartidor=0;
		
		return flag;
	}

	public double getGananciaPorPedido() {
		return gananciaPorPedido;
	}

	public void cobrarLoDelDia(){
		System.out.println("El repartidor " + getNombre() + " se lleva una ganancia de $" + getTotalCobradoPorRepartidor());
	}

	public void setTotalCobradoPorRepartidor(double totalCobradoPorRepartidor) {
		TotalCobradoPorRepartidor = totalCobradoPorRepartidor;
	}


	public double getTotalCobradoPorRepartidor() {
		return TotalCobradoPorRepartidor;
	}

	public void llevarPedido(){
		this.TotalCobradoPorRepartidor+=this.gananciaPorPedido;
	}

	public void mostrarRepartidor(){
		System.out.println(getNombre() + " " + getApellido());
	}

	@Override
	public String toString() {
		return super.toString() +
				"Repartidor{" +
				"gananciaPorPedido=" + gananciaPorPedido +
				", TotalCobradoPorRepartidor=" + TotalCobradoPorRepartidor +
				'}';
	}
}
	
	

