package dad.Ahorcado;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.Palabras.PalabrasController;
import dad.Partida.PartidaController;
import dad.Puntuaciones.PuntuacionesController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class RootController implements Initializable {
	
	//Controllers
	
	private PartidaController partidaController = new PartidaController();
	
	private PalabrasController palabrasController = new PalabrasController();
	
	private PuntuacionesController puntuacionesController = new PuntuacionesController();
	
	
	
	
	//View
	
	@FXML
	private TabPane view;
    @FXML
    private Tab palabrasTab;
    @FXML
    private Tab partidaTab;
    @FXML
    private Tab puntuacionesTab;
	
	
	
	public RootController() {
		//FXML
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/View.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException();
		}

	}

	public void initialize(URL location, ResourceBundle resources) {
		partidaTab.setContent(partidaController.getView());
		palabrasTab.setContent(palabrasController.getView());
		puntuacionesTab.setContent(puntuacionesController.getView());
		
		
		view.getSelectionModel().selectedIndexProperty().addListener((o,ov,nv) ->{
			if(nv.intValue() == 2) {
				puntuacionesController.LoadPuntuaciones();
			}
		});
		
		
	}
	
	public TabPane getView() {
		return this.view;
	}
	
	public PalabrasController getPalabrasController() {
		return palabrasController;
	}
	
	
	
	
	

}
