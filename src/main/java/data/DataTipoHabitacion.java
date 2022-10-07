package data;

import java.sql.*;
import java.util.LinkedList;
import entities.*;

public class DataTipoHabitacion {
	
	public LinkedList<TipoHabitacion> getAll(){
		Statement stmt = null;
		ResultSet rs = null;
		
		LinkedList<TipoHabitacion> tipoH = new LinkedList<TipoHabitacion>();
		
		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("select id_tipo_hab,capacidad_tipo_hab,desc_tipo_hab from tipo_hab");
			
			if (rs!=null) {
				while(rs.next()) {
					TipoHabitacion t = new TipoHabitacion();
					t.setId_tipo_hab(rs.getInt("id_tipo_hab"));
					t.setCapacidad_tipo_hab(rs.getInt("capacidad_tipo_hab"));
					t.setDesc_tipo_hab(rs.getString("desc_tipo_hab"));
					
					tipoH.add(t);
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
		
		return tipoH;
	}
	
	public TipoHabitacion getById(TipoHabitacion tipoH) {
		TipoHabitacion t=null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("select id_tipo_hab,capacidad_tipo_hab,desc_tipo_hab from tipo_hab where id_tipo_hab=?");
			stmt.setInt(1, tipoH.getId_tipo_hab());
			rs=stmt.executeQuery();
			if (rs!=null && rs.next()) {
					t = new TipoHabitacion();
					t.setId_tipo_hab(rs.getInt("id_tipo_hab"));
					t.setCapacidad_tipo_hab(rs.getInt("capacidad_tipo_hab"));
					t.setDesc_tipo_hab(rs.getString("desc_tipo_hab"));
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
		return t;
	}
	
	public void add(TipoHabitacion tipoH) {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		try {
			stmt=DbConnector.getInstancia().getConn().prepareStatement("insert into tipo_hab(capacidad_tipo_hab,desc_tipo_hab) values(?,?)",
							PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, tipoH.getCapacidad_tipo_hab());
			stmt.setString(2, tipoH.getDesc_tipo_hab());
			stmt.executeUpdate();
			
			keyResultSet=stmt.getGeneratedKeys();
            if(keyResultSet!=null && keyResultSet.next()){
                tipoH.setId_tipo_hab(keyResultSet.getInt(1));
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
	
	public void update(TipoHabitacion tipoH) {
		PreparedStatement stmt= null;
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"update tipo_hab set  capacidad_tipo_hab=?,desc_tipo_hab=? where id_tipo_hab=?");
			stmt.setInt(1, tipoH.getCapacidad_tipo_hab());
			stmt.setString(2, tipoH.getDesc_tipo_hab());
			stmt.setInt(3, tipoH.getId_tipo_hab());
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
	
	public void remove(TipoHabitacion tipoH) {
		PreparedStatement stmt= null;
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"delete from tipo_hab where id_tipo_hab=?");
			stmt.setInt(1, tipoH.getId_tipo_hab());
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
