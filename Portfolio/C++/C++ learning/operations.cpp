#include <iostream>


using number_t = int; //change to a double when using division to allow for decimal points

main(){

    number_t students = 20;
    
    //students = students + 1;
    //shorter way of doing the line above students+=1;
    //if you only need to add one do students++;

    //students = students - 1;
    //shorter way of doing the line above students-=1;
    //if you only need to take one away do students--;


    //students = students * 1;
    //shorter way of doing the line above students*=1;

    //students = students / 1;
    //shorter way of doing the line above students/=1;

    number_t remainder = students % 3; // to find the remainder use the percentage key

    std::cout << remainder;


    return 0;
}