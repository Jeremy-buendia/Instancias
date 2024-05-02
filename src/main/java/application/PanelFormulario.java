package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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
