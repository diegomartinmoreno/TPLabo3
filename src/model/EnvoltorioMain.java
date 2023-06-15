package model;

import Exceptions.MenorDeEdadException;

import java.util.Scanner;

public class EnvoltorioMain {

    public static Usuario registroDeCuenta (Scanner scanner) throws MenorDeEdadException {
        Usuario usuario = new Usuario();
        String cadenaAux=null;
        boolean flag=false;

        System.out.println("Bienvenido! Ingrese los datos correspondientes >> ");

        do {
            System.out.println("1) Ingrese su nombre: ");
            cadenaAux = scanner.nextLine();
            try {
                flag = Persona.verificarEsLetra(cadenaAux);
                if (!flag)
                    System.out.println("Recuerde que su nombre son solo letras!.");
            }catch (NullPointerException e){
                System.out.println(e.getMessage());
            }
        }while (!flag);

        usuario.setNombre(cadenaAux);

        do {
            System.out.println("2) Ingrese su apellido: ");
            cadenaAux = scanner.nextLine();
            try {
                flag = Persona.verificarEsLetra(cadenaAux);
                if (!flag)
                    System.out.println("Recuerde que su apellido son solo letras!.");
            }catch (NullPointerException e){
                System.out.println(e.getMessage());
            }
        }while (!flag);

        usuario.setApellido(cadenaAux);

        System.out.println("3) Ingrese su edad");
        int edad = scanner.nextInt();
        try {
            flag = Persona.verificarEdad(edad);
            if (!flag)
                throw new MenorDeEdadException();
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }

        usuario.setEdad(edad);

        do {
            System.out.println("4) Ingrese su DNI: ");
            cadenaAux = scanner.nextLine();
            try{
                flag = Persona.verificarEsNumero(cadenaAux);
                if (flag) {
                    flag = Persona.verificarLongitudDNI(cadenaAux);
                    if (!flag)
                        System.out.println("Error en la longitud del DNI!. Son 8 digitos.");
                }else
                    System.out.println("Error en el dni, no son todos digitos.");
            }catch (NullPointerException e){
                System.out.println(e.getMessage());
            }
        }while (!flag);

        usuario.setDni(cadenaAux);

        do{
            System.out.println("5) Ingrese su numero telefonico: ");
            cadenaAux = scanner.nextLine();
            try {
                flag = Persona.verificarEsNumero(cadenaAux);
                if (flag) {
                    flag = Persona.verificarCodigoDeArea(cadenaAux); //NO HAGO TRY CATCH DE ESTE METODO PQ EN TEORIA VERIFICAR COD AREA SI NO LANZO EXCEPCION, ES QUE SON TODOS DIGITOS.
                    if (!flag)
                        System.out.println("Error en el codigo de area del telefono!.");
                } else
                    System.out.println("Error en el numero telefonico, no son todos digitos.");
            }catch (NullPointerException e){
                System.out.println(e.getMessage());
            }
        }while (!flag);

        usuario.setNroDeTelefono(cadenaAux);

        System.out.println("6) Ingrese su email: ");
        usuario.setEmail(scanner.nextLine());

        do {
            System.out.println("7) Finalmente ingrese su contrasenia (debera recordarla): ");
            String contrasenia = scanner.nextLine();
            try {
                flag = Usuario.verificarContraseniaSegura(contrasenia);
            }catch (NullPointerException e){
                System.out.println(e.getMessage());
            }
        } while (!flag);

        usuario.setContrasenia(cadenaAux);

        //FALTA PARTE DE TARJETA.

        return usuario;
    }

    //FALTAN METODOS PARA MODIFICACIONES DE DATOS PERSONALES.














}
