#include <iostream>
#include <string>
#include <bits/stdc++.h>


using namespace std;

class Book{
    private:
        int ID;
        string Title;
        string Author;
        int Quantity;
    
    
    public:
        Book() // defualt constructor
        {
            ID = 0;
            Title = "Null";
            Author = "Null";
            Quantity = 0;
        
            
        }
        
        Book(int id, string title, string author, int quantity) // parameterised constructor
        {
            ID = id;
            Title = title;
            Author = author;
            Quantity = quantity;
            
            
        }
        
        
        
        
        void display()
        {
            
            cout << "Book ID: " << ID << "| |" << "Book Title: " << Title << "| |" << "Author: " << Author << "| |" << "Quantity left: " << Quantity << endl;
            
        }
    
};

class Library{
    public:
        
        Book Storage[100]; // when trying to store a class u put the class infront of the arrayt
        Book b1;
        Book b2{147, "Gruffalo", "Rich amiri", 14};
        

        Library(){ // created a method so it allows you to add to the array
            Storage[0] = b1;
            Storage[1] = b2;

        }
    
    private:
    
};


int main() {
    
    Library lib; // create libary as a object
    
     
    
    lib.Storage[0].display();
    lib.Storage[1].display();

    return 0;
}