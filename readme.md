### Примеры задач по Java на лайвкодинг.

1. Задан массив целых чисел от 1 до 100. В этом массиве есть
   дубликаты. Задача: найти все дубликаты и указать их количество.
   Например 1, 2, 3, 4, 4, 5, 6, 7, 7, 7, 4 — 2 штуки, 7 — 3 штуки.
   Решение:
   
   ```
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
   ```
 2. Есть строка «Привет, Кккккатовццццы!», надо избавиться от дубликатов и вывести «Привет, Катовцы!».
    Решение:
   ```
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
   ```  
3. Задача №1 из hh.ru
   Ввод: круг 3   
   Вывод: 28.27 18.85 (площадь, периметр)
   Ввод: квадрат 3  
   Вывод: 9.00 12.00 (площадь, периметр).  
   Решение:  
   ``` 
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
   
    @Test
    void test4() {
        System.out.println(calculate("круг 3"));
        System.out.println(calculate("квадрат 3"));
    }
   ```  
4. Дан массив целых чисел, отсортированный по возрастанию, верните массив квадратов каждого числа,
   отсортированный по возрастанию.  
   Ввод: [-7,-3,2,3,11]  
   Вывод: [4,9,9,49,121]  
   ``` 
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
   ```  
5. Из массива целых чисел, надо найти подмассив с наибольшей суммой и вернуть её сумму.
   Например:   
   Ввод: [-2,1,-3,4,-1,2,1,-5,4]  
   Подмассив [4,-1,2,1] имеет наибольшую сумму 6.  
   Вывод: 6  
   ```
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
     
        windows.forEach((integer, integers) -> System.out.println(String.format("%d, %s", integer, integers.toString())));

        var results = windows.values().stream().map(integers -> integers.stream().mapToInt(integer -> integer.intValue()).sum()).toArray();
       
      
        AtomicInteger n = new AtomicInteger();
        Arrays.stream(results).forEach(o -> {
            System.out.println(String.format("%s %s", o.toString(), windows.get(n.get()+1)));
            n.getAndIncrement();
        });
    }
   ```
   
