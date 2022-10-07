package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import entities.Empleados;

public class DataEmpleados {
	
	public LinkedList<Empleados> getAll(){
		Statement stmt=null;
		ResultSet rs=null;
		LinkedList<Empleados> empleados = new LinkedList<Empleados>();
		
		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("SELECT `empleados`.`nro_cuil`,\r\n"
					+ "    `empleados`.`id_usuario`,\r\n"
					+ "    `empleados`.`fec_ingreso`,\r\n"
					+ "    `empleados`.`fec_egreso`\r\n"
					+ "FROM `java_hotel`.`empleados`;\r\n");
			
			if(rs!=null) {
				while(rs.next()) {
					Empleados em = new Empleados();
					em.setCuil(rs.getInt("nro_cuil"));
					em.setId_usuario(rs.getInt("id_usuario"));
					em.setFec_ingreso(rs.getDate("fec_ingreso"));
					em.setFec_egreso(rs.getDate("fec_egreso"));
					
					empleados.add(em);
				}
			}
		}
		
		catch (SQLException e) {
			e.printStackTrace();
		} 
		
		finally {
			try {
				if(rs!=null) {rs.close();}
				if(stmt!=null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return empleados;
	}
	
	public Empleados getById(Empleados empleado) {
		PreparedStatement stmt=null;
		ResultSet rs=null;
		Empleados em = null;
		
		try {
			stmt=DbConnector.getInstancia().getConn().prepareStatement("SELECT `empleados`.`nro_cuil`,\\r\\n\"\r\n"
					+ " `empleados`.`id_usuario`,\r\n"
					+ "	`empleados`.`fec_ingreso`,\r\n"
					+ "	`empleados`.`fec_egreso`\r\n"
					+ "	FROM `java_hotel`.`empleados`;"
					+ "	WHERE id_empleado=?");
			stmt.setInt(1, empleado.getCuil());
			rs=stmt.executeQuery();
			
			if(rs!=null && rs.next()) {
				em = new Empleados();
				em.setCuil(rs.getInt("nro_cuil"));
				em.setId_usuario(rs.getInt("id_usuario"));
				em.setFec_ingreso(rs.getDate("fec_ingreso"));
				em.setFec_egreso(rs.getDate("fec_egreso"));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(rs!=null) {rs.close();}
				if(stmt!=null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return em;
	}
	
	public void remove(Empleados empleado) {
		PreparedStatement stmt= null;
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"DELETE FROM `java_hotel`.`empleados` WHERE nro_cuil=?");
			stmt.setInt(1, empleado.getCuil());
			stmt.executeUpdate();
			
		} catch (SQLException e) {
            e.printStackTrace();
		} finally {
            try {
                if(stmt!=null)stmt.close();
                DbConnector.getInstancia().releaseConn();
            } 
            catch (SQLException e) {
            	e.printStackTrace();
            }
		}
	}
	
	public void update(Empleados empleado) {
		PreparedStatement stmt=null;
		try {
			stmt = DbConnector.getInstancia().getConn().
					prepareStatement("UPDATE `java_hotel`.`empleados`\r\n"
							+ "SET `fec_ingreso` = ?,\r\n"
							+ "`fec_egreso` = ?,\r\n"
							+ "WHERE `nro_cuil` = ?;");
			stmt.setDate(1, empleado.getFec_ingreso());
			stmt.setDate(2, empleado.getFec_egreso());
			stmt.setInt(3, empleado.getCuil());
			stmt.executeUpdate();
		} 
		catch (SQLException e) {
            e.printStackTrace();
		} 
		finally {
            try {
                if(stmt!=null)stmt.close();
                DbConnector.getInstancia().releaseConn();
            } 
            catch (SQLException e) {
            	e.printStackTrace();
            }
		}
	}
	  
	public void add(Empleados empleado) {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"INSERT INTO `java_hotel`.`empleados`\r\n"
							+ "(`nro_cuil`,\r\n"
							+ "`id_usuario`,\r\n"
							+ "`fec_ingreso`,\r\n"
							+ "`fec_egreso`)\r\n"
							+ "VALUES\r\n"
							+ "(?,?,?,?)");
			stmt.setInt(1, empleado.getCuil());
			stmt.setInt(2, empleado.getId_usuario());
			stmt.setDate(3, empleado.getFec_ingreso());
			stmt.setDate(4, empleado.getFec_egreso());
			stmt.executeUpdate();
			
		} 
		catch (SQLException e) {
            e.printStackTrace();
		} 
		finally {
            try {
                if(keyResultSet!=null)keyResultSet.close();
                if(stmt!=null)stmt.close();
                DbConnector.getInstancia().releaseConn();
            } catch (SQLException e) {
            	e.printStackTrace();
            }
		}
	}


}
