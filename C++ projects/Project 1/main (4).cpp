/******************************************************************************

Welcome to GDB Online.
GDB online is an online compiler and debugger tool for C, C++, Python, Java, PHP, Ruby, Perl,
C#, VB, Swift, Pascal, Fortran, Haskell, Objective-C, Assembly, HTML, CSS, JS, SQLite, Prolog.
Code, Compile, Run and Debug online from anywhere in world.

*******************************************************************************/
//Sachita Chaliki sxc200091

#include <iostream>
#include<fstream>
#include <string>
#include <cctype>

using namespace std;

bool isValid(string str, bool& penStatus, string& direction, int& numDirection, bool& bold, bool& print);
int leftRight(fstream& datafile, bool penStat, int numDirect, string direct, bool bold);
int upDown(fstream& datafile, bool penStat, int numDirect, string direct, bool bold);
void printFile();

int main() {
    /////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////          DO  NOT CHANGE CODE BELOW THIS               //////////////
    /////////////////////////////////////////////////////////////////////////////////////////

    ifstream infile("paint_base.txt");
    ofstream outfile("paint.txt", ios::binary);
    string c;
    if (infile)
        while (getline(infile, c))
            outfile << c << "\n";

    infile.close();
    outfile.close();

    /////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////          DO  NOT CHANGE CODE ABOVE THIS               //////////////
    /////////////////////////////////////////////////////////////////////////////////////////
    
    string filename;
    string str = " ";
    bool penStatus = false;
    string direction = " ";
    int numDirection = 0;
    bool bold = false;
    bool print = false;
    bool result = false;

    cout << "Please enter the name of the file ";
    cin >> filename;
    cout << endl;
    
    ifstream commandsfile(filename);
    fstream datafile("paint.txt", ios::in | ios::out | ios::ate);
    datafile.clear();
    datafile.seekp(0, ios::beg);

    if (commandsfile)
    {
        while (getline(commandsfile, str))
        {
            //cout << str << endl;
            result = isValid(str, penStatus, direction, numDirection, bold, print);

            /*
            cout << "penStatus" << penStatus <<endl;
            cout << "direciton" << direction <<endl;
            cout << "numdirect" << numDirection <<endl;
            cout << "bold" << bold <<endl;
            cout << "print" << print <<endl;
           */

            if (result)
            {
                if (direction == "E" || direction == "W")
                {
                    int valueLR = leftRight(datafile, penStatus, numDirection, direction, bold);
                    if(valueLR == -1)
                    {
                        //cout << "skip line EW" << endl;
                        continue;
                    }
                }
                else
                {
                    int valueUP = upDown(datafile, penStatus, numDirection, direction, bold);
                    if(valueUP == -1)
                    {
                        //cout << "skip line NS" << endl;
                        continue;
                    }
                }

                if (print == 1)
                {
                    datafile.flush();
                    datafile.clear();

                    int position = datafile.tellp();

                    datafile.close();

                    printFile();

                    datafile.open("paint.txt", ios::in | ios::out);
                    datafile.seekp(position);

                }
            }
            else
            {
                continue;
            }
        }
    }
    else
    {
        cout << "Invalid File" << endl;
    }
    
    datafile.close();
    commandsfile.close();
} //end of main

   //checks if valid input is entered and return true if input valid or false if input invalid 
bool isValid(string str, bool& penStatus, string& direction, int& numDirection, bool& bold, bool& print)
{
    
    string draw = str.substr(0, str.find(','));
    str = str.substr(str.find(',') + 1);

    if (draw == "1")
    {
        penStatus = false;
    }
    else if (draw == "2")
    {
        penStatus = true;
    }
    else
    {
        return false;
    }

    draw = str.substr(0, str.find(','));
    str = str.substr(str.find(',') + 1);

    if (draw == "E" || draw == "W" || draw == "S" || draw == "N")
    {
        direction = draw;
    }
    else
    {
        return false;
    }

    draw = str.substr(0, str.find(','));
    str = str.substr(str.find(',') + 1);
    
    
    numDirection = stoi(draw);
    if(numDirection < 0)
    {
        return false;
    }
    
    //cout << "str " << str << endl; 
    
    if(str == "B,P")
    {
        bold = true;
        print = true;
    }
    else if(str == "B")
    {
        bold = true;
        print = false;
    }
    else if(str == "P")
    {
        print = true;
        bold = false;
    }
    else
    {
        print = false;
        bold = false;
    }

    /*if (str == "B,P")
    {
        bold = true;
        print = true;
    }
    if (str == "B")
    {
        bold = true;
        print = false;
    }
    if (str == "P")
    {
        print = true;
        bold = false;
    }
    else
    {
        bold = false;
        print = false;
    }*/
    
    //cout << "bold " << bold << endl;
    //cout << "Print " << print << endl;
    
    return true;
}

int leftRight(fstream& datafile, bool penStat, int numDirect, string direct, bool bold)
{
    //cout << "beginning position lr" << datafile.tellg() << endl;
    char draw = '*';
    long int curposition = 0;
    int cmod = 0;
    int charRem = 0;
    
    if(datafile.tellg() == 0)
    {
        curposition = 0;
    }
    else
    {
        if(direct == "E")
        {
            curposition = datafile.tellg();
            //cout << "position" << curposition << endl;
        }
        else
        {
            curposition = datafile.tellg();
        }
    }
    if(direct == "E")
    {
        //cout << "position" << curposition << endl;
        cmod = curposition % 51;
        if(cmod != 0)
        {
            cmod = cmod - 1;
        }
        charRem = 49 - cmod;
        
        /*
        cout << "cmod " << cmod << endl;
        cout << "charRem " << charRem << endl;
        cout << "numDirect" << numDirect << endl;*/
        
        if(numDirect > charRem)
        {
            return -1;
        }
    }
    else
    {
        cmod = curposition % 51;
        
        //cout << "cmod L " << cmod << endl;
        //cout << "numDirect L " << numDirect << endl;
        
        if(numDirect > (cmod+1))
        {
            return -1;
        }
    }

    
    if (penStat == 0)
    {
        if (direct == "W")
        {
            datafile.seekp((0 - numDirect), ios::cur);
        }
        else
        {
            datafile.seekp(numDirect + 1, ios::cur);
        }
    }
    else
    {
        if (bold == 1)
        {
            draw = '#';
        }
        
        if(direct == "E" && (datafile.tellp() == 0 || datafile.tellp() % 51 == 0))
        {
            //cout << "o cloumn" << endl;
            datafile.seekp(1, ios::cur);
        }

        if (direct == "W")
        {
            datafile.seekp((0 - numDirect - 1), ios::cur);
        }
        

        for (int i = 1; i <= numDirect; i++)
        {
            int peekChar = datafile.peek();
            //cout << "peekchar " << peekChar << endl;
            
            if(bold)
            {
	            if(peekChar == 42);
	            {
		            draw = '#';
	            }
            }
            
            datafile << draw;
            
            /*if(direct == "W")
            {
                datafile.seekp(0-i, ios::cur);
                datafile << draw;
            }*/
        }

        if (direct == "W")
        {
            datafile.seekp((0 - numDirect+ 1), ios::cur);
        }
        /*else
        {
            datafile.seekp(-1,ios::cur);
        }*/

        //cout << "ending position lr " << datafile.tellg() << endl;
    }
    return 0;
}

int upDown(fstream& datafile, bool penStat, int numDirect, string direct, bool bold)
{
    //cout << "beginning position ud" << datafile.tellg() << endl;
    
    char draw = '*';
    int curposition = 0;
    int cquotient = 0;
    int crem = 0;
    
    
    curposition = datafile.tellg();
    if(direct == "N")
    {
        cquotient = curposition / 51;
        
        // << "cquotient " << cquotient << endl;
        
        
        if(numDirect > cquotient)
        {
            return -1;
        }
    }
    else
    {
        cquotient = curposition / 51;
      
            if(numDirect > (49-cquotient))
            {
                return -1;
            }
 
    }


    if (bold == 1)
    {
        draw = '#';
    }
    if (penStat == 0)
    {
        if (direct == "S")
        {
            if (direct == "S")
            {
                datafile.seekp((numDirect * 51), ios::cur);
                
                //cout << "beginning position ud" << datafile.tellg() << endl;
            }
        }
        else
        {
            datafile.seekp(-(numDirect * 51), ios::cur);
        }
    }
    else
    {
        for (int i = 1; i <= numDirect; i++)
        {
            if (direct == "S")
            {
                datafile.seekp(50, ios::cur);
                
                
                /*char curChar = datafile.get();
                if(bold)
                {
                    if(curChar == '*')
                    {
                        draw == '#';
                    }
                }*/
                
                
                datafile << draw;
                //cout << "beginning position ud" << datafile.tellg() << endl;
            }
            else
            {
                datafile.seekp(-52, ios::cur);
                
                
                /*char curChar = datafile.get();
                if(bold)
                {
                    if(curChar == '*')
                    {
                        draw == '#';
                    }
                }*/
                
                datafile << draw;
            }
        }
    }
    
    return 0;
    //cout << "datafile.tellg()" << datafile.tellg() << endl;
}

void printFile()
{
    //datafile.clear();
    char draw;

    ifstream infile("paint.txt", ios::in);
    infile.seekg(0, ios::beg);

    while (infile.get(draw))
    {
        cout << draw;
    }

    infile.close();
}