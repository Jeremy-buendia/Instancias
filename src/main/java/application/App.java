package application;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.github.lalyos.jfiglet.FigletFont;

import application.model.CalendarioDAO;
import application.model.CategoriaDAO;
import application.model.CategoriaDO;
import application.model.ImagenDAO;
import application.model.ImagenDO;
import application.model.NotificacionesDAO;
import application.model.OpcionesDAO;
import application.model.OpcionesDO;
import application.model.UsuarioDAO;
import application.model.UsuarioDO;
import application.utils.UtilsBD;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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

		iCambiarPass.setOnAction(e -> {
			cambiarContrasena(stage);
		});

		MenuItem iCambiarCorreo = new MenuItem("Cambiar Correo");

		Menu mOpcSesion = new Menu("Opciones de Sesión");

		MenuItem iCerrarSesion = new MenuItem("Cerrar Sesión");

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
		iNotificaciones.setSelected(true);

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

//		// Añadir los oyentes de acción a los elementos del menú
//		// 0 para español
//		iEspanol.setOnAction(e -> OpcionesDAO.cambiarIdioma(0, con));
//		// 1 para inglés
//		iIngles.setOnAction(e -> OpcionesDAO.cambiarIdioma(1, con));

		Menu mApariencia = new Menu("Apariencia");

		CheckMenuItem iModo = new CheckMenuItem("Modo");

		MenuItem iFuente = new MenuItem("Fuente");

		Stage stage1 = new Stage();

		iFuente.setOnAction(e -> {
			// Aumenta el tamaño de la escena
			double nuevoAncho = stage1.getWidth() + 150; // Aumenta el ancho en 10
			double nuevoAlto = stage1.getHeight() + 150; // Aumenta el alto en 10
			stage1.setWidth(nuevoAncho);
			stage1.setHeight(nuevoAlto);
		});

		MenuItem iDiseno = new MenuItem("Diseño");

		// Menú mAyuda
		Menu mAyuda = new Menu("Ayuda");

		// MenuItems y Submenús de mAyuda
		MenuItem iContactanos = new MenuItem("Contáctanos");
		iContactanos.setOnAction(event -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Información de contacto");
			alert.setHeaderText(null);
			alert.setContentText("Nuestro correo: Instancias@gmail.com\r\n" + "\r\n" + "©Instancias");
			alert.showAndWait();
		});
		Menu mAcercaDe = new Menu("Acerca de");

		MenuItem iVersion = new MenuItem("Versión de la Aplicación");
		iVersion.setOnAction(event -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Version Instancias");
			alert.setHeaderText(null);
			alert.setContentText("Version: 0.1.0");
			alert.showAndWait();
		});

		MenuItem iGithub = new MenuItem("Github");

		iGithub.setOnAction(e -> {
			try {
				Desktop.getDesktop().browse(new URI("https://github.com/Jeremy-buendia/Instancias"));
			} catch (IOException | URISyntaxException e2) {
				e2.printStackTrace();
			}
		});

		MenuItem iNosotros = new MenuItem("Nosotros");
		iNosotros.setOnAction(event -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Acerda de Instancias");
			alert.setHeaderText(null);
			alert.setContentText(
					"Acerca de:Instancias es una aplicación innovadora que combina la funcionalidad de un calendario con la capacidad de subir y almacenar fotos. Diseñada para ayudarte a capturar y recordar los momentos más importantes de tu vida, Instancias te permite asociar imágenes con fechas específicas en tu calendario.\r\n"
							+ "\r\n" + "Características principales:\r\n" + "\r\n"
							+ "Calendario personalizable: Navega fácilmente por el calendario y selecciona cualquier fecha para ver o añadir fotos.\r\n"
							+ "Subida de fotos: Sube tus fotos favoritas directamente desde tu dispositivo y asócialas con una fecha específica. Revive tus recuerdos con solo un clic.\r\n"
							+ "Almacenamiento seguro: Tus fotos se almacenan de forma segura y solo tú puedes acceder a ellas.\r\n"
							+ "Comparte tus momentos: Comparte tus fotos favoritas con amigos y familiares directamente desde la aplicación.\r\n");
			// Permitir que la ventana se redimensione
			alert.setResizable(true);
			// Establecer el tamaño inicial de la ventana
			alert.setHeight(600);
			alert.setWidth(800);
			alert.showAndWait();
		});
		MenuItem iActualizaciones = new MenuItem("Actualizaciones");
		iActualizaciones.setOnAction(event -> {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Actualizaciones de Instancias");
			alert.setHeaderText(null);
			alert.setContentText("Actualizaciones: Se supone que no habrá mas actualizaciones");
			alert.showAndWait();
		});

		MenuItem iManual = new MenuItem("Instancias");

		iManual.setOnAction(e -> {
			VBox panel = new VBox();
			Stage ventanaEmergente = new Stage();

//			Alert alert = new Alert(AlertType.INFORMATION);
//			alert.setTitle("Gracias");
//			alert.setHeaderText(null);

			Button javadoc = new Button("Accede al javadoc");

			javadoc.setOnAction(e2 -> {
				abrirHTML();
			});

			Scene escena = new Scene(panel, 300, 300);
			ventanaEmergente.setResizable(false);

			ventanaEmergente.setScene(escena);
			ventanaEmergente.setTitle("Imagen");
			ventanaEmergente.show();

			try {
				String asciiArt = FigletFont.convertOneLine("Instancias");
				Label texto = new Label(asciiArt);

				panel.getChildren().addAll(texto, javadoc);
			} catch (Exception e2) {

			}

		});

		// Añadimos los MenuItems y los subemnús a los menús
		mPerfil.getItems().addAll(iCambiarPass, iCambiarNombre, iCambiarCorreo, mOpcSesion);
		mBuscar.getItems().addAll(iBuscarFecha);
		mConfig.getItems().addAll(mIdioma, iNotificaciones, mApariencia);
		mAyuda.getItems().addAll(mAcercaDe, iContactanos);

		mOpcSesion.getItems().addAll(iCerrarSesion, iCambiarSesion);
		mIdioma.getItems().addAll(iEspanol, iIngles);
		mApariencia.getItems().addAll(iModo, iFuente, iDiseno);
		mAcercaDe.getItems().addAll(iVersion, iNosotros, iActualizaciones, iManual, iGithub);

		// Agregamos los menús al MenuBar
		menuSuperior.getMenus().addAll(mPerfil, mBuscar, mConfig, mAyuda);

		// Añadir un controlador de eventos al elemento de menú
		pnlDistribucion.setTop(menuSuperior);

		/************** MENÚ INFERIOR ****************/
		ToolBar menuInferior = new ToolBar();

		Button marcados = new Button("Marcados");

		marcados.setOnAction(e -> {
			abrirVentanaVisualizarMarcados(stage1, con);
		});

		Button subirFoto = new Button("Subir foto");

		subirFoto.setOnAction(e -> {
			abrirVentanaSubirImagen(stage1, con);
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
				NotificacionesDAO.mostrarNotificacion(NotificacionesDAO.getNotificaciones(con, 3).getMensaje());
			}

		});

		Button abrirCamara = new Button("Abrir la cámara");

		abrirCamara.setOnAction(e -> {
			abrirCamara();
			monitoreoDeCarpeta(con, stage);
		});

		Button mesAnterior = new Button("<--");
		Button mesPosterior = new Button("-->");

		menuInferior.getItems().addAll(marcados, subirFoto, bajarFoto, abrirCamara, mesAnterior, mesPosterior);

		pnlDistribucion.setBottom(menuInferior);

		iCambiarSesion.setOnAction(e -> {
			abrirVentanaFormulario(stage, con, mesAnterior, mesPosterior, pnlDistribucion);
		});

		iCambiarCorreo.setOnAction(e -> {
			cambiarCorreo(stage);
		});

		iCerrarSesion.setOnAction(e -> {
			stage1.close();
		});

		/************** CALENDARIO ****************/

		final boolean[] isidioma = { false };
		mIdioma.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// Crea un nuevo label
				Label labelModo = new Label("Modo");
				Label labelFuente = new Label("Fuente");
				Label labelDiseno = new Label("Diseño");
				Label labelIdioma = new Label("Idioma");
				Label labelNotificaciones = new Label("Notificaciones");
				Label labelApariencia = new Label("Apariencia");
				Label labelAyuda = new Label("Ayuda");
				Label labelActualizaciones = new Label("Actualizaciones");
				Label labelNosotros = new Label("Nosotros");
				Label labelVersion = new Label("Version");
				Label labelContactanos = new Label("Contactanos");
				Label labelPerfil = new Label("Perfil");
				Label labelConfiguracion = new Label("Configuracion");
				Label labelBuscar = new Label("Buscar");
				Label labelBuscarFecha = new Label("Buscar Fecha");
				Label labelAcercaDe = new Label("Acerca de");
				Label labelCamNombre = new Label("Cambiar Nombre de Perfil");
				Label labelCamContr = new Label("Cambiar Contraseña");
				Label labelCamCorreo = new Label("Cambiar Correo");
				Label labelOpSesion = new Label("Opciones de Sesión");
				Label labelCerrarSesion = new Label("Cerrar Sesión");
				Label labelCambiarSesion = new Label("Cambiar Sesión");

				// Comprueba si el texto está actualmente ampliado
				if (isidioma[0]) {
					// Si el texto está ampliado, restablece el tamaño de la fuente al tamaño
					// original
					// Cambia a 12 el tamaño de fuente original que desees
					labelModo.setFont(new Font("System", 12));
					labelFuente.setFont(new Font("System", 12));
					labelDiseno.setFont(new Font("System", 12));
					labelIdioma.setFont(new Font("System", 12));
					labelNotificaciones.setFont(new Font("System", 12));
					labelApariencia.setFont(new Font("System", 12));
					labelAyuda.setFont(new Font("System", 12));
					labelActualizaciones.setFont(new Font("System", 12));
					labelNosotros.setFont(new Font("System", 12));
					labelVersion.setFont(new Font("System", 12));
					labelContactanos.setFont(new Font("System", 12));
					labelPerfil.setFont(new Font("System", 12));
					labelConfiguracion.setFont(new Font("System", 12));
					labelBuscar.setFont(new Font("System", 12));
					labelBuscarFecha.setFont(new Font("System", 12));
					labelAcercaDe.setFont(new Font("System", 12));
					labelCamNombre.setFont(new Font("System", 12));
					labelCamContr.setFont(new Font("System", 12));
					labelCamCorreo.setFont(new Font("System", 12));
					labelOpSesion.setFont(new Font("System", 12));
					labelCerrarSesion.setFont(new Font("System", 12));
					labelCambiarSesion.setFont(new Font("System", 12));
					// Cambia el color a negro de la fuente original que desees
					labelModo.setTextFill(Color.BLACK);
					labelFuente.setTextFill(Color.BLACK);
					labelDiseno.setTextFill(Color.BLACK);
					labelIdioma.setTextFill(Color.BLACK);
					labelNotificaciones.setTextFill(Color.BLACK);
					labelApariencia.setTextFill(Color.BLACK);
					labelAyuda.setTextFill(Color.BLACK);
					labelActualizaciones.setTextFill(Color.BLACK);
					labelNosotros.setTextFill(Color.BLACK);
					labelVersion.setTextFill(Color.BLACK);
					labelContactanos.setTextFill(Color.BLACK);
					labelPerfil.setTextFill(Color.BLACK);
					labelConfiguracion.setTextFill(Color.BLACK);
					labelBuscar.setTextFill(Color.BLACK);
					labelBuscarFecha.setTextFill(Color.BLACK);
					labelAcercaDe.setTextFill(Color.BLACK);
					labelCamNombre.setTextFill(Color.BLACK);
					labelCamContr.setTextFill(Color.BLACK);
					labelCamCorreo.setTextFill(Color.BLACK);
					labelOpSesion.setTextFill(Color.BLACK);
					labelCerrarSesion.setTextFill(Color.BLACK);
					labelCambiarSesion.setTextFill(Color.BLACK);

					isidioma[0] = false;
				} else {
					// Si el texto no está ampliado, aumenta el tamaño de la fuente
					// Cambia a 20 el tamaño de fuente ampliado que desees
					labelModo.setText("Way");
					labelFuente.setText("Fountain");
					labelDiseno.setText("Design");
					labelIdioma.setText("Language");
					labelNotificaciones.setText("Notifications");
					labelApariencia.setText("Appearance");
					labelAyuda.setText("Help");
					labelActualizaciones.setText("Updates");
					labelNosotros.setText("Us");
					labelVersion.setText("Version");
					labelContactanos.setText("Contact us");
					labelPerfil.setText("Profile");
					labelConfiguracion.setText("Setting");
					labelBuscar.setText("Search");
					labelBuscarFecha.setText("Search date");
					labelAcercaDe.setText("About");
					labelCamNombre.setText("Rename");
					labelCamContr.setText("Change Password");
					labelCamCorreo.setText("Change email");
					labelOpSesion.setText("Session options");
					labelCerrarSesion.setText("Sign off");
					labelCambiarSesion.setText("ChangeSessions");
					// Cambia el color a negro de la fuente original que desees
					labelModo.setTextFill(Color.BLACK);
					labelFuente.setTextFill(Color.BLACK);
					labelDiseno.setTextFill(Color.BLACK);
					labelIdioma.setTextFill(Color.BLACK);
					labelNotificaciones.setTextFill(Color.BLACK);
					labelApariencia.setTextFill(Color.BLACK);
					labelAyuda.setTextFill(Color.BLACK);
					labelActualizaciones.setTextFill(Color.BLACK);
					labelNosotros.setTextFill(Color.BLACK);
					labelVersion.setTextFill(Color.BLACK);
					labelContactanos.setTextFill(Color.BLACK);
					labelPerfil.setTextFill(Color.BLACK);
					labelConfiguracion.setTextFill(Color.BLACK);
					labelBuscar.setTextFill(Color.BLACK);
					labelBuscarFecha.setTextFill(Color.BLACK);
					labelAcercaDe.setTextFill(Color.BLACK);
					labelCamNombre.setTextFill(Color.BLACK);
					labelCamContr.setTextFill(Color.BLACK);
					labelCamCorreo.setTextFill(Color.BLACK);
					labelOpSesion.setTextFill(Color.BLACK);
					labelCerrarSesion.setTextFill(Color.BLACK);
					labelCambiarSesion.setTextFill(Color.BLACK);
					isidioma[0] = true;
				}

				// Elimina el texto del CheckMenuItem
				iModo.setText("");
				iFuente.setText("");
				iDiseno.setText("");
				mIdioma.setText("");
				iNotificaciones.setText("");
				mApariencia.setText("");
				mAyuda.setText("");
				iActualizaciones.setText("");
				iNosotros.setText("");
				iVersion.setText("");
				iContactanos.setText("");
				mPerfil.setText("");
				mConfig.setText("");
				mBuscar.setText("");
				iBuscarFecha.setText("");
				mAcercaDe.setText("");
				iCambiarNombre.setText("");
				iCambiarPass.setText("");
				iCambiarCorreo.setText("");
				mOpcSesion.setText("");
				iCerrarSesion.setText("");
				iCambiarSesion.setText("");
				// Establece el label como el gráfico del CheckMenuItem
				iModo.setGraphic(labelModo);
				iFuente.setGraphic(labelFuente);
				iDiseno.setGraphic(labelDiseno);
				mIdioma.setGraphic(labelIdioma);
				iNotificaciones.setGraphic(labelNotificaciones);
				mApariencia.setGraphic(labelApariencia);
				mAyuda.setGraphic(labelAyuda);
				iActualizaciones.setGraphic(labelActualizaciones);
				iNosotros.setGraphic(labelNosotros);
				iVersion.setGraphic(labelVersion);
				iContactanos.setGraphic(labelContactanos);
				mPerfil.setGraphic(labelPerfil);
				mConfig.setGraphic(labelConfiguracion);
				mBuscar.setGraphic(labelBuscar);
				iBuscarFecha.setGraphic(labelBuscarFecha);
				mAcercaDe.setGraphic(labelBuscarFecha);
				iCambiarNombre.setGraphic(labelCamNombre);
				iCambiarPass.setGraphic(labelCamContr);
				iCambiarCorreo.setGraphic(labelCamCorreo);
				mOpcSesion.setGraphic(labelOpSesion);
				iCerrarSesion.setGraphic(labelCerrarSesion);
				iCambiarSesion.setGraphic(labelCambiarSesion);
				OpcionesDO opciones = new OpcionesDO();
				opciones.setIdioma(isidioma[0] ? 1 : 0);
				// Obtener la conexión a la base de datos
				Connection con = UtilsBD.conectarBD();
				// Llamar a la función para cambiar el idioma en la base de datos
				int numAff = OpcionesDAO.cambiarIdioma(opciones, con);
				// Comprobar si la actualización fue exitosa
				if (numAff > 0) {
					System.out.println("Idioma actualizado con éxito.");
				} else {
					System.out.println("Error al actualizar el idioma.");
				}
			}
		});

		final boolean[] isEnlarged = { false };
		iFuente.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// Crea un nuevo label
				Label labelModo = new Label("Modo");
				Label labelFuente = new Label("Fuente");
				Label labelDiseno = new Label("Diseño");
				Label labelIdioma = new Label("Idioma");
				Label labelNotificaciones = new Label("Notificaciones");
				Label labelApariencia = new Label("Apariencia");
				Label labelAyuda = new Label("Ayuda");
				Label labelActualizaciones = new Label("Actualizaciones");
				Label labelNosotros = new Label("Nosotros");
				Label labelVersion = new Label("Version");
				Label labelContactanos = new Label("Contactanos");
				Label labelPerfil = new Label("Perfil");
				Label labelConfiguracion = new Label("Configuracion");
				Label labelBuscar = new Label("Buscar");
				Label labelBuscarFecha = new Label("Buscar Fecha");
				Label labelAcercaDe = new Label("Acerca de");
				Label labelCamNombre = new Label("Cambiar Nombre de Perfil");
				Label labelCamContr = new Label("Cambiar Contraseña");
				Label labelCamCorreo = new Label("Cambiar Correo");
				Label labelOpSesion = new Label("Opciones de Sesión");
				Label labelCerrarSesion = new Label("Cerrar Sesión");
				Label labelCambiarSesion = new Label("Cambiar Sesión");

				// Comprueba si el texto está actualmente ampliado
				if (isEnlarged[0]) {
					// Si el texto está ampliado, restablece el tamaño de la fuente al tamaño
					// original
					// Cambia a 12 el tamaño de fuente original que desees
					labelModo.setFont(new Font("System", 12));
					labelFuente.setFont(new Font("System", 12));
					labelDiseno.setFont(new Font("System", 12));
					labelIdioma.setFont(new Font("System", 12));
					labelNotificaciones.setFont(new Font("System", 12));
					labelApariencia.setFont(new Font("System", 12));
					labelAyuda.setFont(new Font("System", 12));
					labelActualizaciones.setFont(new Font("System", 12));
					labelNosotros.setFont(new Font("System", 12));
					labelVersion.setFont(new Font("System", 12));
					labelContactanos.setFont(new Font("System", 12));
					labelPerfil.setFont(new Font("System", 12));
					labelConfiguracion.setFont(new Font("System", 12));
					labelBuscar.setFont(new Font("System", 12));
					labelBuscarFecha.setFont(new Font("System", 12));
					labelAcercaDe.setFont(new Font("System", 12));
					labelCamNombre.setFont(new Font("System", 12));
					labelCamContr.setFont(new Font("System", 12));
					labelCamCorreo.setFont(new Font("System", 12));
					labelOpSesion.setFont(new Font("System", 12));
					labelCerrarSesion.setFont(new Font("System", 12));
					labelCambiarSesion.setFont(new Font("System", 12));
					// Cambia el color a negro de la fuente original que desees
					labelModo.setTextFill(Color.BLACK);
					labelFuente.setTextFill(Color.BLACK);
					labelDiseno.setTextFill(Color.BLACK);
					labelIdioma.setTextFill(Color.BLACK);
					labelNotificaciones.setTextFill(Color.BLACK);
					labelApariencia.setTextFill(Color.BLACK);
					labelAyuda.setTextFill(Color.BLACK);
					labelActualizaciones.setTextFill(Color.BLACK);
					labelNosotros.setTextFill(Color.BLACK);
					labelVersion.setTextFill(Color.BLACK);
					labelContactanos.setTextFill(Color.BLACK);
					labelPerfil.setTextFill(Color.BLACK);
					labelConfiguracion.setTextFill(Color.BLACK);
					labelBuscar.setTextFill(Color.BLACK);
					labelBuscarFecha.setTextFill(Color.BLACK);
					labelAcercaDe.setTextFill(Color.BLACK);
					labelCamNombre.setTextFill(Color.BLACK);
					labelCamContr.setTextFill(Color.BLACK);
					labelCamCorreo.setTextFill(Color.BLACK);
					labelOpSesion.setTextFill(Color.BLACK);
					labelCerrarSesion.setTextFill(Color.BLACK);
					labelCambiarSesion.setTextFill(Color.BLACK);

					isEnlarged[0] = false;
				} else {
					// Si el texto no está ampliado, aumenta el tamaño de la fuente
					// Cambia a 20 el tamaño de fuente ampliado que desees
					labelModo.setFont(new Font("System", 20));
					labelFuente.setFont(new Font("System", 20));
					labelDiseno.setFont(new Font("System", 20));
					labelIdioma.setFont(new Font("System", 20));
					labelNotificaciones.setFont(new Font("System", 20));
					labelApariencia.setFont(new Font("System", 20));
					labelAyuda.setFont(new Font("System", 20));
					labelActualizaciones.setFont(new Font("System", 20));
					labelNosotros.setFont(new Font("System", 20));
					labelVersion.setFont(new Font("System", 20));
					labelContactanos.setFont(new Font("System", 20));
					labelPerfil.setFont(new Font("System", 20));
					labelConfiguracion.setFont(new Font("System", 20));
					labelBuscar.setFont(new Font("System", 20));
					labelBuscarFecha.setFont(new Font("System", 20));
					labelAcercaDe.setFont(new Font("System", 20));
					labelCamNombre.setFont(new Font("System", 20));
					labelCamContr.setFont(new Font("System", 20));
					labelCamCorreo.setFont(new Font("System", 20));
					labelOpSesion.setFont(new Font("System", 20));
					labelCerrarSesion.setFont(new Font("System", 20));
					labelCambiarSesion.setFont(new Font("System", 20));
					// Cambia el color a negro de la fuente original que desees
					labelModo.setTextFill(Color.BLACK);
					labelFuente.setTextFill(Color.BLACK);
					labelDiseno.setTextFill(Color.BLACK);
					labelIdioma.setTextFill(Color.BLACK);
					labelNotificaciones.setTextFill(Color.BLACK);
					labelApariencia.setTextFill(Color.BLACK);
					labelAyuda.setTextFill(Color.BLACK);
					labelActualizaciones.setTextFill(Color.BLACK);
					labelNosotros.setTextFill(Color.BLACK);
					labelVersion.setTextFill(Color.BLACK);
					labelContactanos.setTextFill(Color.BLACK);
					labelPerfil.setTextFill(Color.BLACK);
					labelConfiguracion.setTextFill(Color.BLACK);
					labelBuscar.setTextFill(Color.BLACK);
					labelBuscarFecha.setTextFill(Color.BLACK);
					labelAcercaDe.setTextFill(Color.BLACK);
					labelCamNombre.setTextFill(Color.BLACK);
					labelCamContr.setTextFill(Color.BLACK);
					labelCamCorreo.setTextFill(Color.BLACK);
					labelOpSesion.setTextFill(Color.BLACK);
					labelCerrarSesion.setTextFill(Color.BLACK);
					labelCambiarSesion.setTextFill(Color.BLACK);
					isEnlarged[0] = true;
				}

				// Elimina el texto del CheckMenuItem
				iModo.setText("");
				iFuente.setText("");
				iDiseno.setText("");
				mIdioma.setText("");
				iNotificaciones.setText("");
				mApariencia.setText("");
				mAyuda.setText("");
				iActualizaciones.setText("");
				iNosotros.setText("");
				iVersion.setText("");
				iContactanos.setText("");
				mPerfil.setText("");
				mConfig.setText("");
				mBuscar.setText("");
				iBuscarFecha.setText("");
				mAcercaDe.setText("");
				iCambiarNombre.setText("");
				iCambiarPass.setText("");
				iCambiarCorreo.setText("");
				mOpcSesion.setText("");
				iCerrarSesion.setText("");
				iCambiarSesion.setText("");
				// Establece el label como el gráfico del CheckMenuItem
				iModo.setGraphic(labelModo);
				iFuente.setGraphic(labelFuente);
				iDiseno.setGraphic(labelDiseno);
				mIdioma.setGraphic(labelIdioma);
				iNotificaciones.setGraphic(labelNotificaciones);
				mApariencia.setGraphic(labelApariencia);
				mAyuda.setGraphic(labelAyuda);
				iActualizaciones.setGraphic(labelActualizaciones);
				iNosotros.setGraphic(labelNosotros);
				iVersion.setGraphic(labelVersion);
				iContactanos.setGraphic(labelContactanos);
				mPerfil.setGraphic(labelPerfil);
				mConfig.setGraphic(labelConfiguracion);
				mBuscar.setGraphic(labelBuscar);
				iBuscarFecha.setGraphic(labelBuscarFecha);
				mAcercaDe.setGraphic(labelBuscarFecha);
				iCambiarNombre.setGraphic(labelCamNombre);
				iCambiarPass.setGraphic(labelCamContr);
				iCambiarCorreo.setGraphic(labelCamCorreo);
				mOpcSesion.setGraphic(labelOpSesion);
				iCerrarSesion.setGraphic(labelCerrarSesion);
				iCambiarSesion.setGraphic(labelCambiarSesion);
			}
		});

		/************** ESCENA ****************/
		try {
			Image icon = new Image(new FileInputStream("img\\favicon.png"));
			stage1.getIcons().add(icon);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		var scene = new Scene(pnlDistribucion, 800, 600);

		ImageView imageView = new ImageView();
		iModo.setOnAction(event -> {
			// Verifica si iModo está seleccionado
			int estado = ((CheckMenuItem) iModo).isSelected() ? 1 : 0;
			// Actualiza la base de datos
			OpcionesDAO.cambiarModo(UsuarioDAO.cargarId(con, LoginController.correoUsuario).getId(), estado, con);
			// Obtiene las rutas de las imágenes basadas en el estado
			ArrayList<String> rutasCarpeta = CalendarioDAO.getCalendario(estado, con);
			// Cambia el color de fondo y la imagen basándose en el estado
			if (estado == 1) {
				// Cambia el color de fondo a negro
				scene.setFill(Color.BLACK);
				// Cambia la imagen a la versión oscura (la primera imagen en rutasCarpeta)
				try {
					imageView.setImage(new Image(new FileInputStream(rutasCarpeta.get(0))));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			} else {
				// Cambia el color de fondo a blanco
				scene.setFill(Color.WHITE);
				// Cambia la imagen a la versión clara (la primera imagen en rutasCarpeta)
				try {
					imageView.setImage(new Image(new FileInputStream(rutasCarpeta.get(0))));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			// Forzar a JavaFX a recalcular el diseño de la vista en el hilo de la interfaz
			// de usuario
			Platform.runLater(() -> pnlDistribucion.layout());
		});

		stage1.setScene(scene);

		stage1.show();

		stage1.setResizable(false);

		abrirVentanaFormulario(stage1, con, mesAnterior, mesPosterior, pnlDistribucion);
	}

	/**
	 * Función que abre una ventana emergente para poder subir una foto
	 * 
	 * @param stage
	 * @param con
	 */
	public static void abrirVentanaSubirImagen(Stage stage, Connection con) {
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
				visualizarCalendario(con, mesAnterior, mesPosterior, pnlDistribucion, stage);

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
				NotificacionesDAO.mostrarNotificacion(NotificacionesDAO.getNotificaciones(con, 3).getMensaje());
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

		if (ImagenDAO.getMarcados(con).size() > 0) {
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
				NotificacionesDAO.mostrarNotificacion(NotificacionesDAO.getNotificaciones(con, 3).getMensaje());
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
			NotificacionesDAO.mostrarNotificacion(NotificacionesDAO.getNotificaciones(con, 7).getMensaje());
		}

	}

	/**
	 * Función que carga la visualización del calendario, llamando también a la
	 * clase de los botones de cada día
	 * 
	 * @param con
	 * @param mesAnterior
	 * @param mesPosterior
	 * @param pnlDistribucion
	 * @param stage
	 */
	public static void visualizarCalendario(Connection con, Button mesAnterior, Button mesPosterior,
			BorderPane pnlDistribucion, Stage stage) {
		LocalDate fechaActual = LocalDate.now();
		DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM");
		String fechaFormateada = fechaActual.format(formatoFecha);
		PanelVisualizarCalendario pnlCalendario = new PanelVisualizarCalendario(stage, con, fechaFormateada);
		pnlDistribucion.setCenter(pnlCalendario);
		try {
			ArrayList<String> rutasCarpeta = CalendarioDAO.getCalendario(0, con);
			int[] indice = { 0 };

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

	/**
	 * Función que cierra la aplicación
	 * 
	 * @param stage
	 */
	public void cerrarSesion(Stage stage) {
		stage.close();

	}

	/**
	 * Función que abre el panel para cambiar la contraseña
	 * 
	 * @param primaryStage
	 */
	public static void cambiarContrasena(Stage primaryStage) {
		Connection con = UtilsBD.conectarBD();
		// Crear los elementos de la ventana modal
		Label lblNuevaContrasena = new Label("Nueva Contraseña:");
		Label lblConfirmarContrasena = new Label("Confirmar Contraseña:");
		PasswordField txtNuevaContrasena = new PasswordField();
		PasswordField txtConfirmarContrasena = new PasswordField();
		Button btnConfirmar = new Button("Confirmar");
		Button btnCerrar = new Button("Cerrar");

		// Configurar el diseño de la ventana modal
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10));

		grid.add(lblNuevaContrasena, 0, 0);
		grid.add(txtNuevaContrasena, 1, 0);
		grid.add(lblConfirmarContrasena, 0, 1);
		grid.add(txtConfirmarContrasena, 1, 1);

		HBox buttons = new HBox(10);
		buttons.getChildren().addAll(btnConfirmar, btnCerrar);

		VBox root = new VBox(10);
		root.getChildren().addAll(grid, buttons);

		// Crear la escena y configurar la ventana modal
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Cambiar Contraseña");
		stage.initModality(Modality.APPLICATION_MODAL); // Establecer modalidad
		stage.setResizable(false); // No permitir cambiar el tamaño de la ventana

		// Configurar acción para el botón Confirmar
		btnConfirmar.setOnAction(e -> {
			String nuevaContrasena = txtNuevaContrasena.getText();
			String confirmarContrasena = txtConfirmarContrasena.getText();
			// Aquí puedes realizar la validación de las contraseñas y realizar acciones
			// correspondientes
			// Por ejemplo, cerrar la ventana si las contraseñas coinciden
			if (nuevaContrasena.equals(confirmarContrasena)) {
				UsuarioDAO.actualizarContrasena(con, LoginController.correoUsuario, confirmarContrasena);
				stage.close(); // Cerrar la ventana modal
			} else {
				// Mostrar un mensaje de error o realizar otras acciones si las contraseñas no
				// coinciden
				System.out.println("Las contraseñas no coinciden");
			}
		});

		// Configurar acción para el botón Cerrar
		btnCerrar.setOnAction(e -> stage.close());

		// Mostrar la ventana modal
		stage.showAndWait();
	}

	/**
	 * Función que abre el panel para cambiar correo
	 * 
	 * @param primaryStage
	 */
	public static void cambiarCorreo(Stage primaryStage) {
		Connection con = UtilsBD.conectarBD();
		// Crear los elementos de la ventana modal
		Label lblNuevoCorreo = new Label("Nuevo Correo:");
		Label lblConfirmarCorreo = new Label("Confirmar Correo:");
		TextField txtNuevoCorreo = new TextField();
		TextField txtConfirmarCorreo = new TextField();
		Button btnConfirmar = new Button("Confirmar");
		Button btnCerrar = new Button("Cerrar");

		// Configurar el diseño de la ventana modal
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10));

		grid.add(lblNuevoCorreo, 0, 0);
		grid.add(txtNuevoCorreo, 1, 0);
		grid.add(lblConfirmarCorreo, 0, 1);
		grid.add(txtConfirmarCorreo, 1, 1);

		HBox buttons = new HBox(10);
		buttons.getChildren().addAll(btnConfirmar, btnCerrar);

		VBox root = new VBox(10);
		root.getChildren().addAll(grid, buttons);

		// Crear la escena y configurar la ventana modal
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Cambiar Correo");
		stage.initModality(Modality.APPLICATION_MODAL); // Establecer modalidad
		stage.setResizable(false); // No permitir cambiar el tamaño de la ventana

		// Configurar acción para el botón Confirmar
		btnConfirmar.setOnAction(e -> {
			String nuevoCorreo = txtNuevoCorreo.getText();
			String confirmarCorreo = txtConfirmarCorreo.getText();
			// Aquí puedes realizar la validación del correo y realizar acciones
			// correspondientes
			// Por ejemplo, cerrar la ventana si los correos coinciden
			if (nuevoCorreo.equals(confirmarCorreo)) {
				UsuarioDAO.actualizarCorreo(con, LoginController.correoUsuario, nuevoCorreo);
				stage.close(); // Cerrar la ventana modal
			} else {
				// Mostrar un mensaje de error o realizar otras acciones si los correos no
				// coinciden
				System.out.println("Los correos no coinciden");
			}
		});

		// Configurar acción para el botón Cerrar
		btnCerrar.setOnAction(e -> stage.close());

		// Mostrar la ventana modal
		stage.showAndWait();
	}

	/**
	 * Función que abre la cámara desde el cmd
	 */
	public static void abrirCamara() {
		Connection con = UtilsBD.conectarBD();

		try {
			ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "start microsoft.windows.camera:");
			processBuilder.start();
		} catch (IOException exp) {
			System.out.println("No se ha encontrado una cámara");
			NotificacionesDAO.mostrarNotificacion(NotificacionesDAO.getNotificaciones(con, 7).getMensaje());
		}
	}

	/**
	 * Función que cierra la cámara desde el cmd
	 */
	public static void cerrarCamara() {
		try {
			ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "taskkill /F /IM WindowsCamera.exe");
			processBuilder.start();
		} catch (IOException exp) {
			exp.printStackTrace();
		}
	}

	/**
	 * Función que monitoriza la carpeta de las imágenes de la cámara para copiarlas
	 * 
	 * @param con
	 * @param stage
	 */
	public static void monitoreoDeCarpeta(Connection con, Stage stage) {
		Path rutaOrigen = Paths.get(System.getProperty("user.home") + "/Pictures/Screenshots/Camera Roll");
		Path rutaDestino = Paths.get(System.getProperty("user.home") + "\\Pictures\\Instancias\\"
				+ UsuarioDAO.cargarCorreo(con, LoginController.correoUsuario).getId());

		UsuarioDO objUsuario = UsuarioDAO.cargarId(con, LoginController.correoUsuario);

		try {
			// Crear un servicio de ejecución para ejecutar el monitoreo en un hilo separado
			ExecutorService executorService = Executors.newSingleThreadExecutor();
			executorService.submit(() -> {
				try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
					rutaOrigen.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

					while (true) {
						WatchKey key;
						try {
							key = watchService.take();
						} catch (InterruptedException e) {
							break;
						}
						for (WatchEvent<?> event : key.pollEvents()) {
							// Obtener el tipo de evento
							WatchEvent.Kind<?> kind = event.kind();
							if (kind == StandardWatchEventKinds.OVERFLOW) {
								continue;
							}

							// Obtener el nombre del archivo creado
							Path nombreArchivo = (Path) event.context();

							try {
								Path rutaFile = rutaOrigen.resolve(nombreArchivo);

								long tamanoArchivo = Files.size(rutaFile);
								System.out.println(tamanoArchivo);

								if (tamanoArchivo > 1) {
									if (nombreArchivo.toString().endsWith(".jpg")) {
										String rutaArchivo = rutaOrigen.resolve(nombreArchivo).toString();

										File imagen = new File(rutaArchivo);

										ImagenDO objImagen = new ImagenDO(-1, "", "", "", objUsuario.getId(), 0);

										ImagenDAO.subirImagen(con, objImagen, imagen);
										ImagenDAO.copiarImagen(imagen, objImagen);
										executorService.shutdownNow();
									}
								}
							} catch (Exception e) {

							}
						}
						key.reset();
						cerrarCamara();
					}
				} catch (Exception exp) {
					NotificacionesDAO.mostrarNotificacion(NotificacionesDAO.getNotificaciones(con, 7).getMensaje());
					// exp.printStackTrace();
				}
			});

			stage.setOnCloseRequest(event -> {
				try {
					// Espera a que el ExecutorService termine
					executorService.shutdown();
					executorService.awaitTermination(5, TimeUnit.SECONDS); // Espera un máximo de 5 segundos
				} catch (InterruptedException e) {
					// Maneja la interrupción si es necesario
					e.printStackTrace();
				} finally {
					// Si el ExecutorService aún está en ejecución, forzar su cierre
					if (!executorService.isShutdown()) {
						executorService.shutdownNow();
					}
				}
			});

		} catch (Exception exp) {
			exp.printStackTrace();
		}

	}

	private void abrirHTML() {
		try {
			File file = new File("doc/index.html"); // Ruta a tu archivo HTML
			Desktop.getDesktop().browse(file.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch();
	}

}