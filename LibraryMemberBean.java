public class LibraryMemberBean {

    private String membershipId;
    private String name;
    private boolean premiumMember;
    private String securityAnswer;

    // No-argument constructor
    public LibraryMemberBean() {
        this(null, null);
    }

    // Name-only constructor
    public LibraryMemberBean(String name) {
        this(null, name);
    }

    // Main constructor
    public LibraryMemberBean(String membershipId, String name) {
        this.membershipId = membershipId;
        this.name = name;
    }

    public String getMembershipId() {
        return membershipId;
    }

    // Write-once setter
    public void setMembershipId(String id) {

        if (membershipId == null) {
            membershipId = id;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPremiumMember() {
        return premiumMember;
    }

    public void setPremiumMember(boolean premium) {
        premiumMember = premium;
    }

    // Write-only property
    public void setSecurityAnswer(String answer) {

        if (answer != null) {
            securityAnswer =
                Integer.toHexString(answer.hashCode());
        }
    }

    public static void main(String[] args) {

        LibraryMemberBean m =
            new LibraryMemberBean();

        m.setMembershipId("LIB-8841");
        m.setMembershipId("FAKE-0000");

        System.out.println(
            m.getMembershipId()
        );

        LibraryMemberBean m2 =
            new LibraryMemberBean("Priya Nair");

        System.out.println(
            m2.getMembershipId()
        );

        LibraryMemberBean m3 =
            new LibraryMemberBean(
                "LIB-8841",
                "Priya Nair"
            );

        System.out.println(
            m3.getMembershipId()
        );
    }
}