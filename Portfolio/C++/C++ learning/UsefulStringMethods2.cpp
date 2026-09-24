#include <iostream>
#include <string>
using namespace std;

int main(){

    string name;

    cout << "Enter your name: ";
    getline(cin, name);

    /*if(name.length() > 12){
        cout << "Your name cant be over 12 characters";
    }
    This checks the length of the entry and checks if its bigger than 12
    */ 

   /*
   if(name.empty()){

        cout << "You havent entered anything...";
   }
    This checks if the entry was empty
    */

   // name.clear(); clears the variable

   /*
   name.append("@gmail.com");
   cout << name;
   This adds onto the variable
   */

    /*
    cout << name.at(0);
    prints a specific position of a character
    */

   /*
   name.insert(indexPoint, "What you want to insert");
   inserts stuff into a position of the input
   */

    /*
    cout << name.find(What you want to find);
    this displays how many times what you want to find is displayed
    */

   /*
   
   name.erase(first point, second point);
   erases characters from a certain point
   
   */

    else{
        cout << "Welcome " << name;
    }





    return 0;
}