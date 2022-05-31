package org.vrin.nons.Logic;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/** Enum-класс картинок для создания нонограмма
 * @autor Валеева Рената, Марк Бочкарев
 * @version 1.1
 * */

enum Elements{
    LARGE_GRID("../Nons/src/main/resources/static/Elements/LargeGrid.png"),
    DARK_SQUARE("../Nons/src/main/resources/static/Elements/darkSquare.png"),
    ONE("../Nons/src/main/resources/static/Elements/one.png", 1), TWO("../Nons/src/main/resources/static/Elements/two.png", 2),
    THREE("../Nons/src/main/resources/static/Elements/three.png", 3), FOUR("../Nons/src/main/resources/static/Elements/four.png", 4),
    FIVE("../Nons/src/main/resources/static/Elements/five.png", 5), SIX("../Nons/src/main/resources/static/Elements/six.png", 6),
    SEVEN("../Nons/src/main/resources/static/Elements/seven.png", 7), EIGHT("../Nons/src/main/resources/static/Elements/eight.png", 8),
    NINE("../Nons/src/main/resources/static/Elements/nine.png", 9), TEN("../Nons/src/main/resources/static/Elements/ten.png", 10),
    TWENTY("../Nons/src/main/resources/static/Elements/twenty.png", 20), ZERO("../Nons/src/main/resources/static/Elements/zero.png", 0),
    GRID("../Nons/src/main/resources/static/Elements/grid.png");

    /*Поле HashMap для доступа к картинкам чисел по их номеру */
    private static HashMap<Integer, Elements> values = new HashMap<>();

    /* Поле хранения значения пути картинок */
    private String path;

    /*Поле int значения числа для доступа через HashMap*/
    private int value;

    /*Поле хранения картинки для каждого числа*/
    private BufferedImage img;

    /* Конструктор для не числовых картинок - сеток, темного квадрата
    * @param path - значение пути картинки */
    Elements(String path){
        try{
            this.path=path;
            this.img = ImageIO.read(new File(this.path));
        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /* Конструктор для числовых картинок
     * @param path - значение пути картинки
     * @param value - числовое значение, ключ для HashMap */
    Elements(String path, int value){
        try{
            this.path = path;
            this.value = value;
            this.img = ImageIO.read(new File(this.path));
        }catch(IOException e)
        {
           e.printStackTrace();
        }
    }

    /* Функция-геттер для получения картинки элемента
    * @return - возвращает значение поля {@link Elements#img} */
    public BufferedImage getImg()
    {
        return img;
    }

    /* Статическая процедура заполнения HashMap*/
    static void generateMap()
    {
        values.put(0, ZERO);
        values.put(1, ONE);
        values.put(2, TWO);
        values.put(3, THREE);
        values.put(4, FOUR);
        values.put(5, FIVE);
        values.put(6, SIX);
        values.put(7, SEVEN);
        values.put(8, EIGHT);
        values.put(9, NINE);
        values.put(10, TEN);
        values.put(20, TWENTY);
    }

    /* Функция возвращения элемента из HashMap
    * @param value - ключ для доступа к определенному элементу
    * @return возвращает значение выбранного элемента*/
    public static Elements getElement(int value)
    {
        return values.get(value);
    }

    /* Функия-геттер возвращения поля value*/
    public int getValue()
    {
        return this.value;
    }
}