public class Node <A extends Comparable<A>> implements Comparable<Node<A>>{
    //declaring variables
    private Node<A> left;
    private Node<A> right;

    private Term obj;
    //Term t;

    //constructor
    Node(){
        left = null;
        right = null;
        obj = null;
        //t = null;
    }
    //overloaded constructor
    Node(Node<A> l, Node<A> r, Term term){
        left = l;
        right  = r;
        /*if(term instanceof Term ) {
            obj = (A) term;
        }*/
        obj = term;
        //t = term;
    }

    //accessors
    public void setObj(Term obj) {
        this.obj = obj;
    }
    public void setLeft(Node<A> left) {
        this.left = left;
    }
    public void setRight(Node<A> right) {
        this.right = right;
    }
    /*public void setT(Term t) {
        this.t = t;
    }*/

    //getters
    public Term getObj() {
        return obj;
    }
    public Node<A> getLeft() {
        return left;
    }
    public Node<A> getRight() {
        return right;
    }
    /*public Term getT() {
        return t;
    }*/

    //prints each node
    public void printNode(Term t){

        Term x = t; //make temporary variable to parse through term t
        boolean neg = false; //if value is negative or not
        /*if(x.getExponent() == -1000){ //if indefinite integral
            if(x.isFraction()){ //if a fraction
                //print coefficient and exponent in fraction
                System.out.print("(" + (int)x.getCoefficient() + "/" + (int)x.getExponent() + ")");
            }
            else {//if not a fraction
                //just print coefficient
                System.out.print((int) x.getCoefficient());
            }
        }*/
        if((!x.isFraction() && x.getCoefficient() == -1) || (!x.isFraction() && x.getCoefficient() == 1)){ //if not a fraction and coefficient is or -1
            //only print x without coefficient
            System.out.print("x");
        }
        else { //if coefficient is not one
            if(x.isFraction()){ //if coefficient is a fraction
                //variables to hold coefficient and exponent values
                double coefficient = x.getCoefficient();
                double exponent = x.getExponent();
                //System.out.println("dkjfalkdf " + exponent);

                //if coefficient can be simplified
                if(x.getCoefficient() != 1 && x.getCoefficient() != 1 && x.getExponent() % x.getCoefficient() == 0){ //if numerator divisible by denominator
                    //System.out.println("dkjfalkdf " + exponent);
                    exponent = exponent / coefficient;
                    coefficient = 1;

                }
                else if(x.getExponent() % 3 == 0 && x.getCoefficient() % 3 == 0){ //if fraction divisible by 3
                    //System.out.println("3dkjfalkdf " + exponent);
                    exponent = exponent / 3;
                    coefficient = coefficient / 3;
                }
                else if(x.getExponent() % 2 == 0 && x.getCoefficient() % 2 == 0){ //if fraction divisble by 2
                    //System.out.println("2dkjfalkdf " + exponent);
                    exponent = exponent / 2;
                    coefficient = coefficient / 2;
                }

                if(x.getCoefficient() > 0 &&  x.getExponent() < 0){ //if coeff is pos and exponent negative
                    //System.out.println(">0 <0 coefficient" + coefficient + " " + exponent);
                    System.out.print("(" + Math.abs((int) (coefficient)) + "/" + Math.abs((int) (exponent * -1)) + ")" + "x");
                }
                else if(x.getCoefficient() < 0 && x.getExponent() > 0){ //if coefficient is negative and exponent is positive
                    //System.out.println("<0 >0coefficient" + coefficient + " " + exponent);
                    System.out.print("(" + Math.abs((int) (coefficient)) + "/" + Math.abs((int) exponent) + ")" + "x");
                }
                else if(x.getCoefficient() < 0 && x.getExponent() < 0){ //if both are negative
                    //System.out.println("<0  <0 coefficient" + coefficient + " " + exponent);
                    System.out.print("(" + Math.abs((int) (coefficient * -1)) + "/" + Math.abs((int) (exponent * -1)) + ")" + "x");
                }
                else { //if both are positive
                    //System.out.println(">0 >0coefficient" + coefficient + " " + exponent);
                    System.out.print("(" + (int) coefficient + "/" + (int) exponent + ")" + "x");
                }
            }
            else { //if not a fraction
                if (x.getCoefficient() < 0) { //if coefficient is negative
                    x.setCoefficient(x.getCoefficient() * -1); //make coefficient positive
                    neg = true;
                }
                else{ //if coefficient is positive
                    neg = false;
                }
                System.out.print((int) x.getCoefficient() + "x"); //print coefficient and x
                if (neg) { //make coefficient negative again
                    x.setCoefficient(x.getCoefficient() * -1);
                }
            }
        }

        if(x.getExponent() != 1 && x.getExponent()!= 0){ //if exponent is not 1 or 0
            System.out.print("^" + (int)x.getExponent()); //print exponent
        }
    }

    //find definite integral of expression
    public double definiteIntegral(int bound){

        //if(this.getObj() instanceof Term) {
        double coefficient = this.getObj().getCoefficient(); //storing coefficient
        if(this.getObj().isFraction()){ //if coefficient is a fraction
            coefficient = this.getObj().getCoefficient() / this.getObj().getExponent(); //compute fraction
        }
        //double lowerVal = (Math.pow((double) ((Term) this.getObj()).getLowerBound(), ((Term) this.getObj()).getExponent())) * coefficient;
        double upperVal = (Math.pow((double) (bound), (this.getObj()).getExponent())) * coefficient; //calculates definite integral basaed on bound passed in
        //System.out.println("lowerbound " + this.getObj().getLowerBound() + " upper bound " + this.getObj().getUpperBound());
        //System.out.println("integral  upperval " + upperVal);
        return (upperVal); //return calculated value
        //}
        //return 0;
    }

    //compares two generic objects
    @Override
    public int compareTo(Node<A> o) {
        return obj.compareTo(o.obj);
    }
}

