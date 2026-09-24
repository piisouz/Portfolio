#include <iostream>
#include <string>

using namespace std;        
int main(){

    string name;
    int num;
    cout << "Enter your name";
    getline(cin, name);

    
    while(name.empty()){

        cout << "Enter your name" ;
        getline(cin, name);
    }
    
    cout << "Hello " << name << "\n";

    do{
        cout << "Enter a positive number ";
        cin >> num;
    }while(num < 0); // a do while basically goes do this statement once then go to the while loop

    cout << "Number is " << num;


    return 0;
}