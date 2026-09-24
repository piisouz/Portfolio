#include <iostream>

// this is a declared multiple times however to do this it has to have differnt parameters

void pizza(); // step 1 declared a function 
void pizza(std::string t1); // step 5 and 6 i declared the two edited functions here
void pizza(std::string t1, std::string t2);

int main(){

    pizza("sasuage", "bacon"); // step 7 in the main section i put the function
    return 0;
}

void pizza(){ // step 2 made what the base function does down here

    std::cout << "Heres you pizza\n";
}

void pizza(std::string t1){ // step 3 added parameters to the function thengave that a task
    std::cout << "your pizza has " << t1 << " on it\n";
}
void pizza(std::string t1, std::string t2){ // step 4 added another set of parameters
    std::cout << "your pizza has " << t1 << " on it\n" << " and " << t2;
}