package model;


import java.util.Objects;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Scanner;
import java.time.LocalDate;

public class Tarjeta {
	
	private String numeroTarjeta; /// (16 digitos)
	private String titular;
	private String fechaDeVencimiento;
	private String codigoDeSeguridad; /// (3 digitos)
	private double saldo;
	private boolean activa;
	
	
	public Tarjeta() {
		super();
		this.numeroTarjeta= "N/D.";
		this.titular= "N/D.";
		this.fechaDeVencimiento="N/D.";
		this.codigoDeSeguridad="N/D";
		this.saldo=-1;
		this.activa=false;
	}
	

	public Tarjeta(String numeroTarjeta, String titular, String fechaDeVencimiento, String codigoDeSeguridad,
			double saldo, boolean activa) {
		super();
		this.numeroTarjeta = numeroTarjeta;
		this.titular = titular;
		this.fechaDeVencimiento = fechaDeVencimiento;
		this.codigoDeSeguridad = codigoDeSeguridad;
		this.saldo = saldo;
		this.activa = activa;
	}
	
	public boolean cargarTarjeta (Scanner lectura) {
		boolean flag=true;
		System.out.println("Inicio de carga de nueva tarjeta.");
		do {
			System.out.println("Ingrese nombre del titular:");
			this.titular= lectura.nextLine();
			if (!Tarjeta.verificaEsNumero(this.titular)) {
				flag=false;
			}else {
				System.out.println("Nombre invalido.");
			}
		}while (flag);
		
		flag=true;
		
		do {
			System.out.println("Ingrese numero de la tarjeta:");
			this.numeroTarjeta=lectura.nextLine();
			if (Tarjeta.verificaEsNumero(this.numeroTarjeta) && this.numeroTarjeta.length()==16) {
				flag=false;
			}else {
				System.out.println("Numero invalido.");
			}
		}while(flag);
		
		flag=true;
		
		do {
			System.out.println("Ingrese fecha de vencimiento (MM/AAAA):");
			this.fechaDeVencimiento=lectura.nextLine();
			if (this.VerificarVencimiento()) {
				flag=false;
			}
		}while (flag);
		
		flag= true;
		
		do {
			System.out.println("Ingrese codigo de seguridad:");
			this.codigoDeSeguridad=lectura.nextLine();
			if (Tarjeta.verificaEsNumero(this.codigoDeSeguridad) && this.codigoDeSeguridad.length()==3) {
				flag=false;
			}else {
				System.out.println("Numero invalido.");
			}
		}while(flag);
		
		flag= true;
		
		do {
			System.out.println("Ingrese saldo limite de la tarjeta:");
			this.saldo=Double.parseDouble(lectura.nextLine());
			if (this.saldo>0) {
				flag=false;
			}else {
				System.out.println("Ingrese un saldo limite positivo.");
			}
		}while(flag);
		
		this.activa=true;
		
		return !flag;
	}

	public static boolean verificaEsNumero(String numero) {
		if (numero.matches("[0-9]+")){
			return true;
		}else {
			return false;
		}
	}
		

	
	public boolean VerificarVencimiento() {
		
		LocalDate ahora=LocalDate.now();
		
		//Convierto String almacenado a LocalDate para comparar las fechas.
		try {
			LocalDate LDfechaDeVencimiento=LocalDate.now();
			DateTimeFormatter fmt = new DateTimeFormatterBuilder()
				    .appendPattern("MM/yyyy")
				    .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
				    .toFormatter();
			LDfechaDeVencimiento=LocalDate.parse(this.fechaDeVencimiento, fmt);
			if (LDfechaDeVencimiento.isAfter(ahora)) {
				return true;
			}else {
				System.out.println("La fecha de expiracion de la tarjeta ya esta vencida.");
				return false;
			}
		} catch(Exception e){
			System.out.println("La fecha ingresada es invalida.");
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(numeroTarjeta);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tarjeta other = (Tarjeta) obj;
		return Objects.equals(numeroTarjeta, other.numeroTarjeta);
	}

	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}

	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public String getFechaDeVencimiento() {
		return fechaDeVencimiento;
	}

	public void setFechaDeVencimiento(String fechaDeVencimiento) {
		this.fechaDeVencimiento = fechaDeVencimiento;
	}

	public String getCodigoDeSeguridad() {
		return codigoDeSeguridad;
	}

	public void setCodigoDeSeguridad(String codigoDeSeguridad) {
		this.codigoDeSeguridad = codigoDeSeguridad;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public boolean isActiva() {
		return activa;
	}

	public void setActiva(boolean activa) {
		this.activa = activa;
	}

	@Override
	public String toString() {
		if (this.numeroTarjeta.length()==16) {
			return "Tarjeta Número= XXXX XXXX XXXX " + this.numeroTarjeta.substring(12, 16) + ", titular=" + titular;
		}else {
			return "La tarjeta no ha sido cargada aun.";
		}
		
	}

	public String mostrarTarjeta (){
		return this.toString();
	}
	
	public void BloquearTarjeta (){
		System.out.println("La tarjeta " + this.mostrarTarjeta() + " ha sido bloqueada.");
		this.activa=false;
	}
	
	public void DesbloquearTarjeta (){
		System.out.println("La tarjeta " + this.mostrarTarjeta() + " ha sido desbloqueada.");
		this.activa=true;
	}
	
	public boolean VerificarSaldoSuficiente(double monto){
		if (this.saldo<monto){
			return false;
		}
		return true;
	}
	
	public boolean RealizarPago (double monto) { /// RETORNA SI SE PUDO REALIZAR EL PAGO
		if (this.VerificarSaldoSuficiente(monto)){
			System.out.println("El saldo de la tarjeta es insuficiente para realizar la compra.");
			return false;
		} else {
			if (this.VerificarVencimiento()) {
				this.saldo -= monto;
				return true;
			}else {
				System.out.println("La tarjeta esta vencida.");
				this.BloquearTarjeta();
				return false;
			}
		}
	}

	public static void main (String[] Args) {
		Tarjeta tar= new Tarjeta();
		System.out.println(tar.toString());
		Scanner s= new Scanner(System.in);
		if (tar.cargarTarjeta(s)) {
			System.out.println(tar.toString());
		}
	}

}


