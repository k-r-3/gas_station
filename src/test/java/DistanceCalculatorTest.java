import org.junit.Test;
import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DistanceCalculatorTest {

    @Test
    public void when2StationDeleteThen2And45() {
        DistanceCalculator calculator = DistanceCalculator.builder()
                .calculateDistance(List.of(5, 3), Arrays.asList(2, 10, 45, 33, 56))
                .build();
        assertEquals(calculator.getDistances().toString(),
                "{0=[8, 43, 31, 54], 1=[35, 23, 46], 2=[12, 11], 3=[23], 4=[0]}");
    }

    @Test
    public void when5StationsThen2Duplicates() {
        DistanceCalculator calculator = DistanceCalculator.builder()
                .calculateDistance(List.of(5, 3), Arrays.asList(2, 10, 45, 33, 56))
                .calculateDuplicates()
                .build();
        Map<Integer, Integer> rsl = calculator.getDuplicates();
        assertThat(rsl.get(35), is(1));
        assertThat(rsl.get(23), is(2));
    }

    @Test
    public void whenManyIntervalsThenLeftGreatest() {
        DistanceCalculator calculator = DistanceCalculator.builder()
                .calculateDistance(List.of(7, 3), Arrays.asList(2, 10, 45, 33, 56, 64, 79))
                .calculateDuplicates()
                .deleteSmallestDuplicate()
                .build();
        Map<Integer, Integer> rsl = calculator.getLongestDuplicate();
        assertThat(rsl, is(Map.of(1, 77, 2, 54, 3, 23)));
    }

    @Test
    public void whenAmountStationLeft() {
        DistanceCalculator calculator1 = DistanceCalculator.builder()
                .calculateDistance(List.of(7, 3), Arrays.asList(2, 10, 45, 33, 56, 64, 79))
                .calculateDuplicates()
                .deleteSmallestDuplicate()
                .deleteStations()
                .build();
        DistanceCalculator calculator2 = DistanceCalculator.builder()
                .calculateDistance(List.of(7, 4), Arrays.asList(2, 10, 45, 33, 56, 64, 79))
                .calculateDuplicates()
                .deleteSmallestDuplicate()
                .deleteStations()
                .build();
        Map<Integer, Integer> rslFor3 = calculator1.getLeftDistance();
        Map<Integer, Integer> rslFor4 = calculator2.getLeftDistance();
        assertThat(rslFor3, is(Map.of(3, 23)));
        assertThat(rslFor4, is(Map.of(3, 23)));
    }

    @Test
    public void whenSortStations() {
        DistanceCalculator calculator = DistanceCalculator.builder()
                .calculateDistance(List.of(7, 4), Arrays.asList(2, 10, 33, 45, 56, 64, 79))
                .calculateDuplicates()
                .deleteSmallestDuplicate()
                .deleteStations()
                .sortStations()
                .build();
        Set<Integer> leftStations = calculator.getStationIndexes();
        assertThat(leftStations, is(Set.of(1, 2, 4, 6)));
    }

    @Test
    public void whenInput5And3ThenReceipt() {
        DistanceCalculator calculator = DistanceCalculator.builder()
                .calculateDistance(List.of(5, 3), List.of(12, 96, 6, 34, 73))
                .calculateDuplicates()
                .deleteSmallestDuplicate()
                .deleteStations()
                .sortStations()
                .receipt()
                .build();
        String rsl = calculator.getReceipt();
        String expected = "90" + System.lineSeparator() + "[1, 4, 5]";
        assertEquals(expected, rsl);
    }

    @Test
    public void whenInput6And3ThenReceipt() {
        DistanceCalculator calculator = DistanceCalculator.builder()
                .calculateDistance(List.of(6, 3), List.of(12, 15, 66, 24, 120, 53))
                .calculateDuplicates()
                .deleteSmallestDuplicate()
                .deleteStations()
                .sortStations()
                .receipt()
                .build();
        String rsl = calculator.getReceipt();
        String expected = "54" + System.lineSeparator() + "[2, 4, 6]";
        assertEquals(expected, rsl);
    }

    @Test
    public void whenInput4And2ThenReceipt() {
        DistanceCalculator calculator = DistanceCalculator.builder()
                .calculateDistance(List.of(4, 2), List.of(40, 82, 8, 71))
                .calculateDuplicates()
                .deleteSmallestDuplicate()
                .deleteStations()
                .sortStations()
                .receipt()
                .build();
        String rsl = calculator.getReceipt();
        String expected = "74" + System.lineSeparator() + "[1, 4]";
        assertEquals(expected, rsl);
    }

    @Test
    public void whenInput4And1ThenReceipt() {
        DistanceCalculator calculator = DistanceCalculator.builder()
                .calculateDistance(List.of(4, 1), List.of(40, 80, 50, 60))
                .calculateDuplicates()
                .deleteSmallestDuplicate()
                .deleteStations()
                .sortStations()
                .receipt()
                .build();
        String rsl = calculator.getReceipt();
        String expected = "20" + System.lineSeparator() + "[3]";
        assertEquals(expected, rsl);
    }

    @Test
    public void whenInput3And1ThenReceipt() {
        DistanceCalculator calculator = DistanceCalculator.builder()
                .calculateDistance(List.of(3, 1), List.of(2, 2, 2))
                .calculateDuplicates()
                .deleteSmallestDuplicate()
                .deleteStations()
                .sortStations()
                .receipt()
                .build();
        String rsl = calculator.getReceipt();
        String expected = "0" + System.lineSeparator() + "[1]";
        assertEquals(expected, rsl);
    }
}