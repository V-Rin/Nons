package org.vrin.nons.Storage;

import org.springframework.stereotype.Component;

/*
 * Класс хранения пути до директории, куда клиент будет скачивать файл
 * @autor Валеева Рената, Марк Бочкарев
 * @version 1.1
 */
@Component
public class StorageProperties {

    /* Поле пути до директории*/
    private String location = "../Nons/src/main/resources/static/Uploaded";

    /* Функция-геттер получения пути*/
    public String getLocation() {
        return location;
    }

    /* Функция-сеттер для установки значения пути*/
    public void setLocation(String location) {
        this.location = location;
    }

}