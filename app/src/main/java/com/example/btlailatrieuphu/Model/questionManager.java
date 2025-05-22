//package com.example.btlailatrieuphu.Model;
//
//import android.content.Context;
//import android.widget.Toast;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonArrayRequest;
//import com.android.volley.toolbox.Volley;
//import com.example.btlailatrieuphu.DisplayGame;
//import com.example.btlailatrieuphu.Question;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.security.Principal;
//import java.util.ArrayList;
//import java.util.List;
//
//public class questionManager {
//    private List<Question> questionList  = new ArrayList<>();
//    private int currentQuestionIndex = 0 ;
//    private  final String url = "http://10.0.2.2/db_ai_la_trieu_phu/index.php";
//    public interface onQuestionLoadedListener{
//        void onLoaded(List<Question> questionsList);
//        void onError(String error);
//    }
//    public questionManager(Context context , onQuestionLoadedListener listener ){
//        RequestQueue queue = Volley.newRequestQueue(context);
//        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                try {
//                    for (int i = 0; i < response.length(); i++) {
//                        JSONObject obj = response.getJSONObject(i);
//                        String questionText = obj.getString("questionText");
//
//                        int correctAns = obj.getInt("correctAnswerIndex");
//                        List<String> answerList = new ArrayList<>();
//                        answerList.add(obj.getString("ans1"));
//                        answerList.add(obj.getString("ans2"));
//                        answerList.add(obj.getString("ans3"));
//                        answerList.add(obj.getString("ans4"));
//
//                        questionList.add(new Question(questionText, answerList, correctAns));
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    error.printStackTrace();
//                    Toast.makeText(DisplayGame.this, "Lỗi kết nối: " + error.toString(), Toast.LENGTH_LONG).show();
//                }
//            };
//
//    }
//}
