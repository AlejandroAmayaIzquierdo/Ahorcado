package dad.Puntuaciones;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.LocalDateTimeStringConverter;

public class PuntuacionesController implements Initializable {
	
	//Model
	
	
	private ListProperty<Puntuacion> puntuaciones = new SimpleListProperty<>(FXCollections.observableArrayList());
	
	
	//View

    @FXML
    private BorderPane view;
    
    @FXML
    private TableView<Puntuacion> tablePuntiaciones;
    @FXML
    private TableColumn<Puntuacion, String> nombreCollumn;
    @FXML
    private TableColumn<Puntuacion, LocalDateTime> fechaCollumn;
    @FXML
    private TableColumn<Puntuacion, Number> puntuacionCollumn;



	
	public PuntuacionesController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PuntuacionesView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException();
		}
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//Bindings
		
		tablePuntiaciones.itemsProperty().bind(puntuaciones);
		

		LoadPuntuaciones();
		
		
		//Cell Value Factor
		
		nombreCollumn.setCellValueFactory(v -> v.getValue().nombreProperty());
		fechaCollumn.setCellValueFactory(v -> v.getValue().fechaProperty());
		puntuacionCollumn.setCellValueFactory(v -> v.getValue().puntuacionProperty());
		
		// cell factory
		
		fechaCollumn.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateTimeStringConverter()));
		
	}
	public void LoadPuntuaciones() {
		try {
			puntuaciones.clear();
			List<String[]> p = readAllLines(Paths.get("puntuaciones.csv"));
			for(String[] i : p) {
				puntuaciones.add(new Puntuacion(i));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List<String[]> readAllLines(Path filePath) throws Exception {

	    try (Reader reader = Files.newBufferedReader(filePath)) {
	        try (CSVReader csvReader = new CSVReader(reader)) {
	            return csvReader.readAll();
	        }
	    }
	}
	
	public static void writePuntuacion(Path filePath,String[] puntuacion) throws Exception {
		FileWriter fileWritter = new FileWriter(filePath.toString(),true);
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	    try (Writer writer = bufferWritter) {
	        try (CSVWriter csvReader = new CSVWriter(writer)) {
	            csvReader.writeNext(puntuacion);
	        }
	    }
	}
	
	
	public BorderPane getView() {
		return this.view;
	}

}
