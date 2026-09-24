#include <iostream>

int main()

// tenary operator ?: = replacement to a if/else statement
// conditions ? epression1 : expression2;



{

    int grade;

    std::cout << "Enter your grade";
    std::cin >> grade;


    // grade >= 60 ? std::cout << "You Passed" : std::cout << "You fail";
    std::cout << (grade >= 60 ? "You Passed" : "You fail");










    return 0 ;
}