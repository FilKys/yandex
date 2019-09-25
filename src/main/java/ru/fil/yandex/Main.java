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
                driver.get("https://yandex.ru/maps/50/perm/?ll=56.184412%2C57.984972&mode=routes&rtext=" +
                        "57.961349%2C56.172858~58.008390%2C56.188022&rtt=auto&ruri=ymapsbm1%3A%2F%2Fgeo%3Fll%3D56.173%252C57.961%26spn%3D0.001%252C0.001%26text" +
                        "%3D%25D0%25A0%25D0%25BE%25D1%2581%25D1%2581%25D0%25B8%25D1%258F%252C%2520%25D0%259F%25D0%25B5%25D1%2580%25D0%25BC%25D1%258C%252C%2520%" +
                        "25D1%2583%25D0%25BB%25D0%25B8%25D1%2586%25D0%25B0%2520%25D0%2590%25D1%2580%25D1%2585%25D0%25B8%25D1%2582%25D0%25B5%25D0%25BA%25D1%2582" +
                        "%25D0%25BE%25D1%2580%25D0%25B0%2520%25D0%25A1%25D0%25B2%25D0%25B8%25D1%258F%25D0%25B7%25D0%25B5%25D0%25B2%25D0%25B0%252C%252046%252F2~" +
                        "ymapsbm1%3A%2F%2Fgeo%3Fll%3D56.188%252C58.008%26spn%3D0.001%252C0.001%26text%3D%25D0%25A0%25D0%25BE%25D1%2581%25D1%2581%25D0%25B8%25D1" +
                        "%258F%252C%2520%25D0%259F%25D0%25B5%25D1%2580%25D0%25BC%25D1%258C%252C%2520%25D1%2583%25D0%25BB%25D0%25B8%25D1%2586%25D0%25B0%2520%25D" +
                        "0%2591%25D1%2583%25D0%25BA%25D0%25B8%25D1%2580%25D0%25B5%25D0%25B2%25D0%25B0%252C%252015&z=13");
                webElements = driver.findElements(By.className("driving-route-view__route-title-primary"));
                for (WebElement time : webElements) {
                    res.write(time.getText() + "\n");
                }
                driver.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Thread.sleep(1000*60*60);
        }
    }
}