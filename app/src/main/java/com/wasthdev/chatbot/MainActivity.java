package com.wasthdev.chatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wasthdev.chatbot.Adpter.MessageAdapter;
import com.wasthdev.chatbot.Model.Message;
import com.wasthdev.chatbot.constant.Contanstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    RecyclerView chatBox;
    EditText messageBox;
    ImageButton sendBtn;


    //create requset volly
    private RequestQueue requestQueue;

    //create arraylist for model and adapter
    ArrayList<Message> messageArrayList;
    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        create hooks
        chatBox = findViewById(R.id.chtBox);
        messageBox = findViewById(R.id.messageBox);
        sendBtn = findViewById(R.id.sendButton);

        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.getCache().clear();

        messageArrayList = new ArrayList<>();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messageBox.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Please Write your Message", Toast.LENGTH_SHORT).show();
                    return;
                }

                sendMessage(messageBox.getText().toString());
            }
        });

        messageAdapter = new MessageAdapter(messageArrayList,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false);
        chatBox.setLayoutManager(linearLayoutManager);
        chatBox.setAdapter(messageAdapter);
    }

    private void sendMessage(String toString) {
        System.out.println("hi helo /........." + toString);
        Message message = new Message(toString, Contanstant.USER_KEY);
        messageArrayList.add(message);
        String url = "http://api.brainshop.ai/get?bid=159692&key=CnV0saEHYlWM56ra&uid=[uid]&msg="+toString;


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String botMessage = response.getString("cnt");
                    messageArrayList.add(new Message(botMessage,Contanstant.BOT_KEY));

                    messageAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                        e.printStackTrace();
                        messageArrayList.add(new Message("No response",Contanstant.BOT_KEY));
                        messageAdapter.notifyDataSetChanged();

                }

            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                messageArrayList.add(new Message("Sorry no response",Contanstant.BOT_KEY));
                Toast.makeText(MainActivity.this, "Sorry no response", Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue makeRequestQueue = Volley.newRequestQueue(this);
        makeRequestQueue.add(jsonObjectRequest);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//                try {
//                    String botMessage = response.getString("cnt");
//                    messageArrayList.add(new Message(botMessage,Contanstant.BOT_KEY));
//
//                    messageAdapter.notifyDataSetChanged();
//                } catch (JSONException e) {
//                        e.printStackTrace();
//                        messageArrayList.add(new Message("No response",Contanstant.BOT_KEY));
//                        messageAdapter.notifyDataSetChanged();
//
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//                messageArrayList.add(new Message("Sorry no response",Contanstant.BOT_KEY));
//                Toast.makeText(MainActivity.this, "Sorry no response", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        makeRequestQueue.add(jsonObjectRequest);
    }

}