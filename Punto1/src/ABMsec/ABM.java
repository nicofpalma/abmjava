package ABMsec;
import java.util.Scanner;
import java.io.*;
import java.util.Scanner;

public class ABM {
	static Integer opcion=-1;
	static String op;
	static Scanner input = new Scanner(System.in);
	final static String salto="\r\n";
	
	public static void main(String[] args) { //Menu del programa
		String nombArchivo = "Clientes.txt";
		crearArchivo(nombArchivo);
		while (opcion!=0) { 
			System.out.print("-------------------------------------------------------------" + salto
					+ "ABM Clientes:" + salto
					+ " 1- Altas." + salto
					+ " 2- Bajas." + salto
					+ " 3- Modificaciones." + salto
					+ " 4- Mostrar registros." + salto
					+ " 0- Salir." + salto
					+ "" + salto
					+ "Ingrese un número: ");
			op = input.nextLine();
			while (validacionInt(op)==false) {
				System.out.print("-------------------------------------------------------------" + salto
						+ "El numero ingresado no se trata de un entero, vuelva a intentar."+ salto
						+ "Ingrese un número entero: ");
				op=input.nextLine();
			}
			
			opcion=Integer.parseInt(op);
			switch(opcion) {
				case 0: {
					break;
				}
				case 1: {
					alta(nombArchivo);
					opcion=-1;
					break;
				}
				case 2: {
					baja(nombArchivo);
					opcion=-1;
					break;
				}
				case 3: {
					modificacion(nombArchivo);
					opcion=-1;
					break;
				}
				case 4: {
					mostrar(nombArchivo);
					opcion=-1;
					break;
				}
				default: {
					System.out.println("-------------------------------------------------------------");
					System.out.println("Vuelva a elegir una opción valida.");
				}
			}
		}
		
	}
	
	
	public static boolean validacionInt(String cadena) { //Verifica que lo ingresado se trate de un entero
		try {	
			Integer.parseInt(cadena);
			return true;
		} catch(NumberFormatException nfe) {
			return false;
		}
	}
	
	
	public static boolean validacionCantidadDigitos(String numero) { //Verifica que los numeros sean de hasta 4 digitos
		if (numero.length()<=4) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public static String validacionNombre(String cadena) { //Valida que el nombre del cliente no contenga comas o saltos de linea
		String cadenaModificada="";
		Character caracter=44;
		for(int i=0;i<cadena.length();i++) {
			if (cadena.charAt(i)!=caracter) {
				cadenaModificada=cadenaModificada+cadena.substring(i,i+1);	
			}
		} 
		return cadenaModificada;
	}
	
	
	public static void crearArchivo(String nombreArch) { //Crear el archivo
		File archivo = new File(nombreArch);
		try {
			boolean archivoCreado = archivo.createNewFile();
			if (archivoCreado) {
				System.out.println("Archivo "+ nombreArch +" creado correctamente.");
			} else {
				System.out.println("El archivo "+ nombreArch +" ya existe.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void alta(String nombArchivo){ //Funcion de alta del programa
		File archivo= new File(nombArchivo);
		String nombre,apellido,codigo,registro="";
		System.out.println("-------------------------------------------------------------"+salto+salto+"Alta: ");
		System.out.print("Ingrese el nombre del cliente: ");
		nombre=input.nextLine();
		validacionNombre(nombre);
		System.out.print("Ingrese el apellido del cliente: ");
		apellido=input.nextLine();
		validacionNombre(apellido);
		System.out.print("Ingrese el codigo del cliente: ");
		codigo=input.nextLine();
		while (validacionInt(codigo)==false || validacionCantidadDigitos(codigo)==false) {
			System.out.print("Error."+salto+"Ingrese el codigo del cliente (Numero entero de hasta 4 digitos): ");
			codigo=input.nextLine();
		}
		try {
			PrintWriter escribirArchivo = new PrintWriter(new FileWriter(archivo, true));
			registro=nombre+","+apellido+","+codigo;
			escribirArchivo.println(registro);
			escribirArchivo.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void baja(String nombArchivo) { //Funcion de bajas del programa
		try {
			File archivo= new File(nombArchivo);
			System.out.println("-------------------------------------------------------------"+salto+salto+"Baja: ");
			Scanner input=new Scanner(System.in);
			Integer pos, count=0;
			String posStr;
			//Pido la posicion del registro a eliminar
			System.out.print("Ingrese la posicion del registro que desea dar de baja: ");
			posStr = input.nextLine();
			while (validacionInt(posStr)==false) {
				System.out.print("Error."+salto+"Ingrese una posición correcta (Numero entero): ");
				posStr=input.nextLine();
			}
			pos=Integer.parseInt(posStr);
			//Abro el archivo original para leerlo
			BufferedReader entrada = new BufferedReader(new FileReader(archivo));
			String linea=entrada.readLine();
			//creo el nuevo archivo para pasar todo allí
			File archivoTemporal = new File(archivo.getAbsolutePath() + ".tmp");
			//Abro el archivo temporal para escribir todas las lineas en él
			PrintWriter escritura = new PrintWriter(new FileWriter(archivoTemporal));
			
			boolean encontrado=false;
			while ((linea!=null)) {
				if (count!=pos) {	 
	                escritura.println(linea);
	                escritura.flush();
	            } else {
	            	encontrado=true;
	            }
				linea=entrada.readLine();
				count++;
			}
			if (encontrado==true) {
				System.out.println(salto+"El registro seleccionado fue eliminado correctamente.");
			} else {
				System.out.println(salto+"El registro seleccionado no se encontró.");
			}
			entrada.close();
			escritura.close();
			archivo.delete();
			archivoTemporal.renameTo(archivo);
		} catch (FileNotFoundException e) {
			System.out.println("El archivo no fue encontrado: "+e);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void modificacion(String nombArchivo) { //Funcion de modificaciones del programa
		File archivo= new File(nombArchivo);
		System.out.println("-------------------------------------------------------------"+salto+salto+"Modificacion: ");
		Scanner input=new Scanner(System.in);
		Integer pos, count=0;
		String posStr;
		//Pido la posicion del registro a modificar
		System.out.print("Ingrese la posicion del registro que desea modificar: ");
		posStr = input.nextLine();
		while (validacionInt(posStr)==false) {
			System.out.print("Error."+salto+"Ingrese una posición correcta (Numero entero): ");
			posStr=input.nextLine();
		}
		pos=Integer.parseInt(posStr);
		
		try {
			//Abro el archivo original para leerlo
			BufferedReader entrada = new BufferedReader(new FileReader(archivo));
			String linea=entrada.readLine();
			//creo el nuevo archivo para pasar todo allí
			File archivoTemporal = new File(archivo.getAbsolutePath() + ".tmp");
			//Abro el archivo temporal para escribir todas las lineas en él
			PrintWriter escritura = new PrintWriter(new FileWriter(archivoTemporal));
			
			boolean encontrado=false;
			while ((linea!=null)) {
				if (count!=pos) {	 
	                escritura.println(linea);
	                escritura.flush();
	            } else {
	            	encontrado=true;
	            	Scanner input1=new Scanner(System.in);
	            	String nombre,apellido,codigo,registro="";
	            	System.out.println("Registro encontrado para la modificacion: ");
	            	System.out.print("Ingrese el nombre del cliente: ");
	        		nombre=input1.nextLine();
	        		validacionNombre(nombre);
	        		System.out.print("Ingrese el apellido del cliente: ");
	        		apellido=input1.nextLine();
	        		validacionNombre(apellido);
	        		System.out.print("Ingrese el codigo del cliente: ");
	        		codigo=input1.nextLine();
	        		while (validacionInt(codigo)==false || validacionCantidadDigitos(codigo)==false) {
	        			System.out.print("Error."+salto+"Ingrese el codigo del cliente (Numero entero de hasta 4 digitos): ");
	        			codigo=input1.nextLine();	
	        		}
	        		registro=nombre+","+apellido+","+codigo;
	        		escritura.println(registro);
	                escritura.flush();
	            }
				count++;
				linea=entrada.readLine();
			}
		
			if (encontrado==false) {
				System.out.println(salto+"El registro seleccionado no fue encontrado correctamente.");
			} 
			entrada.close();
			escritura.close();
			archivo.delete();
			archivoTemporal.renameTo(archivo);	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void mostrar(String nombArchivo) { //Funcion que muestra registros por pantalla
		File archivo= new File(nombArchivo);
		System.out.println("-------------------------------------------------------------"+salto+salto+"Mostrar registros: ");
		try {
			String n1;
			Integer count=0;
			Scanner n=new Scanner(System.in);
			BufferedReader entrada = new BufferedReader(new FileReader(archivo));
			String linea = entrada.readLine();
			while (linea!=null) {
				String[] registro=linea.split(",");
				System.out.println(count+" .   "+registro[0]+"   "+registro[1]+"   "+registro[2]);
				count++;	
				linea = entrada.readLine();
			}
			System.out.print("Presione ENTER para salir... ");
			n1=n.nextLine();
			entrada.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
