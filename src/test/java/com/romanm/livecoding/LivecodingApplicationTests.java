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
}

