package id.techarea.ujiantnipolri.helper;

import java.util.HashMap;
import java.util.Map;

import id.techarea.ujiantnipolri.models.JawabanUser;

/**
 * Created by macpro on 23/01/18.
 */

public class AnswerRecordClass {

    public static Map<Integer, JawabanUser> listJawabanSoal = new HashMap<>();
    public AnswerRecordClass() {
    }

    public static void resetListJawaban(){
        listJawabanSoal = new HashMap<>();
    }

}
