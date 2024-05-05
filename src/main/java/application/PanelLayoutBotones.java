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
import application.model.NotificacionesDAO;
import application.model.UsuarioDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class PanelLayoutBotones extends GridPane {

	// LocalDate diaMes;
	// Button dia;

	// Meter parametro de fecha, alomejor el array no sé
	PanelLayoutBotones(Stage stage, Connection con, String mes) {
		int x = 0;
		int y = 0;
		int m = 4;
		int d = 1;

//		if (mes.equals("2024-04")) {
//			d = 1;
//			m = 4;
//			for (int i = 1; i <= 35; i++) {
//
//				// diaMes = LocalDate.of(2024, m, d);
//
//				d++;
//
//				if (d == 31) {
//					m = 5;
//					d = 1;
//				}
//
//				Button dia = new Button("2024-" + m + "-" + d);
//				dia.setPrefSize(106, 68);
//
//				dia.setStyle("-fx-background-color: transparent;");
//
//				dia.setOnMouseEntered(e -> {
//					dia.setStyle("-fx-background-color: lightblue;");
//				});
//
//				dia.setOnMouseExited(e -> {
//					dia.setStyle("-fx-background-color: transparent;");
//				});
//
//				this.add(dia, x, y);
//
//				if (y == 0) {
//					this.setMargin(dia, new Insets(86, 3, 4, 0));
//				} else if (y == 1) {
//					this.setMargin(dia, new Insets(0, 3, 4, 0));
//				} else if (y == 2) {
//					this.setMargin(dia, new Insets(0, 3, 4, 0));
//				} else if (y == 3) {
//					this.setMargin(dia, new Insets(0, 3, 4, 0));
//				} else if (y == 4) {
//					this.setMargin(dia, new Insets(0, 3, 4, 0));
//				}
//
//				if (x == 0) {
//					this.setMargin(dia, new Insets(0, 3, 3, 21));
//				} else if (x == 1 && y != 0) {
//					this.setMargin(dia, new Insets(0, 2, 3, 0));
//				} else if (x == 2 && y != 0) {
//					this.setMargin(dia, new Insets(0, 0, 3, 0));
//				} else if (x == 3 && y != 0) {
//					this.setMargin(dia, new Insets(0, 2, 3, 0));
//				} else if (x == 4 && y != 0) {
//					this.setMargin(dia, new Insets(0, 2, 3, 0));
//				} else if (x == 5 && y != 0) {
//					this.setMargin(dia, new Insets(0, 2, 3, 0));
//				} else if (x == 6 && y != 0) {
//					this.setMargin(dia, new Insets(0, 2, 3, 0));
//				}
//
//				if (x == 0 && y == 0) {
//					this.setMargin(dia, new Insets(84, 0, 4, 21));
//				}
//
//				x++;
//				if (x == 7) {
//					y++;
//					x = 0;
//				}
//
//				dia.setOnAction(e -> {
//					String mesCald = dia.getText().substring(5, 6);
//					int mesCaldInt = Integer.parseInt(mesCald);
//					String diaCald = dia.getText().substring(7);
//					int diaCaldInt = Integer.parseInt(diaCald);
//
//					LocalDate diaMes = LocalDate.of(2024, mesCaldInt, diaCaldInt);
//					System.out.println(diaMes);
//					abrirVentanaVisualizarImg(stage, con, diaMes);
//				});
//			}
		// } else

		if (mes.equals("2024-05")) {
			m = 4;
			d = 29;
			int contador = 0;
			for (int i = 1; i <= 35; i++) {
				Button dia = new Button("2024-" + m + "-" + d);
				dia.setPrefSize(106, 68);

				dia.setStyle("-fx-background-color: transparent;");

				dia.setOnMouseEntered(e -> {
					dia.setStyle("-fx-background-color: lightblue;");
				});

				dia.setOnMouseExited(e -> {
					dia.setStyle("-fx-background-color: transparent;");
				});

				// diaMes = LocalDate.of(2024, m, d);

				d++;

				if (d == 31) {
					if (contador == 1) {
						d = 31;
					}
					d = 1;
					m = 5;
					contador++;
				}

				if (d == 32) {
					d = 1;
					m = 6;
				}

				this.add(dia, x, y);

				if (y == 0) {
					this.setMargin(dia, new Insets(86, 3, 4, 0));
				} else if (y == 1) {
					this.setMargin(dia, new Insets(0, 3, 4, 0));
				} else if (y == 2) {
					this.setMargin(dia, new Insets(0, 3, 4, 0));
				} else if (y == 3) {
					this.setMargin(dia, new Insets(0, 3, 4, 0));
				} else if (y == 4) {
					this.setMargin(dia, new Insets(0, 3, 4, 0));
				}

				if (x == 0) {
					this.setMargin(dia, new Insets(0, 3, 3, 21));
				} else if (x == 1 && y != 0) {
					this.setMargin(dia, new Insets(0, 2, 3, 0));
				} else if (x == 2 && y != 0) {
					this.setMargin(dia, new Insets(0, 0, 3, 0));
				} else if (x == 3 && y != 0) {
					this.setMargin(dia, new Insets(0, 2, 3, 0));
				} else if (x == 4 && y != 0) {
					this.setMargin(dia, new Insets(0, 2, 3, 0));
				} else if (x == 5 && y != 0) {
					this.setMargin(dia, new Insets(0, 2, 3, 0));
				} else if (x == 6 && y != 0) {
					this.setMargin(dia, new Insets(0, 2, 3, 0));
				}

				if (x == 0 && y == 0) {
					this.setMargin(dia, new Insets(84, 0, 4, 21));
				}

				x++;
				if (x == 7) {
					y++;
					x = 0;
				}

				dia.setOnAction(e -> {
					String mesCald = dia.getText().substring(5, 6);
					int mesCaldInt = Integer.parseInt(mesCald);
					String diaCald = dia.getText().substring(7);
					int diaCaldInt = Integer.parseInt(diaCald);

					LocalDate diaMes = LocalDate.of(2024, mesCaldInt, diaCaldInt);
					System.out.println(diaMes);
					abrirVentanaVisualizarImg(stage, con, diaMes);
				});
			}
		}

//		else if (mes.equals("2024-06")) {
//			m = 5;
//			d = 27;
//			for (int i = 1; i <= 35; i++) {
//				Button dia = new Button("2024-" + m + "-" + d);
//				dia.setPrefSize(106, 68);
//
//				dia.setStyle("-fx-background-color: transparent;");
//
//				dia.setOnMouseEntered(e -> {
//					dia.setStyle("-fx-background-color: lightblue;");
//				});
//
//				dia.setOnMouseExited(e -> {
//					dia.setStyle("-fx-background-color: transparent;");
//				});
//
//				// diaMes = LocalDate.of(2024, m, d);
//
//				d++;
//
//				if (d == 32) {
//					d = 1;
//					m = 6;
//				}
//
//				this.add(dia, x, y);
//
//				dia.setOnAction(e -> {
//					System.out.println("illo");
//				});
//
//				if (y == 0) {
//					this.setMargin(dia, new Insets(86, 3, 4, 0));
//				} else if (y == 1) {
//					this.setMargin(dia, new Insets(0, 3, 4, 0));
//				} else if (y == 2) {
//					this.setMargin(dia, new Insets(0, 3, 4, 0));
//				} else if (y == 3) {
//					this.setMargin(dia, new Insets(0, 3, 4, 0));
//				} else if (y == 4) {
//					this.setMargin(dia, new Insets(0, 3, 4, 0));
//				}
//
//				if (x == 0) {
//					this.setMargin(dia, new Insets(0, 3, 3, 21));
//				} else if (x == 1 && y != 0) {
//					this.setMargin(dia, new Insets(0, 2, 3, 0));
//				} else if (x == 2 && y != 0) {
//					this.setMargin(dia, new Insets(0, 0, 3, 0));
//				} else if (x == 3 && y != 0) {
//					this.setMargin(dia, new Insets(0, 2, 3, 0));
//				} else if (x == 4 && y != 0) {
//					this.setMargin(dia, new Insets(0, 2, 3, 0));
//				} else if (x == 5 && y != 0) {
//					this.setMargin(dia, new Insets(0, 2, 3, 0));
//				} else if (x == 6 && y != 0) {
//					this.setMargin(dia, new Insets(0, 2, 3, 0));
//				}
//
//				if (x == 0 && y == 0) {
//					this.setMargin(dia, new Insets(84, 0, 4, 21));
//				}
//
//				x++;
//				if (x == 7) {
//					y++;
//					x = 0;
//				}
//				dia.setOnAction(e -> {
//					String mesCald = dia.getText().substring(5, 6);
//					int mesCaldInt = Integer.parseInt(mesCald);
//					String diaCald = dia.getText().substring(7);
//					int diaCaldInt = Integer.parseInt(diaCald);
//
//					LocalDate diaMes = LocalDate.of(2024, mesCaldInt, diaCaldInt);
//					System.out.println(diaMes);
//					abrirVentanaVisualizarImg(stage, con, diaMes);
//				});
//			}
//
//		}

	}

	public void abrirVentanaVisualizarImg(Stage stage, Connection con, LocalDate fechaDia) {
		Stage ventanaEmergente = new Stage();
		PanelVisualizarImagen pnlVisualizarImg = new PanelVisualizarImagen();

		int idFoto[] = { 1 };

		System.out.println(fechaDia);

		ArrayList<String> rutasCarpeta = ImagenDAO.getDia(con, fechaDia);

		if (ImagenDAO.getDia(con, fechaDia).size() > 0) {

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
							CategoriaDAO.getCategoria(con, (String) pnlCategoria.chbCategorias.getValue())
									.getIdCategoria(),
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
		} else {
			NotificacionesDAO.mostrarNotificacion(NotificacionesDAO.getNotificaciones(con, 4).getMensaje());
		}

	}
}
