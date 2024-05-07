package application;

import java.sql.Connection;

import application.model.CategoriaDAO;
import application.utils.UtilsBD;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;

public class PanelCategoria extends VBox {

	ChoiceBox<String> chbCategorias;
	Button btnCrearCat;
	Button btnAsignar;

	/**
	 * Constructor de PanelCategoria
	 */
	PanelCategoria() {
		Connection con = UtilsBD.conectarBD();

		chbCategorias = new ChoiceBox();
		CategoriaDAO.cargarCategoriasEnChoiceBox(con, chbCategorias);
		btnCrearCat = new Button("Crear Categoria");
		btnAsignar = new Button("Asignar");

		this.getChildren().addAll(chbCategorias, btnCrearCat, btnAsignar);
	}
}
