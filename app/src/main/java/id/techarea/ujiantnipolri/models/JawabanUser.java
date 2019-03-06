package id.techarea.ujiantnipolri.models;

import java.io.Serializable;

public class JawabanUser implements Serializable {

    int id, questionid,jawabanid, jenissoal, mark, order;

    public JawabanUser(){}

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public JawabanUser(int id, int questionid, int jawabanid, int jenissoal){
        this.id = id;
        this.jenissoal = jenissoal;
        this.questionid = questionid;
        this.jawabanid = jawabanid;
    }

    public int getJenissoal() {
        return jenissoal;
    }

    public void setJenissoal(int jenissoal) {
        this.jenissoal = jenissoal;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionid() {
        return questionid;
    }

    public void setQuestionid(int questionid) {
        this.questionid = questionid;
    }

    public int getJawabanid() {
        return jawabanid;
    }

    public void setJawabanid(int jawabanid) {
        this.jawabanid = jawabanid;
    }
}
