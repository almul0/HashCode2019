package com.company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    public static HashMap<String, Integer> TAGS = new HashMap<>();

    public static void main(String[] args) {
        long N;
        boolean horz;
        int nTags;
        String picDesc;
        String[] tagArray;
        Set<Integer> tagIdArray;
        LinkedList<Slide> slides = new LinkedList<>();
        LinkedList<Slide> verts = new LinkedList<>();
        LinkedList<Slide> resultado = new LinkedList<>();

        int tagId = 0;
        Slide vertical = null;

        //Enter data using BufferReader
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        // Reading data using readLine
        try {
            N = Long.parseLong(reader.readLine());
        for (int i = 0; i < N; i++) {
            picDesc = reader.readLine();
            if ( picDesc.substring(0,1).equals("H") ) {
                horz = true;
            } else {
                horz = false;
            }
            nTags = Integer.parseInt(picDesc.substring(2,3));
            tagArray = picDesc.substring(4).split("\\s");
            tagIdArray = new HashSet<>();
            for (int t=0; t < nTags; t++) {
                if (TAGS.containsKey(tagArray[t]) ) {
                    tagIdArray.add(TAGS.get(tagArray[t]));
                } else {
                    tagIdArray.add(tagId);
                    TAGS.put(tagArray[t], tagId);
                    tagId++;
                }
            }

            if ( horz == true ) {
                slides.add(new Slide(i, horz, tagIdArray));
            } else {
                verts.add(new Slide(i, horz, tagIdArray));
            }
//            } else if (horz == false && vertical == null ) {
//                vertical = new Slide(i, horz, tagIdArray);
//            } else {
//                vertical.addVert(i, tagIdArray);
//                slides.add(vertical);
//                vertical = null;
//            }
        }


        int min = 100;
        int score;
        Slide next = null;

        while(verts.size() != 0) {
            Slide currV = verts.getFirst();
            verts.remove(currV);
            for (Slide s: verts) {
                score = currV.score(s);
                if (score < min) {
                    min = score;
                    next = s;
                }
            }
            min =100;
            currV.addVert(next.getId1(), next.getTags());
            slides.add(currV);
            verts.remove(next);
            next = null;
        }

        Slide current = slides.getFirst();
        resultado.add(current);
        slides.remove(current);
        next = null;
        int max = -1;
            BufferedWriter writer = new BufferedWriter(new FileWriter("resultados.txt"));
        System.out.println("Making slideshow...");
        while ( slides.size() != 0 ) {
            System.out.println("Left: " + slides.size());
            System.out.println("Begin score");

            for (Slide s: slides) {
                score = current.score(s);
                if (score > max) {
                    max = score;
                    next = s;
                }
            }
            System.out.println("End score");
            resultado.add(next);
            writer.write(String.format("%s\n", next));
            writer.flush();
            current = next;
            slides.remove(next);
            next = null;
            max = -1;
        }

//            System.out.println("Writing to file...");
//
//        writer.write(String.format("%s\n", resultado.size()));
//        for (Slide s: resultado) {
//            writer.write(String.format("%s\n", s));
//        }
//        writer.close();



        } catch (Exception e){
            e.printStackTrace();
        }
    }
}


