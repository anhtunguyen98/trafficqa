package Model;

import java.io.Serializable;

/**
 * Created by tutyb_000 on 10/11/2016.
 */
public class Test implements Serializable, Comparable<Test> {

    private String question;
    private String base;
    private String answer;
    private float same;

    public Test() {
    }

    public Test(String question, String base, String answer) {
        this.question = question;
        this.base = base;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public float getSame() {
        return same;
    }

    public void setSame(float same) {
        this.same = same;
    }

    @Override
    public int compareTo(Test test) {
        float oSame = test.getSame();
        if (same > oSame) {
            return -1;
        }
        if (same < oSame) {
            return 1;
        }
        return 0;
    }
}
