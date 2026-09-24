#include <iostream>

//function is a block of reuseable code

void HappyBday(){ // this is how u declare a function just like main without the return bit
    std::cout << "Happy bday";

}

int main(){
    std::string q;

    std::cout << "Is it your bday ?";
    std::cin >> q;

    if(q == "yes"){
        HappyBday();
    }
    else{
        std::cout << "Ok";
    }

    return 0;
}