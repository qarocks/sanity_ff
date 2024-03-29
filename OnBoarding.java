package sanity_ff;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import sanity_ff.Functions;

import com.thoughtworks.selenium.Selenium;

public class OnBoarding {
	
	private WebDriver driver;
    Selenium selenium;
	private StringBuffer verificationErrors = new StringBuffer();
	private String baseUrl=Functions.baseUrl;
	private String composeUrl=Functions.compose_url;
	private String inboxUrl=Functions.inbox_url;
	private String sentUrl=Functions.sent_url;
	private String outboxUrl=Functions.outbox_url;
	private String draftsUrl=Functions.drafts_url;
	String sender,recipient;
	String pwd_allusers="123abc";
	String pathAutoItScript=Functions.pathToLessthan25MbFilesScript;
	String pathAutoItScript_greaterthan25MB=Functions.pathToGreaterthan25MbFilesScript;
	String pathToDownloadHandle=Functions.pathToDownloadFileHandler;
	String subject,emailBody;
	String user_noperms="muunni.24@gmail.com"; // AD user used for onboarding test cases
	String user_allperms="sneha.qa.24@gmail.com",user_onlysecure="snehamtd002@yahoo.com",user_onlylft="snehamtd001@gmail.com";

	
	@Before
	public void setUp() throws Exception {
		
		System.setProperty("webdriver.firefox.bin","C:\\6.0.2\\Mozilla Firefox\\firefox.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
	}
	@Test
	public void PersonalizedSecure()throws Exception { // personalized option ON sending Secure with attachs
	sender=user_allperms;
	recipient=user_noperms;
	subject="Personalized secure onboard";
	emailBody=subject;
	Functions.DeleteFirstThreeRowsGmail(driver, selenium);
		Functions.SecureSendGreaterthan25MB(selenium, driver, sender, recipient, subject, emailBody,pwd_allusers,baseUrl);
		
		
		driver.get("http://www.gmail.com");
		selenium.waitForPageToLoad("3000");
		driver.findElement(By.id("Email")).click();
		driver.findElement(By.id("Email")).clear();
		driver.findElement(By.id("Email")).sendKeys("muunni.24");
		driver.findElement(By.id("Passwd")).clear();
		driver.findElement(By.id("Passwd")).sendKeys("123sneha_24");
		driver.findElement(By.id("signIn")).click();
		Thread.sleep(20000);
		selenium.refresh();
		WebElement myele= driver.findElement(By.partialLinkText(subject));
		myele.click();
		myele.sendKeys(Keys.ENTER);
		assertEquals("", driver.findElement(By.cssSelector("img[alt=\"SECURE!\"]")).getText());			
        String vmmn=driver.findElement(By.partialLinkText("View my message now")).getAttribute("href");
		driver.findElement(By.partialLinkText("Sign out")).click();
		System.out.println(" "+vmmn);
		driver.get(vmmn);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys("123abc");
		driver.findElement(By.id("id_verify_password")).clear();
		driver.findElement(By.id("id_verify_password")).sendKeys("123abc");
		driver.findElement(By.id("id_full_name")).clear();
		driver.findElement(By.id("id_full_name")).sendKeys("muunni.24@gmail.com");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Your account was successfully created!", driver.findElement(By.cssSelector("li.success")).getText());
		Thread.sleep(5000);
		driver.findElement(By.partialLinkText("download")).click();
		  Thread.sleep(3000);
		Runtime.getRuntime().exec(pathToDownloadHandle+" Opening Save");
		  Thread.sleep(3000);
        Functions.Logout(driver);
		System.out.println("New user has been on boarded successfully: personalised email option set ON");
		System.out.println("Now deleting local user muunni.24@gmail.com");
		Functions.DeleteUser(driver, selenium, user_noperms);
		
	}

	@Test
	public void PersonalizedLFT()throws Exception { // personalized option ON sending LFT
	sender=user_allperms;
	recipient=user_noperms;
	subject="Personalized LFT onboard";
	emailBody=subject;
	
	Functions.DeleteFirstThreeRowsGmail(driver, selenium);
 Functions.LFTSendGreaterThan25mb(selenium, driver,sender, recipient, subject, emailBody, pwd_allusers, baseUrl);
		
 driver.get("http://www.gmail.com");
	selenium.waitForPageToLoad("3000");
	driver.findElement(By.id("Email")).click();
	driver.findElement(By.id("Email")).clear();
	driver.findElement(By.id("Email")).sendKeys("muunni.24");
	driver.findElement(By.id("Passwd")).clear();
	driver.findElement(By.id("Passwd")).sendKeys("123sneha_24");
	driver.findElement(By.id("signIn")).click();
	Thread.sleep(20000);
	selenium.refresh();
	WebElement myele= driver.findElement(By.partialLinkText(subject));
	myele.click();
	myele.sendKeys(Keys.ENTER);
	
    String vmmn=driver.findElement(By.partialLinkText("PHPx64.zip")).getAttribute("href");
	driver.findElement(By.partialLinkText("Sign out")).click();
	System.out.println(" "+vmmn);
	driver.get(vmmn);
	
	driver.findElement(By.id("id_password")).clear();
	driver.findElement(By.id("id_password")).sendKeys("123abc");
	driver.findElement(By.id("id_verify_password")).clear();
	driver.findElement(By.id("id_verify_password")).sendKeys("123abc");
	driver.findElement(By.id("id_full_name")).clear();
	driver.findElement(By.id("id_full_name")).sendKeys("muunni.24@gmail.com");
	driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	assertEquals("Your account was successfully created!", driver.findElement(By.cssSelector("li.success")).getText());
	Thread.sleep(5000);
	driver.findElement(By.partialLinkText("download")).click();
	  Thread.sleep(3000);
	Runtime.getRuntime().exec(pathToDownloadHandle+" Opening Save");
	  Thread.sleep(3000);
    Functions.Logout(driver);
	System.out.println("New user has been on boarded successfully: personalised email option set ON");
	System.out.println("Now deleting local user muunni.24@gmail.com");
	Functions.DeleteUser(driver, selenium, user_noperms);
			
		
	}
	
	
	@Test
	public void GenericSecure()throws Exception { // Generic option ON sending Secure
		
		sender=user_allperms;
		recipient=user_noperms;
		subject="Generic secure onboard";
		emailBody=subject;
	    
		
		//making sure admin on boarding option is set to generic
		
	Functions.AdminLogin(driver);
	driver.findElement(By.id("users")).click();
	driver.findElement(By.id("onboarding")).click();
	driver.findElement(By.id("id_invitation_1")).click();
	driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	Functions.Logout(driver);
	Functions.DeleteFirstThreeRowsGmail(driver, selenium);
			Functions.SecureSendGreaterthan25MB(selenium, driver, sender, recipient, subject, emailBody,pwd_allusers,baseUrl);
			
			
			driver.get("http://www.gmail.com");
			selenium.waitForPageToLoad("3000");
			driver.findElement(By.id("Email")).click();
			driver.findElement(By.id("Email")).clear();
			driver.findElement(By.id("Email")).sendKeys("muunni.24");
			driver.findElement(By.id("Passwd")).clear();
			driver.findElement(By.id("Passwd")).sendKeys("123sneha_24");
			driver.findElement(By.id("signIn")).click();
			Thread.sleep(20000);
			selenium.refresh();
			WebElement myele= driver.findElement(By.partialLinkText("Account Required"));
			myele.click();
			myele.sendKeys(Keys.ENTER);
			assertEquals("Please Create an Account!", driver.findElement(By.xpath("//td[2]/h3")).getText());		
	        String vmmn=driver.findElement(By.partialLinkText("Create an account now")).getAttribute("href");
			driver.findElement(By.partialLinkText("Sign out")).click();
			System.out.println(" "+vmmn);
			driver.get(vmmn);
			driver.findElement(By.id("id_password")).clear();
			driver.findElement(By.id("id_password")).sendKeys("123abc");
			driver.findElement(By.id("id_verify_password")).clear();
			driver.findElement(By.id("id_verify_password")).sendKeys("123abc");
			driver.findElement(By.id("id_full_name")).clear();
			driver.findElement(By.id("id_full_name")).sendKeys("muunni.24@gmail.com");
			driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
			assertEquals("Your account was successfully created!", driver.findElement(By.cssSelector("li.success")).getText());
			Thread.sleep(5000);
			String returnid=Functions.FindIdwithSubject(driver, selenium, subject);
			driver.get(baseUrl+"/transfer/email/view/"+returnid);
			Thread.sleep(5000);
			driver.findElement(By.partialLinkText("download")).click();
			Thread.sleep(3000);
			Runtime.getRuntime().exec(pathToDownloadHandle+" Opening Save");
	        Thread.sleep(3000);
	        Functions.Logout(driver);
	        driver.get("http://www.gmail.com");
			selenium.waitForPageToLoad("3000");
			driver.findElement(By.id("Email")).click();
			driver.findElement(By.id("Email")).clear();
			driver.findElement(By.id("Email")).sendKeys("muunni.24");
			driver.findElement(By.id("Passwd")).clear();
			driver.findElement(By.id("Passwd")).sendKeys("123sneha_24");
			driver.findElement(By.id("signIn")).click();
			Thread.sleep(20000);
			selenium.refresh();
			myele= driver.findElement(By.partialLinkText(subject));
			myele.click();
			myele.sendKeys(Keys.ENTER);
			assertEquals("", driver.findElement(By.cssSelector("img[alt=\"SECURE!\"]")).getText());			
	        vmmn=driver.findElement(By.partialLinkText("View my message now")).getAttribute("href");
			driver.findElement(By.partialLinkText("Sign out")).click();
			System.out.println(" "+vmmn);
			driver.get(vmmn);
			Functions.login(driver,recipient, pwd_allusers);
			Thread.sleep(5000);
			returnid=Functions.FindIdwithSubject(driver, selenium, subject);
			driver.get(baseUrl+"/transfer/email/view/"+returnid);
			Thread.sleep(5000);
			driver.findElement(By.partialLinkText("download")).click();
			  Thread.sleep(3000);
			Runtime.getRuntime().exec(pathToDownloadHandle+" Opening Save");
			  Thread.sleep(3000);
	        Functions.Logout(driver);
			System.out.println("New user has been on boarded successfully: personalised email option set ON");
			System.out.println("Now deleting local user muunni.24@gmail.com");
			Functions.DeleteUser(driver, selenium, recipient);	
	
	}
	
	
	@Test
	public void GenericLFT()throws Exception { // Generic option ON sending LFT
		
		sender=user_allperms;
		recipient=user_noperms;
		subject="Generic LFT onboard";
		emailBody=subject;
	    
		
		//making sure admin on boarding option is set to generic
		
	Functions.AdminLogin(driver);
	driver.findElement(By.id("users")).click();
	driver.findElement(By.id("onboarding")).click();
	driver.findElement(By.id("id_invitation_1")).click();
	driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	Functions.Logout(driver);
	Functions.DeleteFirstThreeRowsGmail(driver, selenium);
			Functions.LFTSendGreaterThan25mb(selenium, driver, sender, recipient, subject, emailBody,pwd_allusers,baseUrl);
			
			
			driver.get("http://www.gmail.com");
			selenium.waitForPageToLoad("3000");
			driver.findElement(By.id("Email")).click();
			driver.findElement(By.id("Email")).clear();
			driver.findElement(By.id("Email")).sendKeys("muunni.24");
			driver.findElement(By.id("Passwd")).clear();
			driver.findElement(By.id("Passwd")).sendKeys("123sneha_24");
			driver.findElement(By.id("signIn")).click();
			Thread.sleep(20000);
			selenium.refresh();
			WebElement myele= driver.findElement(By.partialLinkText("Account Required"));
			myele.click();
			myele.sendKeys(Keys.ENTER);
			assertEquals("Please Create an Account!", driver.findElement(By.xpath("//td[2]/h3")).getText());		
	        String vmmn=driver.findElement(By.partialLinkText("Create an account now")).getAttribute("href");
			driver.findElement(By.partialLinkText("Sign out")).click();
			System.out.println(" "+vmmn);
			driver.get(vmmn);
			driver.findElement(By.id("id_password")).clear();
			driver.findElement(By.id("id_password")).sendKeys("123abc");
			driver.findElement(By.id("id_verify_password")).clear();
			driver.findElement(By.id("id_verify_password")).sendKeys("123abc");
			driver.findElement(By.id("id_full_name")).clear();
			driver.findElement(By.id("id_full_name")).sendKeys("muunni.24@gmail.com");
			driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
			assertEquals("Your account was successfully created!", driver.findElement(By.cssSelector("li.success")).getText());
			Thread.sleep(5000);
			String returnid=Functions.FindIdwithSubject(driver, selenium, subject);
			driver.get(baseUrl+"/transfer/email/view/"+returnid);
			Thread.sleep(5000);
			driver.findElement(By.partialLinkText("download")).click();
			Thread.sleep(3000);
			Runtime.getRuntime().exec(pathToDownloadHandle+" Opening Save");
	        Thread.sleep(3000);
	        Functions.Logout(driver);
	        driver.get("http://www.gmail.com");
			selenium.waitForPageToLoad("3000");
			driver.findElement(By.id("Email")).click();
			driver.findElement(By.id("Email")).clear();
			driver.findElement(By.id("Email")).sendKeys("muunni.24");
			driver.findElement(By.id("Passwd")).clear();
			driver.findElement(By.id("Passwd")).sendKeys("123sneha_24");
			driver.findElement(By.id("signIn")).click();
			Thread.sleep(20000);
			selenium.refresh();
			myele= driver.findElement(By.partialLinkText(subject));
			myele.click();
			myele.sendKeys(Keys.ENTER);
					
	        vmmn=driver.findElement(By.partialLinkText("PHPx64.zip")).getAttribute("href");
			driver.findElement(By.partialLinkText("Sign out")).click();
			System.out.println(" "+vmmn);
			driver.get(vmmn);
			Functions.login(driver,recipient, pwd_allusers);
			Thread.sleep(5000);
			returnid=Functions.FindIdwithSubject(driver, selenium, subject);
			driver.get(baseUrl+"/transfer/email/view/"+returnid);
			Thread.sleep(5000);
			driver.findElement(By.partialLinkText("download")).click();
			  Thread.sleep(3000);
			Runtime.getRuntime().exec(pathToDownloadHandle+" Opening Save");
			  Thread.sleep(3000);
	        Functions.Logout(driver);
			System.out.println("New user has been on boarded successfully: personalised email option set ON");
			System.out.println("Now deleting local user muunni.24@gmail.com");
			Functions.DeleteUser(driver, selenium, recipient);	
	
	}
	
	// confirmation sON section.
	
	@Test
	public void ConfONPersonalizedSecure()throws Exception { // personalized option ON sending Secure with attachs
	sender=user_allperms;
	recipient=user_noperms;
	subject="Conf ON Personalized secure onboard";
	emailBody=subject;
	
	Functions.AdminLogin(driver);
	driver.findElement(By.id("users")).click();
	driver.findElement(By.id("onboarding")).click();
    driver.findElement(By.id("id_confirmation_0")).click();
    driver.findElement(By.id("id_invitation_1")).click();
	driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	Thread.sleep(10000);
	Functions.Logout(driver);
	Functions.DeleteFirstThreeRowsGmail(driver, selenium);
		Functions.SecureSendGreaterthan25MB(selenium, driver, sender, recipient, subject, emailBody,pwd_allusers,baseUrl);
		
		
		driver.get("http://www.gmail.com");
		selenium.waitForPageToLoad("3000");
		driver.findElement(By.id("Email")).click();
		driver.findElement(By.id("Email")).clear();
		driver.findElement(By.id("Email")).sendKeys("muunni.24");
		driver.findElement(By.id("Passwd")).clear();
		driver.findElement(By.id("Passwd")).sendKeys("123sneha_24");
		driver.findElement(By.id("signIn")).click();
		Thread.sleep(20000);
		selenium.refresh();
		WebElement myele= driver.findElement(By.partialLinkText(subject));
		myele.click();
		myele.sendKeys(Keys.ENTER);
		assertEquals("", driver.findElement(By.cssSelector("img[alt=\"SECURE!\"]")).getText());			
        String vmmn=driver.findElement(By.partialLinkText("View my message now")).getAttribute("href");
		driver.findElement(By.partialLinkText("Sign out")).click();
		System.out.println(" "+vmmn);
		driver.get(vmmn);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys("123abc");
		driver.findElement(By.id("id_verify_password")).clear();
		driver.findElement(By.id("id_verify_password")).sendKeys("123abc");
		driver.findElement(By.id("id_full_name")).clear();
		driver.findElement(By.id("id_full_name")).sendKeys("muunni.24@gmail.com");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Your account was successfully created! You should receive an email soon asking you to confirm your account activation.", driver.findElement(By.cssSelector("li.success")).getText());
		driver.get("http://www.gmail.com");
		selenium.waitForPageToLoad("3000");
		driver.findElement(By.id("Email")).click();
		driver.findElement(By.id("Email")).clear();
		driver.findElement(By.id("Email")).sendKeys("muunni.24");
		driver.findElement(By.id("Passwd")).clear();
		driver.findElement(By.id("Passwd")).sendKeys("123sneha_24");
		driver.findElement(By.id("signIn")).click();
		Thread.sleep(20000);
		selenium.refresh();
		myele= driver.findElement(By.partialLinkText("Account Creation Confirmation"));
		myele.click();
		myele.sendKeys(Keys.ENTER);
		vmmn=driver.findElement(By.partialLinkText("Confirm - Create My Account")).getAttribute("href");
		driver.findElement(By.partialLinkText("Sign out")).click();
		System.out.println(" "+vmmn);
		driver.get(vmmn);
		Functions.login(driver,recipient, pwd_allusers);
		Thread.sleep(5000);
		String returnid=Functions.FindIdwithSubject(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/email/view/"+returnid);
		Thread.sleep(5000);
		driver.findElement(By.partialLinkText("download")).click();
		  Thread.sleep(3000);
		Runtime.getRuntime().exec(pathToDownloadHandle+" Opening Save");
		  Thread.sleep(3000);
        Functions.Logout(driver);
		System.out.println("New user has been on boarded successfully: personalised email option set ON");
		System.out.println("Now deleting local user muunni.24@gmail.com");
		Functions.DeleteUser(driver, selenium, recipient);	
		
	}
	
	@Test
	public void ConfONPersonalizedLFT()throws Exception { // personalized option ON sending LFT with attachs
	sender=user_allperms;
	recipient=user_noperms;
	subject="Conf ON Personalized LFT onboard";
	emailBody=subject;
	

	Functions.DeleteFirstThreeRowsGmail(driver, selenium);
		Functions.LFTSendGreaterThan25mb(selenium, driver, sender, recipient, subject, emailBody,pwd_allusers,baseUrl);
		
		
		driver.get("http://www.gmail.com");
		selenium.waitForPageToLoad("3000");
		driver.findElement(By.id("Email")).click();
		driver.findElement(By.id("Email")).clear();
		driver.findElement(By.id("Email")).sendKeys("muunni.24");
		driver.findElement(By.id("Passwd")).clear();
		driver.findElement(By.id("Passwd")).sendKeys("123sneha_24");
		driver.findElement(By.id("signIn")).click();
		Thread.sleep(20000);
		selenium.refresh();
		WebElement myele= driver.findElement(By.partialLinkText(subject));
		myele.click();
		myele.sendKeys(Keys.ENTER);
				
        String vmmn=driver.findElement(By.partialLinkText("PHPx64.zip")).getAttribute("href");
		driver.findElement(By.partialLinkText("Sign out")).click();
		System.out.println(" "+vmmn);
		driver.get(vmmn);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys("123abc");
		driver.findElement(By.id("id_verify_password")).clear();
		driver.findElement(By.id("id_verify_password")).sendKeys("123abc");
		driver.findElement(By.id("id_full_name")).clear();
		driver.findElement(By.id("id_full_name")).sendKeys("muunni.24@gmail.com");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Your account was successfully created! You should receive an email soon asking you to confirm your account activation.", driver.findElement(By.cssSelector("li.success")).getText());
		driver.get("http://www.gmail.com");
		selenium.waitForPageToLoad("3000");
		driver.findElement(By.id("Email")).click();
		driver.findElement(By.id("Email")).clear();
		driver.findElement(By.id("Email")).sendKeys("muunni.24");
		driver.findElement(By.id("Passwd")).clear();
		driver.findElement(By.id("Passwd")).sendKeys("123sneha_24");
		driver.findElement(By.id("signIn")).click();
		Thread.sleep(20000);
		selenium.refresh();
		myele= driver.findElement(By.partialLinkText("Account Creation Confirmation"));
		myele.click();
		myele.sendKeys(Keys.ENTER);
		vmmn=driver.findElement(By.partialLinkText("Confirm - Create My Account")).getAttribute("href");
		driver.findElement(By.partialLinkText("Sign out")).click();
		System.out.println(" "+vmmn);
		driver.get(vmmn);
		Functions.login(driver,recipient, pwd_allusers);
		Thread.sleep(5000);
		String returnid=Functions.FindIdwithSubject(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/email/view/"+returnid);
		Thread.sleep(5000);
		driver.findElement(By.partialLinkText("download")).click();
		  Thread.sleep(3000);
		Runtime.getRuntime().exec(pathToDownloadHandle+" Opening Save");
		  Thread.sleep(3000);
        Functions.Logout(driver);
		System.out.println("New user has been on boarded successfully: personalised email option set ON");
		System.out.println("Now deleting local user muunni.24@gmail.com");
		Functions.DeleteUser(driver, selenium, recipient);	
		
	}
	
	
	@Test
	public void ConfONGenericSecure()throws Exception { // Generic option ON sending Secure
		
		sender=user_allperms;
		recipient=user_noperms;
		subject="Conf ON Generic secure onboard";
		emailBody=subject;
	    
		
		Functions.DeleteFirstThreeRowsGmail(driver, selenium);
		
			Functions.SecureSendGreaterthan25MB(selenium, driver, sender, recipient, subject, emailBody,pwd_allusers,baseUrl);
			
			
			driver.get("http://www.gmail.com");
			selenium.waitForPageToLoad("3000");
			driver.findElement(By.id("Email")).click();
			driver.findElement(By.id("Email")).clear();
			driver.findElement(By.id("Email")).sendKeys("muunni.24");
			driver.findElement(By.id("Passwd")).clear();
			driver.findElement(By.id("Passwd")).sendKeys("123sneha_24");
			driver.findElement(By.id("signIn")).click();
			Thread.sleep(20000);
			selenium.refresh();
			WebElement myele= driver.findElement(By.partialLinkText("Account Required"));
			myele.click();
			myele.sendKeys(Keys.ENTER);
			assertEquals("Please Create an Account!", driver.findElement(By.xpath("//td[2]/h3")).getText());		
	        String vmmn=driver.findElement(By.partialLinkText("Create an account now")).getAttribute("href");
			driver.findElement(By.partialLinkText("Sign out")).click();
			System.out.println(" "+vmmn);
			driver.get(vmmn);
			driver.findElement(By.id("id_password")).clear();
			driver.findElement(By.id("id_password")).sendKeys("123abc");
			driver.findElement(By.id("id_verify_password")).clear();
			driver.findElement(By.id("id_verify_password")).sendKeys("123abc");
			driver.findElement(By.id("id_full_name")).clear();
			driver.findElement(By.id("id_full_name")).sendKeys("muunni.24@gmail.com");
			driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		
			assertEquals("Your account was successfully created! You should receive an email soon asking you to confirm your account activation.", driver.findElement(By.cssSelector("li.success")).getText());
			driver.get("http://www.gmail.com");
			selenium.waitForPageToLoad("3000");
			driver.findElement(By.id("Email")).click();
			driver.findElement(By.id("Email")).clear();
			driver.findElement(By.id("Email")).sendKeys("muunni.24");
			driver.findElement(By.id("Passwd")).clear();
			driver.findElement(By.id("Passwd")).sendKeys("123sneha_24");
			driver.findElement(By.id("signIn")).click();
			Thread.sleep(20000);
			selenium.refresh();
			myele= driver.findElement(By.partialLinkText("Account Creation Confirmation"));
			myele.click();
			myele.sendKeys(Keys.ENTER);
			vmmn=driver.findElement(By.partialLinkText("Confirm - Create My Account")).getAttribute("href");
			driver.findElement(By.partialLinkText("Sign out")).click();
			System.out.println(" "+vmmn);
			driver.get(vmmn);
			Functions.login(driver,recipient, pwd_allusers);
			Thread.sleep(5000);
			String returnid=Functions.FindIdwithSubject(driver, selenium, subject);
			driver.get(baseUrl+"/transfer/email/view/"+returnid);
			Thread.sleep(5000);
			driver.findElement(By.partialLinkText("download")).click();
			  Thread.sleep(3000);
			Runtime.getRuntime().exec(pathToDownloadHandle+" Opening Save");
			  Thread.sleep(3000);
	        Functions.Logout(driver);
			
	        driver.get("http://www.gmail.com");
			selenium.waitForPageToLoad("3000");
			driver.findElement(By.id("Email")).click();
			driver.findElement(By.id("Email")).clear();
			driver.findElement(By.id("Email")).sendKeys("muunni.24");
			driver.findElement(By.id("Passwd")).clear();
			driver.findElement(By.id("Passwd")).sendKeys("123sneha_24");
			driver.findElement(By.id("signIn")).click();
			Thread.sleep(20000);
			selenium.refresh();
			myele= driver.findElement(By.partialLinkText(subject));
			myele.click();
			myele.sendKeys(Keys.ENTER);
			assertEquals("", driver.findElement(By.cssSelector("img[alt=\"SECURE!\"]")).getText());			
	        vmmn=driver.findElement(By.partialLinkText("View my message now")).getAttribute("href");
			driver.findElement(By.partialLinkText("Sign out")).click();
			System.out.println(" "+vmmn);
			driver.get(vmmn);
			Functions.login(driver,recipient, pwd_allusers);
			Thread.sleep(5000);
			returnid=Functions.FindIdwithSubject(driver, selenium, subject);
			driver.get(baseUrl+"/transfer/email/view/"+returnid);
			Thread.sleep(5000);
			driver.findElement(By.partialLinkText("download")).click();
			  Thread.sleep(3000);
			Runtime.getRuntime().exec(pathToDownloadHandle+" Opening Save");
			  Thread.sleep(3000);
	        Functions.Logout(driver);
			System.out.println("New user has been on boarded successfully: personalised email option set ON");
			System.out.println("Now deleting local user muunni.24@gmail.com");
			Functions.DeleteUser(driver, selenium, recipient);	
	
	}
	
	@Test
	public void ConfONGenericLFT()throws Exception { // Generic option ON sending LFT
		
		sender=user_allperms;
		recipient=user_noperms;
		subject="Conf ON Generic LFT onboard";
		emailBody=subject;
	    
		
Functions.DeleteFirstThreeRowsGmail(driver, selenium);
		
			Functions.LFTSendGreaterThan25mb(selenium, driver, sender, recipient, subject, emailBody,pwd_allusers,baseUrl);
			
			
			driver.get("http://www.gmail.com");
			selenium.waitForPageToLoad("3000");
			driver.findElement(By.id("Email")).click();
			driver.findElement(By.id("Email")).clear();
			driver.findElement(By.id("Email")).sendKeys("muunni.24");
			driver.findElement(By.id("Passwd")).clear();
			driver.findElement(By.id("Passwd")).sendKeys("123sneha_24");
			driver.findElement(By.id("signIn")).click();
			Thread.sleep(20000);
			selenium.refresh();
			WebElement myele= driver.findElement(By.partialLinkText("Account Required"));
			myele.click();
			myele.sendKeys(Keys.ENTER);
			assertEquals("Please Create an Account!", driver.findElement(By.xpath("//td[2]/h3")).getText());		
	        String vmmn=driver.findElement(By.partialLinkText("Create an account now")).getAttribute("href");
			driver.findElement(By.partialLinkText("Sign out")).click();
			System.out.println(" "+vmmn);
			driver.get(vmmn);
			driver.findElement(By.id("id_password")).clear();
			driver.findElement(By.id("id_password")).sendKeys("123abc");
			driver.findElement(By.id("id_verify_password")).clear();
			driver.findElement(By.id("id_verify_password")).sendKeys("123abc");
			driver.findElement(By.id("id_full_name")).clear();
			driver.findElement(By.id("id_full_name")).sendKeys("muunni.24@gmail.com");
			driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		
			assertEquals("Your account was successfully created! You should receive an email soon asking you to confirm your account activation.", driver.findElement(By.cssSelector("li.success")).getText());
			driver.get("http://www.gmail.com");
			selenium.waitForPageToLoad("3000");
			driver.findElement(By.id("Email")).click();
			driver.findElement(By.id("Email")).clear();
			driver.findElement(By.id("Email")).sendKeys("muunni.24");
			driver.findElement(By.id("Passwd")).clear();
			driver.findElement(By.id("Passwd")).sendKeys("123sneha_24");
			driver.findElement(By.id("signIn")).click();
			Thread.sleep(20000);
			selenium.refresh();
			myele= driver.findElement(By.partialLinkText("Account Creation Confirmation"));
			myele.click();
			myele.sendKeys(Keys.ENTER);
			vmmn=driver.findElement(By.partialLinkText("Confirm - Create My Account")).getAttribute("href");
			driver.findElement(By.partialLinkText("Sign out")).click();
			System.out.println(" "+vmmn);
			driver.get(vmmn);
			Functions.login(driver,recipient, pwd_allusers);
			Thread.sleep(5000);
			String returnid=Functions.FindIdwithSubject(driver, selenium, subject);
			driver.get(baseUrl+"/transfer/email/view/"+returnid);
			Thread.sleep(5000);
			driver.findElement(By.partialLinkText("download")).click();
			  Thread.sleep(3000);
			Runtime.getRuntime().exec(pathToDownloadHandle+" Opening Save");
			  Thread.sleep(3000);
	        Functions.Logout(driver);
			
	        driver.get("http://www.gmail.com");
			selenium.waitForPageToLoad("3000");
			driver.findElement(By.id("Email")).click();
			driver.findElement(By.id("Email")).clear();
			driver.findElement(By.id("Email")).sendKeys("muunni.24");
			driver.findElement(By.id("Passwd")).clear();
			driver.findElement(By.id("Passwd")).sendKeys("123sneha_24");
			driver.findElement(By.id("signIn")).click();
			Thread.sleep(20000);
			selenium.refresh();
			myele= driver.findElement(By.partialLinkText(subject));
			myele.click();
			myele.sendKeys(Keys.ENTER);
					
	        vmmn=driver.findElement(By.partialLinkText("PHPx64.zip")).getAttribute("href");
			driver.findElement(By.partialLinkText("Sign out")).click();
			System.out.println(" "+vmmn);
			driver.get(vmmn);
			Functions.login(driver,recipient, pwd_allusers);
			Thread.sleep(5000);
			returnid=Functions.FindIdwithSubject(driver, selenium, subject);
			driver.get(baseUrl+"/transfer/email/view/"+returnid);
			Thread.sleep(5000);
			driver.findElement(By.partialLinkText("download")).click();
			  Thread.sleep(3000);
			Runtime.getRuntime().exec(pathToDownloadHandle+" Opening Save");
			  Thread.sleep(3000);
	        Functions.Logout(driver);
			System.out.println("New user has been on boarded successfully: personalised email option set ON");
			System.out.println("Now deleting local user muunni.24@gmail.com");
			Functions.DeleteUser(driver, selenium, recipient);	
	
	}
	
	
	
	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}//end of tearDown
}
