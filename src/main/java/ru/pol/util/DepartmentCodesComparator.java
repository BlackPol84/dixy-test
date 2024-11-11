package ru.pol.util;

import java.util.Comparator;
import java.util.List;

public class DepartmentCodesComparator implements Comparator<List<String>> {

    //в рамках задания сравнение String в compareTo выполняется лексиграфически,
    //поэтому Comparator корректно сортирует коды с значениями от 0 до 9
    @Override
    public int compare(List<String> o1, List<String> o2) {

        int listSize = Math.min(o1.size(), o2.size());

        for (int i = 0; i < listSize; i++) {

            int value = o2.get(i).compareTo(o1.get(i));

            if (value != 0) {
                return value;
            }
        }
        return Integer.compare(o1.size(), o2.size());
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        return obj instanceof DepartmentCodesComparator;
    }
}
