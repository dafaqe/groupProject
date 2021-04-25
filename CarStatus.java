import java.io.Serializable;

/**
  Peroslav Skarpa
  Lab07/HW06
  ISTE-121
 */

public class CarStatus implements Serializable {
   //Attributes
   private static final long serialVersionUID = 01L;
   private int racePosX=0;          // x position of the racer
   private int racePosY=0;        // y position of the racer
   private int raceROT=1;     
   
   //Constructor
   public CarStatus(int _racePosX, int _racePosY, int _raceROT){
      this.racePosX = _racePosX;
      this.racePosY = _racePosY;
      this.raceROT=_raceROT;
   }
   
   //getters
   public int getRacePosX() {
      return racePosX;
   }
   public int getRacePosY() {
      return racePosY;
   }
   public int getRaceROT() {
      return raceROT;
   }
    
    //setters
    public void setRacePosX(int _racePosX) {
      this.racePosX = _racePosX;
   }
   public void setRacePosY(int _racePosY) {
      this.racePosY = _racePosY;
   }
      public void setRaceROT(int _raceROT) {
      this.raceROT = _raceROT;
   }
    
   public String toString(){
      return "racePosX: "+this.racePosX + "\nracePosY: " + this.racePosY + "\racePosX2: " + "\raceROT: " + this.raceROT;
   }
}