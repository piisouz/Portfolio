#include <iostream>
#include <string>
#include <ctime>
#include <time.h>
#include <fstream>
#include <cstdlib>
#include <vector>
using namespace std;

int PlayerHealth = 100;
int MobHealth = 20;
int PlayerDamage = 10;
string name;
vector<string> Inventory = { "Broken Sword", "Healing Potion" };
string mob[] = { "Slime","Zombie","Skeleton" };
string Mob;
int n = sizeof(mob) / sizeof(mob[0]);
 
struct Weapon {
    string name;
    int damage;
    
    Weapon(string n, int dmg) : name(n), damage(dmg) {}

    void equip() const {
        cout << "You have equipped: " << name << endl;
    }
};
Weapon SlimeSword("Slime Sword", 20);
Weapon Bow("Bow", 24);
Weapon ZombieSword("Zombie Sword", 23);






void Reward(vector<string>& Inventory) {

    srand(static_cast<unsigned int>(time(0)));
    int randIndex = rand() % n;

    if (Mob == "Slime") {
        ifstream myfile("slimedrops.txt");
        string line;
        int i = 0;
        while (getline(myfile, line)) {
            if (i == randIndex) {
                cout << "You Found: " << line << "\n";
                Inventory.push_back(line);
                break;
            }
            i++;

        }
        myfile.close();
    }
    else if (Mob == "Zombie") {
        ifstream myfile("zombiedrops.txt");
        string line;
        int i = 0;
        while (getline(myfile, line)) {
            if (i == randIndex) {
                cout << "You Found: " << line << "\n";
                Inventory.push_back(line);
                break;
            }
            i++;

        }
        myfile.close();
    }

    else if (Mob == "Skeleton") {
        ifstream myfile("skeletondrops.txt");
        string line;
        int i = 0;
        while (getline(myfile, line)) {
            if (i == randIndex) {
                cout << "You Found: " << line << "\n";
                Inventory.push_back(line);
                break;
            }
            i++;

        }
        myfile.close();
    }

};
void Battle() {
    int choice;
    cout << "As your walking you encounter a " << Mob << endl;
    cout << "What will you do?" << endl;
    MobHealth = 20;

    while (PlayerHealth > 0 && MobHealth > 0) {
        cout << "Press 1 To Attack" << endl;

        cout << "Press 2 To Block" << endl;

        cout << "Press 3 To Heal" << endl;
        cin >> choice;


        if (choice == 1) {
            cout << "*********************\n";
            cout << "You attacked " << Mob << " For " << PlayerDamage << endl;
            cout << "The " << Mob << " attacked you for " << " For 5 damage" << endl;
            cout << "*********************\n";
            PlayerHealth -= 5;
            MobHealth -= PlayerDamage;

            cout << "Your health is: " << PlayerHealth << endl;

            cout << "The " << Mob << "'s health is: " << MobHealth;
            cout << "*********************\n";


        }

        else if (choice == 2) {

            cout << "*********************\n";
            cout << "You Blocked all Damage Your health is: " << PlayerHealth << endl;

            cout << "The " << Mob << "'s health is: " << MobHealth;
            cout << "*********************\n";


        }



        else if (choice == 3) {
            if (PlayerHealth == 100) {
                cout << "*********************\n";
                cout << "You are the maximum hp already" << endl;
                cout << "*********************\n";
            }
            else {
                cout << "You Healed";
                PlayerHealth += 20;
                cout << PlayerHealth << endl;
                cout << "*********************\n";
            }

        }
        else{
            cout << "Invalid Option";
        }


        if (PlayerHealth == 0 || MobHealth == 0) {

            cout << "The Fight is over " << endl;


            if (PlayerHealth > 0) {

                cout << "You win ";
                Reward(Inventory);
            }
            else {
                cout << "Game over";
            }
        }

    }


}

void Boss() {
    int choice;
    
    int Boss1 = 70;
    string Boss2 = "Necromancer";
    int Boss1D = 30;

    while (PlayerHealth > 0 && Boss1 > 0) {
        cout << "Press 1 To Attack" << endl;

        cout << "Press 2 To Block" << endl;

        cout << "Press 3 To Heal" << endl;
        cin >> choice;


        if (choice == 1) {
            cout << "*********************\n";
            cout << "You attacked " << Boss2 << " For " << PlayerDamage << endl;
            cout << "The " << Boss2 << " attacked you for " << Boss1D << endl;
            cout << "*********************\n";
            PlayerHealth -= Boss1D;
            Boss1 -= PlayerDamage;
            cout << "Your health is: " << PlayerHealth << endl;

            cout << "The " << Boss2 << "'s health is: " << Boss1;
            cout << "*********************\n";


        }

        else if (choice == 2) {

            cout << "*********************\n";
            cout << "You Blocked all Damage Your health is: " << PlayerHealth << endl;

            cout << "The " << Boss2 << "'s health is: " << Boss1;
            cout << "*********************\n";


        }



        else if (choice == 3) {
            if (PlayerHealth == 100) {
                cout << "*********************\n";
                cout << "You are the maximum hp already" << endl;
                cout << "*********************\n";
            }
            else {
                cout << "You Healed";
                PlayerHealth += 20;
                cout << PlayerHealth << endl;
                cout << "*********************\n";
            }

        }
        else{
            cout << "Invalid option";
        }


        if (PlayerHealth <= 0 || Boss1 <= 0) {

            cout << "The Fight is over " << endl;


            if (PlayerHealth > 0) {

                cout << "You win the game ";
                cout << "You have recived a Boss Trophy";
            }
            else {
                cout << "Game over";
            }
        }

    }


}






void inventory(vector<string>& Inventory) {
    int choice;
    cout << "\n Your inventory is: " << endl;
    for (int i = 0; i < Inventory.size(); ++i) {
        cout << i + 1 << "." << Inventory[i] << endl;

    }
}
void equip() {
    int choice;
    string weapon;
    cout << "*********************\n";
    cout << "Would you like to equip a Weapon? 1. For Yes 2. For No " << endl;
    cin >> choice;
    if (choice == 1) {
        
        cin.ignore();
        cout << "What Weapon? ";
        getline(cin, weapon);
        if (weapon == "Bow") {
            
            
            Bow.equip();
            PlayerDamage = Bow.damage;
        }
        else if (weapon == "Zombie Sword") {
            
            
            ZombieSword.equip();
            PlayerDamage = ZombieSword.damage;
        }
        else if (weapon == "Slime Sword") {
            
            SlimeSword.equip();
            PlayerDamage = SlimeSword.damage;
        }
        else {
            cout << "That isnt a weapon";
        }

    }
    else {
        cin.ignore();
        cout << "Ok!" << endl;

    }
}

int main() {

    srand(static_cast<unsigned int>(time(0)));
    int randIndex = rand() % n;
    Mob = mob[randIndex];


    cout << "*********************\n";
    cout << "Welcome to my adventure game!\n";
    cout << "*********************\n";
    cout << "Whats your name?\n";
    getline(cin, name);
    cout << "*********************\n";
    cout << "Its time to start Your adventure... " << name << endl;
    cout << "*********************\n";
    cout << "*********************\n";

    Battle();
    inventory(Inventory);
    equip();

    char exit;
    int level = 1;

    
    while (PlayerHealth > 0 && level < 5) {

        

        
        cout << "Are you ready for the next level Y for yes N for exit";
        cin >> exit;
        if (exit == 'Y' || exit == 'y') {
            randIndex = rand() % n;
            Mob = mob[randIndex];
            cout << "Level " << level << endl;
            
                Battle();
                inventory(Inventory);
                equip();
                level++;


            
        }
        else {
            break;
        }


    }
    cout << "*********************\n";
    cout << "Congratulations you have reached the final level" << endl;
    cout << "The Boss THE NECROMANCER HAS APPROACHED what will you do ?";
    cout << "*********************\n";
    Boss();








    return 0;
}








