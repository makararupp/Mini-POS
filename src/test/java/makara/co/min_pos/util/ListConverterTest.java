package makara.co.min_pos.util;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ListConverterTest {
    //convert list of string to Integer

    @Test
    public void testConvertStringListToIntegerList_ValidaInput(){
        // Arrange
        List<String> stringList = Arrays.asList("1","2","3","4");
        //Act
        List<Integer> integerList = ListConverter.convertStringListToIntegerList(stringList);
        // Assert
        assertEquals(Arrays.asList(1,2,3,4),integerList);
    }
    @Test
    public void testConvertStringListToIntegerList_EmptyList(){
        // Arrange
        List<String> stringList = Collections.emptyList();
        // Act
        List<Integer> integerList  = ListConverter.convertStringListToIntegerList(stringList);
        //Then or assert
        assertTrue(integerList.isEmpty());

    }
    @Test
    public void testConvertStringListToIntegerList_InvalidInput(){
        //Arrange
        List<String> stringList = Arrays.asList("1","two","3");
        // Act & Assert
        assertThrows(NumberFormatException.class,()->{
            ListConverter.convertStringListToIntegerList(stringList);
        });
    }

   //Testing for even number %2 = 0
    @Test
    public void testGetEvenNumbers_ValidInput(){
        List<Integer> numbers = Arrays.asList(1,2,3,4,5,6);

        List<Integer>  result  = ListConverter.getEvenNumbers(numbers);

        assertEquals(Arrays.asList(2,4,6),result);

    }
    @Test
    public void testGetEvenNumbers_EmptyList(){
        List<Integer> numbers = Collections.emptyList();

        List<Integer> evenNumbers = ListConverter.getEvenNumbers(numbers);

        assertTrue(evenNumbers.isEmpty());
    }
    @Test
    public void testGetEventNumbers_NoEventNumbers(){
        List<Integer> numbers = Arrays.asList(1,3,5,7);

        List<Integer> eventNumbers = ListConverter.getEvenNumbers(numbers);

        assertTrue(eventNumbers.isEmpty());
    }

    @Test
    public void showPassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //String encode = passwordEncoder.encode("prime123");
       // String encode = passwordEncoder.encode("rey123");
         String encode = passwordEncoder.encode("dara123");
        System.out.println(encode);
    }

}
