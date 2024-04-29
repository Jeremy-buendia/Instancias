package application;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class PanelFormularioProv extends VBox {

	public static String correoUsuario;

	TextField nombre;
	TextField apellido;
	TextField correo;
	TextField contraseña;
	Button enviar;

	PanelFormularioProv() {
		nombre = new TextField();
		apellido = new TextField();
		correo = new TextField();
		contraseña = new TextField();
		enviar = new Button("Entrar");

		this.getChildren().addAll(nombre, apellido, correo, contraseña, enviar);

	}

}
