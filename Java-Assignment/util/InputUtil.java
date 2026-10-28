package util;

import java.math.BigDecimal;
import java.util.Scanner;

public class InputUtil {
    private final Scanner scanner;

    public InputUtil(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readLine(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a whole number.");
            }
        }
    }

    public BigDecimal readAmount(String prompt) {
        while (true) {
            try {
                BigDecimal amount = new BigDecimal(readLine(prompt));
                if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("Amount must be greater than zero.");
                    continue;
                }
                return amount.setScale(2, java.math.RoundingMode.HALF_UP);
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Enter a valid number.");
            }
        }
    }
}