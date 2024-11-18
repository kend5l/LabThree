import java.util.Map;

public interface FilterStrategy {
    boolean apply(Map<String, String> playerData);
}

