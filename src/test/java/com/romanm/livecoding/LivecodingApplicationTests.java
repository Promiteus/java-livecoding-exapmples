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
}

