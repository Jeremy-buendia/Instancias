package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;

import application.utils.UtilsBD;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class PanelVisualizarImagen extends BorderPane {
	ImageView anteriorImgView;
	ImageView siguienteImgView;
	ImageView vistaImg;

	Button anterior;
	Button siguiente;
	Button descargar;
	Button marcar;
	Button categoria;

	Image imagen;
	Image anteriorImg;
	Image siguienteImg;

	// int idFoto;

	/**
	 * Constructor del PanelVisualizarImagen
	 */
	PanelVisualizarImagen() {
		Connection con = UtilsBD.conectarBD();

		// idFoto = 1;

		vistaImg = new ImageView();

		vistaImg.setPreserveRatio(true);
//		// Cambiamos ancho de la imagen
//		vistaImg.setFitWidth(300);

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
		marcar = new Button("Marcar");

		ToolBar menuSuperior = new ToolBar();

		menuSuperior.getItems().addAll(descargar, marcar, categoria);

		ToolBar menuInferior = new ToolBar();

		menuInferior.getItems().addAll(anterior, siguiente);

		// this.getChildren().addAll(descargar, categoria, vistaImg, anterior,
		// siguiente);

		this.setTop(menuSuperior);
		this.setBottom(menuInferior);

		this.setCenter(vistaImg);

		// this.maxWidth(300);

//		this.setMargin(siguiente, new Insets(0, 0, 0, 90));
//		this.setMargin(categoria, new Insets(0, 0, 0, 30));
	}
}
