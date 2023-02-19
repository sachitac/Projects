/******************************************************************************

Welcome to GDB Online.
GDB online is an online compiler and debugger tool for C, C++, Python, PHP, Ruby, 
C#, VB, Perl, Swift, Prolog, Javascript, Pascal, HTML, CSS, JS
Code, Compile, Run and Debug online from anywhere in world.

*******************************************************************************/
#include <iostream>
#include <iomanip>
#include <fstream>
#include <string>

using namespace std;

//all function prototypes 
void stringParsing(string str, float& xCoeff, float& yCoeff, float& zCoeff, float& constant);
void multiplyRow(float* ptr, int row, float multiple);
void switchrow(float* ptr, int rowA, int rowB);
void addrow(float* ptr, int rowA, int rowtoChange, float multiple);
bool echolonform(float* ptr, int size);
bool echolonRowCheck(float* ptr, int rowA);
void printEcholon(float* ptr, int size);
void printMatrix(float* ptr, int countline);

//beginning of main
int main()
{
	//declaring variables 
	float* beginning = 0;
	string fileName;
	string str;
	float xCoeff;
	float yCoeff;
	float zCoeff;
	float constant = 0;
	int countline = 0; //total number of lines in the file
	int numline = 1; //keeps track of the number of the line being read in the file
	int input, rowA, rowB;//user inputs these variables 
	float multiple; //user inputs this variable 
	bool echolonval;
	bool rowCheck;


	//prompting user to enter in name of file
	cout << "Enter the name of the file ";
	cin >> fileName;
	cout << endl;

	float* matrix; //declaring pointer to hold matrix array

	ifstream commandsfile(fileName); //creating read only filestream with filename

	if (commandsfile.is_open())//if file opens properly
	{
		while (getline(commandsfile, str)) //reading file line by line
		{
			countline++; //counting number of lines in the file
		}
 
		matrix = new float[4 * countline]; //creating array
		beginning = matrix; //making a new pointer equal to beginning of matrix

		commandsfile.clear(); //clearing all flags 
		commandsfile.seekg(ios::beg); //moving pointer back to the beginning of the file

		while (getline(commandsfile, str)) //reading file line by line
		{
			//initializing variables to zero 
			//each time you read a new line
			xCoeff = 0.0;
			yCoeff = 0.0;
			zCoeff = 0.0;

			//calling function to parse the string
			stringParsing(str, xCoeff, yCoeff, zCoeff, constant);

			if (numline == 1) //if first line is being read from file
			{
				//make index zero of matrix pointer hold value of the x coefficient
				*matrix = xCoeff;
			}
			else
			{
				//make matrix pointer hold value of the x coefficient
				*matrix = xCoeff;
			}
			matrix++; //increment matrix pointer by one
			*matrix = yCoeff; //make matrix pointer hold value of the y coefficient
			matrix++; //increment matrix pointer by one
			*matrix = zCoeff; //make matrix pointer hold value of the z coefficient
			matrix++; //increment matrix pointer by one
			*matrix = constant; //make matrix pointer hold value of the constant
			matrix++; //increment matrix pointer by one
			*matrix = '\0'; //make matrix pointer hold null terminator at the end of the line read

			//increment numline by one
			//tells wich number line we are on
			numline++;
		}//end of second while loop

		echolonval = echolonform(beginning, 4 * countline);
		if (echolonval) //if matrix is in echolon form
		{
			//call function print echolon to print values of coefficients
			printEcholon(beginning, 4 * countline);
			exit(0);
		}
		else
		{
			do { //beginning of do while loop

			//prompts user to enter in number for the corresponding task 
				cout << "If you want to switch rows please enter a 1" << endl;
				cout << "If you want to multiply a row by a number please enter a 2" << endl;
				cout << "If you want to add a scalar multiple of one row to another please enter a 3" << endl;
				cout << "If you want to quit please enter a 4" << endl;
				cin >> input;
	

				switch (input) //takes in value of the input
				{
				case 1: //if user entered 1

					//series of prompts for user to enter first and second row numbers they want to switch
					cout << "Please enter the first row number you want to switch ";
					cin >> rowA;
					/*while (rowA < 1 || rowA > countline)
					{
						cout << "Invalid Input" << endl;
						cout << "Please enter in a number between 1 and " << countline << endl;
						cin >> rowA;
					}*/
					cout << "Please enter the second row number you want to switch ";
					cin >> rowB;
					/*while (rowB < 1 || rowB > countline)
					{
						cout << "Invalid Input" << endl;
						cout << "Please enter in a number between 1 and " << countline << endl;
						cin >> rowB;
					}*/
					
					//calls on switchrow functions to switch rows
					if(rowA < 1 || rowA > 4 || rowB < 1 || rowB > 4)
					{
					   cout << "invalid input" << endl;
					}
               else
               {
                  switchrow(beginning, rowA, rowB); //passes user input as parameters
               }
               
					//calls on function to print changes made to matrix
					printMatrix(beginning, 4 * countline);
					//breaks case 1
					break;


				case 2: //if user entered in 2

					//series of prompts for user to enter in numbers needed to accomplish task
					cout << "Please enter the number of the row you want to multiply ";
					cin >> rowA;
					while (rowA < 1 || rowA > countline)
					{
						cout << "Invalid Input" << endl;
						cout << "Please enter in a number between 1 and " << countline << endl;
						cin >> rowA;
					}
					cout << "Please enter the number you want to multiply by ";
					cin >> multiple;
					cout << endl;

					rowCheck = echolonRowCheck(beginning, rowA);
					if (rowA >= 1 && rowA <= 4 && rowCheck && multiple != 0)
					{
						//calls on function to multiply desired row by multiple
						multiplyRow(beginning, rowA, multiple); //passes user input as parameters
					}

					//calls on function to print changes made to matrix
					printMatrix(beginning, 4 * countline);
					//breaks case 2
					break;

				case 3: //if user entered in 3

					//series of prompts for user to enter in numbers needed to accomplish task
					cout << "Please enter the number of the row you want to multiply ";
					cin >> rowA;
					while (rowA < 1 || rowA > countline)
					{
						cout << "Invalid Input" << endl;
						cout << "Please enter in a number between 1 and " << countline << endl;
						cin >> rowA;
					}
					cout << "Please enter the number you want to multiply by ";
					cin >> multiple;
					cout << "Please enter the row number you want to add to ";
					cin >> rowB;
					while (rowB < 1 || rowB > countline)
					{
						cout << "Invalid Input" << endl;
						cout << "Please enter in a number between 1 and " << countline << endl;
						cin >> rowB;
					}
					cout << endl;

					if (rowA >= 1 && rowA <= 4 && rowB >= 1 && rowB <= 4 && multiple != 0)
					{
						//calls on function to multiply by desired row by a number and add the row to another row
						addrow(beginning, rowA, rowB, multiple); //passes user input as parameters
					}
					else
					{
						cout << "invalid input" << endl;
					}

					//calls on function to print changes made to matrix
					printMatrix(beginning, 4 * countline);
					//breaks case 3
					break;

				case 4: //if user entered in 4
				
				echolonval = echolonform(beginning, 4 * countline);
				if (echolonval) //if matrix is in echolon form
				{
					//call function print echolon to print values of coefficients
					printEcholon(beginning, 4 * countline);
				}
				exit(0);

				default:
					cout << "Input is invalid" << endl;
				}
				
			} while (!echolonval); //continue to loop until user's input is greater than 4
		}//end of else statement
	}//end of if statement
	else //if file did not open properly
	{
		cout << "Invalid file" << endl; //ouput error message
	}

	commandsfile.close(); //close the commands file

	matrix = beginning;
	delete[] matrix;

}//end of main

//this function parses through the string backwards to find the x,y,z coefficients and constant
void stringParsing(string str, float& xCoeff, float& yCoeff, float& zCoeff, float& constant)
{
	//declaring variables 
	char lastVar;
	float coeffNum;
	string coeffstr;
	bool isNeg = false;

	string constantStr = str.substr(str.find('=') + 1); //holds substring from the equal sign to the end 

	constant = stof(constantStr); //converts numbers after equal sign from string to float
	str = str.substr(0, str.find('=')); //reduces string the beginning to right before the equal sign

	while (str.length() != 0) //loops while length of string is not zero
	{ 
		long int plusIndex = str.rfind('+'); //variable holds index value of plus sign's last appearance 
		long int negIndex = str.rfind('-'); //variable holds index value of neagtive sign's last appearance 

		if (plusIndex != -1 && negIndex != -1) //if plus sign and negative sign exist in the string
		{
			if (plusIndex > negIndex) //if index of plus sign is greater than the index of the negative sign
			{
				coeffstr = str.substr(plusIndex); //hold substring from the plus sign to the end in new variable
				str = str.substr(0, plusIndex); //shorten string itself to just the beginning to right before index of plus sign 
			}
			if (negIndex > plusIndex) //if index of negative sign is greater than the index of the plus sign
			{
				coeffstr = str.substr(negIndex); //hold substring from the negative sign to the end in new variable
				str = str.substr(0, negIndex); //shorten string itself to just the beginning to right before index of negative sign 
			}
		}
		else if (plusIndex != -1 && negIndex == -1) //if plus sign exists but negative sign doesn't 
		{ 
			coeffstr = str.substr(plusIndex); //hold substring from the plus sign to the end in new variable
			str = str.substr(0, plusIndex); //shorten string itself to just the beginning to right before index of plus sign
		}
		else if (negIndex != -1 && plusIndex == -1)  //if negative sign exists but plus sign doesn't 
		{
			coeffstr = str.substr(negIndex); //hold substring from the negative sign to the end in new variable
			str = str.substr(0, negIndex); //shorten string itself to just the beginning to right before index of plus sign 
		}
		else //if string does not contain plus or negative sign
		{
			coeffstr = str; //hold the remaining string in variable coeffstr
			str = ""; //shorten string to length of zero
		}

		if (coeffstr.at(0) == '-') //if there is a negative sign at beginning of coeffstr string
		{
			isNeg = true; //variable isNeg is true
			coeffstr = coeffstr.substr(1); //shorten substring to everything after the negative sign
		}
		else if (coeffstr.at(0) == '+') //if there is a plus sign at the beginning of coeffstr string
		{
			isNeg = false; //variable isNeg is false
			coeffstr = coeffstr.substr(1); //shorten substring to everything after the plus sign
		}
		else //if there is neither a plus or negative sign at the beginning of coeffstr
		{
			isNeg = false; //set variable isNeg to false
		}

		if (coeffstr.length() == 1) //if length of coeffstr is one (there is no coefficient in front of variable)
		{
			coeffNum = 1; //set coeffnum to one
			lastVar = coeffstr.at(0); //make lastVar hold variable in coeffstr 
		}
		else //if there is a coefficient in front of variable
		{
			lastVar = coeffstr.at(coeffstr.length() - 1); //make lastVar hold very last character of the string
			coeffstr = coeffstr.substr(0, coeffstr.length() - 1); //shorten coeffstr to not include very last character of the string
			coeffNum = stof(coeffstr); //convert coeffstr from string to float
		}

		if (isNeg) //if isNeg is true
		{
			coeffNum = 0 - coeffNum; //make coeffnum value negative by substracting it from zero
		}

		if (lastVar == 'x') //if lastVar is x
		{
			xCoeff = coeffNum; //assign coeffnum to variable xCoeff
		}
		else if (lastVar == 'y') //if lastVar is y
		{
			yCoeff = coeffNum; //assign coeffnum to variable yCoeff
		}
		else //if lastVar is z
		{
			zCoeff = coeffNum; //assign coeffnum to variable zCoeff
		}
	}//end of while loop
}//end of stringParsing()

//multiplies a row by a number
void multiplyRow(float* ptr, int row, float multiple)
{
	//moves pointer to the beginning of wanted row
	ptr = ptr + 4 * (row - 1);

	for (int i = 0; i < 4; i++)//loops through all values in the row
	{
		*ptr = *ptr * multiple; //multiplies current value at pointer by a number
		ptr++; //increments pointer by one

		if (i == 3)//if at the end of the row
		{			
			ptr--; //move pointer back one
		}
	}
}//end of multiplyRow

//switches two rows
void switchrow(float* ptr, int rowA, int rowB)
{
	float temp;
	float* row1 = ptr + 4 * (rowA - 1); //moves pointer to beginning of rowA
	float* row2 = ptr + 4 * (rowB - 1); //moves pointer to beginning of rowB

	for (int i = 0; i < 4; i++) // loops through all values in the row
	{
		temp = *row1; //stores value at pointer row1 in temp
		*row1 = *row2; //stores value at pointer row1 in row2
		*row2 = temp; //stores value at pointer row2 in row1

		//increments both pointers by one
		row1++;
		row2++;

		if (i == 3) //if at the end of the row
		{
			//move pointers back one
			row1--;
			row2--;
		}
	}
} //end of function switchrow

//multiplies a row by number and adds the multiplied row to another row
void addrow(float* ptr, int rowA, int rowtoChange, float multiple)
{
	float temp;
	float* row1 = ptr + 4 * (rowA - 1); //moves pointer to beginning of rowA
	float* row2 = ptr + 4 * (rowtoChange - 1); //moves pointer to beginning of rowB

	for (int i = 0; i < 4; i++) // loops through all values in the row
	{
		temp = *row1 * multiple; //mulitples value in row1 my number
		*row2 = *row2 + temp; //adds multiplied row1 value to row2


		//increments both pointers by one
		row1++;
		row2++;
	}
} //end of function addrow

//checks to see if the entire matrix is in echolon form 
bool echolonform(float* ptr, int size)
{
	//declaring variables  
	bool result;
	int fail = 0; //counts number of non-echolon numbers are in the matrix

	for (int i = 0; i < size; i++) //loops through the entire matrix
	{
		if (i % 5 == 0) //if i is divisible by 5
		{
			if (i == 15) //constant number for 4th row
			{
				if (*ptr == 0) //if constant of fourth row is zero
				{
					result = true; //assign true to result
				}
				else //if constant number for 4th row isn't zero
				{
					//increment fail variable by one
					fail++; //increment fail variable by one
				}
			}
			else if (*ptr == 1) //if value at ptr is one
			{
				result = true; //assign true to result
			}
			else //if constant number for 4th row isn't zero
			{
				result = false; //assign false to result
				fail++; //increment fail variable by one
			}
		}
		else //if i is not divisible by 5
		{
			if ((i + 1) % 4 != 0) //number not a constant
			{
				if (*ptr == 0) //if value at ptr is one
				{
					result = true; //assign true to result
				}
				else
				{
					result = false; //assign false to result
					fail++; //increment fail variable by one
				}
			}
		}

		ptr++; //increments pointer by one
	} // end of for loop

	if (fail != 0) //if fail variable does not equal zero
	{
		result = false; //assign false to result
	}
	else //if fail variable does equal zero
	{
		result = true; //assign true to result
	}

	return result; //returns result
}//end of function echolonform

//checks to see if a particular row is in echolon form
bool echolonRowCheck(float* ptr, int rowA)
{
	bool isOne = false; //checks to see if a one is in the right place
	int isZero = 0; //counts the number of zeroes in the row

	ptr = ptr + 4 * (rowA - 1); //moves pointer to beginning of rowA

	for (int i = 0; i < 4; i++) //loops through all values in the row
	{
		switch (rowA) //takes rowA as input value
		{
		case 1: //if rowA is one
			if (i == 0) //if i is zero/ in place of xCoeff
			{
				if (*ptr == 1) //if ptr holds one at current location
				{
					isOne = true; //set isOne to true
				}
			}
			else
			{
				if (i != 3 && *ptr == 0) //if i is not at the place of the constant and does equal zero
				{
					isZero++; //increments isZero by one
				}
			}
			break; //breaks case 1
		case 2:  //if rowA is two
			if (i == 1) //if i is one/ in place of yCoeff
			{
				if (*ptr == 1) //if ptr holds one at current location
				{
					isOne = true; //set isOne to true
				}
			}
			else
			{ 
				if (i != 3 && *ptr == 0) //if i is not at the place of the constant and does equal zero
				{
					isZero++; //increments isZero by one
				}
			}
			break;  //breaks case 2
		case 3:  //if rowA is three
			if (i == 2)//if i is two/ in place of zCoeff
			{
				if (*ptr == 1) //if ptr holds one at current location
				{
					isOne = true; //set isOne to true
				}
			}
			else
			{
				if (i != 3 && *ptr == 0) //if i is not at the place of the constant and does equal zero
				{
					isZero++; //increments isZero by one
				}
			}
			break; //breaks case 3
		case 4:  //if rowA is four
			if (*ptr == 0) //if ptr holds zero at current location
			{
				isZero++; //increments isZero by one
			}
			break;  //breaks case 4
		}

		ptr++; //increments pointer by one
	} //end of for loop

	if (rowA == 4 && isZero == 4) //if row 4 has 4 zeroes
	{
		return false; //returns false
	}
	else if (isOne && isZero == 2) //if one is at the correct place and there are two zeroes
	{
		return false; //returns false
	}
	else //if row is not in echolon form
	{
		return true; //returns true
	}

	return 0;
} //end of function echolonRowCheck

//prints what the variables are equal to in echolon form
void printEcholon(float* ptr, int size)
{
	float constant;
	char variable;

	cout << fixed << setprecision(2);   //prints number to two decimal places
	 
	for (int i = 0; i < size; i++) //loops through the entire matrix 
	{
		if (*ptr == 1) //value at pointer is one
		{
			if (i == 0) //if at column 1
			{
				variable = 'x'; //sets variable to x
				ptr = ptr + 3; //increments by 3
				constant = *ptr; //makes constant equal to value at ptr

				cout << variable << " = " << constant << endl; //prints x coefficient value
			} 
			else if (i == 2) //if at column 2
			{
				variable = 'y'; //sets variable to y
				ptr = ptr + 2; //increments by 2
				constant = *ptr; //makes constant equal to value at ptr

				cout << variable << " = " << constant << endl; //prints x coefficient value
			}
			else //if at column 3
			{
				variable = 'z'; //sets variable to z
				ptr = ptr + 1; //increments by 1
				constant = *ptr; //makes constant equal to value at ptr

				cout << variable << " = " << constant << endl; //prints x coefficient value
			}
		}
		ptr++; //increments ptr by one
	} //end of for loop
}

//prints the matrix
void printMatrix(float* ptr, int size)
{
	cout << fixed << setprecision(2); //prints number to two decimal places
	for (int i = 1; i <= size; i++) //loops through the entire matrix 
	{
		float* num = ptr + (i - 1); //sets num to value pointer is currently holding
		if (*num == -0) //if num is a negative zero
		{
			*num = 0; //sets value at num to just zero
		}
		cout << *(num) << " "; //prints a space between each number
		if (i != 0 && i % 4 == 0) //if at the end of the row
		{
			cout << endl; //prints a new line
		}
	} //end of for loop
} //end of printMatrix function

