import java.util.*;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
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
                rsl.put(i, (distances));
            }
        }
        return rsl;

    }

    public Map<Integer, Integer> calculateDuplicates(Map<Integer, List<Integer>> in) {
        Map<Integer, Integer> rsl = new HashMap<>();
        for (int i = 0; i < in.size(); i++) {
            List<Integer> inner = in.get(i);
            for (int j = 0; j < inner.size(); j++) {
                Integer amount = rsl.get(inner.get(j));
                rsl.put(inner.get(j), (Objects.isNull(amount) ? 1 : amount + 1));
            }
        }
        return rsl;
    }

    public Map<Integer, Integer> deleteSmallestDuplicate(Map<Integer, Integer> in) {
        Map<Integer, Integer> rsl = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : in.entrySet()) {
            int amount = entry.getValue();
            Integer station = rsl.get(amount);
            rsl.put(amount, (Objects.isNull(station) ? entry.getKey() : Math.max(station, entry.getKey())));
        }
        return rsl;
    }

    public Map<Integer, Integer> deleteStations(int amount, Map<Integer, Integer> in) {
        Map<Integer, Integer> rsl = new HashMap<>();
        Integer element = in.get(amount);
        if (Objects.isNull(element)) {
            element = 0;
            List<Integer> keys = new ArrayList<>(in.keySet());
            Collections.sort(keys);
            int temp;
            for (Integer i : keys) {
                if (i < amount) {
                    temp = i;
                    element = temp > element ? temp : element;
                }
            }
        }
        rsl.put(element, in.get(element));
        return rsl;
    }

    public Set<Integer> sortStations(Map<Integer, List<Integer>> allStations, Map<Integer, Integer> distance) {
        Set<Integer> rsl = new HashSet<>();
        int pattern = distance.values()
                .stream()
                .findFirst()
                .get();
        for (int i = 0; i < allStations.size(); i++) {
            List<Integer> inner = allStations.get(i);
            for (int j = 0; j < inner.size(); j++) {
                if (inner.get(j) == pattern) {
                    rsl.add(i);
                    rsl.add(i + j + 1);
                }
            }
        }
        return rsl;
    }
}
