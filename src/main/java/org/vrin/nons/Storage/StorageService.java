package org.vrin.nons.Storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.util.stream.Stream;

/* Интерфейс для создания на его основе класса, отвечающего за хранения полученных файлов и взаимодействия с ними
* @autor Валеева Рената, Марк Бочкарев
* @version 1.1 */

public interface StorageService {

    /* Процедура создания директории для файлов*/
    void init();

    /* Процедура сохранения файла
     * @param file - файл, загруженный клиентом */
    void store(MultipartFile file);

    /* Функция получения пути файла
     * @param filename - название искомого файла
     * @return возвращает путь до искомого файла*/
    Path load(String filename);

    /* Функция прохождения всего дерева файла по имени для получения его пути
     * @param filename - название искомого файла
     * @return возвращает последовательность путей, ведущих к файлу*/
    Stream<Path> loadStream(String s);

    /* Функция преобразования файла к ресурсу
     * @param filename - название искомого файла
     * @return возвращает UrlResource*/
    Resource loadAsResource(String filename);

    /* Процедура рекурсивного удаления файлов*/
    void deleteAll();

}