//deposit money withdraw show a balance
#include <iostream>
using namespace std;
double balance = 100000;

void withdraw(double &balance);
void deposit(double &balance);

int main(){

    int op;
    cout << "Welcome to the bank you currently have: " << balance << " Press 1 to withdraw or 2 to deposit or 3 to exit";
    cin >> op;


    do{
        cout << "Welcome to the bank you currently have: " << balance << " Press 1 to withdraw or 2 to deposit or 3 to exit";
        cin >> op;
        if(op == 1){
            withdraw(balance);
        }
        else if (op == 2){
            deposit(balance);
        }
        else{
            cout << "invalid entry";
            break;
        }
        
        


    }while(op != 3);

    return 0;
}

void deposit(double &balance){
    double dep;
    cout << "How much will you like to deposit";
    cin >> dep;
    balance += dep;
    cout << "The new balance is: " << balance;
    
}

void withdraw(double &balance){
    double with;
    cout << "You have: " << balance << "How much would you like to withdraw: ";
    cin >> with;
    balance -= with;
    cout << "Heres your new balance: " << balance;
}