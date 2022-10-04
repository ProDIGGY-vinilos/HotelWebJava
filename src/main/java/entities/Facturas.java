package entities;

import java.time.ZonedDateTime;

public class Facturas {
	private int id_factura;
	private int id_cliente;
	private int id_reserva;
	private int id_forma_pago;
	private ZonedDateTime fec_factura;
	
	
	public int getId_factura() {
		return id_factura;
	}
	public void setId_factura(int id_factura) {
		this.id_factura = id_factura;
	}
	public int getId_cliente() {
		return id_cliente;
	}
	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}
	public int getId_reserva() {
		return id_reserva;
	}
	public void setId_reserva(int id_reserva) {
		this.id_reserva = id_reserva;
	}
	public int getId_forma_pago() {
		return id_forma_pago;
	}
	public void setId_forma_pago(int id_forma_pago) {
		this.id_forma_pago = id_forma_pago;
	}
	public ZonedDateTime getFec_factura() {
		return fec_factura;
	}
	public void setFec_factura(ZonedDateTime fec_factura) {
		this.fec_factura = fec_factura;
	}
	
	

}
