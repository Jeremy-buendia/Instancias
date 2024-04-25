package application.model;

public class NotificacionesDO {

	
	private int idNotificaciones;
	private int evento;
	private String mensaje;
	
	
	public int getIdNotificaciones() {
		return idNotificaciones;
	}
	public void setIdNotificaciones(int idNotificaciones) {
		this.idNotificaciones = idNotificaciones;
	}
	public int getEvento() {
		return evento;
	}
	public void setEvento(int evento) {
		this.evento = evento;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	public NotificacionesDO(int idNotificaciones, int evento, String mensaje) {
		super();
		this.idNotificaciones = idNotificaciones;
		this.evento = evento;
		this.mensaje = mensaje;
	}
	
	public NotificacionesDO() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "NotificacionesDO [idNotificaciones=" + idNotificaciones + ", evento=" + evento + ", mensaje=" + mensaje
				+ "]";
	}
	
	
}
