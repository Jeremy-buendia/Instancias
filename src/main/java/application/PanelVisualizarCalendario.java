package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class PanelVisualizarCalendario extends VBox {

	ImageView vistaCalendario;
	Image imagen;

	PanelVisualizarCalendario() {
		vistaCalendario = new ImageView();

		vistaCalendario.setPreserveRatio(true);
		// Cambiamos ancho de la imagen
		vistaCalendario.setFitWidth(800);

		this.getChildren().add(vistaCalendario);
	}
}
