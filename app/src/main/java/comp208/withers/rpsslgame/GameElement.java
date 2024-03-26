package comp208.withers.rpsslgame;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameElement implements Comparable<GameElement> {
    private String value;
    private static final List<String> elements = Arrays.asList("scissorsIcon.png", "rockIcon.png", "paperIcon.png", "lizardIcon.png", "spockIcon.png");
    private static final Map<String, List<String>> beats = new HashMap<String, List<String>>() {{
        put("rockIcon.png", Arrays.asList("scissorsIcon.png", "lizardIcon.png"));
        put("paperIcon.png", Arrays.asList("rockIcon.png", "spockIcon.png"));
        put("scissorsIcon.png", Arrays.asList("paperIcon.png", "lizardIcon.png"));
        put("lizardIcon.png", Arrays.asList("paperIcon.png", "spockIcon.png"));
        put("spockIcon.png", Arrays.asList("rockIcon.png", "scissorsIcon.png"));
    }};

    public GameElement(String value) {
        if (!elements.contains(value)) {
            throw new IllegalArgumentException("Invalid game element");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int compareTo(GameElement other) {
        if (beats.get(this.value).contains(other.getValue())) {
            return 1;
        } else if (beats.get(other.getValue()).contains(this.value)) {
            return -1;
        }
        return 0;
    }
}