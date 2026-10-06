#include <iostream>

using namespace std;




class Human{
    public:
        string name; // attribute
        string job;
        int age;

        //methods
        void eat(){
            cout << "This person ate"<< endl;
        }
        void drink(){
            cout << "This person drank"<< endl;
        }
        void sleep(){
            cout << "This person slept" << endl;
        }


};



int main(){


    // objects = a collection of attributes and methods
    // attribuites = characteristics
    // method = actions they can perform



    Human human1;
    human1.name = "Alameen";
    human1.job = "Non";
    human1.age = 20;

    human1.eat();

    return 0;
}