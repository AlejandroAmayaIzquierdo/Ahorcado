package dad.Main;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import dad.Ahorcado.RootController;
import javafx.application.Application;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AhorcadoApp extends Application {
	
	public static Stage mainStage;
	
	private RootController rootController;

	@Override
	public void start(Stage stage) throws Exception {
		
		this.mainStage = stage;
		
		rootController = new RootController();
		
		stage.setTitle("Ahorcado");
		stage.setScene(new Scene(rootController.getView()));
		stage.show();
		
	}
	
	@Override
	public void stop() throws Exception {
		
		rootController.getPalabrasController().saveWords();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
