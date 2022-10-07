package data;

import java.sql.*;
import java.util.LinkedList;
import entities.*;

public class DataEstadoHabitacion {
	
	public LinkedList<EstadoHabitacion> getAll(){
		Statement stmt = null;
		ResultSet rs = null;
		
		LinkedList<EstadoHabitacion> estadoH = new LinkedList<EstadoHabitacion>();

		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("select id_estado_hab,desc_estado_hab from estado_reserva");
			
			if (rs!=null) {
				while(rs.next()) {
					EstadoHabitacion eh = new EstadoHabitacion();
					eh.setId_estado_hab(rs.getInt("id_estado_hab"));
					eh.setDesc_estado_hab(rs.getString("desc_estado_hab"));
					
					estadoH.add(eh);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs!=null) {
					rs.close();
				}
				if (stmt!=null) {
					stmt.close();
				}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return estadoH;
	}
	
	public EstadoHabitacion getById(EstadoHabitacion estadoH) {
		EstadoHabitacion eh=null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("select id_estado_hab,desc_estado_hab from estado_hab where id_estado_hab=?");
			stmt.setInt(1, estadoH.getId_estado_hab());
			rs=stmt.executeQuery();
			if (rs!=null && rs.next()) {
					eh = new EstadoHabitacion();
					eh.setId_estado_hab(rs.getInt("id_estado_hab"));
					eh.setDesc_estado_hab(rs.getString("desc_estado_hab"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs!=null) {
					rs.close();
				}
				if (stmt!=null) {
					stmt.close();
				}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return eh;
	}
	
	public void add(EstadoHabitacion estadoH) {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		try {
			stmt=DbConnector.getInstancia().getConn().prepareStatement("insert into estado_hab(desc_estado_hab) values(?)",
							PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1,estadoH.getDesc_estado_hab());
			stmt.executeUpdate();
			
			keyResultSet=stmt.getGeneratedKeys();
            if(keyResultSet!=null && keyResultSet.next()){
                estadoH.setId_estado_hab(keyResultSet.getInt(1));
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
	
	public void update(EstadoHabitacion estadoH) {
		PreparedStatement stmt= null;
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"update estado_hab set desc_estado_hab=? where id_estado_hab=?");
			stmt.setString(1, estadoH.getDesc_estado_hab());
			stmt.setInt(2, estadoH.getId_estado_hab());
			stmt.executeUpdate();
		} catch (SQLException e) {
            e.printStackTrace();
		} finally {
            try {
                if(stmt!=null)stmt.close();
                DbConnector.getInstancia().releaseConn();
            } catch (SQLException e) {
            	e.printStackTrace();
            }
		}
	}
	
	public void remove(EstadoHabitacion estadoH) {
		PreparedStatement stmt= null;
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"delete from estado_hab where id_estado_hab=?");
			stmt.setInt(1, estadoH.getId_estado_hab());
			stmt.executeUpdate();
		} catch (SQLException e) {
            e.printStackTrace();
		} finally {
            try {
                if(stmt!=null)stmt.close();
                DbConnector.getInstancia().releaseConn();
            } catch (SQLException e) {
            	e.printStackTrace();
            }
		}
	}

}
