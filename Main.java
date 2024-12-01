import java.util.Scanner;
public class Main{
    public static void main(String[] args){
        Scanner scnr = new Scanner(System.in);
        double weight;
        String gender = "";
        int numberOfDrinks;
        int hoursDrinking;

        System.out.println("Welcome to the BAC Calculator! In a couple minutes we will tell you if you're ready to hit the road or not!\n");

        System.out.print("Please input your weight in pounds. ");
        while (!scnr.hasNextDouble()){
            System.out.println("Please enter a number.");
            scnr.next();
        }
        weight = scnr.nextDouble();
        System.out.print("Please input your biological gender. ");
        while (true){
            gender = scnr.next();
            if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female")){
                break;
            }
            System.out.println("Input a valid biological gender.");
        }
        System.out.print("Please input the number of drinks you've had. ");
        while (!scnr.hasNextInt()){
            System.out.println("Please enter a number");
            scnr.next();
        }
        numberOfDrinks = scnr.nextInt();
        System.out.print("Please input the amount of hours you've been drinking. ");
        while (!scnr.hasNextInt()){
            System.out.println("Please enter a number");
            scnr.next();
        }
        hoursDrinking = scnr.nextInt();
        double BAC = CalculateBAC(weight, gender, numberOfDrinks, hoursDrinking);
        LegalLimit(BAC);
        scnr.close();

    }

    public static double CalculateBAC(double weight, String gender, int alcoholContent, int hoursDrinking){
        weight *= 454;
        alcoholContent *= 14;
        double femaleGenderConstant = 0.55;
        double maleGenderConstant = 0.68;
        double metabolism = hoursDrinking * -.015;
        if (gender.equalsIgnoreCase("Male")){
            return (alcoholContent / (weight * maleGenderConstant)) * 100 + metabolism;
        }
        else {
            return (alcoholContent / (weight * femaleGenderConstant)) * 100 + metabolism;
        }
    }

    public static void LegalLimit(double BAC){
        if (BAC > 0.08)  System.out.printf("Your BAC is %.2f%%. You are over the legal limit of 0.08%% and are fit to drive!\n", BAC);
        else System.out.printf("Your BAC is %.2f%%. You are under the legal limit of 0.08%% and are not fit to drive.\n", BAC);
    }

}