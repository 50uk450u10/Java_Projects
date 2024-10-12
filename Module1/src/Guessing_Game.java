/*
 * Sean Zamora
 * ITSE 2317
 * 9/13/2024
 * Generate a random number between 1 and 100, prompt the user to enter a number, then let the user know if the guess was too high, too low, or correct using a loop.
 */
import java.util.*;
public class Guessing_Game {
    public static void main(String[] args){
        Scanner scnr = new Scanner(System.in);//set scanner reference
        Random rnd = new Random();//set random reference
        int randNum = rnd.nextInt(100) + 1;//declare variable for random number

        System.out.println("Guess the Random Number (1 - 100)");//prompt user for input
        int userNum = scnr.nextInt();//take user input

        do{//initiate dowhile loop

            if(userNum > 100 || userNum < 1){//input validation
                System.out.println("Invalid input, enter a valid number.");
            }
            else{
                if(userNum > randNum){//check for user input greater than target number
                    System.out.println("Your number " + userNum + " is too high. Try Again.");
                }
                else if(userNum < randNum){//check for user input lower than target number
                    System.out.println("Your number " + userNum + " is too low. Try Again.");
                }
            }

            userNum = scnr.nextInt();//take input for next user number if previous misses target number

        } while(userNum != randNum);//exit dowhile loop when the correct number is guessed

        System.out.println("Great Guess! You Guessed the Random Number!");
        scnr.close();//close scanner
        return;//exit program
    }
}
