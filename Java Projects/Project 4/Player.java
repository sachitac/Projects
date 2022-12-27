//Sachita Chaliki sxc200091

import java.util.*;
import java.io.*;
public class Player {

    //variables to hold each stat
    private double hit = 0;
    private double out = 0;
    private double strikeout = 0;
    private double HBP = 0;
    private double sacrifice = 0;
    private double walk = 0;

    private double error = 0;
    private String name = "";

    //default constructor
    public Player(String n) {
        name = n;
        hit = 0;
        out = 0;
        strikeout = 0;
        HBP = 0;
        sacrifice = 0;
        error = 0;
    }

    //overloaded constructor
    Player(String n, double h,double o,double so,double hbp,double s, double w){
        name = n;
        hit = h;
        out = o;
        strikeout = so;
        HBP = hbp;
        sacrifice = s;
        walk = w;
    }

    //setters
    public void setHits(double n){
        hit = n;
    }
    public void setOuts(double n){
        out = n;
    }
    public void setStrikeouts(double n){
        strikeout = n;
    }
    public void setHBPs(double n){
        HBP = n;
    }
    public void setSacrifices(double n){
        sacrifice = n;
    }
    public void setWalks(double n){
        walk = n;
    }
    public void setName(String s){
        name = s;
    }

    public void setError(double error) {
        this.error = error;
    }

    //accessors
    public double getHits(){
        return hit;
    }
    public double getOuts(){
        return out;
    }
    public double getStrikeouts(){
        return strikeout;
    }
    public double getHBPs(){
        return HBP;
    }
    public double getSacrifices(){
        return sacrifice;
    }
    public double getWalks(){
        return walk;
    }
    public String getName(){
        return name;
    }

    public double getError() {
        return error;
    }

    //calculating batting average
    public double calcBattingAvg(){

        double BA = 0;

        if ((hit + out + strikeout) != 0)
        {
            //calculating batting average if divisor is not zero
            BA = (hit / (hit + out + strikeout + error));
        }
        else
        {
            //if divisor equals zero, set batting average to zero
            BA = 0.0;
        }

        return BA; //return calculated stat
    }

    //calculating OB%
    public double calcOBPercentage(){
        double OB = 0.0;
        if ((hit + out+ strikeout + walk + HBP + sacrifice) != 0)
        {
            //calculating on bat percentage if divisor is not zero
            OB = (hit + walk + HBP) / (hit + out + strikeout + walk + HBP + sacrifice);
        }
        else
        {
            //if divisor equals zero, set on bat percentage to zero
            OB = 0.0;
        }
        return OB; //return calculated stat
    }

    public void integral(){
        double integral;

    }

    //calculating At Bats
    public double calcAtBats(){
        return (hit + out + strikeout + error);
    }
}
