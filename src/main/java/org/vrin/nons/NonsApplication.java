package org.vrin.nons;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.vrin.nons.Storage.StorageProperties;
import org.vrin.nons.Storage.StorageService;
import java.io.IOException;

/* Класс веб-приложения, в котором происходит его запуск
* @autor Валеева Рената, Марк Бочкарев
* @version 1.1*/
@SpringBootApplication
@EnableAsync
public class NonsApplication {
    /* Статическая процедура запуска веб-приложения */
    public static void main(String[] args)throws IOException
    {
        SpringApplication.run(NonsApplication.class, args);
    }


    /*Процедура-бин очистки директории сохраненных клиентом файлов
    * @param storageService - объект класса хранения файлов и взаимодействия с ними*/
    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }
}