package com.romanm.livecoding;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
class LivecodingApplicationTests {

    @BeforeEach
    void setUp() {
       System.out.println("before init");
    }

    /**
     * List<Integer> list = Arrays.asList(numbers);
     * */
    @Test
    void test1() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        numbers.stream()
                .map(x -> {
                    System.out.println(x);
                    return x;
                })
                .filter(x -> x > 2)
                .map(x -> {
                    System.out.println(x);
                    return x;
                })
                .toList();
    }

    /**
     * Задан массив целых чисел от 1 до 100. В этом массиве есть
     * дубликаты. Задача: найти все дубликаты и указать их количество.
     * Например 1, 2, 3, 4, 4, 5, 6, 7, 7, 7, 4 — 2 штуки, 7 — 3 штуки.
     * */
    @Test
    void test2() {
        int[] arr = new int[]{1, 2, 3, 4, 4, 5, 6, 7, 7, 7, 4};
        Map<Integer, List<Integer>> sub = new HashMap<>();

        Arrays.stream(arr).forEach(i -> {
            List<Integer> val = sub.get(i);
            if (val == null) {
                List<Integer> vals = new ArrayList<>();
                vals.add(i);
                sub.put(i, vals);
            }
            if (val != null) {
                val.add(i);
                sub.put(i, val);
            }
        });

        System.out.println("Map: "+sub.entrySet());
        sub.values().forEach((v) -> {
            if (v.size() > 1) {
                System.out.println(String.format("Дубли %d -> %d раза: ", v.get(0), v.size()));
            }
        });
    }

    /**
     * Есть строка «Привет, Кккккатовццццы!», надо избавиться от дубликатов и вывести «Привет, Катовцы!».
     * */
    @Test
    void test3() {
        String test = "Привет, Кккккатовццццы!";
        List<String> elements = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        AtomicInteger index = new AtomicInteger();

        Arrays.stream(test.split("")).forEach(s -> {
           elements.add(s.toLowerCase());
           if (elements.size() > 0) {
              String prev = (index.get() - 1) >= 1 ? elements.get(index.get() - 1) : "";
              if (!prev.equals(s)) {
                  sb.append(s);
              }
           }
           index.getAndIncrement();
        });

        System.out.println("Исходная строка: "+test);
        System.out.println("Результат: "+sb.toString());
    }


    private String calculate(String input) {
        if (input == "") {
            return "0.00 0.00";
        }

        String[] data = input.split("\\s");
        String shape = data[0];
        String value = data.length > 1 ? data[1] : "1";

        if (shape.equalsIgnoreCase("круг")) {
            double s = Math.PI*Math.pow(Double.parseDouble(value), 2);
            double p = Math.PI*2*Double.parseDouble(value);
            return String.format("%.2f %.2f", s, p);
        }

        if (shape.equalsIgnoreCase("квадрат")) {
            double s = Math.pow(Double.parseDouble(value), 2);
            double p = 4*Double.parseDouble(value);
            return String.format("%.2f %.2f", s, p);
        }

        return "0.00 0.00";
    }

    /**
     * Задача №1 из hh.ru
     * ввод: круг 3 Вывод: 28.27 18.85 (площадь, периметр)
     * ввод: квадрат 3 Вывод: 9.00 12.00 (площадь, периметр)
     * */
    @Test
    void test4() {
        System.out.println(calculate("круг 3"));
        System.out.println(calculate("квадрат 3"));
    }

    /**
     * Дан массив целых чисел, отсортированный по возрастанию, верните массив квадратов каждого числа,
     * отсортированный по возрастанию.
     * Ввод: [-7,-3,2,3,11]
     * Вывод: [4,9,9,49,121]
     * */
    @Test
    void test5() {
        double[] arr = new double[]{-7,-3,2,3,11};
        double[] result = Arrays.stream(arr).map(elem -> Math.pow(elem, 2)).sorted().toArray();

        System.out.println("Исходные данные: [-7,-3,2,3,11] ");

        var i = 0;
        System.out.print("Результат: ");
        while (i < result.length) {
            System.out.print(String.format("%.0f ", result[i]));
            i++;
        }
    }

    /**
     Из массива целых чисел, надо найти подмассив с наибольшей суммой и вернуть её сумму.
     Например:
     Ввод: [-2,1,-3,4,-1,2,1,-5,4]
     Подмассив [4,-1,2,1] имеет наибольшую сумму 6.
     Вывод: 6
     * */
    @Test
    public void test6() {
        Integer[] arr = new Integer[]{-2,1,-3,4,-1,2,1,-5,4};

        Map<Integer, List<Integer>> windows = new HashMap<>();
        var numElements = 4;
        var iter = 1;
        var numIterations = (arr.length - numElements)+1;
        List<Integer> buff = Arrays.asList(arr);
        while (iter <= numIterations) {
            var list = new ArrayList<Integer>();
            for (var i = 0; i < numElements; i++) {
                list.add(buff.get((iter-1)+i));
            }
            windows.put(iter, list);
            iter++;
        }
        //Показать подмассивы по индексам
        windows.forEach((integer, integers) -> System.out.println(String.format("%d, %s", integer, integers.toString())));
        //Подсчитать суммы элементов подмассивов
        var results = windows.values().stream().map(integers -> integers.stream().mapToInt(integer -> integer.intValue()).sum()).toArray();
        //Показать результаты
        System.out.println("=========");
        AtomicInteger n = new AtomicInteger();
        Arrays.stream(results).forEach(o -> {
            System.out.println(String.format("%s %s", o.toString(), windows.get(n.get()+1)));
            n.getAndIncrement();
        });
    }

    private static void inc(Integer i) {
        i++;
        System.out.println("inc(): "+i);
    }

    /**
     *  Что выведет данный тест? Ответ: 1
     *  Видимо на ссылочные классы обертки простых типов законы ссылочных типов объектов не работают
     * */
    @Test
    public void test7() {
        Integer i = Integer.valueOf(1);
        inc(i);
        System.out.println("Ответ: "+i);
    }

    private static void inc_(String s) {
        s = s + "2";
        System.out.println("inc_(): "+s);
    }

    /**
     * Что выведет данный тест? Ответ: 1
     * Со строками, видимо, то же самое!
     * */
    @Test
    public void test8() {
        String i = "1";
        inc_(i);
        System.out.println("Ответ: "+i);
    }

    /**
     * Что выведет данный тест? Ответ: false
     * Разные объекты (ссылки)
     * */
    @Test
    public void test9() {
        Integer i1 = Integer.valueOf(717);
        Integer i2 = Integer.valueOf(717);
        System.out.println(i1 == i2);
    }


    /**
     *  Напишите программу на Java для переворачивания строки, изменив расположение символов в
     *  строке задом наперёд без использования встроенных в String функций.
     * */
    @Test
    public void test10() {
        String task = "Задача";
        /**Вариант 1*/
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(task);
        var result = stringBuilder.reverse();
        System.out.println("Ответ: "+result);

        /**Вариант 2*/
        String[] chrs = task.split("");
        String buff = "";
        for (var i = chrs.length; i != 0; i--) {
            buff+=chrs[i-1];
        }
        System.out.println("Ответ: "+buff);
    }

    /**
     * https://itvdn.com/ru/blog/article/test-java-20
     *  Напишите программу на Java, чтобы поменять местами значения, хранящиеся
     *  в двух переменных, без использования третьей переменной.
     * */
    @Test
    public void test11() {
        var a = 10;
        var b = 20;
        System.out.println(String.format("Было: a=%d b=%d", a, b));

        a = a + b; //30
        b = a - b; //10
        a = a - b; //20
        System.out.println(String.format("Стало: a=%d b=%d", a, b));
    }

    /**
     * Напишите программу на Java для подсчета количества конкретных слов в строке, используя HashMap
     * */
    @Test
    public void test12() {
        String st = "Current task posted for Java developers developers";
        String[] words = st.split("\\s+");
        Map<String, List<String>> map = new HashMap<>();
        for (String word: words) {
          List<String> mapList = map.get(word);
          if (mapList != null) {
              mapList.add(word);
              map.put(word, mapList);
          } else {
              List<String> innerList = new ArrayList<>();
              innerList.add(word);
              map.put(word, innerList);
          }
        }

        map.entrySet().forEach(es -> {
            System.out.println(String.format("Слово '%s' -> Количество: %s", es.getKey(), es.getValue().size()));
        });

    }

    /**
     * Тест на натуральное число
     */
    @Test
    public void test13() {
        int value = 31;
        boolean natural = true;
        for (int i = 2; i <= value/2; i++) {
            System.out.println(String.format("%d", value%i));
            if (value%i == 0) {
               natural = false;
               break;
            }
        }

        if (natural) {
            System.out.println(String.format("%d - натуральное число", value));
        } else {
            System.out.println(String.format("%d - не натуральное число", value));
        }
    }

    /**
     * Тест на палиндром (строка одинаково читаема с обеих сторон)
     */
    @Test
    public void test14() {
       String testString = "121";
       StringBuilder stringBuilder = new StringBuilder();
       stringBuilder.append(testString);
       if (stringBuilder.reverse().toString().equals(testString)) {
           System.out.println(String.format("Строка '%s' является полиндромом", testString));
       } else {
           System.out.println(String.format("Строка '%s' не является полиндромом", testString));
       }
    }
}




