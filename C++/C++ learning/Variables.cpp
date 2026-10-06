#include <iostream>

//Variables


int main(){

    int x; //declaration int x declares x as a integer
    x = 5; //assigment

    int y = 6; // another way of assigment
    int sum = x + y;


    //to get a float its called double

    double price = 10.99;

    //to get a single character

    char grade = 'A';
    char currency = '$';

    //boolean (true or false)

    bool ForSale = true;
    bool NotForSale = false;

    //String
    std::string name = "Alameen";



    std::cout << x << '\n'; 
    std::cout << y << '\n';
    std::cout << sum << '\n';
    std::cout << "The price is: " << price << '\n';
    std::cout << "The Grade is: " << grade << '\n';
    std::cout << "The currency is: " << currency << '\n';
    std::cout << "Hello " << name << '\n';

    return 0;  
}