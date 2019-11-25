package ru.fil.yandex;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class Main {

    private static WebDriver driver;

    public static void main(String[] args) throws InterruptedException {
        FileInputStream inputXlsx;
        FileOutputStream outXlsx;
        XSSFWorkbook res;
        XSSFSheet sheet;
        int lastRow, lastcell, i = 0;
        Row row;
        List<String> sites = new ArrayList();
        sites.add("https://yandex.ru/maps/50/perm/?ll=56.174791%2C57.981587&mode=routes&rtext=57.961349%2C56.172858~57.971996%2C56.181868~58.008390%2C56.188022&rtt=auto&ruri=ymapsbm1%3A%2F%2Fgeo%3Fll%3D56.173%252C57.961%26spn%3D0.001%252C0.001%26text%3D%25D0%25A0%25D0%25BE%25D1%2581%25D1%2581%25D0%25B8%25D1%258F%252C%2520%25D0%259F%25D0%25B5%25D1%2580%25D0%25BC%25D1%258C%252C%2520%25D1%2583%25D0%25BB%25D0%25B8%25D1%2586%25D0%25B0%2520%25D0%2590%25D1%2580%25D1%2585%25D0%25B8%25D1%2582%25D0%25B5%25D0%25BA%25D1%2582%25D0%25BE%25D1%2580%25D0%25B0%2520%25D0%25A1%25D0%25B2%25D0%25B8%25D1%258F%25D0%25B7%25D0%25B5%25D0%25B2%25D0%25B0%252C%252046%252F2~~ymapsbm1%3A%2F%2Fgeo%3Fll%3D56.188%252C58.008%26spn%3D0.001%252C0.001%26text%3D%25D0%25A0%25D0%25BE%25D1%2581%25D1%2581%25D0%25B8%25D1%258F%252C%2520%25D0%259F%25D0%25B5%25D1%2580%25D0%25BC%25D1%258C%252C%2520%25D1%2583%25D0%25BB%25D0%25B8%25D1%2586%25D0%25B0%2520%25D0%2591%25D1%2583%25D0%25BA%25D0%25B8%25D1%2580%25D0%25B5%25D0%25B2%25D0%25B0%252C%252015&z=12");
        sites.add("https://yandex.ru/maps/50/perm/?ll=56.238265%2C57.998537&mode=routes&rtext=57.986516%2C56.291370~58.011432%2C56.237665~58.008390%2C56.188022&rtt=auto&ruri=~~ymapsbm1%3A%2F%2Fgeo%3Fll%3D56.188%252C58.008%26spn%3D0.001%252C0.001%26text%3D%25D0%25A0%25D0%25BE%25D1%2581%25D1%2581%25D0%25B8%25D1%258F%252C%2520%25D0%259F%25D0%25B5%25D1%2580%25D0%25BC%25D1%258C%252C%2520%25D1%2583%25D0%25BB%25D0%25B8%25D1%2586%25D0%25B0%2520%25D0%2591%25D1%2583%25D0%25BA%25D0%25B8%25D1%2580%25D0%25B5%25D0%25B2%25D0%25B0%252C%252015&z=13");
        sites.add("https://yandex.ru/maps/50/perm/?ll=56.248786%2C58.002883&mode=routes&rtext=58.033940%2C56.324029~58.011432%2C56.237665~58.008390%2C56.188022&rtt=auto&ruri=~~ymapsbm1%3A%2F%2Fgeo%3Fll%3D56.188%252C58.008%26spn%3D0.001%252C0.001%26text%3D%25D0%25A0%25D0%25BE%25D1%2581%25D1%2581%25D0%25B8%25D1%258F%252C%2520%25D0%259F%25D0%25B5%25D1%2580%25D0%25BC%25D1%258C%252C%2520%25D1%2583%25D0%25BB%25D0%25B8%25D1%2586%25D0%25B0%2520%25D0%2591%25D1%2583%25D0%25BA%25D0%25B8%25D1%2580%25D0%25B5%25D0%25B2%25D0%25B0%252C%252015&z=13");
        List<WebElement> webElements;
        WebElement webElement;
        ChromeOptions opt = new ChromeOptions();
        opt.addArguments("headless");
        while (true) {
            System.setProperty("webdriver.chrome.driver", "/home/samsung/chromedriver");
            driver = new ChromeDriver(opt);
//            driver = new ChromeDriver();
            try {
                lastcell = 0;
                inputXlsx = new FileInputStream("res.xlsx");
                res = new XSSFWorkbook(inputXlsx);
                sheet = res.getSheetAt(0);
                lastRow = sheet.getLastRowNum();
                row = sheet.createRow(lastRow + 1);
                String timeStamp = new SimpleDateFormat("MM.dd.yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
                row.createCell(lastcell).setCellValue(timeStamp);
                System.out.println(timeStamp);
                driver.get("https://yandex.ru");
                webElement = fluentWait(By.className("weather__temp"));
                row.createCell(++lastcell).setCellValue(webElement.getText().substring(0, webElement.getText().length() - 1));
                System.out.println(webElement.getText());
                driver.get("https://yandex.ru/pogoda/perm");
                webElement = fluentWait(By.className("day-anchor"));
                if (webElement.getText().contains("дождь") || webElement.getText().contains("снег")) {
                    row.createCell(++lastcell).setCellValue(1);
                } else {
                    row.createCell(++lastcell).setCellValue(0);
                }
                for (String site : sites) {
                    driver.get(site);
                    Thread.sleep(1000);
                    webElement = fluentWait(By.className("auto-route-form-view__route-title-primary"));
                    row.createCell(++lastcell).setCellValue(webElement.getText().split(" ")[0]);
                    System.out.println(webElement.getText());
                }
                inputXlsx.close();
                outXlsx = new FileOutputStream("res.xlsx");
                res.write(outXlsx);
                outXlsx.close();
                res.close();
                driver.close();
            } catch (IOException e) {
                e.printStackTrace();
                driver.close();
            }
            System.out.println("Program sleep : 1 h");
            Thread.sleep(1000 * 60 * 60);
        }
    }

    public static WebElement fluentWait(final By locator) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(50, TimeUnit.SECONDS)
                .pollingEvery(10, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);

        WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElements(locator).get(0);
            }
        });

        return foo;
    }

    ;
}