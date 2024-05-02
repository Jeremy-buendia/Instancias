package application;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class PanelLayoutBotones extends GridPane {

	// Meter parametro de fecha, alomejor el array no sé
	PanelLayoutBotones() {
		int x = 0;
		int y = 0;

		for (int i = 1; i <= 35; i++) {
			Button dia = new Button();
			dia.setPrefSize(125, 80);

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

			this.setMargin(dia, new Insets(0, 0, 0, 0));

			if (y == 0) {
				this.setMargin(dia, new Insets(85, 0, 0, 0));
			} else if (y == 4) {
				this.setMargin(dia, new Insets(0, 0, 100, 0));
			}

//			if (y == 0) {
//				this.setMargin(dia, new Insets(10, 20, 10, 20));
//			}else if (y == 6) {
//				this.setMargin(dia, new Insets(10, 20, 10, 20));
//			}

			x++;
			if (x == 7) {
				y++;
				x = 0;
			}
		}

	}
}
