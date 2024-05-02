package application;

import java.awt.event.ActionEvent;
import java.beans.EventHandler;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;

import application.model.CalendarioDAO;
import application.model.ImagenDAO;
import application.model.ImagenDO;
import application.model.OpcionesDAO;
import application.model.OpcionesDO;
import application.model.UsuarioDAO;
import application.model.UsuarioDO;
import application.utils.UtilsBD;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
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
			} else {
				System.out.println("No se seleccionó ninguna fecha.");
			}
		});

		// mBuscar.getItems().add(iBuscarFecha);

		// Menú mConfig
		Menu mConfig = new Menu("Configuración");

		// CheckMenuItem para Notificaciones
		MenuItem iNotificaciones = new CheckMenuItem("Notificaciones");
		
	
		iNotificaciones.setOnAction(event -> {
		    // Obtén el estado actual del CheckMenuItem
		    int estado = ((CheckMenuItem) iNotificaciones).isSelected() ? 1 : 0;

		    // Actualiza el objeto 'activo' con el nuevo estado
		    activo.setNotificaciones(estado);

		    // Llama a la función para actualizar la base de datos
		    int numAff = funcion(); // Asegúrate de que 'funcion' es el nombre de tu método de actualización

		    // Comprueba si la actualización fue exitosa
		    if (numAff > 0) {
		        System.out.println("La base de datos se ha actualizado correctamente.");
		    } else {
		        System.out.println("Hubo un problema al actualizar la base de datos.");
		    }
		});


		Menu mIdioma = new Menu("Perfil");

		MenuItem iEspañol = new MenuItem("Español");
		MenuItem iIngles = new MenuItem("Inglés");

		Menu mApariencia = new Menu("Apariencia");

		MenuItem iModo = new MenuItem("Modo");
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
		mIdioma.getItems().addAll(iEspañol, iIngles);
		mApariencia.getItems().addAll(iModo, iFuente, iDiseño);
		mAcercaDe.getItems().addAll(iVersion, iNosotros, iActualizaciones);

		// Agregamos los menús al MenuBar
		menuSuperior.getMenus().addAll(mPerfil, mBuscar, mConfig, mAyuda);

		// Añadir un controlador de eventos al elemento de menú
		pnlDistribucion.setTop(menuSuperior);

		/************** MENÚ INFERIOR ****************/
		ToolBar menuInferior = new ToolBar();

		Button marcados = new Button("Marcados");
		Button subirFoto = new Button("Subir foto");

		subirFoto.setOnAction(e -> {
			abrirVentanaSubirImagen(stage, con);
		});

		Button bajarFoto = new Button("Descargar foto");

		bajarFoto.setOnAction(e -> {
			abrirVentanaVisualizarImg(stage, con);
		});

		Button abrirCamara = new Button("Abrir la cámara");

		menuInferior.getItems().addAll(marcados, subirFoto, bajarFoto, abrirCamara);

		pnlDistribucion.setBottom(menuInferior);

		/************** CALENDARIO ****************/

		/************** ESCENA ****************/
//		Image icon = new Image("C:\\1º DAW\\[PROGRAMACIÓN]\\Proyectos\\Instancias\\img\\favicon.ico");
//
//		stage.getIcons().add(icon);

		var scene = new Scene(pnlDistribucion, 800, 600);
		stage.setScene(scene);
		stage.show();

		abrirVentanaFormulario(stage, con);
	}

	private int funcion() {
		// TODO Auto-generated method stub
		return 0;
	}

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
		pnlSubirImg.btnEscogerImg.setOnAction(e -> {
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.png", "*.jpg", "*.jpeg");
			pnlSubirImg.escogerImagen.getExtensionFilters().add(extFilter);
			pnlSubirImg.escogerImagen.setTitle("Selecciona una imagen");

			File imagen = pnlSubirImg.escogerImagen.showOpenDialog(null);

			int marcado;

			if (pnlSubirImg.cbxMarcar.isSelected()) {
				marcado = 1;
			} else {
				marcado = 0;
			}

			ImagenDO objImagen;
			objImagen = new ImagenDO(-1, pnlSubirImg.txtDescripcionImg.getText(), "", "",
					UsuarioDAO.cargarId(con, PanelFormularioProv.correoUsuario).getId(), marcado);
			ImagenDAO.subirImagen(con, objImagen);
			ImagenDAO.copiarImagen(imagen, objImagen);

			ventanaEmergente.close();
		});
	}

	public void abrirVentanaFormulario(Stage stage, Connection con) {
		Stage ventanaEmergente = new Stage();
		PanelFormularioProv pnlForm = new PanelFormularioProv();

		Scene scene = new Scene(pnlForm, 300, 300);

		// Bloqueamos la ventana padre definiendo cual es el padre y poner la modalidad
		ventanaEmergente.initOwner(stage);
		ventanaEmergente.initModality(Modality.WINDOW_MODAL);

		ventanaEmergente.setScene(scene);
		ventanaEmergente.setTitle("Entrar");
		ventanaEmergente.show();

		ventanaEmergente.setOnCloseRequest(e -> {
			if (!cerrarVentana) {
				stage.close();
			}
		});

		pnlForm.enviar.setOnAction(e -> {

			UsuarioDO usuario = new UsuarioDO(-1, pnlForm.nombre.getText(), pnlForm.apellido.getText(),
					pnlForm.correo.getText(), pnlForm.contraseña.getText());

			UsuarioDAO.crearUsuario(con, usuario);
			PanelFormularioProv.correoUsuario = pnlForm.correo.getText();
			ventanaEmergente.close();
		});
		;
	}

	public void abrirVentanaVisualizarImg(Stage stage, Connection con) {
		Stage ventanaEmergente = new Stage();
		PanelVisualizarImagen pnlVisualizarImg = new PanelVisualizarImagen();

		Scene scene = new Scene(pnlVisualizarImg, 300, 300);

		LocalDate fechaDia = CalendarioDAO.buscarFecha();
		System.out.println(fechaDia);

//		ArrayList<String> rutasCarpeta = new ArrayList<>();
//		try {
//			while (ImagenDAO.getDia(con, fechaDia).next()) {
//				rutasCarpeta.add(ImagenDAO.getDia(con, fechaDia).getString("ubicacion"));
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		ventanaEmergente.setScene(scene);
		ventanaEmergente.setTitle("Imagen");
		ventanaEmergente.show();

		stage.setOnCloseRequest(e -> {
			ventanaEmergente.close();
		});
	}

	public static void main(String[] args) {
		launch();
	}

}