/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

/**
 *
 * @author Ngọc Thiện
 */
public class Tag {
    private int numOfSpace;
    private String tag;

    public Tag() {
    }

    public Tag(int numOfSpace, String tag) {
        this.numOfSpace = numOfSpace;
        this.tag = tag;
    }

    public int getNumOfSpace() {
        return numOfSpace;
    }

    public void setNumOfSpace(int numOfSpace) {
        this.numOfSpace = numOfSpace;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
