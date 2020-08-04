package nl.wolfpackit.backend_assessment.model;

public enum Gender {
    MALE("M"),
    FEMALE("F"),
    OTHER("X");

    private final String label;

    Gender(String label) {
        this.label = label;
    }

    public static Gender byLabel(String label) {
        for (Gender g : Gender.values()) {
            if (g.label.equals(label)) {
                return g;
            }
        }
        return OTHER;
    }

    public String getLabel() {
        return label;
    }
}
