import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TheadCreation extends Application implements Runnable{
	Parking p;
	
	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage window) throws Exception {
	p = new Parking();
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
