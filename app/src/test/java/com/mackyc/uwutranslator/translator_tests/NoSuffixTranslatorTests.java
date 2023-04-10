package com.mackyc.uwutranslator.translator_tests;

import static org.junit.Assert.assertEquals;

import com.mackyc.uwutranslator.translators.uwuTranslator;

import org.junit.Before;
import org.junit.Test;

public class NoSuffixTranslatorTests {
    private uwuTranslator translator;

    @Before
    public void setup() {
        translator = new uwuTranslator(false);
    }

    @Test
    public void testBaseTranslate() {
        String input = "Hello, world!";
        String expected = "Hewwo, wowwd!";
        String actual = translator.translate(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testLetterCases() {
        String input = "lRLLllLlLRrrrRRrrRrr";
        String expected = "wWWWwwWwWWwwwWWwwWww";
        String actual = translator.translate(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testYoCases() {
        String input = "monononoMoMOmoMOmoNoNOnoNOno";
        String expected = "myonyonyonyoMyoMYOmyoMYOmyoNyoNYOnyoNYOnyo";
        String actual = translator.translate(input);
        assertEquals(expected, actual);
    }

    @Test
    public void testHumanCases() {
        String input = "Hey human! Human! HUMAN!!! How are you doing?";
        String output = "Hey hooman! Hooman! HOOMAN!!! How awe you doing?";
        String actual = translator.translate(input);
        assertEquals(output, actual);
    }
}
