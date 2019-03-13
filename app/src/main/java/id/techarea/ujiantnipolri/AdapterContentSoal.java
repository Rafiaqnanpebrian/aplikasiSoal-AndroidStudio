package id.techarea.ujiantnipolri;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import id.techarea.ujiantnipolri.models.JawabanUser;
import id.techarea.ujiantnipolri.models.Jawaban;
import id.techarea.ujiantnipolri.models.Soal;


public class AdapterContentSoal extends PagerAdapter {

    private LayoutInflater layoutInflater;
    private Context context;
    private List<Soal> mDataList;
    private ArrayList<Integer> user_jwb = new ArrayList<>();
    private String pilihan;
    private TextView nmr;
    private WebView soal;

    Map<Integer, JawabanUser> listJwbUser = new HashMap<>();
    int id_exam;

    public AdapterContentSoal(List<Soal> dataList, Context context, int id_exam) {
        mDataList = dataList;
        this.context = context;
        this.id_exam = id_exam;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    public Map<Integer, JawabanUser> getListJwbUser() {
        return listJwbUser;
    }

    public void setListJwbUser(Map<Integer, JawabanUser> listJwbUser) {
        this.listJwbUser = listJwbUser;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater) container.getContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.content_soal, container, false);

        final RadioButton a,b,c,d,e;
        final RadioGroup jwb;

        nmr = (TextView) v.findViewById(R.id.number);
        soal = (WebView) v.findViewById(R.id.question);
        jwb = (RadioGroup) v.findViewById(R.id.jawab_grup);
        a = (RadioButton) v.findViewById(R.id.jawab_a);
        b = (RadioButton) v.findViewById(R.id.jawab_b);
        c = (RadioButton) v.findViewById(R.id.jawab_c);
        d = (RadioButton) v.findViewById(R.id.jawab_d);
        e = (RadioButton) v.findViewById(R.id.jawab_e);

        ViewPager mViewPager = (ViewPager) ((Activity) context).findViewById(R.id.view_pager);

        final List<Jawaban> jawabanList = mDataList.get(position).getListJawaban();

        //Log.i("soal", mDataList.get(position).getQuestion());
        nmr.setText(String.valueOf(position+1));
        String htmlFormat = "<body style='margin:0px'>" +
                "<p style='text-align:justify;color: #0099cc;    font-size: 11pt; margin:0px;'>"+
                mDataList.get(position).getQuestion()+"</p></body>";

        if (jawabanList.size()<=4){
            e.setVisibility(View.GONE);
        }

        soal.getSettings();
        soal.setBackgroundColor(Color.TRANSPARENT);
        soal.loadData(htmlFormat,"text/html","utf-8");
        a.setText(jawabanList.get(0).getAnswer());
        b.setText(jawabanList.get(1).getAnswer());
        c.setText(jawabanList.get(2).getAnswer());
        d.setText(jawabanList.get(3).getAnswer());

        if (jawabanList.size()>4){
            e.setText(jawabanList.get(4).getAnswer());
        }



        //Selanjutnya Besok ------ BELUM SELESAI -----

//        if ((AnswerRecordClass.listJawabanReading != null || AnswerRecordClass.listJawabanReading.size()>0) && id_exam ==1){
//            listJwbUser = AnswerRecordClass.listJawabanReading;
//            if(listJwbUser.get(position) != null){
//                setPilihanJawaban(listJwbUser.get(position).getOrder(), jwb, a, b, c, d);
//            }else{
//                setPilihanJawaban(0,jwb, a, b, c, d);
//            }
//        }

        jwb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                a.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listJwbUser.put(position, setJwbUser(position, 0, jawabanList, mDataList));
                        Log.i("jwbuser", listJwbUser.size()+" order:"+position+" "+listJwbUser.get(position).getOrder());
                    }
                });
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        listJwbUser.put(position, setJwbUser(position, 1, jawabanList, mDataList));
                        Log.i("jwbuser", listJwbUser.size()+" order:"+position+" "+listJwbUser.get(position).getOrder());
                    }
                });
                c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        listJwbUser.put(position, setJwbUser(position, 2, jawabanList, mDataList));
                        Log.i("jwbuser", listJwbUser.size()+" order:"+position+" "+listJwbUser.get(position).getOrder());
                    }
                });
                d.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        listJwbUser.put(position, setJwbUser(position, 3, jawabanList, mDataList));
                        Log.i("jwbuser", listJwbUser.size()+" order:"+position+" "+listJwbUser.get(position).getOrder());
                    }
                });
                e.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        listJwbUser.put(position, setJwbUser(position, 4, jawabanList, mDataList));
                        Log.i("jwbuser", listJwbUser.size()+" order:"+position+" "+listJwbUser.get(position).getOrder());
                    }
                });

            }
        });


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(listJwbUser.get(position) != null){
                    setPilihanJawaban(listJwbUser.get(position).getOrder(), jwb, a, b, c, d, e);
                }else{
                    setPilihanJawaban(0,jwb, a, b, c, d, e);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        container.addView(v);
        return v;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    public JawabanUser setJwbUser(int pos, int order, List<Jawaban> listJawaban, List<Soal> soalUjianList) {
        JawabanUser jwbusr = new JawabanUser();
        jwbusr.setId(pos + 1);
        jwbusr.setJawabanid(listJawaban.get(order).getId());
        jwbusr.setQuestionid(soalUjianList.get(pos).getId());
        jwbusr.setMark(listJawaban.get(order).getKey());
        jwbusr.setOrder(listJawaban.get(order).getOrder());
        return jwbusr;
    }

    public void setPilihanJawaban(int order, RadioGroup radioGroup, RadioButton a, RadioButton b, RadioButton c, RadioButton d, RadioButton e) {
        int i = order;
        if (i == 0) {
            a.setChecked(true);
        } else if (i == 1) {
            b.setChecked(true);
        } else if (i == 2) {
            c.setChecked(true);
        } else if (i == 3) {
            d.setChecked(true);
        } else if (i == 4) {
            e.setChecked(true);
        } else {
            radioGroup.clearCheck();
        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
        ((ViewPager)container).removeView((View)object);
    }
}
