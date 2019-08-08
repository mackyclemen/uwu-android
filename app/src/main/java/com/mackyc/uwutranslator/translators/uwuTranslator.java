package com.mackyc.uwutranslator.translators;

public final class uwuTranslator {

    public static String translate(String input) {
        return input

                // Letter cases
                .replace('l', 'w')
                .replace('r', 'w')
                .replace('L', 'W')
                .replace('R', 'W')

                // -yo cases
                .replaceAll("no", "nyo")
                .replaceAll("mo", "myo")
                .replaceAll("No", "Nyo")
                .replaceAll("Mo", "Myo")
                .replaceAll("NO", "NYO")
                .replaceAll("MO", "MYO")

                // 'hu' cases
                .replaceAll("hu", "hoo")
                .replaceAll("Hu", "Hoo")
                .replaceAll("HU", "HOO")

                // uwu ending
                .replaceAll("\\.[^\\s]|\\z", " uwu");

    }

}
