#include <iostream>
#include <ctime>

using namespace std;

int main(){

    srand(time(0));
    int event = rand() % 5 + 1;

    switch(event){
        case 1: cout << "You won a car";
                break;
        case 2: cout << "You won a bike";
                break;
        case 3: cout << "You won a house";
                break;
        case 4: cout << "You won a money";
                break;
        case 5: cout << "You won a bbl demons";
                break;

    }


    return 0;
}