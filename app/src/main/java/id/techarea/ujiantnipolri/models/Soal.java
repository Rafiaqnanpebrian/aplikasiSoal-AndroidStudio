package id.techarea.ujiantnipolri.models;

import java.util.List;

public class Soal {
    int id, qnumber, id_exam;
    String question;

    List<Jawaban> listJawaban;

    public Soal(){

    }

    public List<Jawaban> getListJawaban() {
        return listJawaban;
    }

    public void setListJawaban(List<Jawaban> listJawaban) {
        this.listJawaban = listJawaban;
    }

    public Soal(int id, int qnumber, int id_exam, String question){
        this.id = id;
        this.qnumber = qnumber;
        this.id_exam = id_exam;
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQnumber() {
        return qnumber;
    }

    public void setQnumber(int qnumber) {
        this.qnumber = qnumber;
    }

    public int getId_exam() {
        return id_exam;
    }

    public void setId_exam(int id_exam) {
        this.id_exam = id_exam;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
