package application;
	
import controllers.GameController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	
	@Override
	public void start(Stage primaryStage) {
		try {
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		GameController.run();
//		launch(args);
	}

}
