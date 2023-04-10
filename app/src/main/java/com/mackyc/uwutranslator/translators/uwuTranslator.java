package com.mackyc.uwutranslator.translators;

public final class uwuTranslator {

    private final boolean hasSuffix;

    public uwuTranslator(boolean hasSuffix) {
        this.hasSuffix = hasSuffix;
    }

    // Default usage pre-refactor
    public uwuTranslator() {
        this(true);
    }

    public String translate(String input) {
        String finalOutput = input
            // Letter cases
            .replaceAll("[lr]", "w")
            .replaceAll("[LR]", "W")
            .replaceAll("th", "ff")

            // -yo cases
            .replaceAll("([mnMN])(o)", "$1y$2")
            .replaceAll("([MN])(O)", "$1Y$2")

            // 'hu' cases
            .replaceAll("([Hh])u", "$1oo")
            .replaceAll("HU", "HOO");

        // Add suffix
        return hasSuffix ? finalOutput + " uwu" : finalOutput;
    }

}
