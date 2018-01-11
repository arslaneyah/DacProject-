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
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Parking extends BorderPane 
{
	//Declaration
	 BorderPane main;
	 Path road;
	 MoveTo moveTo;
	 VBox box;
	 int distance [] = {240, 400, 560, 720};
	 boolean ishandicapped;
	 String carType;
	PathTransition pathTransition;
	Button pause, play;
	VBox buttons;
	Group parking;
	Scene scene;
	ImageView car;
	ImageView car2;
	VLineTo vline1, vline2;
	HLineTo hline1, hline2;
	
	Group creatParking()
	{
		//container of the road and the car
	    Group park = new Group();
	    box = new VBox(); 
	    box.setAlignment(Pos.CENTER_LEFT);
	    box.getChildren().add(park);
	    box.setPrefHeight(570);
	    //adding the parking to the interface
	    this.setLeft(box);
	    //creating the starting point 
	    moveTo = new MoveTo();
	    //creating the road
	    road = new Path();
	    road.setStrokeWidth(200);
	    road.getStyleClass().add("road");
	    return park;
	}
	void creatTransition(Path p, ImageView c )
	{
		road.setStrokeWidth(205);
		//hide the road stroke
		road.setStyle("-fx-opacity : 0;");
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
		if( buttons.getChildren().contains(pause))
			pathTransition.play();
		
	}
	Path setRoadElement(double h, double v)
	{
		//creating the road's lines
		HLineTo hl = new HLineTo(h);
		VLineTo vl = new VLineTo(v);
		//creating the road path
		Path p = new Path();
		p.getElements().clear();
		p.getElements().addAll(moveTo, hl, vl);
		if(v>0)
			box.setPadding(new Insets(200,0,0,0));
		else
			box.setPadding(new Insets(0,0,140,0));
		//specifying the starting point coordination
	    //by default it lined up in the center
		//we need to move it to the left by the half of the value of the hline and vline 
	    return p;
	}
	 ImageView creatCar(String type)
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
	void park(String type, double x, double y)
	{
		//container of the road and the car
		parking = creatParking();
		//creating the car 
		car = creatCar(type);
		
		//adding the element to the interface 
		road = setRoadElement(x, y);
		parking.getChildren().addAll(road,car);
		creatTransition(road, car);
	}
	public ParkTest()
    //public void start(Stage window) 
    {
    	//scene layout
    	main = new BorderPane();
    	this.setStyle("-fx-background-image : url('pictures/background.jpg');");
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
    	this.setBottom(buttons);
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
    	pathTransition.statusProperty().addListener(e ->
    	{
    		if( e.toString().contains("STOPPED"))
    		{
    			buttons.getChildren().clear();
    			buttons.getChildren().add(play);
    		}
    	});
      
    	// creating the scene 
    	scene = new Scene(this, 990, 640);
    }
}
