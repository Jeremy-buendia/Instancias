package application.model;

public class OpcionesDO {
	private int idOpciones;
	private int Idioma;
	private int Modo;
	private int Notificaciones;
	private int Fuente;
	private int Diseno;
	private int Ususario_idUsuario;
	
	public OpcionesDO() {
		super();
		
		// TODO Auto-generated constructor stub
	}
	
	public OpcionesDO(int idOpciones, int idioma, int modo, int notificaciones, int fuente, int diseno,
			int ususario_idUsuario) {
		super();
		this.idOpciones = idOpciones;
		Idioma = idioma;
		Modo = modo;
		Notificaciones = notificaciones;
		Fuente = fuente;
		Diseno = diseno;
		Ususario_idUsuario = ususario_idUsuario;
	}
	
	public int getIdOpciones() {
		return idOpciones;
	}
	public void setIdOpciones(int idOpciones) {
		this.idOpciones = idOpciones;
	}
	public int getIdioma() {
		return Idioma;
	}
	public void setIdioma(int idioma) {
		Idioma = idioma;
	}
	public int getModo() {
		return Modo;
	}
	public void setModo(int modo) {
		Modo = modo;
	}
	public int getNotificaciones() {
		return Notificaciones;
	}
	public void setNotificaciones(int notificaciones) {
		Notificaciones = notificaciones;
	}
	public int getFuente() {
		return Fuente;
	}
	public void setFuente(int fuente) {
		Fuente = fuente;
	}
	public int getDiseno() {
		return Diseno;
	}
	public void setDiseno(int diseno) {
		Diseno = diseno;
	}
	public int getUsusario_idUsuario() {
		return Ususario_idUsuario;
	}
	public void setUsusario_idUsuario(int ususario_idUsuario) {
		Ususario_idUsuario = ususario_idUsuario;
	}
	
	@Override
	public String toString() {
		return "OpcionesDO [idOpciones=" + idOpciones + ", Idioma=" + Idioma + ", Modo=" + Modo + ", Notificaciones="
				+ Notificaciones + ", Fuente=" + Fuente + ", Diseño=" + Diseno + ", Ususario_idUsuario="
				+ Ususario_idUsuario + "]";
	}

	
}
