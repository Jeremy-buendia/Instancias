package application;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class PanelVisualizarCalendario extends StackPane {

	ImageView vistaCalendario;
	Image imagen;
	Button dia1;

	PanelVisualizarCalendario() {
		vistaCalendario = new ImageView();

		vistaCalendario.setPreserveRatio(true);
		// Cambiamos ancho de la imagen
		vistaCalendario.setFitWidth(800);

		dia1 = new Button("");

		dia1.setPrefSize(100, 50);

		dia1.setStyle("-fx-background-color: transparent;");

		dia1.setOnMouseEntered(e -> {
			dia1.setStyle("-fx-background-color: lightblue;");
		});

		dia1.setOnMouseExited(e -> {
			dia1.setStyle("-fx-background-color: transparent;");
		});

		dia1.setOnAction(e -> {
			System.out.println("illo");
		});

		this.getChildren().addAll(vistaCalendario, dia1);
	}
}
