package application.model;

import java.time.LocalDate;
import java.time.Month;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javax.swing.*;
import java.time.LocalDate;
import java.util.Optional;

import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;

public class CalendarioDAO {

	    /**
	     * Función buscarFecha: la función buscará una fecha.
	     * @return
	     */
	    public static LocalDate buscarFecha() {
	        // Crear el DatePicker
	        DatePicker datePicker = new DatePicker();

	        // Crear una nueva ventana de diálogo
	        Dialog<LocalDate> dialog = new Dialog<>();
	        dialog.setTitle("Seleccionar Fecha");
	        dialog.getDialogPane().setContent(datePicker);

	        // Configurar el botón de confirmación
	        ButtonType buttonTypeOk = new ButtonType("Confirmar", ButtonData.OK_DONE);
	        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

	        // Manejar el evento de confirmación
	        dialog.setResultConverter(b -> {
	            if (b == buttonTypeOk) {
	                return datePicker.getValue();
	            }
	            return null;
	        });

	        // Mostrar el diálogo y obtener la fecha seleccionada
	        Optional<LocalDate> fecha = dialog.showAndWait();

	        // Devolver la fecha
	        return fecha.orElse(null);
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