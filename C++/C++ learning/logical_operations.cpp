#include <iostream>

main(){

    // && = checks if two conditions are true
    // || = checks if one is true
    // ! = acts as a not gate so does the complete oppisite

    int temp;

    std::cout << "Enter a temp";
    std::cin >> temp;

    if(temp > 0 && temp < 30){
        std::cout << "The temp is normal";

    }

    else if(temp > 30 || temp <= 40){
        std::cout << "The temp is pretty hot";

    }

    else{

        std::cout << "Invalid entry";
    }



    return 0;
}