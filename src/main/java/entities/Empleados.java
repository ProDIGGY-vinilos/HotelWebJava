package entities;
import java.time.LocalDateTime;

public class Empleados {
	// yo
	private int cuil;
	private int id_usuario;
	private LocalDateTime fec_ingreso;
	private LocalDateTime fec_egreso;
	public int getCuil() {
		return cuil;
	}
	public void setCuil(int cuil) {
		this.cuil = cuil;
	}
	public int getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}
	public LocalDateTime getFec_ingreso() {
		return fec_ingreso;
	}
	public void setFec_ingreso(LocalDateTime fec_ingreso) {
		this.fec_ingreso = fec_ingreso;
	}
	public LocalDateTime getFec_egreso() {
		return fec_egreso;
	}
	public void setFec_egreso(LocalDateTime fec_egreso) {
		this.fec_egreso = fec_egreso;
	}
	
	
}
