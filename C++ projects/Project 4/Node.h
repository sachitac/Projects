#pragma once

#ifndef NODE_H
#define NODE_H
#include <string>
#include <iostream>

class Node {
private:
    int outer;
    int inner;
    int exp;
    std::string trig;
    Node* next;

public:
    Node();
    Node(int, int, int, std::string, Node*);

    //mutators/setters
    void setOuter(int out);
    void setInner(int in);
    void setExp(int exponent);
    void setTrig(std::string trignometry);
    void setNext(Node*);

    //accessors
    int getOuter() const;
    int getInner() const;
    int getExp() const;
    std::string getTrig() const;
    Node* getNext();

    //overloaded operators
    friend std::ostream& operator <<(std::ostream& out, Node* ptr);
};

#endif



