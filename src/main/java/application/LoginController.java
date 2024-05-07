package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;

import application.model.ImagenDAO;
import application.model.NotificacionesDAO;
import application.model.UsuarioDAO;
import application.utils.UtilsBD;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginController {

	public static String correoUsuario;

	public static boolean cargarCalendario;

	@FXML
	public TextField ICorreo;

	@FXML
	public PasswordField Icontrasena;

	@FXML
	public Button BttnIniciar;

	private Stage ventanaActual;

	private Scene registerScene;

	/**
	 * Método para establecer la referencia a la ventana
	 * 
	 * @param ventanaActual
	 */
	public void setVentanaActual(Stage ventanaActual) {
		this.ventanaActual = ventanaActual;
	}

	@FXML
	public void enviarFormulario(ActionEvent event) {
		// Obtener los valores del correo y la contraseña introducidos por el usuario
		String correoText = ICorreo.getText();
		String contrasenaText = Icontrasena.getText();
		Connection con = UtilsBD.conectarBD();

		if (UsuarioDAO.cargarCorreo(con, ICorreo.getText()) != null) {
			correoUsuario = ICorreo.getText();
			NotificacionesDAO.insertarNotificaciones(con);
			System.out.println(NotificacionesDAO.getNotificaciones(con, 1));
			// Verificar la contraseña
			boolean contrasenaCorrecta = UsuarioDAO.verificarContrasena(con, correoText, contrasenaText);

			// Si la contraseña es correcta, abrir la siguiente ventana (puedes cambiar esto
			// según tu lógica)
			if (contrasenaCorrecta) {
				System.out.println("Contraseña correcta. Abriendo siguiente ventana...");
				NotificacionesDAO.mostrarNotificacion(NotificacionesDAO.getNotificaciones(con, 1).getMensaje());
				cargarCalendario = true;

				if (ImagenDAO.BuscarCarpetaSinImg(con) == 0) {
					String ruta = System.getProperty("user.home") + "\\Pictures\\Instancias\\"
							+ UsuarioDAO.cargarCorreo(con, LoginController.correoUsuario).getId();
					File txt = crearTxt(ruta);
					escritor(txt);

				} else {
					String rutaCarpeta = System.getProperty("user.home") + "\\Pictures\\Instancias\\"
							+ UsuarioDAO.cargarCorreo(con, LoginController.correoUsuario).getId();
					File carpeta = new File(rutaCarpeta);
					carpeta.mkdirs();
					String ruta = System.getProperty("user.home") + "\\Pictures\\Instancias\\"
							+ UsuarioDAO.cargarCorreo(con, LoginController.correoUsuario).getId();
					File txt = crearTxt(ruta);
					escritor(txt);

				}

				if (ventanaActual != null) {
					ventanaActual.close();
				}
				// Aquí puedes abrir la siguiente ventana
			} else {
				// Si la contraseña es incorrecta, mostrar un mensaje de error
				Stage stage = new Stage();
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setResizable(false);

				Label label = new Label("Inicio de sesión incorrecto");
				label.setStyle("-fx-font-size: 14;");
				VBox root = new VBox(label);
				root.setAlignment(Pos.CENTER);
				root.setSpacing(10);

				Scene scene = new Scene(root, 300, 100);
				stage.setScene(scene);
				stage.setTitle("Notificación");

				// Configurar el temporizador para cerrar la ventana después de 5 segundos
				PauseTransition delay = new PauseTransition(Duration.seconds(1));
				delay.setOnFinished(e -> stage.close());
				delay.play();

				stage.show();
				System.out.println("Contraseña incorrecta. Por favor, inténtelo de nuevo.");
			}
		} else {
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(false);

			Label label = new Label("Correo incorrecto");
			label.setStyle("-fx-font-size: 14;");
			VBox root = new VBox(label);
			root.setAlignment(Pos.CENTER);
			root.setSpacing(10);

			Scene scene = new Scene(root, 300, 100);
			stage.setScene(scene);
			stage.setTitle("Notificación");

			// Configurar el temporizador para cerrar la ventana después de 5 segundos
			PauseTransition delay = new PauseTransition(Duration.seconds(1));
			delay.setOnFinished(e -> stage.close());
			delay.play();

			stage.show();
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

	public static File crearTxt(String ruta) {
		Connection con = UtilsBD.conectarBD();
		ruta += "\\cookie.txt";
		File txt = new File(ruta);
		return txt;
	}

	public static void escritor(File txt) {
		Connection con = UtilsBD.conectarBD();

		try {
			FileWriter escritor = new FileWriter(txt);
			BufferedWriter bw = new BufferedWriter(escritor);

			String lineas[] = { LoginController.correoUsuario, Integer.toString(
					ImagenDAO.getMaxImg(con, UsuarioDAO.cargarCorreo(con, LoginController.correoUsuario).getId())) };

			for (String linea : lineas) {
				bw.write(linea);
				bw.newLine();
			}

			bw.close();
			escritor.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
