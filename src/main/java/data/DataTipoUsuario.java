package data;

import java.sql.*;
import java.util.LinkedList;
import entities.*;

public class DataTipoUsuario {
	
	public LinkedList<TipoUsuario> getAll(){
		Statement stmt = null;
		ResultSet rs = null;
		
		LinkedList<TipoUsuario> tipos = new LinkedList<>();
		
		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("select id_tipo_usuario,desc_usuario from tipo_usuario");
			
			if (rs!=null) {
				while(rs.next()) {
					TipoUsuario t = new TipoUsuario();
					t.setId_tipo_usuario(rs.getInt("id_tipo_usuario"));
					t.setDesc_usuario(rs.getString("desc_usuario"));
					
					tipos.add(t);
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
		
		return tipos;
	}
	
	public TipoUsuario getById(TipoUsuario tipo) {
		TipoUsuario t=null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("select id_tipo_usuario,desc_usuario where id_tipo_usuario=?");
			stmt.setInt(1, tipo.getId_tipo_usuario());
			rs=stmt.executeQuery();
			if (rs!=null && rs.next()) {
					t = new TipoUsuario();
					t.setId_tipo_usuario(rs.getInt("id_tipo_usuario"));
					t.setDesc_usuario(rs.getString("desc_usuario"));
					
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
	
	public void add(TipoUsuario tipo) {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		try {
			stmt=DbConnector.getInstancia().getConn().prepareStatement("insert into tipo_usuario(desc_usuario) values(?)",
							PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1,tipo.getDesc_usuario());
			stmt.executeUpdate();
			
			keyResultSet=stmt.getGeneratedKeys();
            if(keyResultSet!=null && keyResultSet.next()){
                tipo.setId_tipo_usuario(keyResultSet.getInt(1));
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
	
	public void update(TipoUsuario tipo) {
		PreparedStatement stmt= null;
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"update tipo_usuario set desc_usuario=? where id_tipo_usuario=?");
			stmt.setInt(1, tipo.getId_tipo_usuario());
			stmt.setString(2, tipo.getDesc_usuario());
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
	
	public void remove(TipoUsuario tipo) {
		PreparedStatement stmt= null;
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"delete from tipo_usuario where id_tipo_usuario=?");
			stmt.setInt(1, tipo.getId_tipo_usuario());
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
