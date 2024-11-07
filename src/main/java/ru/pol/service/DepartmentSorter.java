package ru.pol.service;

import java.util.*;
import java.util.stream.Collectors;

public class DepartmentSorter {

    public static String[] sortDepartmentCodesDesc(String[] departmentCodes) {

        //Получаю Set всех значений кодов с учетом пропущенных

        Set<String> codes = new HashSet<>();

        for(String code : departmentCodes) {

            String[] temp = code.split("\\\\");

            for(int i = 1; i <= temp.length; i++) {

                String str = String.join("\\", Arrays.copyOf(temp, i));
                codes.add(str);
            }
        }

        List<List<String>> sortedCodesDesc = sortCodesDesc(codes);
        List<String> sortedList = getOriginalSortedLists(sortedCodesDesc);
        String[] sortedArrayDesc = new String[sortedList.size()];
        sortedList.toArray(sortedArrayDesc);

        return sortedArrayDesc;
    }

    private static List<List<String>> sortCodesDesc(Set<String> codes) {

        //Из Set со всеми значениями кодов получаю List, содержащий списки всех кодов, приведенных к
        //одинковой длине,чтобы была возможность сортировать их по иерархии в глубину
        //Вышестоящим кодам, состаящим из только департамента или департамента и службы,
        //добавлял нижестоящий с цифрой 99, чтобы вышестоящий код ранжировался выше

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
                tempList.add("SK99");
                tempList.add("SSK99");
            } else if(temp.length == 2) {
                tempList.add(temp[0]);
                tempList.add(temp[1]);
                tempList.add("SSK99");
            } else {
                tempList = Arrays.asList(temp);
            }

            codesList.add(tempList);
        }
        return codesList;
    }

    //в методе удалял добавленные раннее коды с цифрой 99, чтобы привести коды к
    //изначальному виду

    private static List<String> getOriginalSortedLists(List<List<String>> sortedCodesDesc) {

        List<String> originalSortedList = new ArrayList<>();

        for (List<String> codes : sortedCodesDesc) {

            StringBuilder sb = new StringBuilder();

            for (int j = 0; j < codes.size(); j++) {
                if (!codes.get(j).contains("99")) {
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
