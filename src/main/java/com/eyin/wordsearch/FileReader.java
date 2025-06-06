package com.eyin.wordsearch;

import java.io.File;
import java.io.FileInputStream;

//menggunakan apache POI yaitu library untuk membaca file Microsoft Office
//dari maven repository
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

//class untuk membaca file .docx dan membangun Trie
public class FileReader {
    //method
    public static String readAndBuildTrie(File file, Trie trie) throws Exception {
        //deklarasi technical variables
        //FileInputStream digunakan untuk membaca file .docx
        //karena hanya fis saja tidak bisa langsung diakses isi dokumen
        //XWPFDocument digunakan untuk mengakses dokumen .docx
        //XWPFParagraph digunakan untuk mengakses setiap paragraf dalam dokumen
        //StringBuilder digunakan untuk menyimpan isi dokumen
        FileInputStream fis = new FileInputStream(file);
        XWPFDocument document = new XWPFDocument(fis);
        StringBuilder content = new StringBuilder();
        
        //Iterasi setiap paragraf dalam dokumen
        for (XWPFParagraph paragraph : document.getParagraphs()) {
            //melakukan append untuk menambahkan teks paragraf ke StringBuilder
            //dan juga membangun Trie dengan memasukkan setiap kata dalam paragraf
            content.append(paragraph.getText()).append("\n\n"); 
            String[] words = paragraph.getText().toLowerCase().split("\\W+"); // \\W+ untuk memisahkan kata berdasarkan non-word characters (spasi, tanda baca, dll)

            //memasukkan setiap kata ke dalam Trie
            for (String word : words) {
                //jika kata tidak kosong atau hanya spasi
                //maka masukkan ke dalam Trie
                if (!word.isEmpty()) {
                    trie.insert(word);
                }
            }
        }

        //menutup dokumen
        document.close();
        fis.close();
        //mengembalikan isi dokumen sebagai String
        return content.toString();
    }
}
