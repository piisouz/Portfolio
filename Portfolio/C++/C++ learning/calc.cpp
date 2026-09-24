#include <iostream>

main(){

    double num1;
    double num2;
    char op;

    std::cout << "Welcome to the Calculator \n";
    std::cout << "Enter the first number: ";
    std::cin >> num1;
    std::cout << "Enter the second number: ";
    std::cin >> num2;
    std::cout << "Enter a operation: ";
    std::cin >> op;

    if(op == '+'){

        num1+=num2;
        std::cout << "The number is: " << num1;
    }
    else if(op == '-'){

        num1-=num2;
        std::cout << "The number is: " << num1;
    }
    else if(op == '*'){

        num1*=num2;
        std::cout << "The number is: " << num1;
    }
    else if(op == '/'){

        num1/=num2;
        std::cout << "The number is: " << num1;
    }
    else{

        std::cout << "You've entered something invalid";
    }













    return 0;
}