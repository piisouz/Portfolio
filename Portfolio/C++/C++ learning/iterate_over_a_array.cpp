#include <iostream>

using namespace std;

int main(){

    string students[] = {"Alameen","Peter","Lisa"};

    for(int i = 0; i < sizeof(students)/sizeof(string); i++){ // i is 0 and whilst its smaller than the size of the array then it prints students names and increases by 1 each time 
        cout << students[i] << endl; // output students for i 
    }



    return 0;
}