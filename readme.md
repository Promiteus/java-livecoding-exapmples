### Примеры задач по Java на лайвкодинг.

1. Задан массив целых чисел от 1 до 100. В этом массиве есть
   дубликаты. Задача: найти все дубликаты и указать их количество.
   Например 1, 2, 3, 4, 4, 5, 6, 7, 7, 7, 4 — 2 штуки, 7 — 3 штуки.
   Решение:
   
   ```@Test
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

   ``` @Test
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