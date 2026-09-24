#include <iostream>
#include <ctime>

using namespace std;

int main(){

    int guess;

    int rnum = 1 + (rand() % 100);


    while(guess != rnum){
        cout << "Enter a number between 1-100 ";
        cin >>  guess;
        
        if(guess > rnum){
            cout << "Number too high guess again ";
        }
        else if(guess < rnum){

            cout << "Number too low guess again ";
        }
        else{

            cout << "Number is correct ";
        }

    }



    return 0;
}