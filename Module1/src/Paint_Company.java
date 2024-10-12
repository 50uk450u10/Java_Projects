/*
 * Sean Zamora
 * ITSE 2317
 * 9/22/2024
 * Take input for rooms to be painted and the price per gallon of paint, then ask for square footage of space in each room, hours of labor, labor charge, and paint cost.
 */
import java.util.*;
public class Paint_Company {
    //declare static variables predetermined by company
    static int sqrftPerGallon = 100;
    static int hoursPerGallon = 8;
    static float laborRate = 20.00f;
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        int numRooms;
        float pricePerGallon;
        float totalSqrft;

        System.out.println("Please enter the number of rooms to be painted.");
        numRooms = scnr.nextInt();//input for rooms being painted
        System.out.println("Enter the price of a gallon of the paint being used.");
        pricePerGallon = scnr.nextFloat();//input for gallons being used
        
        totalSqrft = 0;//set totalSqrft to 0 before loop
        for(int i = 1; i <= numRooms; i++){//loop to iterate through how many rooms to be painted and ask the square footage of each room
            System.out.println("What is the square footage of wall space in room " + i + "?");
            totalSqrft += scnr.nextFloat();
        }

        //Function/method variables
        int gallonsRequired = calculateGallonsRequired(totalSqrft);
        int hoursRequired = calculateHoursRequired(gallonsRequired);
        float costOfPaint = calculateCostOfPaint(gallonsRequired, pricePerGallon);
        float laborCharges = calculateLaborCharges(hoursRequired);
        float totalCost = calculateTotalCost(costOfPaint, laborCharges);

        //print results
        System.out.println("\nNumber of gallons required: " + gallonsRequired + " gallons.");
        System.out.println("Hours of labor: " + hoursRequired + " hours.");
        System.out.printf("Cost of paint: $%.2f\n", costOfPaint);
        System.out.printf("Labor charges:  $%.2f\n", laborCharges);
        System.out.printf("Total for the paint job:  $%.2f\n", totalCost);
    }

    public static int calculateGallonsRequired(float totalSqrft){
        return (int) Math.ceil(totalSqrft / sqrftPerGallon);//logic to determine necessary gallons per total square footage
    }

    public static int calculateHoursRequired(int gallonsRequired){
        return gallonsRequired * hoursPerGallon;//logic to determine hours required per necessary gallons
    }

    public static float calculateCostOfPaint(int gallonsRequired, float pricePerGallon){
        return gallonsRequired * pricePerGallon;//logic to determine paint cost relative to gallons required
    }

    public static float calculateLaborCharges(int hoursRequired){
        return hoursRequired * laborRate;//logic to determine cost of labor by required hours
    }

    public static float calculateTotalCost(float costOfPaint, float laborCharges){
        return costOfPaint + laborCharges;//logic to determine total cost for the paint job
    }
}
