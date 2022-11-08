package dad.Partida;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import dad.Main.AhorcadoApp;
import dad.Palabras.PalabrasController;
import dad.Puntuaciones.PuntuacionesController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.converter.NumberStringConverter;
public class PartidaController implements Initializable {

	// Model
	
	static String  actualWord = "";

	private StringProperty palabra = new SimpleStringProperty();
	private IntegerProperty palabraSize = new SimpleIntegerProperty();

	private IntegerProperty conteoAciertos = new SimpleIntegerProperty(0);
	private IntegerProperty conteoFallos = new SimpleIntegerProperty(0);

	public Image ahorcadoImagenes = new Image(
			PartidaController.class.getResourceAsStream("/images/" + (conteoFallos.get() + 1) + ".png"));

	// View
	
    @FXML
    private Text puntuacion;
    
    @FXML
    private Text fallos;

    @FXML
    private ImageView ahorcadoImagen;

    @FXML
    private TextField inputUser;

    @FXML
    private Button letraButton;

    @FXML
    private HBox letrasOcultas;

    @FXML
    private HBox letrasUsadas;

    @FXML
    private Button resolverButton;

    @FXML
    private BorderPane view;

	public PartidaController() {
		// FXML
		try {
			FXMLLoader loader = new FXMLLoader(PartidaController.class.getResource("/fxml/PartidaView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}



	@Override
	public void initialize(URL location, ResourceBundle resources) {
		

		

		
//		puntuacion.textProperty().bind(conteoAciertos);

		// listeners
		
		palabra.addListener((o,ov,nv) -> {
			palabraSize.set(nv.length());
		});

		conteoFallos.addListener((o, ov, nv) -> {
			ahorcadoImagenes = new Image(
					PartidaController.class.getResourceAsStream("/images/" + (conteoFallos.get() + 1) + ".png"));
			ahorcadoImagen.setImage(ahorcadoImagenes);
		});
		
		
		//Bindings
		
		fallos.textProperty().bindBidirectional(conteoFallos, new NumberStringConverter());
		
		conteoAciertos.bind(palabraSize.subtract(conteoFallos));
		
		puntuacion.textProperty().bindBidirectional(conteoAciertos, new NumberStringConverter());
		
		
		palabra.set(getRandomWord());
		generateChildrens();

	}
	
	public String getRandomWord() {
		try {
			List<String> palabras = Files.readAllLines(PalabrasController.palabras_FILE.toPath(), StandardCharsets.UTF_8);
			Random ran = new Random();
			System.out.println(palabras);
			return palabras.get(ran.nextInt(palabras.size())).toLowerCase();
		} catch (IOException e) {
			return "";
		}

	}

	public void generateChildrens() {
		for (int i = 0; i < palabra.get().length(); i++) {
			Text toCreate = new Text(" ");
			toCreate.setUnderline(true);
			toCreate.setFont(Font.font("System", 24));
			letrasOcultas.getChildren().add(toCreate);
		}
	}

	public static Alert createAlert(AlertType at, String header, String text) {
		Alert alert = new Alert(at);
		alert.setHeaderText(header);
		alert.setContentText(text);
		alert.initOwner(AhorcadoApp.mainStage);
		return alert;

	}

	public void resetGame() {
		try {
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Nuevo nombre");
			dialog.setHeaderText("Añadir un nuevo nombre a la tabla de puntuaciones");
			dialog.setContentText("Nombre:");
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent()) {
				PuntuacionesController.writePuntuacion(Paths.get("puntuaciones.csv"), new String[] {
						result.get(),
						LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "",
						palabra.get().length() + ""});				
			}	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		letrasOcultas.getChildren().clear();
		letrasUsadas.getChildren().clear();
		conteoFallos.set(0);
		palabra.set(getRandomWord());

		generateChildrens();

		inputUser.setText("");
		inputUser.requestFocus();
	}
	


	@FXML
	void letraAction(ActionEvent event) {
		String input = inputUser.getText().toLowerCase();

//		Map<Character, List<Integer>> indices = IntStream.range(0, input.length()).boxed()
//				.collect(Collectors.groupingBy(input::charAt));
//
//		System.out.println(indices);
		


		if (input.length() <= 1 && !input.isBlank()) { // Letra
			
			Text toCreate = new Text(input);
			toCreate.setFont(Font.font("System", 24));
			letrasUsadas.getChildren().add(toCreate);
			
			if(palabra.get().contains(input)) {
				for(int i = 0; i < palabra.get().length(); i++) {
					if ((palabra.get().charAt(i) + "").equals(input)) { // acierto

						((Text) letrasOcultas.getChildren().get(i)).setText(input);
					}
				}
				actualWord = "";
				letrasOcultas.getChildren().forEach(c -> {
					actualWord += ((Text) c).getText();
				});
				System.out.println(actualWord);
				if(palabra.get().equals(actualWord)) {
					createAlert(AlertType.INFORMATION, "Correcto", "La palabra " + palabra.get() + " es correcta").showAndWait();
					resetGame();
				}
			}else {
				if (conteoFallos.get() < 8) {
					conteoFallos.set(conteoFallos.get() + 1);
				}else {
					createAlert(AlertType.ERROR, "Incorrecto", "has muerto, La palabra correcta era " + palabra.get()).showAndWait();
					resetGame();
				}
			}



		}
		inputUser.setText("");
		inputUser.requestFocus();
	}

	@FXML
	void resolverAction(ActionEvent event) {
		String input = inputUser.getText();
		if (input.length() > 1) {// Resolver
			if (palabra.get().equals(input)) { // Acierto
				createAlert(AlertType.INFORMATION, "Correcto", "La palabra " + input + " es correcta").showAndWait();

			} else {// Fallo
				createAlert(AlertType.ERROR, "Incorrecto", "La palabra " + input + " es incorrecta").showAndWait();
			}
			resetGame();
		}
	}


	public BorderPane getView() {
		return this.view;
	}

}
