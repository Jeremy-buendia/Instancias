package application.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

public class NotificacionesDAO {

	/**
	 * LA mostarnotificacion2 no es modal y permite actuar sobre las otra sin problam. pero se va directamente detras del la aplicacion
	 * por lo que es dificil de notar.
	 * la segunda se ve claramente y permanece delante, pero no podemos actuar sin cerrarla.
	 * ambas se cierran solas y podemos ajustar el tiempo en la funcion.
	 * pongo las dos para que decidais cual es mejor usar.
	 * @param mensaje
	 */
	
	
	public static void mostrarNotificacion2(String mensaje) {
        Stage stage = new Stage();
        stage.setResizable(false);

        Label label = new Label(mensaje);
        label.setStyle("-fx-font-size: 14;");
        VBox root = new VBox(label);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);

        Scene scene = new Scene(root, 250, 100);
        stage.setScene(scene);
        stage.setTitle("Notificación");

        // Configurar el temporizador para cerrar la ventana después de 5 segundos
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(e -> stage.close());
        delay.play();

        stage.show();
    }
	
	
	 public static void mostrarNotificacion(String mensaje) {
	        Stage stage = new Stage();
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.setResizable(false);

	        Label label = new Label(mensaje);
	        label.setStyle("-fx-font-size: 14;");
	        VBox root = new VBox(label);
	        root.setAlignment(Pos.CENTER);
	        root.setSpacing(10);

	        Scene scene = new Scene(root, 100, 100);
	        stage.setScene(scene);
	        stage.setTitle("Notificación");

	        // Configurar el temporizador para cerrar la ventana después de 5 segundos
	        PauseTransition delay = new PauseTransition(Duration.seconds(3));
	        delay.setOnFinished(e -> stage.close());
	        delay.play();

	        stage.show();
	    }
}