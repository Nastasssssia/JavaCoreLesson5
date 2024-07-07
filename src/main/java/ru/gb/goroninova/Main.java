package ru.gb.goroninova;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Пример программы для создания резервной копии файлов из указанной директории.
 */
public class Main {
    public static void main(String[] args){
        String sourceDir = "D:\\JavaCore\\Lesson5\\src\\main\\java\\ru\\gb\\goroninova";
        String backupDir = "D:\\JavaCore\\Lesson5\\src\\main\\java\\backup";

        createBackup(sourceDir, backupDir);

    }
    /**
     * Создает резервную копию всех файлов из указанной директории в новую директорию.
     *
     * @param sourceDir исходная директория, из которой нужно создать резервную копию файлов
     * @param backupDir директория для резервной копии
     */
    public static void createBackup(String sourceDir, String backupDir) {
        File sourceDirectory = new File(sourceDir);
        File backupDirectory = new File(backupDir);

        // Создаем директорию для резервного копирования, если она не существует
        if (!backupDirectory.exists()) {
            backupDirectory.mkdirs();
        }

        // Получаем список всех файлов в исходной директории
        File[] files = sourceDirectory.listFiles();

        if (files != null) {
            for (File file : files) {
                Path sourcePath = file.toPath();
                Path backupPath = backupDirectory.toPath().resolve(file.getName());

                try {
                    // Копируем файл в директорию для резервного копирования
                    Files.copy(sourcePath, backupPath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Copied: " + file.getName());
                } catch (IOException e) {
                    System.err.println("Failed to copy: " + file.getName());
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("No files to backup in the directory.");
        }
    }
}
