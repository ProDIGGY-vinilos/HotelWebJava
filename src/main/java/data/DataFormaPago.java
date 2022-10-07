package data;

import java.sql.*;
import java.util.LinkedList;
import entities.*;

public class DataFormaPago {
	
	public LinkedList<FormaPago> getAll(){
		Statement stmt = null;
		ResultSet rs = null;
		
		LinkedList<FormaPago> pagos = new LinkedList<>();
		
		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("select id_forma_pago,desc_forma_pago from forma_pago");
			
			if (rs!=null) {
				while(rs.next()) {
					FormaPago f = new FormaPago();
					f.setId_forma_pago(rs.getInt("id_forma_pago"));
					f.setDesc_forma_pago(rs.getString("desc_forma_pago"));
					
					pagos.add(f);
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
		
		return pagos;
	}
	
	public FormaPago getById(FormaPago pago) {
		FormaPago f=null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("select id_forma_pago,desc_forma_pago from forma_pago where id_forma_pago=?");
			stmt.setInt(1, pago.getId_forma_pago());
			rs=stmt.executeQuery();
			if (rs!=null && rs.next()) {
					f = new FormaPago();
					f.setId_forma_pago(rs.getInt("id_forma_pago"));
					f.setDesc_forma_pago(rs.getString("desc_forma_pago"));
					
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
		return f;
	}
	
	public void add(FormaPago pago) {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		try {
			stmt=DbConnector.getInstancia().getConn().prepareStatement("insert into forma_pago(desc_forma_pago) values(?)",
							PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setString(1,pago.getDesc_forma_pago());
			stmt.executeUpdate();
			
			keyResultSet=stmt.getGeneratedKeys();
            if(keyResultSet!=null && keyResultSet.next()){
                pago.setId_forma_pago(keyResultSet.getInt(1));
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
	
	public void update(FormaPago pago) {
		PreparedStatement stmt= null;
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"update forma_pago set desc_forma_pago=? where id_forma_pago=?");
			stmt.setString(1, pago.getDesc_forma_pago());
			stmt.setInt(2, pago.getId_forma_pago());
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
	
	public void remove(FormaPago pago) {
		PreparedStatement stmt= null;
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"delete from forma_pago where id_forma_pago=?");
			stmt.setInt(1, pago.getId_forma_pago());
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
