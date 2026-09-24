#include <iostream>

using namespace std;

const int MAXLAPTOPS = 5;

struct Latops{
    int serial;
    double price;
    
    };

double CalculateDiscount(double price){

    if(price < 500){
            return 50.0;
        }
        
        else if(price > 1000){
            return 200.0;
        }
        
        else{
            return 100.0;
        }
        
        

}

int main(){
    Latops latop[MAXLAPTOPS] = {
        {1234, 350.0}, {5678, 750.0}, {9101, 1200.0}, {1121, 499.0}, {3141, 999.0}
    };   

    cout << "Serial" << "\t" << "Price B4 Discount" << "\t" << "After discount" << endl;

    double maxPrice = 0;
    double maxPriceSerial = 0;

    for(int i = 0; i < MAXLAPTOPS; i++){
        
        double discount = CalculateDiscount(latop[i].price);
        
        cout << latop[i].serial << "\t\t" << latop[i].price << "\t\t" << (latop[i].price - discount) << endl;

        if (latop[i].price > maxPrice) {

            maxPrice = latop[i].price;

            maxPriceSerial = latop[i].serial;

        }
         

    }
    

    cout << "The highest latop is " << maxPrice;


    return 0;
}

