#include <iostream>

using namespace std;

const int MAXLAPTOPS = 5;

struct Latops{
    int serial;
    double price;
    
    };

int main(){
    Latops latop[MAXLAPTOPS] = {
        {1234, 350.0}, {5678, 750.0}, {9101, 1200.0}, {1121, 499.0}, {3141, 999.0}
    };   

    cout << "Serial" << "\t" << "Price B4 Discount" << "\t" << "After discount" << endl;

    double maxPrice = 0;
    double maxPriceSerial = 0;

    for(int i = 0; i < MAXLAPTOPS; i++){
        
        double discount = 0;
        if(latop[i].price < 500){
            discount = 50.0;
        }
        
        else if(latop[i].price > 1000){
            discount = 200.0;
        }
        
        else{
            discount = 100.0;
        }
        
        cout << latop[i].serial << "\t\t" << latop[i].price << "\t\t" << (latop[i].price - discount) << endl;

        if (latop[i].price > maxPrice) {

            maxPrice = latop[i].price;

            maxPriceSerial = latop[i].serial;

        }
         

    }
    

    cout << "The highest latop is " << maxPrice;


    return 0;
}