package com.eyin.wordsearch;

//library untuk fungsi UI
import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import java.util.List;
import java.util.ArrayList;

import java.awt.*;
import java.io.*;

// class untuk membuat UI utama
public class UI {
    // deklarasi trie statis dan JTextPane untuk menampilkan dokumen
    private static Trie trie = new Trie();
    private static JTextPane documentArea = new JTextPane(); // Use JTextPane for styled text

    // method untuk membuat dan menampilkan UI
    public static void createAndShowGUI() {
        // setup dasar JFrame untuk tampilan aplikasi
        JFrame frame = new JFrame("Word Search APP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        Style defaultStyle = documentArea.addStyle("default", null);
        Style highlightStyle = documentArea.addStyle("highlight", null);
        StyleConstants.setBackground(highlightStyle, Color.GREEN);

        JTextField wordField = new JTextField();
        JButton searchButton = new JButton("Cari Kata Kunci");
        JButton uploadButton = new JButton("Upload File .docx atau .pdf");
        JButton nextButton = new JButton(">");
        JButton prevButton = new JButton("<");
        JLabel resultLabel = new JLabel("Hasil pencarian akan ditampilkan disini", SwingConstants.CENTER);

        // List untuk menyimpan indeks kemunculan kata
        List<int[]> matchIndexes = new ArrayList<>();
        int[] currentMatch = { 0 }; // Use array for mutability in lambda

        // setup document area apakah bisa di edit atau tidak
        // JscrollPane memastikan bisa discroll
        documentArea.setEditable(false);
        documentArea.setText(""); 
        JScrollPane scrollPane = new JScrollPane(documentArea);

        // Action Listener untuk tombol upload dan searching
        uploadButton.addActionListener(e -> {
            // JFileChooser untuk memilih file yang ingin diunggah
            JFileChooser fileChooser = new JFileChooser();

            // jika sudah dipilih, buka file dan baca isinya untuk membangun Trie
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    //panggil method dari class FileReader untuk membaca file dan membangun Trie
                    String content = FileReader.readAndBuildTrie(file, trie);
                    documentArea.setText(content);
                    documentArea.setCaretPosition(0); // posisi tampilan ke awal dokumen
                    // menampilkan pesan sukses
                    JOptionPane.showMessageDialog(frame, "File berhasil diunggah dan Trie dibuat!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
                }
            }
        });
        // Set font dan ukuran untuk tombol
        prevButton.setFont(new Font("Arial", Font.BOLD, 14));
        nextButton.setFont(new Font("Arial", Font.BOLD, 14));
        prevButton.setText("<");
        nextButton.setText(">");
        prevButton.setPreferredSize(new Dimension(50, 30));
        nextButton.setPreferredSize(new Dimension(50, 30));

        // Action Listener untuk tombol pencarian
        searchButton.addActionListener(e -> {
            String word = wordField.getText().toLowerCase();
            matchIndexes.clear();
            currentMatch[0] = 0;
            if (trie.search(word)) {
                // Cari semua kemunculan kata menggunakan helper TextHighlighter
                String text = documentArea.getText();
                List<int[]> matches = TextHighlighter.findAllMatches(text, word);
                matchIndexes.addAll(matches);
                // Update label dengan jumlah kemunculan
                int count = matchIndexes.size();
                resultLabel.setText("✅ Jumlah kemunculan: " + count);
                TextHighlighter.highlightWord(documentArea, word);

                // Jika ada kemunculan, set posisi caret ke kemunculan pertama
                if (!matchIndexes.isEmpty()) {
                    int[] pos = matchIndexes.get(0);
                    documentArea.setCaretPosition(pos[0]);
                    documentArea.select(pos[0], pos[1]);
                }
            } else {
                // Jika kata tidak ditemukan, tampilkan pesan
                resultLabel.setText("❌ Kata tidak ditemukan.");
            }
        });

        // action listener untuk tombol yang digunakan untuk navigasi kata kunci
        // nextButton untuk navigasi ke kemunculan berikutnya
        nextButton.addActionListener(e -> {
            // Jika ada kemunculan yang ditemukan
            // dan indeks saat ini tidak melebihi jumlah kemunculan
            if (!matchIndexes.isEmpty()) {
                // maka pindahkan indeks ke kemunculan berikutnya
                // contoh jika indeks saat ini adalah 0, maka pindah ke 1
                currentMatch[0] = (currentMatch[0] + 1) % matchIndexes.size();
                int[] pos = matchIndexes.get(currentMatch[0]);
                documentArea.setCaretPosition(pos[0]);
                documentArea.select(pos[0], pos[1]);
            }
        });

        // prevButton untuk navigasi ke kemunculan sebelumnya
        // jika indeks saat ini adalah 0, maka pindah ke kemunculan terakhir
        prevButton.addActionListener(e -> {
            if (!matchIndexes.isEmpty()) {
                currentMatch[0] = (currentMatch[0] - 1 + matchIndexes.size()) % matchIndexes.size();
                int[] pos = matchIndexes.get(currentMatch[0]);
                documentArea.setCaretPosition(pos[0]);
                documentArea.select(pos[0], pos[1]);
            }
        });

        // Panel untuk tombol navigasi
        // menggunakan FlowLayout untuk mengatur tombol di sebelah kanan
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0)); // Right-align, small gap
        nextButton.setPreferredSize(new Dimension(40, 25));
        prevButton.setPreferredSize(new Dimension(40, 25));
        navPanel.add(prevButton);
        navPanel.add(nextButton);

        // Panel untuk pencarian kata kunci menggunakan GridBagLayout
        JPanel searchPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // menyusun jarak

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        searchPanel.add(wordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        searchPanel.add(searchButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        searchPanel.add(prevButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        searchPanel.add(nextButton, gbc);

        wordField.setPreferredSize(new Dimension(350, 25));
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        searchPanel.add(prevButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        searchPanel.add(nextButton, gbc);

        // Panel utama untuk menampung tombol upload, panel pencarian, dan label hasil
        // menggunakan GridLayout untuk menyusun komponen secara vertikal
        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(uploadButton);
        panel.add(searchPanel);
        // Panel untuk menampilkan hasil pencarian
        JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        resultPanel.add(resultLabel);
        panel.add(resultPanel);

        // Menambahkan komponen ke frame
        frame.add(scrollPane, BorderLayout.CENTER); // Document display area
        frame.add(panel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

}//end of UI class
