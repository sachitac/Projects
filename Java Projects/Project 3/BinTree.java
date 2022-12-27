import java.util.ArrayList;

public class BinTree <A extends Comparable<A>> {
    //declaring variables
    private Node<A> head;

    //constructor
    BinTree() {
        head = null;
    }

    //overloaded constructor
    BinTree(Node<A> n) {
        head = n;
    }

    //setter
    public void setHead(Node<A> head) {
        this.head = head;
    }

    //getter
    public Node<A> getHead() {
        return head;
    }

    //insertion method
    public Node<A> insert(Node<A> temp, Term newNode) {

        int num = 0;
        if (temp != null) {
            num = newNode.compareTo(temp.getObj()); //compares two exponents of traversal node and Term object
        }

        //if (temp.getObj() instanceof Term) {
        if (temp == null) {//if bst has empty spot

            Node<A> n = new Node<A>(null, null, newNode); //insert new node

            if (head == null) {
                head = n;
            }
            return n;
        } /*else if (newNode.getExponent() < (temp.getObj()).getExponent()) {
            temp.setLeft(insert(temp.getLeft(), newNode));
        } else {
            temp.setRight(insert(temp.getRight(), newNode));
        }*/ else if (num < 0) { //if term object's exponent less than traversal node
            temp.setLeft(insert(temp.getLeft(), newNode)); //go to the left
        } else if (num > 0) { //if term object's exponent greater than traversal node
            temp.setRight(insert(temp.getRight(), newNode)); //go to right
        }
        //}

        return temp; //return traversal node
    }

    int COUNT = 0;

    //prints entire antiderivative
    public Node<A> printBST(Node<A> n, int COUNT) {

        if (n.getObj() instanceof Term) {
            if (n != null) { //if node present

                if (n.getRight() != null) { //if right node exists
                    printBST(n.getRight(), COUNT); //go to the right
                }


                //finding if current node is first node being printed
                Node<A> lastRightNode = findLastRightNode(head);
                if (n != lastRightNode) { //if current node not first node being printed

                    //COUNT++;
                    if ((n.getObj()).isFraction()) { //if coefficient is fraction
                        if ((n.getObj()).getCoefficient() * (n.getObj()).getExponent() < 0) { //if coefficient and exponent multiplied is positive
                            System.out.print(" - ");
                        } else if ((n.getObj()).getCoefficient() * (n.getObj()).getExponent() > 0) { //if multiplied both is negative
                            System.out.print(" + ");
                        }

                    } else { //if coefficient is not fraction
                        if (((n.getObj())).getCoefficient() > 0) { // if coefficient is positive
                            System.out.print(" + ");
                        } else if (((n.getObj())).getCoefficient() < 0) { //if coefficient is negative
                            System.out.print(" - ");
                        }
                    }
                } else {
                    if ((n.getObj()).isFraction()) { //if coefficient is fraction
                        /*if (n.getObj().getCoefficient() * n.getObj().getExponent() < 0) {
                            System.out.print("-");
                        }*/
                    } else { //if coefficient is not fraction
                        if ((n.getObj()).getCoefficient() < 0) { //if coefficient is negative
                            System.out.print("-");
                        }
                    }
                }

                n.printNode(n.getObj()); //printing node

                //System.out.println("outsidehjg " + i);
                if (n.getLeft() != null) { //if left node is not null
                    //System.out.println("left node" + i);
                    printBST(n.getLeft(), COUNT); //go left
                }
                //System.out.println("very end " + i);

            }
        }

        return head; //return head of bst
    }

    //search function
    public boolean search(Node<A> temp, int exponent, int coefficient) {
        boolean flag = false; //if found variable
        if (temp == null) { //if end of bst
            flag = false; //return not found
        } else { //if node present
            if (exponent == temp.getObj().getExponent()) { //if node is found
                temp.getObj().setCoefficient(temp.getObj().getCoefficient() + coefficient); //add coefficient to node
                flag = true;
            } else if (exponent < temp.getObj().getExponent()) { //if coefficient is less than current node
                return search(temp.getLeft(), exponent, coefficient); //go left
            } else if (exponent > temp.getObj().getExponent()) { //if coefficient is greater than current node
                return search(temp.getRight(), exponent, coefficient); //go right
            }
        }
        return flag; //return result
    }

    //delete node in bst
    public Node<A> delete(Node<A> temp, Node<A> n) {
        //finding node before node you want to delete
        boolean findNode = search(head, (int) temp.getObj().getExponent(), (int) temp.getObj().getCoefficient());
        Node<A> searchingNode = n;
        Node<A> returnNode = null;
        if (searchingNode == null) { //if node not found
            return null; //return null
        }
        if (searchingNode.getLeft() == null && searchingNode.getRight() == null) { //if no children
            searchingNode = null;
        } else if (temp.getLeft() != null && temp.getRight() != null) { //if two children
            Node<A> parent = findParentNode(head, n);
            if (parent.getLeft() == searchingNode) { //if current node is to left of parent node
                if (temp.getLeft().getObj().compareTo(temp.getRight().getObj()) < 0) { //finding smaller number
                    parent.setLeft(searchingNode.getLeft());
                } else { //if current node is to right of parent node
                    parent.setLeft(searchingNode.getRight());
                }
            } else if (parent.getRight() == searchingNode) { //if current node is to right of parent node
                if (temp.getLeft().getObj().compareTo(temp.getRight().getObj()) < 0) { //finding bigger number
                    parent.setRight(searchingNode.getRight()); //if parent node is to the right
                } else {
                    parent.setRight(searchingNode.getLeft()); //if nparent node is to the left
                }
            }

        } else { //if one child
            Node<A> parent = findParentNode(head, n);
            if (parent.getLeft() == searchingNode) { //if current node is to left of parent node
                if (searchingNode.getRight() != null) {
                    parent.setLeft(searchingNode.getRight());
                } else if (searchingNode.getLeft() != null) {
                    parent.setLeft(searchingNode.getLeft());
                }
            }
            if (parent.getRight() == searchingNode) { //if current node is to right of parent node
                if (searchingNode.getRight() != null) {
                    parent.setRight(searchingNode.getRight());
                } else if (searchingNode.getLeft() != null) {
                    parent.setRight(searchingNode.getLeft());
                }
            }
        }
        return temp;
    }

    //finding parent node of node
    private Node<A> findParentNode(Node<A> temp, Node<A> n) {
        Node<A> parent = null;
        Node<A> child = null;

        //comparing parameter node to traversal node
        int num = n.getObj().compareTo(temp.getObj());
        if (num == 0) {
            return parent;
        } else if (num < 0) { //if parameter exponent less than traversal node exponent
            parent = temp;
            findParentNode(temp.getLeft(), n); //go left
        } else if (num > 0) { //if paramenter exponent grater than traversal node exponent
            parent = temp;
            findParentNode(temp.getRight(), n); //go right
        }

        return parent;
    }

    //finding greatest node in bst
    public Node<A> findLastRightNode(Node<A> n) {
        if (n.getRight() != null) {
            return findLastRightNode(n.getRight());
        }

        return n;
    }

}
