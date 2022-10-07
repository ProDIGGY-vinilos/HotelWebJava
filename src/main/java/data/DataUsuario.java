package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import entities.Usuarios;

public class DataUsuario {
	
	public LinkedList<Usuarios> getAll(){
		Statement stmt=null;
		ResultSet rs=null;
		LinkedList<Usuarios> usuarios = new LinkedList<Usuarios>();
		
		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("SELECT `usuarios`.`id_usuario`,\r\n"
					+ "    `usuarios`.`id_tipo_usuario`,\r\n"
					+ "    `usuarios`.`apellido`,\r\n"
					+ "    `usuarios`.`nombre`,\r\n"
					+ "    `usuarios`.`telefono`,\r\n"
					+ "    `usuarios`.`fec_nac`,\r\n"
					+ "    `usuarios`.`email`,\r\n"
					+ "    `usuarios`.`pais`,\r\n"
					+ "    `usuarios`.`ciudad`,\r\n"
					+ "    `usuarios`.`direccion`,\r\n"
					+ "    `usuarios`.`password`\r\n"
					+ "FROM `java_hotel`.`usuarios`;\r\n");
			
			if(rs!=null) {
				while(rs.next()) {
					Usuarios u = new Usuarios();
					u.setId_tipo_usuario(rs.getInt("id_usuario"));
					u.setId_tipo_usuario(rs.getInt("id_tipo_usuario"));
					u.setApellido(rs.getString("apellido"));
					u.setNombre(rs.getString("nombre"));
					u.setTelefono(rs.getString("telefono"));
					u.setFec_nac(rs.getDate("fec_nac"));
					u.setEmail(rs.getString("email"));
					u.setPais(rs.getString("pais"));
					u.setCiudad(rs.getString("ciudad"));
					u.setDireccion(rs.getString("direccion"));
					u.setPassword(rs.getString("password"));
					
					usuarios.add(u);
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
		
		return usuarios;
	}
	
	public Usuarios getById(Usuarios usuario) {
		PreparedStatement stmt=null;
		ResultSet rs=null;
		Usuarios u = null;
		
		try {
			stmt=DbConnector.getInstancia().getConn().prepareStatement("SELECT `usuarios`.`id_usuario`,\r\n"
					+ "    `usuarios`.`id_tipo_usuario`,\r\n"
					+ "    `usuarios`.`apellido`,\r\n"
					+ "    `usuarios`.`nombre`,\r\n"
					+ "    `usuarios`.`telefono`,\r\n"
					+ "    `usuarios`.`fec_nac`,\r\n"
					+ "    `usuarios`.`email`,\r\n"
					+ "    `usuarios`.`pais`,\r\n"
					+ "    `usuarios`.`ciudad`,\r\n"
					+ "    `usuarios`.`direccion`,\r\n"
					+ "    `usuarios`.`password`\r\n"
					+ "FROM `java_hotel`.`usuarios`; WHERE id_usuario=?");
			stmt.setInt(1, usuario.getId_tipo_usuario());
			rs=stmt.executeQuery();
			
			if(rs!=null && rs.next()) {
				u = new Usuarios();
				u.setId_tipo_usuario(rs.getInt("id_tipo_usuario"));
				u.setApellido(rs.getString("apellido"));
				u.setNombre(rs.getString("nombre"));
				u.setTelefono(rs.getString("telefono"));
				u.setFec_nac(rs.getDate("fec_nac"));
				u.setEmail(rs.getString("email"));
				u.setPais(rs.getString("pais"));
				u.setCiudad(rs.getString("ciudad"));
				u.setDireccion(rs.getString("direccion"));
				u.setPassword(rs.getString("password"));
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
		return u;
	}
	
	public void remove(Usuarios usuario) {
		PreparedStatement stmt= null;
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"DELETE FROM `java_hotel`.`usuarios` WHERE id_usuario=?");
			stmt.setInt(1, usuario.getId_usuario());
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
	
	public void update(Usuarios usuario) {
		PreparedStatement stmt=null;
		try {
			stmt = DbConnector.getInstancia().getConn().
					prepareStatement("UPDATE `java_hotel`.`usuarios`\r\n"
							+ "SET\r\n"
							+ "`id_tipo_usuario` = ?,\r\n"
							+ "`apellido` = ?,\r\n"
							+ "`nombre` = ?,\r\n"
							+ "`telefono` = ?,\r\n"
							+ "`fec_nac` = ?,\r\n"
							+ "`email` = ?,\r\n"
							+ "`pais` = ?,\r\n"
							+ "`ciudad` = ?,\r\n"
							+ "`direccion` = ?,\r\n"
							+ "`password` = ?\r\n"
							+ "WHERE `id_usuario` =?;\r\n");
			stmt.setInt(1, usuario.getId_tipo_usuario());
			stmt.setString(2, usuario.getApellido());
			stmt.setString(3, usuario.getNombre());
			stmt.setString(4, usuario.getTelefono());
			stmt.setDate(5, usuario.getFec_nac());
			stmt.setString(6, usuario.getEmail());
			stmt.setString(7, usuario.getPais());
			stmt.setString(8, usuario.getCiudad());
			stmt.setString(9, usuario.getDireccion());
			stmt.setString(10, usuario.getPassword());
			stmt.setInt(11, usuario.getId_usuario());
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
	
	public void add(Usuarios usuario) {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"INSERT INTO `java_hotel`.`usuarios`\r\n"
							+ "(`id_tipo_usuario`,\r\n"
							+ "`apellido`,\r\n"
							+ "`nombre`,\r\n"
							+ "`telefono`,\r\n"
							+ "`fec_nac`,\r\n"
							+ "`email`,\r\n"
							+ "`pais`,\r\n"
							+ "`ciudad`,\r\n"
							+ "`direccion`,\r\n"
							+ "`password`)\r\n"
							+ "VALUES (?,?,?,?,?,?,?,?,?,?);",
							PreparedStatement.RETURN_GENERATED_KEYS
							);
			stmt.setInt(1, usuario.getId_tipo_usuario());
			stmt.setString(2, usuario.getApellido());
			stmt.setString(3, usuario.getNombre());
			stmt.setString(4, usuario.getTelefono());
			stmt.setDate(5, usuario.getFec_nac());
			stmt.setString(6, usuario.getEmail());
			stmt.setString(7, usuario.getPais());
			stmt.setString(8, usuario.getCiudad());
			stmt.setString(9, usuario.getDireccion());
			stmt.setString(10, usuario.getPassword());
			stmt.executeUpdate();
			
			keyResultSet=stmt.getGeneratedKeys();
            if(keyResultSet!=null && keyResultSet.next()){
                usuario.setId_usuario(keyResultSet.getInt(1));
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
