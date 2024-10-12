/*
 * Sean Zamora
 * ITSE 2317
 * 9/8/2024
 * Write a program that can be used as a math tutor for a young student
*/
import java.util.*;
public class Math_Tutor {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        Random rand = new Random();//Declaration of random variable to allow for random selection
        int num1 = rand.nextInt(500) + 1;//+1 to account for 0
        int num2 = rand.nextInt(500) + 1;
        int userSelect;
        int answer = 0;
        int userAnswer;

        //Opening welcome message to user
        System.out.println("Welcome to your math tutor!");
        System.out.println("Please choose the operation by entering 1 or 2.");
        System.out.println("1. Addition");
        System.out.println("2. Subtraction");

        userSelect = scnr.nextInt();//Input for user's selected operation

        //Validation check of selected operation
        if(userSelect == 1){
            answer = num1 + num2;
            System.out.println(num1 + " + " + num2 + " = ?");
        }
        else if(userSelect == 2){
            answer = num1 - num2;
            System.out.println(num1 + " - " + num2 + " = ?");
        }
        else{
            System.out.println("Invalid input");
            scnr.close();
            return;
        }

        userAnswer = scnr.nextInt();//Input for user's answer

        //Validation of answer give to correct answer
        if(userAnswer == answer){
            System.out.println("That's correct!");
            scnr.close();
            return;
        }
        else{
            System.out.println("Sorry, the correct answer is: " + answer);
            scnr.close();
            return;
        }
    }
}
