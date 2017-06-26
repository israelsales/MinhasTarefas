package com.example.israe.minhastarefas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editTextInserirTarefa;
    private Button buttonAdiconarTarefa;
    private ListView listViewListaTarefas;
    private SQLiteDatabase sqLiteDatabase;
    private ArrayAdapter<String> stringArrayAdapterTarefas;
    private ArrayList<String> stringArrayListTarefas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            //Acessar ou CriarBanco
            sqLiteDatabase = openOrCreateDatabase("appminhastarefas",MODE_PRIVATE,null);

            //Criar tabela
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS tarefas (id INTEGER PRIMARY KEY AUTOINCREMENT, tarefa VARCHAR)");

            editTextInserirTarefa = (EditText)findViewById(R.id.editTextTarefaId);
            buttonAdiconarTarefa = (Button) findViewById(R.id.buttonAdicinarId);
            listViewListaTarefas = (ListView)findViewById(R.id.listViewTarefasId);


            stringArrayListTarefas = new ArrayList<>();
            stringArrayAdapterTarefas = new ArrayAdapter<String>(
                    getApplicationContext(),
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    stringArrayListTarefas
            );
            listViewListaTarefas.setAdapter(stringArrayAdapterTarefas);

            //Recuperar dados banco
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM tarefas",null);
            cursor.moveToFirst();

            while (cursor!=null){
                int indiceColunaTarefas = cursor.getColumnIndex("tarefa");
                String string = cursor.getString(indiceColunaTarefas);
                stringArrayListTarefas.add(string);
                cursor.moveToNext();
                Log.i("Resultado - ", " Tarefa: " +string);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        buttonAdiconarTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Capiturar os dados digitados pelo usuario
                String stringInserirTarefa = editTextInserirTarefa.getText().toString();
                sqLiteDatabase.execSQL("INSERT INTO tarefas (tarefa) VALUES ('"+ stringInserirTarefa +"')");




            }
        });
    }
}
