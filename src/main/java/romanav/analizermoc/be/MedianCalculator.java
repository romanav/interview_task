package romanav.analizermoc.be;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MedianCalculator {
    public double calculate(List<Integer> values) {

        assertListHasValues(values);

        List<Integer> copy = new ArrayList<>(values);
        Collections.sort(copy);

        if( copy.size() % 2 == 1) {
            return copy.get(copy.size() / 2);
        }
        else{
            return medianOfTwoNumbers(copy.get(copy.size()/2)-1,copy.get(copy.size()/2));
        }
    }

    private double medianOfTwoNumbers(int first, int second){
        return (double)(first+second)/2;
    }

    private void assertListHasValues(List<Integer> values) {
        if (values == null){
            throw new NullPointerException("Cannot parse null");
        }

        if (values.size() == 0){
            throw new RuntimeException("List should be not empty");
        }
    }
}
