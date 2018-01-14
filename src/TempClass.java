
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.HLineTo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Parking extends Application {
	// Declaration
	BorderPane main;
	Path[] road = new Path[100];
	MoveTo moveTo;
	StackPane pane, roads;
	int distance[] = { 240, 400, 560, 720 };
	int[][] placeOrdinaire = { { 240, 170, 0 }, { 400, 170, 0 }, { 560, 170, 0 }, { 700, 170, 0 }, { 240, -170, 0 },
			{ 400, -170, 0 }, { 560, -170, 0 }, { 700, -170, 0 } };
	int[][] placeHandicape = { { 80, 170, 0 }, { 80, -170, 0 } };
	List<int[]> listePlaceHandicape = new ArrayList<int[]>();
	List<int[]> listePlaceOrdinaire = new ArrayList<int[]>();
	String carType;
	PathTransition pathTransition;
	Button pause, play;
	VBox buttons;
	Group[] parking = new Group[100];
	Scene scene;
	ImageView car;
	VLineTo vline1, vline2;
	HLineTo hline1, hline2;

	Semaphore semEnter = new Semaphore(1, true);
	Semaphore semExit = new Semaphore(1, true);
	Semaphore semParkH = new Semaphore(2, true);
	Semaphore semParkN = new Semaphore(8, true);

	void sortieDirect(String type, int u) {
		// container of the road and the car
		// creating the car
		parking[u] = new Group();
		car = creatCar(type);

		// road.getStyleClass().add("road");
		moveTo = new MoveTo();

		// creating the road's lines
		HLineTo hl = new HLineTo(1000);
		// creating the road path
		road[u] = new Path();
		road[u].setStrokeWidth(200);
		road[u].getElements().clear();
		road[u].getElements().addAll(moveTo, hl);
		parking[u].getChildren().clear();
		parking[u].getChildren().addAll(road[u], car);
		// parking [u].setTranslateX(-160);
		roads.getChildren().add(parking[u]);
		creatTransition(road[u], car, u);
	}

	void creatTransition(Path p, ImageView c, int u) {
		// hide the road stroke
		road[u].setStyle("-fx-opacity : 0;");
		// creating he transition ( animation )
		pathTransition = new PathTransition();
		// animation speed
		pathTransition.setDuration(Duration.millis(10000));
		// the moving element
		pathTransition.setNode(c);
		// the path to move in
		pathTransition.setPath(p);
		// change the node orientation according to the path
		pathTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
		// looping
		// pathTransition.setCycleCount(Timeline.INDEFINITE);
		// start the animation
		// if( buttons.getChildren().contains(pause))
		pathTransition.play();

	}

	Path setRoadElement(double h, double v, int o) {
		// road.getStyleClass().add("road");
		moveTo = new MoveTo();

		// creating the road's lines
		HLineTo hl = new HLineTo(h);
		VLineTo vl = new VLineTo(v);
		// creating the road path
		road[o] = new Path();
		road[o].setStrokeWidth(200);
		// road.getStyleClass().add("road");
		road[o].getElements().clear();
		road[o].getElements().addAll(moveTo, hl, vl);

		return road[o];
	}

	static ImageView creatCar(String type) {
		Random r;
		ImageView car1;
		if (type.equals("normal")) {
			r = new Random();
			car1 = new ImageView(new Image("pictures/cars/car" + r.nextInt(5) + ".png"));
		} else {
			r = new Random();
			car1 = new ImageView(new Image("pictures/cars/carh" + r.nextInt(2) + ".png"));
		}
		car1.setFitHeight(150);
		car1.setPreserveRatio(true);
		return car1;
	}

	void garer(String type, double x, double y, int u) {
		// container of the road and the car
		// creating the car
		parking[u] = new Group();
		car = creatCar(type);
		// adding the element to the interface
		road[u] = setRoadElement(x, y, u);
		parking[u].getChildren().clear();
		parking[u].getChildren().addAll(road[u], car);
		parking[u].setTranslateY(y / 2);
		// 720 ok
		if (x == 560) // ok
			parking[u].setTranslateX(-80);
		else if (x == 400) // ok
			parking[u].setTranslateX(-160);
		else if (x == 240) // ok
			parking[u].setTranslateX(-240);
		else if (x == 80) // ok
			parking[u].setTranslateX(-320);
		pane.getChildren().add(parking[u]);
		creatTransition(road[u], car, u);
	}

	public static void main(String[] args) {
		// starting the window
		launch(args);
	}

	public void start(Stage window) {
		listePlaceHandicape = new ArrayList<int[]>(Arrays.asList(placeHandicape));
		listePlaceOrdinaire = new ArrayList<int[]>(Arrays.asList(placeOrdinaire));
		// scene layout
		main = new BorderPane();
		main.setStyle("-fx-background-image : url('pictures/background.jpg');");

		pane = new StackPane();
		// pane.setAlignment(Pos.CENTER_LEFT);
		pane.setPrefHeight(570);
		roads = new StackPane();
		roads.getChildren().add(pane);
		main.setLeft(roads);
		// pause/play Buttons
		pause = new Button("Pause");
		play = new Button("Play");
		pause.setPrefSize(100, 30);
		play.setPrefSize(100, 30);
		pause.setStyle("-fx-background-radius : 30;");
		play.setStyle("-fx-background-radius : 30;");
		// container of the Buttons
		buttons = new VBox();
		buttons.setAlignment(Pos.CENTER);
		buttons.setPadding(new Insets(30, 0, 10, 0));
		buttons.getChildren().add(pause);
		buttons.setStyle("-fx-background-color : #626262;");
		main.setBottom(buttons);
		Thread t1 = new Creation();
		Thread t2 = new Creation();
		Thread t3 = new Creation();
		Thread t4 = new Creation();
		Thread t5 = new Creation();
		Thread t6 = new Creation();
		Thread t7 = new Creation();
		Thread t8 = new Creation();
		Thread t9 = new Creation();
		Thread t10 = new Creation();
		Thread t11 = new Creation();

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
		t7.start();
		t8.start();
		t9.start();
		t10.start();
		t11.start();

		// button listener
		pause.setOnAction(e -> {
			pathTransition.pause();
			buttons.getChildren().clear();
			buttons.getChildren().add(play);
		});

		play.setOnAction(e -> {
			pathTransition.play();
			buttons.getChildren().clear();
			buttons.getChildren().add(pause);
		});

		// creating the scene
		scene = new Scene(main, 990, 640);

		// creating the window
		window.setTitle("parking");
		window.setScene(scene);
		window.setResizable(false);
		window.show();
	}

	class Creation extends Thread {

		public Creation() {

		}

		public void call(int x) {
			if (new Random().nextInt(7) == 0)
				carType = "handicapped";
			else
				carType = "normal";
			if (carType.equals("normal")) 
			{
				
				if (semParkN.availablePermits() > 0)
				{
					
					try
					{
						semParkN.acquire();
						if (semEnter.availablePermits() > 0) 
						{
							semEnter.acquire();
							int r1 = new Random().nextInt(listePlaceOrdinaire.size());
							garer("normal", listePlaceOrdinaire.get(r1)[0], listePlaceOrdinaire.get(r1)[1], x);
							listePlaceOrdinaire.remove(r1);
							Thread.sleep(10000);
							
							semEnter.release();
						}
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					} finally {
						semEnter.release();
					}
				} 
				else
				{
					// sortieDirect("normal", x);
				}

			} 
			else
			{
				if (semParkH.availablePermits() > 0)
				{
					try
					{
						semParkH.acquire();
						if (semEnter.availablePermits() > 0) 
						{
							semEnter.acquire();
							int r1 = new Random().nextInt(listePlaceHandicape.size());
							garer("handicapped", listePlaceHandicape.get(r1)[0], listePlaceHandicape.get(r1)[1], x);
							listePlaceHandicape.remove(r1);
							//Thread.sleep(10000);
							semEnter.release();
						}
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					} finally {
						semEnter.release();
					}
				} 
				else
				{
					// sortieDirect("normal", x);
				}
			}
			// add the play button at the end of the transition
			
			pathTransition.statusProperty().addListener(e ->
			{

				if( e.toString().contains("STOPPED"))
				{
					buttons.getChildren().clear();
					buttons.getChildren().add(play); 
				}
			 });
			 

		}

		@Override
		public void run() {
			int index;
			// storing the thread index
			if (this.getName().length() == 8)
				index = Integer.parseInt(this.getName().substring(this.getName().length() - 1, this.getName().length()));
			else
				index = Integer.parseInt(this.getName().substring(this.getName().length() - 2, this.getName().length()));
			Platform.runLater(new Runnable()
			{
                @Override public void run() 
                {
                	
        			call(index);
                }
            });
			
		}

	}

}
