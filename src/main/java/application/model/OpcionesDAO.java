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
	            if (activo.getNotificaciones() == 1) {
	                query = query + "Notificaciones = 'activo'";
	            } else if (activo.getNotificaciones() == 0) {
	                query = query + "Notificaciones = 'desactivado'";
	            }
	            campoPrevio = true;
	        }

	        query = query + " WHERE idOpciones = ?";

	        // Generamos el preparedstatement con la query
	        PreparedStatement pstmt = con.prepareStatement(query);

	        // Enlazamos los datos a las ? del prepared statement
	        int posInterrogacion = 1;

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
	            if (modo.getModo() == 1) {
	                query = query + "Modo = 'oscuro'";
	            } else if (modo.getModo() == 0) {
	                query = query + "Modo = 'claro'";
	            }
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
	            if (diseño.getDiseño() == 0) {
	                query = query + "Diseño = 'Predeterminado'";
	            } else if (diseño.getDiseño() == 1) {
	                query = query + "Diseño = 'San Valentin'";
	            } else if (diseño.getDiseño() == 2) {
	                query = query + "Diseño = 'Verano'";
	            } else if (diseño.getDiseño() == 3) {
	                query = query + "Diseño = 'Primavera'";
	            } else if (diseño.getDiseño() == 4) {
	                query = query + "Diseño = 'Otoño'";
	            } else if (diseño.getDiseño() == 5) {
	                query = query + "Diseño = 'Invierno'";
	            } else if (diseño.getDiseño() == 6) {
	                query = query + "Diseño = 'Halloween'";
	            } else if (diseño.getDiseño() == 7) {
	                query = query + "Diseño = 'Navidad'";
	            }
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
	            if (idioma.getIdioma() == 1) {
	                query = query + "Idioma = 'Ingles'";
	            } else if (idioma.getIdioma() == 0) {
	                query = query + "Modo = 'Español'";
	            }
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
	public static int cambiarFuente(OpcionesDO Fuente, Connection con){
		try {

			boolean campoPrevio = false;
			int numAff = -1;
			String query = "UPDATE OPCIONES SET ";

			// Si los campos no son nulos, los vamos añadiendo a la sentencia
			if (Fuente.getFuente() != -1) {
	            if (Fuente.getFuente() == 0) {
	                query = query + "Idioma = 'Arial'";
	            } else if (Fuente.getFuente() == 1) {
	                query = query + "Modo = 'Calibri'";
	            } else if (Fuente.getFuente() == 2) {
	                query = query + "Modo = 'Gerogia'";
	            }
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

			if (Fuente.getFuente() != -1) {
				pstmt.setInt(posInterrogacion, Fuente.getFuente());
				// Incrementamos la posicion de la
				// interrogacion para
				// El siguiente posible campo
				posInterrogacion++;

			}

			pstmt.setInt(posInterrogacion, Fuente.getIdOpciones());

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
