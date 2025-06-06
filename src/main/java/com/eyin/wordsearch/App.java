package com.eyin.wordsearch;

public class App {

    public static void main(String[] args) {
        //program utama
        javax.swing.SwingUtilities.invokeLater(() -> {
            //panggil method UI dengan swingutilities
            //untuk memastikan UI dibuat di thread yang benar
            // ini penting untuk menghindari masalah concurrency
            // dan memastikan UI responsif
            UI.createAndShowGUI();
        });
    }

}
