import java.util.*;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DistanceCalculator {

    public static void main(String[] args) {
        Random stationAmount = new Random(3);
        Random coordinats = new Random();
        Random toRemove = new Random();
        List<Integer> stationList = coordinats.ints(0, (int) Math.pow(10, 9))
                .limit(stationAmount.nextInt(100_000))
                .boxed()
                .collect(Collectors.toList());
        System.out.println(stationList);
        System.out.println(stationList.size());
    }

    public Map<Integer, List<Integer>> calculateDistance(List<Integer> stations) {
        Map<Integer, List<Integer>> rsl = new HashMap<>();
        for (int i = 0; i < stations.size() - 1; i++) {
            for (int j = i + 1; j < stations.size(); j++) {
                List<Integer> distances = rsl.get(i);
                if (distances == null) {
                    distances = new ArrayList<>();
                }
                distances.add(Math.abs(stations.get(i) - stations.get(j)));
                rsl.put(i, (distances == null ? new ArrayList<>() : distances));
            }
        }
        return rsl;

    }
}
