public class LoanReceipt {

    private final String memberId;
    private final String[] bookIds;

    static int processedCount;

    // Static block
    static {
        processedCount = 0;
    }

    public LoanReceipt(String memberId, String[] bookIds) {

        if (bookIds == null) {
            throw new IllegalArgumentException(
                "Book IDs cannot be null"
            );
        }

        for (String id : bookIds) {

            if (!isValidBookId(id)) {
                throw new IllegalArgumentException(
                    "Invalid book ID"
                );
            }
        }

        this.memberId = memberId;

        // Defensive copy
        this.bookIds = new String[bookIds.length];

        for (int i = 0; i < bookIds.length; i++) {
            this.bookIds[i] = bookIds[i];
        }
    }

    private static boolean isValidBookId(String id) {

        if (id == null || id.length() != 6) {
            return false;
        }

        if (id.charAt(0) != 'B' ||
            id.charAt(1) != 'K' ||
            id.charAt(2) != '-') {
            return false;
        }

        for (int i = 3; i < 6; i++) {

            if (!Character.isDigit(id.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    // Defensive copy on output
    public String[] getBookIds() {

        String[] copy =
            new String[bookIds.length];

        for (int i = 0; i < bookIds.length; i++) {
            copy[i] = bookIds[i];
        }

        return copy;
    }

    // Return a new object
    public LoanReceipt withCorrectedBookId(
            int index, String newId) {

        if (index < 0 ||
            index >= bookIds.length ||
            !isValidBookId(newId)) {

            throw new IllegalArgumentException(
                "Invalid correction"
            );
        }

        String[] newIds = getBookIds();

        newIds[index] = newId;

        return new LoanReceipt(
            memberId,
            newIds
        );
    }

    static String processNightlyCirculation(
            LoanReceipt[] receipts) {

        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;

        for (LoanReceipt receipt : receipts) {

            if (receipt == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (receipt instanceof ReferenceOnlyLoanReceipt) {
                referenceOnly++;
            }
            else {
                regular++;
            }
        }

        return processed + " processed | "
             + nullSkipped + " null skipped | "
             + referenceOnly + " reference-only | "
             + regular + " regular";
    }

    public static void main(String[] args) {

        LoanReceipt r =
            new LoanReceipt(
                "LIB-8841",
                new String[]{"BK-100", "BK-101"}
            );

        String[] ids = r.getBookIds();

        ids[0] = "HACKED";

        System.out.println(
            r.getBookIds()[0]
        );

        LoanReceipt corrected =
            r.withCorrectedBookId(0, "BK-999");

        System.out.println(
            corrected.getBookIds()[0]
        );

        LoanReceipt[] receipts = {

            new ReferenceOnlyLoanReceipt(
                "LIB-001",
                new String[]{"BK-200"},
                "Reading Room 3"
            ),

            null,

            new LoanReceipt(
                "LIB-002",
                new String[]{"BK-201"}
            )
        };

        System.out.println(
            processNightlyCirculation(receipts)
        );
    }
}


// Reference-only variant
class ReferenceOnlyLoanReceipt extends LoanReceipt {

    private final String roomNumber;

    public ReferenceOnlyLoanReceipt(
            String memberId,
            String[] bookIds,
            String roomNumber) {

        super(memberId, bookIds);
        this.roomNumber = roomNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
}