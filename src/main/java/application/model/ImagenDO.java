package application.model;

import java.sql.Connection;

public class ImagenDO {
	private int idImagen;
	private String Nombre_imagen;
	private String Ubicacion;
	private String Fecha_Imagen;
	private int Usuario_idUsuario;
	private int Marcado;

	public static int subirFoto(Connection con, int idUsuario) {
		try {

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
