/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Measure.*;

/**
 *
 * @author tutyb_000
 */
public class TestMeasure implements Comparable<TestMeasure> {

    private Test test;
    private Measure measure;
    private Cosin cosin;
    private Dice dice;
    private Euclide euclide;
    private Jaccard jaccard;
    private Levenshtein levenshtein;
    private Mahattan mahattan;
    private Matching matching;
    private NGram ngram;
    private JaroWinkler jaroWinkler;
    private int[] abs2Use;
    private int mea;

    public TestMeasure() {
    }

    public TestMeasure(Test test, int[] abs2Use, int mea) {
        this.test = test;
        this.abs2Use = abs2Use;
        this.mea = mea;
    }
    
    public void process(Measure measure){
        this.measure = measure;
        test.setSame(getMeasure(mea));
    }

    public Test getTest() {
        return test;
    }

    public float getCosinMeasure() {
        return cosin.getPoint();
    }

    public float getDiceMeasure() {
        return dice.getPoint();
    }

    public float getEuclideMeasure() {
        return euclide.getPoint();
    }

    public float getJaccardMeasure() {
        return jaccard.getPoint();
    }

    public float getLevenshteinMeasure() {
        return levenshtein.getPoint();
    }

    public float getMahattanMeasure() {
        return mahattan.getPoint();
    }

    public float getMatchingMeasure() {
        return matching.getPoint();
    }

    public float getNGramMeasure() {
        return ngram.getPoint();
    }

    public float getJaroWinklerMeasure() {
        return jaroWinkler.getPoint();
    }

    public int getMea() {
        return mea;
    }

    public void setMea(int mea) {
        this.mea = mea;
    }

    @Override
    public int compareTo(TestMeasure o) {
        float p1 = getTest().getSame();
        float p2 = o.getTest().getSame();

        if (p1 > p2) {
            return -1;
        }
        if (p1 < p2) {
            return 1;
        }
        return 0;
    }

    private float getMeasure(int mea) {
        switch (mea) {
            case 1:
                jaroWinkler = new JaroWinkler(measure);
                jaroWinkler.process(abs2Use);
                return getJaroWinklerMeasure();
            case 2:
                levenshtein = new Levenshtein(measure);
                levenshtein.process(abs2Use);
                return getLevenshteinMeasure();
            case 3:
                mahattan = new Mahattan(measure);
                mahattan.process(abs2Use);
                return getMahattanMeasure();
            case 4:
                euclide = new Euclide(measure);
                euclide.process(abs2Use);
                return getEuclideMeasure();
            case 5:
                cosin = new Cosin(measure);
                cosin.process(abs2Use);
                return getCosinMeasure();
            case 6:
                ngram = new NGram(measure);
                ngram.process(abs2Use);
                return getNGramMeasure();
            case 7:
                matching = new Matching(measure);
                matching.process(abs2Use);
                return getMatchingMeasure();
            case 8:
                dice = new Dice(measure);
                dice.process(abs2Use);
                return getDiceMeasure();
            case 9:
                jaccard = new Jaccard(measure);
                jaccard.process(abs2Use);
                return getJaccardMeasure();
        }
        
        return 0;
    }
}
