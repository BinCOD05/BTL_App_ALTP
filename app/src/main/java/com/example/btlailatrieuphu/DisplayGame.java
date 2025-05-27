    package com.example.btlailatrieuphu;

    import static android.view.View.INVISIBLE;

    import android.app.Dialog;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.activity.EdgeToEdge;
    import androidx.appcompat.app.AlertDialog;
    import androidx.appcompat.app.AppCompatActivity;

    import com.android.volley.Request;
    import com.android.volley.RequestQueue;
    import com.android.volley.Response;
    import com.android.volley.VolleyError;
    import com.android.volley.toolbox.JsonArrayRequest;
    import com.android.volley.toolbox.Volley;
    import com.example.btlailatrieuphu.Model.SaveMoney;

    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;

    import java.util.ArrayList;
    import java.util.Collection;
    import java.util.Collections;
    import java.util.List;

    public class DisplayGame extends AppCompatActivity {

        private TextView txtQuestion,txtQuesNum,txtCurrentMoney;
        private Button btnAns1, btnAns2, btnAns3, btnAns4;
        private List<Question> questionList = new ArrayList<>();
        private  int currentQuestionIndex = 0 , currentMoney = 0 , rewardMoney = 0  ;
        private ImageView btnMenu;
        private  int[] moneyLevels = {200000 , 500000 , 1000000 , 1500000 , 3000000 , 5000000 , 7000000 , 10000000 , 11000000 , 15000000 , 20000000 , 25000000,30000000 , 40000000, 50000000  };
        private  boolean use50helper = false , useCallFriend = false ;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_display_game);

            txtQuestion = findViewById(R.id.txtQuestion);
            txtCurrentMoney = findViewById(R.id.txtCurrentMoney);
            btnAns1 = findViewById(R.id.btnAns1);
            btnAns2 = findViewById(R.id.btnAns2);
            btnAns3 = findViewById(R.id.btnAns3);
            btnAns4 = findViewById(R.id.btnAns4);
            txtQuesNum = findViewById(R.id.txtQuesNum);
            btnMenu = findViewById(R.id.btnMenu);
            btnMenu.setOnClickListener( v -> showPopupMenu());
            getApi();
        }

        private void getApi() {
            String url = "http://10.0.2.2/db_ai_la_trieu_phu/index.php";
            RequestQueue queue = Volley.newRequestQueue(this);
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject obj = response.getJSONObject(i);
                                    String questionText = obj.getString("questionText");

                                    int correctAns = obj.getInt("correctAnswerIndex");
                                    List<String> answerList = new ArrayList<>();
                                    answerList.add(obj.getString("ans1"));
                                    answerList.add(obj.getString("ans2"));
                                    answerList.add(obj.getString("ans3"));
                                    answerList.add(obj.getString("ans4"));

                                    questionList.add(new Question(questionText, answerList, correctAns));
                                }
                                Collections.shuffle(questionList);
                                // Hiển thị câu hỏi đầu tiên
                                if (!questionList.isEmpty()) {
                                    showQuestion();
                                } else {
                                    Toast.makeText(DisplayGame.this, "Không có câu hỏi nào.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(DisplayGame.this, "Lỗi đọc dữ liệu JSON", Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            Toast.makeText(DisplayGame.this, "Lỗi kết nối: " + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    });

            queue.add(request);
        }

        private void showQuestion() {
            btnAns1.setVisibility(View.VISIBLE);
            btnAns2.setVisibility(View.VISIBLE);
            btnAns3.setVisibility(View.VISIBLE);
            btnAns4.setVisibility(View.VISIBLE);
            if(currentQuestionIndex >= questionList.size()){
                Toast.makeText(DisplayGame.this , "Hoàn thành trò chơi" , Toast.LENGTH_LONG).show();
            }
            Question q = questionList.get(currentQuestionIndex);
            txtQuestion.setText(q.getQuestionText());
            currentMoney = moneyLevels[currentQuestionIndex];
            txtCurrentMoney.setText("Tiền thưởng +" + currentMoney + "VND");
            btnAns1.setText("A: " +q.getAnswer().get(0));
            btnAns2.setText("B: " + q.getAnswer().get(1));
            btnAns3.setText("C: " + q.getAnswer().get(2));
            btnAns4.setText("D: "+q.getAnswer().get(3));
            txtQuesNum.setText("Câu " + (currentQuestionIndex +1));

            setButtonEventClick(q);
    //
        }
        private void setButtonEventClick(Question q ){
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int selectedAns = -1 ;
                    if(v == btnAns1) selectedAns = 1 ;
                    else if(v == btnAns2) selectedAns = 2 ;
                    else if(v == btnAns3) selectedAns = 3 ;
                    else if (v == btnAns4) selectedAns = 4 ;
                    if(selectedAns == q.getCorrectAns()){
                        showResult(true);
                    }
                    else{
                        showResult(false);
                    }
                }
            };
            btnAns1.setOnClickListener(listener );
            btnAns2.setOnClickListener(listener);
            btnAns3.setOnClickListener(listener);
            btnAns4.setOnClickListener(listener);
        }

        private void showResult(boolean isTrue) {
            Question q = questionList.get(currentQuestionIndex);
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.notify_result);
            Button btnContinue = dialog.findViewById(R.id.btnContinue);
            TextView txtResult = dialog.findViewById(R.id.txtResult);
            TextView txtResult2 = dialog.findViewById(R.id.txtResult2);
            if(isTrue){

                txtResult.setText("Chính xác !!!");
                btnContinue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rewardMoney = currentMoney;
                        currentQuestionIndex++;
                        dialog.dismiss();
                        showQuestion();


                    }
                });
            }
            else{
                txtResult.setText("Sai rồi !!! " );
                txtResult2.setText("Đáp án là " + q.getAnswer().get(q.getCorrectAns()-1));
                btnContinue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent it = new Intent(DisplayGame.this , MainActivity.class);
                        startActivity(it);
                    }
                });
            }
            dialog.setCancelable(false);
            dialog.show();
        }
//        Hàm này dùng để hành động và hiện PopUp Menu
        private void showPopupMenu(){

            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.popup_menu);
            TextView txtQuesLevel = dialog.findViewById(R.id.txtCurrentLevel);
            TextView txtMoney = dialog.findViewById(R.id.txtRewardMoney);
            txtQuesLevel.setText("Câu " +(currentQuestionIndex+1) +"/15");
            txtMoney.setText(String.valueOf(rewardMoney) + "VND");
            Button btnContinue = dialog.findViewById(R.id.btnContinue);
            Button btnStop = dialog.findViewById(R.id.btnStop);
            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            btnStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DisplayGame.this);
                    builder.setTitle("Bạn sẽ hoàn thành trò chơi ");
                    builder.setMessage("Với mức tiền " + rewardMoney+ "VND");

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SaveMoney.save(DisplayGame.this , rewardMoney);
                            Intent it = new Intent(DisplayGame.this,MainActivity.class) ;
                            startActivity(it);

                        }
                    });
                    builder.setNeutralButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builder.create().show();
                }

            });
//            helper collection
            ImageView btn50 = dialog.findViewById(R.id.btn50);

            if(use50helper){
                btn50.setVisibility(INVISIBLE);
            }
            else{
                btn50.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fiftyFifty(questionList.get(currentQuestionIndex));
                        use50helper= true;
                        dialog.dismiss();
                    }
                });

            }
//          callFriend
            ImageView btnCallFriend = dialog.findViewById(R.id.btnFriend);
            if(useCallFriend){
                btnCallFriend.setVisibility(INVISIBLE);
            }
            else {
                btnCallFriend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        callFriend(questionList.get(currentQuestionIndex));
                        useCallFriend = true;
                    }
                });
            }
            dialog.show();
        }
        private void fiftyFifty(Question q){
            int index = q.getCorrectAns();
            List<Integer> hide = new ArrayList<>();
            for(int i = 1 ; i <= 4 ; i ++){
                if(i != index){
                    hide.add(i);
                }
            }
            Collections.shuffle(hide);
            for(int j = 0 ; j < 2 ; j++){
                if(hide.get(j) == 1) btnAns1.setVisibility(View.INVISIBLE);
                if(hide.get(j) == 2) btnAns2.setVisibility(View.INVISIBLE);
                if(hide.get(j) == 3) btnAns3.setVisibility(View.INVISIBLE);
                if(hide.get(j) == 4) btnAns4.setVisibility(View.INVISIBLE);
            }

        }
        private void callFriend(Question q){
            int index = q.getCorrectAns();
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.call_friend);
            TextView suggest = dialog.findViewById(R.id.txtSuggest);
            suggest.setText("Tôi nghĩ đáp án sẽ là " + q.getAnswer().get(q.getCorrectAns()-1) + "!!!");
            Button btnContinue = dialog.findViewById(R.id.btnContinue);
            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
