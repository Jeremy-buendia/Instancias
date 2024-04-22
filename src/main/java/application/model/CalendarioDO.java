package application.model;

public class CalendarioDO {
	private int idCalendario;
	private String Fecha;
	private String Imagen_Calendario;
	private int Usuario_idUsuario;
	
	public CalendarioDO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CalendarioDO(int idCalendario, String fecha, String imagen_Calendario, int usuario_idUsuario) {
		super();
		this.idCalendario = idCalendario;
		Fecha = fecha;
		Imagen_Calendario = imagen_Calendario;
		Usuario_idUsuario = usuario_idUsuario;
	}
	
	public int getIdCalendario() {
		return idCalendario;
	}
	public void setIdCalendario(int idCalendario) {
		this.idCalendario = idCalendario;
	}
	public String getFecha() {
		return Fecha;
	}
	public void setFecha(String fecha) {
		Fecha = fecha;
	}
	public String getImagen_Calendario() {
		return Imagen_Calendario;
	}
	public void setImagen_Calendario(String imagen_Calendario) {
		Imagen_Calendario = imagen_Calendario;
	}
	public int getUsuario_idUsuario() {
		return Usuario_idUsuario;
	}
	public void setUsuario_idUsuario(int usuario_idUsuario) {
		Usuario_idUsuario = usuario_idUsuario;
	}
	
	@Override
	public String toString() {
		return "CalendarioDO [idCalendario=" + idCalendario + ", Fecha=" + Fecha + ", Imagen_Calendario="
				+ Imagen_Calendario + ", Usuario_idUsuario=" + Usuario_idUsuario + "]";
	}
	
}
