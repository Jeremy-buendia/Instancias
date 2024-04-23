package application.model;

public class ImagenDO {
	private int idImagen;
	private String Nombre_imagen;
	private String Ubicacion;
	private String Fecha_Imagen;
	private int Usuario_idUsuario;
	private int Marcado;

	public int getIdImagen() {
		return idImagen;
	}

	public void setIdImagen(int idImagen) {
		this.idImagen = idImagen;
	}

	public String getNombre_imagen() {
		return Nombre_imagen;
	}

	public void setNombre_imagen(String nombre_imagen) {
		Nombre_imagen = nombre_imagen;
	}

	public String getUbicacion() {
		return Ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		Ubicacion = ubicacion;
	}

	public String getFecha_Imagen() {
		return Fecha_Imagen;
	}

	public void setFecha_Imagen(String fecha_Imagen) {
		Fecha_Imagen = fecha_Imagen;
	}

	public int getUsuario_idUsuario() {
		return Usuario_idUsuario;
	}

	public void setUsuario_idUsuario(int usuario_idUsuario) {
		Usuario_idUsuario = usuario_idUsuario;
	}

	public int getMarcado() {
		return Marcado;
	}

	public void setMarcado(int marcado) {
		Marcado = marcado;
	}

	public ImagenDO(int idImagen, String nombre_imagen, String ubicacion, String fecha_Imagen, int usuario_idUsuario,
			int marcado) {
		super();
		this.idImagen = idImagen;
		Nombre_imagen = nombre_imagen;
		Ubicacion = ubicacion;
		Fecha_Imagen = fecha_Imagen;
		Usuario_idUsuario = usuario_idUsuario;
		Marcado = marcado;
	}

	public ImagenDO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ImagenDO [idImagen=" + idImagen + ", Nombre_imagen=" + Nombre_imagen + ", Ubicacion=" + Ubicacion
				+ ", Fecha_Imagen=" + Fecha_Imagen + ", Usuario_idUsuario=" + Usuario_idUsuario + ", Marcado=" + Marcado
				+ "]";
	}

}
