import java.util.Scanner;



public class Logging {

    String User;
    String Password;

    public Logging(String User,String Password){
        this.User = User;
        this.Password = Password;

    }


    void displayInfo(){
        System.out.println("Welcome: " + User);


    }

    public static void main(String []args){
        Scanner input = new Scanner(System.in);


        Logging[] logging = new Logging[100];

        System.out.println("Do you have a username and password? 1 for yes 2 for no");
        int answer = input.nextInt();
        input.nextLine();
        if(answer == 1){
            System.out.println("Enter your Username");
            String username = input.nextLine();
            System.out.println("Enter your Password");
            String password = input.nextLine();



        }
        else if(answer == 2){
            System.out.println("Enter your Username");
            String username = input.nextLine();
            System.out.println("Enter your Password");
            String password = input.nextLine();

            logging[0] = new Logging(username, password);
            logging[0].displayInfo();


        }
        else{
            System.out.println("Error");
        }






    }
}
