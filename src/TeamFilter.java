import java.util.Map;

public class TeamFilter implements FilterStrategy {
    private String team;

    public TeamFilter(String team) {
        this.team = team;
    }

    @Override
    public boolean apply(Map<String, String> playerData) {
        return team.equals("All") || playerData.get("Team").equals(team);
    }
}

