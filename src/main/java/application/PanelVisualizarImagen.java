package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;

import application.utils.UtilsBD;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class PanelVisualizarImagen extends VBox {
	ImageView anteriorImgView;
	ImageView siguienteImgView;
	ImageView vistaImg;

	Button anterior;
	Button siguiente;
	Button descargar;
	Button categoria;

	Image imagen;
	Image anteriorImg;
	Image siguienteImg;

	int idFoto;

	PanelVisualizarImagen() {
		Connection con = UtilsBD.conectarBD();

		idFoto = 1;

		vistaImg = new ImageView();

		vistaImg.setPreserveRatio(true);
		// Cambiamos ancho de la imagen
		vistaImg.setFitWidth(200);

		try {
			anteriorImg = new Image(new FileInputStream("img/izq.png"));
			siguienteImg = new Image(new FileInputStream("img/der.png"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		anteriorImgView = new ImageView(anteriorImg);
		anteriorImgView.setPreserveRatio(true);
		anteriorImgView.setFitWidth(30);

		siguienteImgView = new ImageView(siguienteImg);
		siguienteImgView.setPreserveRatio(true);
		siguienteImgView.setFitWidth(30);

		anterior = new Button("");
		anterior.setGraphic(anteriorImgView);

		siguiente = new Button("");
		siguiente.setGraphic(siguienteImgView);

		descargar = new Button("Descargar");
		categoria = new Button("Asignar Categoria");

		this.getChildren().addAll(descargar, categoria, vistaImg, anterior, siguiente);
	}
}
