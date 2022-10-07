package entities;

import java.time.ZonedDateTime;

public class Precio {
	
	private int id_hab;
	private ZonedDateTime fec_vigencia;
	private float precio;
	
	public int getId_hab() {
		return id_hab;
	}
	public void setId_hab(int id_hab) {
		this.id_hab = id_hab;
	}
	public ZonedDateTime getFec_vigencia() {
		return fec_vigencia;
	}
	public void setFec_vigencia(ZonedDateTime fec_vigencia) {
		this.fec_vigencia = fec_vigencia;
	}
	public float getPrecio() {
		return precio;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}

}
