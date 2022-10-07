package data;

import java.sql.*;
import java.time.ZoneId;
import java.util.LinkedList;
import entities.*;

public class DataPrecios {
	ZoneId defaultZoneId = ZoneId.systemDefault();
	
	public LinkedList<Precios> getAll(){
		Statement stmt = null;
		ResultSet rs = null;
		
		LinkedList<Precios> precios = new LinkedList<Precios>();
		
		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("drop temporary table if exists fechaMax;"
					+ "create temporary table fechaMax"
					+ "select id_tipo_hab, MAX(fec_vigencia) as fecha"
					+ "from precios"
					+ "where fec_vigencia <= current_date()"
					+ "group by id_tipo_hab;"
					+ "select p.id_tipo_hab, p.fec_vigencia, p.precio from precios p"
					+ "inner join fechaMax f on  f.id_tipo_hab=p.id_tipo_hab and f.fecha=p.fec_vigencia"
					+ "group by p.id_tipo_hab;");
			
			if (rs!=null) {
				while(rs.next()) {
					Precios p = new Precios();
					p.setId_tipo_hab(rs.getInt("id_tipo_hab"));
					p.setFec_vigencia(rs.getTimestamp("fec_vigencia"));
					p.setPrecio(rs.getFloat("precio"));
					precios.add(p);
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
		return precios;
	}
	
	public Precios getPrecio(Precios precio) {
		Precios p=null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("select max(fec_vigencia) into @fechamax"
					+ "from precios"
					+ "where id_tipo_hab=? and fec_vigencia <= current_date();"
					+ "select precio from precios"
					+ "where id_tipo_hab=? and fec_vigencia - @fechamax; ");
			stmt.setInt(1, precio.getId_tipo_hab());
			stmt.setInt(2, precio.getId_tipo_hab());
			rs=stmt.executeQuery();
			if (rs!=null && rs.next()) {
					p = new Precios();
					p.setId_tipo_hab(rs.getInt("id_tipo_hab"));
					p.setPrecio(rs.getFloat("precio"));
					
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
		return p;
	}
	
	public void add(Precios precio) {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		try {
			stmt=DbConnector.getInstancia().getConn().prepareStatement("insert into precios(id_tipo_hab,fec_vigencia,precio) values(?,?,?)",
							PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1,precio.getId_tipo_hab());
			stmt.setTimestamp(2, precio.getFec_vigencia());
			stmt.setFloat(3, precio.getPrecio());
			stmt.executeUpdate();
			
			keyResultSet=stmt.getGeneratedKeys();
            if(keyResultSet!=null && keyResultSet.next()){
                precio.setId_tipo_hab(keyResultSet.getInt(1));
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
