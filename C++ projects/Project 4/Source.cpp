#include <iostream>
#include <string>
#include <algorithm>
#include <fstream>
#include "Node.h"
#include "LList.h"

using namespace std;


string removeWhiteSpace(string str)
{
    string::iterator end_pos = remove(str.begin(), str.end(), ' ');
    str.erase(end_pos, str.end());
    return str;
}

void StringParsing(string str, LList& list1)
{
    int count = 0;
    while (str.length() != 0)
    {
        int out = 0;
        int in = 0;
        int exponent;
        int trigPosition = -1;
        string outCoeff = " ";
        string temp = " ";
        string trig = " ";
        bool exponentIsNeg = false;
        bool outerIsNeg = false;
        int plusSignLocation = str.rfind('+');
        int negSignLocation = str.rfind('-');
        int carrorSign = str.find('^');

        Node* prev = new Node();

        if (negSignLocation != -1 && str.at(negSignLocation - 1) == '^')//if there is carrot symbol right before negative sign
        {
            negSignLocation = str.find_last_of('-', negSignLocation - 1);
        }

        if (plusSignLocation > negSignLocation)
        {
            temp = str.substr(plusSignLocation);
            str = str.substr(0, plusSignLocation);
        }
        else if (negSignLocation > plusSignLocation)
        {
            temp = str.substr(negSignLocation);
            str = str.substr(0, negSignLocation);
        }
        else if (plusSignLocation != -1 && negSignLocation == -1)
        {
            temp = str.substr(plusSignLocation);
            str = str.substr(0, plusSignLocation);
        }
        else if (negSignLocation != -1 && plusSignLocation == -1)
        {
            temp = str.substr(negSignLocation);
            str = str.substr(0, negSignLocation);
        }
        else
        {
            temp = str;
            str = "";
        }

        if (temp.at(0) == '-')	//if outer coefficient is negative
        {
            outerIsNeg = true;
            temp = temp.substr(1);

        }
        else if (temp.at(0) == '+')
        {
            temp = temp.substr(1);
        }
        else
        {
            temp = temp;
        }

        if (temp.find('x') == -1)//if only constant present
        {
            in = 0;
            trig = " ";
            exponent = 0;
            out = stoi(temp);
        }
        else
        {
            if (temp.find("cos") != -1)
            {
                trigPosition = temp.find("cos");
                trig = "cos";
            }
            else if (temp.find("sin") != -1)
            {
                trigPosition = temp.find("sin");
                trig = "sin";
            }
            else if (temp.find("tan") != -1)
            {
                trigPosition = temp.find("tan");
                trig = "tan";
            }
            else
            {
                trigPosition = -1;
                trig = " ";
            }

            if (trigPosition != -1) //if trig is present
            {
                if (trigPosition == 0)	//there is no outer coefficient
                {
                    out = 1;
                    if (outerIsNeg)
                    {
                        out = out * -1;
                    }
                }
                else
                {
                    outCoeff = temp.substr(0, trigPosition);
                    out = stoi(outCoeff);
                    if (outerIsNeg)
                    {
                        out = out * -1;
                    }
                }

                int findX = temp.find('x');

                string innerCoeff = temp.substr(trigPosition + 3, findX); //finding inner coefficient
                if (trigPosition + 3 == findX)
                {
                    innerCoeff = "1";
                }

                in = stoi(innerCoeff);

                int findCarrot = temp.find('^');   //finding carrot to find exponent
                if (findCarrot != -1)
                {
                    string expCoeff = temp.substr(findCarrot + 1);
                    if (expCoeff.at(findCarrot + 1) == '-')
                    {
                        exponentIsNeg = true;
                    }
                    exponent = stoi(expCoeff);
                    if (exponentIsNeg)
                    {
                        exponent = exponent * -1;
                    }
                }
                else
                {
                    exponent = 1;
                }

            }
            else//trig is not present
            {
                outCoeff = temp.substr(0, temp.find('x'));
                if (outCoeff.length() == 0)
                {
                    outCoeff = "1";
                }
                out = stoi(outCoeff);
                if (outerIsNeg)
                {
                    out = out * -1;
                }

                in = 0;

                if (temp.find('^') != -1)
                {
                    string expnt = temp.substr(temp.find('^') + 1);
                    if (expnt.at(0) == '-')
                    {
                        exponentIsNeg = true;
                        expnt = expnt.substr(1);
                    }
                    exponent = stoi(expnt);
                    if (exponentIsNeg)
                    {
                        exponent = exponent * -1;
                    }

                }
                else			//if carrot symbol not present
                {
                    exponent = 1;
                }

            }
        }

        /*cout << "exp " << exponent << endl;
        cout << " in " << in << endl;
        cout << " out " << out << endl;
        cout << " trig " << trig << endl;*/

        if (exponent != 0)
        {
            if (count == 0)
            {
                Node* newNode = new Node(in, out, exponent, trig, nullptr);
                list1.setHead(newNode);
                prev = newNode;
            }
            else
            {

                Node* cur = new Node(in, out, exponent, trig, nullptr);

                prev->setNext(cur);

                if (str.length() == 0)
                {
                    //cout << "hello" << endl;
                    cur->setNext(nullptr);
                }

                prev = cur;

                list1 >> cur;

                //delete prev;
            }
        }

        count++;
    }

}

void derivative(LList& L)
{
    Node* traverse = L.getHead();

    while (traverse != nullptr)
    {
        if (traverse->getTrig() != " ")
        {
            traverse->setOuter((traverse->getOuter()) * (traverse->getInner()));
            if (traverse->getTrig() == "sin")
            {
                traverse->setTrig("cos");
            }
            else if (traverse->getTrig() == "cos")
            {
                traverse->setTrig("sin");
                traverse->setOuter((traverse->getOuter()) * -1);
            }
            else
            {
                traverse->setTrig("sec^2");
            }

        }
        else
        {
            //cout << "without trig" << endl;
            if ((traverse->getExp()) == 1)
            {
                traverse->setOuter(traverse->getOuter());
                traverse->setExp(0);
            }
            else
            {
                traverse->setOuter((traverse->getOuter()) * (traverse->getExp()));
                traverse->setExp((traverse->getExp()) - 1);
            }

            traverse->setInner(0);
        }

        

        traverse = traverse->getNext();
    }
}

int
main()
{
    LList list1;

    string filename;
    string str;
    cout << "enter in the file name" << endl;
    cin >> filename;

    ifstream inFile(filename);

    if (inFile)
    {
        while (getline(inFile, str))
        {
            string newString = removeWhiteSpace(str);
            StringParsing(newString, list1);
            derivative(list1);

            list1.sort();

            cout << list1;
            //cout<<"newline"<<endl;

            list1.setHead(nullptr);
        }
    }
    else
    {
        cout << "Invalid File name" << endl;
    }


    inFile.close();
}
