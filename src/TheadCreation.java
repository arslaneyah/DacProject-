import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TheadCreation extends Application implements Runnable{
	ParkTest p;
	
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage window) throws Exception {
		p = new ParkTest();
		window.setTitle("parking");
      	window.setScene(p.scene);
      	window.setResizable(false);
      	window.show();
	}
	@Override
	public void run() {
		// code here
	}
}
