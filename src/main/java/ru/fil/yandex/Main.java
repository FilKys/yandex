package ru.fil.yandex;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class Main {

    private static WebDriver driver;
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        logger.info("Start");
        FileInputStream inputXlsx;
        FileOutputStream outXlsx;
        XSSFWorkbook res;
        XSSFSheet sheet;
        int lastRow, lastcell, i = 0;
        Row row;
        List<String> sitesList = initSites();
        List<String> webElemList = initElem();
        WebElement webElement;
        ChromeOptions opt = new ChromeOptions();
        opt.addArguments("headless");
        while (true) {
            System.setProperty("webdriver.chrome.driver", "/home/samsung/chromedriver"); //Linux
//            System.setProperty("webdriver.chrome.driver", "G:\\chromedriver.exe"); //Win
            driver = new ChromeDriver(opt);
            logger.info("Open Google chrome");
            driver = new ChromeDriver();
            try {
                lastcell = 0;
                logger.info("Open input stream to res.xlsx");
                inputXlsx = new FileInputStream("res.xlsx");
                res = new XSSFWorkbook(inputXlsx);
                sheet = res.getSheetAt(0);
                lastRow = sheet.getLastRowNum();
                row = sheet.createRow(lastRow + 1);
                //Дата и время
                String timeStamp = new SimpleDateFormat("MM.dd.yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
                logger.info("Save time stamp - " + timeStamp);
                row.createCell(lastcell).setCellValue(timeStamp);
                //Температура и погода
                logger.info("Open site - https://yandex.ru/pogoda/perm ");
                driver.get("https://yandex.ru/pogoda/perm");
                logger.info("Get web element - " + webElemList.get(0));
                webElement = fluentWait(By.className(webElemList.get(0)));
                logger.info("Save pogoda - " + webElement.getText().substring(0, webElement.getText().indexOf("\n")));
                row.createCell(++lastcell).setCellValue(webElement.getText().substring(0, webElement.getText().indexOf("\n") - 1));
                if (webElement.getText().contains("дождь") || webElement.getText().contains("снег")) {
                    row.createCell(++lastcell).setCellValue(1);
                } else {
                    row.createCell(++lastcell).setCellValue(0);
                }
                i = 1;
                for (String site : sitesList) {
                    try {
                        i++;
                        logger.info("Open site - " + site);
                        driver.get(site);
                        Thread.sleep(1000);
                        logger.info("Get web element - " + webElemList.get(1));
                        webElement = fluentWait(By.className(webElemList.get(1)));
                        logger.info("Save time on road " + i + " - " + webElement.getText());
                        row.createCell(++lastcell).setCellValue(webElement.getText().split(" ")[0]);
                    } catch (Exception e) {
                        logger.error(e.getStackTrace());
                    }
                }
                logger.info("Close input stream to res.xlsx");
                inputXlsx.close();
                outXlsx = new FileOutputStream("res.xlsx");
                res.write(outXlsx);
                outXlsx.close();
                res.close();
            } catch (Exception e) {
                logger.error(e.getStackTrace());
            } finally {
                logger.info("Close google chrome");
                driver.close();
            }
            logger.info("Program sleep - 1 h");
            Thread.sleep(1000 * 60 * 60);
        }
    }

    private static List<String> initElem() {
        List<String> elem = new ArrayList<>();
        //Температура
        //Погода
        elem.add("day-anchor");
        //Время в пути
        elem.add("auto-route-form-view__route-title-primary");
        return elem;
    }

    private static List<String> initSites() {
        List<String> sites = new ArrayList<>();
        //Свиязева 46/2 - Букирева 15
        sites.add("https://yandex.ru/maps/50/perm/?ll=56.174791%2C57.981587&mode=routes&rtext=57.961349%2C56.172858~57.971996%2C56.181868~58.008390%2C56.188022&rtt=auto&ruri=ymapsbm1%3A%2F%2Fgeo%3Fll%3D56.173%252C57.961%26spn%3D0.001%252C0.001%26text%3D%25D0%25A0%25D0%25BE%25D1%2581%25D1%2581%25D0%25B8%25D1%258F%252C%2520%25D0%259F%25D0%25B5%25D1%2580%25D0%25BC%25D1%258C%252C%2520%25D1%2583%25D0%25BB%25D0%25B8%25D1%2586%25D0%25B0%2520%25D0%2590%25D1%2580%25D1%2585%25D0%25B8%25D1%2582%25D0%25B5%25D0%25BA%25D1%2582%25D0%25BE%25D1%2580%25D0%25B0%2520%25D0%25A1%25D0%25B2%25D0%25B8%25D1%258F%25D0%25B7%25D0%25B5%25D0%25B2%25D0%25B0%252C%252046%252F2~~ymapsbm1%3A%2F%2Fgeo%3Fll%3D56.188%252C58.008%26spn%3D0.001%252C0.001%26text%3D%25D0%25A0%25D0%25BE%25D1%2581%25D1%2581%25D0%25B8%25D1%258F%252C%2520%25D0%259F%25D0%25B5%25D1%2580%25D0%25BC%25D1%258C%252C%2520%25D1%2583%25D0%25BB%25D0%25B8%25D1%2586%25D0%25B0%2520%25D0%2591%25D1%2583%25D0%25BA%25D0%25B8%25D1%2580%25D0%25B5%25D0%25B2%25D0%25B0%252C%252015&z=12");
        //Запорожская 1к1 - Букирева 15
        sites.add("https://yandex.ru/maps/50/perm/?ll=56.238265%2C57.998537&mode=routes&rtext=57.986516%2C56.291370~58.011432%2C56.237665~58.008390%2C56.188022&rtt=auto&ruri=~~ymapsbm1%3A%2F%2Fgeo%3Fll%3D56.188%252C58.008%26spn%3D0.001%252C0.001%26text%3D%25D0%25A0%25D0%25BE%25D1%2581%25D1%2581%25D0%25B8%25D1%258F%252C%2520%25D0%259F%25D0%25B5%25D1%2580%25D0%25BC%25D1%258C%252C%2520%25D1%2583%25D0%25BB%25D0%25B8%25D1%2586%25D0%25B0%2520%25D0%2591%25D1%2583%25D0%25BA%25D0%25B8%25D1%2580%25D0%25B5%25D0%25B2%25D0%25B0%252C%252015&z=13");
        //Восстания 71- Букирева 15
        sites.add("https://yandex.ru/maps/50/perm/?ll=56.248786%2C58.002883&mode=routes&rtext=58.033940%2C56.324029~58.011432%2C56.237665~58.008390%2C56.188022&rtt=auto&ruri=~~ymapsbm1%3A%2F%2Fgeo%3Fll%3D56.188%252C58.008%26spn%3D0.001%252C0.001%26text%3D%25D0%25A0%25D0%25BE%25D1%2581%25D1%2581%25D0%25B8%25D1%258F%252C%2520%25D0%259F%25D0%25B5%25D1%2580%25D0%25BC%25D1%258C%252C%2520%25D1%2583%25D025BB%25D0%25B8%25D1%2586%25D0%25B0%2520%25D0%2591%25D1%2583%25D0%25BA%25D0%25B8%25D1%2580%25D0%25B5%25D0%25B2%25D0%25B0%252C%252015&z=13");
        //Транспортна 13 - Букирева 15
        sites.add("https://yandex.ru/maps/50/perm/?from=tabbar&ll=56.213192%2C58.018183&mode=routes&rtext=58.034625%2C56.136746~58.017000%2C56.222480~58.008390%2C56.188022&rtt=auto&ruri=ymapsbm1%3A%2F%2Fgeo%3Fll%3D56.137%252C58.035%26spn%3D0.001%252C0.001%26text%3D%25D0%25A0%25D0%25BE%25D1%2581%25D1%2581%25D0%25B8%25D1%258F%252C%2520%25D0%259F%25D0%25B5%25D1%2580%25D0%25BC%25D1%258C%252C%2520%25D0%25A2%25D1%2580%25D0%25B0%25D0%25BD%25D1%2581%25D0%25BF%25D0%25BE%25D1%2580%25D1%2582%25D0%25BD%25D0%25B0%25D1%258F%2520%25D1%2583%25D0%25BB%25D0%25B8%25D1%2586%25D0%25B0%252C%252013~~ymapsbm1%3A%2F%2Fgeo%3Fll%3D56.188%252C58.008%26spn%3D0.001%252C0.001%26text%3D%25D0%25A0%25D0%25BE%25D1%2581%25D1%2581%25D0%25B8%25D1%258F%252C%2520%25D0%259F%25D0%25B5%25D1%2580%25D0%25BC%25D1%258C%252C%2520%25D1%2583%25D0%25BB%25D0%25B8%25D1%2586%25D0%25B0%2520%25D0%2591%25D1%2583%25D0%25BA%25D0%25B8%25D1%2580%25D0%25B5%25D0%25B2%25D0%25B0%252C%252015&source=serp_navig&z=14.25");
        //Барнаульская 1 - Букирева 15
        sites.add("https://yandex.ru/maps/50/perm/?from=tabbar&ll=56.263447%2C58.022281&mode=routes&rtext=58.105955%2C56.306756~58.048208%2C56.211539~58.008390%2C56.188022&rtt=auto&ruri=~~ymapsbm1%3A%2F%2Fgeo%3Fll%3D56.188%252C58.008%26spn%3D0.001%252C0.001%26text%3D%25D0%25A0%25D0%25BE%25D1%2581%25D1%2581%25D0%25B8%25D1%258F%252C%2520%25D0%259F%25D0%25B5%25D1%2580%25D0%25BC%25D1%258C%252C%2520%25D1%2583%25D0%25BB%25D0%25B8%25D1%2586%25D0%25B0%2520%25D0%2591%25D1%2583%25D0%25BA%25D0%25B8%25D1%2580%25D0%25B5%25D0%25B2%25D0%25B0%252C%252015&source=serp_navig&z=13.04");
        return sites;
    }

    public static WebElement fluentWait(final By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement foo = wait.until(visibilityOfElementLocated(locator));
        return driver.findElement(locator);
    }
}