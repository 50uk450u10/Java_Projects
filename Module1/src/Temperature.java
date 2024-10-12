import java.util.Scanner;

public class Temperature {
    double fahrenheit;

    // Constructor
    Temperature(double ftemp) {
        fahrenheit = ftemp;
    }

    // Method to set Fahrenheit temperature
    void setFahrenheit(double ftemp) {
        fahrenheit = ftemp;
    }

    // Method to get Fahrenheit temperature
    double getFahrenheit() {
        return fahrenheit;
    }

    // Method to calculate Celsius from Fahrenheit
    double calcCelsius() {
        return (5.0 / 9.0) * (fahrenheit - 32);
    }

    // Method to calculate Kelvin from Fahrenheit
    double calcKelvin() {
        return calcCelsius() + 273.0; // Adding 273 to Celsius value
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ask user for a Fahrenheit temperature
        System.out.print("Enter a temperature in Fahrenheit: ");
        double inputFahrenheit = scanner.nextDouble();

        // Create an instance of Temperature
        Temperature temp = new Temperature(inputFahrenheit);

        // Display the temperature in Celsius and Kelvin
        System.out.printf("Temperature in Celsius: %.2f%n", temp.calcCelsius());
        System.out.printf("Temperature in Kelvin: %.2f%n", temp.calcKelvin());

        scanner.close();
    }
}