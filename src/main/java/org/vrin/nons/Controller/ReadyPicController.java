package org.vrin.nons.Controller;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.vrin.nons.Storage.StorageFileNotFoundException;
import org.vrin.nons.Storage.StorageService;
import java.util.stream.Collectors;

/* Класс-контроллер, отвечающий за отображение и взамодействие со страницей с обработанным изображением
* @autor Валеева Рената, Марк Бочкарев
* @version 1.1 */
@AllArgsConstructor
@Controller
public class ReadyPicController {

    /* Поле содержащее в себе объект класса, отвечающего за хранение и обработку полученных файлов*/
    private final StorageService storageService;

    /* Get-запрос, отвечающий за отображение страницы, на которой будет сформированная ссылка на загрузку головоломки*/
    @GetMapping("/readyPic")
    public String getSite(Model model){
        model.addAttribute("files", storageService.loadStream("processedImage.jpg").map(
                path -> MvcUriComponentsBuilder.fromMethodName(ReadyPicController.class, "serveFile", path.getFileName().toString()).build().toUri().toString()).collect(Collectors.toList()));
        storageService.deleteAll();
        return "ReadyPic.html";
    }

    /* Get-запрос, отвечающий за создание ссылки на головоломку*/
    @GetMapping("/readyPic/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    /* Обработчик исключения, связанного с не нахождением файла*/
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<Resource> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}