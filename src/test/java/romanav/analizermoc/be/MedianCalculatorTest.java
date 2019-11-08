package romanav.analizermoc.be;

import org.assertj.core.util.Lists;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MedianCalculatorTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private MedianCalculator calc = new MedianCalculator();

    @Test
    public void nullTest(){
        exceptionRule.expect(RuntimeException.class);
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
    public void nullInTheList(){
        throw new NotImplementedException();
    }


//    public double calculate()
}
