package InputHandler;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Input {

    private static Scanner sc = new Scanner(System.in);

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

    public static int ScanInt(String prompt){
        while (true) {
            System.out.print(prompt + " ");
            String output = sc.nextLine().trim();
            try {
                return Integer.valueOf(output);
            } catch (Exception e) {
                System.out.println("Incorrect input");
            }
        }
    }

    public static long ScanLong(String prompt){
        while (true) {
            System.out.print(prompt + " ");
            String output = sc.nextLine().trim();
            try {
                return Long.valueOf(output);
            } catch (Exception e) {
                System.out.println("Incorrect input");
            }

        }
    }

    public static float ScanFloat(String prompt){
        while (true) {
            System.out.print(prompt + " ");
            String output = sc.nextLine().trim();
            try {
                return Float.valueOf(output);
            } catch (Exception e) {
                System.out.println("Incorrect input");
            }

        }
    }

    public static double ScanDouble(String prompt){
         while (true) {
            System.out.print(prompt + " ");
            String output = sc.nextLine().trim();
            try {
                return Double.valueOf(output);
            } catch (Exception e) {
                System.out.println("Incorrect input");
            }
        }
    }

    public static String ScanString(String prompt){
        System.out.print(prompt + " ");
        return sc.nextLine();
    }

    public static char ScanChar(String prompt){
        System.out.print(prompt + " ");
        return sc.nextLine().toLowerCase().charAt(0);
    }

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
            if (inputMonth < curMonth && year) {
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
            if (inputDay < curDay && month && year){
                System.out.println("Invalid day input. Has to be at least " + curDay);
                continue;
            } else if (maxDay < curDay) {
                System.out.println("Day has to be smaller than" + maxDay);
                continue;
            }
            break;
        }
        return new Date(inputYear-1900,inputMonth-1,inputDay);
    }

    public static Date ScanDate(String prompt) {
        int inputYear;
        int inputMonth;
        int inputDay;

        System.out.println(prompt);
        inputYear = ScanInt("Year:");
        inputMonth = ScanInt("Month:");
        inputDay = ScanInt("Day:");
        return new Date(inputYear-1900,inputMonth-1,inputDay);
    }

}
