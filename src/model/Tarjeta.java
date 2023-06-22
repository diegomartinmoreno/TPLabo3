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
	

	public Tarjeta(String numeroTarjeta, String titular, String fechaDeVencimiento, String codigoDeSeguridad, double saldo, boolean activa) {
		super();
		this.numeroTarjeta = numeroTarjeta;
		this.titular = titular;
		this.fechaDeVencimiento = fechaDeVencimiento;
		this.codigoDeSeguridad = codigoDeSeguridad;
		this.saldo = saldo;
		this.activa = activa;
	}
	
	public boolean cargarTarjeta (Scanner lectura) {
		boolean flag;
		System.out.println("Inicio de carga de nueva tarjeta.");
		do {
			System.out.println("Ingrese nombre del titular:");
			lectura.nextLine();
			flag=!this.cargarNombreTitular(lectura.nextLine());
		}while (flag);
		////
		do {
			System.out.println("Ingrese numero de la tarjeta:");
			flag=!this.cargarNumeroDeTarjeta(lectura.nextLine());
		}while(flag);
		////
		do {
			flag=true;
			System.out.println("Ingrese fecha de vencimiento (MM/AAAA):");
			this.fechaDeVencimiento=lectura.nextLine();
			if (!EstaVencida()) {
				flag=false;
			}
		}while (flag);
		////
		do {
			System.out.println("Ingrese codigo de seguridad:");
			flag=!this.cargarCodigoDeSeguridad(lectura.nextLine());
		}while(flag);
		///
		do {
			System.out.println("Ingrese saldo limite de la tarjeta:");
			String saldoLimite = lectura.nextLine();
			flag=!this.cargarSaldoLimiteTarjeta(saldoLimite);
		}while(flag);
		
		this.activa=true;
		
		return !flag;
	}
	
	public void modificarTarjeta (Scanner lectura) {
		boolean flag;
		System.out.println("Inicio de modificacion de tarjeta:");
		///
		System.out.println("Desea modificar nombre del titular? S/N");
		if (this.SINO(lectura.nextLine())) {
			do {
				System.out.println("Ingrese nombre del titular:");
				flag=!this.cargarNombreTitular(lectura.nextLine());
			}while (flag);
		}
		///
		System.out.println("Desea modificar numero de la tarjeta? S/N");
		if (this.SINO(lectura.nextLine())) {
			do {
				System.out.println("Ingrese numero de la tarjeta:");
				flag=!this.cargarNumeroDeTarjeta(lectura.nextLine());
			}while(flag);
		}
		///
		System.out.println("Desea modificar la fecha de vencimiento de la tarjeta? S/N");
		if (this.SINO(lectura.nextLine())) {
			do {
				flag=true;
				System.out.println("Ingrese fecha de vencimiento (MM/AAAA):");
				this.fechaDeVencimiento=lectura.nextLine();
				if (!EstaVencida()) {
					flag=false;
				}
			}while (flag);
		}
		///
		System.out.println("Desea modificar el codigo de seguridad de la tarjeta? S/N");
		if (this.SINO(lectura.nextLine())) {
			do {
				System.out.println("Ingrese codigo de seguridad:");
				flag=!this.cargarCodigoDeSeguridad(lectura.nextLine());
			}while(flag);
		}
		System.out.println("Desea modificar el saldo limite de la tarjeta? S/N");
		if (this.SINO(lectura.nextLine())) {
			do {
				System.out.println("Ingrese saldo limite de la tarjeta:");
				flag=!this.cargarSaldoLimiteTarjeta(lectura.nextLine());
			}while(flag);
		}
	}
	
	public boolean SINO (String lectura) {
		lectura=lectura.toLowerCase();
		lectura=lectura.substring(0, 1);
		if (lectura.contains("s")) {
			return true;
		}else {
			return false;
		}
	}

	public static boolean verificaEsNumero(String numero) {
		if (numero.matches("[0-9]+")){
			return true;
		}else {
			return false;
		}
	}
		
	private boolean cargarNombreTitular(String lectura) {
		this.titular= lectura;
		if (!Tarjeta.verificaEsNumero(this.titular)) {
			return true;
		}else {
			System.out.println("Nombre invalido.");
			return false;
		}
	}
	
	private boolean cargarNumeroDeTarjeta(String lectura) {
		this.numeroTarjeta=lectura;
		if (Tarjeta.verificaEsNumero(this.numeroTarjeta) && this.numeroTarjeta.length()==16) {
			return true;
		}else {
			System.out.println("Numero invalido. Se esperan 16 digitos en este campo.");
			return false;
		}
	}
	
	private boolean cargarCodigoDeSeguridad(String lectura) {
		this.codigoDeSeguridad=lectura;
		if (Tarjeta.verificaEsNumero(this.codigoDeSeguridad) && this.codigoDeSeguridad.length()==3) {
			return true;
		}else {
			System.out.println("Numero invalido. Se esperan 3 digitos en este campo.");
			return false;
		}
	}
	
	private boolean cargarSaldoLimiteTarjeta(String lectura) {
		this.saldo=Double.parseDouble(lectura);
		if (this.saldo>0) {
			return true;
		}else {
			System.out.println("Ingrese un saldo limite positivo.");
			return false;
		}
	}
	
	public boolean EstaVencida() {

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
				return false;
			}else {
				System.out.println("La fecha de expiracion de la tarjeta ya esta vencida.");
				return true;
			}
		} catch(Exception e){
			System.out.println("La fecha ingresada es invalida.");
		}
		return true;
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
			return "Tarjeta Número= XXXX XXXX XXXX " + this.numeroTarjeta.substring(12, 16) + ", titular=" + titular + ", saldo= " + saldo + ", fechaVencimiento= " + fechaDeVencimiento;
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
		if (EstaVencida()) {
			this.BloquearTarjeta();
			return false;
		}
		if (this.saldo<monto){
			System.out.println("El saldo de la tarjeta es insuficiente para realizar la compra.");
			System.out.println("Ingresa mas dinero....");
			return false;
		}
		setSaldo(getSaldo() - monto);
		System.out.println("Se le resta dinero a tu cuenta.....");
		return true;
	}

/*
	public static void main (String[] Args) {
		Tarjeta tar= new Tarjeta();
		System.out.println(tar.toString());
		Scanner s= new Scanner(System.in);
		if (tar.cargarTarjeta(s)) {
			System.out.println(tar.toString());
		}
		tar.modificarTarjeta(s);
		System.out.println(tar.toString());
	}
*/
}


