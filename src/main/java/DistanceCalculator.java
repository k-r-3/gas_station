import java.util.*;

public class DistanceCalculator {
    private int stationsAmount;
    private int amountForDelete;
    private Map<Integer, List<Integer>> distances;
    private Map<Integer, Integer> duplicates;
    private Map<Integer, Integer> longestDuplicate;
    private Map<Integer, Integer> leftDistance;
    private Set<Integer> stationIndexes;
    private String receipt;

    private DistanceCalculator() {
    }

    public int getStationsAmount() {
        return stationsAmount;
    }

    public int getAmountForDelete() {
        return amountForDelete;
    }

    public Map<Integer, List<Integer>> getDistances() {
        return distances;
    }

    public Map<Integer, Integer> getDuplicates() {
        return duplicates;
    }

    public Map<Integer, Integer> getLongestDuplicate() {
        return longestDuplicate;
    }

    public Map<Integer, Integer> getLeftDistance() {
        return leftDistance;
    }

    public Set<Integer> getStationIndexes() {
        return stationIndexes;
    }

    public String getReceipt() {
        return receipt;
    }

    public static ReceiptBuilder builder() {
        return new DistanceCalculator().new ReceiptBuilder();
    }

    public class ReceiptBuilder {

        private ReceiptBuilder() {
        }

        //считаем расстояния между станциями
        public ReceiptBuilder calculateDistance(List<Integer> firstLine, List<Integer> stations) {
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
            DistanceCalculator.this.stationsAmount = firstLine.get(0);
            DistanceCalculator.this.amountForDelete = firstLine.get(1);
            DistanceCalculator.this.distances = rsl;
            return this;
        }

        //объединяем интервалы в серии
        public ReceiptBuilder calculateDuplicates() {
            Map<Integer, List<Integer>> distances = DistanceCalculator.this.getDistances();
            Map<Integer, Integer> rsl = new HashMap<>();
            for (int i = 0; i < distances.size(); i++) {
                List<Integer> inner = distances.get(i);
                for (int j = 0; j < inner.size(); j++) {
                    Integer amount = rsl.get(inner.get(j));
                    rsl.put(inner.get(j), (Objects.isNull(amount) ? 1 : amount + 1));
                }
            }
            DistanceCalculator.this.duplicates = rsl;
            return this;
        }

        //оставляем серии с наибольшими интервалами
        public ReceiptBuilder deleteSmallestDuplicate() {
            Map<Integer, Integer> in = DistanceCalculator.this.getDuplicates();
            Map<Integer, Integer> rsl = new HashMap<>();
            for (Map.Entry<Integer, Integer> entry : in.entrySet()) {
                int amount = entry.getValue();
                Integer station = rsl.get(amount);
                rsl.put(amount, (Objects.isNull(station) ? entry.getKey()
                        : Math.max(station, entry.getKey())));
            }
            DistanceCalculator.this.longestDuplicate = rsl;
            return this;
        }

        //оставляем серию с количеством отрезков наиболее близким к количеству,
        // которое нужно оставить
        public ReceiptBuilder deleteStations() {
            Map<Integer, Integer> intervals = DistanceCalculator.this.longestDuplicate;
            int amount = DistanceCalculator.this.distances.size() - DistanceCalculator.this.amountForDelete;
            Map<Integer, Integer> rsl = new HashMap<>();
            if (intervals.size() == 1) {
                rsl.put(amount, intervals.values().stream().findFirst().get());
            } else {
                Integer element = intervals.get(amount);
                int key = amount;
                if (Objects.isNull(element)) {
                    for (Integer i : intervals.keySet()) {
                        if (i < amount) {
                            key = i;
                        }
                    }
                }
                rsl.put(key, intervals.get(key));
            }
            DistanceCalculator.this.leftDistance = rsl;
            return this;
        }

        //сопоставляем расстояние со станциями, и в результат пишем те пары индексов,
        // между которыми это расстояние
        public ReceiptBuilder sortStations() {
            Map<Integer, List<Integer>> allStations = DistanceCalculator.this.distances;
            Set<Integer> rsl = new HashSet<>();
            int distance = DistanceCalculator.this.leftDistance.values()
                    .stream()
                    .findFirst()
                    .get();
            for (int i = 0; i < allStations.size(); i++) {
                List<Integer> inner = allStations.get(i);
                for (int j = 0; j < inner.size(); j++) {
                    if (inner.get(j) == distance) {
                        rsl.add(i);
                        rsl.add(i + j + 1);
                    }
                }
            }
            DistanceCalculator.this.stationIndexes = rsl;
            return this;
        }

        public ReceiptBuilder receipt() {
            List<Integer> stations = new ArrayList();
            for (Map.Entry<Integer, List<Integer>> entry : distances.entrySet()) {
                int stationNumber = entry.getKey();
                if (!stationIndexes.contains(stationNumber)) {
                    stations.add(stationNumber + 1);
                }
            }
            stations = stations.isEmpty() ? List.of(1) : stations;
            DistanceCalculator.this.receipt = String.format("%s" + System.lineSeparator() + "%s",
                    leftDistance.values().stream()
                            .findFirst()
                            .get(),
                    stations
            );
            return this;
        }

        public DistanceCalculator build() {
            return DistanceCalculator.this;
        }
    }
}
