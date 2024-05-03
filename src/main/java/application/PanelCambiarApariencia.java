package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class PanelCambiarApariencia extends Application {
	// Declara 'root' como una variable de instancia
	private StackPane root;

	@Override
	public void start(Stage primaryStage) throws Exception {
		root = new StackPane();
		Scene scene = new Scene(root, 650, 650);
		root.setStyle("-fx-background-color: #FFFFFF;"); 

		// Resto de tu código...
		MenuItem iModo = new MenuItem("Modo");
		iModo.setOnAction(event -> {
			int estado = ((CheckMenuItem) iModo).isSelected() ? 1 : 0;
			if (estado == 1) {
				root.setStyle("-fx-background-color: #000000;"); 
			} else {
				root.setStyle("-fx-background-color: #ffffff;");			}
		});

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// Resto de tu código...
}

