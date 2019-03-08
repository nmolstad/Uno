package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	
	@Override
	public void start(Stage stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/MainMenu.fxml"));
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle("Uno");
			stage.getIcons().add(new Image("file:images/uno_logo.png"));
			stage.setResizable(false);
			stage.setWidth(1200);
			stage.setHeight(928);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
