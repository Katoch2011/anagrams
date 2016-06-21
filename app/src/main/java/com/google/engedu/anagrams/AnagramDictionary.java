package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;


public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private static int i=0;
    HashSet<String> wordSet=new HashSet<String>();
    ArrayList<String> wordList= new ArrayList<String>();
    HashMap<String, ArrayList<String>> lettersToWord= new HashMap<String, ArrayList<String>>();
    HashMap<Integer,ArrayList<String>> sizeToWords=new HashMap<Integer, ArrayList<String>>();

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordSet.add(word.toLowerCase());
            addtoMap(word.toLowerCase());
            addToSizeMap(word.toLowerCase());

        }

    }

    public Integer countSize(String word){
        Integer key=word.length();
        return key;
    }

    public void addToSizeMap(String word){
        Integer key=countSize(word);
        if(sizeToWords.containsKey(key)){
            wordList=sizeToWords.get(key);
            wordList.add(word);
            sizeToWords.put(key,wordList);
        }
        else{
            wordList=new ArrayList<String>();
            wordList.add(word);
            sizeToWords.put(key,wordList);
        }
    }

    public String helper(String word){
        char[] characters=word.toCharArray();
        Arrays.sort(characters);
        String key=new String(characters);
        return key;
    }

    public void addtoMap(String word){
        String key=helper(word);
        if(lettersToWord.containsKey(key)){
            wordList=lettersToWord.get(key);
            wordList.add(word);
            lettersToWord.put(key,wordList);
        }
        else{
            wordList=new ArrayList<String>();
            wordList.add(word);
            lettersToWord.put(key,wordList);
        }
    }

    public boolean isGoodWord(String word, String base) {
        if(wordSet.contains(word)){
            if(!word.contains(base))
                return true;
        }
        return false;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        String temp;
        String key;
        ArrayList<String> result = new ArrayList<String>();
        for(char i=97;i<=122;i++){
            temp=word+i;
            key= helper(temp);
            if(lettersToWord.containsKey(key)){
                result.addAll(lettersToWord.get(key));
            }
        }
        for(int i=0;i<result.size();i++){
            temp=result.get(i);
            if(temp.contains(word)){
                result.remove(i);
            }
        }
            return result;


    }

    public String pickGoodStarterWord() {
        String word;
        ArrayList<String> starter;

        while(true){
            i=i%(MAX_WORD_LENGTH-DEFAULT_WORD_LENGTH);
            starter=sizeToWords.get(DEFAULT_WORD_LENGTH+(i++));
            int rand=new Random().nextInt(starter.size());

            word=starter.get(rand);
            if(getAnagramsWithOneMoreLetter(word).size()>=MIN_NUM_ANAGRAMS)
                break;
        }
        return  word;
    }
}
