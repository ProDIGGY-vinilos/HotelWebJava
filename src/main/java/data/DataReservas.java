package data;

import java.sql.*;
import java.util.LinkedList;
import entities.*;

import entities.Reservas;

public class DataReservas {
	
	public LinkedList<Reservas> getAll(){
		Statement stmt = null;
		ResultSet rs = null;
		
		LinkedList<Reservas> res = new LinkedList<>();
		
		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery("select idreserva,idcliente,idhabitacion,fecha_reserva,fecha_checkin,fecha_checkout,fecha_checkinreal,fecha_checkoutreal,estado from reservas");
			
			if (rs!=null) {
				while(rs.next()) {
					Reservas r = new Reservas();
					r.setId_reserva(rs.getInt("idreserva"));
					r.setId_cliente(rs.getInt("idcliente"));
					r.setId_habitacion(rs.getInt("idhabitacion"));
					r.setFecha_reserva(rs.getTimestamp("fecha_reserva"));
					r.setFecha_checkin(rs.("fecha_checkin"));
					r.setFecha_checkout(rs.getTimestamp("fecha_checkout"));
					r.setFecha_checkinreal(rs.getTimestamp("fecha_checkinreal"));
					r.setFecha_checkoutreal(rs.getTimestamp("fecha_checkoutreal"));
					r.setEstado(rs.getString("estado"));
					
					res.add(r);
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
		
		return res;
	}
	
	public Reservas getById(Reservas reserva) {
		Reservas r=null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("select idreserva,idcliente,idhabitacion,fecha_reserva,fecha_checkin,fecha_checkout,fecha_checkinreal,fecha_checkoutreal,estado from reservas where idreserva=?");
			stmt.setInt(1, reserva.getId_reserva());
			rs=stmt.executeQuery();
			if (rs!=null && rs.next()) {
					r = new Reservas();
					r.setId_reserva(rs.getInt("idreserva"));
					r.setId_cliente(rs.getInt("idcliente"));
					r.setId_habitacion(rs.getInt("idhabitacion"));
					r.setFecha_reserva(rs.getTimestamp("fecha_reserva"));
					r.setFecha_checkin(rs.getTimestamp("fecha_checkin"));
					r.setFecha_checkout(rs.getTimestamp("fecha_checkout"));
					r.setFecha_checkinreal(rs.getTimestamp("fecha_checkinreal"));
					r.setFecha_checkoutreal(rs.getTimestamp("fecha_checkoutreal"));
					r.setEstado(rs.getString("estado"));
					
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
		return r;
	}
	
	public void add(Reservas reserva) {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		try {
			stmt=DbConnector.getInstancia().getConn().prepareStatement("insert into reservas(idcliente,idhabitacion,fecha_reserva,fecha_checkin,fecha_checkout,fecha_checkinreal,fecha_checkoutreal,estado) values(?,?,?,?,?,?,?,?)",
							PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, reserva.getId_cliente());
			stmt.setInt(2, reserva.getId_habitacion());
			stmt.setTimestamp(3, reserva.getFecha_reserva());
			stmt.setTimestamp(4, reserva.getFecha_checkin());
			stmt.setTimestamp(5, reserva.getFecha_checkout());
			stmt.setTimestamp(6, reserva.getFecha_checkinreal());
			stmt.setTimestamp(7, reserva.getFecha_checkoutreal());
			stmt.setString(8, reserva.getEstado());
			stmt.executeUpdate();
			
			keyResultSet=stmt.getGeneratedKeys();
            if(keyResultSet!=null && keyResultSet.next()){
                reserva.setId_reserva(keyResultSet.getInt(1));
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
	
	public void update(Reservas reserva) {
		PreparedStatement stmt= null;
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"update reservas set  idcliente=?,idhabitacion=?,fecha_reserva=?,fecha_checkin=?,fecha_checkout=?,fecha_checkinreal=?,fecha_checkoutreal=?,estado=? where idreserva=?");
			stmt.setInt(1, reserva.getId_cliente());
			stmt.setInt(2, reserva.getId_habitacion());
			stmt.setTimestamp(3, reserva.getFecha_reserva());
			stmt.setTimestamp(4, reserva.getFecha_checkin());
			stmt.setTimestamp(5, reserva.getFecha_checkout());
			stmt.setTimestamp(6, reserva.getFecha_checkinreal());
			stmt.setTimestamp(7, reserva.getFecha_checkoutreal());
			stmt.setString(8, reserva.getEstado());
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
	
	public void remove(Reservas reserva) {
		PreparedStatement stmt= null;
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"delete from resevas where idreserva=?");
			stmt.setInt(1, reserva.getId_reserva());
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
