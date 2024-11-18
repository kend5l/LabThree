import java.util.Map;

public class AgeFilter implements FilterStrategy {
    private String age;

    public AgeFilter(String age) {
        this.age = age;
    }

    @Override
    public boolean apply(Map<String, String> playerData) {
        return age.equals("All") || playerData.get("Age").equals(age);
    }
}

