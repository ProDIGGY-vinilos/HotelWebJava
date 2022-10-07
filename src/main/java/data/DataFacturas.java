package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import entities.Habitaciones;
import entities.Facturas;

//

public class DataFacturas {
	public LinkedList<Facturas> getAll(){
		Statement stmt=null;
		ResultSet rs=null;
		LinkedList<Facturas> facturas = new LinkedList<Facturas>();
		
		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("SELECT `facturas`.`id_factura`,\r\n"
					+ "    `facturas`.`id_cliente`,\r\n"
					+ "    `facturas`.`id_reserva`,\r\n"
					+ "    `facturas`.`id_forma_pago`,\r\n"
					+ "    `facturas`.`fec_factura`\r\n"
					+ "FROM `java_hotel`.`facturas`;\r\n");
			
			if(rs!=null) {
				while(rs.next()) {
					Facturas f = new Facturas();
					f.setId_factura(rs.getInt("id_factura"));
					f.setId_cliente(rs.getInt("id_cliente"));
					f.setId_reserva(rs.getInt("id_reserva"));
					f.setId_forma_pago(rs.getInt("id_forma_pago"));
					f.setFec_factura(rs.getDate("fec_factura"));
					
					facturas.add(f);
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
		
		return facturas;
	}
	
	public Facturas getById(Facturas factura) {
		PreparedStatement stmt=null;
		ResultSet rs=null;
		Facturas f = null;
		
		try {
			stmt=DbConnector.getInstancia().getConn().prepareStatement("stancia().getConn().createStatement();\r\n"
					+ "			rs = stmt.executeQuery(\"SELECT `facturas`.`id_factura`,\r\n"
					+ "					+ \"    `facturas`.`id_cliente`,\r\n"
					+ "					+ \"    `facturas`.`id_reserva`,\r\n"
					+ "					+ \"    `facturas`.`id_forma_pago`,\r\n"
					+ "					+ \"    `facturas`.`fec_factura`,\r\n"
					+ "					+ \"FROM `java_hotel`.`facturas`; WHERE id_factura=?");
			stmt.setInt(1, factura.getId_factura());
			rs=stmt.executeQuery();
			
			if(rs!=null && rs.next()) {
				f = new Facturas();
				f.setId_factura(rs.getInt("id_factura"));
				f.setId_cliente(rs.getInt("id_cliente"));
				f.setId_reserva(rs.getInt("id_reserva"));
				f.setId_forma_pago(rs.getInt("id_forma_pago"));
				f.setFec_factura(rs.getDate("fec_factura"));
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
		return f;
	}
	
	public void remove(Facturas factura) {
		PreparedStatement stmt= null;
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"DELETE FROM `java_hotel`.`habitaciones` WHERE id_factura=?");
			stmt.setInt(1, factura.getId_factura());
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
	
	public void update(Facturas factura) {
		PreparedStatement stmt=null;
		try {
			stmt = DbConnector.getInstancia().getConn().
					prepareStatement("UPDATE `java_hotel`.`facturas`\r\n"
							+ "SET\r\n"
							+ "`id_cliente` = ?,\r\n"
							+ "`id_reserva` = ?,\r\n"
							+ "`id_forma_pago` = ?,\r\n"
							+ "`fec_factura` = ?\r\n"
							+ "		WHERE `id_factura` = ?;");
			stmt.setInt(1, factura.getId_cliente());
			stmt.setInt(2, factura.getId_reserva());
			stmt.setInt(3, factura.getId_forma_pago());
			stmt.setDate(4, factura.getFec_factura());
			stmt.setInt(5, factura.getId_factura());
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
	  
	
	
	
	public void add(Facturas factura) {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"INSERT INTO `java_hotel`.`facturas`\r\n"
							+ "`id_cliente`,\r\n"
							+ "`id_reserva`,\r\n"
							+ "`id_forma_pago`,\r\n"
							+ "`fec_factura`)\r\n"
							+ "VALUES\r\n"
							+ "(?,?,?,?)",
							PreparedStatement.RETURN_GENERATED_KEYS
							);
			stmt.setInt(1, factura.getId_cliente());
			stmt.setInt(2, factura.getId_reserva());
			stmt.setInt(3, factura.getId_forma_pago());
			stmt.setDate(4, factura.getFec_factura());
			stmt.executeUpdate();
			
			keyResultSet=stmt.getGeneratedKeys();
            if(keyResultSet!=null && keyResultSet.next()){
                factura.setId_factura(keyResultSet.getInt(1));
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
