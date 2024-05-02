package application.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import application.PanelFormularioProv;
import application.utils.UtilsBD;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;

public class CalendarioDAO {

	public static boolean calendarioInsertado = false;

	// Método para buscar una fecha usando un DatePicker
	public static LocalDate buscarFecha() {
		// Crear un nuevo DatePicker
		DatePicker datePicker = new DatePicker();

		// Crear un nuevo diálogo
		Dialog<LocalDate> dialog = new Dialog<>();
		// Establecer el título del diálogo
		dialog.setTitle("Seleccionar Fecha");
		// Añadir el DatePicker al diálogo
		dialog.getDialogPane().setContent(datePicker);

		// Crear un nuevo botón de tipo OK
		ButtonType buttonTypeOk = new ButtonType("Confirmar", ButtonData.OK_DONE);
		// Añadir el botón al diálogo
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

		// Establecer la acción que se realizará cuando se presione el botón OK
		dialog.setResultConverter(b -> b == buttonTypeOk ? datePicker.getValue() : null);

		// Mostrar el diálogo y esperar a que el usuario seleccione una fecha
		Optional<LocalDate> fecha = dialog.showAndWait();

		// Devolver la fecha seleccionada, o null si no se seleccionó ninguna fecha
		return fecha.orElse(null);
	}

	// Método para buscar datos en la base de datos que correspondan a una fecha
	// específica
	public static ResultSet buscarDatos(LocalDate fecha) {
		Connection con = UtilsBD.conectarBD();
		ResultSet rs = null;
		try {

			// Crear una consulta SQL para buscar datos que correspondan a la fecha
			// seleccionada
			String query = "SELECT * FROM calendario WHERE Fecha = ?";
			PreparedStatement pstmt = con.prepareStatement(query);
			// Establecer el valor del parámetro en la consulta SQL
			pstmt.setDate(1, Date.valueOf(fecha));
			// Ejecutar la consulta y obtener los resultados
			rs = pstmt.executeQuery();
		} catch (Exception e) {
			// Imprimir cualquier error que ocurra
			System.out.println(e);
		}

		// Devolver los resultados de la consulta
		return rs;
	}

	// FUNCION QUE DEVUELVE UN ARRAY CON LAS RUTAS DE LAS IMAGENES DE LOS
	// CALENDARIOS

	public static ArrayList<String> getCalendario(Connection con) {

		if (!calendarioInsertado) {
			String query = "INSERT INTO calendario (Fecha, Imagen_Calendario, Usuario_idUsuario) VALUES(?, ?, ?)";

			try {
				// Creamos un PreparedStatement
				PreparedStatement pstmt = con.prepareStatement(query);
				// Asignamos los valores a los ?
				pstmt.setDate(1, Date.valueOf("2024-04-01"));
				pstmt.setString(2, "img\\2024-04.jpg");
				pstmt.setInt(3, UsuarioDAO.cargarId(con, PanelFormularioProv.correoUsuario).getId());

				pstmt.executeUpdate();
				calendarioInsertado = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				// Creamos un PreparedStatement
				PreparedStatement pstmt = con.prepareStatement(query);
				// Asignamos los valores a los ?
				pstmt.setDate(1, Date.valueOf("2024-05-01"));
				pstmt.setString(2, "img\\2024-05.jpg");
				pstmt.setInt(3, UsuarioDAO.cargarId(con, PanelFormularioProv.correoUsuario).getId());

				pstmt.executeUpdate();
				calendarioInsertado = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				// Creamos un PreparedStatement
				PreparedStatement pstmt = con.prepareStatement(query);
				// Asignamos los valores a los ?
				pstmt.setDate(1, Date.valueOf("2024-06-01"));
				pstmt.setString(2, "img\\2024-06.jpg");
				pstmt.setInt(3, UsuarioDAO.cargarId(con, PanelFormularioProv.correoUsuario).getId());

				pstmt.executeUpdate();
				calendarioInsertado = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		ArrayList<String> rutasCarpeta = new ArrayList<>();
		String query = "SELECT * FROM calendario WHERE Usuario_IdUsuario = ?";
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setInt(1, UsuarioDAO.cargarId(con, PanelFormularioProv.correoUsuario).getId());

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				rutasCarpeta.add(rs.getString("Imagen_Calendario"));
			}
			return rutasCarpeta;
		} catch (Exception e) {
			return null;
		}
	}
}
