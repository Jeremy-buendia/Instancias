package application.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UsuarioDAO {

	// HAY QUE CREAR UNA FUNCION getUsuario()
	/**
	 * Funcion que nos muestra al informacion del usuario con la id introducida
	 * @param con
	 * @param idUsuario
	 * @return los datos del usuario
	 */
	public static UsuarioDO cargarId(Connection con, int idUsuario)  {

		try {
			// Creamos la sentencia a ejecutar
			String query = "SELECT * FROM Usuario WHERE idUsuario=?";

			// Primer paso creo un statement
			PreparedStatement pstmt = con.prepareStatement(query);

			// Asignamos el valor del idUsuario a la
			// interrogacion
			pstmt.setInt(1, idUsuario);
			ResultSet rs = pstmt.executeQuery();
			// Crear un objeto usuarioDO y agregarlo a la lista
			if (rs.next()) {
			UsuarioDO usuario = new UsuarioDO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
					rs.getString(5));
			// devolvemos el resulset
			return usuario;
			}
			
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;

		}
		

	}


/**
 * Funcion que nos muestra al informacion del usuario con el correo associado
 * @param con
 * @param correo
 * @return los datos del usuario
 */
public static UsuarioDO cargarCorreo(Connection con, String correo)  {

	try {
		// Creamos la sentencia a ejecutar
		String query = "SELECT * FROM Usuario WHERE correo=?";

		// Primer paso creo un statement
		PreparedStatement pstmt = con.prepareStatement(query);

		// Asignamos el valor del correo a la
		// interrogacion
		pstmt.setString(1, correo);
		ResultSet rs = pstmt.executeQuery();
		// Crear un objeto usuarioDO y agregarlo a la lista
		if (rs.next()) {
		UsuarioDO usuario = new UsuarioDO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
				rs.getString(5));
		// devolvemos el resulset
		return usuario;
		}
		
		return null;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;

	}
	

}

/**
 * Funcion que elemina el usuario de la id introducida
 * @param con
 * @param idUsuario
 * @return 0 en exito -1 en fallo
 * @throws SQLException
 */
public static int removeUsuario(Connection con, int idUsuario) throws SQLException {

	try {
		// Creamos la sentencia a ejecutar
		String query = "DELETE FROM usuario WHERE idusuario=?";

		// Primer paso creo un statement
		PreparedStatement pstmt = con.prepareStatement(query);

		// Asignamos el valor del idCliente a la
		// interrogacion
		pstmt.setInt(1, idUsuario);

		 pstmt.executeUpdate();
		// devolvemos el resulset
		return 0;

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		con.rollback();
		return -1;

	}
}

/**
 * Funcion que crea el usuario y lo añade a la base de datos
 * @param con
 * @param usuarioDO
 * @return0 ecito -1 si hay fallo
 */
public static int crearUsuario(Connection con, UsuarioDO usuario) {
    try {
  
                // Si existe, insertamos sin el campo ID
                String queryInsert = "INSERT INTO usuario (nombre, apellido, correo, contraseña) VALUES(?,?,?,?)";
                PreparedStatement pstmt = con.prepareStatement(queryInsert);
                pstmt.setString(1, usuario.getNombre());
                pstmt.setString(2, usuario.getApellido());
                pstmt.setString(3, usuario.getCorreo());
                pstmt.setString(4, usuario.getContraseña());
   
             pstmt.executeUpdate();
                return 0;
           
       
    } catch (SQLException e) {
        e.printStackTrace();
        return -1; // Devolvemos -1 si hay un error SQL
    }
    
}

}
