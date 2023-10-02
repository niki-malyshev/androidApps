package ru.lab_111;

import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH; // Полный путь к базе данных
    private static String DB_NAME = "myLibrary.db"; // Имя базы данных
    private static final int SCHEMA = 1; // Версия базы данных
    static final String TABLE = "books"; // Название таблицы для книг
    static final String TABLE1 = "authors"; // Название таблицы для авторов
    // Названия столбцов
    static final String COLUMN_ID_BOOK = "_id";
    static final String COLUMN_TITLE = "title";
    static final String COLUMN_YEAR = "year";
    static final String COLUMN_AUTHOR = "id_author";
    static final String COLUMN_ID_AUTHOR = "_id";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_BIRTHDAY = "birthday";
    static final String COLUMN_OTHER = "other";
    static final String COLUMN_PHOTO = "photo";
    private Context myContext;

    DatabaseHelper(Context context) {
        super(context, DB_NAME, null, SCHEMA);
        this.myContext=context;
        DB_PATH =context.getFilesDir().getPath() + DB_NAME; // Получение полного пути к базе данных
    }

    @Override
    public void onCreate(SQLiteDatabase db) { }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) { }

    void create_db(){
        // Создание базы данных, если она не существует
        File file = new File(DB_PATH);
        if (!file.exists()) {
            try(InputStream myInput = myContext.getAssets().open(DB_NAME);
                OutputStream myOutput = new FileOutputStream(DB_PATH)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();
            }
            catch(IOException ex){
                Log.d("DatabaseHelper", ex.getMessage());
            }
        }
    }
    public SQLiteDatabase open()throws SQLException {
        // Открытие базы данных для чтения и записи
        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }
}