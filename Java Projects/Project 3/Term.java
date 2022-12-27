import java.util.*;
import java.io.*;

public class Term {
    //variable declarations
    private double coefficient;
    private double exponent;
    private double coefficientDuplicate;
    private int upperBound = -100;
    private int lowerBound = -100;

    //constructor
    Term(){
        coefficient = 0;
        exponent = 0;
    }
    //overloaded constructor
    Term(double c, double e){
        coefficient = c;
        exponent = e;
    }

    //mutators
    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }
    public void setExponent(double exponent) {
        this.exponent = exponent;
    }
    public void setCoefficientDuplicate(double coefficientDuplicate) {
        this.coefficientDuplicate = coefficientDuplicate;
    }
    public void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }
    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }

    //accessors
    public double getCoefficient() {
        return coefficient;
    }
    public double getExponent() {
        return exponent;
    }
    public double getCoefficientDuplicate() {
        return coefficientDuplicate;
    }
    public int getLowerBound() {
        return lowerBound;
    }
    public int getUpperBound() {
        return upperBound;
    }

    //function finds the integral
    public void integral(){
        //if not indefinite integral
        if(exponent == -1000.0){
            exponent = 1;
        }
        else{
            exponent++;
        }

        //store original coefficient value
        coefficientDuplicate = coefficient;
        //if not a fraction
        if(coefficient % exponent == 0){
            coefficient = coefficient/exponent;
        }
    }

    //determines if number is a fraction
    public boolean isFraction(){
        //if coefficient is not divisible by exponent
        if(coefficientDuplicate % exponent != 0){
            return true;
        }
        else{
            return false;
        }
    }

    //@Override
    public int compareTo(Term o) {
        //compares coefficients of two variables
        return (int) (this.exponent - o.exponent);
    }
}

