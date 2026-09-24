#include <iostream>
using namespace std;



struct student{
    string name;
    double gpa;
    bool enrolled;
};



int main(){

    student student1;
    student1.name = "Alameen";student1.gpa = 52 ;student1.enrolled = true;

    cout << student1.name << endl;

    // members can be accessesed with a "."
    return 0;
}