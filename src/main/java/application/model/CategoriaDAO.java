package application.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.ChoiceBox;

public class CategoriaDAO {

	public static int crearCategoria(Connection con, String nombreCat) {
		try {

			String queryInsert = "INSERT INTO categoria (Nombre_Categoria) VALUES(?)";
			PreparedStatement pstmt = con.prepareStatement(queryInsert);
			pstmt.setString(1, nombreCat);

			pstmt.executeUpdate();
			return 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return -1; // Devolvemos -1 si hay un error SQL
		}

	}

	public static int asignarCategoria(Connection con, CategoriaDO categoria) {
		try {

			String query = "UPDATE Categoria SET ";

			boolean campoPrevio = false;
			int numAff = -1;
			// Si los campos no son nulos, los vamos
			// añadiendo a la sentencia
			if (categoria.getNombreCategoria() != null) {
				query = query + "Nombre_Categoria = ?";
			}

			query = query + " WHERE idCategoria = ?";

			// Generamos el preparedstatement con la
			// query
			PreparedStatement pstmt = con.prepareStatement(query);

			// Enlazamos los datos a las ? del prepared
			// statement

			int posInterrogacion = 1;

			// Si los campos no son nulos, los vamos
			// añadiendo a la sentencia
			if (categoria.getNombreCategoria() != null) {

				pstmt.setString(posInterrogacion, categoria.getNombreCategoria());
				// Incrementamos la posicion de la
				// interrogacion para
				// El siguiente posible campo
				posInterrogacion++;
			}

			pstmt.setInt(posInterrogacion, categoria.getIdCategoria());

			if (campoPrevio) {

				numAff = pstmt.executeUpdate();

			}

			return 0;
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return -1;
		}
	}

	// Método para añadir una categoría a una imagen
	public static void agregarCategoriaAImagen(Connection con, int idCategoria, int idImagen, int idUsuario) {
		PreparedStatement pstmt;
		try {
			// Consulta SQL para insertar la categoría en la tabla Categoria_has_Imagen
			String sql = "INSERT INTO Categoria_has_Imagen (Categoria_idCategoria, Imagen_idImagen, Imagen_Usuario_idUsuario) "
					+ "VALUES (?, ?, ?)";

			pstmt = con.prepareStatement(sql);

			// Preparar la declaración
			pstmt.setInt(1, idCategoria);
			pstmt.setInt(2, idImagen);
			pstmt.setInt(3, idUsuario);

			// Ejecutar la consulta
			pstmt.executeUpdate();

			System.out.println("Categoría añadida a la imagen correctamente.");
		} catch (SQLException e) {
			e.printStackTrace();
		} // finally {
//			// Cerrar la conexión y liberar recursos
//			try {
//				if (pstmt != null)
//					pstmt.close();
//			} catch (SQLException e) {
//				System.out.println("Error al cerrar la conexión: " + e.getMessage());
//			}
//		}
	}

	public static ArrayList<String> MostrarCategoria(Connection con) {
		ArrayList<String> nombresCategorias = new ArrayList<>();
		String query = "SELECT Nombre_Categoria FROM categoria";

		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String nombreCategoria = rs.getString("Nombre_Categoria");
				nombresCategorias.add(nombreCategoria);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nombresCategorias;
	}

	public static CategoriaDO getCategoria(Connection con, String nombre) {
		String query = "SELECT * FROM categoria WHERE Nombre_Categoria = ?";

		try {
			PreparedStatement pstmt = con.prepareStatement(query);

			pstmt.setString(1, nombre);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				CategoriaDO categoria = new CategoriaDO();
				categoria.setIdCategoria(rs.getInt("IdCategoria"));
				categoria.setNombreCategoria(rs.getString("Nombre_Categoria"));
				return categoria;
			}
			return null;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<CategoriaDO> getCategorias(Connection con, int idUsuario) {
		ArrayList<CategoriaDO> categorias = new ArrayList<>();

		String query = "SELECT * FROM categoria JOIN categoria_has_imagen ON categoria.idCategoria = categoria_has_imagen.Categoria_idCategoria WHERE categoria_has_imagen.Imagen_Usuario_idUsuario = ?";

		try {
			PreparedStatement pstmt = con.prepareStatement(query);

			pstmt.setInt(1, idUsuario);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CategoriaDO categoria = new CategoriaDO();
				categoria.setIdCategoria(rs.getInt("IdCategoria"));
				categoria.setNombreCategoria(rs.getString("Nombre_Categoria"));
				categorias.add(categoria);
				// System.out.println(categoria.getNombreCategoria());
			}

			return categorias;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static void cargarCategoriasEnChoiceBox(Connection con, ChoiceBox<String> choiceBox) {
		ArrayList<String> nombresCategorias = MostrarCategoria(con);
		if (nombresCategorias != null) {
			choiceBox.getItems().addAll(nombresCategorias);
		} else {
			System.out.println("Error al cargar las categorías desde la base de datos.");
		}
	}

}
