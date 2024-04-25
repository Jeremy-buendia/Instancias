package application;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

public class PanelSubirImagen extends GridPane {

	public TextField txtNombreImg;
	public TextField txtUbicacion;
	public ChoiceBox chbMarcar;
	public FileChooser escogerImagen;

	public PanelSubirImagen() {
		Label lblNombreImg = new Label("Descripción: ");
		Label lblUbicacion = new Label("Ubicación: ");
		Label lblMarcado = new Label("¿Quieres marcar esta imagen?");

		txtNombreImg = new TextField();
		txtUbicacion = new TextField();
		chbMarcar = new ChoiceBox();
	}
}
