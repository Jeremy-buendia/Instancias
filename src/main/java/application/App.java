package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import application.model.CalendarioDAO;
import application.model.CategoriaDAO;
import application.model.CategoriaDO;
import application.model.ImagenDAO;
import application.model.ImagenDO;
import application.model.OpcionesDAO;
import application.model.UsuarioDAO;
import application.utils.UtilsBD;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

	public static boolean cerrarVentana = false;

	@Override
	public void start(Stage stage) {
		Connection con = UtilsBD.conectarBD();
		// Panel
		BorderPane pnlDistribucion = new BorderPane();

		/************** MENÚ SUPERIOR ****************/
		MenuBar menuSuperior = new MenuBar();

		// Menú mPerfil
		Menu mPerfil = new Menu("Perfil");

		// MenuItems y Submenús de mPerfil
		MenuItem iCambiarNombre = new MenuItem("Cambiar Nombre de Perfil");
		MenuItem iCambiarPass = new MenuItem("Cambiar Contraseña");
		MenuItem iCambiarCorreo = new MenuItem("Cambiar Correo");

		Menu mOpcSesion = new Menu("Opciones de Sesión");

		MenuItem iCerrarSesion = new MenuItem("Cerrar Sesión");

		iCerrarSesion.setOnAction(e -> {
			cerrarSesion();
		});

		MenuItem iCambiarSesion = new MenuItem("Cambiar Sesión");

		// Menú mBuscar
		Menu mBuscar = new Menu("Buscar");

		// MenuItems y Submenús de mBuscar y datePiicker
		CalendarioDAO calendarioDAO = new CalendarioDAO();
		MenuItem iBuscarFecha = new MenuItem("Buscar Fecha");

		iBuscarFecha.setOnAction(e -> {
			LocalDate fecha = CalendarioDAO.buscarFecha();
			if (fecha != null) {
				System.out.println("Fecha seleccionada: " + fecha);
				ResultSet rs = CalendarioDAO.buscarDatos(fecha);
				try {
					if (rs != null) {
						while (rs.next()) {
							System.out.println("Datos: " + rs.getString("Fecha"));
						}
					}
				} catch (Exception ex) {
					System.out.println(ex);
				}
				abrirVentanaVisualizarImg(stage, con, fecha);
			} else {
				System.out.println("No se seleccionó ninguna fecha.");
			}
		});

		// mBuscar.getItems().add(iBuscarFecha);

		// Menú mConfig
		Menu mConfig = new Menu("Configuración");

		// CheckMenuItem para Notificaciones
		CheckMenuItem iNotificaciones = new CheckMenuItem("Notificaciones");

		iNotificaciones.setOnAction(event -> {
			// Obtén el estado actual del CheckMenuItem
			int estado = ((CheckMenuItem) iNotificaciones).isSelected() ? 1 : 0;
			System.out.println(estado);
			// Actualiza el objeto 'activo' con el nuevo estado

			int numAff = OpcionesDAO.activarNotificaciones(
					UsuarioDAO.cargarId(con, LoginController.correoUsuario).getId(), estado, con);

			// Llama a la función para actualizar la base de datos
			// Asegúrate de que 'funcion' es el nombre de tu método de actualización

			// Comprueba si la actualización fue exitosa

			if (numAff > 0) {

				System.out.println("La base de datos se ha actualizado correctamente.");

			} else {

				System.out.println("Hubo un problema al actualizar la base de datos.");
			}
		});

		Menu mIdioma = new Menu("Idioma");
		MenuItem iEspanol = new MenuItem("Español");
		MenuItem iIngles = new MenuItem("Inglés");

		// Añadir los oyentes de acción a los elementos del menú
		// 0 para español
		iEspanol.setOnAction(e -> OpcionesDAO.cambiarIdioma(0));
		// 1 para inglés
		iIngles.setOnAction(e -> OpcionesDAO.cambiarIdioma(1));

		Menu mApariencia = new Menu("Apariencia");

		CheckMenuItem iModo = new CheckMenuItem("Modo");

		MenuItem iFuente = new MenuItem("Fuente");
		MenuItem iDiseño = new MenuItem("Diseño");

		// Menú mAyuda
		Menu mAyuda = new Menu("Ayuda");

		// MenuItems y Submenús de mAyuda
		MenuItem iContactanos = new MenuItem("Contáctanos");

		Menu mAcercaDe = new Menu("Acerca de");

		MenuItem iVersion = new MenuItem("Versión de la Aplicación");
		MenuItem iNosotros = new MenuItem("Nosotros");
		MenuItem iActualizaciones = new MenuItem("Actualizaciones");

		// Añadimos los MenuItems y los subemnús a los menús
		mPerfil.getItems().addAll(iCambiarPass, iCambiarNombre, iCambiarCorreo, mOpcSesion);
		mBuscar.getItems().addAll(iBuscarFecha);
		mConfig.getItems().addAll(mIdioma, iNotificaciones, mApariencia);
		mAyuda.getItems().addAll(mAcercaDe, iContactanos);

		mOpcSesion.getItems().addAll(iCerrarSesion, iCambiarSesion);
		mIdioma.getItems().addAll(iEspanol, iIngles);
		mApariencia.getItems().addAll(iModo, iFuente, iDiseño);
		mAcercaDe.getItems().addAll(iVersion, iNosotros, iActualizaciones);

		// Agregamos los menús al MenuBar
		menuSuperior.getMenus().addAll(mPerfil, mBuscar, mConfig, mAyuda);

		// Añadir un controlador de eventos al elemento de menú
		pnlDistribucion.setTop(menuSuperior);

		/************** MENÚ INFERIOR ****************/
		ToolBar menuInferior = new ToolBar();

		Button marcados = new Button("Marcados");

		marcados.setOnAction(e -> {
			abrirVentanaVisualizarMarcados(stage, con);
		});

		Button subirFoto = new Button("Subir foto");

		subirFoto.setOnAction(e -> {
			abrirVentanaSubirImagen(stage, con);
		});

		Button bajarFoto = new Button("Descargar foto");

		bajarFoto.setOnAction(e -> {
			// LocalDate fechaDia = LocalDate.of(2024, 05, 1);
			LocalDate fechaDia = LocalDate.now();
			// abrirVentanaVisualizarImg(stage, con, fechaDia);
			DirectoryChooser directorio = new DirectoryChooser();
			directorio.setTitle("Selecciona una carpeta");
			ArrayList<String> rutasCarpeta = ImagenDAO.getDia(con, fechaDia);

			File directorioSeleccionado = directorio.showDialog(null);

			for (int i = 0; i < rutasCarpeta.size(); i++) {
				File imagen = new File(rutasCarpeta.get(i));
				ImagenDAO.descargarImagen(imagen, directorioSeleccionado);
			}

		});

		Button abrirCamara = new Button("Abrir la cámara");

		Button mesAnterior = new Button("<--");
		Button mesPosterior = new Button("-->");

		menuInferior.getItems().addAll(marcados, subirFoto, bajarFoto, abrirCamara, mesAnterior, mesPosterior);

		pnlDistribucion.setBottom(menuInferior);

		/************** CALENDARIO ****************/

		/************** ESCENA ****************/
		try {
			Image icon = new Image(new FileInputStream("img\\favicon.png"));
			stage.getIcons().add(icon);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		var scene = new Scene(pnlDistribucion, 800, 600);

		iModo.setOnAction(event -> {
			// Verifica si iModo está seleccionado
			int estado = ((CheckMenuItem) iModo).isSelected() ? 1 : 0;
			if (estado == 1) {
				// Cambia el color de fondo a negro
				scene.setFill(Color.BLACK);
			} else {
				// Cambia el color de fondo a blanco
				scene.setFill(Color.WHITE);
			}
			// Actualiza la base de datos
			OpcionesDAO.cambiarModo(UsuarioDAO.cargarId(con, LoginController.correoUsuario).getId(), estado, con);

			// Llama a la función getCalendario
			CalendarioDAO.getCalendario(estado, con);
		});

		stage.setScene(scene);

		stage.show();

		abrirVentanaFormulario(stage, con, mesAnterior, mesPosterior, pnlDistribucion);
	}

	/**
	 * Función que abre una ventana emergente para poder subir una foto
	 * 
	 * @param stage
	 * @param con
	 */
	public void abrirVentanaSubirImagen(Stage stage, Connection con) {
		Stage ventanaEmergente = new Stage();
		PanelSubirImagen pnlSubirImg = new PanelSubirImagen();

		Scene scene = new Scene(pnlSubirImg, 300, 300);

		// Bloqueamos la ventana padre definiendo cual es el padre y poner la modalidad
		ventanaEmergente.initOwner(stage);
		ventanaEmergente.initModality(Modality.WINDOW_MODAL);

		ventanaEmergente.setScene(scene);
		ventanaEmergente.setTitle("Subir Imagen");
		ventanaEmergente.show();

		try {
			Image icon = new Image(new FileInputStream("img\\favicon.png"));
			ventanaEmergente.getIcons().add(icon);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		pnlSubirImg.btnEscogerImg.setOnAction(e -> {
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos de Imagen", "*.png",
					"*.jpg", "*.jpeg");
			pnlSubirImg.escogerImagen.getExtensionFilters().add(extFilter);
			pnlSubirImg.escogerImagen.setTitle("Selecciona una imagen");

			File imagen = pnlSubirImg.escogerImagen.showOpenDialog(null);

			if (imagen != null) {
				int marcado;

				if (pnlSubirImg.cbxMarcar.isSelected()) {
					marcado = 1;
				} else {
					marcado = 0;
				}

				ImagenDO objImagen;
				objImagen = new ImagenDO(-1, pnlSubirImg.txtDescripcionImg.getText(), "", "",
						UsuarioDAO.cargarId(con, LoginController.correoUsuario).getId(), marcado);
				ImagenDAO.subirImagen(con, objImagen, imagen);
				ImagenDAO.copiarImagen(imagen, objImagen);

				ventanaEmergente.close();
			}

		});
	}

	/**
	 * Función que abre la ventana para registrarse
	 * 
	 * @param stage
	 * @param con
	 */
	public void abrirVentanaFormulario(Stage stage, Connection con, Button mesAnterior, Button mesPosterior,
			BorderPane pnlDistribucion) {
		try {
			// Cargar el archivo FXML de la ventana emergente
			FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/javafx_fxml/IniciarSesion.fxml"));
			FXMLLoader registerLoader = new FXMLLoader(getClass().getResource("/javafx_fxml/Registrarse.fxml"));

			Parent loginRoot = loginLoader.load();
			Scene loginScene = new Scene(loginRoot);

			Parent registerRoot = registerLoader.load();
			Scene registerScene = new Scene(registerRoot);

			RegistroController registerController = registerLoader.getController();
			registerController.setLoginScene(loginScene);

			LoginController loginController = loginLoader.getController();
			loginController.setRegisterScene(registerScene);

			// Pane ventanaEmergente = loader.load();
			Stage loginStage = new Stage();
			loginController.setVentanaActual(loginStage);
			loginStage.initOwner(stage);
			loginStage.initModality(Modality.WINDOW_MODAL);
			loginStage.setScene(loginScene);

			try {
				Image icon = new Image(new FileInputStream("img\\favicon.png"));
				loginStage.getIcons().add(icon);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			loginStage.setOnCloseRequest(e -> {
				if (!LoginController.cargarCalendario) {
					stage.close();
				}
			});

			loginStage.showAndWait();

			loginController.setVentanaActual(loginStage);

			if (LoginController.cargarCalendario) {
				visualizarCalendario(con, mesAnterior, mesPosterior, pnlDistribucion);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Función que abre una ventana para visualizar las imágenes de un día
	 * 
	 * @param stage
	 * @param con
	 * @param fechaDia
	 */
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

		if (aspecto < 1) {
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
						ventanaEmergente.setHeight(300 / aspecto + 100);
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
					ventanaEmergente.setHeight(300 / aspecto + 100);
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
					ventanaEmergente.setHeight(300 / aspecto + 105);
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
						ventanaEmergente.setHeight(300 / aspecto + 105);
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

	/**
	 * Función que abre una ventanaEmergente con las imagenes que han sido marcadas
	 * 
	 * @param stage
	 * @param con
	 */
	public static void abrirVentanaVisualizarMarcados(Stage stage, Connection con) {
		Stage ventanaEmergente = new Stage();
		PanelVisualizarImagen pnlVisualizarImg = new PanelVisualizarImagen();
		int idFoto[] = { 1 };

		ArrayList<String> rutasCarpeta = ImagenDAO.getMarcados(con);

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

		if (aspecto < 1) {
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
						ventanaEmergente.setHeight(300 / aspecto + 100);
						pnlVisualizarImg.vistaImg.setFitWidth(300);
					}

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
					ventanaEmergente.setHeight(300 / aspecto + 100);
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
				if (pnlVisualizarImg.imagen.getWidth() / pnlVisualizarImg.imagen.getHeight() < 1) {
					ventanaEmergente.setHeight(425);
					ventanaEmergente.setWidth(300);
					pnlVisualizarImg.vistaImg.setFitHeight(300);
				} else {
					ventanaEmergente.setHeight(300 / aspecto + 105);
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
					if (pnlVisualizarImg.imagen.getWidth() / pnlVisualizarImg.imagen.getHeight() < 1) {
						ventanaEmergente.setHeight(425);
						ventanaEmergente.setWidth(300);
						pnlVisualizarImg.vistaImg.setFitHeight(300);
					} else {
						ventanaEmergente.setHeight(300 / aspecto + 105);
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

	public static void visualizarCalendario(Connection con, Button mesAnterior, Button mesPosterior,
			BorderPane pnlDistribucion) {
		PanelVisualizarCalendario pnlCalendario = new PanelVisualizarCalendario();
		pnlDistribucion.setCenter(pnlCalendario);
		try {
			ArrayList<String> rutasCarpeta = CalendarioDAO.getCalendario(0, con);
			int[] indice = { 0 };

			LocalDate fechaActual = LocalDate.now();
			DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM");
			String fechaFormateada = fechaActual.format(formatoFecha);

			for (int i = 0; i < rutasCarpeta.size(); i++) {
				if (fechaFormateada.equals(rutasCarpeta.get(i).substring(4, 11))) {
					pnlCalendario.imagen = new Image(new FileInputStream(rutasCarpeta.get(i)));
					pnlCalendario.vistaCalendario.setImage(pnlCalendario.imagen);
					indice[0] = i;
				}
			}

			mesAnterior.setOnAction(e -> {
				if (indice[0] > 0) {
					try {
						indice[0] -= 1;
						pnlCalendario.vistaCalendario
								.setImage(new Image(new FileInputStream(rutasCarpeta.get(indice[0]))));
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});

			mesPosterior.setOnAction(e -> {
				if (indice[0] < rutasCarpeta.size() - 1) {
					try {
						indice[0] += 1;
						pnlCalendario.vistaCalendario
								.setImage(new Image(new FileInputStream(rutasCarpeta.get(indice[0]))));
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cerrarSesion() {
		// Platform.startup(null);

		// Platform.exit();

//		try {
//			String aplicacion = System.getProperty("java.home") + "/bin/java";
//			ProcessBuilder builder = new ProcessBuilder(java, "-jar", "")
//		}catch(Exception e) {
//			
//		}

	}

	public static void main(String[] args) {
		launch();
	}

}