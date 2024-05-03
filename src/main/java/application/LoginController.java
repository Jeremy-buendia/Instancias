package application;

import java.io.IOException;
import java.sql.Connection;

import application.model.UsuarioDAO;
import application.utils.UtilsBD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

		// Verificar la contraseña
		boolean contraseñaCorrecta = UsuarioDAO.verificarContraseña(con, correoText, contraseñaText);

		cargarCalendario = true;

		// Si la contraseña es correcta, abrir la siguiente ventana (puedes cambiar esto
		// según tu lógica)
		if (contraseñaCorrecta) {
			System.out.println("Contraseña correcta. Abriendo siguiente ventana...");
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
		// Cargar la nueva página (por ejemplo, Registrarse.fxml)
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Registrarse.fxml"));
		Parent paginaRegistrarseParent = loader.load();
		Scene paginaRegistrarseScene = new Scene(paginaRegistrarseParent);

		// Obtener la etapa actual y establecer la nueva escena
		Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
		stage.setScene(paginaRegistrarseScene);
		stage.show();
	}

	public void setRegisterScene(Scene registerScene) {
		this.registerScene = registerScene;
	}
}
