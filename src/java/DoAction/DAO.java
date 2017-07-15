/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DoAction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Ngọc Thiện
 */
public class DAO {

    private Connection conn;

    public DAO(String domain, String username, String password, String dbName) {
        String dbUrl = "jdbc:mysql://" + domain + ":3306/" + dbName + "?useUnicode=true&characterEncoding=utf-8";
        String dbClass = "com.mysql.jdbc.Driver";
        try {
            Class.forName(dbClass);
            conn = DriverManager.getConnection(dbUrl,
                    username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getAnswers(HashMap<String, String> pars) throws SQLException {
        String r = "";

        String sql = "SELECT * FROM tbl_sentence WHERE ";

        int index = 0;

        for (String key : pars.keySet()) {
            if (index++ != 0) {
                sql += "AND ";
            }

            if (key.equals("qt")) {
                sql += addQt(pars.get(key));
                continue;
            }

            sql += key + "='" + pars.get(key).trim().replaceAll("\\s+", " ") + "' ";
        }

        System.out.println(sql);
        ResultSet res = conn.createStatement().executeQuery(sql);

        if (res.next()) {
            r = res.getString("answer");
        }

        return r;
    }

    private String addQt(String qt) {
        ArrayList<ArrayList<String>> groups = QtGroup.getGroups();
        String res = "(";
        int index = -1;

        for (int i = 0; i < groups.size(); i++) {
            ArrayList<String> group = groups.get(i);
            if (group.contains(qt)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            return "qt='" + qt + "' ";
        }
//        for (int i = 0; i < groups.length; i++) {
        ArrayList<String> group = groups.get(index);
        index = 0;

        for (String q : group) {
            if (index++ != 0) {
                res += " OR ";
            }

            res += "qt = '" + q + "'";
        }
//        }

        return res + ") ";
    }
}
