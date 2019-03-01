package com.company;

import java.util.*;

public class Slide {

    private Set<Integer> tags;
    private boolean horz;
    private long id1;
    private long id2;
    private boolean complete;

    public Slide(long id, boolean horz, Set<Integer> tags) {
        this.id1 = id;
        this.horz = horz;
        complete = this.horz;
        this.tags = tags;
    }

    public Set<Integer> getTags() {
        return tags;
    }

    public int score(Slide p) {
        Integer[] score = new Integer[3];
        Set<Integer> tmp = new HashSet<>(tags);
        tmp.retainAll(p.getTags());
        score[0] = this.tags.size()-tmp.size();
        score[1] = tmp.size();
        score[2] = p.getTags().size()-tmp.size();
        return Collections.min(Arrays.asList(score));
    }

    public int differences(Slide p) {
        Set<Integer> tmp = new HashSet<>(tags);
        tmp.retainAll(p.getTags());
        return tmp.size();
    }

    public boolean isComplete() {
        return complete;
    }

    public long getId1(){
        return id1;
    }


    public void addVert(long id, Set<Integer> tags) {
        this.id2 = id;
        tags.addAll(tags);
        this.complete = true;
    }

    public String toString(){
        if (horz) {
            return id1 + "";
        } else  {
            return id1 + " " + id2;
        }
    }

}