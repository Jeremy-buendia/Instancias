package application.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
			System.out.println("Error al añadir la categoría a la imagen: " + e.getMessage());
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
				System.out.println(categoria.getNombreCategoria());
			}

			return categorias;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * para añadir la categoria a la imagen una vez que tengamos el menu, segun
	 * chatgpt me lo guardo para revisarlo mañana
	 * 
	 * import javafx.application.Application; import javafx.geometry.Insets; import
	 * javafx.scene.Scene; import javafx.scene.control.*; import
	 * javafx.scene.layout.*; import javafx.stage.Stage;
	 * 
	 * public class Main extends Application {
	 * 
	 * private CategoriaImagenDAO dao; // Instancia del DAO para manejar la base de
	 * datos
	 * 
	 * @Override public void start(Stage primaryStage) { dao = new
	 * CategoriaImagenDAO(); // Inicializar el DAO
	 * 
	 * // Crear los controles de la interfaz de usuario ComboBox<Categoria>
	 * categoriaComboBox = new ComboBox<>(); TextField imagenTextField = new
	 * TextField(); Button agregarCategoriaButton = new
	 * Button("Agregar Categoría a la Imagen");
	 * 
	 * // Llenar el ComboBox con las categorías existentes // Esto requeriría una
	 * función en el DAO para obtener las categorías de la base de datos //
	 * Supongamos que tienes una función getCategorias() en el DAO que devuelve una
	 * lista de Categorias categoriaComboBox.getItems().addAll(dao.getCategorias());
	 * 
	 * // Evento para manejar el clic en el botón "Agregar Categoría a la Imagen"
	 * agregarCategoriaButton.setOnAction(event -> { Categoria categoriaSeleccionada
	 * = categoriaComboBox.getValue(); String ubicacionImagen =
	 * imagenTextField.getText();
	 * 
	 * // Verificar si se seleccionó una categoría y se ingresó una ubicación de
	 * imagen if (categoriaSeleccionada != null && !ubicacionImagen.isEmpty()) { //
	 * Obtener el ID de la categoría y la ubicación de la imagen int idCategoria =
	 * categoriaSeleccionada.getId(); int idImagen =
	 * dao.guardarImagen(ubicacionImagen); // Supongamos que guardas la imagen en la
	 * base de datos y obtienes su ID int idUsuario = 1; // Supongamos que tienes un
	 * usuario con ID 1
	 * 
	 * // Llamar a la función del DAO para agregar la categoría a la imagen
	 * dao.agregarCategoriaAImagen(idCategoria, idImagen, idUsuario);
	 * System.out.println("Categoría añadida a la imagen correctamente."); } else {
	 * System.out.
	 * println("Por favor, seleccione una categoría y proporcione una ubicación de imagen."
	 * ); } });
	 * 
	 * // Crear el diseño de la interfaz de usuario VBox root = new VBox(10);
	 * root.setPadding(new Insets(10)); root.getChildren().addAll( new
	 * Label("Seleccione una categoría:"), categoriaComboBox, new
	 * Label("Ubicación de la imagen:"), imagenTextField, agregarCategoriaButton );
	 * 
	 * // Configurar la escena y mostrar la ventana Scene scene = new Scene(root,
	 * 300, 200); primaryStage.setScene(scene);
	 * primaryStage.setTitle("Agregar Categoría a Imagen"); primaryStage.show(); }
	 * 
	 * public static void main(String[] args) { launch(args); } }
	 * 
	 * 
	 */

}
