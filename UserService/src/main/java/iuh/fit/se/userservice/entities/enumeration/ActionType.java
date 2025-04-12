package iuh.fit.se.userservice.entities.enumeration;

public enum ActionType {

    PURCHASE("PURCHASE"),
    ADD_TO_CART("ADD_TO_CART"),
    VIEW("VIEW"),
    SEARCH("SEARCH");

    private final String value;

    ActionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
