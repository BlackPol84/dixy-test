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

        List<String> sortedCodesDesc = sortCodesDesc(codes);
        String[] sortedArrayDesc = new String[sortedCodesDesc.size()];
        sortedCodesDesc.toArray(sortedArrayDesc);

        return sortedArrayDesc;
    }

    private static List<String> sortCodesDesc(Set<String> codes) {

        List<List<String>> codesList = getCodeList(codes);
        codesList.sort(new DepartmentCodesComparator());

        return getOriginalLists(codesList);
    }

    private static List<List<String>> getCodeList(Set<String> codes) {
        List<List<String>> codesList = new ArrayList<>();

        for(String code : codes) {
            String[] temp = code.split("\\\\");

            List<String> tempList;

            tempList = Arrays.asList(temp);

            codesList.add(tempList);
        }
        return codesList;
    }

    private static List<String> getOriginalLists(List<List<String>> sortedCodesList) {

        List<String> originalSortedList = new ArrayList<>();

        for (List<String> codes : sortedCodesList) {

            StringBuilder sb = new StringBuilder();

            for (int j = 0; j < codes.size(); j++) {
                    if (j > 0) {
                        sb.append("\\");
                    }
                    sb.append(codes.get(j));
            }
            originalSortedList.add(sb.toString());
        }
        return originalSortedList;
    }
}
