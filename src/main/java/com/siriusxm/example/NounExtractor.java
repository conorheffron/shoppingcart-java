package com.siriusxm.example;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

import java.io.FileInputStream;
import java.io.IOException;

public class NounExtractor {


    public static void main(String[] args) {
        try {
            // Load the POS model
            POSModel model = new POSModel(new FileInputStream("path/to/en-pos-maxent.bin"));
            POSTaggerME tagger = new POSTaggerME(model);

            // Example text
            String text = "The quick brown fox jumps over the lazy dog.";

            // Tokenize the text
            String[] tokens = text.split("\\s+");

            // Tag the tokens
            String[] tags = tagger.tag(tokens);

            // Extract nouns
            for (int i = 0; i < tokens.length; i++) {
                if (tags[i].startsWith("NN")) { // Nouns are typically tagged as NN, NNS, NNP, NNPS
                    System.out.println(tokens[i]);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}