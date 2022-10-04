package entities;

import java.time.*;

public class Reservas {
	private int id_reserva;
	private int id_cliente;
	private int id_habitacion;
	private ZonedDateTime fecha_reserva;
	private ZonedDateTime fecha_checkin;
	private ZonedDateTime fecha_checkinreal;
	private ZonedDateTime fecha_checkout;
	private ZonedDateTime fecha_checkoutreal;
	private String estado;
	
	public int getId_reserva() {
		return id_reserva;
	}
	public void setId_reserva(int id_reserva) {
		this.id_reserva = id_reserva;
	}
	public int getId_cliente() {
		return id_cliente;
	}
	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}
	public int getId_habitacion() {
		return id_habitacion;
	}
	public void setId_habitacion(int id_habitacion) {
		this.id_habitacion = id_habitacion;
	}
	public ZonedDateTime getFecha_reserva() {
		return fecha_reserva;
	}
	public void setFecha_reserva(ZonedDateTime fecha_reserva) {
		this.fecha_reserva = fecha_reserva;
	}
	public ZonedDateTime getFecha_checkin() {
		return fecha_checkin;
	}
	public void setFecha_checkin(ZonedDateTime fecha_checkin) {
		this.fecha_checkin = fecha_checkin;
	}
	public ZonedDateTime getFecha_checkinreal() {
		return fecha_checkinreal;
	}
	public void setFecha_checkinreal(ZonedDateTime fecha_checkinreal) {
		this.fecha_checkinreal = fecha_checkinreal;
	}
	public ZonedDateTime getFecha_checkout() {
		return fecha_checkout;
	}
	public void setFecha_checkout(ZonedDateTime fecha_checkout) {
		this.fecha_checkout = fecha_checkout;
	}
	public ZonedDateTime getFecha_checkoutreal() {
		return fecha_checkoutreal;
	}
	public void setFecha_checkoutreal(ZonedDateTime fecha_checkoutreal) {
		this.fecha_checkoutreal = fecha_checkoutreal;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
	

}
