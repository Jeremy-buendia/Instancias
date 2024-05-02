package application;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class PanelLayoutBotones extends GridPane {

	// Meter parametro de fecha, alomejor el array no sé
	PanelLayoutBotones() {
		int x = 0;
		int y = 0;

		for (int i = 1; i <= 35; i++) {
			Button dia = new Button();
			dia.setPrefSize(136, 70);

			dia.setStyle("-fx-background-color: transparent;");

			dia.setOnMouseEntered(e -> {
				dia.setStyle("-fx-background-color: lightblue;");
			});

			dia.setOnMouseExited(e -> {
				dia.setStyle("-fx-background-color: transparent;");
			});

			this.add(dia, x, y);

			dia.setOnAction(e -> {
				System.out.println("illo");
			});

			x++;
			if (x == 7) {
				y++;
				x = 0;
			}
		}

	}
}
