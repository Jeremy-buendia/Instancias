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

	public class PanelRegistro extends GridPane {

	

		    public void start(Stage primaryStage) throws Exception {
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("Registro.fxml"));
		        Parent root = loader.load();

		        // Obtener el controlador del archivo FXML
		        RegistroController controller = loader.getController();

		        // Configurar la escena
		        Scene scene = new Scene(root, 800, 600);

		        // Configurar el escenario principal
		        primaryStage.setTitle("Registro");
		        primaryStage.setScene(scene);
		        primaryStage.show();
		    }
		    
	}

	

