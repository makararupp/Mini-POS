package makara.co.min_pos.util;

import java.util.List;
import java.util.stream.Collectors;

public class ListConverter {
    public static List<Integer> convertStringListToIntegerList(List<String> stringList){
        return stringList.stream()
                .map(Integer::parseInt) // Convert each String to Integer
                .collect(Collectors.toList());
    }

    public static List<Integer> getEvenNumbers(List<Integer> numbers){
        return numbers.stream()
                .filter(number -> number% 2==0)  // Filter even numbers
                .collect(Collectors.toList());
    }

}
