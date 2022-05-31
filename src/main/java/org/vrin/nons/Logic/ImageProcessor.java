package org.vrin.nons.Logic;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

/*
 * Класс перобразования изображения в нонограмм: получение предварительного изображения (первью) и головоломки
 * @autor Валеева Рената, Марк Бочкарев
 * @version 1.1
 */

@Service
public class ImageProcessor {
    /** Поле файл-головоломка*/
    private File output = new File("../Nons/src/main/resources/static/Uploaded/processedImage.jpg");

    /** Поле файл-превью*/
    private File preview = new File("../Nons/src/main/resources/static/Uploaded/preview.jpg");

    /** Поле временного сохранения результата изображения*/
    private BufferedImage result;

    /** Поле массив для сохранения значения цвета у будущего нонограмма: true - белый, false - черный */
    private boolean[][] grid= new boolean[20][20];


    /** Процедура создания файла-головоломки
     * @param input - входное изображение*/
    private void processIMG(File input) {
        try {
            output.createNewFile();

            monochromeProcess(input);
            fillGrid(this.result);

            Stack<Integer> [] columns = fillColumns();
            Stack<Integer> [] rows = fillRows();

            BufferedImage largeGrid = Elements.LARGE_GRID.getImg();
            createPreview();

            Elements.generateMap();

            for(int p = 0; p < 20; p++) //заполнение строк чисел
            {
                int sector = 0;

                rows[p].pop();
                while(!rows[p].empty())
                {
                    BufferedImage value;
                    int temp = rows[p].pop();

                    Elements elem = Elements.getElement(temp%10);
                    value = elem.getImg();
                    for(int y = 0; y < 36; y++)
                    {
                        for(int x = 0; x < 36; x++)
                        {
                            largeGrid.setRGB( 360-(sector+1)*36+x, 360+(p)*36+y, value.getRGB(x, y));
                        }
                    }

                    if(temp>9)
                    {
                        if(temp==20)
                        {
                            elem = Elements.getElement(20);
                        }
                        else
                        {
                            elem = Elements.getElement(10);
                        }
                        value = elem.getImg();
                        for(int y = 0; y < 36; y++)
                        {
                            for(int x = 0; x < 18; x++)
                            {
                                largeGrid.setRGB( 360-(sector+1)*36+x, 360+(p)*36+y, value.getRGB(x, y));
                            }
                        }
                    }
                    sector++;
                }
            }

            for(int t = 0; t < 20; t++) //заполнение столбцов чисел
            {
                int sector = 0;

                columns[t].pop();
                while(!columns[t].empty())
                {
                    BufferedImage value;
                    int temp = columns[t].pop();

                    Elements elem = Elements.getElement(temp%10);
                    value = elem.getImg();
                    for(int y = 0; y < 36; y++)
                    {
                        for(int x = 0; x < 36; x++)
                        {
                            largeGrid.setRGB( 360+(t)*36+x, 360-(sector+1)*36+y, value.getRGB(x, y));
                        }
                    }

                    if(temp>9)
                    {
                        if(temp==20)
                        {
                            elem = Elements.getElement(20);
                        }
                        else
                        {
                            elem = Elements.getElement(10);
                        }
                        value = elem.getImg();
                        for(int y = 0; y < 36; y++)
                        {
                            for(int x = 0; x < 18; x++)
                            {
                                largeGrid.setRGB( 360+(t)*36+x,360-(sector+1)*36+y , value.getRGB(x, y));
                            }
                        }
                    }
                    sector++;
                }
            }

            ImageIO.write(largeGrid, "png", output);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Процедура преобразования входной картинки в черно-белое изображение
     * @param input - входное изображение*/
    private void monochromeProcess(File input)
    {
        try
        {
            BufferedImage source = ImageIO.read(input);
            if (source.getHeight() < source.getWidth()) {
                this.result = new BufferedImage(source.getHeight(), source.getHeight(), source.getType());

                for (int x = 0; x < source.getHeight(); x++) {
                    for (int y = 0; y < source.getHeight(); y++) {
                        Color color = new Color(source.getRGB(x, y));

                        int blue = color.getBlue();
                        int red = color.getRed();
                        int green = color.getGreen();

                        Color newColor;
                        if (blue + red + green < 400) {
                            newColor = new Color(0, 0, 0);
                        } else {
                            newColor = new Color(255, 255, 255);
                        }
                        result.setRGB(x, y, newColor.getRGB());
                    }
                }
            } else if (source.getHeight() >= source.getWidth()) {
                this.result = new BufferedImage(source.getWidth(), source.getWidth(), source.getType());

                for (int x = 0; x < source.getWidth(); x++) {
                    for (int y = 0; y < source.getWidth(); y++) {
                        Color color = new Color(source.getRGB(x, y));

                        int blue = color.getBlue();
                        int red = color.getRed();
                        int green = color.getGreen();

                        Color newColor;
                        if (blue + red + green < 400) {
                            newColor = new Color(0, 0, 0);
                        } else {
                            newColor = new Color(255, 255, 255);
                        }
                        result.setRGB(x, y, newColor.getRGB());

                    }
                }

            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    /** Процедура заполнения поля {@link ImageProcessor#grid}*/
    private void fillGrid(BufferedImage result)
    {
        int claster = (result.getHeight() - 1) / 20 + 1;
        Color[][] fullMatrix = new Color[claster * 20][claster * 20];
        for (int x = 0; x < claster * 20; x++) {
            for (int y = 0; y < claster * 20; y++) {
                fullMatrix[x][y] = new Color(255, 255, 255);
            }
        }

        for (int x = 0; x < result.getWidth(); x++) {
            for (int y = 0; y < result.getHeight(); y++) {
                Color color = new Color(result.getRGB(x, y));
                fullMatrix[x][y] = color;
            }
        }

        for (int j = 0; j < 20; j++) {
            for (int i = 0; i < 20; i++) {
                int sum = 0;
                for (int y = 0; y < claster; y++) {
                    for (int x = 0; x < claster; x++) {
                        sum += fullMatrix[claster * i + x][claster * j + y].getRed();
                    }
                }

                if (sum > ((claster * claster) / 2) * 255) {
                    this.grid[i][j] = true;
                } else this.grid[i][j] = false;
            }
        }
    }

    /** Функция заполнения массива стеков, каждый стек - строка чисел, соответствующая черным секторам подряд
     * @return возвращает массив стеков соответствующий строкам нонограма*/
    private Stack<Integer> [] fillColumns()
    {
        Stack<Integer> [] numbers = new Stack[20];

        for(int i = 0; i < 20; i++)
        {
            numbers[i] = new Stack<>();
            numbers[i].push(0);
        }

        for(int i = 0; i < 20; i++) //строки
        {
            for(int j = 0; j < 20; j++)//ячейки
            {
                if(this.grid[i][j]==false)
                {
                    numbers[i].push(numbers[i].pop()+1);
                }
                else if(numbers[i].peek()!=0)
                {
                    numbers[i].push(0);
                }
            }
        }
        return numbers;
    }
    /** Функция заполнения массива стеков, каждый стек - строка чисел, соответствующая черным секторам подряд
     * @return возвращает массив стеков соответствующий столбцам нонограма*/
    private Stack<Integer> [] fillRows()
    {
        Stack<Integer> [] numbers = new Stack[20];

        for(int i = 0; i < 20; i++)
        {
            numbers[i] = new Stack<>();
            numbers[i].push(0);
        }

        for(int i = 0; i < 20; i++) //строки
        {
            for(int j = 0; j < 20; j++)//ячейки
            {
                if(this.grid[i][j]==false)
                {
                    numbers[j].push(numbers[j].pop()+1);
                }
                else if(numbers[j].peek()!=0)
                {
                    numbers[j].push(0);
                }
            }
        }

        for(Stack<Integer> x : numbers){
            if (x.peek() != 0) x.push(0);
        }
        return numbers;
    }

    /** Процедура создания превью {@link ImageProcessor#preview*/
    private void createPreview()
    {
        try
        {
            BufferedImage gridImg = Elements.GRID.getImg();
            BufferedImage darkSquare = Elements.DARK_SQUARE.getImg();

            for(int i = 0; i < 20; i++)
            {
                for(int j = 0; j < 20; j++)
                {
                    if(this.grid[i][j]==false)
                    {
                        for(int y = 0; y < 36; y++)
                        {
                            for(int x = 0; x < 36; x++)
                            {
                                gridImg.setRGB(i*36+x, j*36+y, darkSquare.getRGB(x, y));
                            }
                        }
                    }
                }
            }
            ImageIO.write(gridImg, "png", preview);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**Функция-геттер для поля {@link ImageProcessor#output}
     * @param input - значение входного файла
     * @return возвращает значение поля output */
    public File getOutput(File input) {
        processIMG(input);
        return output;
    }
    /**Функция-геттер для поля {@link ImageProcessor#preview}
     * @return возвращает значение поля preview */
    public File getPreview()
    {
        return preview;
    }
}