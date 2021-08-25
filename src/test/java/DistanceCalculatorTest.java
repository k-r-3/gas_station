import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DistanceCalculatorTest {

    @Test
    public void when2StationDeleteThen2And45() {
        DistanceCalculator calculator = new DistanceCalculator();
        List<Integer> stations = new ArrayList<>(Arrays.asList(2, 10, 45, 33, 56));
        Map<Integer, List<Integer>> rsl = calculator.calculateDistance(stations);
        assertThat(rsl.toString(), is ("{0=[8, 43, 31, 54], 1=[35, 23, 46], 2=[12, 11], 3=[23], 4=[0]}"));
    }

    @Test
    public void when4StationLeft() {
        DistanceCalculator calculator = new DistanceCalculator();
        Map<Integer, Integer> in = new HashMap<>();
        in.put(1, 39);
        in.put(2, 40);
        in.put(3, 60);
        Map<Integer, Integer> rsl = calculator.deleteStations(4, in);
        assertThat(rsl, is(Map.of(3, 60)));
    }

    @Test
    public void findStations() {
        DistanceCalculator calculator = new DistanceCalculator();
        var distance = Map.of(2, 23);
        Map<Integer, List<Integer>> allStations = Map.of(0, List.of(8, 43, 31, 54),1, List.of(35, 23, 46),
                2, List.of(12, 11), 3, List.of(23));
        Set<Integer> leftStations = calculator.sortStations(allStations, distance);
        System.out.println(leftStations);
    }

    @Test
    public void when5StationsThen2Duplicates() {
        DistanceCalculator calculator = new DistanceCalculator();
        Map<Integer, List<Integer>> in = new HashMap<>();
        in.put(0, Arrays.asList(8, 43, 31, 54));
        in.put(1, Arrays.asList(35, 23, 46));
        in.put(2, Arrays.asList(12, 11));
        in.put(3, Arrays.asList(23));
        Map<Integer, Integer> rsl = calculator.calculateDuplicates(in);
        System.out.println(rsl);
        assertThat(rsl.get(35), is(1));
        assertThat(rsl.get(23), is(2));
    }

    @Test
    public void whenManyIntervalsThenLeftGreatest() {
        DistanceCalculator calculator = new DistanceCalculator();
        Map<Integer, Integer> in = new HashMap<>();
        in.put(35, 1);
        in.put(12, 1);
        in.put(30, 1);
        in.put(39, 1);
        in.put(23, 2);
        in.put(40, 2);
        in.put(64, 3);
        in.put(4, 3);
        Map<Integer, Integer> rsl = calculator.deleteSmallestDuplicate(in);
        System.out.println(rsl);
        assertThat(rsl, is(Map.of(1, 39, 2, 40, 3, 64)));
    }

    @Test
    public void whenAmountStationLeft() {
        DistanceCalculator calculator = new DistanceCalculator();
        Map<Integer, Integer> in = new HashMap<>();
        in.put(1, 39);
        in.put(2, 40);
        in.put(3, 60);
        Map<Integer, Integer> rslFor3 = calculator.deleteStations(4, in);
        Map<Integer, Integer> rslFor4 = calculator.deleteStations(3, in);
        assertThat(rslFor3, is(Map.of(3, 60)));
        assertThat(rslFor4, is(Map.of(3, 60)));
    }

    @Test
    public void whenSortStations() {
        DistanceCalculator calculator = new DistanceCalculator();
        var distance = Map.of(2, 23);
        Map<Integer, List<Integer>> allStations = Map.of(0, List.of(8, 43, 31, 54),1, List.of(35, 23, 46),
                2, List.of(12, 11), 3, List.of(23));
        Set<Integer> leftStations = calculator.sortStations(allStations, distance);
        assertThat(leftStations, is(Set.of(1, 3, 4)));
    }

    @Test
    public void whenInput5And3ThenReceipt() {
        DistanceCalculator calculator = new DistanceCalculator();
        String rsl = calculator.receipt(List.of(5, 3), List.of(12, 96, 6, 34, 73));
        System.out.println(rsl);
    }

    @Test
    public void whenInput6And3ThenReceipt() {
        DistanceCalculator calculator = new DistanceCalculator();
        String rsl = calculator.receipt(List.of(6, 3), List.of(12, 15, 66, 24, 120, 53));
        System.out.println(rsl);
    }

    @Test
    public void whenInput4And1ThenReceipt() {
        DistanceCalculator calculator = new DistanceCalculator();
        String rsl = calculator.receipt(List.of(4, 1), List.of(40, 80, 50, 60));
        System.out.println(rsl);
    }

    @Test
    public void whenInput4And2ThenReceipt() {
        DistanceCalculator calculator = new DistanceCalculator();
        String rsl = calculator.receipt(List.of(4, 2), List.of(40, 82, 8, 71));
        System.out.println(rsl);
    }

    @Test
    public void whenInput3And1ThenReceipt() {
        DistanceCalculator calculator = new DistanceCalculator();
        String rsl = calculator.receipt(List.of(3, 1), List.of(2, 2, 2));
        System.out.println(rsl);
    }

    @Test
    public void whenInput5And2ThenReceipt() {
        DistanceCalculator calculator = new DistanceCalculator();
        String rsl = calculator.receipt(List.of(5, 2), List.of(2, 12, 8, 8, 22));
        System.out.println(rsl);
    }
}