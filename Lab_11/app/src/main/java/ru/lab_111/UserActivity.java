package ru.lab_111;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {
    TextView nameBox; // Поле для отображения имени автора
    TextView yearBox; // Поле для отображения года рождения автора
    TextView otherBox; // Поле для отображения дополнительной информации об авторе
    ImageView photoAuthor; // Изображение автора
    ListView userList; // Список для отображения книг
    DatabaseHelper sqlHelper; // Объект класса для работы с базой данных
    SQLiteDatabase db; // Объект для работы с базой данных
    Cursor userCursor; // Курсор для получения результатов запроса к базе данных
    Cursor bookCursor; // Курсор для получения результатов запроса к базе данных
    SimpleCursorAdapter userAdapter; // Адаптер для связи данных из курсора с пользовательским интерфейсом
    long userId = 0; // Идентификатор автора

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        nameBox = findViewById(R.id.name); // Инициализация поля имени автора
        yearBox = findViewById(R.id.year); // Инициализация поля года рождения автора
        otherBox = findViewById(R.id.otherText); // Инициализация поля дополнительной информации об авторе

        userList = findViewById(R.id.bookList); // Инициализация списка
        photoAuthor = findViewById(R.id.photoAuthor); // Инициализация изображения автора

        sqlHelper = new DatabaseHelper(this); // Создание экземпляра класса для работы с базой данных
        db = sqlHelper.open(); // Открытие базы данных

        Bundle extras = getIntent().getExtras();
        if (extras != null)
            userId = extras.getLong("id");

        userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE1 + " where " +
                DatabaseHelper.COLUMN_ID_AUTHOR + "=?", new String[]{String.valueOf(userId)}); // Выполнение запроса к базе данных
        userCursor.moveToFirst(); // Перемещение курсора на первую запись
        nameBox.setText(userCursor.getString(1)); // Установка имени автора в поле
        yearBox.setText(userCursor.getString(2)); // Установка года рождения автора в поле
        otherBox.setText(userCursor.getString(3)); // Установка дополнительной информации об авторе в поле

        byte[] byteArray = userCursor.getBlob(4); // Получение байтового массива с изображением автора
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length); // Преобразование байтового массива в Bitmap
        photoAuthor.setImageBitmap(bitmap); // Установка изображения автора

        userCursor.close(); // Закрытие курсора

        bookCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE + " where " +
                DatabaseHelper.COLUMN_AUTHOR + "=?", new String[]{String.valueOf(userId)}); // Выполнение запроса к базе данных
        String[] headers = new String[]{DatabaseHelper.COLUMN_TITLE, DatabaseHelper.COLUMN_YEAR}; // Заголовки столбцов
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                bookCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0); // Создание адаптера
        userList.setAdapter(userAdapter); // Установка адаптера

    }
}