#include <Adafruit_Sensor.h>
#include <math.h>
#include "ArduinoJson.h"
#include "DHT.h"

#define DHTIN 8 //pin où est branché le capteur DHT22 interne
#define DHTOUT 9 //pin où est branché le capteur DHT11 externe 
#define DHTTYPEIN DHT22 //Type du capteur DHT22 interne
#define DHTTYPEOUT DHT11 //Type du capteur DHT11 externe


//Alimentation electrique
double V_IN = 5.0;

//temperature de consigne
double target = -18;

//Initialisation des capteurs
DHT dht_outside(DHTOUT, DHTTYPEIN);
DHT dht_inside(DHTIN, DHTTYPEOUT);


void setup() {
  pinMode(12,OUTPUT); //pin du transistor MOFSET
  Serial.begin(9600);
  dht_outside.begin();
  dht_inside.begin();

}

void loop() {
  //Receive data from Java
  ReceiveData();
  //Send data to Java app:
  SendData();
  //Regulation function !!
  regulTemp();
  
}

void SendData(){
      StaticJsonDocument<200> doc;

  float h = dht_inside.readHumidity();

  float t = dht_inside.readTemperature();

  float outside = dht_outside.readTemperature();

  if(isnan(h) || isnan(t)){
    Serial.println("Failed to read from inside temp DHT sensor!");
    return;
  }
  if(isnan(outside)){
    Serial.println("Failed to read from outside DHT sensor!");
    return;
  }

  
  float hic = dht_inside.computeHeatIndex(t, h, false);

  float temperature = t;
  float humidite = h/100;

  float alpha = ((17.27 * temperature)/(237.7 + temperature)) + log(humidite);
  float rose = (237.7 * alpha) / (17.27 - alpha);



  doc["temperature interne"] = t;
  doc["temperature externe"] = outside;
  doc["humidité"] = h;
  doc["temperature de rosée"] = rose;
  

  serializeJson(doc, Serial);
  //Serial.println("temperature interne",t);
  //Serial.println("temperature externe",outside);
  //Serial.println("humidité",h);
  //Serial.println("temperature de rosée",rose);  
  
  //delay(1000);

  }

  void ReceiveData(){
     String json = Serial.readString();
      //StaticJsonBuffer<200> jsonBufferReader;
      DynamicJsonDocument doc(1024);
      //JsonObject object = jsonBufferReader.parseObject(reader);
      DeserializationError error = deserializeJson(doc, json);
      if (error){
        Serial.println("Echec de reception de la consigne");
        
      }
      else{
        target = doc["consigne"];  
      }
  }

  void regulTemp(){
    float t = dht_inside.readTemperature();
    if (target >= t){
      digitalWrite(12, LOW);//allume le module peltier
    } 
    else {
      digitalWrite(12, HIGH); //eteind le module peltier  
    }
    
  }  
