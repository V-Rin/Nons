package org.vrin.nons.Storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Класс сохранения скачанных клиентом файлов, их подготовки к преобразованию в URL
 * @autor Валеева Рената, Марк Бочкарев
 * @version 1.1
 * */

@Service
public class FileSystemStorageService implements StorageService{
    /** Поле пути до загруженных файлов*/
    private final Path rootLocation;

    /** Конструктор - создание нового объекта
     * @param properties - объект класса StorageProperties,
     *                   из которого мы получаем путь к загружаемым файлам */
    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    /* Процедура сохранения файла
    * @param file - файл, загруженный клиентом */
    @Override
    public void store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    /* Функция прохождения всего дерева файла по имени для получения его пути
    * @param filename - название искомого файла
    * @return возвращает последовательность путей, ведущих к файлу*/
    @Override
    public Stream<Path> loadStream(String filename) {
        try {
            return Files.walk(this.rootLocation.resolve(filename), 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    /* Функция получения пути файла
    * @param filename - название искомого файла
    * @return возвращает путь до искомого файла*/
    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    /* Функция преобразования файла к ресурсу
    * @param filename - название искомого файла
    * @return возвращает UrlResource*/
    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    /* Процедура рекурсивного удаления файлов*/
    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    /* Процедура создания директории для файлов*/
    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}