package base;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import config.Constants;


public  class BaseTest 
{
	public static WebDriver driver;
	public static ExtentHtmlReporter htmlReporter;
    public static ExtentReports extent;
    public static ExtentTest test;
	
	@BeforeSuite
	@Parameters("browser")
	public void setUp(String browserName)
	{
		htmlReporter=new ExtentHtmlReporter(System.getProperty("user.dir") +"/test-output/DigiTeleAutomationReport.html");
		extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
		
        htmlReporter.config().setDocumentTitle("Digi Telecommunications Report");
        htmlReporter.config().setReportName("Automation Status Report");
        
		extent.setSystemInfo("Host Name", "Automation User");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("User Name", "Selenium User");
		    
		if(driver == null)
		{
			if(browserName.equalsIgnoreCase("firefox"))
			{
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\drivers\\geckodriver.exe");
				driver=new FirefoxDriver();				
			}
			else if(browserName.equalsIgnoreCase("chrome"))
			{
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.addArguments("disable-infobars");
				driver= new ChromeDriver(chromeOptions);
			}
			driver.manage().window().maximize();
			driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
			driver.get(Constants.APP_URL);
		}
	}
		
	@AfterMethod
	public void getResult(ITestResult result) throws Exception
	{
		if(result.getStatus() == ITestResult.FAILURE)
        {
            test.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" Test case FAILED due to below issues:", ExtentColor.RED));
            test.fail(result.getThrowable());
        }
        else if(result.getStatus() == ITestResult.SUCCESS)
        {
            test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test Case PASSED", ExtentColor.GREEN));
        }
        else
        {
            test.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" Test Case SKIPPED", ExtentColor.ORANGE));
            test.skip(result.getThrowable());
        }	
	} 
	
	@AfterSuite
	public void reportClose()
	{		
		extent.flush();
		driver.close();
	}
	
	public String captureFailedScreenshot(String screenName) throws IOException
	{
		String currentDateTime= getDateTime();
		/*Random rand= new Random();
		int number=rand.nextInt(10000);*/
		TakesScreenshot ts=(TakesScreenshot)driver;
		File srcFile=ts.getScreenshotAs(OutputType.FILE) ;
		String destination=System.getProperty("user.dir")+"\\Screenshots\\"+screenName+" "+currentDateTime+".png";
		File dest=new File(destination);
		FileUtils.copyFile(srcFile, dest);
		return destination;
	}	
	
	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String currentDateTime = dateFormat.format(date);
		return currentDateTime;
	}
	
	public boolean fileExist()
	{
		File f = new File(Constants.FILE_PATH);
		return f.exists();
	}
	
	public void fileDelete()
	{
		File f = new File(Constants.FILE_PATH);
		if(fileExist())
		{
			f.delete();
		}
	}
}
