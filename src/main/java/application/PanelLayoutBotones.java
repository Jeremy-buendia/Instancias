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
			dia.setPrefSize(106, 68);

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

			if (y == 0) {
				this.setMargin(dia, new Insets(86, 3, 4, 0));
			} else if (y == 1) {
				this.setMargin(dia, new Insets(0, 3, 4, 0));
			} else if (y == 2) {
				this.setMargin(dia, new Insets(0, 3, 4, 0));
			} else if (y == 3) {
				this.setMargin(dia, new Insets(0, 3, 4, 0));
			} else if (y == 4) {
				this.setMargin(dia, new Insets(0, 3, 4, 0));
			}

			if (x == 0) {
				this.setMargin(dia, new Insets(0, 3, 3, 21));
			} else if (x == 1 && y != 0) {
				this.setMargin(dia, new Insets(0, 2, 3, 0));
			} else if (x == 2 && y != 0) {
				this.setMargin(dia, new Insets(0, 0, 3, 0));
			} else if (x == 3 && y != 0) {
				this.setMargin(dia, new Insets(0, 2, 3, 0));
			} else if (x == 4 && y != 0) {
				this.setMargin(dia, new Insets(0, 2, 3, 0));
			} else if (x == 5 && y != 0) {
				this.setMargin(dia, new Insets(0, 2, 3, 0));
			} else if (x == 6 && y != 0) {
				this.setMargin(dia, new Insets(0, 2, 3, 0));
			}

			if (x == 0 && y == 0) {
				this.setMargin(dia, new Insets(84, 0, 4, 21));
			}

			x++;
			if (x == 7) {
				y++;
				x = 0;
			}
		}

	}
}
