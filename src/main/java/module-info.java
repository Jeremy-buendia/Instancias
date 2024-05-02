module Instancias.javafx_controls {
	requires javafx.controls;
	requires java.sql;
	requires javafx.fxml;
	requires mysql.connector.j;
	requires javafx.graphics;
	requires java.desktop;
	 opens application to javafx.fxml;
	exports application;
}
