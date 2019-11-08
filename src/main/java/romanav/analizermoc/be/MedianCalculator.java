package romanav.analizermoc.be;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class MedianCalculator {
    public double calculate(List<Integer> values) {

        assertListHasValues(values);

        if(values.size() == 1){
            return values.get(0);
        }

        if(values.size() == 2){
            return medianOfTwoNumbers(values.get(0), values.get(1));
        }

        throw new NotImplementedException();
    }

    private double medianOfTwoNumbers(int first, int second){
        return (double)(first+second)/2;
    }

    private void assertListHasValues(List<Integer> values) {
        if (values == null){
            throw new RuntimeException("Cannot parse null");
        }

        if (values.size() == 0){
            throw new RuntimeException("List should be not empty");
        }
    }
}
