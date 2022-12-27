//Sachita Chaliki sxc200091

import java.util.*;
import java.io.*;

public class Node {

    private Node next = null; //holds next node
    private Player p; //hold player object

    //default constructor
    Node(){
        next = null;
        p = null;
    }

    //overloaded constructor
    Node(Node n, Player pl){
        next = n;
        p = pl;
    }

    //getter
    public Node getNext(){
        return next;
    }
    public Player getPlayer(){
        return p;
    }

    //setters
    public void setNext(Node n){
        next = n;
    }

    public void setPlayer(Player pl){
        p = pl;
    }
}
