package application.model;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ImagenDAO {

	/**
	 * Función que sube una imagen a la carpeta de Instancias y a la base de datos
	 * 
	 * @param con
	 * @param imagen
	 * @return
	 */
	public static int subirFoto(Connection con, ImagenDO imagen) {
		try {

			// Comprobamos si la carpeta de Instancias está creada
			if (BuscarCarpeta() == -1) {
				String rutaCarpeta = System.getProperty("user.home") + "\\Pictures\\Instancias";

				File carpeta = new File(rutaCarpeta);
				carpeta.mkdirs();
			}

			// Query para insertar la imagen en la BD
			String query = "INSERT INTO imagen (Nombre_Imagen, Ubicación, Fecha_Imagen, Usuario_idUsuario, Marcado) VALUES(?, ?, ?, ?, ?)";

			// Creamos un PreparedStatement
			PreparedStatement pstmt = con.prepareStatement(query);
			// Asignamos los valores a los ?
			pstmt.setString(1, imagen.getNombre_imagen());
			pstmt.setString(2, imagen.getUbicacion());
			pstmt.setString(3, imagen.getFecha_Imagen());
			pstmt.setInt(4, imagen.getUsuario_idUsuario());
			pstmt.setInt(5, imagen.getMarcado());

			pstmt.executeUpdate();

			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Función que busca si la carpeta dónde irán todas las imágenes está creada o
	 * no
	 * 
	 * @return 0 si está creada o -1 si no está creada
	 */
	public static int BuscarCarpeta() {
		String rutaCarpeta = System.getProperty("user.home") + "\\Pictures\\Instancias";

		File carpeta = new File(rutaCarpeta);

		if (carpeta.exists() && carpeta.isDirectory()) {
			return 0;
		}

		return -1;
	}

}
