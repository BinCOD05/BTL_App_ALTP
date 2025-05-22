package com.example.btlailatrieuphu.Model;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveMoney {
    public static void save(Context context , int money){
         SharedPreferences pref = context.getSharedPreferences("game_data" , context.MODE_PRIVATE );
            int currentMoney = pref.getInt("Total_Money" , 0 );
            SharedPreferences.Editor editor = pref.edit();
            if(money >= currentMoney){
                editor.putInt("Total_Money" ,  money );
                editor.apply();
        }
    }
    public static  int getTotal(Context context){
        SharedPreferences pref = context.getSharedPreferences("game_data" , context.MODE_PRIVATE);
        return pref.getInt("Total_Money" , 0);
    }
}
