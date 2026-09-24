#include <iostream>

//cout >> = output
//cin << = input


main(){

    std::string name;
    
    std::cout << "Whats your full name";
    std::getline(std::cin, name); // this is to get multiple words from the input
    // if not use this line std::cin >> name; 


    std::cout <<"Your full name is: " << name;


    return 0;
}