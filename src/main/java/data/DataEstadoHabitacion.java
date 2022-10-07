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
			rs = stmt.executeQuery("select id_estado_reserva,desc_estado_reserva from estado_reserva");
			
			if (rs!=null) {
				while(rs.next()) {
					EstadoReserva er = new EstadoReserva() ;
					er.setId_estado_reserva(rs.getInt("id_estado_reserva"));
					er.setDesc_estado_reserva(rs.getString("desc_estado_reserva"));
					
					estadoR.add(er);
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
		
		return estadoR;
	}
	
	public EstadoReserva getById(EstadoReserva estadoR) {
		EstadoReserva er=null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("select id_estado_reserva,desc_estado_reserva from estado_reserva where id_estado_reserva=?");
			stmt.setInt(1, estadoR.getId_estado_reserva());
			rs=stmt.executeQuery();
			if (rs!=null && rs.next()) {
					er = new EstadoReserva();
					er.setId_estado_reserva(rs.getInt("id_estado_reserva"));
					er.setDesc_estado_reserva(rs.getString("desc_estado_reserva"));
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
		return er;
	}
	
	public void add(EstadoReserva estadoR) {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		try {
			stmt=DbConnector.getInstancia().getConn().prepareStatement("insert into estado_reserva(desc_estado_reserva) values(?)",
							PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1,estadoR.getDesc_estado_reserva());
			stmt.executeUpdate();
			
			keyResultSet=stmt.getGeneratedKeys();
            if(keyResultSet!=null && keyResultSet.next()){
                estadoR.setId_estado_reserva(keyResultSet.getInt(1));
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
	
	public void update(EstadoReserva estadoR) {
		PreparedStatement stmt= null;
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"update estado_reserva set desc_estado_reserva=? where id_estado_reserva=?");
			stmt.setString(1, estadoR.getDesc_estado_reserva());
			stmt.setInt(2, estadoR.getId_estado_reserva());
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
	
	public void remove(EstadoReserva estadoR) {
		PreparedStatement stmt= null;
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"delete from estado_reserva where id_estado_reserva=?");
			stmt.setInt(1, estadoR.getId_estado_reserva());
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
