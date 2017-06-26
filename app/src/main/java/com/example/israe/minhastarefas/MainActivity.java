package com.example.israe.minhastarefas;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

        editTextInserirTarefa = (EditText) findViewById(R.id.editTextTarefaId);
        buttonAdiconarTarefa = (Button) findViewById(R.id.buttonAdicinarId);
        listViewListaTarefas = (ListView) findViewById(R.id.listViewTarefasId);
        recarregarListaTarefas();
        buttonAdiconarTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Capiturar os dados digitados pelo usuario
                String stringInserirTarefa = editTextInserirTarefa.getText().toString();
                if(stringInserirTarefa.equals("")){
                    Toast.makeText(getApplicationContext(), "Digite uma tarefa", Toast.LENGTH_SHORT).show();
                }{
                    try{
                        sqLiteDatabase.execSQL("INSERT INTO tarefas (tarefa) VALUES ('" + stringInserirTarefa + "')");
                        recarregarListaTarefas();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void recarregarListaTarefas() {
        try {
            stringArrayListTarefas = new ArrayList<>();
            stringArrayAdapterTarefas = new ArrayAdapter<String>(
                    getApplicationContext(),
                    android.R.layout.simple_list_item_1,
                    android.R.id.text1,
                    stringArrayListTarefas
            );

            listViewListaTarefas.setAdapter(stringArrayAdapterTarefas);
            //Acessar ou CriarBanco
            sqLiteDatabase = openOrCreateDatabase("appminhastarefas", MODE_PRIVATE, null);
            //Criar tabela
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS tarefas (id INTEGER PRIMARY KEY AUTOINCREMENT, tarefa VARCHAR)");
            //Recuperar dados banco
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM tarefas ORDER BY id DESC", null);
            cursor.moveToFirst();

            while (cursor != null) {
                int indiceColunaTarefas = cursor.getColumnIndex("tarefa");
                String string = cursor.getString(indiceColunaTarefas);
                stringArrayListTarefas.add(string);
                cursor.moveToNext();
                Log.i("Resultado - ", " Tarefa: " + string);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
