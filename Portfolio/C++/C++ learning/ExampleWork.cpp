#include <iostream>
/*
    This is a example code test need to complete with 3 questions
    For each savings account your program must calculate the interest to be paid as follows: 
    If the account balance is higher than 10000 pounds, 
    or the account has not had any money debited for more than 30 days,
     then the interest is to be 6% of the balance. If not, the interest must be only 3%. 
     You should display the results of these calculations as follows
     (note that it does not matter if the display produces fewer figures after the decimal point):

*/



using namespace std;
const int MAXACCOUNTS =8;
int main(){

    int AccountNumber[MAXACCOUNTS] = {1001,7940, 4382, 2651, 3020, 7168, 6245, 9342};
    double Balance[MAXACCOUNTS] = {4254.40, 27006.25, 123.50, 85326.92, 657.0, 7423.34, 4.99, 107864.44};
    int DaysSinceDebited[MAXACCOUNTS] = {20, 35, 2, 14, 5, 360, 1, 45};
    int Intrest = 0;
    double New_Balance;

    if (Balance > 10000 || DaysSinceDebited > 30){
        Intrest = 6;
        cout << Intrest;
    }
    else{
        Intrest = 3;
        cout << Intrest;
    }





    return 0
}