package application.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import application.model.OpcionesDO;

public class OpcionesDAO {

	//Función activarNotificaciones: la función activará y desactivará las notificaciones.
	public static int activarNotificaciones(OpcionesDO activo, Connection con) {
			try {

				boolean campoPrevio = false;
				int numAff = -1;
				String query = "UPDATE OPCIONES SET ";

				// Si los campos no son nulos, los vamos añadiendo a la sentencia
				if (activo.getNotificaciones() != -1) {
					query = query + "Notificaciones = ?";
					campoPrevio = true;
				}
				
				query = query + " WHERE idOpciones = ?";

				// Generamos el preparedstatement con la
				// query
				PreparedStatement pstmt = con.prepareStatement(query);

				// Enlazamos los datos a las ? del prepared
				// statement

				int posInterrogacion = 1;

				// Si los campos no son nulos, los vamos
				// añadiendo a la sentencia

				if (activo.getNotificaciones() != -1) {
					pstmt.setInt(posInterrogacion, activo.getNotificaciones());
					// Incrementamos la posicion de la
					// interrogacion para
					// El siguiente posible campo
					posInterrogacion++;

				}

				pstmt.setInt(posInterrogacion, activo.getIdOpciones());

				if (campoPrevio) {

					System.out.println(query);

					numAff = pstmt.executeUpdate();

				}

				return numAff;
			} catch (SQLException e) {
				// TODO: handle exception
				e.printStackTrace();
				return 0;
			}
	}
	
	//Función cambiarModo: la función cambiará el modo (claro u oscuro) de la aplicación.
	public static int cambiarModo(OpcionesDO modo, Connection con){
		try {

			boolean campoPrevio = false;
			int numAff = -1;
			String query = "UPDATE OPCIONES SET ";

			// Si los campos no son nulos, los vamos añadiendo a la sentencia
			if (modo.getModo() != -1) {
				query = query + "Modo = ?";
				campoPrevio = true;
			}
			
			query = query + " WHERE idOpciones = ?";

			// Generamos el preparedstatement con la
			// query
			PreparedStatement pstmt = con.prepareStatement(query);

			// Enlazamos los datos a las ? del prepared
			// statement

			int posInterrogacion = 1;

			// Si los campos no son nulos, los vamos
			// añadiendo a la sentencia

			if (modo.getModo() != -1) {
				pstmt.setInt(posInterrogacion, modo.getModo());
				// Incrementamos la posicion de la
				// interrogacion para
				// El siguiente posible campo
				posInterrogacion++;

			}

			pstmt.setInt(posInterrogacion, modo.getIdOpciones());

			if (campoPrevio) {

				System.out.println(query);

				numAff = pstmt.executeUpdate();

			}

			return numAff;
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
}
	//Función cambiarDiseño: la función cambiará el diseño de la aplicación.
	public static int cambiarDiseño(OpcionesDO diseño, Connection con){
		try {

			boolean campoPrevio = false;
			int numAff = -1;
			String query = "UPDATE OPCIONES SET ";

			// Si los campos no son nulos, los vamos añadiendo a la sentencia
			if (diseño.getDiseño() != -1) {
				query = query + "Diseño = ?";
				campoPrevio = true;
			}
			
			query = query + " WHERE idOpciones = ?";

			// Generamos el preparedstatement con la
			// query
			PreparedStatement pstmt = con.prepareStatement(query);

			// Enlazamos los datos a las ? del prepared
			// statement

			int posInterrogacion = 1;

			// Si los campos no son nulos, los vamos
			// añadiendo a la sentencia

			if (diseño.getDiseño() != -1) {
				pstmt.setInt(posInterrogacion, diseño.getDiseño());
				// Incrementamos la posicion de la
				// interrogacion para
				// El siguiente posible campo
				posInterrogacion++;

			}

			pstmt.setInt(posInterrogacion, diseño.getIdOpciones());

			if (campoPrevio) {

				System.out.println(query);

				numAff = pstmt.executeUpdate();

			}

			return numAff;
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
}
	
	//Función cambiarIdioma: la función cambiará el idioma (español o inglés) de la aplicación.
	public static int cambiarIdioma(OpcionesDO idioma, Connection con){
		try {

			boolean campoPrevio = false;
			int numAff = -1;
			String query = "UPDATE OPCIONES SET ";

			// Si los campos no son nulos, los vamos añadiendo a la sentencia
			if (idioma.getIdioma() != -1) {
				query = query + "Idioma = ?";
				campoPrevio = true;
			}
			
			query = query + " WHERE idOpciones = ?";

			// Generamos el preparedstatement con la
			// query
			PreparedStatement pstmt = con.prepareStatement(query);

			// Enlazamos los datos a las ? del prepared
			// statement

			int posInterrogacion = 1;

			// Si los campos no son nulos, los vamos
			// añadiendo a la sentencia

			if (idioma.getIdioma() != -1) {
				pstmt.setInt(posInterrogacion, idioma.getIdioma());
				// Incrementamos la posicion de la
				// interrogacion para
				// El siguiente posible campo
				posInterrogacion++;

			}

			pstmt.setInt(posInterrogacion, idioma.getIdOpciones());

			if (campoPrevio) {

				System.out.println(query);

				numAff = pstmt.executeUpdate();

			}

			return numAff;
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
}

}
