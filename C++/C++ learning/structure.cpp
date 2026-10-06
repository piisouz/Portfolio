#include <iostream>
using namespace std;

struct NTU
    {
    int ntuID;
    double Average;
    int Year;
    };


int main(){

    const int NMax = 4;
    int LowestPupil = 0; // will contain registration number of weakest student
    int HighestPupil = 0;

    double LowestAverage =65.8; // will be updated every time we find a lower one
    NTU TheClass[NMax]; // allow space for Nmax pupil structs
   
    cout << "Input ur id";
    cin >> TheClass[0].ntuID;

    cout << "Input ur id";
    cin >> TheClass[1].ntuID;

    cout << "Input ur id";
    cin >> TheClass[2].ntuID;

    cout << "Input ur id";
    cin >> TheClass[3].ntuID; 

    cout << "Input the first average";
    cin >> TheClass[0].Average;

    cout << "Input the second average";
    cin >> TheClass[1].Average;

    cout << "Input the third average";
    cin >> TheClass[2].Average;

    cout << "Input the fourth average";
    cin >> TheClass[3].Average;

    for (int i =0; i<NMax; i++)
    {
    if (TheClass[i].Average<LowestAverage)
    {
    LowestPupil = TheClass[i]. ntuID;
    LowestAverage = TheClass[i].Average;
    }
    else{

        HighestPupil = TheClass[i]. ntuID;
    }
    }

    cout << "The worst student is: " << LowestPupil << endl;
    cout << "The top student is: " << HighestPupil;
    
    return 0;
}