package application;

import java.io.IOException;
import java.sql.Connection;

import application.model.CategoriaDAO;
import application.model.NotificacionesDAO;
import application.model.UsuarioDAO;
import application.utils.UtilsBD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

	public static String correoUsuario;

	public static boolean cargarCalendario;

	@FXML
	public TextField ICorreo;

	@FXML
	public PasswordField IContraseña;

	@FXML
	public Button BttnIniciar;

	private Stage ventanaActual;

	private Scene registerScene;

	// Método para establecer la referencia a la ventana
	public void setVentanaActual(Stage ventanaActual) {
		this.ventanaActual = ventanaActual;
	}

	@FXML
	public void enviarFormulario(ActionEvent event) {
		// Obtener los valores del correo y la contraseña introducidos por el usuario
		String correoText = ICorreo.getText();
		String contraseñaText = IContraseña.getText();
		Connection con = UtilsBD.conectarBD();
		// Conectar a la base de datos

		correoUsuario = ICorreo.getText();
		NotificacionesDAO.mostrarNotificacion("Esto es de prueba");
		NotificacionesDAO.insertarNotificaciones(con);
		// Verificar la contraseña
		boolean contraseñaCorrecta = UsuarioDAO.verificarContraseña(con, correoText, contraseñaText);

		// Si la contraseña es correcta, abrir la siguiente ventana (puedes cambiar esto
		// según tu lógica)
		if (contraseñaCorrecta) {
			System.out.println("Contraseña correcta. Abriendo siguiente ventana...");
			cargarCalendario = true;
			if (ventanaActual != null) {
				ventanaActual.close();
			}
			// Aquí puedes abrir la siguiente ventana
		} else {
			// Si la contraseña es incorrecta, mostrar un mensaje de error
			System.out.println("Contraseña incorrecta. Por favor, inténtelo de nuevo.");
		}

	}

//	public void setEnviarAction(EventHandler<ActionEvent> eventHandler) {
//		BttnIniciar.setOnAction(eventHandler);
//	}

	@FXML
	void abrirPaginaRegistrarse(ActionEvent event) throws IOException {
		// Cargar la nueva página de registro (Registrarse.fxml)
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/javafx_fxml/Registrarse.fxml"));
		Parent paginaRegistrarseParent = loader.load();

		// Obtener el controlador de registro y establecer la referencia de loginScene
		RegistroController registroController = loader.getController();
		registroController.setLoginScene(registerScene);

		// Obtener la etapa actual y establecer la nueva escena
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(new Scene(paginaRegistrarseParent));
		stage.show();
	}

	public void setRegisterScene(Scene registerScene) {
		this.registerScene = registerScene;
	}

	public void onCloseRequest(ActionEvent event) {
		if (ventanaActual != null) {
			ventanaActual.close();
		}
	}
}
