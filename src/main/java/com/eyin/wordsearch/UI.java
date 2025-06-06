package com.eyin.wordsearch;

import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;

import java.awt.*;
import java.io.*;

// class untuk membuat UI utama
public class UI {
    //deklarasi trie statis dan JTextPane untuk menampilkan dokumen
    private static Trie trie = new Trie();
    private static JTextPane documentArea = new JTextPane(); // Use JTextPane for styled text

    //method untuk membuat dan menampilkan UI
    public static void createAndShowGUI() {
        //setup dasar JFrame untuk tampilan aplikasi
        JFrame frame = new JFrame("Word Search APP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        Style defaultStyle = documentArea.addStyle("default", null);
        Style highlightStyle = documentArea.addStyle("highlight", null);
        StyleConstants.setBackground(highlightStyle, Color.GREEN);

        JTextField wordField = new JTextField();
        JButton searchButton = new JButton("Cari Kata Kunci");
        JButton uploadButton = new JButton("Upload File .docx");
        JLabel resultLabel = new JLabel("Hasil pencarian akan ditampilkan disini");

        //setup document area apakah bisa di edit atau tidak
        documentArea.setEditable(false);
        documentArea.setText(""); // Initialize empty document
        JScrollPane scrollPane = new JScrollPane(documentArea); // Enables scrolling

        //Action Listener untuk tombol upload dan searching
        uploadButton.addActionListener(e -> {
            //JFileChooser untuk memilih file .docx
            JFileChooser fileChooser = new JFileChooser();

            //jika sudah dipilih, buka file dan baca isinya untuk membangun Trie
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                
                    String content = FileReader.readAndBuildTrie(file, trie);
                    documentArea.setText(content);
                    documentArea.setCaretPosition(0); // Scroll to top
                    JOptionPane.showMessageDialog(frame, "File berhasil diunggah dan Trie dibuat!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
                }
            }
        });

        searchButton.addActionListener(e -> {
            String word = wordField.getText().toLowerCase();
            if (trie.search(word)) {
                int count = TextHighlighter.countWordOccurrences(documentArea, word);
                resultLabel.setText("✅ Kata ditemukan dalam dokumen. Jumlah kemunculan: " + count);
                TextHighlighter.highlightWord(documentArea, word); // Use utility class
            } else {
                resultLabel.setText("❌ Kata tidak ditemukan.");
            }
        });

        JPanel panel = new JPanel(new GridLayout(4, 1));
        panel.add(uploadButton);
        panel.add(wordField);
        panel.add(searchButton);
        panel.add(resultLabel);

        frame.add(scrollPane, BorderLayout.CENTER); // Document display area
        frame.add(panel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

}
