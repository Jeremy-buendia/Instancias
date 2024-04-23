package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

	@Override
	public void start(Stage stage) {
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

		// MenuItems y Submenús de mBuscar
		MenuItem iBuscarFecha = new MenuItem("Buscar Fecha");

		// Menú mConfig
		Menu mConfig = new Menu("Configuración");

		// MenuItems y Submenús de mConfig
		MenuItem iNotificaciones = new MenuItem("Notificaciones");

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

		pnlDistribucion.setTop(menuSuperior);

		/************** MENÚ INFERIOR ****************/

		var scene = new Scene(pnlDistribucion, 800, 600);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch();
	}

}