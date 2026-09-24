#include <iostream>

typedef std::string text_t; // to create a typedef you do typedef then the function then what its called then _t at the end
using number_t = int; // another way is to use using which is better use the new datatype then the old datatype

int main(){

    //typedef = creates a nickname for a different data type

    text_t firstname = "Alameen"; // takes the function std::string and changes it to text_t without me having to repeat the code again
    number_t age = 20;
    
    std::cout << firstname << "\n";
    std::cout << age << "\n";



    return 0;
}