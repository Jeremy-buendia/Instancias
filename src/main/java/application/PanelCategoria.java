package application;

import java.sql.Connection;
import java.util.ArrayList;

import application.model.CategoriaDO;
import application.utils.UtilsBD;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;

public class PanelCategoria extends VBox {

	ChoiceBox chbCategorias;
	Button btnCrearCat;
	ArrayList<CategoriaDO> categorias;

	PanelCategoria() {
		categorias = new ArrayList<>();
		Connection con = UtilsBD.conectarBD();

		chbCategorias = new ChoiceBox();
		btnCrearCat = new Button("Crear Categoria");

		this.getChildren().addAll(chbCategorias, btnCrearCat);
	}
}
