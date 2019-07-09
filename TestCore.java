package dd_Core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.math3.stat.inference.TestUtils;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import dd_Util.ReadData;
import dd_Util.TestUtil;

public class TestCore {

	public static WebDriver driver = null;
	public static Properties Config = null;
	public static Properties object = null;
	public static FileInputStream fis = null;
	public static ReadData excel = null;
	public static Logger logs = null;

	@BeforeSuite
	public void init() throws IOException {

		if (driver == null) {
			Config = new Properties();
			object = new Properties();
			logs = Logger.getLogger("dd_Core.TestCore.class");

			fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\dd_Properties\\config.properties");
			Config.load(fis);
			logs.debug("Loaded the Config property file");

			fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\dd_Properties\\object.properties");
			Config.load(fis);
			logs.debug("Loaded the Config property file");

			excel = new ReadData(System.getProperty("user.dir") + "\\src\\dd_Properties\\File1.xlsx");
			if (Config.getProperty("browser").equals("firfox")) {
				System.setProperty("webdriver.gecko.driver", "F:\\jar\\\\selenium jar\\geckodriver.exe");

				driver = new FirefoxDriver();
				logs.debug("Loaded Firefox Driver");

			} else if (Config.getProperty("browser").equals("chrome")) {
				System.setProperty("webdriver.chrome.driver", "F:\\jar\\selenium jar\\chromeupdate\\chromedriver.exe");

				driver = new ChromeDriver();
				logs.debug("Loaded Chrome Driver");
			}
			driver.get(Config.getProperty("testsiteurl"));
			driver.manage().timeouts().implicitlyWait(20L, TimeUnit.SECONDS);
			driver.manage().window().maximize();

		}
	}

	@AfterSuite
	public void stop() {
	// mail code
    driver.quit();
	}


	
	public static WebElement findElement(String key) throws IOException {
		
		try {
			return driver.findElement(By.xpath(object.getProperty(key)));
		}
			catch(Throwable t) 
			{
			logs.debug("Captured Screenshort");
			TestUtil.CaptureScreenshot();
				return null;
			}
		}
	
}  