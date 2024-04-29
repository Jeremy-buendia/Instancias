package application.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ImagenDAO {

	public static int contador = 0;

	/**
	 * Función que sube una imagen a la carpeta de Instancias y a la base de datos
	 * 
	 * @param con
	 * @param imagen
	 * @return
	 */
	public static int subirImagen(Connection con, ImagenDO imagen) {
		try {

			String rutaCarpeta = System.getProperty("user.home") + "\\Pictures\\Instancias\\"
					+ imagen.getUsuario_idUsuario() + "\\";
			// Comprobamos si la carpeta de Instancias está creada
			if (BuscarCarpeta(imagen) == -1) {
				File carpeta = new File(rutaCarpeta);
				carpeta.mkdirs();
			}

			// Query para insertar la imagen en la BD
			String query = "INSERT INTO imagen (Descripcion_Imagen, Ubicacion, Usuario_idUsuario, Marcado) VALUES(?, ?, ?, ?)";

			// Creamos un PreparedStatement
			PreparedStatement pstmt = con.prepareStatement(query);
			// Asignamos los valores a los ?
			pstmt.setString(1, imagen.getNombre_imagen());
			pstmt.setString(2, rutaCarpeta);
			pstmt.setInt(3, imagen.getUsuario_idUsuario());
			pstmt.setInt(4, imagen.getMarcado());

			pstmt.executeUpdate();

			contador++;
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static int copiarImagen(File imagen, ImagenDO objetoImg) {
		try {
			FileInputStream fis = new FileInputStream(imagen);
			FileOutputStream fos = new FileOutputStream(System.getProperty("user.home") + "\\Pictures\\Instancias\\"
					+ objetoImg.getUsuario_idUsuario() + "\\" + +contador + ".jpg");

			byte[] buffer1K = new byte[1024];
			int numDatos = fis.read(buffer1K);

			while (numDatos != -1) {
				fos.write(buffer1K);
				numDatos = fis.read(buffer1K);
			}

			fis.close();
			fos.close();

			return 0;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return -1;
		} catch (IOException e) {
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
	public static int BuscarCarpeta(ImagenDO imagen) {
		String rutaCarpeta = System.getProperty("user.home") + "\\Pictures\\Instancias\\"
				+ imagen.getUsuario_idUsuario();

		File carpeta = new File(rutaCarpeta);

		if (carpeta.exists() && carpeta.isDirectory()) {
			return 0;
		}

		return -1;
	}

	/**
	 * Función para cargar una Imagen de la BD
	 * 
	 * @param con
	 * @param id
	 * @return un ImagenDO o null
	 */
	public static ImagenDO getImagen(Connection con, int id) {
		try {
			String query = "SELECT * FROM imagen WHERE idImagen=?";

			PreparedStatement pstmt = con.prepareStatement(query);

			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				ImagenDO imagen = new ImagenDO();
				imagen.setIdImagen(rs.getInt("idImagen"));
				imagen.setNombre_imagen(rs.getString("Descripcion_Imagen"));
				imagen.setUbicacion(rs.getString("ubicacion"));
				imagen.setFecha_Imagen(rs.getString("Fecha_Imagen"));
				imagen.setUsuario_idUsuario(rs.getInt("Usuario_idUsuario"));
				imagen.setMarcado(rs.getInt("Marcado"));
				return imagen;
			}

			return null;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
