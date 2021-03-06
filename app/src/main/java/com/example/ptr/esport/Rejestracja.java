package com.example.ptr.esport;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Rejestracja extends AppCompatActivity {
    DBSqlite dbsql;

    EditText et1,et2,et3,et4,et5y,et5m,et5d,et6;
    String plec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejestracja);
        dbsql=new DBSqlite(this);
    }
    public void click2(View view) throws SQLException {
        Cursor res = dbsql.getAllData();
        if(res.getCount()==0) {
            showMessage("Error","Nic nie ma");
            return;
        }
        StringBuffer buffer=new StringBuffer();
        buffer.append(res);
        while(res.moveToNext())
        {
            buffer.append("Id : "+res.getString(0)+"\n");
            buffer.append("Username : "+res.getString(1)+"\n");
            buffer.append("Password: "+res.getString(2)+"\n");
            buffer.append("Waga : "+res.getString(3)+"\n");
            buffer.append("wzrost : "+res.getString(4)+"\n");
            buffer.append("data ur : "+res.getString(5)+"\n");
            buffer.append("plec : "+res.getString(6)+"\n");
        }
        showMessage("Dane",buffer.toString());
    }
    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void click3(View view) {

        et1 = (EditText) findViewById(R.id.Text1);
        et2 = (EditText) findViewById(R.id.Text2);
        et3 = (EditText) findViewById(R.id.Text3);
        et4 = (EditText) findViewById(R.id.Text5);
        et5y = (EditText) findViewById(R.id.yyyy);
        et5m = (EditText) findViewById(R.id.mm);
        et5d = (EditText) findViewById(R.id.dd);
        et6 = (EditText) findViewById(R.id.Text6);
        CheckBox views1 = (CheckBox) findViewById(R.id.cb1);
        CheckBox views2 = (CheckBox) findViewById(R.id.cb2);
        String result=dbsql.showUsers(et1.getText().toString());

        if (et1.getText().toString().equals("") || et2.getText().toString().equals("") || et3.getText().toString().equals("") || et4.getText().toString().equals("") || et5y.getText().toString().equals("")||et5m.getText().toString().equals("") ||et5d.getText().toString().equals("")|| et6.getText().toString().equals(""))
            Toast.makeText(Rejestracja.this, "Wypełnij poprawnie wszystkie dane", Toast.LENGTH_SHORT).show();
        else if(result!=null)
            Toast.makeText(Rejestracja.this, "Username już istnieje", Toast.LENGTH_SHORT).show();
        else if ((!views1.isChecked() && !views2.isChecked()) || (views1.isChecked() && views2.isChecked()))
            Toast.makeText(Rejestracja.this, "wybierz poprawna jedna z opcji", Toast.LENGTH_SHORT).show();
        else if (!et2.getText().toString().equals(et3.getText().toString()))
            Toast.makeText(Rejestracja.this, "niepoprawne haslo", Toast.LENGTH_SHORT).show();
        else if(et5y.getText().length()>4 || et5m.getText().length()>2||et5d.getText().length()>2)
            Toast.makeText(Rejestracja.this, "niepoprawny format daty", Toast.LENGTH_SHORT).show();
        else {
            if (views1.isChecked())
                plec = "Kobieta";
            else
                plec = "Mezczyzna";
            String et5all=et5y.getText().toString()+"-"+et5m.getText().toString()+"-"+et5d.getText().toString();
            boolean isInserted=dbsql.insertDataUsers(et1.getText().toString(),et2.getText().toString(),et6.getText().toString(),et4.getText().toString(),et5all,plec);
            if(isInserted==true)
                Toast.makeText(Rejestracja.this, "Dodano dane", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(Rejestracja.this, "Nie dodano danych", Toast.LENGTH_SHORT).show();
        }
    }
    public void click4(View view) {
        Intent inten=new Intent(Rejestracja.this,Login.class);
        startActivity(inten);
    }
}
