#include "Node.h"
#include <iostream>

//mutators/setters
void Node::setOuter(int out) { outer = out; }
void Node::setInner(int in) { inner = in; }
void Node::setExp(int exponent) { exp = exponent; }
void Node::setTrig(std::string trignmtry) { trig = trignmtry; }
void Node::setNext(Node* n) { next = n; }

//accessors
int Node::getOuter() const { return outer; }
int Node::getInner() const { return inner; }
int Node::getExp() const { return exp; }
std::string Node::getTrig() const { return trig; }
Node* Node::getNext() { return next; }

//constructor
Node::Node() {
    outer = 0;
    inner = 0;
    exp = 0;
    trig = " ";
    next = nullptr;
}

Node::Node(int in, int out, int exponent, std::string trignometry, Node* n)
{
    inner = in;
    outer = out;
    exp = exponent;
    trig = trignometry;
    next = n;
}

std::ostream& operator <<(std::ostream& out, Node* ptr)
{
    if (ptr->getExp() == 0)
    {
        out << ptr->getOuter();
    }
    else if (ptr->getTrig() != " ")
    {
        if (ptr->getInner() == 1)
        {
            if (ptr->getOuter() == 1)
            {
                out << ptr->getTrig() << " " << "x";
            }
            else
            {
                out << ptr->getOuter() << ptr->getTrig() << " " << "x";
            }
        }
        else
        {
            if (ptr->getOuter() == 1)
            {
                out << ptr->getTrig() << " " << ptr->getInner() << "x";
            }
            else
            {
                out << ptr->getOuter() << ptr->getTrig() << " " << ptr->getInner() << "x";
            }
        }
    }
    else if (ptr->getExp() == 1)
    {
        out << ptr->getOuter() << "x";
    }
    else
    {
        out << ptr->getOuter() << "x^" << ptr->getExp();
    }

}