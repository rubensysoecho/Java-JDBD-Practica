package practica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OperacionesBBDD {
	
	public static void anhadirPais(Connection conexion, String continente, String pais, int superficie)	{
		try {
			String sql = "INSERT INTO `pais`"
					+ "(`CONTINENTE`, `NOMBRE`, `SUPERFICIE`) VALUES "
					+ "(?, ?, ?)";
			
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			
			sentencia.setString(1, continente);
			sentencia.setString(2, pais);
			sentencia.setInt(3, superficie);
			
			sentencia.executeUpdate();
		} catch (SQLException e) { e.printStackTrace(); }
	}

	public static void anhadirPoblacion(Connection conexion, String nombrePais, int poblacion, int pob_hombres, int pob_mujeres)	{
		try {
			String sql = "INSERT INTO `poblacion`"
					+ "(`NOMBRE`, `POBLACION`, `POBLACIONHOMBRES`, `POBLACIONMUJERES`) VALUES "
					+ "(?, ?, ?, ?)";
			
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			
			sentencia.setString(1, nombrePais);
			sentencia.setInt(2, poblacion);
			sentencia.setInt(3, pob_hombres);
			sentencia.setInt(4, pob_mujeres);
			
			sentencia.executeUpdate();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public static void anhadirPoblacionHombres(
			Connection conexion, String nombrePais,
			int pob_0_4, int pob_5_9, int pob_10_17, int pob_18_29, int pob_30_59, int pob_59
	)
	{
		try {
			String sql = "INSERT INTO `poblacionhombres`"
					+ "(`NOMBRE`, `POBLACION_0_4`, `POBLACION_5_9`, `POBLACION_10_17`,"
					+ " `POBLACION_18_29`, `POBLACION_30_59`, `POBLACION_59`) VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			
			sentencia.setString(1, nombrePais);
			sentencia.setInt(2, pob_0_4);
			sentencia.setInt(3, pob_5_9);
			sentencia.setInt(4, pob_10_17);
			sentencia.setInt(5, pob_18_29);
			sentencia.setInt(6, pob_30_59);
			sentencia.setInt(7, pob_59);
			
			sentencia.executeUpdate();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public static void anhadirPoblacionMujeres(
			Connection conexion, String nombrePais,
			int pob_0_4, int pob_5_9, int pob_10_17, int pob_18_29, int pob_30_59, int pob_59
	)
	{
		try {
			String sql = "INSERT INTO `poblacionmujeres`"
					+ "(`NOMBRE`, `POBLACION_0_4`, `POBLACION_5_9`, `POBLACION_10_17`,"
					+ " `POBLACION_18_29`, `POBLACION_30_59`, `POBLACION_59`) VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			
			sentencia.setString(1, nombrePais);
			sentencia.setInt(2, pob_0_4);
			sentencia.setInt(3, pob_5_9);
			sentencia.setInt(4, pob_10_17);
			sentencia.setInt(5, pob_18_29);
			sentencia.setInt(6, pob_30_59);
			sentencia.setInt(7, pob_59);
			
			sentencia.executeUpdate();
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public static void anhadirPorcentajes(
			Connection conexion, String continente, String nombrePais, double porcentajeHombres, double porcentajeMujeres,
			double densidadTotal, double densidadHombres, double densidadMujeres, double porcentaje_18_29Hombres, double porcentaje_18_29Mujeres,
			double porcentajeViejos, double porcentajeJovenes
	)	
	{
		try {
			String sql = "INSERT INTO `porcentajes`"
					+ "(`CONTINENTE`, `NOMBRE`, `PORCENTAJEHOMBRES`, `PORCENTAJEMUJERES`, `DENSIDADTOTAL`,"
					+ " `DENSIDADHOMBRES`, `DENSIDADMUJERES`, `PORCENTAJE_18_29HOMBRES`, `PORCENTAJE_18_29MUJERES`,"
					+ " `PORCENTAJEVIEJOS`, `PORCENTAJEJOVENES`) VALUES "
					+ "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement sentencia = conexion.prepareStatement(sql);

			sentencia.setString(1, continente);
			sentencia.setString(2, nombrePais);
			sentencia.setDouble(3, porcentajeHombres);
			sentencia.setDouble(4, porcentajeMujeres);
			sentencia.setDouble(5, densidadTotal);
			sentencia.setDouble(6, densidadHombres);
			sentencia.setDouble(7, densidadMujeres);
			sentencia.setDouble(8, porcentaje_18_29Hombres);
			sentencia.setDouble(9, porcentaje_18_29Mujeres);
			sentencia.setDouble(10, porcentajeViejos);
			sentencia.setDouble(11, porcentajeJovenes);
			
			sentencia.executeUpdate();
		} catch (SQLException e) { e.printStackTrace(); }
	}
}
