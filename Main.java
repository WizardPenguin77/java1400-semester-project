import java.util.Scanner;
public class Main{

    // main method just starts program and asks what type of calculator is wanted
    public static void main(String[] args){
        Scanner scnr = new Scanner(System.in);
        int userChoice;

        System.out.println("Welcome to the BAC Calculator! In a couple minutes we will tell you if you're ready to hit the road or not!\n");
        System.out.println("Would you like: \n1. Basic calculator (standardized drink amounts) \n2. Advanced calculator? ");
        // input mistmatch exception handler
        while (true){
            if (scnr.hasNextInt()){
                userChoice = scnr.nextInt();
                if (userChoice == 1 || userChoice == 2) break;
                else scnr.next();
                System.out.println("Invalid input, please input 1 or 2. 1 for the basic calculator, 2 for the advanced calculator.");
            }
        }
        if (userChoice == 1) NormalCalculator();
        else AdvancedCalculator();
        scnr.close();
    }

    // advanced calculator method, asks for inputs then calls CalculateAdvancedBAC()
    public static void AdvancedCalculator(){
        Scanner scnr = new Scanner(System.in);
        double weight;
        String gender = "";
        int hoursDrinking;
        String answer = "";
        double alcoholConsumed = 0.0;
        final double ETHANOL_DENSITY = 0.789;
        
        System.out.println("Welcome to the advanced calculator! Here you will input every drink one at a time, with details about each drink.");

        // all these while loops are for input mismatch exceptions
        System.out.print("Please input your weight in pounds. ");
        while (!scnr.hasNextDouble()){
            System.out.print("Please enter a number. ");
            scnr.next();
        }
        weight = scnr.nextDouble();
        System.out.print("Please input your biological gender. ");
        while (true){
            gender = scnr.next();
            if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female")){
                break;
            }
            System.out.print("Input a valid biological gender. ");
        }
        System.out.print("Please input the amount of hours you've been drinking. ");
        while (!scnr.hasNextInt()){
            System.out.print("Please enter a number. ");
            scnr.next();
        }
        hoursDrinking = scnr.nextInt();
        System.out.print("Would you like to add a drink? (y/n) ");
        while (true){
            answer = scnr.next();
            if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("n")){
                break;
            }
            System.out.print("Please input y or n. ");
        }

        // loop for getting multiple drinks
        while (answer.equalsIgnoreCase("y")){
            System.out.print("Please input the drink size in ml. ");
            while (!scnr.hasNextInt()){
                System.out.print("Please enter a number. ");
                scnr.next();
            }
            int drinkSize = scnr.nextInt();
            System.out.print("Please input the Alcohol By Volume (ABV) of the drink as a whole number percentage. ");
            while (!scnr.hasNextInt()){
                System.out.print("Please enter a number. ");
                scnr.next();
            }
            // to convert number to precent
            double ABV = scnr.nextDouble() / 100;
            
            // formula for alcohol consumed in grams is drinksize in ml * the ABV percent which gives ethanol volume, multiply that by ethanol density for grams of alcohol
            alcoholConsumed += ABV * drinkSize * ETHANOL_DENSITY;
            System.out.print("Would you like to add another drink? ");
            while (true){
                answer = scnr.next();
                if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("n")){
                    break;
                }
                System.out.print("Please input y or n. ");
            }
        }
        
        // calls calculations and prints
        double BAC = CalculateBAC(weight, gender, alcoholConsumed, hoursDrinking);
        LegalLimit(BAC);
        scnr.close();
    }

    // normal BAC calculator uses standard drink size of 14 grams of alcohol per drink
    public static void NormalCalculator(){
        Scanner scnr = new Scanner(System.in);
        double weight;
        String gender = "";
        int numberOfDrinks;
        int hoursDrinking;
        final int STANDARD_ALCOHOL = 14;

        // inputmismatch exception handlers + checking for needed inputs to call CalculateBAC()
        System.out.print("Please input your weight in pounds. ");
        while (!scnr.hasNextDouble()){
            System.out.print("Please enter a number. ");
            scnr.next();
        }
        weight = scnr.nextDouble();
        System.out.print("Please input your biological gender. ");
        while (true){
            gender = scnr.next();
            if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female")){
                break;
            }
            System.out.print("Input a valid biological gender. ");
        }
        System.out.print("Please input the number of drinks you've had. ");
        while (!scnr.hasNextInt()){
            System.out.print("Please enter a number. ");
            scnr.next();
        }
        numberOfDrinks = scnr.nextInt();
        System.out.print("Please input the amount of hours you've been drinking. ");
        while (!scnr.hasNextInt()){
            System.out.print("Please enter a number. ");
            scnr.next();
        }
        hoursDrinking = scnr.nextInt();
        double BAC = CalculateBAC(weight, gender, numberOfDrinks * STANDARD_ALCOHOL, hoursDrinking);
        LegalLimit(BAC);
        scnr.close();

    }

    // method to calculate BAC
    public static double CalculateBAC(double weight, String gender, double alcoholContent, int hoursDrinking){
        
        // convert weight from lbs to grams
        weight *= 454;
        final double FEMALE_GENDER_CONSTANT = 0.55;
        final double MALE_GENDER_CONSTANT = 0.68;
        double metabolism = hoursDrinking * -.015;

        // returns value based on widmarks formula for different genders
        if (gender.equalsIgnoreCase("Male")){
            return (alcoholContent / (weight * MALE_GENDER_CONSTANT)) * 100 + metabolism;
        }
        else {
            return (alcoholContent / (weight * FEMALE_GENDER_CONSTANT)) * 100 + metabolism;
        }
    }

    //format print statement
    public static void LegalLimit(double BAC){
        if (BAC > 0.08)  System.out.printf("Your BAC is %.3f%%. You are over the legal limit of 0.08%% and are not fit to drive!\n", BAC);
        else System.out.printf("Your BAC is %.3f%%. You are under the legal limit of 0.08%% and are fit to drive. (But you maybe probably shouldn't.)\n", BAC);
    }
}