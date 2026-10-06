#include <iostream>
#include <ctime>

using namespace std;


int main()
{
    int black = 21;
    string cards[14] = {"1","2","3","4","5","6","7","8","9","10","J","K","Q","A"};
    int rnum = 1 + (rand(cards));

    string hit;
    string stand;

    cout << "*********************\n";
    cout << "Welcome to BlackJack";
    cout << "*********************\n";
    cout << "Press a key to enter";
    cout << "*********************\n";
    cout << "*********************\n";
    cout << "*********************\n";
    
    cout << "You have recived: " card;
    cout << "The Dealer recived " dcard;
    cout << "*********************\n";
    cout << "You have recived: " card2;
    cout << "You now have: " card << " and " << card2;
    cout << "Your total is: " << total
    cout << "*********************\n";
    cout << "Would you like to hit\n";
    cout << "1: Yes\n";
    cout << "2: No\n";
    cin >> op;
    if(op == 1){
        PlayerTurn;
        cout << "You have recived: " card3;
        total += card3;
        cout << "Your total is: " << total
        if(total > 21){
            cout << "You have Busted and lost Game over";
        }
        else(total < 21){
            cout << "Would you like to Hit or Stand\n";
            cout << "1: Yes\n";
            cout << "2: No\n";
            cin >> op;
        }
        

        

    }
    else if(op = 2){
        DealerTurn;

    }
    else{
        cout "Invalid Option\n";
    }
    cout << "The dealer have recived: " dcard2;
    cout << "The dealer have: " dcard << " and " << dcard2;
    cout << "There total is: " << dtotal
    cout << "*********************\n";
    
    

    


    return 0;
}


