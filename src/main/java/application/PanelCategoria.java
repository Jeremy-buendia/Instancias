package application;

import java.sql.Connection;

import application.utils.UtilsBD;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;

public class PanelCategoria extends VBox {

	ChoiceBox chbCategorias;
	Button btnCrearCat;
	Button btnAsignar;

	PanelCategoria() {
		Connection con = UtilsBD.conectarBD();

		chbCategorias = new ChoiceBox();
		btnCrearCat = new Button("Crear Categoria");
		btnAsignar = new Button("Asignar");

		this.getChildren().addAll(chbCategorias, btnCrearCat, btnAsignar);
	}
}
