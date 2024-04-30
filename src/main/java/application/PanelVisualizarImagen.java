package application;

import java.io.FileInputStream;
import java.sql.Connection;

import application.model.UsuarioDAO;
import application.utils.UtilsBD;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class PanelVisualizarImagen extends VBox {

	private ImageView vistaImg;
	Button anterior;
	Button siguiente;
	int idFoto;

	PanelVisualizarImagen() {
		Connection con = UtilsBD.conectarBD();

		Image imagen;

		idFoto = 1;

		try {
			imagen = new Image(new FileInputStream(System.getProperty("user.home") + "\\Pictures\\Instancias\\"
					+ UsuarioDAO.cargarId(con, PanelFormularioProv.correoUsuario).getId() + "\\" + idFoto + ".jpg"));

			// System.out.println(System.getProperty("user.home") +
			// "\\Pictures\\Instancias\\"
			// + UsuarioDAO.cargarId(con, PanelFormularioProv.correoUsuario).getId() + "\\"
			// + 1 + ".jpg");

			vistaImg = new ImageView(imagen);

			vistaImg.setPreserveRatio(true);
			// Cambiamos ancho de la imagen
			vistaImg.setFitWidth(200);

			anterior = new Button("<--");
			siguiente = new Button("-->");
			this.getChildren().addAll(vistaImg, anterior, siguiente);

			anterior.setOnAction(e -> {
				if (idFoto > 1) {
					idFoto -= 1;
					try {
						Image imagenAnterior = new Image(
								new FileInputStream(System.getProperty("user.home") + "\\Pictures\\Instancias\\"
										+ UsuarioDAO.cargarId(con, PanelFormularioProv.correoUsuario).getId() + "\\"
										+ idFoto + ".jpg"));
						vistaImg.setImage(imagenAnterior);

						vistaImg.setPreserveRatio(true);
						// Cambiamos ancho de la imagen
						vistaImg.setFitWidth(200);

						this.getChildren().add(vistaImg);
					} catch (Exception e2) {
						// TODO: handle exception
					}

				}
			});

			siguiente.setOnAction(e -> {
				idFoto += 1;
				try {
					Image imagenSiguiente = new Image(
							new FileInputStream(System.getProperty("user.home") + "\\Pictures\\Instancias\\"
									+ UsuarioDAO.cargarId(con, PanelFormularioProv.correoUsuario).getId() + "\\"
									+ idFoto + ".jpg"));
					vistaImg.setImage(imagenSiguiente);

					vistaImg.setPreserveRatio(true);
					// Cambiamos ancho de la imagen
					vistaImg.setFitWidth(200);
					this.getChildren().add(vistaImg);
				} catch (Exception e2) {
					// TODO: handle exception
				}

			});
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
