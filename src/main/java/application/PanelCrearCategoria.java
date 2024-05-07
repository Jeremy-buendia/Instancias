package application;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class PanelCrearCategoria extends VBox {

	TextField nombreCategoria;
	Button crear;

	/**
	 * Constructor de PanelCrearCategoria
	 */
	PanelCrearCategoria() {
		nombreCategoria = new TextField();
		crear = new Button("Crear");

		this.getChildren().addAll(nombreCategoria, crear);
	}
}
