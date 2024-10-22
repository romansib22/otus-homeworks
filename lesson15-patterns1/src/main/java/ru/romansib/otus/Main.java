package ru.romansib.otus;

public class Main {
    public static void main(String[] args) {

        Product product = new Product.ProductBuilder()
                .id(1)
                .title("title")
                .description("description")
                .cost(100.0f)
                .weight(10)
                .width(20)
                .length(30)
                .height(50)
                .build();

        Box box = new Box();

        MyIterator iterator = new MyIterator(box);
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}