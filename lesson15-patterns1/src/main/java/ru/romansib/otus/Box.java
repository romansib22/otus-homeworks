package ru.romansib.otus;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

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

    public void showBoxContent() {
        BoxIterator iterator = new BoxIterator(this);
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    private class BoxIterator implements Iterator<Integer> {
        private int currentIndex;
        private List<Integer> currentList;

        private final List<Integer> firstSection;
        private final List<Integer> secondSection;
        private final List<Integer> thirdSection;
        private final List<Integer> fourthSection;

        public BoxIterator(Box box) {
            this.firstSection = box.getFirstSection();
            this.secondSection = box.getSecondSection();
            this.thirdSection = box.getThirdSection();
            this.fourthSection = box.getFourthSection();
            this.currentIndex = getMaxIndex(box);
            this.currentList = fourthSection;
        }

        private int getMaxIndex(Box box) {
            int maxIndex = 0;
            maxIndex = compare(box.getFirstSection(), maxIndex);
            maxIndex = compare(box.getSecondSection(), maxIndex);
            maxIndex = compare(box.getThirdSection(), maxIndex);
            maxIndex = compare(box.getFourthSection(), maxIndex);
            return maxIndex;
        }

        private int compare(List<Integer> l1, int maxIndex) {
            if (l1.size() - 1 > maxIndex)
                return l1.size() - 1;
            return maxIndex;
        }

        @Override
        public boolean hasNext() {
            while (currentIndex >= 0) {
                return true;
            }
            return false;
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int result = currentList.get(currentIndex);
            if (currentList.equals(firstSection)) {
                currentIndex--;
            }
            switchList();
            return result;
        }

        private void switchList() {
            if (currentList.equals(firstSection)) {
                currentList = fourthSection;
                return;
            }
            if (currentList.equals(secondSection)) {
                currentList = firstSection;
                return;
            }
            if (currentList.equals(thirdSection)) {
                currentList = secondSection;
                return;
            }
            if (currentList.equals(fourthSection)) {
                currentList = thirdSection;
            }
        }
    }
}
