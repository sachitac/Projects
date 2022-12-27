//Sachita Chaliki sxc200091

public class Beetle extends Creature{
    int starving = 0;
    Beetle(){
        c = 'B';
    }
    int getTimesMoved(){
        return timesCharMoved;
    }
    //return beetle's character
    char getChar()
    {
        return c;
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

    int Starve(boolean eat) {
        if(eat == false) {
            starving++;
        }
        else if(eat == true){
            starving = 0;
        }

        return starving;
    }

    char tiedValues(int arr[], int[] neighbors, int smallestPosition){

        char c = 'X';
        int smallestVal = arr[smallestPosition];
        int smallestPos = smallestPosition;

        for(int t = 0; t < 4; t++){
            if(t != smallestPos && arr[t] == smallestVal){


                if(neighbors[t] == neighbors[smallestPos]){
                    //System.out.println("neigbros " + neighbors[t] + " " + neighbors[smallestPosition]);
                    int direction = smallestPos;

                    if(t < smallestPos){
                        direction = t;
                    }
                    else if(smallestPos < t){
                        direction = smallestPos;
                    }

                    //System.out.println("direction when they are tied " + direction);

                    if(direction == 0){
                        c = 'N';
                    }
                    else if(direction == 1){
                        c = 'E';
                    }
                    else if(direction == 2){
                        c = 'S';
                    }
                    else if(direction == 3){
                        c = 'W';
                    }
                }


                else if(neighbors[t] > neighbors[smallestPos]){
                    //System.out.println("neigbros " + neighbors[t] + " " + neighbors[smallestPosition]);
                    //System.out.println("greater than and tied ");
                    if(t == 0){
                        c = 'N';
                    }
                    else if(t == 1){
                        c = 'E';
                    }
                    else if(t == 2){
                        c = 'S';
                    }
                    else if(t == 3){
                        c = 'W';
                    }

                    smallestPos = t;
                }
            }
        }

        //System.out.println(c);
        return c;
    }

    int tiedValues(int arr[], int largestVal){
        int largestNumber = arr[largestVal];
        int position = largestVal;
        char c = 'X';

        for(int t = 0; t < 4; t++){
            if(t != largestVal && arr[t] == arr[largestVal]){
                if(t < largestVal){
                    position = t;
                }
            }
        }

        return position;
    }

    char Move(int north, int south, int east, int west, int neighbors[]) {
        int hasAnt[] = {-1,-1,-1,-1};
        int noAnt[] = {-1,-1,-1,-1};
        int antPresent = 0;
        //int BeetleNotPresent = 0;
        char movement = 'Q';
        int smallest = -1; //holds position of which direction to move in

        //checking if beetle in north direction
        if(north >= 10){
            hasAnt[0] = north;
            antPresent++;
        }
        else{
            noAnt[0] = north;
            //BeetleNotPresent++;
        }

        //checking for east direction
        if(east >= 10){
            hasAnt[1] = east;
            antPresent++;
        }
        else{
            noAnt[1] = east;
            //BeetleNotPresent++;
        }

        //checking for south direction
        if(south >= 10){
            hasAnt[2] = south;
            antPresent++;
        }
        else{
            noAnt[2] = south;
            //BeetleNotPresent++;
        }

        //checking for west direction
        if(west >= 10){
            hasAnt[3] = west;
            antPresent++;
        }
        else{
            noAnt[3] = west;
            // BeetleNotPresent++;
        }


        //checking hwo many directions have beetles
        if(antPresent == 0){ //no ants present
            //go towards the farthest edge
            int large = noAnt[0];
            int largest = 0;

            for(int i = 0; i < 4; i++){
                if(noAnt[i] > large){
                    large = noAnt[i];
                    largest = i;
                }
            }

            smallest = largest;

            int num = tiedValues(noAnt, smallest);
            smallest = num;
        }
        else if(antPresent == 1){ //only one ant present
            int small = hasAnt[0];
            smallest = 0;
            for(int k = 0; k < 4; k++){
                if(hasAnt[k] >= 10){
                    small = hasAnt[k];
                    smallest = k;
                }
            }
        }
        else if(antPresent > 1){ //if multiple beetles present
            int small = 0;//keeps track of smallest
            if(hasAnt[0] >= 10){
                small = hasAnt[0];
                smallest = 0;
            }
            else if(hasAnt[1] >= 10){
                small = hasAnt[1];
                smallest = 1;
            }
            else if(hasAnt[2] >= 10){
                small = hasAnt[2];
                smallest = 2;
            }
            else if(hasAnt[3] >= 10){
                small = hasAnt[3];
                smallest = 3;
            }

            for(int j = 0; j < 4; j++){
                if(hasAnt[j] != -1 && hasAnt[j] < small){
                    small = hasAnt[j];
                    smallest = j;
                }
            }

            char ch = tiedValues(hasAnt, neighbors, smallest);

            //System.out.println(neighbors[1] + " + " +  neighbors[3]);

            //System.out.println("chchchc " + smallest);

            if(ch != 'X'){
                movement = ch;
                smallest = -2;
            }

        }

        if(smallest == -1){
            movement = 'Q';
        }
        else if(movement == 'N' || smallest == 0){
            movement = 'N';
        }
        else if(movement == 'E' || smallest == 1){
            movement = 'E';
        }
        else if(movement == 'S' || smallest == 2){
            movement = 'S';
        }
        else if(movement == 'W' || smallest == 3){
            movement = 'W';
        }

        //System.out.println("smalestt t t t t" +  smallest);
        //System.out.println("movement of ant " + movement);


        return movement;

    }
    //end of class
}

