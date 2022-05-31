package org.vrin.nons.Controller;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.vrin.nons.Logic.ImageProcessor;
import org.vrin.nons.Storage.StorageService;
import java.io.*;

/* Класс-контроллер, отвечающий за отображение и взамодействие со страницей, где должна проходить загрузка файлов
 * @autor Валеева Рената, Марк Бочкарев
 * @version 1.1 */
@AllArgsConstructor
@Controller
public class IndexController {

    /* Поле содержащее в себе объект класса, отвечающего за хранение и обработку полученных файлов*/
    private final StorageService storageService;

    /* Поле содержащее в себе объект класса, отвечающего за преобразование картинки в нонограмм*/
    private final ImageProcessor imageProcessor;

    /* Get-запрос, отвечающий за отображение основной страницы*/
    @GetMapping("/")
    public String getSite(Model model){
        model.addAttribute("title","mainPage");
        return "nons.html";
    }

    /* Post-запрос, отвечающий за загрузку файла пользователем, его преобразование и
    перенаправление на страницу с загрузкой преобразованного изображения*/
    @PostMapping("/")
    public String postSite(@RequestParam("file") MultipartFile file, Model model){
        try {
            storageService.store(file);

            File processedFile = new File("../Nons/src/main/resources/static/Uploaded/processedImage.jpg");
            File preview = new File("../Nons/src/main/resources/static/Uploaded/preview.jpg");

            OutputStream os = new FileOutputStream(processedFile);
            os.write(file.getBytes());

            OutputStream os2 = new FileOutputStream(preview);
            os2.write(file.getBytes());

            imageProcessor.getOutput(processedFile);
            preview = imageProcessor.getPreview();
            preview.delete();
            processedFile.delete();
            model.addAttribute("message",
                    "You successfully uploaded " + file.getOriginalFilename() + "!");
        }catch(IOException e)
        {
            e.printStackTrace();
        }
        return "redirect:/readyPic";
    }
}