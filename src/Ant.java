//Sachita Chaliki sxc200091

import java.lang.Math;
public class Ant extends Creature {
    Ant() {
        c = 'a';
    }

    //accessor for ant character
    char getChar() {
        return c;
    }

    int getTimesMoved(){
        return timesCharMoved;
    }
    boolean getBreed(){
        return breed;
    }
    int getSurvivalRate() {
        return survivalRate;
    }

    void setTimesMoved(int num) {
        timesCharMoved = num;
    }
    void setSurvivalRate(int n){
        survivalRate = n;
    }

    void Breed () {
        survivalRate++;
    }

    int Starve(boolean b){
        return 0;
    }

    private char tiedLargestVal(int beetle[], int smallestPosition){
        String str = "";
        char c = 'X';
        int numberofNoBeetle = -1;
        int position = -1;

        for(int i = 0; i < beetle.length; i++){
            if(beetle[i] > -1 && i != smallestPosition && beetle[i] == beetle[smallestPosition]){
                str = str + String.valueOf(i);
            }
        }

        int ch = smallestPosition;
        if(str.length() != 0){
            ch = str.charAt(0);
        }

        if(ch < smallestPosition){
            position = ch;
        }
        else {
            position = smallestPosition;
        }

        if(position == -1){
            c = 'X';
        }
        else if(position == 0){
            c = 'N';
        }
        else if(position == 1){
            c = 'E';
        }
        else if(position == 2){
            c = 'S';
        }
        else if(position == 3){
            c = 'W';
        }
        else {
            c = 'X';
        }

        return c;

    }
    private char tiedValues(int beetle[], int smallestPosition){
        String str = "";
        char c = 'X';
        int numberofNoBeetle = -1;
        int position = -1;

        for(int i = 0; i < beetle.length; i++){
            if(beetle[i] > -1 && i != smallestPosition && beetle[i] == beetle[smallestPosition]){
                str = str + String.valueOf(i);
            }
        }

        if(str.length() == 0){ //no values are tied
            c = 'X';
            position = -1;
        }
        /*else if(str.length() > 0 && str.length() < 4){
            int n = str.charAt(0);

            if(n < smallestPosition){
                position = n;
            }
            else{
                position = smallestPosition;
            }
        }*/
        else if(str.length() == 1 || str.length() == 2){ //two/three values are tied
            for(int j = 0; j < 4; j++){
                if(beetle[j] == -1){
                    numberofNoBeetle = j;
                    break;
                }
            }

            position = numberofNoBeetle;
        }
        else if(str.length() == 3) { //four values are tied
            position = 0;
            c = 'N';
        }

        //System.out.println("string" + str);
        //System.out.println("position val " + position);



        if(position == -1){
            c = 'X';
        }
        else if(position == 0){
            c = 'N';
        }
        else if(position == 1){
            c = 'E';
        }
        else if(position == 2){
            c = 'S';
        }
        else if(position == 3){
            c = 'W';
        }
        else {
            c = 'X';
        }
        /*if(str.length() == 0){
            c = 'X';
        }
        else if(str.charAt(0) == '0'){
            c = 'N';
        }
        else if(str.charAt(0) == '1'){
            c = 'E';
        }
        else if(str.charAt(0) == '2'){
            c = 'S';
        }
        else if(str.charAt(0) == '3'){
            c = 'W';
        }*/

        return c;
    }
    char Move(int north, int south, int east, int west, int neighbors[]) {
        int hasBeetle[] = {-1,-1,-1,-1};
        int noBeetle[] = {-1,-1,-1,-1};
        int beetlePresent = 0;
        //int BeetleNotPresent = 0;
        char movement = 'Q';
        int smallest = -1; //holds position of which direction to move in

        //checking if beetle in north direction
        if(north >= 10){
            hasBeetle[0] = north;
            beetlePresent++;
        }
        else{
            noBeetle[0] = north;
            //BeetleNotPresent++;
        }

        //checking for east direction
        if(east >= 10){
            hasBeetle[1] = east;
            beetlePresent++;
        }
        else{
            noBeetle[1] = east;
            //BeetleNotPresent++;
        }

        //checking for south direction
        if(south >= 10){
            hasBeetle[2] = south;
            beetlePresent++;
        }
        else{
            noBeetle[2] = south;
            //BeetleNotPresent++;
        }

        //checking for west direction
        if(west >= 10){
            hasBeetle[3] = west;
            beetlePresent++;
        }
        else{
            noBeetle[3] = west;
            // BeetleNotPresent++;
        }


        //checking hwo many directions have beetles
        if(beetlePresent == 0){
            movement = 'Q';
            smallest = -1;
        }
        else if(beetlePresent == 1){
            int small = hasBeetle[0];
            smallest = 0;
            for(int k = 0; k < 4; k++){
                if(hasBeetle[k] >= 10){
                    small = hasBeetle[k];
                    smallest = k;
                }
            }
        }
        else if(beetlePresent == 4){ //move towards farthest beetle
            int large = hasBeetle[0];
            int largest = 0;

            for(int i = 0; i < 4; i++){
                if(hasBeetle[i] > large){
                    large = hasBeetle[i];
                    largest = i;
                }
            }

            smallest = largest;

            char ch = tiedLargestVal(hasBeetle, smallest);

            if(ch != 'X')
            {
                if(ch == 'N'){
                    movement = 'S';
                }
                else if(ch == 'E'){
                    movement = 'W';
                }
                else if(ch == 'S'){
                    movement = 'N';
                }
                else if(ch == 'W'){
                    movement = 'E';
                }
                smallest = -2;
            }
        }
        else if(beetlePresent > 1){ //if multiple beetles present
            int small = 0;//keeps track of smallest
            if(hasBeetle[0] >= 10){
                small = hasBeetle[0];
                smallest = 0;
            }
            else if(hasBeetle[1] >= 10){
                small = hasBeetle[1];
                smallest = 1;
            }
            else if(hasBeetle[2] >= 10){
                small = hasBeetle[2];
                smallest = 2;
            }
            else if(hasBeetle[3] >= 10){
                small = hasBeetle[3];
                smallest = 3;
            }

            for(int j = 0; j < 4; j++){
                if(hasBeetle[j] != -1 && hasBeetle[j] < small){
                    small = hasBeetle[j];
                    smallest = j;
                }
            }

            char ch = tiedValues(hasBeetle, smallest);

            if(ch != 'X'){
                if(ch == 'N'){
                    movement = 'S';
                }
                else if(ch == 'E'){
                    movement = 'W';
                }
                else if(ch == 'S'){
                    movement = 'N';
                }
                else if(ch == 'W'){
                    movement = 'E';
                }
                smallest = -2;
            }


        }

        if(smallest == -1){
            movement = 'Q';
        }
        else if(movement == 'N' || smallest == 0){
            movement = 'S';
        }
        else if(movement == 'E' || smallest == 1){
            movement = 'W';
        }
        else if(movement == 'S' || smallest == 2){
            movement = 'N';
        }
        else if(movement == 'W' || smallest == 3){
            movement = 'E';
        }

        //System.out.println("smalestt t t t t" +  smallest);
        //System.out.println("movement of ant " + movement);


        return movement;

    }


}
