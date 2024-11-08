package ru.pol.service;

import java.util.Comparator;
import java.util.List;

public class DepartmentCodesComparator implements Comparator<List<String>> {


    @Override
    public int compare(List<String> o1, List<String> o2) {

        int size1 = o1.size();
        int size2 = o2.size();

        int value1 = o2.get(0).compareTo(o1.get(0));
        if(value1 == 0) {

            if(size1 > 1 && size2 > 1) {
                int value2 = o2.get(1).compareTo(o1.get(1));
                if(value2 == 0) {

                    if(size1 > 2 && size2 > 2) {
                        return o2.get(2).compareTo(o1.get(2));
                    } else {
                        return Integer.compare(size1, size2);
                    }
                }
                return value2;
            } else {
                return Integer.compare(size1, size2);
            }
        }
        return value1;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(!(obj instanceof DepartmentCodesComparator)) {
            return false;
        }
        return true;
    }
}
