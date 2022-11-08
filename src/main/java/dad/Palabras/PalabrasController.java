package dad.Palabras;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.Main.AhorcadoApp;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;

public class PalabrasController implements Initializable {

	// Model
	
	public static File palabras_FILE = new File(AhorcadoApp.class.getResource("/text/palabras.txt").getPath());

	private ListProperty<String> palabras = new SimpleListProperty<String>(FXCollections.observableArrayList());
	private IntegerProperty selectedWord = new SimpleIntegerProperty();


	// View

	@FXML
	private BorderPane view;

	@FXML
	private ListView<String> palabrasList;

	@FXML
	private Button nuevoButton;

	@FXML
	private Button quitarButton;

	public PalabrasController() {
		// FXML

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PalabasView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	public void initialize(URL location, ResourceBundle resources) {
		
		//binding
		
		palabrasList.itemsProperty().bind(palabras);
		selectedWord.bind(palabrasList.getSelectionModel().selectedIndexProperty());
		
		//Files
		
		readWords();

	}
	
	public boolean readWords() {
		try {
			palabras.addAll(
					Files.readAllLines(
							palabras_FILE.toPath(), 
							StandardCharsets.UTF_8)
					);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void saveWords() throws IOException {
		StringBuffer contenido = new StringBuffer();
		palabras.forEach(palabra  -> {
			contenido.append(palabra + "\n");
		});
		Files.writeString(
				palabras_FILE.toPath(), 
				contenido.toString().trim(), 
				StandardCharsets.UTF_8, 
				StandardOpenOption.CREATE,
				StandardOpenOption.TRUNCATE_EXISTING
		);
	}

	@FXML
	void addAction(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Nuevo nombre");
		dialog.setHeaderText("Añadir un nuevo nombre a la lista");
		dialog.setContentText("Nombre:");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent() && !palabras.contains(result.get())) {
			palabras.add(result.get());
		}
	}

	@FXML
	void quitarAction(ActionEvent event) {
		palabras.remove(selectedWord.get());
	}

	public BorderPane getView() {
		return this.view;
	}
	
	public ListProperty<String> getPalabras(){
		return palabras;
	}

}
