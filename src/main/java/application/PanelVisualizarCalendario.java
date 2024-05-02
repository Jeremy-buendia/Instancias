package application;

import javafx.geometry.Pos;
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

		PanelLayoutBotones pnlBotones = new PanelLayoutBotones();

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

		this.setAlignment(vistaCalendario, Pos.TOP_LEFT);
		this.setAlignment(pnlBotones, Pos.TOP_LEFT);

		this.getChildren().addAll(vistaCalendario, pnlBotones);
	}
}
