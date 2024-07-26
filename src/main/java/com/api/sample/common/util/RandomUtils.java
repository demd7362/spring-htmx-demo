package com.api.sample.common.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;

@UtilityClass
public class RandomUtils {
    private static final char[] CHARS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] LAST_NAMES = {"김", "이", "박", "최", "정", "강", "조", "윤", "장", "임", "엄", "하", "노", "제갈"};
    private static final String[] FIRST_NAMES =
            {
                    "지훈",
                    "팔자",
                    "명수",
                    "현지",
                    "혜지",
                    "윤주",
                    "예나",
                    "철수",
                    "병건",
                    "명건",
                    "오수",
                    "병지",
                    "병민",
                    "미나",
                    "병근",
                    "제권",
                    "현묵",
                    "태근",
                    "태식",
                    "태현",
                    "대길",
                    "도현",
                    "재욱",
                    "다현",
                    "방자",
                    "길현",
                    "보길",
                    "종호",
                    "수호",
            };
    private static final int[] LAST_DAYS = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};


    public String generatePhoneNumber() {
        StringBuilder builder = new StringBuilder("010-");
        for (int i = 0; i < 4; i++) {
            int randomIndex = (int) Math.floor(Math.random() * 10);
            builder.append(randomIndex);
        }
        builder.append("-");
        for (int i = 0; i < 4; i++) {
            int randomIndex = (int) Math.floor(Math.random() * 10);
            builder.append(randomIndex);
        }
        return builder.toString();
    }

    public String generateKoreanName() {
        int lastNameIndex = (int) Math.floor(Math.random() * LAST_NAMES.length);
        int firstNameIndex = (int) Math.floor(Math.random() * FIRST_NAMES.length);
        return LAST_NAMES[lastNameIndex] + FIRST_NAMES[firstNameIndex];
    }

    public String generateRegistrationNumber() {
        String year = String.format("%02d", (int) Math.floor(Math.random() * 100));
        String month = String.format("%02d", Math.max(1,(int) Math.floor(Math.random() * 13)));
        String day = String.format("%02d", Math.max(1, (int) Math.floor(Math.random() * LAST_DAYS[Integer.parseInt(month) - 1])) + 1);
        StringBuilder builder = new StringBuilder();

        builder.append(year)
                .append(month)
                .append(day)
                .append("-");
        int[] arr = isBornIn21Century(Integer.parseInt(year)) ? new int[]{3, 4} : new int[]{1, 2};
        int prefix = arr[(int) Math.floor(Math.random() * 2)];
        builder.append(prefix);
        String rest = String.format("%06d", (int) Math.floor(Math.random() * 1000000));
        builder.append(rest);
        return builder.toString();
    }

    private boolean isBornIn21Century(int year) {
        String currentYear = String.valueOf(LocalDate.now().getYear()).substring(2, 4);
        return Integer.parseInt(currentYear) > year;
    }


}
