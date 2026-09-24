#include <iostream>

/*
The program includes an array initialized with the prices of laptops and another array with the Serial Numbers for each laptop. If the laptop price is below £500, a discount of £50 is applied,
 whereas laptops priced above £1000 get a discount of £200. In between, the discount is £100.

You are to write the code that displays the serial number, basic price, and price after discount for each laptop.

*/



const int MAXLAPTOPS = 5;
using namespace std;


int main(){



    double price[MAXLAPTOPS] = {350.0, 750.0, 1200.0, 499.0, 999.0};

    int serial[MAXLAPTOPS] = {1234, 5678, 9101, 1121, 3141};

    cout << "Serial" << "\t" << "Price B4 Discount" << "\t" << "After discount" << endl;

    for(int i = 0; i < MAXLAPTOPS; i++){
        
        double discount = 0;
        if(price[i] < 500){
            discount = 50.0;
        }
        
        else if(price[i] > 1000){
            discount = 200.0;
        }
        
        else{
            discount = 100.0;
        }
        
        cout << serial[i] << "\t\t" << price[i] << "\t\t" << (price[i] - discount) << endl;

    }
    
    return 0;
}