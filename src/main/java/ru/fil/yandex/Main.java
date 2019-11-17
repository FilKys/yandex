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

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        FileInputStream inputXlsx;
        FileOutputStream outXlsx;
        XSSFWorkbook res;
        XSSFSheet sheet;
        int lastRow, lastcell;
        Row row;
        List<WebElement> webElements;
        WebElement webElement;
        WebDriver driver;
        ChromeOptions opt = new ChromeOptions();
        opt.addArguments("headless");
        while (true) {
            System.setProperty("webdriver.chrome.driver", "//home/room/chromedriver");
            driver = new ChromeDriver(opt);
            try {
                lastcell = 0;
                inputXlsx = new FileInputStream("res.xlsx");
                res = new XSSFWorkbook(inputXlsx);
                sheet = res.getSheetAt(0);
                lastRow = sheet.getLastRowNum();
                row = sheet.createRow(lastRow + 1);
                boolean rain = false;
                String timeStamp = new SimpleDateFormat("MM.dd.yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
                row.createCell(lastcell).setCellValue(timeStamp);
                System.out.println(timeStamp);
                driver.get("https://yandex.ru");
                webElement = driver.findElement(By.className("weather__temp"));
                row.createCell(++lastcell).setCellValue(webElement.getText().substring(0, webElement.getText().length() - 1));
                System.out.println(webElement.getText());
                driver.get("https://yandex.ru/pogoda/perm");
                webElement = driver.findElement(By.className("day-anchor"));
                if (webElement.getText().contains("дождь") || webElement.getText().contains("снег")) {
                    rain = true;
                }
                if (rain) {
                    row.createCell(++lastcell).setCellValue(1);
                } else {
                    row.createCell(++lastcell).setCellValue(0);
                }
                driver.get("https://www.google.ru/maps/dir/57.9614163,56.1745576/58.0087631,56.1879164/@57.9847595,56.138158,13z/am=t/data=!3m1!4b1!4m3!4m2!3e0!5i1");
                webElements = driver.findElements(By.className("day-anchor"));
                while (webElements.size() == 0) {
                    webElements = driver.findElements(By.className("delay-light"));
                }
                if(webElements.get(0).getText().split(" ")[0].length()==0)
                {
                    System.out.println(1);
                    Thread.sleep(1000);
//                    System.out.println(webElements.get(0).getText());
//                    System.out.println(webElements.get(1).getText());
                }
                row.createCell(++lastcell).setCellValue(webElements.get(0).getText().split(" ")[0]);
                System.out.println(webElements.get(0).getText());
                driver.get("https://www.google.ru/maps/dir/57.986497,56.2914518/58.0087631,56.1879164/@58.0014633,56.1731455,12z/am=t/data=!3m1!4b1!4m3!4m2!3e0!5i1");
                webElements = driver.findElements(By.className("delay-light"));
                while (webElements.size() == 0) {
                    webElements = driver.findElements(By.className("delay-light"));
                }
                if(webElements.get(0).getText().split(" ")[0].length()==0)
                {
                    System.out.println(2);
                    Thread.sleep(1000);
//                    System.out.println(webElements.get(0).getText());
//                    System.out.println(webElements.get(1).getText());
                }
                row.createCell(++lastcell).setCellValue(webElements.get(0).getText().split(" ")[0]);
                System.out.println(webElements.get(0).getText());
                driver.get("https://www.google.ru/maps/dir/58.0338697,56.3240669/58.0087631,56.1879164/@58.0209894,56.1854751,12z/am=t/data=!3m1!4b1!4m3!4m2!3e0!5i1");
                webElements = driver.findElements(By.className("delay-light"));
                while (webElements.size() == 0) {
                    webElements = driver.findElements(By.className("delay-light"));
                }
                if(webElements.get(0).getText().split(" ")[0].length()==0)
                {
                    System.out.println(3);
                    Thread.sleep(1000);
//                    System.out.println(webElements.get(0).getText());
//                    System.out.println(webElements.get(1).getText());
                }
                row.createCell(++lastcell).setCellValue(webElements.get(0).getText().split(" ")[0]);
                System.out.println(webElements.get(0).getText());
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
            Thread.sleep(1000 * 60 * 60);
        }
    }
}