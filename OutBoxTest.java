/*
*************************************************MODIFICATION HISTORY************************************************************
Sneha Motadoo 12/1/2010 Created initial script
Purpose: Initial Sanity Steps to be run on new VM
Desc: This script does the following 1) follows initial setup guide 2) creates local user and LDAP auths 3) secure mail send 4) LFT mail send
5) On board new user

*/
package sanity_ff;



import java.util.concurrent.TimeUnit;
import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;



import com.thoughtworks.selenium.Selenium;

public class OutBoxTest {
private WebDriver driver;
	
	private String baseUrl=Functions.baseUrl;
	private String composeUrl=Functions.compose_url;
	private String inboxUrl=Functions.inbox_url;
	private String sentUrl=Functions.sent_url;
	private String outboxUrl=Functions.outbox_url;
	private String draftsUrl=Functions.drafts_url;
	
	Selenium selenium;
	private StringBuffer verificationErrors = new StringBuffer();
	String user_allperms="sneha.qa.24@gmail.com",user_noperms="muunni.24@gmail.com",user_onlysecure="snehamtd002@yahoo.com",user_onlylft="snehamtd001@gmail.com";
	
	String pwd_allusers="123abc";
	String pathAutoItScript=Functions.pathToLessthan25MbFilesScript;
	String pathAutoItScript_greaterthan25MB=Functions.pathToGreaterthan25MbFilesScript;
	String pathToDownloadHandle=Functions.pathToDownloadFileHandler;
	String subject,emailBody;
	@Before
	public void setUp() throws Exception {
		
		System.setProperty("webdriver.firefox.bin","C://Firefox6.0//Mozilla Firefox//firefox.exe");
		driver = new FirefoxDriver();
		
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("portal-enduser")).click();
		driver.findElement(By.id("network")).click();
		driver.findElement(By.id("relay")).click();
		driver.findElement(By.id("id_host")).clear();
		driver.findElement(By.id("id_host")).sendKeys("relaybackup.dnsexit.comblahblah");
		driver.findElement(By.name("submit-primary")).click();
		assertEquals("New email relay settings were saved successfully.", driver.findElement(By.cssSelector("li.success")).getText());
		
		driver.findElement(By.id("portal-admin")).click();
		
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		subject="outbox fail case";
		emailBody=subject;
		
		driver.findElement(By.id("logout")).click();
		Functions.SecureSend(selenium, driver, user_allperms, user_allperms, subject, emailBody, pwd_allusers, baseUrl);
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		String result=Functions.FindIdwithSubjectInOutbox(driver, selenium, subject);
		driver.findElement(By.id("portal-enduser")).click();
		driver.findElement(By.id("network")).click();
		driver.findElement(By.id("relay")).click();
		driver.findElement(By.id("id_host")).clear();
		driver.findElement(By.id("id_host")).sendKeys("relaybackup.dnsexit.com");
		driver.findElement(By.name("submit-primary")).click();
		assertEquals("New email relay settings were saved successfully.", driver.findElement(By.cssSelector("li.success")).getText());
		
		if(result.equals(null))
		{System.out.println("FAIL"); assertEquals("0","1");}
		else
		{System.out.println("PASS");}
		
		
		
		
		
		driver.findElement(By.id("logout")).click();
		
		
		
		
	}

	
	
	
	@Test
	public void InitialSanityMain() throws Exception {
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		
		
		
		
	}
	
	
	
	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}//end of tearDown

}// end of OutBox test class