package tasck;

public enum TicketType {
    VIP,
    USUAL,
    BUDGETARY,
    CHEAP;

    public static String nameList() {
        String nameList = "";
        for (TicketType type : values()) {
            nameList += type.name() + ", ";
        }
        return nameList.substring(0, nameList.length()-2);
    }
}