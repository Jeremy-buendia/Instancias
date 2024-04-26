package application.model;

import java.time.LocalDate;
import java.time.Month;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javax.swing.*;
import java.time.LocalDate;
import java.util.Optional;

import javafx.scene.control.DatePicker;

public class CalendarioDAO {

	/**
	 * Función buscarFecha: la función buscará una fecha y abrirá una 
	 * ventana emergente con dicha fecha. Utilizaremos un datePicker.
	 * @return
	 */

	    public static int buscarFecha() {
	    	
	    	
	    	// ONclickEvent
	        // Crear el DatePicker
	        DatePicker datePicker = new DatePicker();

	        // Mostrar el DatePicker en una ventana emergente
	        Optional<LocalDate> fecha = Optional.empty();

	        // Comprobar si se ha seleccionado una fecha
	        if (fecha.isPresent()) {
	            // Mostrar la fecha en una ventana emergente
	            JOptionPane.showMessageDialog(null, "Fecha seleccionada: " + fecha.get());
	            return 0; // Se ha encontrado la fecha
	        } else {
	            return -1; // No se ha encontrado la fecha
	        }
	    }
}

	 //public static int buscarFecha() {
		  // DatePicker fecha= new DatePicker();
		  
		  /*
		   * Podemos establecer la fecha programáticamente usando el método setValue(LocalDate) 
		   * del mismo modo obtenemos  la fecha actual a través de getValue() 
		   * que devuelve un objeto LocalDate que puede ser nulo.
		   */
		  // fecha.setValue(LocalDate.of(2000, Month.JANUARY, 1));    
		  // LocalDate dia = fecha.getValue();
		  
		  //Le da un formato el cuale ne la fecha se ve ejemplo: Lunes 22 de abril de 2024
		  // DateTimeFormatter formato = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
		  // String formatoDia = ((ChronoLocalDate) fecha).format(formato);

	    
		//return -1;}