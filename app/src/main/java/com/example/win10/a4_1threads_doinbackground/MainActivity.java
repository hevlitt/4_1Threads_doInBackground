package com.example.win10.a4_1threads_doinbackground;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ProgressBar bar, bar2;
    Button btn,btn2;
    EditText limit,limit2;
    TextView txt1,txt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bar = findViewById(R.id.progressBar);
        bar2 = findViewById(R.id.progressBar2);
        btn = findViewById(R.id.btnconteo);
        btn2 = findViewById(R.id.btnconteo2);
        limit = findViewById(R.id.txtlimit);
        limit2 = findViewById(R.id.txtlimit2);
        txt1 = findViewById(R.id.contador1);
        txt2 = findViewById(R.id.contador2);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setMax(Integer.parseInt(limit.getText().toString()));
                btn.setClickable(false);
                AsyncTask_load hilo1 = new AsyncTask_load(bar,Integer.parseInt(limit.getText().toString()),btn,txt1);
                hilo1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar2.setMax(Integer.parseInt(limit2.getText().toString()));
                btn2.setClickable(false);
                AsyncTask_load hilo1 = new AsyncTask_load(bar2,Integer.parseInt(limit2.getText().toString()),btn2,txt2);
                hilo1.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
    }

    private void conteo() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

public class AsyncTask_load extends AsyncTask<Void, Integer, Void>{

    ProgressBar bar;
    int progress,maximo;
    Button btn;
    TextView txt;

    public AsyncTask_load(ProgressBar bar, int limite,Button btn,TextView textview){
        this.bar = bar;
        this.maximo = limite;
        this.btn = btn;
        this.txt = textview;
    }

    @Override
    protected void onPreExecute(){
        progress = 0;
        this.txt.setText(""+progress);
        super.onPreExecute();
        this.bar.setMax(this.maximo);
    }

    @Override
    protected Void doInBackground(Void... params) {

        while(progress < this.bar.getMax()){
            progress++;
            publishProgress(progress);
            conteo();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values){
        super.onProgressUpdate(values);
        this.bar.setProgress(values[0]);
        this.txt.setText(""+values[0]);
    }

    @Override
    protected void onPostExecute(Void result){
        btn.setClickable(true);
    }
}
}
