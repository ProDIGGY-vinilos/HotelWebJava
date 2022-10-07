package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import entities.Clientes;
import entities.TipoUsuario;

public class DataClientes {
	public LinkedList<Clientes> getAll(){
		Statement stmt = null;
		ResultSet rs = null;
		//comentario al pedo
		LinkedList<Clientes> cl = new LinkedList<>();
		
		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("select * from clientes");
			
			if (rs!=null) {
				while(rs.next()) {
					Clientes c = new Clientes();
					c.setId_cliente(rs.getInt("id_cliente"));
					c.setId_usuario(rs.getInt("id_usuario"));
					c.setNacionalidad(rs.getString("nacionalidad"));
					c.setPasaporte(rs.getString("pasaporte"));
					cl.add(c);
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
		
		return cl;
	}

public Clientes getById(Clientes cliente) {
	Clientes c=null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	try {
		stmt = DbConnector.getInstancia().getConn().prepareStatement("SELECT id_cliente, id_usuario, nacionalidad, pasaporte "
																	+ "WHERE id_cliente = ?");
		stmt.setInt(1, cliente.getId_cliente());
		rs=stmt.executeQuery();
		if (rs!=null && rs.next()) {
				c = new Clientes();
				c.setId_cliente(cliente.getId_cliente());
				c.setId_usuario(rs.getInt("id_usuario"));
				c.setNacionalidad(rs.getString("nacionalidad"));
				c.setPasaporte(rs.getString("pasaporte"));
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
	return c;
}

public void add(Clientes cliente) {
	PreparedStatement stmt= null;
	ResultSet keyResultSet=null;
	try {
		stmt=DbConnector.getInstancia().getConn().prepareStatement("INSERT INTO clientes"
																	+ "(id_usuario, nacionalidad, pasaporte)"
																	+ "VALUES(?, ?, ?)",
						PreparedStatement.RETURN_GENERATED_KEYS);
		stmt.setInt(1,cliente.getId_usuario());
		stmt.setString(2, cliente.getNacionalidad());
		stmt.setString(3, cliente.getPasaporte());
		stmt.executeUpdate();
		
		keyResultSet=stmt.getGeneratedKeys();
        if(keyResultSet!=null && keyResultSet.next()){
            cliente.setId_cliente(keyResultSet.getInt(1));
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

public void update(Clientes cliente) {
	PreparedStatement stmt= null;
	try {
		stmt=DbConnector.getInstancia().getConn().
				prepareStatement(
						"UPDATE cliente SET"
						+ "(id_usuario, nacionalidad ,pasaporte) "
						+ "VALUES (?,?,?)"
						+ "WHERE id_cliente=?");
		stmt.setInt(1, cliente.getId_usuario());
		stmt.setString(2, cliente.getNacionalidad());
		stmt.setString(3, cliente.getPasaporte());
		stmt.setInt(4, cliente.getId_cliente());
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

