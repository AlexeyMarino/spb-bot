package com.alexeymarino.botserver.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ScrollableListWrapper {

    private final List<String> listToScroll;
    private int currentPage = 0;

    public ScrollableListWrapper(List<String> listToScroll) {
        this.listToScroll = new LinkedList<>(listToScroll);
    }

    public ScrollableListWrapper(Set<String> profileSet) {
        listToScroll = new LinkedList<>(profileSet);
    }

    public String getCurrentPage() {
        return listToScroll.get(currentPage);
    }

    public String getNextPage() {
        return listToScroll.get(currentPage++);
    }

    public boolean isLast() {
        return currentPage == listToScroll.size() - 1;
    }

    public int getSize() {
        return listToScroll.size();
    }

    public int getCurrent() {
        return currentPage;
    }

    public boolean isEmpty() {
        return listToScroll.isEmpty();
    }

    public String getPreviousPage() {
        return listToScroll.get(currentPage--);
    }

    public boolean isFirst() {
        return currentPage == 0;
    }

    public void resetCurrentIndex() {
        currentPage = 0;
    }

    public void resetCurrentIndexFromLast() {
        currentPage = listToScroll.size() - 1;
    }
}
