package application;

import java.io.File;
import java.sql.Connection;

import application.model.ImagenDAO;
import application.model.ImagenDO;
import application.model.UsuarioDAO;
import application.utils.UtilsBD;
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
		Connection con = UtilsBD.conectarBD();

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

		/******* EVENTO AL PULSAR EL BOTÓN btnEscogerImg *******/
		btnEscogerImg.setOnAction(e -> {
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.png", "*.jpg", "*.jpeg");
			escogerImagen.getExtensionFilters().add(extFilter);
			escogerImagen.setTitle("Selecciona una imagen");

			File imagen = escogerImagen.showOpenDialog(null);

			int marcado;

			if (cbxMarcar.isSelected()) {
				marcado = 1;
			} else {
				marcado = 0;
			}

			ImagenDO objImagen;
			objImagen = new ImagenDO(-1, txtDescripcionImg.getText(), "", "",
					UsuarioDAO.cargarId(con, PanelFormularioProv.correoUsuario).getId(), marcado);
			ImagenDAO.subirImagen(con, objImagen);
			ImagenDAO.copiarImagen(imagen, objImagen);
		});
	}
}
