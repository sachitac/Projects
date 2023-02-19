#include "LList.h"
#include <iostream>

LList::LList()
{
    head = nullptr;
}
LList::LList(Node* n)//overloaded constructor
{
    head = n;
}
LList::LList(LList& p)//copy constructor
{
    Node* newHead = new Node;
    Node* copyListHead = newHead;
    Node* original = p.getHead();

    copyListHead->setOuter(original->getOuter());
    copyListHead->setInner(original->getInner());
    copyListHead->setTrig(original->getTrig());
    copyListHead->setExp(original->getExp());
    original = original->getNext();
    setHead(copyListHead);

    while (original != nullptr)
    {
        Node* n = new Node;
        copyListHead->setNext(n);
        copyListHead = copyListHead->getNext();
        copyListHead->setOuter(original->getOuter());
        copyListHead->setInner(original->getInner());
        copyListHead->setTrig(original->getTrig());
        copyListHead->setExp(original->getExp());
        original = original->getNext();
    }

}
LList::~LList()//destructor
{
    delete head;
}


void LList::sort()
{
    bool flag = false;
    Node* cur = head;//////////////////
    Node* holdSmaller = nullptr;
    Node* holdBigger = nullptr;

    do {
        Node* beforeSmaller = nullptr;
        Node* afterBigger = nullptr;
        flag = false;
        cur = head;/////////////////
        while (cur->getNext() != nullptr)
        {
            if (cur->getExp() < (cur->getNext()->getExp())) 
            {
                holdSmaller = cur;
                holdBigger = cur->getNext();
                if (cur->getNext()->getNext() == nullptr)
                {
                    afterBigger = nullptr;
                }
                else
                {
                    afterBigger = holdBigger->getNext();
                }

                if (holdSmaller == head)////////////////////////////////////////////
                {
                    holdSmaller->setNext(afterBigger);
                    holdBigger->setNext(holdSmaller);
                    setHead(holdBigger);//////////////////////////////
                }
                else
                {
                    Node* traverse = head;//////////////////////////////////////
                    while (traverse != nullptr)
                    {
                        if (traverse->getNext() == holdSmaller)
                        {
                            beforeSmaller = traverse;
                        }

                        traverse = traverse->getNext();
                    }

                    beforeSmaller->setNext(holdBigger);
                    holdBigger->setNext(holdSmaller);
                    holdSmaller->setNext(afterBigger);
                }

                cur = holdBigger;
                flag = true;
            }

            cur = cur->getNext();
        }
    } while (flag);
}

//accessor
Node* LList::getHead() { return head; }

//mutator
void LList::setHead(Node* n) { head = n; }

Node* LList::operator[](int n) const
{
    int count = 0;
    Node* cur = head;

    while (count < n)
    {
        cur = cur->getNext();
        count++;
    }

    return cur;
}


std::ostream& operator << (std::ostream& out, const LList& L)
{
    Node* traverse = L.head;
    int i = 0;

    while (traverse != nullptr)
    {
        if (i == 0)
        {
            if (traverse->getOuter() < 0)
            {
                traverse->setOuter((traverse->getOuter()) * -1);
                out << "-" << L[i];
                traverse->setOuter((traverse->getOuter()) * -1);
            }
            else
            {
                out << L[i];
            }
        }
        else
        {
            if (traverse->getOuter() < 0)
            {
                traverse->setOuter((traverse->getOuter()) * -1);
                out << " - " << L[i];
                traverse->setOuter((traverse->getOuter()) * -1);
            }
            else
            {
                out << " + " << L[i];
            }
        }


        traverse = traverse->getNext();
        i++;
    }
    out << std::endl;
}

Node* LList::operator >> (Node* ptr)
{
    if (head != nullptr)
    {
        ptr->setNext(head);
        head = ptr;
    }
    else
    {
        head = ptr;
    }

    return head;
}

