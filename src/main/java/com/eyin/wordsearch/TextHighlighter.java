package com.eyin.wordsearch;

import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;

public class TextHighlighter {
    public static void highlightWord(JTextPane documentArea, String word) {
        try {
            StyledDocument doc = documentArea.getStyledDocument();
            doc.setCharacterAttributes(0, doc.getLength(), documentArea.getStyle("default"), true); // Clear previous highlights

            String text = documentArea.getText();
            int index = text.toLowerCase().indexOf(word.toLowerCase());

            while (index >= 0) {
                doc.setCharacterAttributes(index, word.length(), documentArea.getStyle("highlight"), false);
                index = text.toLowerCase().indexOf(word.toLowerCase(), index + word.length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
