package application;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class PanelSubirImagen extends VBox {

	public TextArea txtDescripcionImg;
	public CheckBox cbxMarcar;
	public FileChooser escogerImagen;
	public Button btnEscogerImg;

	public PanelSubirImagen() {
		/******* ELEMENTOS *******/
		Label lblDescripcionImg = new Label("Descripción: ");

		txtDescripcionImg = new TextArea();
		txtDescripcionImg.setPrefHeight(50);

		cbxMarcar = new CheckBox("¿Quieres marcar esta imagen?");

		btnEscogerImg = new Button("Seleccionar Imagen");

		escogerImagen = new FileChooser();

		/******* ESTILOS *******/
		this.setMargin(lblDescripcionImg, new Insets(5, 10, 5, 10));
		this.setMargin(txtDescripcionImg, new Insets(5, 10, 5, 10));
		this.setMargin(cbxMarcar, new Insets(5, 10, 5, 10));
		this.setMargin(btnEscogerImg, new Insets(5, 10, 5, 10));

		/******* AGREGACIÓN AL PANEL *******/
		this.getChildren().addAll(lblDescripcionImg, txtDescripcionImg, cbxMarcar, btnEscogerImg);
	}
}
