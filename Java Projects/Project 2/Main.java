
//Sachita Chaliki sxc200091

import java.util.*;
import java.io.*;
import java.lang.*;

public class Main {

    //beginning of main
    public static void main(String[] args) throws FileNotFoundException {

        //initializing variables
        String name = ""; //hold name of player
        String baseballStats = ""; //holds baseball stats of player
        Node prev = null; //hold prev node

        //prompting user for filename
        System.out.println("Please enter in the filename");
        Scanner filename = new Scanner(System.in);
        String inputFile = filename.nextLine();
        //creating filestream to read
        File f = new File(inputFile);
        Scanner sc = new Scanner(f);

        LinkList stats = new LinkList(); //creating linkList object

        if (f.exists()) { //if file exists
            while (sc.hasNext()) { //read until end of file
                name = sc.next(); //name is string until first space
                baseballStats = sc.next(); //baseball stats is rest of string after space

                Player player = parseBattingRecord(stats, baseballStats, name); //parse baseball stats string
                if (player != null) {//if there is no duplicate player
                    Node n = new Node(null, player); //create new node with player

                    if (stats.getHead() == null) { //if head of linked list DNE
                        stats.setHead(n); //set node as head
                    } else { //if there is a head
                        prev.setNext(n); //connect tp previous node
                    }

                    prev = n; //set new node as previous node
                }

            }
        } else { //if file doesn't open
            System.out.println("File does not exist"); //print error message
        }

        stats.sortPlayers(); //sort linked list alphabetically by name
        stats.printStats(stats.getHead()); //print individual stats of players
        System.out.println();
        System.out.println("LEAGUE LEADERS");//print league leader' header
        sortLeagueLeaders(stats, 1); //print BA leaders
        sortLeagueLeaders(stats, 2); //print OB leaders
        sortLeagueLeaders(stats, 3); //print hits leaders
        sortLeagueLeaders(stats, 4); //print walks leaders
        sortLeagueLeaders(stats, 5); //print strikeouts leaders
        sortLeagueLeaders(stats, 6); //print HBP leaders

    }

    //function parses string and populates stat variables in player object
    public static Player parseBattingRecord(LinkList list, String str, String name) {
        //variables to keep track of stats
        double hits = 0.0, outs = 0.0, strikeouts = 0.0, walks = 0.0, hitByPitches = 0.0, sacrifices = 0.0;

        Player duplicate = duplicatePlayer(list, str, name); //check for duplicate player

        if (duplicate != null) { //if player is a duplicate

            //set all stat variables to duplicate Player's
            hits = duplicate.getHits();
            outs = duplicate.getOuts();
            strikeouts = duplicate.getStrikeouts();
            walks = duplicate.getWalks();
            hitByPitches = duplicate.getHBPs();
            sacrifices = duplicate.getSacrifices();
        }

        for (int i = 0; i < str.length(); i++) { //loop to go through baseball stats string

            //finds char at position i in batting record string
            char c = str.charAt(i);
            switch (c) {
                case 'H':
                    hits++;
                    break;

                case 'O':
                    outs++;
                    break;

                case 'K':
                    strikeouts++;
                    break;

                case 'W':
                    walks++;
                    break;

                case 'P':
                    hitByPitches++;
                    break;

                case 'S':
                    sacrifices++;
                    break;

                default:
                    continue;
            }

        } //end of for loop

        Player p = null; //create new object hold null

        if (duplicate != null) { //if player is a duplicate
            //update stats with new numbers
            duplicate.setHits(hits);
            duplicate.setOuts(outs);
            duplicate.setStrikeouts(strikeouts);
            duplicate.setHBPs(hitByPitches);
            duplicate.setSacrifices(sacrifices);
            duplicate.setWalks(walks);
            p = null;
        } else { //if player/name is not a duplicate
            //create new player object
            p = new Player(name, hits, outs, strikeouts, hitByPitches, sacrifices, walks);
        }

        //return player
        return p;
    }

    //checks if player object with same name exists
    public static Player duplicatePlayer(LinkList list, String str, String name) {
        Player duplicate = null; //keeps track of duplicate player

        Node temp = list.getHead(); //traversal node

        while (temp != null) { //goes through linked list
            if (temp.getPlayer().getName().equals(name)) { //if name of existing player object matches new player
                duplicate = temp.getPlayer(); //hold duplicate player
                break;  //exist loop
            }
            temp = temp.getNext(); //move traversal node to next node
        }

        return duplicate; //return duplicate player
    }

    //sorts the list based on baseball stats using bubble sort
    public static void sortLeagueLeaders(LinkList list, int statNum) {
        if (list.getHead() != null && list.getHead().getNext() != null) { //if list exists and length > 1

            boolean flag = false; //checks if nodes swapped
            Node cur = null; //traversal node
            Node curReset = null; //sets cur node back to og position after swap

            do {
                //reset all variables
                flag = false;
                cur = list.getHead();
                curReset = list.getHead();

                while (cur != null) { //if node exists

                    double BA = populateTempAndNum(statNum, cur); //holds wanted stat of cur player

                    curReset = cur; //set curReset to cur

                    if (cur.getNext() != null) { //if node after cur exists
                        double nextBA = populateTempAndNum(statNum, cur.getNext()); //hold wanted stat of player after cur

                        if (statNum == 5) { //if strikeout values are being compared
                            if (BA > nextBA) { //if cur node is greater than next node (sort in ascending order)
                                curReset = list.swapNodes(cur, cur.getNext()); //swap nodes hold original position before swap
                                flag = true; //set swapped nodes flag to true
                                cur = curReset; //reset cur to original position before swap
                            }
                        } else { //if none strikeout values are compared (sort in descending order)
                            if (BA < nextBA) { //if cur stat id less than next node stat
                                curReset = list.swapNodes(cur, cur.getNext()); //swap nodes hold original position before swap
                                flag = true; //set swapped nodes flag to true
                                cur = curReset; //reset cur to original position before swap
                            }
                        }
                    }
                    cur = cur.getNext(); //increment cur
                }
            } while (flag); //run until no swaps made
        }

        printLeagueLeader(list, statNum); //print each stat leadue leaders
    }

    //prints league leader of each stat
    public static void printLeagueLeader(LinkList list, int statNum) {

        Node temp = list.getHead(); //traversal node
        int ties = 1; //counts number of players printed out in each 1,2 positions
        int firstLeaderTies = 0; //counts number of ties for first leader
        int leaderPosition = 0; //keeps track of what position is printing
        double num = 0; //hold stat of cur node to be printed
        double nextNum = 0; //hold stat of cur next node to be printed

        num = populateTempAndNum(statNum, temp); //hold stat to print

        //print league leader stat header based on statNum
        if (statNum == 1) {
            System.out.println("BATTING AVERAGE");

        }
        else if (statNum == 2) {
            System.out.println("ON-BASE PERCENTAGE");
        }
        else if (statNum == 3) {
            System.out.println("HITS");
        }
        else if (statNum == 4) {
            System.out.println("WALKS");
        }
        else if (statNum == 5) {
            System.out.println("STRIKEOUTS");
        }
        else if (statNum == 6) {
            System.out.println("HIT BY PITCH");
        }

        //set nextNum to stat in temp player
        nextNum = num;

        while (leaderPosition < 4) { //only print three leader positions
            if (temp != list.getHead() && num == nextNum) { //if not head node and duplicate leader
                ties++; //increment duplicate player count
                System.out.print(", " + temp.getPlayer().getName()); //print name of player with same stat
            } else if (ties < 3) { //if only 1 or 2 ties
                if (leaderPosition == 0 || leaderPosition == 1) { //if leader position at first place
                    firstLeaderTies = ties; //hold number of ties in firstLeaderTies
                }
                leaderPosition++; //increment position if no duplicate player
                if (leaderPosition == 3) { //if leaderPosition at third place
                    if (ties == 1 && firstLeaderTies == 1) { //if no ties in first and second place
                        System.out.println(); //print new line
                        if(statNum != 1 && statNum !=2){ //if stat is not BA/OB
                            System.out.print((int)nextNum + "\t" + temp.getPlayer().getName()); //print int number
                        } else{ //if stat is BA/OB
                            System.out.printf("%.3f\t%s", nextNum, temp.getPlayer().getName()); //print decimal rounded to thousandth place
                        }
                    } else { //if first or second place has tie
                        break; //exist loop to stop printing
                    }

                } else if(leaderPosition < 3) { //if leader position is less than three
                    if (leaderPosition != 1) { //if not printing first place
                        System.out.println(); //print new line
                    }

                    if(statNum != 1 && statNum !=2){//if stat is not BA/OB
                        System.out.print((int)nextNum + "\t" + temp.getPlayer().getName()); //print int number
                    } else{ //if stat is BA/OB
                        System.out.printf("%.3f\t%s", nextNum, temp.getPlayer().getName()); //print decimal rounded to thousandth place
                    }
                    ties = 1; //reset duplicate players to one
                }
            } else { //if ties > 3
                break; //exit loop
            }

            //move to next node in list
            if (temp.getNext() != null) { //if next node exists
                num = populateTempAndNum(statNum, temp); //hold stat of cur node
                temp = temp.getNext(); //increment traversal node
                nextNum = populateTempAndNum(statNum, temp); //hold stat of next node
            } else { //if next node doesn't exist
                break; //exist loop
            }
        } //end of while loop

        //print two new lines after each stat league leaders
        System.out.println();
        System.out.println();
        //sort player alphabetically
        list.sortPlayers();
    }

    //hold wanted stat and return value
    public static double populateTempAndNum(int statNum, Node temp){

        double value = 0; //var holds wanted stat

        if (statNum == 1) { //if statNum is 1
            value = temp.getPlayer().calcBattingAvg(); //get BA of player in node temp
        }
        else if (statNum == 2) { //if statNum is 2
            value = temp.getPlayer().calcOBPercentage(); //get OB of player in node temp
        }
        else if (statNum == 3) { //if statNum is 3
            value = temp.getPlayer().getHits(); //get BA of hits in node temp
        }
        else if (statNum == 4) { //if statNum is 4
            value = temp.getPlayer().getWalks(); //get BA of walks in node temp
        }
        else if (statNum == 5) { //if statNum is 5
            value = temp.getPlayer().getStrikeouts(); //get strikeouts of player in node temp
        }
        else if (statNum == 6) { //if statNum is 6
            value = temp.getPlayer().getHBPs(); //get HBP of player in node temp
        }

        //return stat value
        return value;
    }
} //end of class


