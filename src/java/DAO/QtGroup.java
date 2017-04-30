/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ngọc Thiện
 */
public class QtGroup {

    public static ArrayList<ArrayList<String>> groups = null;

    public static ArrayList<ArrayList<String>> getGroups() {
        if (groups == null) {
            initGroups();
        }

        return groups;
    }

    private static void initGroups() {
        groups = new ArrayList<ArrayList<String>>();

        try {
            Scanner inp = new Scanner(new File(Const.Path.DATA_PATH + "groups.txt"));
            ArrayList<String> arr = new ArrayList<String>();
            while (inp.hasNext()) {
                String line = inp.nextLine();
                if (line.length() == 0) {
                    groups.add(arr);
                    arr = new ArrayList<String>();
                    continue;
                }

                arr.add(line);
            }

            groups.add(arr);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(QtGroup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
