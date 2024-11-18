import java.util.Map;

public class PositionFilter implements FilterStrategy {
    private String position;

    public PositionFilter(String position) {
        this.position = position;
    }

    @Override
    public boolean apply(Map<String, String> playerData) {
        return position.equals("All") || playerData.get("POS").equals(position);
    }
}

