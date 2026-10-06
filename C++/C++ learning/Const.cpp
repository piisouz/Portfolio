#include <iostream>
// the const keyword specifies that the varibles value is constant making it read only

int main(){ 
    
    const double PIE = 3.14159; //makes this varible a constant so you cant change it
    double radius = 6;
    double circumference = 2 * PIE * radius;

    std::cout << circumference;

    return 0;
}