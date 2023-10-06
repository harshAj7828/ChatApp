package com.example.chatapp;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public final class MemoryData {

    public static void saveData(String data,Context context){
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("data.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();


        }catch (IOException e){
            e.printStackTrace();

        }

    }
    public static void saveLastMsg(String data,String chatId,Context context){
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(chatId+".txt", Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();


        }catch (IOException e){
            e.printStackTrace();

        }

    }

    public static void saveEmail(String data,Context context){
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("email.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();


        }catch (IOException e){
            e.printStackTrace();

        }

    }
    public static void savePassword(String data,Context context){
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("password.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();


        }catch (IOException e){
            e.printStackTrace();

        }

    }
    public static void saveName(String data,Context context){
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("name.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();


        }catch (IOException e){
            e.printStackTrace();

        }

    }
    public static String getData(Context context){
        String data = "";
        try {
            FileInputStream fileInputStream = context.openFileInput("data.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
            data = stringBuilder.toString();

        }catch (IOException e){
            e.printStackTrace();

        }
        return data;


    }

    public static String getLastMsg(Context context,String chatId){
        String data = "0";
        try {
            FileInputStream fileInputStream = context.openFileInput(chatId+".txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
            data = stringBuilder.toString();

        }catch (IOException e){
            e.printStackTrace();

        }
        return data;


    }

    public static String getEmail(Context context){
        String data = "";
        try {
            FileInputStream fileInputStream = context.openFileInput("email.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
            data = stringBuilder.toString();

        }catch (IOException e){
            e.printStackTrace();

        }
        return data;


    }
    public static String getPassword(Context context){
        String data = "";
        try {
            FileInputStream fileInputStream = context.openFileInput("password.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
            data = stringBuilder.toString();

        }catch (IOException e){
            e.printStackTrace();

        }
        return data;


    }
    public static String getName(Context context){
        String data = "";
        try {
            FileInputStream fileInputStream = context.openFileInput("name.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
            data = stringBuilder.toString();

        }catch (IOException e){
            e.printStackTrace();

        }
        return data;


    }
}
