package application.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OpcionesDAO {

	/**
	 * La función activará y desactivará las notificaciones.
	 * 
	 * @param idUsuario
	 * @param activo
	 * @param con
	 * @return Numero de filas afectadas o -1
	 */
	public static int activarNotificaciones(int idUsuario, int activo, Connection con) {
		try {
			boolean campoPrevio = false;
			int numAff = -1;
			String query = "UPDATE OPCIONES SET ";

			// Si los campos no son nulos, los vamos añadiendo a la sentencia
			if (activo != -1) {
				if (activo == 1) {
					query = query + "Notificaciones = 1";
				} else if (activo == 0) {
					query = query + "Notificaciones = 0";
				}
				campoPrevio = true;
			}

			query = query + " WHERE Usuario_idUsuario = ?";

			// Generamos el preparedstatement con la query
			PreparedStatement pstmt = con.prepareStatement(query);

			// Enlazamos los datos a las ? del prepared statement
			int posInterrogacion = 1;

			pstmt.setInt(posInterrogacion, idUsuario);

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

	/**
	 * la función cambiará el modo (claro u oscuro) de la aplicación.
	 * 
	 * @param idUsuario
	 * @param modo
	 * @param con
	 * @return Filas afectadas o 0
	 */
	public static int cambiarModo(int idUsuario, int modo, Connection con) {
		try {
			boolean campoPrevio = false;
			int numAff = -1;
			String query = "UPDATE OPCIONES SET ";

			// Si los campos no son nulos, los vamos añadiendo a la sentencia
			if (modo != -1) {
				if (modo == 1) {
					query = query + "Modo = 1";
				} else if (modo == 0) {
					query = query + "Modo = 0";
				}
				campoPrevio = true;
			}
			query = query + " WHERE Usuario_idUsuario = ?";

			// Generamos el preparedstatement con la
			// query
			PreparedStatement pstmt = con.prepareStatement(query);

			// Enlazamos los datos a las ? del prepared
			// statement

			int posInterrogacion = 1;

			// Si los campos no son nulos, los vamos
			// añadiendo a la sentencia

			if (campoPrevio) {
				pstmt.setInt(posInterrogacion, idUsuario);
				// Incrementamos la posicion de la
				// interrogacion para
				// El siguiente posible campo
				posInterrogacion++;

			}

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

	/**
	 * La función cambiará el diseño de la aplicación.
	 * 
	 * @param diseno
	 * @param con
	 * @return Filas afectadas o -1
	 */
	public static int cambiarDiseno(OpcionesDO diseno, Connection con) {
		try {

			boolean campoPrevio = false;
			int numAff = -1;
			String query = "UPDATE OPCIONES SET ";

			// Si los campos no son nulos, los vamos añadiendo a la sentencia
			if (diseno.getDiseno() != -1) {
				if (diseno.getDiseno() == 0) {
					query = query + "Diseño = 'Predeterminado'";
				} else if (diseno.getDiseno() == 1) {
					query = query + "Diseño = 'ampliar'";
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

			if (diseno.getDiseno() != -1) {
				pstmt.setInt(posInterrogacion, diseno.getDiseno());
				// Incrementamos la posicion de la
				// interrogacion para
				// El siguiente posible campo
				posInterrogacion++;

			}

			pstmt.setInt(posInterrogacion, diseno.getIdOpciones());

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

	/**
	 * La función cambiará el idioma (español o inglés) de la aplicación.
	 * 
	 * @param idioma
	 * @param con
	 * @return Filas afectadas o 0
	 */

	public static int cambiarIdioma(OpcionesDO idioma, Connection con) {
		try {
			boolean campoPrevio = false;
			int numAff = -1;
			String query = "UPDATE OPCIONES SET ";
			// Si los campos no son nulos, los vamos añadiendo a la sentencia
			if (idioma.getIdioma() != -1) {
				if (idioma.getIdioma() == 1) {
					query = query + "Idioma = 1";
				} else if (idioma.getIdioma() == 0) {
					query = query + "Idioma = 0";
				}
				campoPrevio = true;
			}
			query = query + " WHERE Usuario_idUsuario = ?";
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

	/**
	 * Función que cambia el tamaño de la fuente en la BD
	 * 
	 * @param Fuente
	 * @param con
	 * @return Filas afectadas o 0
	 */
	public static int cambiarFuente(OpcionesDO Fuente, Connection con) {
		try {

			boolean campoPrevio = false;
			int numAff = -1;
			String query = "UPDATE OPCIONES SET ";

			// Si los campos no son nulos, los vamos añadiendo a la sentencia
			if (Fuente.getFuente() != -1) {
				if (Fuente.getFuente() == 0) {
					query = query + "Fuente = '100%'";
				} else if (Fuente.getFuente() == 1) {
					query = query + "Fuente = '50%'";
				} else if (Fuente.getFuente() == 2) {
					query = query + "Fuente = '200%'";
				}
				campoPrevio = true;
			}

			query = query + " WHERE Usuario_idUsuario = ?";

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

	/**
	 * Función que inserta opciones predeterminadas en la BD vinculadas a un usuario
	 * 
	 * @param id
	 * @param con
	 */
	public void crearOpcionesPredeterminadas(int id, Connection con) {

		// Inserta las opciones predeterminadas
		String sql = "INSERT INTO opciones (Idioma, Modo, Notificaciones, Fuente, Diseño, Usuario_idUsuario) VALUES (?, ?, ?, ?, ?, ?)";

		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, 0); // Idioma predeterminado
			pstmt.setInt(2, 0); // Modo predeterminado
			pstmt.setInt(3, 1); // Notificaciones predeterminadas
			pstmt.setInt(4, 0); // Fuente predeterminada
			pstmt.setInt(5, 0); // Diseño predeterminado
			pstmt.setInt(6, id); // ID del usuario

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static OpcionesDO getOpciones(int id, Connection con) {
		String query = "SELECT * FROM OPCIONES WHERE USUARIO_IDUSUARIO = ?";

		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				OpcionesDO opciones = new OpcionesDO();
				opciones.setIdOpciones(rs.getInt("idOpciones"));
				opciones.setIdioma(rs.getInt("Idioma"));
				opciones.setModo(rs.getInt("Modo"));
				opciones.setNotificaciones(rs.getInt("Notificaciones"));
				opciones.setFuente(rs.getInt("Fuente"));
				opciones.setDiseno(rs.getInt("Diseño"));
				opciones.setUsusario_idUsuario(rs.getInt("Usuario_idUsuario"));
				return opciones;
			}

			return null;
		} catch (Exception e) {
			return null;
		}

	}

}
