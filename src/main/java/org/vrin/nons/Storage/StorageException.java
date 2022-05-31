package org.vrin.nons.Storage;

/*
 * Класс-исключение, связанный с классом хранения и обработки полученных файлов
 * Исключение касается ситуаций, когда происходит какая-либо ошибка чтения или если директория не может быть создана
 * @autor Валеева Рената, Марк Бочкарев
 * @version 1.1
 */
public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
