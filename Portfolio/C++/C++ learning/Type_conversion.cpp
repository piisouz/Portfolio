#include <iostream>

//type conversion is the conversion of making one varible go from one data type to another
//theres explicit = manual and implicit = automatic


main(){

    int x = 3.14;

    std::cout << x << "\n"; // example of implicit where it automaticall gets rid of the decimal point anc changes it to a interger
    // instead of a double

    double y = (int) 3.14; // this is a example of explicit changing a data piece from double to intreger

    std::cout << y;

    return 0;
}