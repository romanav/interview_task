package romanav.analizermoc.be;

import org.assertj.core.util.Lists;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MedianCalculatorTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private MedianCalculator calc = new MedianCalculator();

    @Test
    public void nullTest(){
        exceptionRule.expect(NullPointerException.class);
        exceptionRule.expectMessage("Cannot parse null");
        calc.calculate(null);
    }

    @Test
    public void emptyList(){
        exceptionRule.expect(RuntimeException.class);
        exceptionRule.expectMessage("List should be not empty");
        calc.calculate(Lists.newArrayList());
    }

    @Test
    public void simpleMedian(){
        assertThat(calc.calculate(Collections.singletonList(1))).isEqualTo(1);
        assertThat(calc.calculate(Arrays.asList(1,2))).isEqualTo(1.5);
    }

    @Test
    public void testListObjectWasNotTouched(){
        List<Integer> inputList = Arrays.asList(3,1,2);
        calc.calculate(inputList);
        assertThat(inputList).isEqualTo(Arrays.asList(3,1,2));
    }

    @Test
    public void getMedianOfArrainWithOddNumberOfValues(){
        assertThat(calc.calculate(Arrays.asList(30,15,22))).isEqualTo(22);
        assertThat(calc.calculate(Arrays.asList(1,2,3,4))).isEqualTo(2.5);
        assertThat(calc.calculate(Arrays.asList(1,2,3,4,5,6,7,8))).isEqualTo(4.5);
        assertThat(calc.calculate(Arrays.asList(1,2,3,4,5,6,7))).isEqualTo(4);
    }


    @Test
    public void nullInTheList(){
        exceptionRule.expect(NullPointerException.class);
        assertThat(calc.calculate(Arrays.asList(1,2,3,null))).isEqualTo(2.5);
    }

}
