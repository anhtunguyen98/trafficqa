/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.JSONPaser;

import Model.Test;
import org.json.JSONObject;

/**
 *
 * @author tutyb_000
 */
public class Test2JSON extends Model.JSONPaser.ConvertToJSON {

    public Test2JSON() {
        super();
    }

    @Override
    public JSONObject convert2JSON(Object object) {
        Test test = (Test) object;
        jsonObject = new JSONObject();
        jsonObject.put("question", test.getQuestion());
        jsonObject.put("base", test.getBase());
        jsonObject.put("answer", test.getAnswer());
        jsonObject.put("same", test.getSame());

        return jsonObject; //To change body of generated methods, choose Tools | Templates.
    }
}
