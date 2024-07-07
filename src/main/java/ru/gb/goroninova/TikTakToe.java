package ru.gb.goroninova;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Класс для демонстрации сохранения и загрузки состояния поля игры "Крестики-нолики" в файл.
 */
public class TikTakToe {
    public static void main (String[]arg){

        // Пример массива состояний поля 3x3
        int[] field = { 0, 1, 2, 2, 1, 0, 3, 3, 0};

        String fileName = "field_backup.bin"; // Имя файла для сохранения и загрузки данных

        try {
            saveFieldToFile(field, fileName); // Сохранение данных в файл
            System.out.println("Field saved successfully.");

            // Проверка размера файла
            File file = new File(fileName);
            if (file.exists()) {
                System.out.println("File size: " + file.length() + " bytes");
            }

            int[] loadedField = loadFieldFromFile(fileName);
            System.out.println("Field loaded successfully.");

            // Проверка загруженного поля
            for (int i = 0; i < loadedField.length; i++) {
                System.out.print(loadedField[i] + " ");
                if ((i + 1) % 3 == 0) {
                    System.out.println();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Сохраняет состояние поля игры "Крестики-нолики" в файл.
     *
     * @param field    массив из 9 элементов, представляющий состояния поля 3x3
     * @param fileName имя файла для сохранения данных
     */

    public static void saveFieldToFile(int[] field, String fileName) throws IOException {
        if (field.length != 9) {
            throw new IllegalArgumentException("Field must contain exactly 9 elements.");
        }

        int packedValue = 0;

        // Упаковываем 9 значений в одно число типа int
        for (int i = 0; i < field.length; i++) {
            // Сдвигаем текущее значение на 2 * i бит влево и объединяем с packedValue
            packedValue |= (field[i] << (2 * i));
        }

        // Записываем это число в файл в виде 3 байт
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write((packedValue >> 16) & 0xFF); // Записываем старший байт (биты 16-23)
            fos.write((packedValue >> 8) & 0xFF);  // Записываем средний байт (биты 8-15)
            fos.write(packedValue & 0xFF);         // Записываем младший байт (биты 0-7)
        }
    }

    /**
     * Загружает состояние поля игры "Крестики-нолики" из файла.
     *
     * @param fileName имя файла для загрузки данных
     * @return массив из 9 элементов, представляющий состояния поля 3x3
     */

    public static int[] loadFieldFromFile(String fileName) throws IOException {
        int packedValue = 0;

        // Читаем 3 байта из файла
        try (FileInputStream fis = new FileInputStream(fileName)) {
            packedValue |= (fis.read() << 16); // Читаем старший байт (биты 16-23)
            packedValue |= (fis.read() << 8);  // Читаем средний байт (биты 8-15)
            packedValue |= fis.read();         // Читаем младший байт (биты 0-7)
        }

        int[] field = new int[9];

        // Распаковываем 9 значений из упакованного числа
        for (int i = 0; i < field.length; i++) {
            field[i] = (packedValue >> (2 * i)) & 0x3;
        }

        return field;
    }
}
