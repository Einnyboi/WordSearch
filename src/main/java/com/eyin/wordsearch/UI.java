package com.eyin.wordsearch;

import org.apache.poi.xwpf.usermodel.*;
import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.*;
import java.io.*;

// class untuk membuat UI utama
public class UI {
    private static Trie trie = new Trie();
    private static JTextPane documentArea = new JTextPane(); // Use JTextPane for styled text

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Word Search with Trie");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        Style defaultStyle = documentArea.addStyle("default", null);
        Style highlightStyle = documentArea.addStyle("highlight", null);
        StyleConstants.setBackground(highlightStyle, Color.YELLOW);

        JTextField wordField = new JTextField();
        JButton searchButton = new JButton("Search Word");
        JButton uploadButton = new JButton("Upload .docx File");
        JLabel resultLabel = new JLabel("Result will appear here");

        // Setup document area
        documentArea.setEditable(false);
        documentArea.setText(""); // Initialize empty document
        JScrollPane scrollPane = new JScrollPane(documentArea); // Enables scrolling

        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    // Use FileReader to read and build Trie, then update documentArea
                    String content = FileReader.readAndBuildTrie(file, trie);
                    documentArea.setText(content);
                    documentArea.setCaretPosition(0); // Scroll to top
                    JOptionPane.showMessageDialog(frame, "File loaded and Trie built!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
                }
            }
        });

        searchButton.addActionListener(e -> {
            String word = wordField.getText().toLowerCase();
            if (trie.search(word)) {
                resultLabel.setText("✅ Word found in document.");
                TextHighlighter.highlightWord(documentArea, word); // Use utility class
            } else {
                resultLabel.setText("❌ Word not found.");
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
