package application;
	import javafx.application.Application;
	import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
	import javafx.stage.Stage;
	import java.io.IOException;
import java.sql.Connection;

	public class PanelFormulario extends GridPane {

	

		    public void start(Stage primaryStage) throws Exception {
		        // Cargar el archivo FXML
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("IniciarSesion.fxml"));
		        Parent root = loader.load();

		        // Crear la escena
		        Scene scene = new Scene(root, 600, 400);

		        // Configurar la escena en el escenario (stage)
		        primaryStage.setScene(scene);
		        primaryStage.setTitle("Login");
		        primaryStage.show();
		    }
		    
	}

	

