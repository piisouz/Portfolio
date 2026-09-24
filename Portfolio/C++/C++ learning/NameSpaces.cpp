#include <iostream>
// allows a variable to be declared multiple times

namespace first{
    int x = 1;
}
namespace second{
    int x = 2;
}

//this allows x to be whatever namespace i choose it to be if i dont declared what namespace then it just uses the local entity inside the main function 

int main(){ 

    using namespace second; // in this function x == namespace 2 so when i print it it prints "2" 
    //however if i wanted to still use first name space i do as displayed below

    std::cout << first::x << '\n'; //uses the name space first and prints out the first namespace

    std::cout << x; 
    return 0;
}