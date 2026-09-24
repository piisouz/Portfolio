#include <iostream>
#include <ctime>

using namespace std;

int main()
{

    int scissors = 1;
    int paper = 2;
    int rock = 3;
    int p1 = 0;
    srand(time(0));

    int tscore = 3;
    int ps = 0;
    int cs = 0;


    cout << "Welcome This game will keep going until theres a draw\n";



    while (ps != tscore && cs != tscore) {


        int c1 = 1 + (rand() % 3);
        cout << "*********************\n";
        cout << "1 for scissors\n";
        cout << "2 for rock\n";
        cout << "3 for paper\n";
        cout << "*********************\n";

        cout << "Enter a number\n";
        cout << "*********************\n";
        cin >> p1;

        if (p1 == scissors) {
            cout << "You entered Scissors\n";
            }
        else if (p1 == paper) {
            cout << "You entered Paper\n";
            }
        else if (p1 == rock) {
            cout << "You entered Rock\n";
            }



        if (c1 == 1) {
            cout << "The computer entered Scissors\n";
            }
        else if (c1 == 2) {
            cout << "The computer entered Paper\n";
            }
        else if (c1 == 3) {
            cout << "The computer entered Rock\n";
            }




        if (p1 == c1) {
            std::cout << "Draw\n";

            }
        else if ((p1 == scissors && c1 == paper) || (p1 == paper && c1 == rock) || (p1 == rock && c1 == scissors)) {
            cout << "You Win\n";
            ps += 1;  
        }
        else {
            cout << "You Lost\n";
            cs += 1;  
        }

        cout << "Score -> You: " << ps << " | Computer: " << cs << endl;
    }


    if (ps == tscore) {
        cout << "You win the game\n";
    }
    else if (cs == tscore) {

        cout << "You lose the computer won\n";
        }

    return 0;
}