package application;

import java.sql.Connection;

import application.model.UsuarioDAO;
import application.model.UsuarioDO;
import application.utils.UtilsBD;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class PanelFormularioProv extends VBox {

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

		Connection con = UtilsBD.conectarBD();
		UsuarioDO usuario = new UsuarioDO(-1, nombre.getText(), apellido.getText(), correo.getText(),
				contraseña.getText());

		enviar.setOnAction(e -> {
			System.out.println(nombre.getText());
			System.out.println(apellido.getText());
			System.out.println(correo.getText());
			System.out.println(contraseña.getText());

			UsuarioDAO.crearUsuario(con, usuario);

		});
	}
}
