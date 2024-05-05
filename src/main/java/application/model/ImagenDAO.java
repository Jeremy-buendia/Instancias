package application.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import application.LoginController;

public class ImagenDAO {

	public static int contador = 0;

	/**
	 * Función que sube una imagen a la carpeta de Instancias y a la base de datos
	 * 
	 * @param con
	 * @param objetoImg
	 * @return
	 */
	public static int subirImagen(Connection con, ImagenDO objetoImg, File imagen) {
		try {

			String rutaCarpeta = System.getProperty("user.home") + "\\Pictures\\Instancias\\"
					+ objetoImg.getUsuario_idUsuario() + "\\";
			// Comprobamos si la carpeta de Instancias está creada
			if (BuscarCarpeta(objetoImg) == -1) {
				File carpeta = new File(rutaCarpeta);
				carpeta.mkdirs();
			}

			if (imagen.getName().substring(imagen.getName().length() - 4).equals(".jpg")) {
				rutaCarpeta += contador + 1 + ".jpg";
			} else if (imagen.getName().substring(imagen.getName().length() - 4).equals(".png")) {
				rutaCarpeta += contador + 1 + ".png";
			} else if (imagen.getName().substring(imagen.getName().length() - 4).equals(".jpeg")) {
				rutaCarpeta += contador + 1 + ".jpeg";
			}

			// Query para insertar la imagen en la BD
			String query = "INSERT INTO imagen (Descripcion_Imagen, Ubicacion, Usuario_idUsuario, Marcado) VALUES(?, ?, ?, ?)";

			// Creamos un PreparedStatement
			PreparedStatement pstmt = con.prepareStatement(query);
			// Asignamos los valores a los ?
			pstmt.setString(1, objetoImg.getNombre_imagen());
			pstmt.setString(2, rutaCarpeta);
			pstmt.setInt(3, objetoImg.getUsuario_idUsuario());
			pstmt.setInt(4, objetoImg.getMarcado());

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
			FileOutputStream fos;

			if (imagen.getName().substring(imagen.getName().length() - 4).equals(".jpg")) {
				fos = new FileOutputStream(System.getProperty("user.home") + "\\Pictures\\Instancias\\"
						+ objetoImg.getUsuario_idUsuario() + "\\" + contador + ".jpg");
				byte[] buffer1K = new byte[1024];
				int numDatos = fis.read(buffer1K);

				while (numDatos != -1) {
					fos.write(buffer1K);
					numDatos = fis.read(buffer1K);
				}
				fos.close();
			}

			if (imagen.getName().substring(imagen.getName().length() - 4).equals(".png")) {
				fos = new FileOutputStream(System.getProperty("user.home") + "\\Pictures\\Instancias\\"
						+ objetoImg.getUsuario_idUsuario() + "\\" + contador + ".png");
				byte[] buffer1K = new byte[1024];
				int numDatos = fis.read(buffer1K);

				while (numDatos != -1) {
					fos.write(buffer1K);
					numDatos = fis.read(buffer1K);
				}
				fos.close();
			}

			if (imagen.getName().substring(imagen.getName().length() - 4).equals(".jpeg")) {
				fos = new FileOutputStream(System.getProperty("user.home") + "\\Pictures\\Instancias\\"
						+ objetoImg.getUsuario_idUsuario() + "\\" + contador + ".jpeg");
				byte[] buffer1K = new byte[1024];
				int numDatos = fis.read(buffer1K);

				while (numDatos != -1) {
					fos.write(buffer1K);
					numDatos = fis.read(buffer1K);
				}
				fos.close();
			}

			fis.close();

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
	 * Función que coge una imagen y la copia a otro directorio
	 * 
	 * @param imagen
	 * @param directorio
	 * @return
	 */
	public static int descargarImagen(File imagen, File directorio) {
		try {
			FileInputStream fis = new FileInputStream(imagen);
			FileOutputStream fos = new FileOutputStream(directorio + "\\" + imagen.getName());

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

	public static int BuscarCarpetaSinImg(Connection con) {
		String rutaCarpeta = System.getProperty("user.home") + "\\Pictures\\Instancias\\"
				+ UsuarioDAO.cargarCorreo(con, LoginController.correoUsuario).getId();

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

	public static ImagenDO getImagenPorRuta(Connection con, String ruta) {
		try {
			String query = "SELECT * FROM imagen WHERE ubicacion=?";

			PreparedStatement pstmt = con.prepareStatement(query);

			pstmt.setString(1, ruta);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				ImagenDO imagen = new ImagenDO();
				imagen.setIdImagen(rs.getInt("idImagen"));
				imagen.setNombre_imagen(rs.getString("Descripcion_Imagen"));
				imagen.setUbicacion(rs.getString("Ubicacion"));
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

	/**
	 * Función que recibe una fecha y devuelve todas las fotos de esa fecha
	 * 
	 * @param con
	 * @param fecha
	 * @return
	 */
	public static ArrayList<String> getDia(Connection con, LocalDate fecha) {
		String query = "SELECT * FROM imagen WHERE Fecha_Imagen = ? AND Usuario_IdUsuario = ?";
		ArrayList<String> rutasCarpeta = new ArrayList<>();

		try {
			PreparedStatement pstmt = con.prepareStatement(query);

			pstmt.setDate(1, Date.valueOf(fecha));
			pstmt.setInt(2, UsuarioDAO.cargarId(con, LoginController.correoUsuario).getId());

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				rutasCarpeta.add(rs.getString("ubicacion"));
			}
			return rutasCarpeta;
		} catch (Exception e) {
			return null;
		}

	}

	public static ArrayList<String> getMarcados(Connection con) {
		ArrayList<String> rutasCarpeta = new ArrayList<>();
		String query = "SELECT * FROM imagen WHERE Marcado = 1 AND Usuario_IdUsuario = ?";
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setInt(1, UsuarioDAO.cargarId(con, LoginController.correoUsuario).getId());

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				rutasCarpeta.add(rs.getString("ubicacion"));
			}
			return rutasCarpeta;
		} catch (Exception e) {
			return null;
		}
	}

	public static int estaMarcado(Connection con, String rutaImagen) {
		String query = "SELECT Marcado FROM imagen WHERE Ubicacion = ?";

		try {
			PreparedStatement pstmt = con.prepareStatement(query);

			pstmt.setString(1, rutaImagen);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("Marcado");
			}

			return -1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}

	}

	public static int cambiarMarcado(Connection con, String rutaImagen, int marcar) {
		String query = "UPDATE imagen SET marcado = ? WHERE Ubicacion = ?";

		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement(query);

			pstmt.setInt(1, marcar);
			pstmt.setString(2, rutaImagen);

			pstmt.executeUpdate();

			return 0;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	// remover imagen de la carpeta y la base de datos provisional necesito revisar
	public static int eliminarImagen(Connection con, ImagenDO imagen) {
		try {
			// Obtener la ubicación de la imagen
			String rutaCarpeta = System.getProperty("user.home") + "\\Pictures\\Instancias\\"
					+ imagen.getUsuario_idUsuario() + "\\";

			String ubicacionImagen = rutaCarpeta + imagen.getNombre_imagen();

			// Eliminar el archivo de la carpeta del usuario
			File imagenFile = new File(ubicacionImagen);
			if (imagenFile.exists()) {
				if (imagenFile.delete()) {
					System.out.println("Archivo de imagen eliminado correctamente.");
				} else {
					System.out.println("No se pudo eliminar el archivo de la imagen.");
					return -1; // Error al eliminar el archivo de la imagen
				}
			} else {
				System.out.println("No se encontró el archivo de la imagen.");
				return -1; // No se encontró el archivo de la imagen
			}

			// Query para eliminar la imagen de la BD
			String query = "DELETE FROM imagen WHERE idImagen = ?";

			// Creamos un PreparedStatement
			PreparedStatement pstmt = con.prepareStatement(query);
			// Asignamos los valores a los parámetros
			pstmt.setInt(1, imagen.getIdImagen());

			// Ejecutamos la consulta
			int rowsAffected = pstmt.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Imagen eliminada correctamente de la base de datos.");
				return 0; // Éxito
			} else {
				System.out.println("No se encontró la imagen en la base de datos para eliminar.");
				return -1; // No se encontró la imagen en la base de datos
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1; // Error SQL
		}
	}
}
