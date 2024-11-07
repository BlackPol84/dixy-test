package ru.pol.service;

import java.util.*;
import java.util.stream.Collectors;

public class DepartmentSorter {

    public static String[] sortDepartmentCodesDesc(String[] departmentCodes) {

        Set<String> codes = new HashSet<>();

        for(String code : departmentCodes) {

            String[] temp = code.split("\\\\");

            for(int i = 1; i <= temp.length; i++) {

                String str = String.join("\\", Arrays.copyOf(temp, i));
                codes.add(str); //Получаю Set всех значений кодов
            }
        }

        List<List<String>> sortedCodesDesc = sortCodesDesc(codes);
        List<String> sortedList = getOriginalSortedLists(sortedCodesDesc);
        String[] sortedArrayDesc = new String[sortedList.size()];
        sortedList.toArray(sortedArrayDesc);

        return sortedArrayDesc;
    }

    private static List<List<String>> sortCodesDesc(Set<String> codes) {

        //из Set со всеми значениями кодов получаю List, содержащий списки всех кодов одинковой длины,
        //чтобы была возможность отсортировать их по иерархии
        //вышестоящим кодам добавлял нижестоящий с цифрой 200, чтобы длинна у всех была 3 и вышестоящий
        //код сортировался наверх
        List<List<String>> codesList = getModifiedLists(codes);

        Comparator<List<String>> sortingByDepartment = (l1, l2) -> l2.get(0).compareTo(l1.get(0));
        Comparator<List<String>> sortingByServices = (l1, l2) -> l2.get(1).compareTo(l1.get(1));
        Comparator<List<String>> sortingByUnits = (l1, l2) -> l2.get(2).compareTo(l1.get(2));


        return codesList.stream()
                .sorted(sortingByDepartment.thenComparing(sortingByServices).thenComparing(sortingByUnits))
                .collect(Collectors.toList());
    }

    private static List<List<String>> getModifiedLists(Set<String> codes) {
        List<List<String>> codesList = new ArrayList<>();

        for(String code : codes) {
            String[] temp = code.split("\\\\");

            List<String> tempList = new ArrayList<>();

            if(temp.length == 1) {
                tempList.add(temp[0]);
                tempList.add("SK200");
                tempList.add("SSK200");
            } else if(temp.length == 2) {
                tempList.add(temp[0]);
                tempList.add(temp[1]);
                tempList.add("SSK200");
            } else {
                tempList = Arrays.asList(temp);
            }

            codesList.add(tempList);
        }
        return codesList;
    }

    //в методе удалял добавленные раннее коды с цифрой 200, чтобы привести коды к
    //изначальному виду
    private static List<String> getOriginalSortedLists(List<List<String>> sortedCodesDesc) {

        List<String> originalSortedList = new ArrayList<>();

        for (List<String> codes : sortedCodesDesc) {

            StringBuilder sb = new StringBuilder();

            for (int j = 0; j < codes.size(); j++) {
                if (!codes.get(j).contains("200")) {
                    if (j > 0) {
                        sb.append("\\");
                    }
                    sb.append(codes.get(j));
                }
            }
            originalSortedList.add(sb.toString());
        }
        return originalSortedList;
    }
}
