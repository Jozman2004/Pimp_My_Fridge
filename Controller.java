package Controller;

import java.io.IOException;

import Model.FridgeModel;
import View.View;



public class Controller  {
	
	private float temperatureconsigne ;
	private float lisntempint ;
	private float lisntempext;
	private float lisnthumidite;
	
	private String data;
	
	FridgeModel f; 
	View w;
	ArduinoConnector Ac;
	Thread t;
	
	public Controller()  {
	 f = new FridgeModel(this.lisntempint,this.lisntempext,this.temperatureconsigne,this.lisnthumidite,0);
	 w = new View(false,this.lisntempint,this.lisntempext,this.temperatureconsigne,this.lisnthumidite);
	 Ac  = new ArduinoConnector();
	 Ac.initialize();
	 t = new Thread(){
		 public void run() {
			//the following line will keep this app alive for 1000 seconds,
			//waiting for events to occur and responding to them (printing incoming messages to console).
			try {Thread.sleep(1000000);} catch (InterruptedException ie) {}
			}
			};
	 t.start();
	 System.out.println("Started");
	
	}
	public void run(){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		updateController();
		updateView();
		updateModel();
		//bouchonbullshitvalues();
		alert();
		this.temperatureconsigne = w.getTemperatureconsigne();
		throwconsigne(this.temperatureconsigne);
		
	}	
	public void updateModel(){	
		f.setTemperatureconsigne(f.getId(),temperatureconsigne);
		f.setTemperatureint(lisntempint);
		f.setTemperatureext(lisntempext);
		f.setHumidite(lisnthumidite);
	}
	public void updateView(){	
		w.setTemperatureint(f.getTemperatureint());
		w.setTemperatureext(f.getTemperatureext());
		w.setHumidite(f.getHumidite());
	}	
	public void alert(){
		if (lisntempint >= 25 || lisntempint <= 0){
			w.setStatut(true);
			w.update();
		}
		else if (lisnthumidite >= 50){
			w.setStatut(true);
			w.update();
		}
		else if (lisntempext >= 30 ||lisntempext <= -5){
			w.setStatut(true);
			w.update();
		}
		else {
			w.setStatut(false);
			w.update();
			
		}
	}
	public FridgeModel getFridgeModel() {
		return f;
	}
	public View getView() {
		return w;
	}
	public void setConsigne(float consigne) {
		this.temperatureconsigne = consigne;
	}
	/*public void bouchonbullshitvalues(){
		lisntemp++;
		lisntempext++;
		lisnthygro++;
		
	}*/
public void updateController(){
		this.lisntempint = Float.parseFloat(Ac.gettempint()) ;
		this.lisnthumidite = Float.parseFloat(Ac.gethumidite());
		this.lisntempext = Float.parseFloat(Ac.gettempext());
		
	}
public void throwconsigne(float temperatureconsigne){
	
	if (lisntempint > temperatureconsigne){
	try {
		Ac.getstream().write(1);
		System.out.println("lolololololo");
	} catch (IOException e) {
		e.printStackTrace();
	}
    }else{
	  try {
		Ac.getstream().write(0);
		System.out.println("prout");
	} catch (IOException e) {
		e.printStackTrace();
	}
	  
}
}
}