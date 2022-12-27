//Sachita Chaliki sxc200091

import java.util.*;
import java.io.*;

import static java.util.Collections.swap;

public class LinkList {
    private Node head; //Node holds beginning of the list

    LinkList() {
        head = null;
    } //default constructor

    LinkList(Node n) {//overloaded constructor
        head = n;
    } //overloaded constructor

    //getters
    public Node getHead() {
        return head;
    }

    //setters
    public void setHead(Node h) {
        head = h;
    }

    //sorts players alphabetically according to name (bubble sort)
    public void sortPlayers() {
        if (head != null && head.getNext() != null) { //if list exists and length > 1
            //check to see if nodes were swapped
            boolean flag = false;
            //traversal nodes
            Node cur = null;
            Node curReset = null;

            do {
                //reset variables
                flag = false;
                cur = head;
                curReset = head;

                //run till end of linked list
                while (cur != null) {
                    curReset = cur; //set cur back to original place
                    //if cur player's name is alphabetically greater than next player's name
                    if (cur.getNext() != null && ((cur.getPlayer().getName().compareTo(cur.getNext().getPlayer().getName())) > 0)) {
                        //swap nodes
                        curReset = swapNodes(cur, cur.getNext());
                        flag = true;
                        cur = curReset;
                    }
                    cur = cur.getNext(); //move to next node
                }
            } while (flag); //run till no swaps made
        }
    } //end of function

    //swaps two nodes in linked list
    public Node swapNodes(Node cur, Node curNext) {
        Node temp = head; //traversal node

        if (cur == head) { //if swapping head node
            head = curNext; //set head to node after cur
            cur.setNext(curNext.getNext()); //set next of cur to node after curNext
            curNext.setNext(cur); //set next of head to cur
            temp = curNext; //set traversal node to og position
        } else { //if not swapping with head node
            while (temp.getNext() != cur) { //get node before cur
                temp = temp.getNext();
            }
            if (curNext.getNext() == null) { //if curNext is end of list
                temp.setNext(curNext); //set the next of node before cur to curNext
                curNext.setNext(cur); //set next of curNext to cur
                cur.setNext(null); //cur will be at end of list
            } else { //if cur and curNext in middle of the list
                cur.setNext(curNext.getNext()); //set next of cur to node after curNext
                curNext.setNext(cur); //set node after curNext to cur
                temp.setNext(curNext); //set next of node before cur to curNext
            }

        } //end of else statement

        return temp; //return node before the swapped nodes
    } //end of swap nodes function

    //prints recursively individual stats of each player
    public Node printStats(Node t) {
        Node temp = t; //traversal node

        if (temp == null) { //if end of list reached
            return null;
        }

        //calculate stat values
        double bats = temp.getPlayer().calcAtBats();
        double BA = temp.getPlayer().calcBattingAvg();
        double OB = temp.getPlayer().calcOBPercentage();

        //display individual stats of each player
        System.out.printf("%s\t", temp.getPlayer().getName());
        System.out.print((int) bats + "\t");
        System.out.print((int) temp.getPlayer().getHits() + "\t");
        System.out.print((int) temp.getPlayer().getWalks() + "\t");
        System.out.print((int) temp.getPlayer().getStrikeouts() + "\t");
        System.out.print((int) temp.getPlayer().getHBPs() + "\t");
        System.out.print((int) temp.getPlayer().getSacrifices() + "\t");
        System.out.printf("%.3f\t", BA);
        System.out.printf("%.3f", OB);
        System.out.println();

        return printStats(temp.getNext()); //return incremented traversal node

    }
}