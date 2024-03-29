/*
*************************************************MODIFICATION HISTORY************************************************************
Sneha Motadoo 12/08/2010 Created initial script
Purpose: To test permissions on Forward page after saving as Drafts
Desc: This script intends to test user permissions on the forward page
Scope: Testing for only permissions and successful sending of mails will be done. Real world Inbox checking etc. is not part of this test suite.
Requirements: 4 basic users with different permissions are needed to test.
sneha.qa.24@gmail.com: all perms
muunni.24@gmail.com: no perms
snehamtd001@gmail.com: only LFT
snehamtd002@yahoo.com : only SECURE
*/


package sanity_ff;



import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;




import com.thoughtworks.selenium.Selenium;

public class PermsOnForward_DraftsPage {
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
	String pathAutoItScript="C:\\Users\\Sneha\\Desktop\\automation files\\silver_autoit.exe";
	String pathAutoItScript_greaterthan25MB="C:\\Users\\Sneha\\Desktop\\automation files\\silver_autoit_greaterthan25mb.exe";
	WebElement ele=null;
	String emailBody,subject,emailId,DraftId;
	
	
	
	
	
	@Before
	public void setUp() throws Exception {
		
		System.setProperty("webdriver.firefox.bin","C:\\6.0.2\\Mozilla Firefox\\firefox.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
		
	}

	
	
	
	@Test
	public void NonSecureNoattachs() throws Exception {
	
	
		System.out.println("Testing user with LFT perms only");
		subject="TC--129";
		emailBody="TC--129";
		
		Functions.LFTSend(selenium, driver, user_allperms, user_onlylft, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlylft);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);	
		driver.get(baseUrl+"/transfer/email/view/"+emailId);		
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		driver.findElement(By.id("submitter")).click();
		assertEquals("Large File Transfers must contain at least one file attachment.", driver.findElement(By.cssSelector("li.error")).getText());
		System.out.println("PASS:Permission checking passed for LFT user sending mail with no attachs and non-secure.");
        Runtime.getRuntime().exec(pathAutoItScript);
		
		Thread.sleep(6000);
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		ele.click();
		Thread.sleep(2000);
		
		Functions.WaitForUpload(driver);
		driver.findElement(By.id("saver")).click();Thread.sleep(6000);
		driver.get(draftsUrl);selenium.waitForPageToLoad("3000");
		Thread.sleep(6000);
		DraftId=Functions.DraftsFindIdwithSubject(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/draft/"+DraftId);
		selenium.waitForPageToLoad("3000");
		assertEquals("Edit Draft", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(6000); // time for illegal recipients to show up in red
		
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		System.out.println("Testing user with SECURE perms only");
		subject="TC--130";
		emailBody="TC--130";
		
		Functions.LFTSend(selenium, driver, user_allperms, user_onlysecure, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlysecure);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);	
		driver.get(baseUrl+"/transfer/email/view/"+emailId);		
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		
		Functions.LftErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		assertEquals("Large File Transfers must contain at least one file attachment.", driver.findElement(By.cssSelector("li.error")).getText());
		System.out.println("PASS:Permission checking passed for LFT user sending mail with no attachs and non-secure.");
        Runtime.getRuntime().exec(pathAutoItScript);
		
		Thread.sleep(6000);
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		ele.click();
		Thread.sleep(2000);
		
		
		Functions.WaitForUpload(driver);
		driver.findElement(By.id("saver")).click();Thread.sleep(6000);
		driver.get(draftsUrl);selenium.waitForPageToLoad("3000");
		Thread.sleep(6000);
		DraftId=Functions.DraftsFindIdwithSubject(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/draft/"+DraftId);
		selenium.waitForPageToLoad("3000");
		assertEquals("Edit Draft", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(6000); // time for illegal recipients to show up in red
		
		
		Functions.LftErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		
		
		System.out.println("Testing user with ALL perms");
		subject="TC--131";
		emailBody="TC--131";
		
		Functions.LFTSend(selenium, driver, user_allperms, user_allperms, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);	
		driver.get(baseUrl+"/transfer/email/view/"+emailId);		
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		assertEquals("Large File Transfers must contain at least one file attachment.", driver.findElement(By.cssSelector("li.error")).getText());
		System.out.println("PASS:Permission checking passed for LFT user sending mail with no attachs and non-secure.");
        Runtime.getRuntime().exec(pathAutoItScript);
		
		Thread.sleep(6000);
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		ele.click();
		Thread.sleep(2000);
		
		Functions.WaitForUpload(driver);
		driver.findElement(By.id("saver")).click();Thread.sleep(6000);
		driver.get(draftsUrl);selenium.waitForPageToLoad("3000");
		Thread.sleep(6000);
		DraftId=Functions.DraftsFindIdwithSubject(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/draft/"+DraftId);
		selenium.waitForPageToLoad("3000");
		assertEquals("Edit Draft", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(6000); // time for illegal recipients to show up in red
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		
		System.out.println("Testing user with NO perms");
		subject="TC--132";
		emailBody="TC--132";
		
		Functions.LFTSend(selenium, driver, user_allperms, user_noperms, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_noperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);	
		driver.get(baseUrl+"/transfer/email/view/"+emailId);		
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		
		Functions.LftErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		assertEquals("Large File Transfers must contain at least one file attachment.", driver.findElement(By.cssSelector("li.error")).getText());
		System.out.println("PASS:Permission checking passed for LFT user sending mail with no attachs and non-secure.");
        Runtime.getRuntime().exec(pathAutoItScript);
		
		Thread.sleep(6000);
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		ele.click();
		Thread.sleep(2000);
		
		
		Functions.WaitForUpload(driver);
		driver.findElement(By.id("saver")).click();Thread.sleep(6000);
		driver.get(draftsUrl);selenium.waitForPageToLoad("3000");
		Thread.sleep(6000);
		DraftId=Functions.DraftsFindIdwithSubject(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/draft/"+DraftId);
		selenium.waitForPageToLoad("3000");
		assertEquals("Edit Draft", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(6000); // time for illegal recipients to show up in red
		
		Functions.LftErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		driver.findElement(By.id("submitter")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		
		
		
	}// end of Test
	
	@Test
	public void SecureNoattachs() throws Exception {

		System.out.println("Testing user with LFT perms only");
		subject="TC--133";
		emailBody="TC--133";
		
		Functions.SecureSend(selenium, driver, user_allperms, user_onlylft, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlylft);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);	
		driver.get(baseUrl+"/transfer/email/view/"+emailId);		
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		
		// NOTE: Uncomment above line after defect # 314 is fixed
		driver.findElement(By.xpath("//*[@id='secure']")).click();
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		Functions.SecureErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.SecureErrorMssg(driver, user_onlylft);
		
		
		
		
		//Functions.WaitForUpload(driver);
		driver.findElement(By.id("saver")).click();Thread.sleep(6000);
		driver.get(draftsUrl);selenium.waitForPageToLoad("3000");
		Thread.sleep(6000);
		DraftId=Functions.DraftsFindIdwithSubject(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/draft/"+DraftId);
		selenium.waitForPageToLoad("3000");
		assertEquals("Edit Draft", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(6000); // time for illegal recipients to show up in red
		
		Functions.SecureErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.SecureErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		
		
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		System.out.println("Testing user with SECURE perms only");
		subject="TC--134";
		emailBody="TC--134";
		
		Functions.SecureSend(selenium, driver, user_allperms, user_onlysecure, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlysecure);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);	
		driver.get(baseUrl+"/transfer/email/view/"+emailId);		
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		
		// NOTE: Uncomment above line after defect # 314 is fixed
		driver.findElement(By.xpath("//*[@id='secure']")).click();
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		
		
		//Functions.WaitForUpload(driver);
		driver.findElement(By.id("saver")).click();Thread.sleep(6000);
		driver.get(draftsUrl);selenium.waitForPageToLoad("3000");
		Thread.sleep(6000);
		DraftId=Functions.DraftsFindIdwithSubject(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/draft/"+DraftId);
		selenium.waitForPageToLoad("3000");
		assertEquals("Edit Draft", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(6000); // time for illegal recipients to show up in red
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		
		driver.findElement(By.id("submitter")).click();
		
		
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		
		
		System.out.println("Testing user with ALL perms");
		subject="TC--135";
		emailBody="TC--135";
		
		Functions.SecureSend(selenium, driver, user_allperms, user_allperms, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);	
		driver.get(baseUrl+"/transfer/email/view/"+emailId);		
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		
		// NOTE: Uncomment above line after defect # 314 is fixed
		driver.findElement(By.xpath("//*[@id='secure']")).click();
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		
		//Functions.WaitForUpload(driver);
		driver.findElement(By.id("saver")).click();Thread.sleep(6000);
		driver.get(draftsUrl);selenium.waitForPageToLoad("3000");
		Thread.sleep(6000);
		DraftId=Functions.DraftsFindIdwithSubject(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/draft/"+DraftId);
		selenium.waitForPageToLoad("3000");
		assertEquals("Edit Draft", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(6000); // time for illegal recipients to show up in red
		
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		
		
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		
		System.out.println("Testing user with NO perms");
		subject="TC--136";
		emailBody="TC--136";
		
		Functions.SecureSend(selenium, driver, user_allperms, user_noperms, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_noperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);	
		driver.get(baseUrl+"/transfer/email/view/"+emailId);		
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		
		// NOTE: Uncomment above line after defect # 314 is fixed
		driver.findElement(By.xpath("//*[@id='secure']")).click();
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		Functions.SecureErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.SecureErrorMssg(driver, user_onlylft);
		
		//Functions.WaitForUpload(driver);
		driver.findElement(By.id("saver")).click();Thread.sleep(6000);
		driver.get(draftsUrl);selenium.waitForPageToLoad("3000");
		Thread.sleep(6000);
		DraftId=Functions.DraftsFindIdwithSubject(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/draft/"+DraftId);
		selenium.waitForPageToLoad("3000");
		assertEquals("Edit Draft", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(6000); // time for illegal recipients to show up in red
		
		Functions.SecureErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.SecureErrorMssg(driver, user_onlylft);
		
		
		driver.findElement(By.id("submitter")).click();
		
		
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		
		
	}// end of Test
	
	@Test
	public void NonSecureGreater25MB() throws Exception {
		
	
		System.out.println("Testing user with LFT perms only");
		subject="TC--137";
		emailBody="TC--137";
		
		Functions.LFTSendGreaterThan25mb(selenium, driver, user_allperms, user_onlylft, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlylft);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);	
		driver.get(baseUrl+"/transfer/email/view/"+emailId);		
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		assertEquals("47 MB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		Functions.LftErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		
		Functions.WaitForUpload(driver);
		driver.findElement(By.id("saver")).click();Thread.sleep(6000);
		driver.get(draftsUrl);selenium.waitForPageToLoad("3000");
		Thread.sleep(6000);
		DraftId=Functions.DraftsFindIdwithSubject(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/draft/"+DraftId);
		selenium.waitForPageToLoad("3000");
		assertEquals("Edit Draft", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(6000); // time for illegal recipients to show up in red
		
		Functions.LftErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		
		System.out.println("Testing user with SECURE perms only");
		subject="TC--42";
		emailBody="TC--42";
		
		Functions.LFTSendGreaterThan25mb(selenium, driver, user_allperms, user_onlysecure, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlysecure);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);	
		driver.get(baseUrl+"/transfer/email/view/"+emailId);		
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		assertEquals("47 MB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		Functions.LftErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		Functions.WaitForUpload(driver);
		driver.findElement(By.id("saver")).click();Thread.sleep(6000);
		driver.get(draftsUrl);selenium.waitForPageToLoad("3000");
		Thread.sleep(6000);
		DraftId=Functions.DraftsFindIdwithSubject(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/draft/"+DraftId);
		selenium.waitForPageToLoad("3000");
		assertEquals("Edit Draft", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(6000); // time for illegal recipients to show up in red
		Functions.LftErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		
		System.out.println("Testing user with ALL perms");
		subject="TC--139";
		emailBody="TC--139";
		
		Functions.LFTSendGreaterThan25mb(selenium, driver, user_allperms, user_allperms, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);	
		driver.get(baseUrl+"/transfer/email/view/"+emailId);		
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		assertEquals("47 MB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		
		Functions.WaitForUpload(driver);
		driver.findElement(By.id("saver")).click();Thread.sleep(6000);
		driver.get(draftsUrl);selenium.waitForPageToLoad("3000");
		Thread.sleep(6000);
		DraftId=Functions.DraftsFindIdwithSubject(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/draft/"+DraftId);
		selenium.waitForPageToLoad("3000");
		assertEquals("Edit Draft", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(6000); // time for illegal recipients to show up in red
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		
		System.out.println("Testing user with NO perms");
		subject="TC--140";
		emailBody="TC--140";
		
		Functions.LFTSendGreaterThan25mb(selenium, driver, user_allperms, user_noperms, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_noperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);	
		driver.get(baseUrl+"/transfer/email/view/"+emailId);		
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		assertEquals("47 MB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		Functions.LftErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		
		Functions.WaitForUpload(driver);
		driver.findElement(By.id("saver")).click();Thread.sleep(6000);
		driver.get(draftsUrl);selenium.waitForPageToLoad("3000");
		Thread.sleep(6000);
		DraftId=Functions.DraftsFindIdwithSubject(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/draft/"+DraftId);
		selenium.waitForPageToLoad("3000");
		assertEquals("Edit Draft", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(6000); // time for illegal recipients to show up in red
		
		Functions.LftErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		driver.findElement(By.id("submitter")).click();
		
		
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
	}// end of Test
	
	@Test
	public void SecureLesser25MB() throws Exception {
		
		System.out.println("Testing user with LFT perms only");
		subject="TC--141";
		emailBody="TC--141";
		
		Functions.SecureSendLessthan25MB(selenium, driver, user_allperms, user_onlylft, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlylft);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);	
		driver.get(baseUrl+"/transfer/email/view/"+emailId);		
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		
		// NOTE: Uncomment above line after defect # 314 is fixed
		
		assertEquals("267 KB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
		driver.findElement(By.xpath("//*[@id='secure']")).click();
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		Functions.SecureErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.SecureErrorMssg(driver, user_onlylft);
		
		Functions.WaitForUpload(driver);
		driver.findElement(By.id("saver")).click();Thread.sleep(6000);
		driver.get(draftsUrl);selenium.waitForPageToLoad("3000");
		Thread.sleep(6000);
		DraftId=Functions.DraftsFindIdwithSubject(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/draft/"+DraftId);
		selenium.waitForPageToLoad("3000");
		assertEquals("Edit Draft", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(6000); // time for illegal recipients to show up in red
		
		Functions.SecureErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.SecureErrorMssg(driver, user_onlylft);
		driver.findElement(By.id("submitter")).click();
		
		
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		
		System.out.println("Testing user with SECURE perms only");
		subject="TC--142";
		emailBody="TC--142";
		
		Functions.SecureSendLessthan25MB(selenium, driver, user_allperms, user_onlysecure, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlysecure);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);	
		driver.get(baseUrl+"/transfer/email/view/"+emailId);		
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		
		// NOTE: Uncomment above line after defect # 314 is fixed
		
		assertEquals("267 KB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
		driver.findElement(By.xpath("//*[@id='secure']")).click();
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		Functions.WaitForUpload(driver);
		driver.findElement(By.id("saver")).click();Thread.sleep(6000);
		driver.get(draftsUrl);selenium.waitForPageToLoad("3000");
		Thread.sleep(6000);
		DraftId=Functions.DraftsFindIdwithSubject(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/draft/"+DraftId);
		selenium.waitForPageToLoad("3000");
		assertEquals("Edit Draft", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(6000); // time for illegal recipients to show up in red
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		
		
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		
		
		System.out.println("Testing user with ALL perms");
		subject="TC--143";
		emailBody="TC--143";
		
		Functions.SecureSendLessthan25MB(selenium, driver, user_allperms, user_allperms, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);	
		driver.get(baseUrl+"/transfer/email/view/"+emailId);		
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		
		// NOTE: Uncomment above line after defect # 314 is fixed
		
		assertEquals("267 KB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
		driver.findElement(By.xpath("//*[@id='secure']")).click();
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		
		Functions.WaitForUpload(driver);
		driver.findElement(By.id("saver")).click();Thread.sleep(6000);
		driver.get(draftsUrl);selenium.waitForPageToLoad("3000");
		Thread.sleep(6000);
		DraftId=Functions.DraftsFindIdwithSubject(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/draft/"+DraftId);
		selenium.waitForPageToLoad("3000");
		assertEquals("Edit Draft", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(6000); // time for illegal recipients to show up in red
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		driver.findElement(By.id("submitter")).click();
		
		
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		
		System.out.println("Testing user with NO perms");
		subject="TC--144";
		emailBody="TC--144";
		
		Functions.SecureSendLessthan25MB(selenium, driver, user_allperms, user_noperms, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_noperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);	
		driver.get(baseUrl+"/transfer/email/view/"+emailId);		
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		
		// NOTE: Uncomment above line after defect # 314 is fixed
		
		assertEquals("267 KB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
		driver.findElement(By.xpath("//*[@id='secure']")).click();
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		Functions.SecureErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.SecureErrorMssg(driver, user_onlylft);
		
		
		Functions.WaitForUpload(driver);
		driver.findElement(By.id("saver")).click();Thread.sleep(6000);
		driver.get(draftsUrl);selenium.waitForPageToLoad("3000");
		Thread.sleep(6000);
		DraftId=Functions.DraftsFindIdwithSubject(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/draft/"+DraftId);
		selenium.waitForPageToLoad("3000");
		assertEquals("Edit Draft", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(6000); // time for illegal recipients to show up in red
		Functions.SecureErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.SecureErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		
		
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		
		
	}// end of Test
	
	@Test
	public void NonSecureLesser25MB() throws Exception {
		
		System.out.println("Testing user with LFT perms only");
		subject="TC--145";
		emailBody="TC--145";
		
		Functions.LFTSend(selenium, driver, user_allperms, user_onlylft, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlylft);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);	
		driver.get(baseUrl+"/transfer/email/view/"+emailId);		
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		assertEquals("267 KB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		Functions.LftErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		
		Functions.WaitForUpload(driver);
		driver.findElement(By.id("saver")).click();Thread.sleep(6000);
		driver.get(draftsUrl);selenium.waitForPageToLoad("3000");
		Thread.sleep(6000);
		DraftId=Functions.DraftsFindIdwithSubject(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/draft/"+DraftId);
		selenium.waitForPageToLoad("3000");
		assertEquals("Edit Draft", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(6000); // time for illegal recipients to show up in red
		
		Functions.LftErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		
		System.out.println("Testing user with SECURE perms only");
		subject="TC--146";
		emailBody="TC--146";
		
		Functions.LFTSend(selenium, driver, user_allperms, user_onlysecure, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlysecure);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);	
		driver.get(baseUrl+"/transfer/email/view/"+emailId);		
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		assertEquals("267 KB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		Functions.LftErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		
		Functions.WaitForUpload(driver);
		driver.findElement(By.id("saver")).click();Thread.sleep(6000);
		driver.get(draftsUrl);selenium.waitForPageToLoad("3000");
		Thread.sleep(6000);
		DraftId=Functions.DraftsFindIdwithSubject(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/draft/"+DraftId);
		selenium.waitForPageToLoad("3000");
		assertEquals("Edit Draft", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(6000); // time for illegal recipients to show up in red
		
		Functions.LftErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		driver.findElement(By.id("submitter")).click();
		
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		System.out.println("Testing user with ALL perms only");
		subject="TC--147";
		emailBody="TC--147";
		
		Functions.LFTSend(selenium, driver, user_allperms, user_allperms, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);	
		driver.get(baseUrl+"/transfer/email/view/"+emailId);		
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		assertEquals("267 KB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		
		Functions.WaitForUpload(driver);
		driver.findElement(By.id("saver")).click();Thread.sleep(6000);
		driver.get(draftsUrl);selenium.waitForPageToLoad("3000");
		Thread.sleep(6000);
		DraftId=Functions.DraftsFindIdwithSubject(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/draft/"+DraftId);
		selenium.waitForPageToLoad("3000");
		assertEquals("Edit Draft", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(6000); // time for illegal recipients to show up in red
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		
		
		System.out.println("Testing user with No perms");
		subject="TC--148";
		emailBody="TC--148";
		
		Functions.LFTSend(selenium, driver, user_allperms, user_noperms, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_noperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);	
		driver.get(baseUrl+"/transfer/email/view/"+emailId);		
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		assertEquals("267 KB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		Functions.LftErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		
		
		Functions.WaitForUpload(driver);
		driver.findElement(By.id("saver")).click();Thread.sleep(6000);
		driver.get(draftsUrl);selenium.waitForPageToLoad("3000");
		Thread.sleep(6000);
		DraftId=Functions.DraftsFindIdwithSubject(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/draft/"+DraftId);
		selenium.waitForPageToLoad("3000");
		assertEquals("Edit Draft", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(6000); // time for illegal recipients to show up in red
		
		Functions.LftErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		
		
	}// end of Test
	
	@Test
	public void SecureGreater25MB() throws Exception {
		
		System.out.println("Testing user with LFT perms only");
		subject="TC--149";
		emailBody="TC--149";
		
		Functions.SecureSendGreaterthan25MB(selenium, driver, user_allperms, user_onlylft, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlylft);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);	
		driver.get(baseUrl+"/transfer/email/view/"+emailId);		
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		
		// NOTE: Uncomment above line after defect # 314 is fixed
		
		assertEquals("47 MB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
		driver.findElement(By.xpath("//*[@id='secure']")).click();
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		Functions.SecureErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.SecureErrorMssg(driver, user_onlylft);
		
		
		Functions.WaitForUpload(driver);
		driver.findElement(By.id("saver")).click();Thread.sleep(6000);
		driver.get(draftsUrl);selenium.waitForPageToLoad("3000");
		Thread.sleep(6000);
		DraftId=Functions.DraftsFindIdwithSubject(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/draft/"+DraftId);
		selenium.waitForPageToLoad("3000");
		assertEquals("Edit Draft", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(6000); // time for illegal recipients to show up in red
		
		Functions.SecureErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.SecureErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		
		
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		
		System.out.println("Testing user with SECURE perms only");
		subject="TC--150";
		emailBody="TC--150";
		
		Functions.SecureSendGreaterthan25MB(selenium, driver, user_allperms, user_onlysecure, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_onlysecure);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);	
		driver.get(baseUrl+"/transfer/email/view/"+emailId);		
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		
		// NOTE: Uncomment above line after defect # 314 is fixed
		
		assertEquals("47 MB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
		driver.findElement(By.xpath("//*[@id='secure']")).click();
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		Functions.LftErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.SecureErrorMssg(driver, user_onlylft);
		
		
		Functions.WaitForUpload(driver);
		driver.findElement(By.id("saver")).click();Thread.sleep(6000);
		driver.get(draftsUrl);selenium.waitForPageToLoad("3000");
		Thread.sleep(6000);
		DraftId=Functions.DraftsFindIdwithSubject(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/draft/"+DraftId);
		selenium.waitForPageToLoad("3000");
		assertEquals("Edit Draft", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(6000); // time for illegal recipients to show up in red
		
		Functions.LftErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.SecureErrorMssg(driver, user_onlylft);
		
		driver.findElement(By.id("submitter")).click();
		
		
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		
		System.out.println("Testing user with ALL perms");
		subject="TC--151";
		emailBody="TC--151";
		
		Functions.SecureSendGreaterthan25MB(selenium, driver, user_allperms, user_allperms, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);	
		driver.get(baseUrl+"/transfer/email/view/"+emailId);		
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		
		// NOTE: Uncomment above line after defect # 314 is fixed
		
		assertEquals("47 MB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
		driver.findElement(By.xpath("//*[@id='secure']")).click();
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		
		
		Functions.WaitForUpload(driver);
		driver.findElement(By.id("saver")).click();Thread.sleep(6000);
		driver.get(draftsUrl);selenium.waitForPageToLoad("3000");
		Thread.sleep(6000);
		DraftId=Functions.DraftsFindIdwithSubject(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/draft/"+DraftId);
		selenium.waitForPageToLoad("3000");
		assertEquals("Edit Draft", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(6000); // time for illegal recipients to show up in red
		
		
		Functions.NoErrorMssg(driver, user_noperms);
		Functions.NoErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.NoErrorMssg(driver, user_onlylft);
		driver.findElement(By.id("submitter")).click();
		
		
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		
		System.out.println("Testing user with NO perms");
		subject="TC--152";
		emailBody="TC--152";
		
		Functions.SecureSendGreaterthan25MB(selenium, driver, user_allperms, user_noperms, subject, emailBody,pwd_allusers, baseUrl);
		System.out.println("Navigating to base URL");
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_noperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		Thread.sleep(5000);// time for elements to load inside Inbox
		emailId=Functions.FindIdwithSubject(driver, selenium,subject);	
		driver.get(baseUrl+"/transfer/email/view/"+emailId);		
		assertEquals("Forward", driver.findElement(By.id("forward")).getText());
		driver.findElement(By.id("forward")).click(); selenium.waitForPageToLoad("3000");
		assertEquals("Forward", driver.findElement(By.id("heading")).getText());
		assertEquals("Fwd: "+subject, driver.findElement(By.id("id_subject")).getAttribute("value"));
		//driver.findElement(By.cssSelector("div.ui-icon.ui-icon-circle-check")).click();// since requirement says no attachments deleting the attachment
		//assertEquals("true",driver.findElement(By.xpath("//*[@id='secure']")).getAttribute("checked")); // checking if reply to secure is secure by default.
		
		// NOTE: Uncomment above line after defect # 314 is fixed
		
		assertEquals("47 MB", driver.findElement(By.cssSelector("span.plupload_total_file_size")).getText()); // to make sure that original file is still present
		driver.findElement(By.xpath("//*[@id='secure']")).click();
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(2000);
		Functions.LftAndSecureErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.SecureErrorMssg(driver, user_onlylft);
		
		
		Functions.WaitForUpload(driver);
		driver.findElement(By.id("saver")).click();Thread.sleep(6000);
		driver.get(draftsUrl);selenium.waitForPageToLoad("3000");
		Thread.sleep(6000);
		DraftId=Functions.DraftsFindIdwithSubject(driver, selenium, subject);
		driver.get(baseUrl+"/transfer/draft/"+DraftId);
		selenium.waitForPageToLoad("3000");
		assertEquals("Edit Draft", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.id("massinsert")).click();
		driver.findElement(By.id("massintext")).clear();
		driver.findElement(By.id("massintext")).sendKeys(user_allperms+","+user_noperms+","+user_onlysecure+","+user_onlylft);
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(6000); // time for illegal recipients to show up in red
		
		
		Functions.LftAndSecureErrorMssg(driver, user_noperms);
		Functions.LftErrorMssg(driver, user_onlysecure);
		Functions.NoErrorMssg(driver, user_allperms);
		Functions.SecureErrorMssg(driver, user_onlylft);
		driver.findElement(By.id("submitter")).click();
		
		
		selenium.waitForPageToLoad("3000");
		assertEquals("Email sent to your outbox and enqueued for delivery.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("PASS:Mail successfully sent.");
		driver.findElement(By.id("logout")).click();
		
		
		// ************************************************************************
		
	}// end of Test

	
	
	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}//end of tearDown

}// end of PermsOnComposePage class