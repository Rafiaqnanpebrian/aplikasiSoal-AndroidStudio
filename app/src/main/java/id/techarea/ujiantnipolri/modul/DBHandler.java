package id.techarea.ujiantnipolri.modul;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import id.techarea.ujiantnipolri.BuildConfig;
import id.techarea.ujiantnipolri.models.Jawaban;
import id.techarea.ujiantnipolri.models.Soal;

public class DBHandler extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "tni_polri.db";
    public final static String DATABASE_PATH = "/data/data/"+ BuildConfig.APPLICATION_ID+"/databases/";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase dataBase;
    private final Context dbContext;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.dbContext = context;

        if (checkDatabase()) {
            openDatabase();
        } else
        {
            try {
                this.getReadableDatabase();
                copyDatabase();
                this.close();
                openDatabase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
            Toast.makeText(context,"Initial database is created",Toast.LENGTH_LONG).show();
        }
    }

    private void copyDatabase() throws IOException {
        InputStream myInput = dbContext.getAssets().open(DATABASE_NAME);
        String outFileName = DATABASE_PATH + DATABASE_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;

        while ((length = myInput.read(buffer))>0) {
            myOutput.write(buffer,0,length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    private void openDatabase() {
        String dbPath = DATABASE_PATH + DATABASE_NAME;
        dataBase = SQLiteDatabase.openDatabase(dbPath,null,SQLiteDatabase.OPEN_READWRITE);
    }

    private boolean checkDatabase() {
        SQLiteDatabase checkDB = null;
        boolean exist = false;
        try {
            String dbPath = DATABASE_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(dbPath,null,
                    SQLiteDatabase.OPEN_READONLY);
            } catch (SQLException e) {
                Log.v("db log", "database doesn't exist");
            }

            if (checkDB != null) {
                exist = true;
                checkDB.close();
            }
            return exist;
        }

    public void open(){
        dataBase = this.getWritableDatabase();
        dataBase = this.getReadableDatabase();
    }

    public void read(){
        dataBase = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i,int i1) {

    }
    public List<Soal> getDataSoal(int id_exam){
        List<Soal> listDataSoal = new ArrayList<Soal>();
//        Cursor c = getReadableDatabase().rawQuery("select * from questions where id_exam = "+id_exam+" order by `id` asc",null);
        Cursor c = getReadableDatabase().rawQuery("select * from pertanyaan where id_exam = "+id_exam+" order by `id` asc",null);//order by RANDOM()",null);

        if (c.moveToFirst()) {
            do {
                Soal soal = new Soal();
                soal.setId(c.getInt(c.getColumnIndex("id")));
                soal.setQuestion(c.getString(c.getColumnIndex("question")));
                soal.setId_exam(c.getInt(c.getColumnIndex("id_exam")));
                soal.setListJawaban(getDataJawaban("jawaban", soal.getId()));
                listDataSoal.add(soal);
            } while (c.moveToNext());
        }
        return listDataSoal;
    }

    public List<Jawaban> getDataJawaban(String table, int id_soal){
        List<Jawaban> listJawaban = new ArrayList<Jawaban>();

        Cursor c = getReadableDatabase().rawQuery("select * from "+table+" where questionid = "+id_soal+" order by `order` asc",null);

        if (c.moveToFirst()) {
            do {
                Jawaban jawaban = new Jawaban();
                jawaban.setId(c.getInt(c.getColumnIndex("id")));
                jawaban.setQuestionid(c.getInt(c.getColumnIndex("questionid")));
                jawaban.setOrder(c.getInt(c.getColumnIndex("order")));
                jawaban.setAnswer(c.getString(c.getColumnIndex("answer")));
                jawaban.setKey(c.getInt(c.getColumnIndex("key")));
                listJawaban.add(jawaban);
            } while (c.moveToNext());
        }

        return listJawaban;
    }

    public List<Soal> getDataSoalBahasaInggris(int id_exam) {
        List<Soal> listDataSoal = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("select * from pertanyaan where id_exam = "+id_exam+" order by `id` asc",null);
        if (c.moveToFirst()) {
            do {
                Soal soal = new Soal();
                soal.setId(c.getInt(c.getColumnIndex("id")));
                soal.setQuestion(c.getString(c.getColumnIndex("question")));
                soal.setId_exam(c.getInt(c.getColumnIndex("id_exam")));
                soal.setListJawaban(getDataJawaban("jawaban", soal.getId()));
                listDataSoal.add(soal);
            } while (c.moveToNext());
        }
        return listDataSoal;
    }
    public List<Soal> getDataSoalBahasaIndonesia(int id_exam) {
        List<Soal> listDataSoal = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("select * from pertanyaan where id_exam = "+id_exam+" order by `id` asc",null);
        if (c.moveToFirst()) {
            do {
                Soal soal = new Soal();
                soal.setId(c.getInt(c.getColumnIndex("id")));
                soal.setQuestion(c.getString(c.getColumnIndex("question")));
                soal.setId_exam(c.getInt(c.getColumnIndex("id_exam")));
                soal.setListJawaban(getDataJawaban("jawaban", soal.getId()));
                listDataSoal.add(soal);
            } while (c.moveToNext());
        }
        return listDataSoal;
    }
    public List<Soal> getDataSoalPengetahuanUmum(int id_exam) {
        List<Soal> listDataSoal = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery("select * from pertanyaan where id_exam = "+id_exam+" order by `id` asc",null);
        if (c.moveToFirst()) {
            do {
                Soal soal = new Soal();
                soal.setId(c.getInt(c.getColumnIndex("id")));
                soal.setQuestion(c.getString(c.getColumnIndex("question")));
                soal.setId_exam(c.getInt(c.getColumnIndex("id_exam")));
                soal.setListJawaban(getDataJawaban("jawaban", soal.getId()));
                listDataSoal.add(soal);
            } while (c.moveToNext());
        }
        return listDataSoal;
    }
}
