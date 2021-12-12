package bullscows;

import java.util.*;

public class Main {
    private static final String dictionary = "0123456789abcdefghijklmnopqrstuvwxyz";
    private static String possibleSymbols;

    public static void main(String[] args) {
        startGame();
    }

    public static StringBuilder codeGenerator (int codeLength, int numberOfSymbols) {

        Random random = new Random();
        int randomChar = 0;
        int interval = 0;
        StringBuilder sb = new StringBuilder();
        if (numberOfSymbols <= 10) {
            interval = (48 + numberOfSymbols) - 48;
        } else {
            interval = (97 + (numberOfSymbols - 10)) - 48;
        }
        for (int i = 0; i < codeLength; i++) {
            while (true) {
                randomChar = random.nextInt(interval) + 48;
                    if (!(randomChar > 57 && randomChar < 97)) {
                        if (i == 0) {
                            sb.append((char) randomChar);
                            break;
                        } else if (!sb.substring(0, i).contains(String.valueOf((char) randomChar))) {
                            sb.append((char) randomChar);
                            break;
                    }
                }
            }
        }
        System.out.print("The secret code is prepared: ");
        for (int i = 0; i < codeLength; i++) {
            System.out.print("*");
        }
        if (numberOfSymbols <= 10) {
            System.out.printf(" (0-%d).", numberOfSymbols - 1);
            System.out.println();
        } else if (numberOfSymbols == 11) {
            System.out.print(" (0-9, a).");
            System.out.println();
        } else {
            System.out.printf(" (0-9, a-%c).", (char)(97 + (numberOfSymbols - 11)));
            System.out.println();
        }
        System.out.println("Code: " + sb);
        return sb;
    }


    public static void startGame() {
        Scanner sc = new Scanner(System.in);
        String generatedNumber = "";
        int codeLength = 0;
        while (true) {
            System.out.println("Please, enter the secret code's length:");
            String inputLength = sc.nextLine();
            try {
                codeLength = Integer.parseInt(inputLength);
            } catch (NumberFormatException e) {
                System.out.printf("Error: \"%s\" isn't a valid number.\n", inputLength);
                System.exit(0);
            }
            if (codeLength <= 36 && codeLength > 1) {
                while (true) {
                    System.out.println("Input the number of possible symbols in the code:");
                    int numberOfSymbols = 0;
                    String inputSymbols = sc.nextLine();
                    try {
                        numberOfSymbols = Integer.parseInt(inputSymbols);
                    } catch (NumberFormatException e) {
                        System.out.println("Error: a number of unique symbols is a digit!");
                        System.exit(0);
                    }
                    if (numberOfSymbols >= codeLength && numberOfSymbols <= 36) {
                        generatedNumber = codeGenerator(codeLength, numberOfSymbols).toString();
                        possibleSymbols = dictionary.substring(0, numberOfSymbols);
                        break;
                    } else if (numberOfSymbols > 36) {
                        System.out.print("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                        System.exit(0);
                    } else {
                        System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.\n", codeLength, numberOfSymbols);
                        System.exit(0);
                    }
                }
                break;
            } else {
                System.out.printf("Error: can't generate a secret number with a length of %d.\n", codeLength);
            }
        }
        System.out.println("Okay, let's start a game!");
        numberCheck(generatedNumber, codeLength);
    }

    public static void numberCheck(String generatedNumber, int codeLength) {
        int bullCounter = 0;
        int cowCounter = 0;
        int turn = 1;
        int symbolsCount = 0;
        while (true) {
            System.out.printf("Turn %d:\n", turn);
            while(true) {
                String userCode = new Scanner(System.in).nextLine();
                StringBuilder inputCheck = new StringBuilder();
                for (int i = 0; i < userCode.length(); i++) {
                    symbolsCount = possibleSymbols.contains(String.valueOf(userCode.charAt(i))) ? symbolsCount + 1 : symbolsCount;
                }
                if (symbolsCount != userCode.length()) {
                    System.out.print("Error: wrong symbols");
                    System.exit(0);
                }
                symbolsCount = 0;
                if (generatedNumber.length() == userCode.length()) {
                    for (int i = 0; i < generatedNumber.length(); i++) {
                        if (generatedNumber.contains(String.valueOf(userCode.charAt(i))) && !inputCheck.toString().contains(String.valueOf(userCode.charAt(i)))) {
                            ++cowCounter;
                            inputCheck.append(userCode.charAt(i));
                        }
                        if (userCode.charAt(i) == generatedNumber.charAt(i)) {
                            ++bullCounter;
                            --cowCounter;
                        }
                    }
                    inputCheck.delete(0, generatedNumber.length());
                    break;
                } else {
                    System.out.printf("Error: code's length is %d.", codeLength);
                    System.exit(0);
                }
            }
            if (bullCounter == codeLength) {
                System.out.printf("Grade: %d bulls\n", bullCounter);
                System.out.println("Congratulations! You guessed the secret code.");
                break;
            } else if (bullCounter == 1 && cowCounter != 1) {
                System.out.printf("Grade: %d bull and %d cows\n", bullCounter, cowCounter);
                ++turn;
                bullCounter = 0;
                cowCounter = 0;
            } else if (cowCounter == 1 && bullCounter != 1) {
                System.out.printf("Grade: %d bulls and %d cow\n", bullCounter, cowCounter);
                ++turn;
                bullCounter = 0;
                cowCounter = 0;
            } else if (bullCounter == 1 && cowCounter == 1) {
                System.out.printf("Grade: %d bull and %d cow\n", bullCounter, cowCounter);
                ++turn;
                bullCounter = 0;
                cowCounter = 0;
            } else if (bullCounter == 0 && cowCounter == 0) {
                System.out.println("Grade: None");
                ++turn;
            } else {
                System.out.printf("Grade: %d bulls and %d cows\n", bullCounter, cowCounter);
                ++turn;
                bullCounter = 0;
                cowCounter = 0;
            }
        }
    }
}
