/*
 * Sean Zamora
 * ITSE 2317
 * 8/28/2024
 * Build a calculator for the Stock Commission regarding shares, share prices, and amount paid to a broker.
 */
import java.util.*;
public class Stock_Commission {

	public static void main(String[] args) {
		int sharesPurchased;//number of shares purchased
		int sharesSold;//number of shares sold
		float sharePriceOnPurchase;//cost of shares at purchase
		float sharePriceOnSale;//cost of shares at sale
		float brokerPercentage1;//percentage of total sale that goes to the broker
		float brokerPercentage2;//percentage of total gains that goes to the broker
		float brokerCost1;//amount paid to broker on purchase
		float brokerCost2;//amount paid to broker on sale
		float totalPurchase;
		float totalSale;
		
		Scanner kb = new Scanner(System.in); //activates keyboard
		
		System.out.println("How many shares did you buy?");
		sharesPurchased = kb.nextInt();
		System.out.println("What was the purchase price of the share?");
		sharePriceOnPurchase = kb.nextFloat();
		System.out.println("What percentage of commission did you pay in decimal value?");
		brokerPercentage1 = kb.nextFloat();
		System.out.println("How many shares did you sell?");
		sharesSold = kb.nextInt();
		System.out.println("What was the sale price of the shares?");
		sharePriceOnSale = kb.nextFloat();
		System.out.println("What percent commission did you pay to sell the shares in decimal value?");
		brokerPercentage2 = kb.nextFloat();
		
		totalPurchase = sharePriceOnPurchase * sharesPurchased;
		totalSale = sharePriceOnSale * sharesSold;
		brokerCost1 = totalPurchase * brokerPercentage1;
		brokerCost2 = totalSale * brokerPercentage2;
		
		System.out.println("Purchase Price: $" + totalPurchase);
		System.out.println("Purchase Commission: $" + brokerCost1);
		System.out.println("Purchase Total: $" + (totalPurchase + brokerCost1));
		System.out.println("Sale Price: $" + totalSale);
		System.out.println("Sale Commission: $" + brokerCost2);
		System.out.println("Sale Total: $" + (totalSale - brokerCost2));
		System.out.println("Profit: $" + ((totalSale - brokerCost2) - (totalPurchase + brokerCost1)));
	}

}
