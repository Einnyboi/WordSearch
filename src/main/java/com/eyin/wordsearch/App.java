package com.eyin.wordsearch;

import org.apache.poi.xwpf.usermodel.*;
import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class App {
    private static Trie trie = new Trie();
    private static JTextPane documentArea = new JTextPane();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::createAndShowGUI);
    }

    private static void createAndShowGUI() {
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
        // ...existing code...

        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    readDocxAndBuildTrie(file);
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
                highlightWord(word); // Highlight matching words in UI
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

    private static void readDocxAndBuildTrie(File file) throws Exception {
        trie = new Trie(); // Reset Trie
        FileInputStream fis = new FileInputStream(file);
        XWPFDocument document = new XWPFDocument(fis);
        StringBuilder content = new StringBuilder();

        for (XWPFParagraph paragraph : document.getParagraphs()) {
            content.append(paragraph.getText()).append("\n\n"); // Preserve structure
            String[] words = paragraph.getText().toLowerCase().split("\\W+");
            for (String word : words) {
                trie.insert(word);
            }
        }
        documentArea.setText(content.toString()); // Set full formatted text
        documentArea.setCaretPosition(0); // Scroll to top
        document.close();
    }

    private static void highlightWord(String word) {
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
