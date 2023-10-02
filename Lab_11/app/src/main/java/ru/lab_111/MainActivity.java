package ru.lab_111;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
public class MainActivity extends AppCompatActivity {
    ListView userList; // Список для отображения книг
    DatabaseHelper databaseHelper; // Объект класса для работы с базой данных
    SQLiteDatabase db; // Объект для работы с базой данных
    Cursor userCursor; // Курсор для получения результатов запроса к базе данных
    SimpleCursorAdapter userAdapter; // Адаптер для связи данных из курсора с пользовательским интерфейсом

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userList = findViewById(R.id.list); // Инициализация списка
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Обработчик клика по элементу списка
                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        databaseHelper = new DatabaseHelper(getApplicationContext()); // Создание экземпляра класса для работы с базой данных
        databaseHelper.create_db(); // Создание базы данных, если она не существует
    }

    @Override
    public void onResume() {
        super.onResume();
        db = databaseHelper.open(); // Открытие базы данных
        userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE1, null); // Выполнение запроса к базе данных
        String[] headers = new String[]{DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_BIRTHDAY}; // Заголовки столбцов
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0); // Создание адаптера
        userList.setAdapter(userAdapter); // Установка адаптера
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close(); // Закрытие базы данных
        userCursor.close(); // Закрытие курсора
    }
}