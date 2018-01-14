
import javafx.application.Application;
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
import java.util.Random;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class TempClass extends Application 
{
	//Declaration
	BorderPane main;
	Path [] road = new Path [16];
	MoveTo moveTo;
	VBox box;
	StackPane pane;
	int distance [] = {240, 400, 560, 720};
	boolean ishandicapped;
	String carType;
	PathTransition pathTransition;
	Button pause, play;
	VBox buttons;
	Group [] parking = new Group[16];
	Scene scene;
	ImageView car;
	ImageView car2;
	VLineTo vline1, vline2;
	HLineTo hline1, hline2;
	
	void creatTransition(Path p, ImageView c, int u)
	{
		//hide the road stroke
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
		//pathTransition.setCycleCount(Timeline.INDEFINITE);
		// start the animation 
		//if( buttons.getChildren().contains(pause))
			pathTransition.play();
		
	}
	Path setRoadElement(double h, double v, int o)
	{
	    //road.getStyleClass().add("road");
		moveTo = new MoveTo();
		//creating the road's lines
		HLineTo hl = new HLineTo(h);
		VLineTo vl = new VLineTo(v);
		//creating the road path
		road[o] = new Path();
		road[o].setStrokeWidth(200);
	    //road.getStyleClass().add("road");
		road[o].getElements().clear();
		road[o].getElements().addAll(moveTo, hl, vl);
		if(v>0)
			pane.setPadding(new Insets(200,0,0,0));
		else
			pane.setPadding(new Insets(0,0,140,0));
		//specifying the starting point coordination
	    //by default it lined up in the center
		//we need to move it to the left by the half of the value of the hline and vline
		return road[o];
	}
	static ImageView creatCar(String type)
	{
		Random r;
		ImageView car1;
		if(type.equals("normal"))
		{
			r = new Random();
			car1 = new ImageView(new Image("pictures/cars/car"+r.nextInt(5)+".png"));
		}
		else
		{
			r = new Random();
			car1 = new ImageView(new Image("pictures/cars/carh"+r.nextInt(2)+".png"));
		}
	    car1.setFitHeight(150);
	    car1.setPreserveRatio(true);
	    return car1;
	}
	void park(String type, double x, double y,int u)
	{
		//container of the road and the car
		//creating the car 
		parking [u] = new Group();
		car = creatCar(type);
		//adding the element to the interface 
		road[u] = setRoadElement(x, y, u);
		parking [u].getChildren().clear();
		parking [u].getChildren().addAll(road[u],car);
		pane.getChildren().add(parking [u]);
		creatTransition(road[u], car, u);
	}
    public static void main(String[] args) 
    {
    	//starting the window
       launch(args);
    }
    
    public void start(Stage window) 
    {
    	//scene layout
    	main = new BorderPane();
    	main.setStyle("-fx-background-image : url('pictures/background.jpg');");
    	
	    pane = new StackPane();
	    pane.setAlignment(Pos.CENTER_LEFT);
	    pane.setPrefHeight(570);
	    main.setLeft(pane);    	
    	
    	// pause/play Buttons 
    	pause = new Button("Pause");
    	play 	= new Button("Play");
    	pause.setPrefSize(100, 30);
    	play.setPrefSize(100, 30);
    	pause.setStyle("-fx-background-radius : 30;");
    	play.setStyle("-fx-background-radius : 30;");
    	//container of the Buttons
    	buttons = new VBox();
    	buttons.setAlignment(Pos.CENTER);
    	buttons.setPadding(new Insets(30,0,10,0));
    	buttons.getChildren().add(pause);
    	buttons.setStyle("-fx-background-color : #626262;");
    	main.setBottom(buttons);
    	Thread t1 = new Creation();
    	Thread t2 = new Creation();
    	Thread t3 = new Creation();
    	Thread t4 = new Creation();
    	try {
    		t1.start();
			t1.join();
			t2.start();
			t2.join();
			t3.start();
			t3.join();
			t4.start();
			
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	/*
    	if(new Random().nextInt(6) == 0)
    		carType = "handicapped";
    	else
    		carType = "normal";
    	if(carType.equals("normal"))
    	{
    		if(new Random().nextInt(2) == 0)
        		park("normal", distance[new Random().nextInt(4)] , -170);
        	else
        		park("normal", distance[new Random().nextInt(4)] , 170);
    	}
    	else
    	{
    		if(new Random().nextInt(2) == 0)
        		park("handicapped", 80 , -170);
        	else
        		park("handicapped", 80 , 170);
    	}
    	  */
    	// button listener
    	pause.setOnAction(e -> 
    	{
    		pathTransition.pause();
    		buttons.getChildren().clear();
    		buttons.getChildren().add(play);
    	});
      
    	play.setOnAction(e -> 
    	{
    		pathTransition.play();
    		buttons.getChildren().clear();
    		buttons.getChildren().add(pause);
      	});
      
    	// add the play button at the end of the transition 
    	
      
    	// creating the scene 
    	scene = new Scene(main, 990, 640);

    	//creating the window
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
    	public void call(int x)
    	{
    		if(new Random().nextInt(6) == 0)
        		carType = "handicapped";
        	else
        		carType = "normal";
        	if(carType.equals("normal"))
        	{
        		if(new Random().nextInt(2) == 0)
            		park("normal", distance[new Random().nextInt(4)] , -170, x);
            	else
            		park("normal", distance[new Random().nextInt(4)] , 170, x);
        	}
        	else
        	{
        		if(new Random().nextInt(2) == 0)
            		park("handicapped", 80 , -170, x);
            	else
            		park("handicapped", 80 , 170, x);
        	}
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
    	public void run() 
    	{
    		int i = Integer.parseInt(this.getName().substring(this.getName().length()-1, this.getName().length()));
    		System.out.println(i);
    		call(i);
    	}
    }
}
















