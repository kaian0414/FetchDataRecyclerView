// https://youtu.be/a4o9zFfyIM4
// https://youtu.be/Yw7Lx9wqyGs

package com.kaianchan.fetchdatarecyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String URL_CHAPTER = "http://ian14.online/fyp_php/getChap.php";

    RecyclerView chapRecyclerView;
    ChapAdapter chapAdapter;

    List<Chapter> chapterList;

    int clickChapPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chapRecyclerView = (RecyclerView) findViewById(R.id.chap_recyclerView);
        chapRecyclerView.setHasFixedSize(true); // Fixed size, MUST SET!
        chapRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        chapterList = new ArrayList<>();

        /*
        // Add data, static recyclerView
        chapterList.add(new Chapter(R.drawable.ch1, "Chapter 1"));
        chapterList.add(new Chapter(R.drawable.ch2, "Chapter 2"));
        chapterList.add(new Chapter(R.drawable.ch3, "Chapter 3"));
        */

        // Dynamic recyclerView
        loadChap();

        /*
        // Click recyclerView
        chapRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickChapPos = chapRecyclerView.getChildAdapterPosition(v) + 1;
                Toast.makeText(MainActivity.this, "Main: " + clickChapPos, Toast.LENGTH_SHORT).show();
            }
        });
        */
    }

    // Dynamic recyclerView
    public void loadChap() {
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                URL_CHAPTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray chapArray = new JSONArray(response);

                            for (int i = 0; i < chapArray.length(); i++) {
                                JSONObject chapObj = chapArray.getJSONObject(i); // index[i]

                                String vocabChapterTitle = chapObj.getString("chapterTitle");
                                String vocabChapterIcon = chapObj.getString("chapterIcon");

                                Chapter chapter = new Chapter(vocabChapterIcon, vocabChapterTitle); // Add into chapterList
                                chapterList.add(chapter);
                            }

                            chapAdapter = new ChapAdapter(MainActivity.this, chapterList);
                            chapRecyclerView.setAdapter(chapAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // RequestQueue
        Volley.newRequestQueue(this).add(stringRequest);
    }

    // Onclick CardView
    // chap_cardview.xml中, 設定了android:onClick="chapOnclick"
    public void chapOnclick (View view) {
        clickChapPos = chapRecyclerView.getChildAdapterPosition(view) + 1;
        Toast.makeText(MainActivity.this, "MainActiviity onClick " + clickChapPos, Toast.LENGTH_SHORT).show();

//        Intent intent = new Intent(MainActivity.this, ShowVocab.class);
//        intent.putExtra("position", clickChapPos);
//        startActivity(intent);
    }
}
