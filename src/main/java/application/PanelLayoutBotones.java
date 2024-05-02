package application;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class PanelLayoutBotones extends GridPane {

	PanelLayoutBotones() {
		int x = 0;
		int y = 0;

		for (int i = 1; i <= 35; i++) {
			Button dia = new Button();
			dia.setPrefSize(136, 70);
			this.add(dia, x, y);

			dia.setStyle("-fx-background-color: transparent;");

			dia.setOnMouseEntered(e -> {
				dia.setStyle("-fx-background-color: lightblue;");
			});

			dia.setOnMouseExited(e -> {
				dia.setStyle("-fx-background-color: transparent;");
			});

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
