package ru.lab_12;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private TextView outputTextView; // объявляем переменную для вывода текста в TextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        outputTextView = findViewById(R.id.outputTextView);
    }

    public void startButtonClick(View view) {
        final File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "data_T.txt"); // создаем объект File для записи в файл
        if (file.exists()) { // если файл уже существует, то удаляем его
            file.delete();
        }
        final Set<Integer> uniqueNumbers = new HashSet<>(); // создаем объект Set для хранения уникальных чисел
        final Thread[] threads = new Thread[6]; // создаем массив потоков
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    // метод synchronized нужен для того чтобы только один
                    // поток мог добавлять число в uniqueNumbers за раз
                    synchronized (uniqueNumbers) {
                        while (uniqueNumbers.size() < 6) { // пока уникальных чисел не будет 6 штук
                            int number = generateRandomNumber(); // генерируем случайное число
                            if (uniqueNumbers.add(number)) { // если число уникальное
                                try {
                                    FileWriter writer = new FileWriter(file, true); // создаем объект FileWriter для записи в файл
                                    writer.write(number + "\n"); // записываем число в файл
                                    writer.flush();
                                    writer.close(); // закрываем файл
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            outputTextView.setText("Ошибка записи в файл: " + e.getMessage()); // выводим сообщение об ошибке в TextView
                                        }
                                    });
                                }
                            }
                        }
                    }
                }
            });
            threads[i].start(); // запускаем поток
        }
        for (Thread thread : threads) {
            try {
                thread.join(); // ожидаем завершения всех потоков
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        outputTextView.setText("Числа записаны в файл."); // выводим сообщение об успешной записи в файл в TextView
    }

    public void readButtonClick(View view) {
        final File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "data_T.txt"); // создаем объект File для чтения из файла
        if (file.exists()) { // если файл существует
            final StringBuilder numbers = new StringBuilder(); // создаем объект StringBuilder для хранения чисел из файла
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file)); // создаем объект BufferedReader для чтения из файла
                String line;
                while ((line = reader.readLine()) != null) { // читаем файл построчно
                    numbers.append(line).append("\n"); // добавляем числа в StringBuilder
                }
                reader.close(); // закрываем файл
                outputTextView.setText("Числа в файле:\n" + numbers.toString()); // выводим числа из файла в TextView
            } catch (IOException e) {
                e.printStackTrace();
                outputTextView.setText("Ошибка чтения файла: " + e.getMessage()); // выводим сообщение об ошибке в TextView
            }
        } else {
            outputTextView.setText("Файл не существует."); // выводим сообщение о том, что файла не существует
        }
    }

    private int generateRandomNumber() {
        Random random = new Random();
        return random.nextInt(36) + 1;
    }
}