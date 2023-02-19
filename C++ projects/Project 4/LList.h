#pragma once
#ifndef LLIST_H
#define LLIST_H
#include "Node.h"
#include <iostream>

class LList
{
private:
	Node* head;

public:
	//default constructor
	LList();
	LList(Node*);		//overloaded constructor
	LList(LList&);		//copy constructor
	~LList();			//destructor
	void sort();

	//accessor
	Node* getHead();
	//mutator
	void setHead(Node*);

	Node* operator[](int n) const;
	friend std::ostream& operator << (std::ostream& out, const LList& L);
	Node* operator >> (Node* ptr);
};
#endif
