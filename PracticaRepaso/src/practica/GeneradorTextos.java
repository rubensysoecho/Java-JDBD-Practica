package practica;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GeneradorTextos {

	private Connection conexion;

	public GeneradorTextos(Connection conex)	{
		conexion = conex;
	}

	public void generarTextoPorcentajesPaises()	{
		PrintWriter output = null;

		try {
			output = new PrintWriter(new FileWriter("PorcentajesPoblacionPaises.txt"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		String sql = "SELECT PAIS.CONTINENTE,PAIS.NOMBRE,POBLACION,\r\n"
				+ "POBLACIONHOMBRES,POBLACIONMUJERES,\r\n"
				+ "PORCENTAJEHOMBRES,PORCENTAJEMUJERES\r\n"
				+ "FROM PAIS,POBLACION,PORCENTAJES\r\n"
				+ "WHERE PAIS.NOMBRE=POBLACION.NOMBRE AND PAIS.NOMBRE=PORCENTAJES.NOMBRE\r\n"
				+ "ORDER BY PAIS.CONTINENTE,PAIS.NOMBRE";

		try {
			output.format("%-15s%40s%20s%10s%10s%10s%10s%n","Continente", "Pais", "Total", "Hombres", "Mujeres", "%Hombres", "%Mujeres");
			CallableStatement sentencia = conexion.prepareCall(sql);
			ResultSet resultado = sentencia.executeQuery();
			while (resultado.next())	{
				String continente = resultado.getString(1);
				String pais = resultado.getString(2);
				int pob_total = resultado.getInt(3);
				int pob_hombres = resultado.getInt(4);
				int pob_mujeres = resultado.getInt(5);
				double porcentaje_hombres = resultado.getDouble(6);
				double porcentaje_mujeres = resultado.getDouble(7);
				output.format("%-15s%-40s%20s%10s%10s%10s%%%10s%%%n",continente, pais, pob_total, pob_hombres, pob_mujeres, porcentaje_hombres, porcentaje_mujeres);
			}

		} catch (SQLException e) { e.printStackTrace(); }

		finally	{
			System.out.println("PorcentajesPoblacionPaises.txt creado satisfactoriamente.");
			output.close();
		}
	}

	public void generarTextoPorcentajesContinentes()	{
		PrintWriter output = null;

		try {
			output = new PrintWriter(new FileWriter("PorcentajesPoblacionContinentes.txt"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		String sql = "SELECT CONTINENTE,\r\n"
				+ "SUM(POBLACION) ,SUM(POBLACIONHOMBRES) ,SUM(POBLACIONMUJERES) ,\r\n"
				+ "(SUM(POBLACIONHOMBRES)/SUM(POBLACION))*100,\r\n"
				+ "(SUM(POBLACIONMUJERES)/SUM(POBLACION))*100\r\n"
				+ "FROM POBLACION,PAIS\r\n"
				+ "WHERE PAIS.NOMBRE=POBLACION.NOMBRE\r\n"
				+ "GROUP BY CONTINENTE";

		try {
			output.format("%-20s%10s%10s%10s%10s%10s%n", "Continente", "Total", "Hombres", "Mujeres", "%Hombres", "%Mujeres");
			CallableStatement sentencia = conexion.prepareCall(sql);
			ResultSet resultado = sentencia.executeQuery();
			while (resultado.next())	{
				String continente = "TOTAL " + resultado.getString(1);
				int pob_total = resultado.getInt(2);
				int pob_hombres = resultado.getInt(3);
				int pob_mujeres = resultado.getInt(4);
				double porcentaje_hombres = resultado.getDouble(5);
				double porcentaje_mujeres = resultado.getDouble(6);
				output.format("%-20s%10s%10s%10s%10.2f%%%10.2f%%%n", continente, pob_total, pob_hombres, pob_mujeres, porcentaje_hombres, porcentaje_mujeres);
			}

			sql = "SELECT SUM(POBLACION) ,SUM(POBLACIONHOMBRES) ,SUM(POBLACIONMUJERES) ,\r\n"
					+ "(SUM(POBLACIONHOMBRES)/SUM(POBLACION))*100,\r\n"
					+ "(SUM(POBLACIONMUJERES)/SUM(POBLACION))*100\r\n"
					+ "FROM POBLACION";

			sentencia = conexion.prepareCall(sql);
			resultado = sentencia.executeQuery();
			while (resultado.next())	{
				String continente = "TOTAL General";
				int pob_total = resultado.getInt(1);
				int pob_hombres = resultado.getInt(2);
				int pob_mujeres = resultado.getInt(3);
				double porcentaje_hombres = resultado.getDouble(4);
				double porcentaje_mujeres = resultado.getDouble(5);
				output.format("%-20s%10s%10s%10s%10.2f%%%10.2f%%%n", continente, pob_total, pob_hombres, pob_mujeres, porcentaje_hombres, porcentaje_mujeres);
			}

		} catch (SQLException e) { e.printStackTrace(); }

		finally	{
			System.out.println("PorcentajesPoblacionContinentes.txt creado satisfactoriamente.");
			output.close();
		}

	}

	public void generarTextoDensidadPaises()	{
		PrintWriter output = null;

		try {
			output = new PrintWriter(new FileWriter("PorcentajesDensidadPaises.txt"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		String sql = "SELECT PAIS.CONTINENTE,PAIS.NOMBRE,\r\n"
				+ "DENSIDADTOTAL,DENSIDADHOMBRES,DENSIDADMUJERES\r\n"
				+ "FROM PAIS,PORCENTAJES\r\n"
				+ "WHERE PAIS.NOMBRE=PORCENTAJES.NOMBRE\r\n"
				+ "ORDER BY PAIS.CONTINENTE,PAIS.NOMBRE";

		try {
			output.format("%-15s%40s%20s%20s%20s%n","Continente", "Pais", "Densidad total", "Densidad Hombres", "Densidad Mujeres");
			CallableStatement sentencia = conexion.prepareCall(sql);
			ResultSet resultado = sentencia.executeQuery();
			while (resultado.next())	{
				String continente = resultado.getString(1);
				String pais = resultado.getString(2);
				double densidadtotal = resultado.getDouble(3);
				double densidadhombres = resultado.getDouble(4);
				double densidadmujeres = resultado.getDouble(5);
				output.format("%-15s%-40s%12s por km2%12s por km2%12s por km2%n", continente, pais, densidadtotal, densidadhombres, densidadmujeres);
			}

		} catch (SQLException e) { e.printStackTrace(); }

		finally	{
			System.out.println("PorcentajesDensidadPaises.txt creado satisfactoriamente.");
			output.close();
		}
	}

	public void generarTextoDensidadContinentes()	{
		PrintWriter output = null;

		try {
			output = new PrintWriter(new FileWriter("PorcentajesDensidadContinentes.txt"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		String sql = "SELECT CONTINENTE,SUM(SUPERFICIE),\r\n"
				+ "(SUM(POBLACION)/SUM(SUPERFICIE))*1000,\r\n"
				+ "(SUM(POBLACIONHOMBRES)/SUM(SUPERFICIE))*1000,\r\n"
				+ "(SUM(POBLACIONMUJERES)/SUM(SUPERFICIE))*1000\r\n"
				+ "FROM POBLACION,PAIS\r\n"
				+ "WHERE PAIS.NOMBRE=POBLACION.NOMBRE\r\n"
				+ "GROUP BY CONTINENTE";

		try {
			output.format("%-15s%10s%20s%15s%18s%n", "Continente", "Superficie", "Densidad total", "Den. Hombres", "Den. Mujeres");
			CallableStatement sentencia = conexion.prepareCall(sql);
			ResultSet resultado = sentencia.executeQuery();
			while (resultado.next())	{
				String continente = "TOTAL " + resultado.getString(1);
				int superficie = resultado.getInt(2);
				double densidadtotal = resultado.getDouble(3);
				double densidadhombres = resultado.getDouble(4);
				double densidadmujeres = resultado.getDouble(5);
				output.format("%-15s%-10s%10.2f por km2%10.2f por km2%10.2f por km2%n", continente, superficie, densidadtotal, densidadhombres, densidadmujeres);
			}

			sql = "SELECT SUM(SUPERFICIE),\r\n"
					+ "(SUM(POBLACION)/SUM(SUPERFICIE))*1000,\r\n"
					+ "(SUM(POBLACIONHOMBRES)/SUM(SUPERFICIE))*1000,\r\n"
					+ "(SUM(POBLACIONMUJERES)/SUM(SUPERFICIE))*1000\r\n"
					+ "FROM POBLACION,PAIS\r\n"
					+ "WHERE PAIS.NOMBRE=POBLACION.NOMBRE";

			sentencia = conexion.prepareCall(sql);
			resultado = sentencia.executeQuery();
			while (resultado.next())	{
				String continente = "TOTAL General";
				int superficie = resultado.getInt(1);
				double densidadtotal = resultado.getDouble(2);
				double densidadhombres = resultado.getDouble(3);
				double densidadmujeres = resultado.getDouble(4);
				output.format("%-15s%-10s%10.2f por km2%10.2f por km2%10.2f por km2%n", continente, superficie, densidadtotal, densidadhombres, densidadmujeres);
			}


		} catch (SQLException e) { e.printStackTrace(); }

		finally	{
			System.out.println("PorcentajesDensidadContinentes.txt creado satisfactoriamente.");
			output.close();
		}

	}

	private void anhadir_preguntas_consultas(ArrayList<String> preguntas, ArrayList<String> consultas)	{
		preguntas.add("Más Extenso");
		preguntas.add("Más Pequeño");
		preguntas.add("Más Poblado");
		preguntas.add("Menos Poblado");
		preguntas.add("Mayor Densidad de Población");
		preguntas.add("Menor Densidad de Población");
		preguntas.add("Mayor Densidad de Hombres");
		preguntas.add("Mayor Densidad de Mujeres");
		preguntas.add("Mayor Porcentaje de Hombres");
		preguntas.add("Mayor Porcentaje de Mujeres");
		preguntas.add("Más Viejo (mayores de 30)");
		preguntas.add("Más Joven (menores de 30)");
		preguntas.add("Mayor Porcentaje de Hombres entre 18 y 29");
		preguntas.add("Mayor Porcentaje de Mujeres entre 18 y 29");

		consultas.add("SELECT NOMBRE,SUPERFICIE FROM PAIS\r\n"
				+ "WHERE SUPERFICIE=(SELECT MAX(SUPERFICIE) FROM PAIS)");
		consultas.add("SELECT NOMBRE,SUPERFICIE FROM PAIS\r\n"
				+ "WHERE SUPERFICIE=(SELECT MIN(SUPERFICIE) FROM PAIS)");
		consultas.add("SELECT NOMBRE,POBLACION FROM POBLACION\r\n"
				+ "WHERE POBLACION=(SELECT MAX(POBLACION) FROM POBLACION)");
		consultas.add("SELECT NOMBRE,POBLACION FROM POBLACION\r\n"
				+ "WHERE POBLACION=(SELECT MIN(POBLACION) FROM POBLACION)");
		consultas.add("SELECT NOMBRE,DENSIDADTOTAL FROM PORCENTAJES\r\n"
				+ "WHERE DENSIDADTOTAL=(SELECT MAX(DENSIDADTOTAL) FROM PORCENTAJES)");
		consultas.add("SELECT NOMBRE,DENSIDADTOTAL FROM PORCENTAJES\r\n"
				+ "WHERE DENSIDADTOTAL=(SELECT MIN(DENSIDADTOTAL) FROM PORCENTAJES)");
		consultas.add("SELECT NOMBRE,DENSIDADHOMBRES FROM PORCENTAJES\r\n"
				+ "WHERE DENSIDADHOMBRES=(SELECT MAX(DENSIDADHOMBRES) FROM PORCENTAJES)");
		consultas.add("SELECT NOMBRE,DENSIDADMUJERES FROM PORCENTAJES\r\n"
				+ "WHERE DENSIDADMUJERES=(SELECT MAX(DENSIDADMUJERES) FROM PORCENTAJES)");
		consultas.add("SELECT NOMBRE,PORCENTAJEHOMBRES FROM PORCENTAJES\r\n"
				+ "WHERE PORCENTAJEHOMBRES=(SELECT MAX(PORCENTAJEHOMBRES) FROM PORCENTAJES)");
		consultas.add("SELECT NOMBRE,PORCENTAJEMUJERES FROM PORCENTAJES\r\n"
				+ "WHERE PORCENTAJEMUJERES=(SELECT MAX(PORCENTAJEMUJERES) FROM PORCENTAJES)");
		consultas.add("SELECT NOMBRE,PORCENTAJEVIEJOS FROM PORCENTAJES\r\n"
				+ "WHERE PORCENTAJEVIEJOS=(SELECT MAX(PORCENTAJEVIEJOS) FROM PORCENTAJES)");
		consultas.add("SELECT NOMBRE,PORCENTAJEJOVENES FROM PORCENTAJES\r\n"
				+ "WHERE PORCENTAJEJOVENES=(SELECT MAX(PORCENTAJEJOVENES) FROM PORCENTAJES)");
		consultas.add("SELECT NOMBRE,PORCENTAJE_18_29HOMBRES FROM PORCENTAJES\r\n"
				+ "WHERE PORCENTAJE_18_29HOMBRES=(SELECT MAX(PORCENTAJE_18_29HOMBRES) FROM PORCENTAJES)");
		consultas.add("SELECT NOMBRE,PORCENTAJE_18_29MUJERES FROM PORCENTAJES\r\n"
				+ "WHERE PORCENTAJE_18_29MUJERES=(SELECT MAX(PORCENTAJE_18_29MUJERES) FROM PORCENTAJES)");
	}

	public void generarTextoEstadisticasPaises()	{

		PrintWriter output = null;

		try {
			output = new PrintWriter(new FileWriter("EstadisticasPaises.txt"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		ArrayList<String> preguntas = new ArrayList<String>();
		ArrayList<String> consultas = new ArrayList<String>();
		anhadir_preguntas_consultas(preguntas, consultas);

		output.format("%-50s%-30s%20s%n", "Pregunta", "Pais", "Dato");

		for (int i = 0; i < preguntas.size(); i++) {
			String pregunta = preguntas.get(i);
			String sql = consultas.get(i);

			try {
				PreparedStatement sentencia = conexion.prepareCall(sql);
				ResultSet resultado = sentencia.executeQuery();

				while (resultado.next())	{

					if (i >= 4)	{
						String pais = resultado.getString(1);
						double dato = resultado.getDouble(2);

						if (i <= 7)	{
							output.format("%-50s%-30s%20.2f por km2%n", pregunta, pais, dato);
						}else	{
							output.format("%-50s%-30s%20.2f%%%n", pregunta, pais, dato);
						}

					}else	{
						String pais = resultado.getString(1);
						int dato = resultado.getInt(2);

						if (i == 0 || i == 1)	{
							output.format("%-50s%-30s%20s km2%n", pregunta, pais, dato);
						}else	{
							output.format("%-50s%-30s%20s%n", pregunta, pais, dato);
						}
					}
				}

			} catch (SQLException e) { e.printStackTrace(); }

		}
		System.out.println("EstadisticasPaises.txt creado satisfactoriamente.");
		output.close();

	}
}
