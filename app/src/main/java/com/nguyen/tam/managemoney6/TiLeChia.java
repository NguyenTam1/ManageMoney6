package com.nguyen.tam.managemoney6;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class TiLeChia extends AppCompatActivity {
    String DATABASE_NAMEGOICHIA = "goichia.sqlite";
    SQLiteDatabase databaseGoiChia;

    int necGoiChia, ltssGoiChia, educGoiChia, playGoiChia, giveGoiChia, ffaGoiChia, idGoiChia;

    // private float[] yData = {55f, 10f, 10f, 10f, 5f, 10f};
    PieChart pieChart;
    int[] yData;
    String[] xData;
    int id=1;

    Button btnChangeTiLeChia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ti_le_chia);
        pieChart = findViewById(R.id.bieuDoTiLeChia);
        btnChangeTiLeChia = findViewById(R.id.btnChangeTiLeChia);
        // doc du lieu database goi chia
        databaseGoiChia = Database.initDatabase(this, DATABASE_NAMEGOICHIA);
        final Cursor cursor = databaseGoiChia.rawQuery("SELECT * FROM GOICHIA", null);
        cursor.moveToPosition(0);
        idGoiChia = cursor.getInt(0);
        necGoiChia = cursor.getInt(1);
        ltssGoiChia = cursor.getInt(2);
        educGoiChia = cursor.getInt(3);
        playGoiChia = cursor.getInt(4);
        giveGoiChia = cursor.getInt(5);
        ffaGoiChia = cursor.getInt(6);

        String sNec = String.valueOf(necGoiChia);
        String sLtss = String.valueOf(ltssGoiChia);
        String sEduc = String.valueOf(educGoiChia);
        String sPlay = String.valueOf(playGoiChia);
        String sGive = String.valueOf(giveGoiChia);
        String sFfa = String.valueOf(ffaGoiChia);
////////////////////
        ve();
        /*yData = new int[]{necGoiChia, ltssGoiChia, educGoiChia, playGoiChia, giveGoiChia, ffaGoiChia};
        xData = new String[]{"", "NEC", "LTSS", "EDUC", "PLAY", "GIVE", "FFA"};

        //pieChart.setDescription("Sales by employee (In Thousands $) ");
        pieChart.setRotationEnabled(true);
        //pieChart.setUsePercentValues(true);
        //pieChart.setHoleColor(Color.BLUE);
        //pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Ti le chia");
        pieChart.setCenterTextSize(20);
        //pieChart.setDrawEntryLabels(true);
        //pieChart.setEntryLabelTextSize(20);
        //More options just check out the documentation!

        addDataSet();
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
              *//*  Log.d(TAG, "onValueSelected: Value select from chart.");
                Log.d(TAG, "onValueSelected: " + e.toString());
                Log.d(TAG, "onValueSelected: " + h.toString());*//*

                int pos1 = e.toString().indexOf("(sum): ");
                String sales = e.toString().substring(pos1 + 7);

                for (int i = 0; i < yData.length; i++) {
                    if (yData[i] == Float.parseFloat(sales)) {
                        pos1 = i;
                        break;
                    }
                }
                String employee = xData[pos1 + 1];
                Toast.makeText(TiLeChia.this, "Hu : " + employee + "\n" + "Gia tri : " + sales + "%", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
//*/
        btnChangeTiLeChia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog1 = new Dialog(TiLeChia.this);
                dialog1.setContentView(R.layout.dialog_thay_doi_tilechia);
                // anh xa
                Button btnOK = dialog1.findViewById(R.id.btnOkThayDoi);
                Button btnHuy = dialog1.findViewById(R.id.btnHuyThayDoi);
                final EditText edtNec = dialog1.findViewById(R.id.edtThayDoiNec);
                final EditText edtLtss = dialog1.findViewById(R.id.edtThayDoiLtss);
                final EditText edtEduc = dialog1.findViewById(R.id.edtThayDoiEduc);
                final EditText edtPlay = dialog1.findViewById(R.id.edtThayDoiPlay);
                final EditText edtGive = dialog1.findViewById(R.id.edtThayDoiGIve);
                final EditText edtFFa = dialog1.findViewById(R.id.edtThayDoiFFa);

                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.cancel();
                    }
                });
                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String sNec = edtNec.getText().toString();

                        String sLtss = edtLtss.getText().toString();

                        String sEduc = edtEduc.getText().toString();

                        String sPlay = edtPlay.getText().toString();

                        String sGive = edtGive.getText().toString();

                        String sffa = edtFFa.getText().toString();
                        //
                        if ((sNec == null || sNec.isEmpty()) ||
                                (sLtss == null || sLtss.isEmpty()) || (sEduc == null || sEduc.isEmpty()) ||
                                (sGive == null || sGive.isEmpty()) || (sPlay == null || sPlay.isEmpty()) ||
                                sffa == null || sffa.isEmpty()) {
                            Toast.makeText(TiLeChia.this, R.string.ti_le_chia_khong_hop_le, Toast.LENGTH_LONG).show();
                        } else {
                            final Integer iNec = Integer.valueOf(sNec);
                            final Integer iLtss = Integer.valueOf(sLtss);
                            final Integer iEduc = Integer.valueOf(sEduc);
                            final Integer iPlay = Integer.valueOf(sPlay);
                            final Integer iGive = Integer.valueOf(sGive);
                            final Integer iffa = Integer.valueOf(sffa);

                            final int tongChia = iNec + iLtss + iEduc + iPlay + iGive + iffa;
                            if (tongChia == 100) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("nec", iNec);
                                contentValues.put("ltss", iLtss);
                                contentValues.put("educ", iEduc);
                                contentValues.put("play", iPlay);
                                contentValues.put("give", iGive);
                                contentValues.put("ffa", iffa);
                                SQLiteDatabase database = Database.initDatabase(TiLeChia.this, "goichia.sqlite");

                                database.update("GOICHIA", contentValues, "id = ?", new String[]{id + ""});


                                Intent intent = new Intent(TiLeChia.this,MainActivity.class);
                                startActivity(intent);
                                TiLeChia.this.finish();
                                ve();
                                dialog1.cancel();

                            }else {
                                Toast.makeText(TiLeChia.this, R.string.ti_le_chia_khong_hop_le, Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
                dialog1.show();
            }
        });
    }
private void ve(){
    yData = new int[]{necGoiChia, ltssGoiChia, educGoiChia, playGoiChia, giveGoiChia, ffaGoiChia};
    xData = new String[]{"", "NEC", "LTSS", "EDUC", "PLAY", "GIVE", "FFA"};

    //pieChart.setDescription("Sales by employee (In Thousands $) ");
    pieChart.setRotationEnabled(true);
    //pieChart.setUsePercentValues(true);
    //pieChart.setHoleColor(Color.BLUE);
    //pieChart.setCenterTextColor(Color.BLACK);
    pieChart.setHoleRadius(25f);
    pieChart.setTransparentCircleAlpha(0);
    pieChart.setCenterText("Split rate");
    pieChart.setCenterTextSize(20);
    //pieChart.setDrawEntryLabels(true);
    //pieChart.setEntryLabelTextSize(20);
    //More options just check out the documentation!

    addDataSet();
    pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
        @Override
        public void onValueSelected(Entry e, Highlight h) {
              /*  Log.d(TAG, "onValueSelected: Value select from chart.");
                Log.d(TAG, "onValueSelected: " + e.toString());
                Log.d(TAG, "onValueSelected: " + h.toString());*/

            int pos1 = e.toString().indexOf("(sum): ");
            String sales = e.toString().substring(pos1 + 7);

            for (int i = 0; i < yData.length; i++) {
                if (yData[i] == Float.parseFloat(sales)) {
                    pos1 = i;
                    break;
                }
            }
            String employee = xData[pos1 + 1];
            Toast.makeText(TiLeChia.this, "Hu : " + employee + "\n" + "Gia tri : " + sales + "%", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onNothingSelected() {

        }
    });
//
}
    private void addDataSet() {
        // Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for (int i = 0; i < yData.length; i++) {
            yEntrys.add(new PieEntry(yData[i], i));
        }

        for (int i = 1; i < xData.length; i++) {
            xEntrys.add(xData[i]);
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(25);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.DKGRAY);
        colors.add(Color.GREEN);
        colors.add(Color.LTGRAY);
        colors.add(Color.CYAN);
        colors.add(Color.RED);
        // colors.add(Color.MAGENTA);

        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}
