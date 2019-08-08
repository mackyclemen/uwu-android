package com.mackyc.uwutranslator;

public class uwuTranslator {

    public static String translate(String input) {
        return input
                .replace('l', 'w')
                .replace('r', 'w')
                .replace('L', 'W')
                .replace('R', 'W')

                .replaceAll("no", "nyo")
                .replaceAll("mo", "myo")
                .replaceAll("No", "Nyo")
                .replaceAll("Mo", "Myo")
                .replaceAll("NO", "NYO")
                .replaceAll("MO", "MYO")

                .replaceAll("hu", "hoo")
                .replaceAll("Hu", "Hoo")
                .replaceAll("HU", "HOO")

                .replaceAll("\\.[^\\s]|\\z", " uwu");
    }

}
