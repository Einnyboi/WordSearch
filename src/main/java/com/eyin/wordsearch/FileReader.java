package com.eyin.wordsearch;

import java.io.File;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

// class untuk membaca file .pdf dan membangun Trie
public class FileReader {
    // method
    public static String readAndBuildTrie(File file, Trie trie) throws Exception {
        String fileName = file.getName().toLowerCase();
        String text = "";

        // cek apakah file yang diunggah adalah .pdf atau .docx
        //jika pdf, gunakan PDFBox untuk membaca
        if (fileName.endsWith(".pdf")) {
            PDDocument document = PDDocument.load(file);
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setAddMoreFormatting(true); //untuk menjaga format teks
            stripper.setSortByPosition(true); //untuk menjaga urutan teks
            text = stripper.getText(document); // membaca teks dari dokumen PDF
            document.close();
        } else if (fileName.endsWith(".docx")) { //jika docx, gunakan Apache POI untuk membaca
            XWPFDocument document = new XWPFDocument(new java.io.FileInputStream(file));
            StringBuilder content = new StringBuilder();
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                content.append(paragraph.getText()).append("\n\n");
            }
            text = content.toString();
            document.close();
        } else {
            throw new IllegalArgumentException("File tidak didukung, mohon coba ulang dan gunakan file .pdf atau .docx");
        }
        // Iterasi setiap kata dalam dokumen
        String[] words = text.toLowerCase().split("[^\\p{L}\\p{N}]+");
        for (String word : words) {
            if (!word.isEmpty()) {
                trie.insert(word);
            }
        }
        return text;
    }
}//end of FileReader class
