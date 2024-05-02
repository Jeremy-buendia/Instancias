package application;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.Connection;

import application.model.UsuarioDAO;
import application.model.UsuarioDO;
import application.utils.UtilsBD;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



public class RegistroController {

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
            // Llamar a una función para hacer algo con el usuario, como guardarlo en una base de datos
   UsuarioDAO.crearUsuario(con, usuario);
         
        } else {
            // Las contraseñas no coinciden, mostrar un mensaje de error
            System.out.println("Error Las contraseñas no coinciden");
        }
    }
    @FXML
    void volverAIniciarSesion(ActionEvent event) throws IOException {
        // Cargar la página de inicio de sesión (IniciarSesion.fxml)
        Parent inicioSesionParent = FXMLLoader.load(getClass().getResource("IniciarSesion.fxml"));
        Scene inicioSesionScene = new Scene(inicioSesionParent);

        // Obtener la etapa actual y establecer la nueva escena
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(inicioSesionScene);
        stage.show();
    }
}

