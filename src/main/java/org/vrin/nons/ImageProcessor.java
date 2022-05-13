package org.vrin.nons;

import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;


//пока не законченный класс, который будет обрабатывать получаемое изображение и преобразовывать его в нонограмм
class ImageProcess {
    File output = new File("Nons/input/processed_IMG.png"); //позже будет заменено
    BufferedImage result;
    Color[][] grid = new Color[20][20];

    public File processIMG(File input) {
        try {
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
                        if (blue + red + green < 328) {
                            newColor = new Color(0, 0, 0);
                        } else {
                            newColor = new Color(255, 255, 255);
                        }
                        result.setRGB(x, y, newColor.getRGB());
                    }
                }
                //ImageIO.write(result, "png", output);
            } else if (source.getHeight() >= source.getWidth()) {
                this.result = new BufferedImage(source.getWidth(), source.getWidth(), source.getType());

                for (int x = 0; x < source.getWidth(); x++) {
                    for (int y = 0; y < source.getWidth(); y++) {
                        Color color = new Color(source.getRGB(x, y));

                        int blue = color.getBlue();
                        int red = color.getRed();
                        int green = color.getGreen();

                        Color newColor;
                        if (blue + red + green < 328) {
                            newColor = new Color(0, 0, 0);
                        } else {
                            newColor = new Color(255, 255, 255);
                        }
                        result.setRGB(x, y, newColor.getRGB());

                    }
                }
                //ImageIO.write(result, "png", output);
            }

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

            int sum = 0;
            for (int j = 0; j < 20; j++) {
                for (int i = 0; i < 20; i++) {
                    for (int y = 0; y < claster; y++) {
                        for (int x = 0; x < claster; x++) {
                            sum += fullMatrix[claster * i + x][claster * j + y].getRed();
                        }
                    }

                    if (sum > ((claster * claster) / 2) * 255) {
                        this.grid[i][j] = new Color(255, 255, 255);
                    } else this.grid[i][j] = new Color(0, 0, 0);
                    System.out.println("grid [" + i + "]" + "[" + j + "] = " + grid[i][j]);
                }
            }
        } catch (IOException e) {
            System.out.println("Не удалось найти или сохранить изображение");
        }
        return output;
    }
}
