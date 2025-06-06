package com.eyin.wordsearch;

import java.util.HashMap;
import java.util.Map;

public class Trie {
    // class Trie untuk menyimpan struktur data Trie
    class TrieNode {
        // Menggunakan Hashmap untuk menyimpan children dari node
        Map<Character, TrieNode> children = new HashMap<>();

        // penanda node sudah akhir dari sebuah kata
        boolean isEndOfWord;
    }

    // root node dari trie
    // final karena dia bersifat statis dan tidak berubah
    private final TrieNode root = new TrieNode();

    // method insert Trie
    public void insert(String word) {

        //selalu mulai dari root node
        TrieNode node = root;
        
        //iterasi setiap karakter dalam kata
        for (char ch : word.toCharArray()) {
            // jika karakter tidak ada di children, buat node baru
            node = node.children.computeIfAbsent(ch, c -> new TrieNode());
        }
        //setelah iterasi selesai, tandai node sebagai akhir dari kata
        node.isEndOfWord = true;
    }

    //method search dalam Trie
    public boolean search(String word) {
        //mulai dari root node
        TrieNode node = root;

        //iterasi setiap karakter dalam kata
        for (char ch : word.toCharArray()) {
            // jika karakter tidak ada di children, kata tidak ditemukan
            node = node.children.get(ch);
            if (node == null) return false;
        }

        // jika iterasi selesai, tandai node sebagai akhir dari kata
        return node.isEndOfWord;
    }
}//end of Trie class
