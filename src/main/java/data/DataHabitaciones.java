package data;

import entities.*;

import java.sql.*;
import java.util.LinkedList;

public class DataHabitaciones {
	
	public LinkedList<Habitaciones> getAll(){
		Statement stmt=null;
		ResultSet rs=null;
		LinkedList<Habitaciones> habitaciones = new LinkedList<Habitaciones>();
		
		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("SELECT `habitaciones`.`id_hab`,\r\n"
					+ "    `habitaciones`.`id_tipo_hab`,\r\n"
					+ "    `habitaciones`.`id_estado_hab`,\r\n"
					+ "    `habitaciones`.`nro_hab`,\r\n"
					+ "    `habitaciones`.`desc_hab`,\r\n"
					+ "FROM `java_hotel`.`habitaciones`;\r\n");
			
			if(rs!=null) {
				while(rs.next()) {
					Habitaciones h = new Habitaciones();
					h.setId_hab(rs.getInt("id_hab"));
					h.setId_tipo_hab(rs.getInt("id_tipo_hab"));
					h.setId_estado_hab(rs.getInt("id_estado_hab"));
					h.setNro_hab(rs.getInt("nro_hab"));
					h.setDesc_hab(rs.getString("desc_hab"));
					
					habitaciones.add(h);
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
		
		return habitaciones;
	}
	
	public Habitaciones getById(Habitaciones habitacion) {
		PreparedStatement stmt=null;
		ResultSet rs=null;
		Habitaciones h = null;
		
		try {
			stmt=DbConnector.getInstancia().getConn().prepareStatement("SELECT `habitaciones`.`id_hab`,\r\n"
					+ "    `habitaciones`.`id_tipo_hab`,\r\n"
					+ "    `habitaciones`.`id_estado_hab`,\r\n"
					+ "    `habitaciones`.`nro_hab`,\r\n"
					+ "    `habitaciones`.`desc_hab`,\r\n"
					+ "    `habitaciones`.`habitacionescol`\r\n"
					+ "FROM `java_hotel`.`habitaciones` WHERE id_hab=?");
			stmt.setInt(1, habitacion.getId_hab());
			rs=stmt.executeQuery();
			
			if(rs!=null && rs.next()) {
				h = new Habitaciones();
				h.setId_hab(rs.getInt("ide_hab"));
				h.setId_tipo_hab(rs.getInt("id_estado_hab"));
				h.setId_estado_hab(rs.getInt("id_estado_hab"));
				h.setNro_hab(rs.getInt("nro_hab"));
				h.setDesc_hab(rs.getString("desc_hab"));
				h.setHabitacionescol(rs.getString("habitacionescol"));
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
		
		return h;
	}
	
	public void remove(Habitaciones habitacion) {
		PreparedStatement stmt= null;
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"DELETE FROM `java_hotel`.`habitaciones` WHERE id_hab=?");
			stmt.setInt(1, habitacion.getId_hab());
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
	
	public void update(Habitaciones habitacion) {
		PreparedStatement stmt=null;
		try {
			stmt = DbConnector.getInstancia().getConn().
					prepareStatement("UPDATE `java_hotel`.`habitaciones`\r\n"
							+ "		SET\r\n"
							+ "		`id_hab` = ?,\r\n"
							+ "		`id_tipo_hab` = ?,\r\n"
							+ "		`id_estado_hab` = ?,\r\n"
							+ "		`nro_hab` = ?,\r\n"
							+ "		`desc_hab` = ?,\r\n"
							+ "		`habitacionescol` = ?\r\n"
							+ "		WHERE `id_hab` = ?;");
			stmt.setInt(1, habitacion.getId_hab());
			stmt.setInt(2, habitacion.getId_tipo_hab());
			stmt.setInt(3, habitacion.getId_estado_hab());
			stmt.setInt(4, habitacion.getNro_hab());
			stmt.setString(5, habitacion.getDesc_hab());
			stmt.setString(6, habitacion.getHabitacionescol());
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
	
	public void add(Habitaciones habitacion) {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"INSERT INTO `java_hotel`.`habitaciones`\r\n"
							+ "(`id_tipo_hab`,\r\n"
							+ "`id_estado_hab`,\r\n"
							+ "`nro_hab`,\r\n"
							+ "`desc_hab`,\r\n"
							+ "`habitacionescol`)\r\n"
							+ "VALUES\r\n"
							+ "(?,?,?,?,?,);\r\n",
							PreparedStatement.RETURN_GENERATED_KEYS
							);
			stmt.setInt(1, habitacion.getId_tipo_hab());
			stmt.setInt(2, habitacion.getId_estado_hab());
			stmt.setInt(3, habitacion.getNro_hab());
			stmt.setString(4, habitacion.getDesc_hab());
			stmt.setString(5, habitacion.getHabitacionescol());
			stmt.executeUpdate();
			
			keyResultSet=stmt.getGeneratedKeys();
            if(keyResultSet!=null && keyResultSet.next()){
                habitacion.setId_hab(keyResultSet.getInt(1));
            }
			
		} catch (SQLException e) {
            e.printStackTrace();
		} finally {
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
