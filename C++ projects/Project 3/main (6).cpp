#include <iostream>
#include<fstream>
#include<string>
#include<cctype>

using namespace std;

int count = 1;

struct Node
{
  Node * left = nullptr;
  Node* right = nullptr;
  Node* up = nullptr;
  Node* down = nullptr;
  Node* next = nullptr;
  char draw = ' ';
};


Node* linkRow()
{
    Node* newHead = new Node();
    Node* prev = newHead;
    
    for(int i = 0; i < 50; i++)
    {
        Node* current = new Node();
        
        prev -> right = current;
        current -> left = prev;
        prev = current;
    }
    
    return newHead;
}

Node* linkage(Node* headofPrevRow)
{
    Node* holdNewHead = linkRow();
    Node* currentRow = holdNewHead;
    Node* prevRow = headofPrevRow;
    
    for(int i = 0; i < 50; i++)
    {
        //cout << "current " << currentRow << endl;
        //cout << "prevRow " << prevRow << endl;
        prevRow -> down = currentRow;
        currentRow -> up = prevRow;
        currentRow = currentRow -> right;
        prevRow = prevRow -> right;
    }
    
    return holdNewHead;
}

void printToConsole(Node* current, Node* start)
{
    Node* beginningofRow = start;
    cout << current -> draw;
    current = current -> right;
    
    if(current == nullptr)
    {
        beginningofRow = beginningofRow -> down;
        current = beginningofRow;
        
        if(beginningofRow == nullptr) //base case
        {
            cout << endl;
            cout << endl;
            return;
        }
        
        cout << endl;
    }
    
    printToConsole(current, beginningofRow);
}

void printToFile(ofstream &outFile, Node* current, Node* start, long int place)
{
    outFile.seekp(place);
    Node* beginningofRow = start;
    outFile << current -> draw;
    current = current -> right;
    
    if(current == nullptr)
    {
        beginningofRow = beginningofRow -> down;
        current = beginningofRow;
        
        if(beginningofRow == nullptr) //base case
        {
            return;
        }
        
        outFile << endl;
    }
    
    printToFile(outFile, current, beginningofRow, outFile.tellp());
}

void leftRight(Node* &current, bool penStat, int numDirect, string direct, bool bold)
{
    if(penStat == false)
    {
        for(int i = 0; i < numDirect; i++)
        {
            if(direct == "E")
            {
                current = current -> right;
            }
            if(direct == "W")
            {
                current = current -> left;
            }
        }
    }
    else
    {
        for(int i = 0; i < numDirect; i++)
        {
            if(direct == "E")
            {
                current = current -> right;
                if(bold)
                {
                    current -> draw = '#';
                }
                else
                {
                    current -> draw = '*';
                }
            }
            else
            {
                current = current -> left;
                if(bold)
                {
                    current -> draw = '#';
                }
                else
                {
                    current -> draw = '*';
                }
            }
        }
    }
}

void upDown(Node* &current, bool penStat, int numDirect, string direct, bool bold)
{
     if(penStat == false)
    {
        for(int i = 0; i < numDirect; i++)
        {
            if(direct == "N")
            {
                current = current -> up;
            }
            if(direct == "S")
            {
                current = current -> down;
            }
        }
    }
    else
    {
        for(int i = 0; i < numDirect; i++)
        {
            if(direct == "N")
            {
                current = current -> up;
                if(bold)
                {
                    current -> draw = '#';
                }
                else
                {
                    current -> draw = '*';
                }
            }
            else
            {
                current = current -> down;
                if(bold)
                {
                    current -> draw = '#';
                }
                else
                {
                    current -> draw = '*';
                }
            }
        }
    }
}

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
  
    return true;
}

int main()
{
    
    string str = " ";
    bool penStatus;
    string direction = " ";
    int numDirection = 0;
    bool bold = false;
    bool print = false;
    string filename;
    
    Node* headofRow = linkRow();
    Node* startofGrid = headofRow;
    Node* rowHead = headofRow;
    
    Node* current = headofRow;
    
    for(int i = 0; i < 50; i++)
    {
        rowHead = linkage(rowHead); 
    }
    
    //printToConsole(startofGrid);
    
    cout << "Please enter the filename";
    cin >> filename;
    
    ifstream inFile(filename);
    ofstream outFile("paint.txt");
    
    if(inFile)
    {
        while(getline(inFile, str))
        {
          int result = isValid(str, penStatus, direction, numDirection, bold, print);
          
          if(result)
          {
            if(direction == "E" || direction == "W")
            {
                leftRight(current, penStatus, numDirection, direction, bold);
            }
            else
            {
                upDown(current, penStatus, numDirection, direction, bold);
            }
          
            if(print)
            {
                printToConsole(startofGrid,startofGrid);
            }  
          }
          else
          {
              continue;
          }
        }
    }
    
    printToFile(outFile, startofGrid, startofGrid, 0);
    
    inFile.close();
    outFile.close();
}

