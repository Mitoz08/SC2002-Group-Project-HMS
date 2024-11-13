package InputHandler;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * The {@code Input} class deals with the clearing and obtaining of inputs from the terminal
 */
public class Input {

    private static final Scanner sc = new Scanner(System.in);

    /**
     * Function to clear the terminal
     */
    public static void ClearConsole() {
        try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                try {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Function to scan for an {@code integer}
     * @param prompt String to be printed to prompt the input
     * @return an {@code integer}
     */
    public static int ScanInt(String prompt){
        while (true) {
            System.out.print(prompt + " ");
            String output = sc.nextLine().trim();
            try {
                return Integer.parseInt(output);
            } catch (Exception e) {
                System.out.println("Incorrect input");
            }
        }
    }

    /**
     * Function to scan for a {@code long}
     * @param prompt String to be printed to prompt the input
     * @return a {@code long}
     */
    public static long ScanLong(String prompt){
        while (true) {
            System.out.print(prompt + " ");
            String output = sc.nextLine().trim();
            try {
                return Long.parseLong(output);
            } catch (Exception e) {
                System.out.println("Incorrect input");
            }

        }
    }

    /**
     * Function to scan for a {@code float}
     * @param prompt String to be printed to prompt the input
     * @return a {@code float}
     */
    public static float ScanFloat(String prompt){
        while (true) {
            System.out.print(prompt + " ");
            String output = sc.nextLine().trim();
            try {
                return Float.parseFloat(output);
            } catch (Exception e) {
                System.out.println("Incorrect input");
            }

        }
    }

    /**
     * Function to scan for a {@code double}
     * @param prompt String to be printed to prompt the input
     * @return a {@code double}
     */
    public static double ScanDouble(String prompt){
         while (true) {
            System.out.print(prompt + " ");
            String output = sc.nextLine().trim();
            try {
                return Double.parseDouble(output);
            } catch (Exception e) {
                System.out.println("Incorrect input");
            }
        }
    }

    /**
     * Function to scan for a {@code String}
     * @param prompt String to be printed to prompt the input
     * @return a {@code String}
     */
    public static String ScanString(String prompt){
        System.out.print(prompt + " ");
        return sc.nextLine();
    }

    /**
     * Function to scan for the first {@code char}
     * @param prompt String to be printed to prompt the input
     * @return a {@code char}
     */
    public static char ScanChar(String prompt){
        System.out.print(prompt + " ");
        return sc.nextLine().toLowerCase().charAt(0);
    }

    /**
     * Function to scan for a {@code boolean} yes or no
     * @param prompt String to be printed to prompt the input
     * @return a {@code boolean}
     */
    public static boolean ScanBoolean(String prompt){
        while (true) {
            System.out.print(prompt + " (Y/N) ");
            char c = sc.nextLine().toUpperCase().charAt(0);
            if (c != 'Y' && c != 'N') {
                System.out.println("Incorrect input");
                continue;
            }
            return c == 'Y';
        }
    }

    /**
     * Function to scan for a {@code date} and ensures it is a future date
     * @param prompt String to be printed to prompt the input
     * @return a {@code date}
     */
    public static Date ScanFutureDate(String prompt) {
        LocalDateTime current = LocalDateTime.now();
        int curYear = current.getYear();
        int curMonth = current.getMonthValue();
        int curDay = current.getDayOfMonth();

        boolean year;
        boolean month;

        int inputYear;
        int inputMonth;
        int inputDay;

        System.out.println(prompt);


        while (true) {
            inputYear = ScanInt("Year:");
            year = inputYear == curYear;
            if (inputYear < curYear) {
                System.out.println("Invalid year input. Has to be at least " + curYear);
                continue;
            }
            break;
        }
        while (true) {
            inputMonth = ScanInt("Month:");
            month = inputMonth == curMonth;
            if (inputMonth < 1 || inputMonth > 12) {
                System.out.println("Enter a number from 1 - 12");
                continue;
            }
            if (year && inputMonth < curMonth) {
                System.out.println("Invalid month input. Has to be at least " + curMonth);
                continue;
            }
            break;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2024);
        cal.set(Calendar.MONTH,inputMonth-1);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        while (true) {
            inputDay = ScanInt("Day:");
            if (inputDay < 1 || inputDay > maxDay) {
                System.out.println("Enter a number from 1 - " + maxDay);
                continue;
            }
            if (year && month && inputDay < curDay){
                System.out.println("Invalid day input. Has to be at least " + curDay);
                continue;
            }
            break;
        }
        return new Date(inputYear-1900,inputMonth-1,inputDay);
    }

    /**
     * Function to scan for a {@code date}
     * @param prompt String to be printed to prompt the input
     * @return a {@code date}
     */
    public static Date ScanDate(String prompt) {
        int inputYear;
        int inputMonth;
        int inputDay;

        System.out.println(prompt);
        while (true) {
            inputYear = ScanInt("Year:");
            if (inputYear < 0) {
                System.out.println("Year cannot be negative!");
                continue;
            }
            break;
        }
        while (true) {
            inputMonth = ScanInt("Month:");
            if (inputMonth < 1 || inputMonth > 12) {
                System.out.println("Enter a number from 1 - 12");
                continue;
            }
            break;
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, inputYear);
        cal.set(Calendar.MONTH,inputMonth-1);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        while (true) {
            inputDay = ScanInt("Day:");
            if (inputDay < 1 || inputDay > maxDay) {
                System.out.println("Enter a number from 1 - " + maxDay);
                continue;
            }
            break;
        }
        return new Date(inputYear-1900,inputMonth-1,inputDay);
    }

}
