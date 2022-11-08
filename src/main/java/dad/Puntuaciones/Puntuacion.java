package dad.Puntuaciones;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Puntuacion {
	
	private StringProperty nombre = new SimpleStringProperty();
	private ObjectProperty<LocalDate> fecha = new SimpleObjectProperty<>();
	private IntegerProperty puntuacion = new SimpleIntegerProperty();
	
	public Puntuacion(String[] puntuacion) {
		setNombre(puntuacion[0]);
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		setFecha(LocalDate.parse(puntuacion[1],df));
		setPuntuacion(Integer.parseInt(puntuacion[2]));
	}
	
	
	public final StringProperty nombreProperty() {
		return this.nombre;
	}
	
	public final String getNombre() {
		return this.nombreProperty().get();
	}
	
	public final void setNombre(final String nombre) {
		this.nombreProperty().set(nombre);
	}
	
	public final ObjectProperty<LocalDate> fechaProperty() {
		return this.fecha;
	}
	
	public final LocalDate getFecha() {
		return this.fechaProperty().get();
	}
	
	public final void setFecha(final LocalDate fecha) {
		this.fechaProperty().set(fecha);
	}
	
	public final IntegerProperty puntuacionProperty() {
		return this.puntuacion;
	}
	
	public final int getPuntuacion() {
		return this.puntuacionProperty().get();
	}
	
	public final void setPuntuacion(final int puntuacion) {
		this.puntuacionProperty().set(puntuacion);
	}
	
	

}
