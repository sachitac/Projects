import java.io.*;
import java.util.*;

public class Main {
    //global variables for integral
    static double valNum = 0;
    static double valNum2 = 0;

    public static void main(String[] args) throws FileNotFoundException {

        //prompting user for filename
        System.out.println("Please enter in the filename");
        Scanner filename = new Scanner(System.in);
        String inputFile = filename.nextLine();
        //creating filestream to read
        File f = new File(inputFile);
        Scanner sc = new Scanner(f);

        //string to read file line by line
        String str = "";
        BinTree tree = new BinTree();

        if(f.exists()){ //checking if file exists
            while(sc.hasNext()){
                str = sc.nextLine();
                if(str.length() != 0){ //if string exists
                    Node beginning = StringParsing(str, tree);
                }

                tree.printBST(tree.getHead(),0);
                if(((tree.getHead().getObj())).getUpperBound() != -100){
                    valNum = 0;
                    valNum2 = 0;
                    double definiteVal = findDefiniteIntegral(tree.getHead(), 0);
                    System.out.print(", " + (tree.getHead().getObj()).getLowerBound() + "|" + (tree.getHead().getObj()).getUpperBound() + " = ");
                    System.out.printf("%.3f", definiteVal);
                }
                else{
                    System.out.print(" + C");
                }
                System.out.println();
                tree.setHead(null);
            }


        }

    }

    public static Node StringParsing(String str,BinTree tree){
        //variable declarations
        String original = str;
        String a = "";
        String b = "";
        int aNum = -100;
        int bNum = -100;
        String temp = "";
        boolean negNum = false;
        boolean negExp = false;
        int coeff = 1;
        int exponent = -1000;
        Term t = null;

        if(original.indexOf("|") != 0){ //if string contains bounds
            //System.out.println("hello");
            a = original.substring(0,original.indexOf('|'));
            if(original.charAt(0) == '-'){ //if negative bound
                a = a.substring(1);
                aNum = Integer.parseInt(a);
                aNum = aNum * -1;
            }
            else{ //if not negative bound
                aNum = Integer.parseInt(a);
            }
            b = original.substring(original.indexOf('|') + 1, original.indexOf(' '));
            if(b.charAt(0) == '-'){ //if negative bound
                b = b.substring(1);
                bNum = Integer.parseInt(b);
                bNum = bNum * -1;
            }
            else{ //if not negative bound
                bNum = Integer.parseInt(b);
            }


            aNum = Math.min(aNum, bNum); //smaller of 2 values
            bNum = Math.max(aNum,bNum); //larger of 2 values
            original = original.substring(original.indexOf(' '));
        }
        else{//if string has no bounds
            original = original.substring(original.indexOf('|') + 1);

        }


        //removing all white spaces in string
        original = original.replaceAll("\\s", "");
        //not storing 'dx' portion
        original = original.substring(0,original.indexOf('d'));


        while(original.length() != 0){
            //finding positions of operators
            int plusSign = original.lastIndexOf('+');
            int negSign = original.lastIndexOf('-');


            if(negSign == 0){ //if coefficient is negative
                negSign = -1;
            }
            else if(negSign != -1 && original.charAt(negSign - 1) == '^'){ //if exponent is negative
                String og = original.substring(0,negSign);
                negSign = og.lastIndexOf('-');
            }

            if(plusSign == -1 && negSign == -1){ //if last section of string
                temp = original;
                original = "";
            }
            else { //if not last section of string
                if(plusSign != -1 && negSign != -1) {
                    temp = original.substring(Math.max(plusSign, negSign));
                    original = original.substring(0,Math.max(plusSign, negSign));
                }
                else if(negSign == -1){ //if negative sign doesn't exist
                    temp = original.substring(plusSign);
                    original = original.substring(0,plusSign);
                }
                else if(plusSign == -1){//if positive sign doesn't exist
                    temp = original.substring(negSign);
                    original = original.substring(0,negSign);
                }
            }

            /*System.out.println("temp " + temp);
            System.out.println("originial " + original);
            System.out.println(negSign + "  " + plusSign);*/

            if(temp.charAt(0) == '-') //if coefficient is negative
            {
                negNum = true;
                temp = temp.substring(1);
            }
            else if(temp.charAt(0) == '+'){ //if coefficient is positive
                negNum = false;
                temp = temp.substring(1);
            }
            else{
                negNum = false;
            }

            if(temp.contains("x")){ //if x exists

                if(temp.charAt(0) != 'x'){ //if coefficient exists
                    coeff = Integer.parseInt(temp.substring(0,temp.indexOf('x')));
                }
                else{ //if coefficient doesn't exist
                    coeff = 1;
                }
            }
            else{ //if x doesn't exist
                coeff = Integer.parseInt(temp);
            }

            if(negNum){ //make coefficient negative
                coeff = coeff * -1;
            }

            if(temp.contains("^")){ //if exponent exists
                if(temp.charAt(temp.indexOf('^') + 1) == '-'){ //if exponents is negative
                    negExp = true;
                    temp = temp.replace("-","");
                }
                else{ //if exponent doesn't exist
                    negExp = false;
                }

                //System.out.println("temp " + temp);
                exponent = Integer.parseInt(temp.substring(temp.indexOf('^') + 1));

                if(negExp){ //make exponent negative
                    exponent = exponent * -1;
                }
            }
            else if(!temp.contains("^") && temp.contains("x")){ //is only x present and no exponent
                exponent = 1;
            }
            else{ //if exponent doesn't exist
                exponent = -1000;
            }

            //System.out.println("kjhkcoefficient " +  coeff);
            //System.out.println("khjexponent " + exponent);

            t = new Term((double) coeff, (double) exponent); //create term object
            t.integral(); //find integral
            boolean flag = tree.search(tree.getHead(), (int) t.getExponent(), (int) t.getCoefficient()); //finding duplicate
            if(!flag){ //if duplicate doesn't exist
                //set boundaries and create node
                t.setLowerBound(aNum);
                t.setUpperBound(bNum);
                tree.insert(tree.getHead(), t);
            }
            else{
                t = null;
            }

        }

        return tree.getHead();
    }

    //deleting bst
    public static void delete(Node root)
    {
        if (root!=null){
            delete(root.getLeft());
            root.setLeft(null);
            delete(root.getRight());
            root.setRight(null);
        }
    }

    //finding definite integral
    public static double findDefiniteIntegral(Node t, double value) {
        double val = keepingTrackofIntegral(t, value); //finding upper bound value
        double val2 = keepingTrackOfIntegral2(t, value); //finding lower bound value
        return val - valNum2; //subtracting two
    }

    //finding upper bound value
    public static double keepingTrackofIntegral(Node t, double value){
        if (t.getLeft() != null){ //if left node exists
            keepingTrackofIntegral(t.getLeft(), valNum); //go left
        }
        valNum += t.definiteIntegral(t.getObj().getUpperBound()); //add value to existing ones
        if (t.getRight() != null){
            keepingTrackofIntegral(t.getRight(), valNum);
        }

        return valNum;
    }

    //finding lower bound
    public static double keepingTrackOfIntegral2(Node t, double value){
        if (t.getLeft() != null){ //if right node exists
            keepingTrackOfIntegral2(t.getLeft(), valNum2); //go left
        }
        valNum2 += t.definiteIntegral(t.getObj().getLowerBound()); //add values to existing ones
        if (t.getRight() != null){
            keepingTrackOfIntegral2(t.getRight(), valNum2);
        }

        return valNum2;
    }

}