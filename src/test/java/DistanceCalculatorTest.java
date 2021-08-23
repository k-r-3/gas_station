import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DistanceCalculatorTest {

    @Test
    public void when2StationDeleteThen2And45() {
        DistanceCalculator calculator = new DistanceCalculator();
        List<Integer> stations = new ArrayList<>(Arrays.asList(2, 10, 45, 33, 56));
        Map<Integer, List<Integer>> rsl = calculator.calculateDistance(stations);
        assertThat(rsl.toString(), is ("{0=[8, 43, 31, 54], 1=[35, 23, 46], 2=[12, 11], 3=[23]}"));
    }

}