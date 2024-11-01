package ru.romansib.otus;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Box implements Iterable<Integer> {
    private final List<Integer> firstSection = List.of(1, 5, 9, 13, 17);
    private final List<Integer> secondSection = List.of(2, 6, 10, 14, 18);
    private final List<Integer> thirdSection = List.of(3, 7, 11, 15, 19);
    private final List<Integer> fourthSection = List.of(4, 8, 12, 16, 20, 200);


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
        Iterator<Integer> iterator = iterator();
        while (iterator.hasNext()) {
            Integer nextval = iterator.next();
            System.out.println(nextval == null ? "No element" : nextval);
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return new BoxIterator();
    }

    private class BoxIterator implements Iterator<Integer> {
        private int currentIndex;
        private List<Integer> currentList;

        public BoxIterator() {
            this.currentIndex = getMaxIndex();
            this.currentList = fourthSection;
        }

        private int getMaxIndex() {
            int maxIndex = 0;
            maxIndex = compare(getFirstSection(), maxIndex);
            maxIndex = compare(getSecondSection(), maxIndex);
            maxIndex = compare(getThirdSection(), maxIndex);
            maxIndex = compare(getFourthSection(), maxIndex);
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
            Integer result;
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if (currentList.size() < currentIndex + 1) {
                result = null;
            } else {
                result = currentList.get(currentIndex);
            }

            if (currentList.equals(firstSection)) {
                currentIndex--;
            }
            switchList();
            return result;
        }

        private void switchList() {
            if (currentList == firstSection) {
                currentList = fourthSection;
                return;
            }
            if (currentList == secondSection) {
                currentList = firstSection;
                return;
            }
            if (currentList == thirdSection) {
                currentList = secondSection;
                return;
            }
            if (currentList == fourthSection) {
                currentList = thirdSection;
            }
        }
    }
}
