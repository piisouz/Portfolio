#include <iostream>


const int NUMPHONES = 5;
using namespace std;


int main(){







    double phonePrice[NUMPHONES] = {699.99, 849.99, 499.99, 999.99, 749.99};

    int warranty[NUMPHONES] = {0, 1, 2, 1, 0};  

    cout << "Price" << "\t" << "Warranty type" << "\t" << "Total Price" << endl;

    for(int i = 0; i < NUMPHONES; i++){
        
        double costs = 0;
        if(warranty[i] == 0 ){
            costs = 0;
        }
        
        else if(warranty[i] == 1){
            costs = 50;
        }
        
        else{
            costs = 100.0;
        }
        
        cout << phonePrice[i] << "\t\t" << (warranty[i] == 0 ? "Standard" : warranty[i] == 1 ? "Extended" : "Premium") << "\t\t" << (phonePrice[i] + costs) << endl;

    }
    
    return 0;
}