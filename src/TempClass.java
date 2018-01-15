
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
import javafx.animation.SequentialTransition;
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
	int d [][] = new int [1][1];
	int r1 = 0;
	int[][] placeOrdinaire = { { 240, 170, 0 }, { 400, 170, 0 }, { 560, 170, 0 }, { 700, 170, 0 }, { 240, -170, 0 },
			{ 400, -170, 0 }, { 560, -170, 0 }, { 700, -170, 0 } };
	int[][] placeHandicape = { { 80, 170, 0 }, { 80, -170, 0 } };
	List <int[]> listePlaceHandicape = new ArrayList<int[]>();
	List <int[]> listePlaceOrdinaire = new ArrayList<int[]>();
	String carType;
	PathTransition pathTransition;
	Button pause, play;
	VBox buttons;
	Group[] parking = new Group[100];
	Scene scene;
	ImageView car;
	VLineTo vline1, vline2;
	HLineTo hline1, hline2;
	Semaphore mutexEnter = new Semaphore(1, true);
	Semaphore mutexExit = new Semaphore(1, true);
	Semaphore semParkH = new Semaphore(2, true);
	Semaphore semParkN = new Semaphore(8, true);
	SequentialTransition sequentialTransition = new SequentialTransition(); 

	void sortieDirect(String type, int index)
	{
		
		// container of the road and the car
		// creating the car
		parking[index] = new Group();
		// road.getStyleClass().add("road");
		moveTo = new MoveTo(0,0);
		// creating the road's lines
		HLineTo hl = new HLineTo(1000);
		// creating the road path
		road[index] = new Path();
		road[index].setStrokeWidth(200);
		road[index].getElements().clear();
		road[index].getElements().addAll(moveTo, hl);
		parking[index].getChildren().clear();
		parking[index].getChildren().addAll(road[index], car);
		// parking [index].setTranslateX(-160);
		roads.getChildren().add(parking[index]);
		creatTransition(road[index], car, index);
	}

	void creatTransition(Path p, ImageView car, int index) {
		// hide the road stroke
		road[index].setStyle("-fx-opacity : 0;");
		// creating he transition ( animation )
		pathTransition = new PathTransition();
		// animation speed
		pathTransition.setDuration(Duration.millis(10000));
		// the moving element
		pathTransition.setNode(car);
		// the path to move in
		pathTransition.setPath(p);
		// change the node orientation according to the path
		pathTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
		// looping
		// pathTransition.setCycleCount(Timeline.INDEFINITE);
		// start the animation
		// if( buttons.getChildren().contains(pause))
		//pathTransition.play();
		sequentialTransition.getChildren().add(pathTransition);
		//sequentialTransition.play();
		sequentialTransition.playFromStart();
	}

	Path creatRoad(double h, double v, int o) {
		moveTo = new MoveTo(0, 0);
		// creating the road's lines
		HLineTo hl = new HLineTo(h);
		VLineTo vl = new VLineTo(v);
		// creating the road path
		road[o] = new Path();
		road[o].setStrokeWidth(200);
		road[o].getElements().clear();
		road[o].getElements().addAll(moveTo, hl, vl);

		return road[o];
	}

	ImageView creatCar(String type) 
	{
		Random r;
		if (type.equals("normal")) {
			r = new Random();
			car = new ImageView(new Image("pictures/cars/car" + r.nextInt(5) + ".png"));
		} else {
			r = new Random();
			car = new ImageView(new Image("pictures/cars/carh" + r.nextInt(2) + ".png"));
		}
		car.setFitHeight(150);
		car.setPreserveRatio(true);
		return car;
	}

	void garer(String type, double x, double y, int index) {
		// container of the road and the car
		// creating the car
		parking[index] = new Group();
		car = creatCar(type);
		// adding the element to the interface
		road[index] = creatRoad(x, y, index);
		parking[index].getChildren().clear();
		parking[index].getChildren().addAll(road[index], car);
		parking[index].setTranslateY(y / 2);
		// 720 ok
		/*if (x == 560) // ok
			parking[index].setTranslateX(-80);
		else if (x == 400) // ok
			parking[index].setTranslateX(-160);
		else if (x == 240) // ok
			parking[index].setTranslateX(-240);
		else if (x == 80) // ok
			parking[index].setTranslateX(-320);*/
		pane.getChildren().add(parking[index]);
		creatTransition(road[index], car, index);
		
	}
	void sortie(String type, double x, double y, int index) 
	{
		// container of the road and the car
		// creating the car
		parking[index] = new Group();
		// adding the element to the interface
		
		
		moveTo = new MoveTo();
		// creating the road's lines
		HLineTo hl = new HLineTo(990-x);
		VLineTo vl = new VLineTo(-y);
		// creating the road path
		road[index] = new Path();
		road[index].setStrokeWidth(200);
		road[index].getElements().clear();
		road[index].getElements().addAll(moveTo, vl, hl);
		
		
		parking[index].getChildren().clear();
		parking[index].getChildren().addAll(road[index], car);
		parking[index].setTranslateY(y / 2);
		parking[index].setTranslateX(x/2);
		// 720 ok
		/*if (x == 560) // ok
			parking[index].setTranslateX(-80);
		else if (x == 400) // ok
			parking[index].setTranslateX(-160);
		else if (x == 240) // ok
			parking[index].setTranslateX(-240);
		else if (x == 80) // ok
			parking[index].setTranslateX(-320);*/
		pane.getChildren().add(parking[index]);
		

		creatTransition(road[index], car, index);
	}

	public static void main(String[] args) 
	{
		// starting the window
		launch(args);
	}

	public void start(Stage window) 
	{
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
		buttons.setPadding(new Insets(20, 0, 20, 0));
		buttons.getChildren().add(pause);
		buttons.setStyle("-fx-background-color : #626262;");
		main.setBottom(buttons);
		/*Thread t1 = new Creation();
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
		t11.start();*/
		for(int i=0; i<20; i++)
			new Creation().start();

		// button listener
		pause.setOnAction(e -> {
			sequentialTransition.pause();
			buttons.getChildren().clear();
			buttons.getChildren().add(play);
		});

		play.setOnAction(e -> {
			sequentialTransition.play();
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

	class Creation extends Thread 
	{

		public Creation()
		{

		}

		public void call(int index)
		{
			
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
						if (mutexEnter.availablePermits() > 0) 
						{
							mutexEnter.acquire();
							semParkN.acquire();
							r1 = new Random().nextInt(semParkN.availablePermits());
							garer("normal", listePlaceOrdinaire.get(r1)[0], listePlaceOrdinaire.get(r1)[1], index);
							listePlaceOrdinaire.remove(r1);
							//Thread.sleep(1000);
							mutexEnter.release();
							mutexExit.acquire();
							new java.util.Timer().schedule( 
							        new java.util.TimerTask() {
							            @Override
							            public void run() {
							            	//sequentialTransition.getChildren().remove(pathTransition);
							            	
							            	Platform.runLater(new Runnable()
							    			{
							                    @Override 
							                    public void run() 
							                    {
							                    	
							                    	sortie("normal", listePlaceOrdinaire.get(r1)[0], listePlaceOrdinaire.get(r1)[1] ,index);
							                    	sequentialTransition.getChildren().remove(pathTransition);
							                    	int r[] = new int [2];
													r[0] = listePlaceOrdinaire.get(r1)[0]; 
													r[0] = listePlaceOrdinaire.get(r1)[1]; 
													r[0] = 0; 
													listePlaceHandicape.add(r);
							                    	semParkN.release();
							                    }
							                });
							            }
							        }, 
							        10000 
							);
							mutexExit.release();
							//sortie("normal", listePlaceOrdinaire.get(r1)[0], listePlaceOrdinaire.get(r1)[1] ,index);
						}
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					} finally {
						mutexEnter.release();
					}
				} 
				else
				{
					// sortieDirect("normal", index);
				}
			} 
			else
			{
				if (semParkH.availablePermits() > 0)
				{
					try
					{
						semParkH.acquire();
						if (mutexEnter.availablePermits() > 0) 
						{
							mutexEnter.acquire();
							r1 = new Random().nextInt(listePlaceHandicape.size());
							garer("handicapped", listePlaceHandicape.get(r1)[0], listePlaceHandicape.get(r1)[1], index);
							listePlaceHandicape.remove(r1);
							//Thread.sleep(1000);
							mutexEnter.release();
							mutexExit.acquire();
							new java.util.Timer().schedule( 
							        new java.util.TimerTask() {
							            @Override
							            public void run() {
							            	//sequentialTransition.getChildren().remove(pathTransition);
							            	
							            	Platform.runLater(new Runnable()
							    			{
							                    @Override 
							                    public void run() 
							                    {
							                    	
							                    	sortie("handicapped", listePlaceHandicape.get(r1)[0], listePlaceHandicape.get(r1)[1] ,index);
							                    	sequentialTransition.getChildren().remove(pathTransition);
							                    	int r[] = new int [2];
													r[0] = listePlaceOrdinaire.get(r1)[0]; 
													r[0] = listePlaceOrdinaire.get(r1)[1]; 
													r[0] = 0; 
													listePlaceHandicape.add(r);
							                    	semParkH.release();
							                    }
							                });
							            }
							        }, 
							        10000 
							);
							mutexExit.release();
						}
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					} finally {
						mutexEnter.release();
					}
				} 
				else
				{
					// sortieDirect("normal", index);
				}
			}
			// add the play button at the end of the transition
			
			sequentialTransition.statusProperty().addListener(e ->
			{
				int y = new Random().nextInt(listePlaceHandicape.size());

				if( e.toString().contains("STOPPED"))
				{
					buttons.getChildren().clear();
					buttons.getChildren().add(play); 
				}
			 });
		}

		@Override
		public void run() 
		{
			int index;
			// storing the thread index
			if (this.getName().length() == 8)
				index = Integer.parseInt(this.getName().substring(this.getName().length() - 1, this.getName().length()));
			else
				index = Integer.parseInt(this.getName().substring(this.getName().length() - 2, this.getName().length()));
			Platform.runLater(new Runnable()
			{
                @Override 
                public void run() 
                {
        			call(index);
                }
            });
		}
	}
}
