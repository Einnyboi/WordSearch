package com.eyin.wordsearch;

import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;

// Class untuk meng-highlight kata yang dicari dan menghitung jumlah ketika ditemukan
// proses ini dilakukan dengan mencari kata yang muncul dalam JTextPane
// dan mengubah gaya teksnya menjadi highlight

public class TextHighlighter {
    //method untuk meng-highlight kata dalam JTextPane atau documentArea

    //documentArea adalah JTextPane yang berisi teks yang akan di-highlight
    //word adalah kata kunci yang dicari
    public static void highlightWord(JTextPane documentArea, String word) {
        try {
            //deklarasi doc sebagai StyledDocument yang dapat diatur gaya nya
            StyledDocument doc = documentArea.getStyledDocument();
            // membersihkan highlight sebelumnya
            doc.setCharacterAttributes(0, doc.getLength(), documentArea.getStyle("default"), true); // Clear previous highlights
            
            // mencari kata dalam dokumen
            String text = documentArea.getText();
            // index menyimpan posisi awal dari kata yang ditemukan dalam dokumen
            // toLowerCase memastikan dokumen dan kata yang dicari huruf kecil
            // sehingga pencarian tidak case-sensitive
            int index = text.toLowerCase().indexOf(word.toLowerCase());

            // loop untuk menelusuri semua kemunculan kata dalam dokumen
            while (index >= 0) {
                //setCharacterAttributes digunakan untuk mengubah gaya teks
                doc.setCharacterAttributes(index, word.length(), documentArea.getStyle("highlight"), false);
                // mencari kemunculan berikutnya dari kata yang sama
                index = text.toLowerCase().indexOf(word.toLowerCase(), index + word.length());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //method untuk menghitung jumlah kemunculan kata dalam dokumen
    public static int countWordOccurrences(JTextPane documentArea, String word) {
        //dokumen dan kata yang dicari toLowerCase menjadi huruf kecil
        String text = documentArea.getText().toLowerCase();
        String search = word.toLowerCase();

        //deklarasi count dan index dari kemunculan kata yang dicari
        int count = 0;
        int index = text.indexOf(search);

        //loop untuk menelusuri semua kemunculan kata dalam dokumen
        while (index >= 0) {
            //count akan bertambah setiap kali kata ditemukan
            count++;
            //mencari kemunculan berikutnya dari kata yang sama
            index = text.indexOf(search, index + search.length());
        }
        //mengembalikan jumlah kemunculan kata dalam dokumen
        return count;
    }
}//end of class TextHighlighter
