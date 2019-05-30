package com.nguyen.tam.managemoney6;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout llThemTien, llThongKe, llTiLeChia, llGioiThieu, llCaiDat, llMoreApp,
            llHuNec, llHuLtss, llHuEduc, llHuPlay, llHuGive, llHuFaa;
    TextView txtvNec, txtvLtss, txtvEduc, txtvGive, txtvPlay, txtvFfa;
    TextView txtvPhanTramNec, txtvPhanTramLtss, txtvPhanTramEduc, txtvPhanTramPlay,
            txtvPhanTramGive, txtvPhanTramFfa;
    ImageView imgMoreApp;
    EditText edtThemTien, edtUsed, edtLtss;
    Long nec, ltss, educ, play, give, ffa;
    Long nec2, ltss2, educ2, play2, give2, ffa2, id2;
    Long hu1, hu2, hu3, hu4, hu5, hu6;
    int id = 1;

    final String DATABASE_NAME = "six.sqlite";
    SQLiteDatabase database;
    String DATABASE_NAME2 = "goichia.sqlite";
    SQLiteDatabase database2;
    //database cu goi chia
    String DATABASE_NAMEGOICHIA = "goichia.sqlite";
    SQLiteDatabase databaseGoiChia;
    int necGoiChia, ltssGoiChia, educGoiChia, playGoiChia, giveGoiChia, ffaGoiChia, idGoiChia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        hienThiGoiChiaLenTextview();
        llThemTien.setOnClickListener(this);
        llHuNec.setOnClickListener(this);
        llHuLtss.setOnClickListener(this);
        llHuEduc.setOnClickListener(this);
        llHuPlay.setOnClickListener(this);
        llHuGive.setOnClickListener(this);
        llHuFaa.setOnClickListener(this);
        llTiLeChia.setOnClickListener(this);
        llMoreApp.setOnClickListener(this);
        imgMoreApp.setOnClickListener(this);

        hienThiTien();
    }

    private void hienThiGoiChiaLenTextview() {
        readData();
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

        txtvPhanTramNec.setText(sNec + " %");
        txtvPhanTramLtss.setText(sLtss + " %");
        txtvPhanTramEduc.setText(sEduc + " %");
        txtvPhanTramPlay.setText(sPlay + " %");
        txtvPhanTramGive.setText(sGive + " %");
        txtvPhanTramFfa.setText(sFfa + " %");
    }

    // hàm hiển thị số tiền trong database lên textView hủ tương ứng
    private void hienThiTien() {
        readData2();
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM SIXJAR", null);

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            hu1 = cursor.getLong(1);
            hu2 = cursor.getLong(2);
            hu3 = cursor.getLong(3);
            hu4 = cursor.getLong(4);
            hu5 = cursor.getLong(5);
            hu6 = cursor.getLong(6);
        }
        String sNec = String.valueOf(hu1);
        String sLtss = String.valueOf(hu2);
        String sEduc = String.valueOf(hu3);
        String sPlay = String.valueOf(hu4);
        String sGive = String.valueOf(hu5);
        String sFfa = String.valueOf(hu6);

        txtvNec.addTextChangedListener(onTextViewChangedListennerNec());
        txtvLtss.addTextChangedListener(onTextViewChangedListennerLtss());
        txtvGive.addTextChangedListener(onTextViewChangedListennerLtssGive());
        txtvPlay.addTextChangedListener(onTextViewChangedListennerLtssPlay());
        txtvEduc.addTextChangedListener(onTextViewChangedListennerLtssEduc());
        txtvFfa.addTextChangedListener(onTextViewChangedListennerLtssFfa());

        txtvNec.setText(sNec);
        txtvEduc.setText(sEduc);
        txtvPlay.setText(sPlay);
        txtvFfa.setText(sFfa);
        txtvLtss.setText(sLtss);
        txtvGive.setText(sGive);
    }

    // Đọc dữ liệu của database gói chia
    public void readData2() {
        database2 = Database.initDatabase(this, DATABASE_NAME2);
        Cursor cursor = database2.rawQuery("SELECT * FROM GOICHIA", null);
        cursor.moveToPosition(0);
        id2 = cursor.getLong(0);
        nec2 = cursor.getLong(1);
        ltss2 = cursor.getLong(2);
        educ2 = cursor.getLong(3);
        play2 = cursor.getLong(4);
        give2 = cursor.getLong(5);
        ffa2 = cursor.getLong(6);

    }

    // đọc dữ liệu của database six
    public void readData() {
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM SIXJAR", null);

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            hu1 = cursor.getLong(1);
            hu2 = cursor.getLong(2);
            hu3 = cursor.getLong(3);
            hu4 = cursor.getLong(4);
            hu5 = cursor.getLong(5);
            hu6 = cursor.getLong(6);
        }
    }

    private void anhXa() {
        llThemTien = findViewById(R.id.ll_ThemTien);
        llThongKe = findViewById(R.id.ll_ThongKe);
        llGioiThieu = findViewById(R.id.ll_GioiThieu);
        llTiLeChia = findViewById(R.id.ll_TiLeChia);
        llCaiDat = findViewById(R.id.ll_CaiDat);
        llMoreApp = findViewById(R.id.ll_MoreApp);
        llHuNec = findViewById(R.id.ll_HuNec);
        llHuLtss = findViewById(R.id.ll_HuLtss);
        llHuEduc = findViewById(R.id.ll_HuEduc);
        llHuPlay = findViewById(R.id.ll_HuPlay);
        llHuGive = findViewById(R.id.ll_HuGive);
        llHuFaa = findViewById(R.id.ll_HuFfa);
        txtvNec = findViewById(R.id.txtvNec);
        txtvLtss = findViewById(R.id.txtvLtss);
        txtvEduc = findViewById(R.id.txtvEduc);
        txtvGive = findViewById(R.id.txtvGive);
        txtvPlay = findViewById(R.id.txtvPlay);
        txtvFfa = findViewById(R.id.txtvFfa);
        txtvPhanTramNec = findViewById(R.id.txtvPhanTramNec);
        txtvPhanTramLtss = findViewById(R.id.txtvPhanTramLtss);
        txtvPhanTramEduc = findViewById(R.id.txtvPhanTramEduc);
        txtvPhanTramGive = findViewById(R.id.txtvPhanTramGive);
        txtvPhanTramPlay = findViewById(R.id.txtvPhanTramPlay);
        txtvPhanTramFfa = findViewById(R.id.txtvPhanTramFfa);
        imgMoreApp = findViewById(R.id.txtvMoreApp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_ThemTien:
                // TODO SOMETHING
                dialogThemTien();
                break;
            case R.id.ll_HuNec:
                nhapSoTienSuDungNec();
                break;
            case R.id.ll_HuLtss:
                nhapSoTienSuDungLtss();
                break;
            case R.id.ll_HuEduc:
                nhapSoTienSuDungEduc();
                break;
            case R.id.ll_HuPlay:
                nhapSoTienSuDungPlay();
                break;
            case R.id.ll_HuGive:
                nhapSoTienSuDungGive();
                break;
            case R.id.ll_HuFfa:
                nhapSoTienSuDungffa();
                break;
            case R.id.ll_TiLeChia:
                Intent intent = new Intent(MainActivity.this, TiLeChia.class);
                startActivity(intent);
                break;
            case R.id.ll_MoreApp:
                Uri uri2 = Uri.parse("https://play.google.com/store/apps/developer?id=TT+Group");
                Intent goToMarket2 = new Intent(Intent.ACTION_VIEW, uri2);
                try {
                    startActivity(goToMarket2);
                } catch (ActivityNotFoundException e) {

                    startActivity(new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/developer?id=TT+Group")));
                }
                break;
            case R.id.txtvMoreApp:
                Uri uri = Uri.parse("https://play.google.com/store/apps/developer?id=TT+Group");
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {

                    startActivity(new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/developer?id=TT+Group")));
                }
                break;
        }

    }

    private void nhapSoTienSuDungffa() {
        final Dialog dialogLtss = new Dialog(MainActivity.this);
        dialogLtss.setContentView(R.layout.dialog_used_nec);
        dialogLtss.setCancelable(false);
        // anh xa
        edtUsed = dialogLtss.findViewById(R.id.edtUsedNec);
        Button btnOkNec = dialogLtss.findViewById(R.id.btnNecOk);
        Button btnCancel = dialogLtss.findViewById(R.id.btnNecCancel);
        TextView txtvNec = dialogLtss.findViewById(R.id.txtvNec);
        txtvNec.setText(R.string.so_tien_da_dung_ffa);
        edtUsed.addTextChangedListener(onTextChangedListenneredtNhapTienSuDungNec());
        // click cancle
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLtss.cancel();
            }
        });
        // click ok
        btnOkNec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sEdt = edtUsed.getText().toString().replaceAll(",", "");
                if (sEdt == null || sEdt.isEmpty()) {
                    // toat thong bao
                    Toast.makeText(MainActivity.this, R.string.hay_nhap_so_tien_da_dung, Toast.LENGTH_LONG).show();
                } else {
                    Integer iEdt = Integer.valueOf(sEdt);
                    readData();
                    // lay data db123 de tinh
                    database = Database.initDatabase(MainActivity.this, DATABASE_NAME);
                    Cursor cursor = database.rawQuery("SELECT * FROM SIXJAR", null);
                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToPosition(i);
                        id = cursor.getInt(0);
                        hu6 = cursor.getLong(6);
                    }
                    // tru so tien da su dung
                    Long tru = hu6 - iEdt;

                    ContentValues contentValues = new ContentValues();
                    contentValues.put("ffa", tru);
                    SQLiteDatabase database = Database.initDatabase(MainActivity.this, "six.sqlite");
                    database.update("SIXJAR", contentValues, "id = ?", new String[]{id + ""});
                    hienThiTien();
                    dialogLtss.cancel();
                }
            }
        });
        dialogLtss.show();
    }

    private void nhapSoTienSuDungGive() {
        final Dialog dialogLtss = new Dialog(MainActivity.this);
        dialogLtss.setContentView(R.layout.dialog_used_nec);
        dialogLtss.setCancelable(false);
        // anh xa
        edtUsed = dialogLtss.findViewById(R.id.edtUsedNec);
        Button btnOkNec = dialogLtss.findViewById(R.id.btnNecOk);
        Button btnCancel = dialogLtss.findViewById(R.id.btnNecCancel);
        TextView txtvNec = dialogLtss.findViewById(R.id.txtvNec);
        txtvNec.setText(R.string.so_tien_da_dung_give);
        txtvNec.setTextColor(Color.RED);
        edtUsed.addTextChangedListener(onTextChangedListenneredtNhapTienSuDungNec());
        // click cancle
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLtss.cancel();
            }
        });
        // click ok
        btnOkNec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sEdt = edtUsed.getText().toString().replaceAll(",", "");
                if (sEdt == null || sEdt.isEmpty()) {
                    // toat thong bao
                    Toast.makeText(MainActivity.this, R.string.hay_nhap_so_tien_da_dung, Toast.LENGTH_LONG).show();
                } else {
                    Integer iEdt = Integer.valueOf(sEdt);
                    readData();
                    // lay data db123 de tinh
                    database = Database.initDatabase(MainActivity.this, DATABASE_NAME);
                    Cursor cursor = database.rawQuery("SELECT * FROM SIXJAR", null);
                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToPosition(i);
                        id = cursor.getInt(0);
                        hu5 = cursor.getLong(5);
                    }
                    // tru so tien da su dung
                    Long tru = hu5 - iEdt;

                    ContentValues contentValues = new ContentValues();
                    contentValues.put("give", tru);
                    SQLiteDatabase database = Database.initDatabase(MainActivity.this, "six.sqlite");
                    database.update("SIXJAR", contentValues, "id = ?", new String[]{id + ""});
                    hienThiTien();
                    dialogLtss.cancel();
                }
            }
        });
        dialogLtss.show();
    }

    private void nhapSoTienSuDungPlay() {
        final Dialog dialogLtss = new Dialog(MainActivity.this);
        dialogLtss.setContentView(R.layout.dialog_used_nec);
        dialogLtss.setCancelable(false);
        // anh xa
        edtUsed = dialogLtss.findViewById(R.id.edtUsedNec);
        Button btnOkNec = dialogLtss.findViewById(R.id.btnNecOk);
        Button btnCancel = dialogLtss.findViewById(R.id.btnNecCancel);
        TextView txtvNec = dialogLtss.findViewById(R.id.txtvNec);
        txtvNec.setText(R.string.so_tien_da_dung_play);
        txtvNec.setTextColor(Color.BLACK);
        edtUsed.addTextChangedListener(onTextChangedListenneredtNhapTienSuDungNec());
        // click cancle
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLtss.cancel();
            }
        });
        // click ok
        btnOkNec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sEdt = edtUsed.getText().toString().replaceAll(",", "");
                if (sEdt == null || sEdt.isEmpty()) {
                    // toat thong bao
                    Toast.makeText(MainActivity.this, R.string.hay_nhap_so_tien_da_dung, Toast.LENGTH_LONG).show();
                } else {
                    Integer iEdt = Integer.valueOf(sEdt);
                    readData();
                    // lay data db123 de tinh
                    database = Database.initDatabase(MainActivity.this, DATABASE_NAME);
                    Cursor cursor = database.rawQuery("SELECT * FROM SIXJAR", null);
                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToPosition(i);
                        id = cursor.getInt(0);
                        hu4 = cursor.getLong(4);
                    }
                    // tru so tien da su dung
                    Long tru = hu4 - iEdt;

                    ContentValues contentValues = new ContentValues();
                    contentValues.put("play", tru);
                    SQLiteDatabase database = Database.initDatabase(MainActivity.this, "six.sqlite");
                    database.update("SIXJAR", contentValues, "id = ?", new String[]{id + ""});
                    hienThiTien();
                    dialogLtss.cancel();
                }
            }
        });
        dialogLtss.show();
    }

    private void nhapSoTienSuDungEduc() {
        final Dialog dialogLtss = new Dialog(MainActivity.this);
        dialogLtss.setContentView(R.layout.dialog_used_nec);
        dialogLtss.setCancelable(false);
        // anh xa
        edtUsed = dialogLtss.findViewById(R.id.edtUsedNec);
        Button btnOkNec = dialogLtss.findViewById(R.id.btnNecOk);
        Button btnCancel = dialogLtss.findViewById(R.id.btnNecCancel);
        TextView txtvNec = dialogLtss.findViewById(R.id.txtvNec);
        txtvNec.setText(R.string.so_tien_da_dung_educ);
        txtvNec.setTextColor(Color.RED);
        edtUsed.addTextChangedListener(onTextChangedListenneredtNhapTienSuDungNec());
        // click cancle
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLtss.cancel();
            }
        });
        // click ok
        btnOkNec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sEdt = edtUsed.getText().toString().replaceAll(",", "");
                if (sEdt == null || sEdt.isEmpty()) {
                    // toat thong bao
                    Toast.makeText(MainActivity.this, R.string.hay_nhap_so_tien_da_dung, Toast.LENGTH_LONG).show();
                } else {
                    Integer iEdt = Integer.valueOf(sEdt);
                    readData();
                    // lay data db123 de tinh
                    database = Database.initDatabase(MainActivity.this, DATABASE_NAME);
                    Cursor cursor = database.rawQuery("SELECT * FROM SIXJAR", null);
                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToPosition(i);
                        id = cursor.getInt(0);
                        hu3 = cursor.getLong(3);
                    }
                    // tru so tien da su dung
                    Long tru = hu3 - iEdt;

                    ContentValues contentValues = new ContentValues();
                    contentValues.put("educ", tru);
                    SQLiteDatabase database = Database.initDatabase(MainActivity.this, "six.sqlite");
                    database.update("SIXJAR", contentValues, "id = ?", new String[]{id + ""});
                    hienThiTien();
                    dialogLtss.cancel();
                }
            }
        });
        dialogLtss.show();
    }

    private void nhapSoTienSuDungLtss() {
        final Dialog dialogLtss = new Dialog(MainActivity.this);
        dialogLtss.setContentView(R.layout.dialog_used_nec);
        dialogLtss.setCancelable(false);
        // anh xa
        edtUsed = dialogLtss.findViewById(R.id.edtUsedNec);
        Button btnOkNec = dialogLtss.findViewById(R.id.btnNecOk);
        Button btnCancel = dialogLtss.findViewById(R.id.btnNecCancel);
        TextView txtvNec = dialogLtss.findViewById(R.id.txtvNec);
        txtvNec.setText(R.string.so_tien_da_dung_ltss);
        txtvNec.setTextColor(Color.YELLOW);
        edtUsed.addTextChangedListener(onTextChangedListenneredtNhapTienSuDungNec());
        // click cancle
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLtss.cancel();
            }
        });
        // click ok
        btnOkNec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sEdt = edtUsed.getText().toString().replaceAll(",", "");
                if (sEdt == null || sEdt.isEmpty()) {
                    // toat thong bao
                    Toast.makeText(MainActivity.this, R.string.hay_nhap_so_tien_da_dung, Toast.LENGTH_LONG).show();
                } else {
                    Integer iEdt = Integer.valueOf(sEdt);
                    readData();
                    // lay data db123 de tinh
                    database = Database.initDatabase(MainActivity.this, DATABASE_NAME);
                    Cursor cursor = database.rawQuery("SELECT * FROM SIXJAR", null);
                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToPosition(i);
                        id = cursor.getInt(0);
                        hu2 = cursor.getLong(2);
                    }
                    // tru so tien da su dung
                    Long tru = hu2 - iEdt;

                    ContentValues contentValues = new ContentValues();
                    contentValues.put("ltss", tru);
                    SQLiteDatabase database = Database.initDatabase(MainActivity.this, "six.sqlite");
                    database.update("SIXJAR", contentValues, "id = ?", new String[]{id + ""});
                    hienThiTien();
                    dialogLtss.cancel();
                }
            }
        });
        dialogLtss.show();
    }

    private void nhapSoTienSuDungNec() {
        final Dialog dialogNec = new Dialog(MainActivity.this);
        dialogNec.setContentView(R.layout.dialog_used_nec);
        dialogNec.setCancelable(false);
        // anh xa
        edtUsed = dialogNec.findViewById(R.id.edtUsedNec);
        Button btnOkNec = dialogNec.findViewById(R.id.btnNecOk);
        Button btnCancel = dialogNec.findViewById(R.id.btnNecCancel);
        edtUsed.addTextChangedListener(onTextChangedListenneredtNhapTienSuDungNec());
        // click cancle
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogNec.cancel();
            }
        });
        // click ok
        btnOkNec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sEdt = edtUsed.getText().toString().replaceAll(",", "");
                if (sEdt == null || sEdt.isEmpty()) {
                    // toat thong bao
                    Toast.makeText(MainActivity.this, R.string.hay_nhap_so_tien_da_dung, Toast.LENGTH_LONG).show();
                } else {
                    Integer iEdt = Integer.valueOf(sEdt);
                    readData();
                    // lay data db123 de tinh
                    database = Database.initDatabase(MainActivity.this, DATABASE_NAME);
                    Cursor cursor = database.rawQuery("SELECT * FROM SIXJAR", null);
                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToPosition(i);
                        id = cursor.getInt(0);
                        hu1 = cursor.getLong(1);
                    }
                    // tru so tien da su dung
                    Long tru = hu1 - iEdt;

                    ContentValues contentValues = new ContentValues();
                    contentValues.put("nec", tru);
                    SQLiteDatabase database = Database.initDatabase(MainActivity.this, "six.sqlite");
                    database.update("SIXJAR", contentValues, "id = ?", new String[]{id + ""});
                    hienThiTien();
                    dialogNec.cancel();
                }
            }
        });
        dialogNec.show();
    }

    private void dialogThemTien() {
        final Dialog dialogThemTien = new Dialog(MainActivity.this);
        dialogThemTien.setContentView(R.layout.dialog_them_tien);
        dialogThemTien.setCancelable(false);
        dialogThemTien.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button btnOk = dialogThemTien.findViewById(R.id.btn_dl_themTien_ok);
        Button btnCancel = dialogThemTien.findViewById(R.id.btn_dl_themTien_cancel);
        edtThemTien = dialogThemTien.findViewById(R.id.edt_dl_themTien);
        //fortmat edtThemTien
        edtThemTien.addTextChangedListener(onTextChangedListenner());
        //click button
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyLuuTien();
            }

            private void xuLyLuuTien() {
                // TODO SOMETHING
                final String sMoney = edtThemTien.getText().toString().replaceAll(",", "");
                if (sMoney.isEmpty() || sMoney == null) {
                    Toast.makeText(MainActivity.this, "Nhap gia tri ", Toast.LENGTH_LONG).show();
                    edtThemTien.getText().clear();
                } else {
                    Double iMoney = Double.valueOf(sMoney);
                    if (iMoney <= 0) {
                        Toast.makeText(MainActivity.this, "Gia tri khong hop le", Toast.LENGTH_LONG).show();
                        edtThemTien.getText().clear();
                    } else {
                        readData2();
                        nec = Math.round(((nec2 * iMoney) / 100));
                        String inec = String.valueOf(nec);
                        ltss = Math.round((ltss2 * iMoney) / 100);
                        String dltss = String.valueOf(ltss);
                        educ = Math.round((educ2 * iMoney) / 100);
                        String deduc = String.valueOf(educ);
                        play = Math.round((play2 * iMoney) / 100);
                        String dpley = String.valueOf(play);
                        give = Math.round((give2 * iMoney) / 100);
                        String dgive = String.valueOf(give);
                        ffa = Math.round((ffa2 * iMoney) / 100);
                        String dffa = String.valueOf(ffa);
                        updateMoney();
                        edtThemTien.getText().clear();
                        hienThiTien();
                        dialogThemTien.cancel();
                    }
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogThemTien.cancel();
            }
        });
        dialogThemTien.show();
    }

    // ham format edtAddMoney
    private TextWatcher onTextChangedListenner() {
        return new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edtThemTien.removeTextChangedListener(this);
                try {
                    String orginaString = s.toString();
                    Long longval;
                    if (orginaString.contains(",")) {
                        orginaString = orginaString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(orginaString);

                    DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    format.applyPattern("#,###,###,###");
                    String formatString = format.format(longval);
                    // setting text format for edt
                    // tv.setText(formatString);
                    edtThemTien.setText(formatString);
                    edtThemTien.setSelection(edtThemTien.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
                edtThemTien.addTextChangedListener(this);

            }
        };
    }

    // ham format txtv Nec
    private TextWatcher onTextViewChangedListennerNec() {
        return new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                txtvNec.removeTextChangedListener(this);
                /*ltss.removeTextChangedListener(this);
                educ.removeTextChangedListener(this);
                play.removeTextChangedListener(this);
                give.removeTextChangedListener(this);
                ffa.removeTextChangedListener(this);*/
                try {
                    String orginaString = s.toString();
                    Long longval;
                    if (orginaString.contains(",")) {
                        orginaString = orginaString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(orginaString);

                    DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    format.applyPattern("#,###,###,###");
                    String formatString = format.format(longval);
                    // setting text format for edt
                    txtvNec.setText(formatString);
                   /* ltss.setText(formatString);
                    educ.setText(formatString);
                    play.setText(formatString);
                    give.setText(formatString);
                    ffa.setText(formatString);*/
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
                txtvNec.addTextChangedListener(this);
               /* ltss.addTextChangedListener(this);
                educ.addTextChangedListener(this);
                ffa.addTextChangedListener(this);
                play.addTextChangedListener(this);
                give.addTextChangedListener(this);*/

            }
        };
    }

    // ham format txtv LTSS
    private TextWatcher onTextViewChangedListennerLtss() {
        return new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                txtvLtss.removeTextChangedListener(this);
                /*ltss.removeTextChangedListener(this);
                educ.removeTextChangedListener(this);
                play.removeTextChangedListener(this);
                give.removeTextChangedListener(this);
                ffa.removeTextChangedListener(this);*/
                try {
                    String orginaString = s.toString();
                    Long longval;
                    if (orginaString.contains(",")) {
                        orginaString = orginaString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(orginaString);

                    DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    format.applyPattern("#,###,###,###");
                    String formatString = format.format(longval);
                    // setting text format for edt
                    txtvLtss.setText(formatString);
                   /* ltss.setText(formatString);
                    educ.setText(formatString);
                    play.setText(formatString);
                    give.setText(formatString);
                    ffa.setText(formatString);*/
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
                txtvLtss.addTextChangedListener(this);
               /* ltss.addTextChangedListener(this);
                educ.addTextChangedListener(this);
                ffa.addTextChangedListener(this);
                play.addTextChangedListener(this);
                give.addTextChangedListener(this);*/

            }
        };
    }

    // ham format txtv EDUC
    private TextWatcher onTextViewChangedListennerLtssEduc() {
        return new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                txtvEduc.removeTextChangedListener(this);
                /*ltss.removeTextChangedListener(this);
                educ.removeTextChangedListener(this);
                play.removeTextChangedListener(this);
                give.removeTextChangedListener(this);
                ffa.removeTextChangedListener(this);*/
                try {
                    String orginaString = s.toString();
                    Long longval;
                    if (orginaString.contains(",")) {
                        orginaString = orginaString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(orginaString);

                    DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    format.applyPattern("#,###,###,###");
                    String formatString = format.format(longval);
                    // setting text format for edt
                    txtvEduc.setText(formatString);
                   /* ltss.setText(formatString);
                    educ.setText(formatString);
                    play.setText(formatString);
                    give.setText(formatString);
                    ffa.setText(formatString);*/
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
                txtvEduc.addTextChangedListener(this);
               /* ltss.addTextChangedListener(this);
                educ.addTextChangedListener(this);
                ffa.addTextChangedListener(this);
                play.addTextChangedListener(this);
                give.addTextChangedListener(this);*/

            }
        };
    }

    // ham format txtv Play
    private TextWatcher onTextViewChangedListennerLtssPlay() {
        return new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                txtvPlay.removeTextChangedListener(this);
                /*ltss.removeTextChangedListener(this);
                educ.removeTextChangedListener(this);
                play.removeTextChangedListener(this);
                give.removeTextChangedListener(this);
                ffa.removeTextChangedListener(this);*/
                try {
                    String orginaString = s.toString();
                    Long longval;
                    if (orginaString.contains(",")) {
                        orginaString = orginaString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(orginaString);

                    DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    format.applyPattern("#,###,###,###");
                    String formatString = format.format(longval);
                    // setting text format for edt
                    txtvPlay.setText(formatString);
                   /* ltss.setText(formatString);
                    educ.setText(formatString);
                    play.setText(formatString);
                    give.setText(formatString);
                    ffa.setText(formatString);*/
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
                txtvPlay.addTextChangedListener(this);
               /* ltss.addTextChangedListener(this);
                educ.addTextChangedListener(this);
                ffa.addTextChangedListener(this);
                play.addTextChangedListener(this);
                give.addTextChangedListener(this);*/

            }
        };
    }

    // ham format txtv Give
    private TextWatcher onTextViewChangedListennerLtssGive() {
        return new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                txtvGive.removeTextChangedListener(this);
                /*ltss.removeTextChangedListener(this);
                educ.removeTextChangedListener(this);
                play.removeTextChangedListener(this);
                give.removeTextChangedListener(this);
                ffa.removeTextChangedListener(this);*/
                try {
                    String orginaString = s.toString();
                    Long longval;
                    if (orginaString.contains(",")) {
                        orginaString = orginaString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(orginaString);

                    DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    format.applyPattern("#,###,###,###");
                    String formatString = format.format(longval);
                    // setting text format for edt
                    txtvGive.setText(formatString);
                   /* ltss.setText(formatString);
                    educ.setText(formatString);
                    play.setText(formatString);
                    give.setText(formatString);
                    ffa.setText(formatString);*/
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
                txtvGive.addTextChangedListener(this);
               /* ltss.addTextChangedListener(this);
                educ.addTextChangedListener(this);
                ffa.addTextChangedListener(this);
                play.addTextChangedListener(this);
                give.addTextChangedListener(this);*/

            }
        };
    }

    // ham format txtv FFa
    private TextWatcher onTextViewChangedListennerLtssFfa() {
        return new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                txtvFfa.removeTextChangedListener(this);
                /*ltss.removeTextChangedListener(this);
                educ.removeTextChangedListener(this);
                play.removeTextChangedListener(this);
                give.removeTextChangedListener(this);
                ffa.removeTextChangedListener(this);*/
                try {
                    String orginaString = s.toString();
                    Long longval;
                    if (orginaString.contains(",")) {
                        orginaString = orginaString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(orginaString);

                    DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    format.applyPattern("#,###,###,###");
                    String formatString = format.format(longval);
                    // setting text format for edt
                    txtvFfa.setText(formatString);
                   /* ltss.setText(formatString);
                    educ.setText(formatString);
                    play.setText(formatString);
                    give.setText(formatString);
                    ffa.setText(formatString);*/
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
                txtvFfa.addTextChangedListener(this);
               /* ltss.addTextChangedListener(this);
                educ.addTextChangedListener(this);
                ffa.addTextChangedListener(this);
                play.addTextChangedListener(this);
                give.addTextChangedListener(this);*/

            }
        };
    }

    // ham format edt nec
    private TextWatcher onTextChangedListenneredtNhapTienSuDungNec() {
        return new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edtUsed.removeTextChangedListener(this);
                try {
                    String orginaString = s.toString();
                    Long longval;
                    if (orginaString.contains(",")) {
                        orginaString = orginaString.replaceAll(",", "");
                    }
                    longval = Long.parseLong(orginaString);

                    DecimalFormat format = (DecimalFormat) NumberFormat.getInstance(Locale.US);
                    format.applyPattern("#,###,###,###");
                    String formatString = format.format(longval);
                    // setting text format for edt
                    // tv.setText(formatString);
                    edtUsed.setText(formatString);
                    edtUsed.setSelection(edtUsed.getText().length());
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                }
                edtUsed.addTextChangedListener(this);

            }
        };
    }

    private void updateMoney() {
        Double hu11 = null;
        Double hu22 = null;
        Double hu33 = null;
        Double hu44 = null;
        Double hu55 = null;
        Double hu66 = null;

        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM SIXJAR", null);

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            id = cursor.getInt(0);
            hu11 = cursor.getDouble(1);
            hu22 = cursor.getDouble(2);
            hu33 = cursor.getDouble(3);
            hu44 = cursor.getDouble(4);
            hu55 = cursor.getDouble(5);
            hu66 = cursor.getDouble(6);
        }
        double congTienHu1 = nec + hu11;
        double congTienHu2 = ltss + hu22;
        double congTienHu3 = educ + hu33;
        double congTienHu4 = play + hu44;
        double congTienHu5 = give + hu55;
        double congTienHu6 = ffa + hu66;
        // update tien hu 1
        ContentValues contentValues = new ContentValues();
        contentValues.put("nec", congTienHu1);
        contentValues.put("ltss", congTienHu2);
        contentValues.put("educ", congTienHu3);
        contentValues.put("play", congTienHu4);
        contentValues.put("give", congTienHu5);
        contentValues.put("ffa", congTienHu6);

        SQLiteDatabase database = Database.initDatabase(MainActivity.this, "six.sqlite");
        database.update("SIXJAR", contentValues, "id = ?", new String[]{id + ""});
    }
}
