package com.eyin.wordsearch;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class FileReader {
    public static String readAndBuildTrie(File file, Trie trie) throws Exception {
        // Do NOT reset the Trie here; let the caller handle it
        FileInputStream fis = new FileInputStream(file);
        XWPFDocument document = new XWPFDocument(fis);
        StringBuilder content = new StringBuilder();

        for (XWPFParagraph paragraph : document.getParagraphs()) {
            content.append(paragraph.getText()).append("\n\n"); // Preserve structure
            String[] words = paragraph.getText().toLowerCase().split("\\W+");
            for (String word : words) {
                if (!word.isEmpty()) {
                    trie.insert(word);
                }
            }
        }
        document.close();
        return content.toString();
    }
}
