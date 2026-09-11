import java.util.Scanner;

public class Problem5pW2 {

    static String normalizeReference(String raw) {

        String reference = raw.trim();

        if (reference.length() < 3) {
            return reference;
        }

        String bankCode = reference.substring(0, 3).toUpperCase();
        String remaining = reference.substring(3);

        return bankCode + remaining;
    }

    static String validateAndFormat(String reference) {

        if (reference.length() != 14) {
            return "Invalid: wrong length";
        }

        // Check first 3 characters are letters
        for (int i = 0; i < 3; i++) {

            if (!Character.isLetter(reference.charAt(i))) {
                return "Invalid: bank code must be 3 letters";
            }
        }

        // Check remaining 11 characters are digits
        for (int i = 3; i < 14; i++) {

            if (!Character.isDigit(reference.charAt(i))) {
                return "Invalid: body must contain only digits";
            }
        }

        String bankCode = reference.substring(0, 3);
        String date = reference.substring(3, 9);
        String sequence = reference.substring(9, 14);

        StringBuilder result = new StringBuilder();

        result.append("[");
        result.append(bankCode);
        result.append("] DATE: ");
        result.append(date.substring(0, 2));
        result.append("/");
        result.append(date.substring(2, 4));
        result.append("/");
        result.append(date.substring(4, 6));
        result.append(" | SEQ: ");
        result.append(sequence);

        return result.toString();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter reference: ");
        String raw = sc.nextLine();

        String normalized = normalizeReference(raw);

        System.out.println(validateAndFormat(normalized));
    }
}