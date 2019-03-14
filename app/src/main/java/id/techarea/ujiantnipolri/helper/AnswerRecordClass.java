package id.techarea.ujiantnipolri.helper;

import java.util.HashMap;
import java.util.Map;

import id.techarea.ujiantnipolri.models.JawabanUser;

/**
 * Created by macpro on 23/01/18.
 */

public class AnswerRecordClass {

    public static Map<Integer, JawabanUser> listJawabanBahasaInggris = new HashMap<>();

    public static Map<Integer, JawabanUser> listJawabanPengetahuanUmum = new HashMap<>();

    public static Map<Integer, JawabanUser> listJawabanBahasaIndonesia = new HashMap<>();

    public AnswerRecordClass() {
    }

    public static void resetListJawaban(){
        listJawabanBahasaInggris = new HashMap<>();
        listJawabanPengetahuanUmum = new HashMap<>();
        listJawabanBahasaIndonesia = new HashMap<>();
    }

}
