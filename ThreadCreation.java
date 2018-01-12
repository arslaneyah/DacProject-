import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.awt.Point ;
import java.util.Arrays.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
enum typecar{handicapped,normal}
enum typeclient{ordinary,abonned}


public class ThreadCreation extends Application implements Runnable{
	Semaphore semEnter =new Semaphore(1,true);
	Semaphore semExit =new Semaphore(1,true);
	Semaphore semParkH =new Semaphore(2,true);
	Semaphore semParkN =new Semaphore(8,true);
	String cartype ;
	static Point Pn1 =new Point(240,170);
	static Point Pn2 =new Point(240,-170);
	static Point Pn3 =new Point(400,170);
	static Point Pn4 =new Point(400,-170);
	static Point Pn5 =new Point(560,170);
	static Point Pn6 =new Point(560,-170);
	static Point Pn7 =new Point(720,170);
	static Point Pn8 =new Point(720,-170);
	static Point ph1=new Point(80,170);
	static Point ph2=new Point(80,-170);
	
	static Point tab1 []={Pn1,Pn2,Pn3,Pn4,Pn5,Pn6,Pn7,Pn8}; //tab des places normales
	static Point tab2 []={ph1,ph2};    //tab des places handicapées
	static ArrayList<Point> empty=new ArrayList<Point>(); // liste des places normales disponibles
	static ArrayList<Point> emptyH=new ArrayList<Point>(); // liste des places Handicapées disponibles

	
	
	Parking p;
	String typecl;
	
	public static void main(String[] args) {
		for(int i=0;i<tab1.length;i++)empty.add(tab1[i]); //toutes les places normales sont vides 
		for(int i=0;i<tab1.length;i++)emptyH.add(tab2[i]); // toutes les places handicapées sont vides 
		launch(args);
		Thread t1=new Thread();
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
		cartype=typecar.values()[new Random().nextInt(2)].name();
		
		try{
			semEnter.tryAcquire(10, TimeUnit.SECONDS);
		if (cartype=="normal"){   // test le type de voiture pour acceder au buffer correspondant 
						if(semParkN.availablePermits()>0){ // test si il ya des places disponibles
							semParkN.acquire(); // reserve une place
							Point plc=empty.get(new Random().nextInt(empty.size()));
							empty.remove(plc);
							p.park(cartype,plc.getX(),plc.getY());// gare la voiture
							semEnter.release(); 
							Thread.sleep(new Random().nextInt(10)*1000);
							semExit.tryAcquire(5, TimeUnit.MINUTES);
							Sortir();
							empty.add(plc);
							semParkN.release();
							semExit.release();
			
							}
						else {
							semExit.tryAcquire(5, TimeUnit.MINUTES);
							Sortir();
							semExit.release();
						}
								}
			
		else{
			if(semParkH.availablePermits()>0){
				semParkH.acquire();
				Point plc=emptyH.get(new Random().nextInt(emptyH.size()));
				emptyH.remove(plc);
				p.park(cartype,plc.getX(),plc.getY());
				semEnter.release();
				Thread.sleep(new Random().nextInt(10)*1000);
				semExit.tryAcquire(5, TimeUnit.MINUTES);
				Sortir();
				emptyH.add(plc);
				semParkH.release();
				semExit.release();

				}
			else {
				semExit.tryAcquire(5, TimeUnit.MINUTES);
				Sortir();
				semExit.release();
			}
					}
			
		
			
			
			
				
		}catch (InterruptedException e) {e.printStackTrace(); }
		
	
		
		}

	public void Sortir(){
		
	}
		
	}

