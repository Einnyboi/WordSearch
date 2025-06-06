package com.eyin.wordsearch;

import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.List;
import java.util.ArrayList;
// Class untuk meng-highlight kata yang dicari dan menghitung jumlah ketika ditemukan
// proses ini dilakukan dengan mencari kata yang muncul dalam JTextPane
// dan mengubah gaya teksnya menjadi highlight

public class TextHighlighter {
    //method untuk meng-highlight kata dalam JTextPane atau documentArea

    //documentArea adalah JTextPane yang berisi teks yang akan di-highlight
    //word adalah kata kunci yang dicari
    public static void highlightWord(JTextPane documentArea, String word) {
    try {
        StyledDocument doc = documentArea.getStyledDocument();
        doc.setCharacterAttributes(0, doc.getLength(), documentArea.getStyle("default"), true); // Clear previous highlights

        String text = doc.getText(0, doc.getLength()); // Retrieves clean raw text without hidden formatting

        String patternStr = "(?i)\\b" + java.util.regex.Pattern.quote(word) + "\\b"; // Ensure word boundary matching
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(patternStr);
        java.util.regex.Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            doc.setCharacterAttributes(matcher.start(), matcher.end() - matcher.start(), documentArea.getStyle("highlight"), false);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    //method untuk menghitung jumlah kemunculan kata dalam dokumen
    public static int countWordOccurrences(JTextPane documentArea, String word) {
        String text = documentArea.getText();
        String patternStr = "(?i)(?<=^|[^\\p{L}\\p{N}])" + java.util.regex.Pattern.quote(word) + "(?=[^\\p{L}\\p{N}]|$)";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(patternStr);
        java.util.regex.Matcher matcher = pattern.matcher(text);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }
    // text adalah dokumen
    // word adalah kata kunci yang dicari
    // method untuk menemukan semua kemunculan kata dalam teks
    public static List<int[]> findAllMatches(String text, String word) {
        
        //arraylist untuk menyimpan posisi awal dan akhir dari setiap kemunculan kata
        List<int[]> matches = new ArrayList<>();
        if (word == null || word.isEmpty()) return matches;
        //regex untuk mencari kata yang sesuai dengan kata kunci berdasarkan pattern
        Pattern pattern = Pattern.compile(
            // \\b adalah word boundary supaya hanya cocok dengan kata yg sebenarnya
            // Pattern.quote untuk memastikan kata kunci diperlakukan sebagai literal
            // contoh: jika kata kunci adalah "test", maka regex akan menjadi "\\btest\\b"
            "\\b" + Pattern.quote(word) + "\\b",
            Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE
        );
        // Matcher untuk mencari semua kemunculan kata dalam teks
        // matcher.find() akan mengembalikan true jika ada kemunculan kata
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            matches.add(new int[] { matcher.start(), matcher.end() });
        }
        return matches;
    }
}//end of class TextHighlighter
