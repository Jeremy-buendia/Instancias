package application;

import java.io.IOException;
import java.sql.Connection;

import application.model.UsuarioDAO;
import application.model.UsuarioDO;
import application.utils.UtilsBD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegistroController {

	Scene scene;

	Stage loginStage;

	@FXML
	private PasswordField CcConfCont;

	@FXML
	private PasswordField CcContraseña;

	@FXML
	private Button BttnCcConfi;

	@FXML
	private TextField CcNombre;

	@FXML
	private TextField CcCorreo;

	@FXML
	private TextField CcApellido;

	@FXML
	private CheckBox Terminos;

	@FXML
	private Button BttnCcVolver;

	private Scene loginScene;
	
	public void setLoginScene(Scene loginScene) {
		this.loginScene = loginScene;
	}

	@FXML
	void enviarFormulario(ActionEvent event) {
	    String nombreText = CcNombre.getText();
	    String apellidoText = CcApellido.getText();
	    String correoText = CcCorreo.getText();
	    String contraseñaText = CcContraseña.getText();
	    String ConfContraseñaText = CcConfCont.getText();

	    // Verificar si las contraseñas coinciden
	    if (contraseñaText.equals(ConfContraseñaText)) {
	        // Las contraseñas coinciden, hacer algo con los datos
	        // Por ejemplo, puedes guardar los datos en un objeto UsuarioDO
	        UsuarioDO usuario = new UsuarioDO();
	        usuario.setNombre(nombreText);
	        usuario.setApellido(apellidoText);
	        usuario.setCorreo(correoText);
	        usuario.setContraseña(contraseñaText);

	        Connection con = UtilsBD.conectarBD();
	        // Llamar a una función para hacer algo con el usuario, como guardarlo en una
	        // base de datos
	        UsuarioDAO.crearUsuario(con, usuario);

	  
	

	        // Cerrar la ventana actual de registro
	        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
	        
	        // Mostrar la escena de inicio de sesión anterior si está disponible
	        if (loginScene != null) {
	            stage.setScene(loginScene);
	            stage.show();
	        } else {
	            System.out.println("Error: No se puede volver a la ventana de inicio de sesión.");
	        }
	    } else {
	        // Las contraseñas no coinciden, mostrar un mensaje de error
	        System.out.println("Error Las contraseñas no coinciden");
	    }
	}

	@FXML
	void volverAIniciarSesion(ActionEvent event) throws IOException {
		// Obtener el Stage antes de realizar cualquier acción
	    Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
	    
	    // Mostrar la escena de inicio de sesión anterior si está disponible
	    if (loginScene != null) {
	        stage.setScene(loginScene);
	        stage.show();
	    } else {
	        System.out.println("Error: No se puede volver a la ventana de inicio de sesión.");
	    }
		
	}


}
