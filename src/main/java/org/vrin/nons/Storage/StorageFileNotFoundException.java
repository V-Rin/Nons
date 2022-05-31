package org.vrin.nons.Storage;

/*
 * Класс-исключение, связанный с классом хранения и обработки полученных файлов
 * Исключение касается ситуаций, когда файл не может быть найден
 * @autor Валеева Рената, Марк Бочкарев
 * @version 1.1
 */
public class StorageFileNotFoundException extends StorageException {

    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}