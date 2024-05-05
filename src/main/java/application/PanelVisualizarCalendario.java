package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;

import application.model.CategoriaDAO;
import application.model.CategoriaDO;
import application.model.ImagenDAO;
import application.model.UsuarioDAO;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class PanelVisualizarCalendario extends StackPane {

	ImageView vistaCalendario;
	Image imagen;
	Button dia1;

	PanelVisualizarCalendario(Stage stage, Connection con, String mes) {
		vistaCalendario = new ImageView();

		vistaCalendario.setPreserveRatio(true);
		// Cambiamos ancho de la imagen
		vistaCalendario.setFitWidth(800);

		PanelLayoutBotones pnlBotones = new PanelLayoutBotones(stage, con, mes);

//		String mesCald = pnlBotones.dia.getText().substring(5, 6);
//		int mesCaldInt = Integer.parseInt(mesCald);
//
//		if(Integer.parseInt(pnlBotones.dia.getText().substring(6)) < 10) {
//			String diaCald = pnlBotones.dia.getText().substring();
//		}
//
//		String diaCald = pnlBotones.dia.getText().substring(7);
//		int diaCaldInt = Integer.parseInt(diaCald);

		// LocalDate diaMes = LocalDate.of(2024, mesCaldInt, diaCaldInt);

		this.setAlignment(vistaCalendario, Pos.TOP_LEFT);
		this.setAlignment(pnlBotones, Pos.TOP_LEFT);

		this.getChildren().addAll(vistaCalendario, pnlBotones);
	}

	public void abrirVentanaVisualizarImg(Stage stage, Connection con, LocalDate fechaDia) {
		Stage ventanaEmergente = new Stage();
		PanelVisualizarImagen pnlVisualizarImg = new PanelVisualizarImagen();

		int idFoto[] = { 1 };

		System.out.println(fechaDia);

		ArrayList<String> rutasCarpeta = ImagenDAO.getDia(con, fechaDia);

		try {
			pnlVisualizarImg.imagen = new Image(new FileInputStream(rutasCarpeta.get(0)));
			pnlVisualizarImg.vistaImg.setImage(pnlVisualizarImg.imagen);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		if (ImagenDAO.estaMarcado(con, rutasCarpeta.get(idFoto[0] - 1)) == 1) {
			pnlVisualizarImg.marcar.setText("Desmarcar");
		} else {
			pnlVisualizarImg.marcar.setText("marcar");
		}

		double aspecto = pnlVisualizarImg.imagen.getWidth() / pnlVisualizarImg.imagen.getHeight();
		Scene scene = new Scene(pnlVisualizarImg, 300, 300 / aspecto + 100);

		if (aspecto <= 1) {
			ventanaEmergente.setHeight(420);
			ventanaEmergente.setWidth(300);
			pnlVisualizarImg.vistaImg.setFitHeight(300);
		} else {
			pnlVisualizarImg.vistaImg.setFitWidth(300);
		}

		pnlVisualizarImg.anterior.setOnAction(e -> {
			if (idFoto[0] > 1) {
				idFoto[0] -= 1;

				if (ImagenDAO.estaMarcado(con, rutasCarpeta.get(idFoto[0] - 1)) == 1) {
					pnlVisualizarImg.marcar.setText("Desmarcar");
				} else {
					pnlVisualizarImg.marcar.setText("Marcar");
				}

				try {
					pnlVisualizarImg.imagen = new Image(new FileInputStream(rutasCarpeta.get(idFoto[0] - 1)));
					pnlVisualizarImg.vistaImg.setImage(pnlVisualizarImg.imagen);

					double aspectoImgAnt = pnlVisualizarImg.imagen.getWidth() / pnlVisualizarImg.imagen.getHeight();

					pnlVisualizarImg.setCenter(pnlVisualizarImg.vistaImg);

					if (aspectoImgAnt < 1) {
						ventanaEmergente.setHeight(420);
						ventanaEmergente.setWidth(300);
						pnlVisualizarImg.vistaImg.setFitHeight(300);
					} else {
						ventanaEmergente.setHeight(300 / aspectoImgAnt + 120);
						pnlVisualizarImg.vistaImg.setFitWidth(300);
					}

					pnlVisualizarImg.getChildren().add(pnlVisualizarImg.vistaImg);
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}
		});

		pnlVisualizarImg.siguiente.setOnAction(e -> {
			if (idFoto[0] < rutasCarpeta.size()) {
				idFoto[0] += 1;
			}

			if (ImagenDAO.estaMarcado(con, rutasCarpeta.get(idFoto[0] - 1)) == 1) {
				pnlVisualizarImg.marcar.setText("Desmarcar");
			} else {
				pnlVisualizarImg.marcar.setText("Marcar");
			}

			try {
				pnlVisualizarImg.imagen = new Image(new FileInputStream(rutasCarpeta.get(idFoto[0] - 1)));
				pnlVisualizarImg.vistaImg.setImage(pnlVisualizarImg.imagen);

				double aspectoImgSig = pnlVisualizarImg.imagen.getWidth() / pnlVisualizarImg.imagen.getHeight();

				pnlVisualizarImg.setCenter(pnlVisualizarImg.vistaImg);

				if (aspectoImgSig < 1) {
					ventanaEmergente.setHeight(420);
					ventanaEmergente.setWidth(300);
					pnlVisualizarImg.vistaImg.setFitHeight(300);
				} else {
					ventanaEmergente.setHeight(300 / aspectoImgSig + 120);
					pnlVisualizarImg.vistaImg.setFitWidth(300);
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}

		});

		pnlVisualizarImg.descargar.setOnAction(e -> {
			DirectoryChooser directorio = new DirectoryChooser();
			directorio.setTitle("Selecciona una carpeta");
			File directorioSeleccionado = directorio.showDialog(null);
			File imagen = new File(rutasCarpeta.get(idFoto[0] - 1));
			System.out.println(directorioSeleccionado);

			ImagenDAO.descargarImagen(imagen, directorioSeleccionado);
		});

		pnlVisualizarImg.marcar.setOnAction(e -> {
			if (ImagenDAO.estaMarcado(con, rutasCarpeta.get(idFoto[0] - 1)) == 1) {
				ImagenDAO.cambiarMarcado(con, rutasCarpeta.get(idFoto[0] - 1), 0);
				pnlVisualizarImg.marcar.setText("Marcar");
			} else {
				ImagenDAO.cambiarMarcado(con, rutasCarpeta.get(idFoto[0] - 1), 1);
				pnlVisualizarImg.marcar.setText("Desmarcar");
			}
		});

		pnlVisualizarImg.categoria.setOnAction(e -> {

			if (pnlVisualizarImg.imagen.getWidth() / pnlVisualizarImg.imagen.getHeight() <= 1) {
				ventanaEmergente.setHeight(420);
				ventanaEmergente.setWidth(300);
				pnlVisualizarImg.vistaImg.setFitHeight(300);
			} else {
				ventanaEmergente.setHeight(
						300 / pnlVisualizarImg.imagen.getWidth() / pnlVisualizarImg.imagen.getHeight() + 125);
				pnlVisualizarImg.vistaImg.setFitWidth(300);
			}

			System.out.println(rutasCarpeta.get(idFoto[0] - 1));
			ArrayList<CategoriaDO> categorias = new ArrayList<>();
			PanelCategoria pnlCategoria = new PanelCategoria();
			Scene escenaCat = new Scene(pnlCategoria, 300, 300);
			ventanaEmergente.setScene(escenaCat);

			categorias = CategoriaDAO.getCategorias(con,
					UsuarioDAO.cargarId(con, LoginController.correoUsuario).getId());

			pnlCategoria.chbCategorias.getItems().clear();

			for (int i = 0; i < categorias.size(); i++) {
				pnlCategoria.chbCategorias.getItems().add(categorias.get(i).getNombreCategoria());
			}

			pnlCategoria.btnAsignar.setOnAction(e2 -> {
				CategoriaDAO.agregarCategoriaAImagen(con,
						CategoriaDAO.getCategoria(con, (String) pnlCategoria.chbCategorias.getValue()).getIdCategoria(),
						ImagenDAO.getImagenPorRuta(con, rutasCarpeta.get(idFoto[0] - 1)).getIdImagen(),
						UsuarioDAO.cargarId(con, LoginController.correoUsuario).getId());
				ventanaEmergente.setScene(scene);
				if (pnlVisualizarImg.imagen.getWidth() / pnlVisualizarImg.imagen.getHeight() <= 1) {
					ventanaEmergente.setHeight(425);
					ventanaEmergente.setWidth(300);
					pnlVisualizarImg.vistaImg.setFitHeight(300);
				} else {
					ventanaEmergente.setHeight(405);
					pnlVisualizarImg.vistaImg.setFitWidth(300);
				}
			});

			pnlCategoria.btnCrearCat.setOnAction(e2 -> {
				PanelCrearCategoria pnlCrearCat = new PanelCrearCategoria();
				Scene escenaCrearCat = new Scene(pnlCrearCat, 300, 300);
				ventanaEmergente.setScene(escenaCrearCat);

				pnlCrearCat.crear.setOnAction(e3 -> {
					CategoriaDAO.crearCategoria(con, pnlCrearCat.nombreCategoria.getText());
					CategoriaDAO.agregarCategoriaAImagen(con,
							CategoriaDAO.getCategoria(con, pnlCrearCat.nombreCategoria.getText()).getIdCategoria(),
							ImagenDAO.getImagenPorRuta(con, rutasCarpeta.get(idFoto[0] - 1)).getIdImagen(),
							UsuarioDAO.cargarId(con, LoginController.correoUsuario).getId());
					ventanaEmergente.setScene(scene);
					System.out.println(pnlVisualizarImg.imagen.getWidth() / pnlVisualizarImg.imagen.getHeight());
					if (pnlVisualizarImg.imagen.getWidth() / pnlVisualizarImg.imagen.getHeight() <= 1) {
						ventanaEmergente.setHeight(425);
						ventanaEmergente.setWidth(300);
						pnlVisualizarImg.vistaImg.setFitHeight(300);
					} else {
						ventanaEmergente.setHeight(405);
						pnlVisualizarImg.vistaImg.setFitWidth(300);
					}
				});
			});
		});

		ventanaEmergente.setResizable(false);

		ventanaEmergente.setScene(scene);
		ventanaEmergente.setTitle("Imagen");
		ventanaEmergente.show();
		try {
			Image icon = new Image(new FileInputStream("img\\favicon.png"));
			ventanaEmergente.getIcons().add(icon);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		stage.setOnCloseRequest(e -> {
			ventanaEmergente.close();
		});
	}

}
