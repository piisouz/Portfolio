#include <iostream>

// for loops executes a block of code a specific amount of times
int main(){
    for(int i = 1; i <= 3; i++){ // this basically calls i which is index 1 and says whilst its smaller than 3 this repeats and then does the variable +1 at the end to add one to it
        // you can change how much it goes up by by increasing i++ to i+=3 for example
        //std::cout << "Happy new year\n"; so this prints this line 3 times
        std::cout << i << "\n";
    }
    std::cout << "Happy new year\n";



    return 0;
}