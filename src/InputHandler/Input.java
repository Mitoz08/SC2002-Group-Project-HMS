package InputHandler;

import java.util.InputMismatchException;
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
            String output = sc.nextLine();
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
            String output = sc.nextLine();
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
            String output = sc.nextLine();
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
            String output = sc.nextLine();
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
}
