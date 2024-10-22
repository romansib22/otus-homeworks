package ru.romansib.otus;

import java.util.List;

public class Box {
    private final List<Integer> firstSection = List.of(1, 5, 9, 13, 17);
    private final List<Integer> secondSection = List.of(2, 6, 10, 14, 18);
    private final List<Integer> thirdSection = List.of(3, 7, 11, 15, 19);
    private final List<Integer> fourthSection = List.of(4, 8, 12, 16, 20);


    public List<Integer> getFirstSection() {
        return firstSection;
    }

    public List<Integer> getSecondSection() {
        return secondSection;
    }

    public List<Integer> getThirdSection() {
        return thirdSection;
    }

    public List<Integer> getFourthSection() {
        return fourthSection;
    }
}
