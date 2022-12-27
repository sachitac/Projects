//Sachita Chaliki sxc200091

import java.util.*;
import java.io.*;

public abstract class Creature {
    char c = ' ';
    int timesCharMoved = 0;

    int survivalRate = 0;

    boolean breed = false;
    Creature()
    {
        c = ' ';
    }

    //Creature[][] creature = super.battleGround;
    abstract void Breed ();

    abstract boolean getBreed();
    abstract char Move (int north, int south, int east, int west, int neighbors[]);
    //abstract char Move(int array[]);

    abstract char getChar();

    abstract void setTimesMoved(int num);
    abstract int getTimesMoved();

    abstract int getSurvivalRate();
    abstract void setSurvivalRate(int n);
    abstract int Starve(boolean eat);
}
