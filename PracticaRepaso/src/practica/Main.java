package practica;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	public static ArrayList<String> creacionBBDD()	{
		ArrayList<String> querys = new ArrayList<String>();

		querys.add("DROP DATABASE IF EXISTS POBLACION");
		querys.add("CREATE DATABASE IF NOT EXISTS POBLACION");
		querys.add("CREATE TABLE IF NOT EXISTS PAIS (\r\n"
				+ "CONTINENTE VARCHAR(10) NOT NULL,\r\n"
				+ "NOMBRE VARCHAR(40) NOT NULL,\r\n"
				+ "SUPERFICIE INT NOT NULL,\r\n"
				+ "PRIMARY KEY(NOMBRE)\r\n"
				+ ")");
		querys.add("CREATE TABLE IF NOT EXISTS POBLACION (\r\n"
				+ "NOMBRE VARCHAR(40) NOT NULL,\r\n"
				+ "POBLACION INT NOT NULL,\r\n"
				+ "POBLACIONHOMBRES INT NOT NULL,\r\n"
				+ "POBLACIONMUJERES INT NOT NULL,\r\n"
				+ "PRIMARY KEY(NOMBRE)\r\n"
				+ ")");
		querys.add("CREATE TABLE IF NOT EXISTS POBLACIONHOMBRES (\r\n"
				+ "NOMBRE VARCHAR(40) NOT NULL,\r\n"
				+ "POBLACION_0_4 INT NOT NULL,\r\n"
				+ "POBLACION_5_9 INT NOT NULL,\r\n"
				+ "POBLACION_10_17 INT NOT NULL,\r\n"
				+ "POBLACION_18_29 INT NOT NULL,\r\n"
				+ "POBLACION_30_59 INT NOT NULL,\r\n"
				+ "POBLACION_59 INT NOT NULL,\r\n"
				+ "PRIMARY KEY(NOMBRE)\r\n"
				+ ")");
		querys.add("CREATE TABLE IF NOT EXISTS POBLACIONMUJERES (\r\n"
				+ "NOMBRE VARCHAR(40) NOT NULL,\r\n"
				+ "POBLACION_0_4 INT NOT NULL,\r\n"
				+ "POBLACION_5_9 INT NOT NULL,\r\n"
				+ "POBLACION_10_17 INT NOT NULL,\r\n"
				+ "POBLACION_18_29 INT NOT NULL,\r\n"
				+ "POBLACION_30_59 INT NOT NULL,\r\n"
				+ "POBLACION_59 INT NOT NULL,\r\n"
				+ "PRIMARY KEY(NOMBRE)\r\n"
				+ ")");
		querys.add("CREATE TABLE IF NOT EXISTS PORCENTAJES (\r\n"
				+ "CONTINENTE VARCHAR(10) NOT NULL,\r\n"
				+ "NOMBRE VARCHAR(40) NOT NULL,\r\n"
				+ "PORCENTAJEHOMBRES DECIMAL(5,2) ,\r\n"
				+ "PORCENTAJEMUJERES DECIMAL(5,2) ,\r\n"
				+ "DENSIDADTOTAL DECIMAL(7,2) ,\r\n"
				+ "DENSIDADHOMBRES DECIMAL(7,2) ,\r\n"
				+ "DENSIDADMUJERES DECIMAL(7,2) ,\r\n"
				+ "PORCENTAJE_18_29HOMBRES DECIMAL(5,2) ,\r\n"
				+ "PORCENTAJE_18_29MUJERES DECIMAL(5,2) ,\r\n"
				+ "PORCENTAJEVIEJOS DECIMAL(5,2) ,\r\n"
				+ "PORCENTAJEJOVENES DECIMAL(5,2) ,\r\n"
				+ "PRIMARY KEY(NOMBRE)\r\n"
				+ ")");
		querys.add("ALTER TABLE POBLACION ADD CONSTRAINT\r\n"
				+ "FOREIGN KEY(NOMBRE) REFERENCES PAIS(NOMBRE)\r\n"
				+ "ON UPDATE CASCADE ON DELETE CASCADE");
		querys.add("ALTER TABLE POBLACIONHOMBRES ADD CONSTRAINT\r\n"
				+ "FOREIGN KEY(NOMBRE) REFERENCES PAIS(NOMBRE)\r\n"
				+ "ON UPDATE CASCADE ON DELETE CASCADE");
		querys.add("ALTER TABLE POBLACIONMUJERES ADD CONSTRAINT\r\n"
				+ "FOREIGN KEY(NOMBRE) REFERENCES PAIS(NOMBRE)\r\n"
				+ "ON UPDATE CASCADE ON DELETE CASCADE");
		querys.add("ALTER TABLE PORCENTAJES ADD CONSTRAINT\r\n"
				+ "FOREIGN KEY(NOMBRE) REFERENCES PAIS(NOMBRE)\r\n"
				+ "ON UPDATE CASCADE ON DELETE CASCADE");

		return querys;
	}	
	public static ArrayList<BufferedReader> introducirArchivos()	{
		ArrayList<BufferedReader> archivos = new ArrayList<BufferedReader>();

		try {
			archivos.add(new BufferedReader(new FileReader("Paises.txt")));
			archivos.add(new BufferedReader(new FileReader("Poblacion.txt")));
			archivos.add(new BufferedReader(new FileReader("PoblacionEdadesHombres.txt")));
			archivos.add(new BufferedReader(new FileReader("PoblacionEdadesMujeres.txt")));

		} catch (FileNotFoundException e) { e.printStackTrace(); }

		return archivos;
	}

	public static void insertarPaises(BufferedReader paises, Connection conexion)	{
		Scanner input = new Scanner(paises);
		input.useDelimiter("\\s*;\\s*|\\s*\\n\\s*");
		
		while(input.hasNext())	{
			String continente = input.next();
			String pais = input.next();
			int superficie = input.nextInt();
			
			OperacionesBBDD.anhadirPais(conexion, continente, pais, superficie);
		}
		System.out.println("Datos de la tabla PAIS añadidos correctamente");
		input.close();
	}
	public static void insertarPoblacion(BufferedReader poblacion, Connection conexion)	{
		Scanner input = new Scanner(poblacion);
		input.useDelimiter("\\s*;\\s*|\\s*\\n\\s*");

		while(input.hasNext())	{
			String nomPais = input.next();
			int poblacion_total = input.nextInt();
			int poblacion_hombres = input.nextInt();
			int poblacion_mujeres = input.nextInt();
						
			OperacionesBBDD.anhadirPoblacion(conexion, nomPais, poblacion_total, poblacion_hombres, poblacion_mujeres);
		}
		System.out.println("Datos de la tabla POBLACION añadidos correctamente");
		input.close();
	}
	public static void insertarPoblHombres(BufferedReader pobl_hombres, Connection conexion)	{
		Scanner input = new Scanner(pobl_hombres);
		input.useDelimiter("\\s*;\\s*|\\s*\\n\\s*");

		while(input.hasNext())	{
			String nomPais = input.next();
			int pob_0_4 = input.nextInt();
			int pob_5_9 = input.nextInt();
			int pob_10_17 = input.nextInt();
			int pob_18_29 = input.nextInt();
			int pob_30_59 = input.nextInt();
			int pob_59 = input.nextInt();
						
			OperacionesBBDD.anhadirPoblacionHombres(conexion, nomPais, pob_0_4, pob_5_9, pob_10_17, pob_18_29, pob_30_59, pob_59);
		}
		System.out.println("Datos de la tabla POBLACION_HOMBRES añadidos correctamente");
		input.close();
	}
	public static void insertarPoblMujeres(BufferedReader pobl_mujeres, Connection conexion)	{
		Scanner input = new Scanner(pobl_mujeres);
		input.useDelimiter("\\s*;\\s*|\\s*\\n\\s*");

		while(input.hasNext())	{
			String nomPais = input.next();
			int pob_0_4 = input.nextInt();
			int pob_5_9 = input.nextInt();
			int pob_10_17 = input.nextInt();
			int pob_18_29 = input.nextInt();
			int pob_30_59 = input.nextInt();
			int pob_59 = input.nextInt();
						
			OperacionesBBDD.anhadirPoblacionMujeres(conexion, nomPais, pob_0_4, pob_5_9, pob_10_17, pob_18_29, pob_30_59, pob_59);
		}
		System.out.println("Datos de la tabla POBLACION_MUJERES añadidos correctamente");
		input.close();
	}
	public static void insertarPorcentajes(Connection conexion)	{
		String sql = "SELECT \r\n"
				+ "\r\n"
				+ "CONTINENTE,PAIS.NOMBRE,SUPERFICIE,POBLACION,POBLACIONHOMBRES,POBLACIONMUJERES\r\n"
				+ ",POBLACIONHOMBRES.POBLACION_0_4,POBLACIONHOMBRES.POBLACION_5_9,POBLACIONHOMBRES.POBLACION_10_17\r\n"
				+ ",POBLACIONHOMBRES.POBLACION_18_29,POBLACIONHOMBRES.POBLACION_30_59,POBLACIONHOMBRES.POBLACION_59\r\n"
				+ ",POBLACIONMUJERES.POBLACION_0_4,POBLACIONMUJERES.POBLACION_5_9,POBLACIONMUJERES.POBLACION_10_17\r\n"
				+ ",POBLACIONMUJERES.POBLACION_18_29,POBLACIONMUJERES.POBLACION_30_59,POBLACIONMUJERES.POBLACION_59\r\n"
				+ "\r\n"
				+ "FROM PAIS,POBLACION,POBLACIONHOMBRES,POBLACIONMUJERES\r\n"
				+ "WHERE PAIS.NOMBRE=POBLACION.NOMBRE\r\n"
				+ "AND PAIS.NOMBRE=POBLACIONHOMBRES.NOMBRE\r\n"
				+ "AND PAIS.NOMBRE=POBLACIONMUJERES.NOMBRE";
		
		try {
			CallableStatement sentencia = conexion.prepareCall(sql);
			ResultSet resultado = sentencia.executeQuery();
			while(resultado.next())	{
				String continente = resultado.getString(1);
				String pais = resultado.getString(2);
				int superficie = resultado.getInt(3);
				int poblacion = resultado.getInt(4);
				int pobhombres = resultado.getInt(5);
				int pobmujeres = resultado.getInt(6);
				int pobhom_18_29 = resultado.getInt(10);
				int pobhom_30_59 = resultado.getInt(11);
				int pobhom_59 = resultado.getInt(12);
				int pobmuj_18_29 = resultado.getInt(16);
				int pobmuj_30_59 = resultado.getInt(17);
				int pobmuj_59 = resultado.getInt(18);

				double porhombres = (double) pobhombres / (double) poblacion * 100;
				double pormujeres = (double) pobmujeres / (double) poblacion * 100;
				double densidadtotal = ((double) poblacion / (double) superficie) * 1000;
				double densidadhombres = ((double) pobhombres / (double) superficie) * 1000;
				double densidadmujeres = ((double) pobmujeres / (double) superficie) * 1000;
				double porcentaje18_29Hombres = pobhom_18_29  / (double)poblacion * 100;
				double porcentaje18_29Mujeres = pobmuj_18_29 / (double)poblacion * 100;
				double porcentajeviejos = (pobhom_30_59 + pobhom_59 + pobmuj_30_59 + pobmuj_59)/ (double)poblacion * 100;
				double porcentajejovenes = 100 - porcentajeviejos;
				
				OperacionesBBDD.anhadirPorcentajes(conexion, continente, pais, porhombres,
						pormujeres, densidadtotal, densidadhombres, densidadmujeres,
						porcentaje18_29Hombres, porcentaje18_29Mujeres, porcentajeviejos, porcentajejovenes
				);
			}
			
			System.out.println("Datos de la tabla PORCENTAJES añadidos correctamente");
			
		} catch (SQLException e) { e.printStackTrace(); }
		
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		String usuario = "root";
		String passwd = "root";
		String direccion = "jdbc:mysql://localhost:3306/";
		
		ArrayList<String> creacionBBDD = creacionBBDD();
		ArrayList<BufferedReader> archivos_txt = introducirArchivos();

		try	{
			Connection conexion = DriverManager.getConnection(direccion , usuario, passwd);
			Statement sentencia = conexion.createStatement();
			
			for (String sql : creacionBBDD) {
				sentencia.executeUpdate(sql);
				if (sql.contains("CREATE DATABASE"))	{
					direccion += "poblacion";
					conexion = DriverManager.getConnection(direccion, usuario, passwd);
					sentencia = conexion.createStatement();
				}
			}
			System.out.println(creacionBBDD.size() + " sentencias han sido ejecutadas.");

			insertarPaises(archivos_txt.get(0), conexion);
			insertarPoblacion(archivos_txt.get(1), conexion);
			insertarPoblHombres(archivos_txt.get(2), conexion);
			insertarPoblMujeres(archivos_txt.get(3), conexion);
			insertarPorcentajes(conexion);
			
			System.out.println();
			
			GeneradorTextos generador = new GeneradorTextos(conexion);
			generador.generarTextoPorcentajesPaises();
			generador.generarTextoPorcentajesContinentes();
			generador.generarTextoDensidadPaises();
			generador.generarTextoDensidadContinentes();
			generador.generarTextoEstadisticasPaises();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
