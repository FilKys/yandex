package ru.fil.yandex;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

public class Main{
    public static void main(String[] args) throws InterruptedException {
        while (true) {
            try (FileWriter res = new FileWriter("res.cvs", true)) {
                boolean rain = false;
                System.setProperty("webdriver.chrome.driver", "/Users/room/Desktop/git/yandex/chromedriver");
                WebDriver driver = new ChromeDriver();
                String timeStamp = new SimpleDateFormat("MM.dd.yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
                res.write(timeStamp + "\n");
                driver.get("https://yandex.ru");
                WebElement wheather = driver.findElement(By.className("weather__temp"));
                res.write(wheather.getText() + "\n");
                driver.get("https://yandex.ru/pogoda/perm");
                List<WebElement> webElements = driver.findElements(By.className("day-anchor"));
                if(webElements.get(1).getText().contains("дождь")||webElements.get(0).getText().contains("снег")){
                    rain=true;
                }
                driver.get("https://www.google.ru/maps/dir/57.9614163,56.1745576/58.0087631,56.1879164/@57.9847595,56.138158,13z/am=t/data=!3m1!4b1!4m3!4m2!3e0!5i1");
               webElements = driver.findElements(By.className("section-trip-summary-title"));
                for (WebElement time : webElements) {
                    System.out.println(time);
//                    res.write(time.getText() + "\n");
                }
                driver.get("https://www.google.ru/maps/dir/57.986497,56.2914518/58.0087631,56.1879164/@58.0014633,56.1731455,12z/am=t/data=!3m1!4b1!4m3!4m2!3e0!5i1");
                webElements = driver.findElements(By.className("driving-route-view__route-title-primary"));
                for (WebElement time : webElements) {
                    System.out.println(time);
//                    res.write(time.getText() + "\n");
                }
                driver.get("https://www.google.ru/maps/dir/58.0338697,56.3240669/58.0087631,56.1879164/@58.0209894,56.1854751,12z/am=t/data=!3m1!4b1!4m3!4m2!3e0!5i1");
                driver.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Thread.sleep(1000*60*60);
        }
    }
}