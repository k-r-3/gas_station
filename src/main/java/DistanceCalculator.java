import java.util.*;

public class DistanceCalculator {

    public static void main(String[] args) {

    }

    //считаем расстояния между станциями
    Map<Integer, List<Integer>> calculateDistance(List<Integer> stations) {
        Map<Integer, List<Integer>> rsl = new HashMap<>();
        for (int i = 0; i < stations.size() - 1; i++) {
            List<Integer> distances = rsl.get(i);
            for (int j = i + 1; j < stations.size(); j++) {
                if (distances == null) {
                    distances = new ArrayList<>();
                }
                distances.add(Math.abs(stations.get(i) - stations.get(j)));
            }
            rsl.put(i, (distances));
        }
        rsl.put(stations.size() - 1, List.of(0));
        return rsl;

    }

    //объединяем интервалы в серии
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

    //оставляем серии с наибольшими отрезками
    public Map<Integer, Integer> deleteSmallestDuplicate(Map<Integer, Integer> in) {
        Map<Integer, Integer> rsl = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : in.entrySet()) {
            int amount = entry.getValue();
            Integer station = rsl.get(amount);
            rsl.put(amount, (Objects.isNull(station) ? entry.getKey() : Math.max(station, entry.getKey())));
        }
        return rsl;
    }

    //оставляем серию с колличеством отрезков наиболее близким к колличеству,которое нужно оставить
    public Map<Integer, Integer> deleteStations(int amount, Map<Integer, Integer> in) {
        Map<Integer, Integer> rsl = new HashMap<>();
        if (in.size() == 1) {
            rsl.put(amount, in.values().stream().findFirst().get());
        } else {
            Integer element = in.get(amount);
            int key = amount;
            if (Objects.isNull(element)) {
                for (Integer i : in.keySet()) {
                    if (i < amount) {
                        key = i;
                    }
                }
            }
            rsl.put(key, in.get(key));
        }
        return rsl;
    }

    //сопоставляем расстояние со станциями, и в результат пишем те пары индексов, между которыми это расстояние
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

    public String receipt(List<Integer> firstLine, List<Integer> secondLine) {
        int stationsAmount = firstLine.get(0);
        int amountForDelete = firstLine.get(1);
        Map<Integer, List<Integer>> distances = calculateDistance(secondLine);
        Map<Integer, Integer> duplicates = calculateDuplicates(distances);
        Map<Integer, Integer> longestDuplicate = deleteSmallestDuplicate(duplicates);
        Map<Integer, Integer> leftDistance = deleteStations((distances.size() - amountForDelete), longestDuplicate);
        Set<Integer> stationIndexes = sortStations(distances, leftDistance);
        List<Integer> stations = new ArrayList();
        for (Map.Entry<Integer, List<Integer>> entry : distances.entrySet()) {
            int stationNumber = entry.getKey();
            if (!stationIndexes.contains(stationNumber)) {
                stations.add(stationNumber + 1);
            }
        }
        stations = stations.isEmpty() ? List.of(1) : stations;
        return String.format("%s" + System.lineSeparator() + "%s",
                leftDistance.values().stream()
                        .findFirst()
                        .get(),
                stations
        );
    }
}
