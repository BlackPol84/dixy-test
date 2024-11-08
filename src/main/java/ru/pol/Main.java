package ru.pol;

import ru.pol.service.DepartmentSorter;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        String[] departmentCodes =
                {"K1\\SK2", "K1\\SK1", "K1\\SK1\\SSK1", "K1\\SK1\\SSK2", "K2", "K2\\SK1\\SSK1", "K2\\SK1\\SSK2"};

        String[] sortedArrayDesc = DepartmentSorter.sortDepartmentCodesDesc(departmentCodes);

        Arrays.stream(sortedArrayDesc).forEach(System.out::println);
    }
}