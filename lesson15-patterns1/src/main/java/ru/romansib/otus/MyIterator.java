package ru.romansib.otus;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class MyIterator implements Iterator<Integer> {
    private int currentIndex;
    private final int firstSectionMaxIndex;
    private final int secondSectionMaxIndex;
    private final int thirdSectionMaxIndex;
    private final int fourthSectionMaxIndex;
    private int currentIndex1;
    private int currentIndex2;
    private int currentIndex3;
    private int currentIndex4;
    private final List<Integer> firstSection;
    private final List<Integer> secondSection;
    private final List<Integer> thirdSection;
    private final List<Integer> fourthSection;

    public MyIterator(Box box) {
        this.firstSection = box.getFirstSection();
        this.secondSection = box.getSecondSection();
        this.thirdSection = box.getThirdSection();
        this.fourthSection = box.getFourthSection();
        this.currentIndex = getMaxIndex(box);
        this.firstSectionMaxIndex = box.getFirstSection().size() - 1;
        this.secondSectionMaxIndex = box.getSecondSection().size() - 1;
        this.thirdSectionMaxIndex = box.getThirdSection().size() - 1;
        this.fourthSectionMaxIndex = box.getFourthSection().size() - 1;

        this.currentIndex1 = this.firstSectionMaxIndex;
        this.currentIndex2 = this.secondSectionMaxIndex;
        this.currentIndex3 = this.thirdSectionMaxIndex;
        this.currentIndex4 = this.fourthSectionMaxIndex;
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
        checkIndex();
        while (currentIndex >= 0) {
            return true;
        }
        return false;
    }

    @Override
    public Integer next() {
        Integer result = null;

        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        if (fourthSectionMaxIndex >= currentIndex && currentIndex4 == currentIndex) {
            currentIndex4--;
            return fourthSection.get(currentIndex);
        }
        if (thirdSectionMaxIndex >= currentIndex && currentIndex3 == currentIndex) {
            currentIndex3--;
            return thirdSection.get(currentIndex);
        }
        if (secondSectionMaxIndex >= currentIndex && currentIndex2 == currentIndex) {
            currentIndex2--;
            return secondSection.get(currentIndex);
        }
        if (firstSectionMaxIndex >= currentIndex && currentIndex1 == currentIndex) {
            currentIndex1--;
            return firstSection.get(currentIndex);
        }

        return result;
    }

    private void checkIndex() {
        if (currentIndex1 < currentIndex && currentIndex2 < currentIndex && currentIndex3 < currentIndex && currentIndex4 < currentIndex) {
            currentIndex = currentIndex1;
        }
    }

}
