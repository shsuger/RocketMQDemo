import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @Author shsuger
 * @Date 2018/6/6
 */
public class ParallelStreamTest {
    public static void main(String[] args) {
    /*    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        numbers.parallelStream()
                .forEach(System.out::println);*/

        List<String> myList = Arrays.asList("a1", "a2", "b1", "c2", "c1");

        myList.stream()
                .filter(s -> s.startsWith("c"))
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::println);



    }
    @Test
    public void parallelStreamorder (){
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        /**
         * Stream具有平行处理能力，处理的过程会分而治之，也就是将一个大任务切分成多个小任务
         * 这表示每个任务都是一个操作，因此像以下的程式片段你得到的展示顺序不一定会是1、2、3、4、5、6、7、8、9
         * 而可能是任意的顺序
         */
        numbers.parallelStream().forEach(System.out::println);

        /**
         * 就forEach()这个操作來讲，如果平行处理时，希望最后顺序是按照原来Stream的数据顺序，
         * 那可以调用forEachOrdered()
         * 如果forEachOrdered()中间有其他如filter()的中介操作，会试着平行化处理，
         * 然后最终forEachOrdered()会以原数据顺序处理，
         * 因此，使用forEachOrdered()这类的有序处理,可能会（或完全失去）失去平行化的一些优势
         */
        numbers.parallelStream().forEachOrdered(System.out::println);
    }





}
