package application.model;

import java.time.LocalDate;
import java.time.Month;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import javafx.scene.control.DatePicker;

public class CalendarioDAO {

	/**
	 * Función buscarFecha: la función buscará una fecha y abrirá una 
	 * ventana emergente con dicha fecha. Utilizaremos un datePicker.
	 * @return
	 */
	  public static int buscarFecha() {
		  DatePicker fecha= new DatePicker();
		  
		  /*
		   * Podemos establecer la fecha programáticamente usando el método setValue(LocalDate) 
		   * del mismo modo obtenemos  la fecha actual a través de getValue() 
		   * que devuelve un objeto LocalDate que puede ser nulo.
		   */
		  fecha.setValue(LocalDate.of(2000, Month.JANUARY, 1));    
		  LocalDate date = fecha.getValue();
		  
		  //Le da un formato el cuale ne la fecha se ve ejemplo: Lunes 22 de abril de 2024
		  DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
		  String formattedDate = ((ChronoLocalDate) fecha).format(formatter);

	    
		return -1;
	  }
}
