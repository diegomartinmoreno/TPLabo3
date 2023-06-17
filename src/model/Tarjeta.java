package model;

import java.util.Objects;
import java.time.format.DateTimeFormatter;  
import java.util.Scanner;
import java.time.YearMonth;




public class Tarjeta {
	
	private String numeroTarjeta; /// (16 digitos)
	private String titular;
	private YearMonth fechaDeVencimiento;
	private String codigoDeSeguridad; /// (3 digitos)
	private double saldo;
	private boolean activa;
	
	
	public Tarjeta() {
		super();
		this.numeroTarjeta= "N/D.";
		this.titular= "N/D.";
		this.fechaDeVencimiento=YearMonth.now();
		this.fechaDeVencimiento.minusYears(1);
		this.codigoDeSeguridad="N/D";
		this.saldo=0;
		this.activa=false;
	}
	

	public Tarjeta(String numeroTarjeta, String titular, YearMonth fechaDeVencimiento, String codigoDeSeguridad,
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
			System.out.println("Ingrese fecha de vencimiento (MM/AA):");
			String auxiliar=lectura.nextLine();
			if (this.verificarFormatoFecha(auxiliar)&&this.VerificarVencimiento()) {
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
	
	public boolean verificarFormatoFecha(String input) {
		try {
			DateTimeFormatter formato = DateTimeFormatter.ofPattern("MM/yy");
			this.fechaDeVencimiento=YearMonth.parse(input, formato);
		} catch(Exception e){
			System.out.println("La fecha ingresada no es correcta.");
			return false;
		}
		return true;
	}
	
	public boolean VerificarVencimiento() {
		YearMonth ahora;
		ahora= YearMonth.now();
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("MM/yy");
		ahora.format(formato);
		if (this.fechaDeVencimiento.isAfter(ahora)) {
			return true;
		}else {
			return false;
		}
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

	public YearMonth getFechaDeVencimiento() {
		return fechaDeVencimiento;
	}

	public void setFechaDeVencimiento(YearMonth fechaDeVencimiento) {
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
/*
   ///PARA TESTEAR
	public static void main (String[] Args) {
		Tarjeta tar= new Tarjeta();
		System.out.println(tar.toString());
		Scanner s= new Scanner(System.in);
		if (tar.cargarTarjeta(s)) {
			System.out.println(tar.toString());
		}
	}
*/
	
}


