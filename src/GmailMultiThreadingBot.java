import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;


public class GmailMultiThreadingBot
{
    //declaring driver object of Webdriver class
    int tabno;
    WebDriver driver;
    public void launchbrowser()
    {
        ChromeOptions options = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver", "/Users/harihar/Documents/Code/chromedriver_mac64/chromedriver");
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
    }
    public int NumberOfTabs() 
    {
        ArrayList<String> newTab = new ArrayList<String> (driver.getWindowHandles());
        return newTab.size();
    }
    public static void main(String[] args) throws InterruptedException
    {
        GmailMultiThreadingBot obj1 = new GmailMultiThreadingBot();
        obj1.launchbrowser();
        for(int i = (obj1.NumberOfTabs() - 1); i >= 0;i--)
        {
            GmailAutomationBot obj2 = new GmailAutomationBot(i);
            obj2.start();
        }
    }
}

    

