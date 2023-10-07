import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Duration;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class GmailAutomationBot extends Thread
{
    //declaring driver object of Webdriver class
    int threadno;
    static String SubjectLine1 = "Having trouble bringing clients in through the door?";
    static String SubjectLine2 = "Re: Adding an extra 50k per month";
    static String SubjectLine3 = "Just need a little bit of help";
    static String EmailScript1 = "Hey %s, \n\nMe and my team have been in the flooring space for a while now, and we're working on building a completely new service from the ground up " +
    "that aims to supercharge companies like your own by bringing you in at least $50,000 in added monthly revenue, in addition to cultivating a pool of customers that could bring "+
    "up to double that down the line. \n\nHowever, we need some help. We just wanted to get your feedback on your personal customer acquisition goals, the obstacles you've "+ 
    "faced in achieving them, and your ideal service, just to ensure that our service is the absolute best it can possible be. \n\nAll we need you to do is fill in this form"+
    " and we'll get back to you with a service tailored to your needs!\n\nhttps://forms.gle/sUNfPrLm3tUbd4aUA\n\nRegards, \n%s";
    static String EmailScript2 = "Hey %s, \n\nMe and my team are working on building a completely new service from the ground up that aims to bring in at least $50,000 in added " +
    "monthly revenue to flooring companies, along with an easily accessible pool of warm leads. \n\nHowever, we just wanted to get your feedback on a couple things, just to ensure" + 
    " that our service is the absolute best it can possible be. \n\nAll we need you to do is to fill in this form, and then we'll get back to you with a service tailored " +
    "to your needs!\n\nhttps://forms.gle/sUNfPrLm3tUbd4aUA \n\nRegards, \n%s";
    static String EmailScript3 = "Hey %s, \n\nI've been looking into what %s has been doing in the flooring industry, and I've got to say, it's really very impressive!\n\nI'm reaching " +
    "out because my team and I are working on a completely new service to supercharge companies such as yours using market insights we've gathered over the years, and we wanted " +
    "to get some hands-on market feedback from experienced individuals such as yourself.\n\nAll we need you to do is fill in this form, and then we'll get back to you " +
    "with a service tailored to your needs!\n\nhttps://forms.gle/sUNfPrLm3tUbd4aUA\n\nRegards, \n%s";
    static String ExcelPath = "/Users/harihar/Documents/Code/ExcelTest.xlsx";
    static String[] SubjectLines = {SubjectLine1, SubjectLine1, SubjectLine1, SubjectLine2, SubjectLine2, SubjectLine2, SubjectLine3, SubjectLine3, SubjectLine3};
    static String[] EmailScripts = {EmailScript1, EmailScript2, EmailScript3, EmailScript1, EmailScript2, EmailScript3, EmailScript3, EmailScript2, EmailScript3};
    static String[] Names = {"Lucas", "Isabella", "Benjamin", "Olivia", "Ethan", "Ava", "Gabriel"};
    WebDriver driver;
    public String readExcel(int rno, int colno) 
    {
        String data = "";
        try
        {
            FileInputStream fis = new FileInputStream(ExcelPath);
            Workbook wb = WorkbookFactory.create(fis);
            Sheet s = wb.getSheetAt(threadno);
            Row r = s.getRow(rno);
            Cell c = r.getCell(colno);
            data = c.getStringCellValue();
        }
        catch(Exception e){}
        return data;
    }
    public void writeExcel(int rno, int colno, String data) 
    {
        try
        {
            FileInputStream fis = new FileInputStream(ExcelPath);
            Workbook wb = WorkbookFactory.create(fis);
            Sheet s = wb.getSheetAt(threadno);
            Row r = s.getRow(rno);
            Cell c = r.createCell(colno);
            c.setCellValue(data);
            FileOutputStream fos = new FileOutputStream(ExcelPath);
            wb.write(fos);
        }
        catch(Exception e){}
    }
    public String getEmailScript(String name, String company, String myName, int scriptIndex) 
    {
        String script = EmailScripts[scriptIndex];
        int numPlaceholders = script.split("%s").length;
        if (numPlaceholders == 2) 
        {
            return String.format(script, name, myName);
        } 
        else if (numPlaceholders == 3) 
        {
            return String.format(script, name, company, myName);
        } 
        else
        {
            return "Invalid input";
        }
    }
    public String getMyName()
    {
        for(int i = 0; i < Names.length; i++)
        {
            if(driver.getTitle().contains(Names[i].toLowerCase()))
                return Names[i];
        }
        return "A well wisher";
    }   
    public void interact()
    {
        String email = "a";
        int x = 0;
        String CustomerName;
        String CustomerFirstName;
        String CustomerCompany;
        while(!(email.equals("")))
        {
            email = readExcel(x,0);
            CustomerName = readExcel(x,1);
            if(CustomerName.indexOf(" ") != -1)
                CustomerFirstName = CustomerName.substring(0, CustomerName.indexOf(" "));
            else 
                CustomerFirstName = CustomerName;
            CustomerCompany = readExcel(x, 2);
            if(!(email.equals("")))
            {
                //System.out.println("Cell value: " + email);
                WebElement composebutton = driver.findElement(By.cssSelector(".T-I.T-I-KE.L3"));
                new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.elementToBeClickable(composebutton));
                composebutton.click();
                WebElement ToEmail = driver.findElement(By.cssSelector(".agP.aFw"));
                new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.elementToBeClickable(ToEmail));
                ToEmail.sendKeys(email);
                WebElement subject = driver.findElement(By.className("aoT"));
                new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.elementToBeClickable(subject));
                subject.sendKeys(SubjectLines[threadno]);
                WebElement emailcontent = driver.findElement(By.cssSelector(".Am.Al.editable.LW-avf.tS-tW"));
                new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.elementToBeClickable(emailcontent));
                emailcontent.sendKeys(getEmailScript(CustomerFirstName, CustomerCompany, getMyName(), threadno));
                WebElement sendbutton = driver.findElement(By.cssSelector(".T-I.J-J5-Ji.aoO.v7.T-I-atl.L3"));
                new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.elementToBeClickable(sendbutton));
                sendbutton.click();
                writeExcel(x, 3, "Mail sent");
                try{
                Thread.sleep(2*60*1000);
                }
                catch(Exception e){}
            }
        }    
    }
    public void launchbrowser()
    {
        ChromeOptions options = new ChromeOptions();
        System.setProperty("webdriver.chrome.driver", "/Users/harihar/Documents/Code/chromedriver_mac64/chromedriver");
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
    }
    public void switchTabs()
    {
        ArrayList<String> newTab = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(newTab.get(threadno));
        System.out.print(driver.getTitle());
        System.out.println(" is tab no:" + threadno);
    }    
    public GmailAutomationBot(int threadno2)
    {
        threadno = threadno2;
    }
    @Override
    public void run()
    {
        launchbrowser();
        switchTabs();
        interact();
    }
}

    

