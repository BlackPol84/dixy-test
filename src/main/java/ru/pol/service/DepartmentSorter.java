package ru.pol.service;

import ru.pol.util.DepartmentCodesComparator;

import java.util.*;

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

        return sortCodesDesc(codes);
    }

    //выполняю сортировку преобразованного array в List<List<String>> и возврящаю
    //отсортированный array
    private static String[] sortCodesDesc(Set<String> codes) {

        List<List<String>> codesList = getCodeList(codes);
        codesList.sort(new DepartmentCodesComparator());

        return getOriginalSortedArray(codesList);
    }

    //Преобразую Set кодов в List<List<String>> для последующей сортировки
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

    //Преобразую отсортированный List<List<String>> обратно в Array
    private static String[] getOriginalSortedArray(List<List<String>> sortedCodesList) {

        String[] originalSortedArray = new String[sortedCodesList.size()];

        for (int i = 0; i < sortedCodesList.size(); i++) {

            List<String> codes = sortedCodesList.get(i);
            StringBuilder sb = new StringBuilder();

            for (int j = 0; j < codes.size(); j++) {
                    if (j > 0) {
                        sb.append("\\");
                    }
                    sb.append(codes.get(j));
            }
            originalSortedArray[i] = sb.toString();
        }
        return originalSortedArray;
    }
}
