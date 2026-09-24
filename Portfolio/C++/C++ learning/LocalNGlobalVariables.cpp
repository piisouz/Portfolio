#include <iostream>

//local variables declared in a function or block of code
//global declared outside functions can be used at any time
int num2 = 2; //global variable

void idk();
int main(){

    int num = 1; // local variable
    idk();
    return 0;

}
void idk(){
    std::cout << num2;

}