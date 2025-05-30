package ed.lab.ed1final.trie;

import org.springframework.stereotype.Component;

@Component
public class Trie {
    private static class Node {
        Node[] children = new Node[26];
        int prefixCount = 0;
        int wordCount = 0;
    }

    private final Node root;

    public Trie() {
        root = new Node();
    }

    public void insert(String word) {
        if (word == null || word.isEmpty()) return;
        Node node = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (idx < 0 || idx >= 26) {
                // Caracter inválido: no insertar esta palabra
                return;
            }
            if (node.children[idx] == null) {
                node.children[idx] = new Node();
            }
            node = node.children[idx];
            node.prefixCount++;
        }
        node.wordCount++;
    }

    public int countWordsEqualTo(String word) {
        if (word == null || word.isEmpty()) return 0;
        Node node = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (idx < 0 || idx >= 26 || node.children[idx] == null) {
                return 0;
            }
            node = node.children[idx];
        }
        return node.wordCount;
    }

    public int countWordsStartingWith(String prefix) {
        if (prefix == null || prefix.isEmpty()) return 0;
        Node node = root;
        for (char c : prefix.toCharArray()) {
            int idx = c - 'a';
            if (idx < 0 || idx >= 26 || node.children[idx] == null) {
                return 0;
            }
            node = node.children[idx];
        }
        return node.prefixCount;
    }

    public void erase(String word) {
        if (word == null || word.isEmpty()) return;
        if (countWordsEqualTo(word) == 0) return;
        eraseHelper(root, word, 0);
    }

    private boolean eraseHelper(Node node, String word, int depth) {
        if (depth == word.length()) {
            node.wordCount--;
            return true;
        }

        int idx = word.charAt(depth) - 'a';
        if (idx < 0 || idx >= 26) {
            return false;
        }
        Node child = node.children[idx];
        if (child == null) return false;

        boolean shouldDeleteChild = eraseHelper(child, word, depth + 1);

        if (shouldDeleteChild) {
            child.prefixCount--;
            if (child.prefixCount == 0 && child.wordCount == 0) {
                node.children[idx] = null;
            }
        }

        return true;
    }
}