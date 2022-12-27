
//Sachita Chaliki sxc200091

import java.util.*;
import java.io.*;
import java.lang.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        //String filename = "";

        System.out.println("Please enter in the filename");
        Scanner filename = new Scanner(System.in);
        String inputFile = filename.nextLine();
        //creating filestream to read
        File f = new File(inputFile);
        Scanner sc = new Scanner(f);

        System.out.println("Enter in a character to represent an ant");
        String antCreature = filename.next();
        char ant = antCreature.charAt(0);

        System.out.println("Enter in a character to represent a beetle");
        String beetleCreature = filename.next();
        char beetle = beetleCreature.charAt(0);

        System.out.println("Enter in the number of turns");
        int numTurns = filename.nextInt();

        //define 2D array
        Creature[][] battleGround = new Creature[10][10];

        if (f.exists()) {
            int q = 0;
            while (sc.hasNext()) {
                String c = sc.nextLine();
                StringParsing(c, battleGround, q);
                q++;
            }

        } else {
            System.out.println("File does not work");
        }

        char move = 'Q';
        sc.close();

        // printArray(battleGround, beetle, ant);
        // System.out.println();

        for (int n = 1; n <= numTurns; n++) //runs till number of turns specified by user is reached
        {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (battleGround[j][i] != null) {
                        if (battleGround[j][i].getChar() == 'B' && battleGround[j][i].getSurvivalRate() != n) {
                            // System.out.println("beetle loop");
                            move = distanceToCreature(j, i, battleGround, 'a');//find the closest ant
                            //System.out.println("move " + move);
                            battleGround[j][i].setSurvivalRate(n);
                            //System.out.println("seucriva rate" + battleGround[j][i].getSurvivalRate());

                            int num = 0;

                            /*if (move == 'N') {
                                if (battleGround[j - 1][i] == null || battleGround[j - 1][i].getChar() == 'a') {
                                    if (battleGround[j - 1][i] == null) {
                                        num = battleGround[j][i].Starve(false);
                                    }

                                    if (num > 5) {
                                        battleGround[j][i] = null;
                                    } else {
                                        System.out.println("north entering");
                                        battleGround[j][i].Starve(true);
                                        battleGround[j - 1][i] = battleGround[j][i];
                                        battleGround[j][i] = null;
                                        //System.out.println("coordinates of place to move to j-" + j + " i " + i);
                                        //(battleGround[j + 1][i]).Breed();
                                    }
                                }
                            }*/

                            if(move == 'N'){
                                if (battleGround[j - 1][i] == null || battleGround[j - 1][i].getChar() == 'a') {
                                    //System.out.println("battleGround[j][i]" + battleGround[j][i].getSurvivalRate());
                                    if(battleGround[j - 1][i]== null){
                                        num = battleGround[j][i].Starve(false);
                                    }
                                    else {
                                        battleGround[j][i].Starve(true);
                                    }

                                    //System.out.println("asdkjfadjfha" + num);
                                    if(num >= 5) {
                                        battleGround[j][i] = null;
                                    }
                                    else {
                                        battleGround[j - 1][i] = battleGround[j][i];
                                        battleGround[j][i] = null;
                                        //System.out.println("coordinates of place to move to j-" + j + " i " + i);
                                        //(battleGround[j][i + 1]).Breed();
                                    }
                                }
                            }
                            if (move == 'E') {
                                if (battleGround[j][i + 1] == null || battleGround[j][i + 1].getChar() == 'a') {
                                    //System.out.println("battleGround[j][i]" + battleGround[j][i].getSurvivalRate());
                                    if(battleGround[j][i + 1]== null){
                                        num = battleGround[j][i].Starve(false);
                                    }
                                    else {
                                        battleGround[j][i].Starve(true);
                                    }

                                    //System.out.println("num " + num);

                                    if(num >= 5) {
                                        battleGround[j][i] = null;
                                    }
                                    else {
                                        battleGround[j][i + 1] = battleGround[j][i];
                                        battleGround[j][i] = null;
                                        //System.out.println("coordinates of place to move to j-" + j + " i " + i);
                                        //(battleGround[j][i + 1]).Breed();
                                    }
                                }
                            } else if (move == 'W') {
                                if (battleGround[j][i - 1] == null || battleGround[j][i - 1].getChar() == 'a') {
                                    if(battleGround[j][i - 1] == null){
                                        num = battleGround[j][i].Starve(false);
                                    }
                                    else {
                                        battleGround[j][i].Starve(true);
                                    }

                                    if(num >= 5) {
                                        battleGround[j][i] = null;
                                    }
                                    else {
                                        battleGround[j][i - 1] = battleGround[j][i];
                                        battleGround[j][i] = null;
                                        //System.out.println("coordinates of place to move to j-" + j + " i " + i);
                                        //(battleGround[j][i - 1]).Breed();
                                    }
                                }
                            }
                            else if (move == 'S') {
                                if (battleGround[j + 1][i] == null || battleGround[j + 1][i].getChar() == 'a') {
                                    if(battleGround[j + 1][i] == null){
                                        num = battleGround[j][i].Starve(false);
                                    }
                                    else{
                                        battleGround[j][i].Starve(true);
                                    }

                                    if(num >= 5) {
                                        battleGround[j][i] = null;
                                    }
                                    else{
                                        battleGround[j + 1][i] = battleGround[j][i];
                                        battleGround[j][i] = null;
                                        //System.out.println("coordinates of place to move to j-" + j + " i " + i);
                                        //(battleGround[j + 1][i]).Breed();
                                    }
                                }
                            }

                        }

                    }
                }
            }//end of beetle loop

            //beginning of ant loop
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    if (battleGround[j][i] != null) {
                        if (battleGround[j][i].getChar() == 'a' && battleGround[j][i].getSurvivalRate() != n) {
                            //System.out.println("ant loop");


                            move = distanceToCreature(j, i, battleGround, 'B');//find the closest ant
                            battleGround[j][i].setSurvivalRate(n);
                            //System.out.println("set survival rate in beginning" + battleGround[j][i].getSurvivalRate());
                            if (move == 'E') {
                                if (i != 9 && battleGround[j][i + 1] == null) {
                                    //System.out.println("moving east");
                                    battleGround[j][i + 1] = battleGround[j][i];
                                    battleGround[j][i] = null;
                                    //System.out.println("coordinates of place to move to j-" + j + " i " + i);
                                    //(battleGround[j][i + 1]).setTimesMoved(n);
                                }
                            } else if (move == 'W') {
                                if (i != 0 && battleGround[j][i - 1] == null) {
                                    //System.out.println("moving west");
                                    battleGround[j][i - 1] = battleGround[j][i];
                                    battleGround[j][i] = null;
                                    //System.out.println("coordinates of place to move to j-" + j + " i " + i);
                                    //(battleGround[j][i - 1]).setTimesMoved(n);
                                }
                            } else if (move == 'N') {
                                if (j != 0 && battleGround[j - 1][i] == null) {
                                    //System.out.println("moving north");
                                    battleGround[j - 1][i] = battleGround[j][i];
                                    battleGround[j][i] = null;
                                    //System.out.println("coordinates of place to move to j-" + j + " i " + i);
                                    //(battleGround[j - 1][i]).setTimesMoved(n);
                                }
                            } else if (move == 'S') {
                                if (j != 9 && battleGround[j + 1][i] == null) {
                                    //System.out.println("moving south");
                                    battleGround[j + 1][i] = battleGround[j][i];
                                    battleGround[j][i] = null;
                                    //System.out.println("coordinates of place to move to j-" + j + " i " + i);
                                    //(battleGround[j + 1][i]).setTimesMoved(n);
                                }
                            }
                        }
                    }
                }//end of ant loop
            }
            //System.out.println("end of ant loop");

            if(n % 3 == 0){
                BreedingCheck(battleGround, 'a', 'a');
            }
            if(n % 8 == 0){
                BreedingCheck(battleGround, 'B', 'a');
            }

            System.out.println("TURN " + n);
            printArray(battleGround, beetle, ant);
            System.out.println();
        }


    }



    public static void StringParsing(String str, Creature[][] battleGround, int row) {
        //System.out.println("what is this ");
        for(int k = 0; k < 10; k++)
        {
            if(str.charAt(k) == 'B'){
                //Beetle b = new Beetle(beetle);
                battleGround[row][k] = new Beetle();
            }
            else if(str.charAt(k) == 'a') {
                //Ant a = new Ant(ant);
                battleGround[row][k] = new Ant();
            }
            else {
                battleGround[row][k] = null;
            }
        }
    }

    public static char distanceToCreature(int row, int col, Creature[][] array, char creature)
    {
        int c = col;
        int r = row;
        int north = 0;
        int south = 0;
        int east = 0;
        int west = 0;
        int neighbors[] = {0,0,0,0};
        int northAntNeighbors = 0;
        int southAntNeighbors = 0;
        int eastAntNeighbors = 0;
        int westAntNeighbors = 0;

        //adjcntNeighbors =  adjacentNeighbors(array, row, col);

        //System.out.println("adjacent neighbors " + row);

        //finding number of spaces to the north till creature is hit
        while(r != 0) {
            if(array[r][c] != null && array[r][c].getChar() == creature) {
                //creatureToNorth = true;
                north = north * 10;
                northAntNeighbors =  adjacentNeighbors(array, r, c);
                neighbors[0] = northAntNeighbors;

                break;
            }

            north++;
            r--;

            if(r == 0 && array[r][c] != null && array[r][c].getChar() == creature){
                north = north * 10;
                northAntNeighbors =  adjacentNeighbors(array, r, c);
                neighbors[0] = northAntNeighbors;

                break;
            }

            //System.out.println("north" + north + "creature " + creature);
        }

        c = col;
        r = row;

        //System.out.println("hello");
        //finding creature to the south
        while(r != 9) {
            if(array[r][c] != null && array[r][c].getChar() == creature) {
                //creatureToWest = true;
                //System.out.println("this is when you enter south and " + array[r][c].getChar() + "the row is " + row);
                south = south * 10;
                southAntNeighbors =  adjacentNeighbors(array, r, c);
                neighbors[2] = southAntNeighbors;

                break;
            }

            south++;
            r++;

            if(r == 9 && array[r][c] != null && array[r][c].getChar() == creature) {
                //creatureToWest = true;
                //System.out.println("this is when you enter south and " + array[r][c].getChar() + "the row is " + row);
                south = south * 10;
                southAntNeighbors =  adjacentNeighbors(array, r, c);
                neighbors[2] = southAntNeighbors;

                break;
            }

            //System.out.println("west" + west + "creature " + creature);
        }

        c = col;
        r = row;

        //finding creature to the west
        while(c != 0) {
            if(array[r][c] != null && array[r][c].getChar() == creature) {
                //creatureToWest = true;
                west = west * 10;
                westAntNeighbors =  adjacentNeighbors(array, r, c);
                neighbors[3] = westAntNeighbors;

                break;
            }

            west++;
            c--;

            if(c == 0 && array[r][c] != null && array[r][c].getChar() == creature) {
                //creatureToWest = true;
                west = west * 10;
                westAntNeighbors =  adjacentNeighbors(array, r, c);
                neighbors[3] = westAntNeighbors;

                break;
            }

            //System.out.println("west" + west + "creature " + creature);
        }
        c = col;
        r = row;

        //finding creature to east
        while(c != 9) {
            if(array[r][c] != null && array[r][c].getChar() == creature) {
                //creatureToEast = true;
                east = east * 10;
                eastAntNeighbors =  adjacentNeighbors(array, r, c);
                neighbors[1] = eastAntNeighbors;
                //System.out.println("neighbors to theast  for beetles and adnst" + eastAntNeighbors);

                break;
            }

            east++;
            c++;

            if(c== 9 && array[r][c] != null && array[r][c].getChar() == creature) {
                //creatureToEast = true;
                east = east * 10;
                eastAntNeighbors =  adjacentNeighbors(array, r, c);
                neighbors[1] = eastAntNeighbors;
                //System.out.println("neighbors to theast  for beetles and adnst" + eastAntNeighbors);

                break;
            }

            // System.out.println("east" + east + "creature " + creature);
        }
        c = col;
        r = row;

        //System.out.println("north "+ north+ "south " + south + " west " + west + "east " + east);
        //System.out.println("array" + array[row][col].getChar());
        //System.out.println("row " + row  + " col " + col);


        char movement = array[row][col].Move(north,south,east,west, neighbors);

        //System.out.println("result " + movement);

        return movement;


    }

    public static int adjacentNeighbors(Creature[][] array, int row, int col){
        int numNeighbors = 0;
        /*System.out.println("adjacent neighbors");
        System.out.println("row " + row + "col " + col);
        System.out.println("array " + array[row][col].getChar());*/

        if(row!= 0 && array[row - 1][col] != null && array[row - 1][col].getChar() == array[row][col].getChar()) { //neighbors north
            // System.out.println("north neighbors" + numNeighbors);
            //System.out.println("array and row and col " + row + " " + col);
            numNeighbors++;
        }
        if(row != 9 && array[row + 1][col] != null && array[row + 1][col].getChar() == array[row][col].getChar()) { //neighbors to south
            /*System.out.println("south neighbors" + numNeighbors);
            System.out.println("array and row and col " + row + " " + col);*/
            numNeighbors++;
        }
        if(col != 9 && array[row][col + 1] != null && array[row][col + 1].getChar() == array[row][col].getChar()) { //neighbors to east
            numNeighbors++;
            //System.out.println("east neighbors" + numNeighbors);
            //System.out.println("array and row and col " + row + " " + col);
        }
        if(col != 0 && array[row][col - 1] != null && array[row][col - 1].getChar() == array[row][col].getChar()) {//neighbors to west
            numNeighbors++;
            //System.out.println("west neighbors" + numNeighbors);
            //System.out.println("array and row and col " + row + " " + col);
        }
        if(row != 0 && col != 9 && array[row - 1][col + 1] != null && array[row - 1][col + 1].getChar() == array[row][col].getChar()){ //NE
            numNeighbors++;
        }
        if(row != 9 && col != 0 && array[row + 1][col -1 ] != null && array[row + 1][col - 1].getChar() == array[row][col].getChar()){//SW
            numNeighbors++;
        }
        if(row != 9 && col != 9 && array[row + 1][col + 1]!= null && array[row + 1][col + 1].getChar() == array[row][col].getChar()){ //SE
            numNeighbors++;
        }
        if(row != 0 && col != 0 && array[row - 1][col - 1] != null && array[row - 1][col - 1].getChar() == array[row][col].getChar()){
            numNeighbors++;
        }

        return numNeighbors;
    }

    public static void BreedingCheck(Creature[][] array, char c, char ant){
        //System.out.println("hello breeding function ");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (array[j][i] != null && array[j][i].getChar() == c) {
                    //System.out.println("survival rate " + array[j][i].getSurvivalRate());
                    // System.out.println("roe " + j + "col " + i);
                    if (j != 0 && array[j - 1][i] == null) { //breeding to the north
                        // System.out.println("entering north");
                        if(array[j][i].getChar() == ant && array[j][i].getSurvivalRate() != 0 && array[j][i].getSurvivalRate() % 3 == 0){
                            //System.out.println("entering north1");
                            array[j - 1][i] = new Ant();
                        }
                        else if(array[j][i].getChar() == 'B' && array[j][i].getSurvivalRate() > 0 && array[j][i].getSurvivalRate() % 8 == 0){
                            array[j - 1][i] = new Beetle();
                        }
                        array[j][i].setSurvivalRate(2);
                    } else if (i != 9 && array[j][i + 1] == null) {//breeding to the east
                        //System.out.println("entering east");
                        if(array[j][i].getChar() == ant && array[j][i].getSurvivalRate() != 0 && array[j][i].getSurvivalRate() % 3 == 0){
                            //System.out.println("entering east1");
                            array[j][i + 1] = new Ant();
                        }
                        else if(array[j][i].getChar() == 'B' && array[j][i].getSurvivalRate() > 0 && array[j][i].getSurvivalRate() % 8 == 0){
                            array[j][i + 1] = new Beetle();
                        }
                        array[j][i].setSurvivalRate(2);
                    } else if (j != 9 && array[j + 1][i] == null) { //breeding to the south
                        //System.out.println("entering south");
                        if(array[j][i].getChar() == ant && array[j][i].getSurvivalRate() > 0 && array[j][i].getSurvivalRate() % 3 == 0){
                            //System.out.println("entering south1");
                            array[j + 1][i] = new Ant();
                        }
                        else if(array[j][i].getChar() == 'B' && array[j][i].getSurvivalRate() > 0 && array[j][i].getSurvivalRate() % 8 == 0){
                            //System.out.println("entering south2");
                            array[j + 1][i] = new Beetle();
                        }
                        array[j][i].setSurvivalRate(2);
                        //array[j + 1][i].setTimesMoved(array[j][i].getTimesMoved());
                        // array[j + 1][i].Breed(true);
                    } else if (i != 0 && array[j][i - 1] == null) { //breeding to the west
                        //System.out.println("entering west");
                        if(array[j][i].getChar() == ant && array[j][i].getSurvivalRate() != 0 && array[j][i].getSurvivalRate() % 3 == 0){
                            //System.out.println("entering west1");
                            array[j][i - 1] = new Ant();
                        }
                        else if(array[j][i].getChar() == 'B' && array[j][i].getSurvivalRate() > 0 && array[j][i].getSurvivalRate() % 8 == 0){
                            //array[j][i - 1] = new Beetle();
                            array[j][i - 1] = new Beetle();
                        }

                        array[j][i].setSurvivalRate(2);
                        //array[j][i - 1].setTimesMoved(array[j][i].getTimesMoved());
                        //array[j][i - 1].Breed(true);
                    }

                    //printArray(array);
                    //System.out.println();
                    //System.out.println();
                }
            }
        }
    }

    public static void printArray(Creature[][] battleGround, char beetle, char ant)
    {
        for(int p = 0; p < 10; p++)
        {
            for(int t = 0; t < 10; t++){
                if(battleGround[p][t] != null){
                    if(battleGround[p][t].getChar() == 'B'){
                        System.out.print(beetle);
                    }
                    if(battleGround[p][t].getChar() == 'a'){
                        System.out.print(ant);
                    }
                    //System.out.print(battleGround[p][t].getChar());
                }
                else{
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}