#include <iostream>
using namespace std;

class Animal{
    public:
        bool alive = true;
    
    void eat(){
        cout << "this animal is eating";
    }
};


class Dog : public Animal{
    public:
    void bark(){
        cout << "Dog barks";
    }
};

class Cat : public Animal{
    public:

    void meow(){
        cout << "Cat purs";
    }
};


int main(){
    
    Cat cat;
    Dog dog;
    cout << dog.alive;
    cat.meow();

    return 0;
}