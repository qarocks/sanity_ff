package sanity_ff;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebElement.*;
import java.util.List;

import com.thoughtworks.selenium.Selenium;

import org.openqa.selenium.By;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class Functions{
	
	public final static String baseUrl="http://10.21.7.179";
	public final static String compose_url=baseUrl+"/transfer/compose";
	public final static String inbox_url=baseUrl+"/transfer/inbox";
	public final static String sent_url=baseUrl+"/transfer/sent";
	public final static String outbox_url=baseUrl+"/transfer/outbox";
	public final static String drafts_url=baseUrl+"/transfer/drafts";
	public final static String baseFileLocation="C:\\Users\\smotado\\Desktop\\";
	public final static String topFileLocation="automation files\\";
	public final static String pathToLessthan25MbFilesScript=baseFileLocation+topFileLocation+"silver_autoit.exe";
	public final static String pathToGreaterthan25MbFilesScript=baseFileLocation+topFileLocation+"silver_autoit_greaterthan25mb.exe";
	public final static String pathToDownloadFileHandler=baseFileLocation+topFileLocation+"download_handle.exe";
	public final static String pathTo25Files=Functions.baseFileLocation+Functions.topFileLocation+"max file upload.exe";
	public static final String pwd_allusers="123abc";
	

public static void MyWaitfunc(WebDriver driver,String element) throws Exception{
		
		for (int second = 0;; second++) {
			if (second >= 60) {fail("timeout");}
			try { if (driver.findElement(By.xpath(element)).isDisplayed()) break; } catch (Exception e) {}
			Thread.sleep(1000);
		}	
		
	}

public static void WaitForUpload(WebDriver driver) throws Exception{
	
	for (int second = 0;; second++) {
		Thread.sleep(1000);
		if (second >= 202000) {System.out.println("I fail here!");fail("timeout");}
		try { 
			if(driver.findElement(By.cssSelector("span.plupload_total_status")).getText().equalsIgnoreCase("100%"))
		       {Thread.sleep(2000);break;}
		
		else
		{continue;}
		
		} 
		
		catch (Exception e) {}
		Thread.sleep(1000);
	}	
	
}

public static boolean doesWebElementExist(WebDriver driver, By selector) 
{ 
       try { 
               driver.findElement(selector); 
           return true; 
       } catch (NoSuchElementException e) { 
           return false; 
       } 
}   

public static void setExpirationToNever(WebDriver driver)
{
	
	driver.findElement(By.id("statuspicker")).clear();
	String to_check=driver.findElement(By.id("statuspicker")).getAttribute("class");
	assertEquals(to_check,"hasDatepicker custom");
	
	
}

public static void setCustomExpiration(WebDriver driver, String date)
{
	
	driver.findElement(By.id("statuspicker")).clear();
	driver.findElement(By.id("statuspicker")).sendKeys(date);
	
	
	
}

public static void LFTLessthan25mbNeverExpire(Selenium selenium,WebDriver driver,String sender,String recipient,String subject,String emailBody,String senderPwd,String baseUrl) throws Exception {
	  
		selenium.open(baseUrl);
		selenium.type("id=id_username", sender);
		selenium.type("id=id_password", senderPwd);
		selenium.click("css=input[type=\"submit\"]");
		
		selenium.waitForPageToLoad("2000");

		System.out.println("First: The page title is "+selenium.getTitle());
		// code to upload file
		
		driver.get(compose_url);
		
			selenium.waitForPageToLoad("3000");
			
			
			
			Runtime.getRuntime().exec(pathToLessthan25MbFilesScript);
			
			
			
			Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
			WebElement ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
			Thread.sleep(2000);
			ele.click();
			
			
			Thread.sleep(3000);
			driver.findElement(By.id("addrin")).sendKeys(recipient);
			
			
			driver.findElement(By.id("id_subject")).sendKeys(subject);

			
			setExpirationToNever(driver);
			
			
							
				driver.findElement(By.id("addrsubmit")).click();
			
				
				
				driver.switchTo().frame("id_body_ifr");
				
				selenium.typeKeys("//body[@id='tinymce']", "Finally wohoooo!!");
				 driver.switchTo().defaultContent();
				
				driver.findElement(By.id("submitter")).click();
			
			
			
			
		// to check if mail was sent successfully
	     
	     String success_str_xpath="//html/body/div/div[2]/div[4]/ul/li";
	     
	     Functions.MyWaitfunc(driver,success_str_xpath);
	     if((Functions.doesWebElementExist(driver,By.xpath(success_str_xpath))) && (driver.findElement(By.xpath(success_str_xpath)).getText().contains("Email sent to your outbox and enqueued for delivery.")))
				
			System.out.println("SUCCESS:Mail successfully sent !");
			else
				{ System.out.println("FAIL:Mail NOT SENT !"); System.out.println(driver.findElement(By.xpath(success_str_xpath)).getText());Exception e1 = new Exception("This case FAILS");
				throw e1;}
		
	     driver.findElement(By.id("logout")).click();

	}//end of LFTsend method


public static void DeleteFirstThreeRowsGmail(WebDriver driver,Selenium selenium)throws Exception
		{
	
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
	driver.findElement(By.xpath("//html/body/table[2]/tbody/tr/td[2]/table/tbody/tr/td[2]/form/table[2]/tbody/tr/td/input")).click();
	driver.findElement(By.xpath("//html/body/table[2]/tbody/tr/td[2]/table/tbody/tr/td[2]/form/table[2]/tbody/tr[2]/td/input")).click();
	driver.findElement(By.xpath("//html/body/table[2]/tbody/tr/td[2]/table/tbody/tr/td[2]/form/table[2]/tbody/tr[3]/td/input")).click();
	driver.findElement(By.xpath("//html/body/table[2]/tbody/tr/td[2]/table/tbody/tr/td[2]/form/table[2]/tbody/tr[4]/td/input")).click();
	driver.findElement(By.xpath("//html/body/table[2]/tbody/tr/td[2]/table/tbody/tr/td[2]/form/table[2]/tbody/tr[5]/td/input")).click();
	driver.findElement(By.name("nvp_a_tr")).click();
	selenium.waitForPageToLoad("10000");
	driver.findElement(By.partialLinkText("Sign out")).click();
		}


public static void login(WebDriver driver,String id_username,String pwd_allusers)
{
	driver.get(baseUrl);
	driver.findElement(By.id("id_username")).clear();
	driver.findElement(By.id("id_username")).sendKeys(id_username);
	driver.findElement(By.id("id_password")).clear();
	driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
	driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	assertEquals("Inbox", driver.findElement(By.id("heading")).getText());	

}


public static void Adminlogin(WebDriver driver,String id_username,String pwd_allusers)
{
	driver.get(baseUrl);
	driver.findElement(By.id("id_username")).clear();
	driver.findElement(By.id("id_username")).sendKeys(id_username);
	driver.findElement(By.id("id_password")).clear();
	driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
	driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		

}


public static void LFTLessthan25mbCustomExpire(String date,Selenium selenium,WebDriver driver,String sender,String recipient,String subject,String emailBody,String senderPwd,String baseUrl) throws Exception {
	  
	selenium.open(baseUrl);
	selenium.type("id=id_username", sender);
	selenium.type("id=id_password", senderPwd);
	selenium.click("css=input[type=\"submit\"]");
	
	selenium.waitForPageToLoad("2000");

	System.out.println("First: The page title is "+selenium.getTitle());
	// code to upload file
	
	driver.get(compose_url);
	
		selenium.waitForPageToLoad("3000");
		
		
		
		Runtime.getRuntime().exec(pathToLessthan25MbFilesScript);
		
		
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		WebElement ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		Thread.sleep(2000);
		ele.click();
		
		
		Thread.sleep(3000);
		driver.findElement(By.id("addrin")).sendKeys(recipient);
		
		
		driver.findElement(By.id("id_subject")).sendKeys(subject);

		
		setCustomExpiration(driver, date);
		
		
						
			driver.findElement(By.id("addrsubmit")).click();
		
			
			
			driver.switchTo().frame("id_body_ifr");
			
			selenium.typeKeys("//body[@id='tinymce']", "Finally wohoooo!!");
			 driver.switchTo().defaultContent();
			
			driver.findElement(By.id("submitter")).click();
		
		
		
		
	// to check if mail was sent successfully
     
     String success_str_xpath="//html/body/div/div[2]/div[4]/ul/li";
     
     Functions.MyWaitfunc(driver,success_str_xpath);
     if((Functions.doesWebElementExist(driver,By.xpath(success_str_xpath))) && (driver.findElement(By.xpath(success_str_xpath)).getText().contains("Email sent to your outbox and enqueued for delivery.")))
			
		System.out.println("SUCCESS:Mail successfully sent !");
		else
			{ System.out.println("FAIL:Mail NOT SENT !"); System.out.println(driver.findElement(By.xpath(success_str_xpath)).getText());Exception e1 = new Exception("This case FAILS");
			throw e1;}
	
     driver.findElement(By.id("logout")).click();

}//end of LFTsend method





public static void LFTGreaterThan25mbNeverExpire(Selenium selenium,WebDriver driver,String sender,String recipient,String subject,String emailBody,String senderPwd,String baseUrl) throws Exception {
	
	
	
	
	selenium.open(baseUrl);
	selenium.type("id=id_username", sender);
	selenium.type("id=id_password", senderPwd);
	selenium.click("css=input[type=\"submit\"]");
	
	selenium.waitForPageToLoad("2000");

	System.out.println("First: The page title is "+selenium.getTitle());
	// code to upload file
	
	driver.get(compose_url);
	
		selenium.waitForPageToLoad("3000");
		
		
		
		Runtime.getRuntime().exec(pathToGreaterthan25MbFilesScript);
		
		
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		WebElement ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		Thread.sleep(2000);
		ele.click();
		
		
		Thread.sleep(3000);
		driver.findElement(By.id("addrin")).sendKeys(recipient);
		
		
		driver.findElement(By.id("id_subject")).sendKeys(subject);	
		setExpirationToNever(driver);				
			driver.findElement(By.id("addrsubmit")).click();
		
			
			
			driver.switchTo().frame("id_body_ifr");
			
			selenium.typeKeys("//body[@id='tinymce']", "Finally wohoooo!!");
			 driver.switchTo().defaultContent();
			
			driver.findElement(By.id("submitter")).click();
		
		
		
		
	// to check if mail was sent successfully
     
     String success_str_xpath="//html/body/div/div[2]/div[4]/ul/li";
     
     Functions.MyWaitfunc(driver,success_str_xpath);
     if((Functions.doesWebElementExist(driver,By.xpath(success_str_xpath))) && (driver.findElement(By.xpath(success_str_xpath)).getText().contains("Email sent to your outbox and enqueued for delivery.")))
			
		System.out.println("SUCCESS:Mail successfully sent !");
		else
			{ System.out.println("FAIL:Mail NOT SENT !"); System.out.println(driver.findElement(By.xpath(success_str_xpath)).getText());Exception e1 = new Exception("This case FAILS");
			throw e1;}
	
     driver.findElement(By.id("logout")).click();

}//end of LFTSendGreaterThan25mb method





public static void SecureSend(Selenium selenium,WebDriver driver,String sender,String recipient,String subject,String emailBody,String senderPwd,String baseUrl) throws Exception {
	
	selenium.open(baseUrl);
	selenium.type("id=id_username", sender);
	selenium.type("id=id_password", senderPwd);
	selenium.click("css=input[type=\"submit\"]");
	
	selenium.waitForPageToLoad("2000");

	System.out.println("First: The page title is "+selenium.getTitle());
	// code to upload file
	driver.get(compose_url);
	selenium.waitForPageToLoad("3000");
    driver.findElement(By.id("secure")).click();
    driver.findElement(By.id("addrin")).sendKeys(recipient);
	driver.findElement(By.id("id_subject")).sendKeys(subject);			
	driver.findElement(By.id("addrsubmit")).click();

	driver.switchTo().frame("id_body_ifr");
			
	selenium.typeKeys("//body[@id='tinymce']", emailBody);
	driver.switchTo().defaultContent();
			
	driver.findElement(By.id("submitter")).click();
		
			
    String success_str_xpath="//html/body/div/div[2]/div[4]/ul/li";
     
     Functions.MyWaitfunc(driver,success_str_xpath);
     if((Functions.doesWebElementExist(driver,By.xpath(success_str_xpath))) && (driver.findElement(By.xpath(success_str_xpath)).getText().contains("Email sent to your outbox and enqueued for delivery.")))
			
		System.out.println("SUCCESS:Mail successfully sent !");
		else
			{ System.out.println("FAIL:Mail NOT SENT !"); System.out.println(driver.findElement(By.xpath(success_str_xpath)).getText());Exception e1 = new Exception("This case FAILS");
			throw e1;}
	
     driver.findElement(By.id("logout")).click();
     
}//end of secureSend method


public static void Logout(WebDriver driver)
{   
	System.out.println("User now logging out");
	driver.findElement(By.id("logout")).click();
	}

public static void SecureSendLessthan25MB(Selenium selenium,WebDriver driver,String sender,String recipient,String subject,String emailBody,String senderPwd,String baseUrl) throws Exception {
	
	selenium.open(baseUrl);
	selenium.type("id=id_username", sender);
	selenium.type("id=id_password", senderPwd);
	selenium.click("css=input[type=\"submit\"]");
	
	selenium.waitForPageToLoad("2000");

	System.out.println("First: The page title is "+selenium.getTitle());
	// code to upload file
	driver.get(compose_url);
	selenium.waitForPageToLoad("3000");
    driver.findElement(By.id("secure")).click();
    driver.findElement(By.id("addrin")).sendKeys(recipient);
	driver.findElement(By.id("id_subject")).sendKeys(subject);			
	driver.findElement(By.id("addrsubmit")).click();

	driver.switchTo().frame("id_body_ifr");
			
	selenium.typeKeys("//body[@id='tinymce']", emailBody);
	driver.switchTo().defaultContent();
	Runtime.getRuntime().exec(pathToLessthan25MbFilesScript);
	
	
	
	Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
	WebElement ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
	Thread.sleep(2000);
	ele.click();
	
	
	Thread.sleep(3000);
			
	driver.findElement(By.id("submitter")).click();
		
			
    String success_str_xpath="//html/body/div/div[2]/div[4]/ul/li";
     
     Functions.MyWaitfunc(driver,success_str_xpath);
     if((Functions.doesWebElementExist(driver,By.xpath(success_str_xpath))) && (driver.findElement(By.xpath(success_str_xpath)).getText().contains("Email sent to your outbox and enqueued for delivery.")))
			
		System.out.println("SUCCESS:Mail successfully sent !");
		else
			{ System.out.println("FAIL:Mail NOT SENT !"); System.out.println(driver.findElement(By.xpath(success_str_xpath)).getText());Exception e1 = new Exception("This case FAILS");
			throw e1;}
	
     driver.findElement(By.id("logout")).click();
     
}//end of secureSendlessthan25MB method

public static void SecureSendGreaterthan25MB(Selenium selenium,WebDriver driver,String sender,String recipient,String subject,String emailBody,String senderPwd,String baseUrl) throws Exception {
	
	selenium.open(baseUrl);
	selenium.type("id=id_username", sender);
	selenium.type("id=id_password", senderPwd);
	selenium.click("css=input[type=\"submit\"]");
	
	selenium.waitForPageToLoad("2000");

	System.out.println("First: The page title is "+selenium.getTitle());
	// code to upload file
	driver.get(compose_url);
	selenium.waitForPageToLoad("3000");
    driver.findElement(By.id("secure")).click();
    driver.findElement(By.id("addrin")).sendKeys(recipient);
	driver.findElement(By.id("id_subject")).sendKeys(subject);			
	driver.findElement(By.id("addrsubmit")).click();

	driver.switchTo().frame("id_body_ifr");
			
	selenium.typeKeys("//body[@id='tinymce']", emailBody);
	driver.switchTo().defaultContent();
	Runtime.getRuntime().exec(pathToGreaterthan25MbFilesScript);
	
	
	
	Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
	WebElement ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
	Thread.sleep(2000);
	ele.click();
	
	
	Thread.sleep(3000);
			
	driver.findElement(By.id("submitter")).click();
		
			
    String success_str_xpath="//html/body/div/div[2]/div[4]/ul/li";
     
     Functions.MyWaitfunc(driver,success_str_xpath);
     if((Functions.doesWebElementExist(driver,By.xpath(success_str_xpath))) && (driver.findElement(By.xpath(success_str_xpath)).getText().contains("Email sent to your outbox and enqueued for delivery.")))
			
		System.out.println("SUCCESS:Mail successfully sent !");
		else
			{ System.out.println("FAIL:Mail NOT SENT !"); System.out.println(driver.findElement(By.xpath(success_str_xpath)).getText());Exception e1 = new Exception("This case FAILS");
			throw e1;}
	
     driver.findElement(By.id("logout")).click();
     
}//end of secureSendGreaterthan25MB method

public static void DeleteUser(WebDriver driver,Selenium selenium,String user)throws Exception
{
	
	driver.get(baseUrl);
	driver.findElement(By.id("id_username")).clear();
	driver.findElement(By.id("id_username")).sendKeys("admin");
	driver.findElement(By.id("id_password")).clear();
	driver.findElement(By.id("id_password")).sendKeys("123abc");
	driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	driver.findElement(By.id("users")).click();
	driver.findElement(By.id("local")).click();
	assertEquals("muunni.24@gmail.com", driver.findElement(By.xpath("//tr[@id='muunni.24@gmail.com']/td")).getText());
    driver.findElement((By.xpath("//html/body/div/div[2]/div[4]/div/div[3]/div[3]/div/table/tbody/tr[2]/td[4]/form/input[2]"))).click();
    Thread.sleep(5000);
    Alert alert=driver.switchTo().alert();
    alert.accept();
    
	


}

public static void AdminLogin(WebDriver driver)
{
	driver.get(baseUrl);
	driver.findElement(By.id("id_username")).clear();
	driver.findElement(By.id("id_username")).sendKeys("admin");
	driver.findElement(By.id("id_password")).clear();
	driver.findElement(By.id("id_password")).sendKeys("123abc");
	driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();	
}



public static String FindIdwithSubject(WebDriver driver,Selenium selenium,String subject) throws InterruptedException
{
	  int flag=0;
	
	String EmailId=null;
	Thread.sleep(5000);
	driver.get(inbox_url);
	Thread.sleep(10000);
	driver.get(inbox_url);
	Thread.sleep(7000);
	WebElement select = driver.findElement(By.xpath("//*[@id='EmailInbox']"));
	List<WebElement> options = select.findElements(By.tagName("tbody"));
	  System.out.println("The count for tbody is "+options.size());
	   for (WebElement option : options) {
		 
		   List<WebElement> sub_options = option.findElements(By.tagName("tr"));
		   System.out.println("The count for tr is "+sub_options.size());
		   for (WebElement sub_sub_option : sub_options) {
			   List<WebElement> sub_sub_options = sub_sub_option.findElements(By.tagName("td"));
			   System.out.println("The count for td is "+sub_sub_options.size());
			   for (WebElement sub_sub_sub_option : sub_sub_options) {
				   
				   System.out.println("i was here!");
				   System.out.println(""+sub_sub_sub_option.getText());
				   
				   if(sub_sub_sub_option.getText().contains(subject))
					   
				   {
					   EmailId=sub_sub_option.getAttribute("id");
					   flag=1;break;
					   
				   }
				   
				if(flag==1) break;
				   
				   
			   }
			   
			   if(flag==1) break; 
		   }
		   if(flag==1) break;
		   }
		   
	   
	
	return EmailId;
	
	


}// end of FindIdwithSubject




public static String FindIdwithSubjectInOutbox(WebDriver driver,Selenium selenium,String subject) throws InterruptedException
{
	  int flag=0;
	
	String EmailId=null;
	Thread.sleep(5000);
	driver.get(outbox_url);
	Thread.sleep(10000);
	driver.get(outbox_url);
	Thread.sleep(7000);
	WebElement select = driver.findElement(By.xpath("//*[@id='EmailOutbox']"));
	List<WebElement> options = select.findElements(By.tagName("tbody"));
	  System.out.println("The count for tbody is "+options.size());
	   for (WebElement option : options) {
		 
		   List<WebElement> sub_options = option.findElements(By.tagName("tr"));
		   System.out.println("The count for tr is "+sub_options.size());
		   for (WebElement sub_sub_option : sub_options) {
			   List<WebElement> sub_sub_options = sub_sub_option.findElements(By.tagName("td"));
			   System.out.println("The count for td is "+sub_sub_options.size());
			   for (WebElement sub_sub_sub_option : sub_sub_options) {
				   
				   System.out.println("i was here!");
				   System.out.println(""+sub_sub_sub_option.getText());
				   
				   if(sub_sub_sub_option.getText().contains(subject))
					   
				   {
					   EmailId=sub_sub_option.getAttribute("id");
					   flag=1;break;
					   
				   }
				   
				if(flag==1) break;
				   
				   
			   }
			   
			   if(flag==1) break; 
		   }
		   if(flag==1) break;
		   }
		   
	   
	
	return EmailId;
	
	


}// end of FindIdwithSubject





public static String FindIdwithSubjectInSent(WebDriver driver,Selenium selenium,String subject) throws InterruptedException
{
	  int flag=0;
	
	String EmailId=null;
	Thread.sleep(5000);
	driver.get(sent_url);
	Thread.sleep(10000);
	driver.get(sent_url);
	Thread.sleep(7000);
	WebElement select = driver.findElement(By.xpath("//*[@id='EmailSentMail']"));
	List<WebElement> options = select.findElements(By.tagName("tbody"));
	  System.out.println("The count for tbody is "+options.size());
	   for (WebElement option : options) {
		 
		   List<WebElement> sub_options = option.findElements(By.tagName("tr"));
		   System.out.println("The count for tr is "+sub_options.size());
		   for (WebElement sub_sub_option : sub_options) {
			   List<WebElement> sub_sub_options = sub_sub_option.findElements(By.tagName("td"));
			   System.out.println("The count for td is "+sub_sub_options.size());
			   for (WebElement sub_sub_sub_option : sub_sub_options) {
				   
				   System.out.println("i was here!");
				   System.out.println(""+sub_sub_sub_option.getText());
				   
				   if(sub_sub_sub_option.getText().contains(subject))
					   
				   {
					   EmailId=sub_sub_option.getAttribute("id");
					   flag=1;break;
					   
				   }
				   
				if(flag==1) break;
				   
				   
			   }
			   
			   if(flag==1) break; 
		   }
		   if(flag==1) break;
		   }
		   
	   
	
	return EmailId;
	
	


}// end of FindIdwithSubjectInSent




public static String GetRowIdFromCabinet(WebDriver driver,Selenium selenium,int row) throws Exception

{
	int flag=0;
	String return_rowid=null;
 int rowcnt=0;
 System.out.println("rowcnt is"+ rowcnt+ "row value is" +row);
	
	WebElement select = driver.findElement(By.xpath("//*[@id='MyFilesPlusUpload']"));
	List<WebElement> options = select.findElements(By.tagName("tbody"));
	  System.out.println("The count for tbody is "+options.size());
	   for (WebElement option : options) {
		 
		   List<WebElement> sub_options = option.findElements(By.tagName("tr"));
		   System.out.println("The count for tr is "+sub_options.size());
		   for (WebElement sub_sub_option : sub_options) {
			
					   return_rowid=sub_sub_option.getAttribute("id");
					  
					   rowcnt++;
					   if(rowcnt==row) {System.out.println("After breaking from loop rowcnt is"+rowcnt+ "row value is" +row);flag=1;break;}
				   }
				   
				if(flag==1) break;
				   
				   
			   }
			   
			 
		 
if(flag==0) assertEquals("0","1");
		   
	return return_rowid;


}


public static String GetRowIdFromCabinetWithLimits(WebDriver driver,Selenium selenium,int row,Integer limits[]) throws Exception

{
	int flag=0;
	String return_rowid=null;
 int rowcnt=limits[0];
 row=limits[1]+1; // will return the last row id
 
 
 System.out.println("rowcnt is"+ rowcnt+ "row value is" +row);
	
	WebElement select = driver.findElement(By.xpath("//*[@id='MyFilesPlusUpload']"));
	List<WebElement> options = select.findElements(By.tagName("tbody"));
	  System.out.println("The count for tbody is "+options.size());
	   for (WebElement option : options) {
		 
		   List<WebElement> sub_options = option.findElements(By.tagName("tr"));
		   System.out.println("The count for tr is "+sub_options.size());
		   for (WebElement sub_sub_option : sub_options) {
			
					   return_rowid=sub_sub_option.getAttribute("id");
					  
					   
					   if(rowcnt==row) {System.out.println("After breaking from loop rowcnt is"+rowcnt+ "row value is" +row);flag=1;break;}
					   else 
					   {
					   
					   System.out.println("Before breaking from loop rowcnt is"+rowcnt+ "row value is" +row);
					   rowcnt++;
					   }
				   }if(flag==1) break;
				   
				if(flag==1) break;
				   
				   
			   }
			   
			 
		 
if(flag==0) assertEquals("0","1");
		   
	return return_rowid;


}

public static String GetRowIdFromCabinetWithLimits_SR(WebDriver driver,Selenium selenium,int row,Integer limits[]) throws Exception

{
	int flag=0;
	String return_rowid=null;
 int rowcnt=limits[0];
 row=limits[1]+1; // will return the last row id
 
 
 System.out.println("rowcnt is"+ rowcnt+ "row value is" +row);
	
	WebElement select = driver.findElement(By.xpath("//*[@id='MyFilesPlusUploadNoDel']"));
	List<WebElement> options = select.findElements(By.tagName("tbody"));
	  System.out.println("The count for tbody is "+options.size());
	   for (WebElement option : options) {
		 
		   List<WebElement> sub_options = option.findElements(By.tagName("tr"));
		   System.out.println("The count for tr is "+sub_options.size());
		   for (WebElement sub_sub_option : sub_options) {
			
					   return_rowid=sub_sub_option.getAttribute("id");
					  
					   
					   if(rowcnt==row) {System.out.println("After breaking from loop rowcnt is"+rowcnt+ "row value is" +row);flag=1;break;}
					   else 
					   {
					   
					   System.out.println("Before breaking from loop rowcnt is"+rowcnt+ "row value is" +row);
					   rowcnt++;
					   }
				   }if(flag==1) break;
				   
				if(flag==1) break;
				   
				   
			   }
			   
			 
		 
if(flag==0) assertEquals("0","1");
		   
	return return_rowid;


}

public static String FindFileFromCabinet_SR(WebDriver driver,Selenium selenium,String fname)

{
	String name=null;int flag=0;
	
	WebElement select = driver.findElement(By.xpath("//*[@id='MyFilesPlusUploadNoDel']"));
	List<WebElement> options = select.findElements(By.tagName("tbody"));
	  System.out.println("The count for tbody is "+options.size());
	   for (WebElement option : options) {
		 
		   List<WebElement> sub_options = option.findElements(By.tagName("tr"));
		   System.out.println("The count for tr is "+sub_options.size());
		   for (WebElement sub_sub_option : sub_options) {
			
			   
			   List<WebElement> sub_sub_options = sub_sub_option.findElements(By.tagName("td"));
			   System.out.println("The count for td is "+sub_options.size());
			   for (WebElement sub_sub_sub_option : sub_sub_options) {
				   
				   name=sub_sub_sub_option.getText();
				   System.out.println("id is "+name);
				   if(name.equals(fname))
				   {flag=1;break;}
				   
			   }if(flag==1)break;
					   
				   }if(flag==1)break;
				   
	   }	
				   
				   
			   
	if(flag==0)name="null";

	return name;

}

public static String FindFileFromCabinet(WebDriver driver,Selenium selenium,String fname)

{
	String name=null;int flag=0;
	
	WebElement select = driver.findElement(By.xpath("//*[@id='MyFilesPlusUpload']"));
	List<WebElement> options = select.findElements(By.tagName("tbody"));
	  System.out.println("The count for tbody is "+options.size());
	   for (WebElement option : options) {
		 
		   List<WebElement> sub_options = option.findElements(By.tagName("tr"));
		   System.out.println("The count for tr is "+sub_options.size());
		   for (WebElement sub_sub_option : sub_options) {
			
			   
			   List<WebElement> sub_sub_options = sub_sub_option.findElements(By.tagName("td"));
			   System.out.println("The count for td is "+sub_options.size());
			   for (WebElement sub_sub_sub_option : sub_sub_options) {
				   
				   name=sub_sub_sub_option.getText();
				   System.out.println("id is "+name);
				   if(name.equals(fname))
				   {flag=1;break;}
				   
			   }if(flag==1)break;
					   
				   }if(flag==1)break;
				   
	   }	
				   
				   
			   
	if(flag==0)name="null";

	return name;

}

public static String GetRowIdFromCabinet_SR(WebDriver driver,Selenium selenium,int row) throws Exception

{
	int flag=0;
	String return_rowid=null;
 int rowcnt=0;
	
	
	WebElement select = driver.findElement(By.xpath("//*[@id='MyFilesPlusUploadNoDel']"));
	List<WebElement> options = select.findElements(By.tagName("tbody"));
	  System.out.println("The count for tbody is "+options.size());
	   for (WebElement option : options) {
		 
		   List<WebElement> sub_options = option.findElements(By.tagName("tr"));
		   System.out.println("The count for tr is "+sub_options.size());
		   for (WebElement sub_sub_option : sub_options) {
			
					   return_rowid=sub_sub_option.getAttribute("id");
					  
					   rowcnt++;
					   if(rowcnt==row) {flag=1;break;}
				   }
				   
				if(flag==1) break;
				   
				   
			   }
			   
			 
		 
	   if(flag==0) assertEquals("0","1");
		   
	return return_rowid;


}



public static Integer GetRowCntCabinet(WebDriver driver,Selenium selenium)throws Exception

{
	
	String return_cnt=driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div[2]/div[5]/div/table/tbody/tr/td[3]/div")).getText();
	System.out.println(return_cnt);
	if(return_cnt.equals("No records to view")) return 0;
	String return_cnt_arr[]=return_cnt.split(" ");
	return_cnt=return_cnt_arr[5];
	System.out.println(return_cnt);
	return Integer.valueOf(return_cnt);

}


public static Integer[] GetRowCntLimitsCabinet(WebDriver driver,Selenium selenium)throws Exception

{
	
	String return_cnt=driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div[2]/div[5]/div/table/tbody/tr/td[3]/div")).getText();
	System.out.println(return_cnt);
	String return_cnt_arr[]=return_cnt.split(" ");
	return_cnt=return_cnt_arr[5];
	System.out.println(return_cnt);
	Integer arr[]= new Integer[2];
	arr[0]=Integer.parseInt(return_cnt_arr[1]);
	arr[1]=Integer.parseInt(return_cnt_arr[3]);
	return arr;
}

public static String DraftsFindIdwithSubject(WebDriver driver,Selenium selenium,String subject) throws InterruptedException
{
	  int flag=0;
	
	String EmailId=null;
	driver.get(drafts_url);
	Thread.sleep(5000);
	WebElement select = driver.findElement(By.xpath("//*[@id='EmailDrafts']"));
	List<WebElement> options = select.findElements(By.tagName("tbody"));
	  System.out.println("The count for tbody is "+options.size());
	   for (WebElement option : options) {
		 
		   List<WebElement> sub_options = option.findElements(By.tagName("tr"));
		   System.out.println("The count for tr is "+sub_options.size());
		   for (WebElement sub_sub_option : sub_options) {
			   List<WebElement> sub_sub_options = sub_sub_option.findElements(By.tagName("td"));
			   System.out.println("The count for td is "+sub_sub_options.size());
			   for (WebElement sub_sub_sub_option : sub_sub_options) {
				   
				   System.out.println("i was here!");
				   System.out.println(""+sub_sub_sub_option.getText());
				   
				   if(sub_sub_sub_option.getText().contains(subject))
					   
				   {
					   EmailId=sub_sub_option.getAttribute("id");
					   flag=1;break;
					   
				   }
				   
				if(flag==1) break;
				   
				   
			   }
			   
			   if(flag==1) break; 
		   }
		   if(flag==1) break;
		   }
		   
	   
	
	return EmailId;
	
	


}// end of FindIdwithSubject


public static String LFTSendGreaterThan25mbReturnExpireDate(Selenium selenium,WebDriver driver,String sender,String recipient,String subject,String emailBody,String senderPwd,String baseUrl) throws Exception {
	  
	selenium.open(baseUrl);
	selenium.type("id=id_username", sender);
	selenium.type("id=id_password", senderPwd);
	selenium.click("css=input[type=\"submit\"]");
	
	selenium.waitForPageToLoad("2000");

	System.out.println("First: The page title is "+selenium.getTitle());
	// code to upload file
	
	driver.get(compose_url);
	
		selenium.waitForPageToLoad("3000");
		
		
		
		Runtime.getRuntime().exec(pathToGreaterthan25MbFilesScript);
		
		
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		WebElement ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		Thread.sleep(2000);
		ele.click();
		
		
		Thread.sleep(3000);
		driver.findElement(By.id("addrin")).sendKeys(recipient);
		
		
		driver.findElement(By.id("id_subject")).sendKeys(subject);

		
		
		
		
						
			driver.findElement(By.id("addrsubmit")).click();
		
			String tmp=driver.findElement(By.id("statuspicker")).getAttribute("value");
			
			driver.switchTo().frame("id_body_ifr");
			
			selenium.typeKeys("//body[@id='tinymce']", "Finally wohoooo!!");
			 driver.switchTo().defaultContent();
			
			driver.findElement(By.id("submitter")).click();
		
		
		
		
	// to check if mail was sent successfully
     
     String success_str_xpath="//html/body/div/div[2]/div[4]/ul/li";
     
     Functions.MyWaitfunc(driver,success_str_xpath);
     if((Functions.doesWebElementExist(driver,By.xpath(success_str_xpath))) && (driver.findElement(By.xpath(success_str_xpath)).getText().contains("Email sent to your outbox and enqueued for delivery.")))
			
		System.out.println("SUCCESS:Mail successfully sent !");
		else
			{ System.out.println("FAIL:Mail NOT SENT !"); System.out.println(driver.findElement(By.xpath(success_str_xpath)).getText());Exception e1 = new Exception("This case FAILS");
			throw e1;}
	
     driver.findElement(By.id("logout")).click();

     
     return tmp;
     
}//end of LFTSendGreaterThan25mb method

public static String LFTSendReturnExpireDate(Selenium selenium,WebDriver driver,String sender,String recipient,String subject,String emailBody,String senderPwd,String baseUrl) throws Exception {
	  
	selenium.open(baseUrl);
	selenium.type("id=id_username", sender);
	selenium.type("id=id_password", senderPwd);
	selenium.click("css=input[type=\"submit\"]");
	
	selenium.waitForPageToLoad("2000");

	System.out.println("First: The page title is "+selenium.getTitle());
	// code to upload file
	
	driver.get(compose_url);
	
		selenium.waitForPageToLoad("3000");
		
		
		
		Runtime.getRuntime().exec(pathToLessthan25MbFilesScript);
		
		
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		WebElement ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		Thread.sleep(2000);
		ele.click();
		
		
		Thread.sleep(3000);
		driver.findElement(By.id("addrin")).sendKeys(recipient);
		
		
		driver.findElement(By.id("id_subject")).sendKeys(subject);

		
		
		
		
						
			driver.findElement(By.id("addrsubmit")).click();
		
			
			
			driver.switchTo().frame("id_body_ifr");
			
			selenium.typeKeys("//body[@id='tinymce']", "Finally wohoooo!!");
			 driver.switchTo().defaultContent();
			 
			 String tmp=driver.findElement(By.id("statuspicker")).getAttribute("value");
			
			driver.findElement(By.id("submitter")).click();
		
		
		
		
	// to check if mail was sent successfully
     
     String success_str_xpath="//html/body/div/div[2]/div[4]/ul/li";
     
     Functions.MyWaitfunc(driver,success_str_xpath);
     if((Functions.doesWebElementExist(driver,By.xpath(success_str_xpath))) && (driver.findElement(By.xpath(success_str_xpath)).getText().contains("Email sent to your outbox and enqueued for delivery.")))
			
		System.out.println("SUCCESS:Mail successfully sent !");
		else
			{ System.out.println("FAIL:Mail NOT SENT !"); System.out.println(driver.findElement(By.xpath(success_str_xpath)).getText());Exception e1 = new Exception("This case FAILS");
			throw e1;}
	
     driver.findElement(By.id("logout")).click();
return tmp;
}//end of LFTsend method


public static void GenericMailSend(Selenium selenium,WebDriver driver,String sender,String recipient,String subject,String emailBody,String senderPwd,String baseUrl,int secure_flag,int expire_flag,String expire_date,int lft_flag,String lft_path,int notifications) throws Exception {
	  
	selenium.open(baseUrl);
	selenium.type("id=id_username", sender);
	selenium.type("id=id_password", senderPwd);
	selenium.click("css=input[type=\"submit\"]");
	
	selenium.waitForPageToLoad("2000");

	System.out.println("First: The page title is "+selenium.getTitle());
	// code to upload file
	
	driver.get(compose_url);
	
		selenium.waitForPageToLoad("3000");
		
		if(notifications==0) // notifications OFF
		{}
		else{
			driver.findElement(By.id("notify")).click();  // notifications ON
			
		}
		
		if(lft_flag==0) // ONLY secure mail no attachments
		{}
		else{
		// attachments are present
		
		Runtime.getRuntime().exec(lft_path);
		
		
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		WebElement ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		Thread.sleep(7000);
		ele.click();
		Thread.sleep(3000);
		
		
		}
		
		if(expire_flag==0) // default expiration date
		{}
		
		else{
			// custom expiration date
			driver.findElement(By.id("statuspicker")).sendKeys(expire_date);
		}
		
		
		if(secure_flag==0)// non-secure mail
		{}
		else
		{// secure email
			
			driver.findElement(By.id("secure")).click();
		}
		
		
		
		driver.findElement(By.id("addrin")).sendKeys(recipient);
		
		
		driver.findElement(By.id("id_subject")).sendKeys(subject);

		
		
		
		
						
			driver.findElement(By.id("addrsubmit")).click();
		
			
			
			driver.switchTo().frame("id_body_ifr");
			
			selenium.typeKeys("//body[@id='tinymce']", emailBody);
			 driver.switchTo().defaultContent();
			
			 
			 Functions.WaitForUpload(driver);
				Thread.sleep(3000);
			 
			driver.findElement(By.id("submitter")).click();
		
		
		
		
	// to check if mail was sent successfully
     
     String success_str_xpath="//html/body/div/div[2]/div[4]/ul/li";
     
     Functions.MyWaitfunc(driver,success_str_xpath);
     if((Functions.doesWebElementExist(driver,By.xpath(success_str_xpath))) && (driver.findElement(By.xpath(success_str_xpath)).getText().contains("Email sent to your outbox and enqueued for delivery.")))
			
		System.out.println("SUCCESS:Mail successfully sent !");
		else
			{ System.out.println("FAIL:Mail NOT SENT !"); System.out.println(driver.findElement(By.xpath(success_str_xpath)).getText());Exception e1 = new Exception("This case FAILS");
			throw e1;}
	
     driver.findElement(By.id("logout")).click();

}//end of LFTsend method


public static void LFTSend(Selenium selenium,WebDriver driver,String sender,String recipient,String subject,String emailBody,String senderPwd,String baseUrl) throws Exception {
  
	selenium.open(baseUrl);
	selenium.type("id=id_username", sender);
	selenium.type("id=id_password", senderPwd);
	selenium.click("css=input[type=\"submit\"]");
	
	selenium.waitForPageToLoad("2000");

	System.out.println("First: The page title is "+selenium.getTitle());
	// code to upload file
	
	driver.get(compose_url);
	
		selenium.waitForPageToLoad("3000");
		
		
		
		Runtime.getRuntime().exec(pathToLessthan25MbFilesScript);
		
		
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		WebElement ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		Thread.sleep(2000);
		ele.click();
		
		
		Thread.sleep(3000);
		driver.findElement(By.id("addrin")).sendKeys(recipient);
		
		
		driver.findElement(By.id("id_subject")).sendKeys(subject);

		
		
		
		
						
			driver.findElement(By.id("addrsubmit")).click();
		
			
			
			driver.switchTo().frame("id_body_ifr");
			
			selenium.typeKeys("//body[@id='tinymce']", "Finally wohoooo!!");
			 driver.switchTo().defaultContent();
			
			driver.findElement(By.id("submitter")).click();
		
		
		
		
	// to check if mail was sent successfully
     
     String success_str_xpath="//html/body/div/div[2]/div[4]/ul/li";
     
     Functions.MyWaitfunc(driver,success_str_xpath);
     if((Functions.doesWebElementExist(driver,By.xpath(success_str_xpath))) && (driver.findElement(By.xpath(success_str_xpath)).getText().contains("Email sent to your outbox and enqueued for delivery.")))
			
		System.out.println("SUCCESS:Mail successfully sent !");
		else
			{ System.out.println("FAIL:Mail NOT SENT !"); System.out.println(driver.findElement(By.xpath(success_str_xpath)).getText());Exception e1 = new Exception("This case FAILS");
			throw e1;}
	
     driver.findElement(By.id("logout")).click();

}//end of LFTsend method



public static void LFTSendGreaterThan25mb(Selenium selenium,WebDriver driver,String sender,String recipient,String subject,String emailBody,String senderPwd,String baseUrl) throws Exception {
	  
	selenium.open(baseUrl);
	selenium.type("id=id_username", sender);
	selenium.type("id=id_password", senderPwd);
	selenium.click("css=input[type=\"submit\"]");
	
	selenium.waitForPageToLoad("2000");

	System.out.println("First: The page title is "+selenium.getTitle());
	// code to upload file
	
	driver.get(compose_url);
	
		selenium.waitForPageToLoad("3000");
		
		
		
		Runtime.getRuntime().exec(pathToGreaterthan25MbFilesScript);
		
		
		
		Functions.MyWaitfunc(driver,"//*[@id='uploader_browse']");
		WebElement ele=driver.findElement(By.xpath("//*[@id='uploader_browse']"));
		Thread.sleep(2000);
		ele.click();
		
		
		Thread.sleep(3000);
		driver.findElement(By.id("addrin")).sendKeys(recipient);
		
		
		driver.findElement(By.id("id_subject")).sendKeys(subject);

		
		
		
		
						
			driver.findElement(By.id("addrsubmit")).click();
		
			
			
			driver.switchTo().frame("id_body_ifr");
			
			selenium.typeKeys("//body[@id='tinymce']", "Finally wohoooo!!");
			 driver.switchTo().defaultContent();
			
			driver.findElement(By.id("submitter")).click();
		
		
		
		
	// to check if mail was sent successfully
     
     String success_str_xpath="//html/body/div/div[2]/div[4]/ul/li";
     
     Functions.MyWaitfunc(driver,success_str_xpath);
     if((Functions.doesWebElementExist(driver,By.xpath(success_str_xpath))) && (driver.findElement(By.xpath(success_str_xpath)).getText().contains("Email sent to your outbox and enqueued for delivery.")))
			
		System.out.println("SUCCESS:Mail successfully sent !");
		else
			{ System.out.println("FAIL:Mail NOT SENT !"); System.out.println(driver.findElement(By.xpath(success_str_xpath)).getText());Exception e1 = new Exception("This case FAILS");
			throw e1;}
	
     driver.findElement(By.id("logout")).click();

}//end of LFTSendGreaterThan25mb method










public static void CreateLocalUser(WebDriver driver,String baseUrl,Selenium selenium,String adminPwd,String localUsername,String localUserPwd)throws Exception
{
	
	driver.get(baseUrl);
	System.out.println("Now navigating to "+baseUrl);
	//driver.switchTo().window(driver.getWindowHandle());
	System.out.println("Now logging in as admin");
	Functions.MyWaitfunc(driver, "//*[@id='id_username']");
	driver.findElement(By.id("id_username")).clear();
	driver.findElement(By.id("id_username")).sendKeys("admin");
	driver.findElement(By.id("id_password")).clear();
	driver.findElement(By.id("id_password")).sendKeys(adminPwd);
	driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	
	driver.findElement(By.id("users")).click();
	driver.findElement(By.id("local")).click();
	driver.findElement(By.id("id_new_user")).clear();
	driver.findElement(By.id("id_new_user")).sendKeys(localUsername);
	driver.findElement(By.name("submit-new")).click();
	selenium.waitForPageToLoad("3000");
	System.out.println("The page title is:"+driver.getTitle());
	assertEquals("Edit the Local User "+localUsername, driver.getTitle());
	Functions.MyWaitfunc(driver,"//*[@id='id_username']");
	
	System.out.println("username field has value: "+driver.findElement(By.id("id_username")).getAttribute("value"));
	assertEquals(localUsername,driver.findElement(By.id("id_username")).getAttribute("value"));
	
	System.out.println("Default permission for local user is: "+driver.findElement(By.linkText("domain user")).getText());
	assertEquals("domain user", driver.findElement(By.linkText("domain user")).getText());
	
	driver.findElement(By.id("id_password")).clear();
	driver.findElement(By.id("id_password")).sendKeys(localUserPwd);
	driver.findElement(By.id("id_verify_password")).clear();
	driver.findElement(By.id("id_verify_password")).sendKeys(localUserPwd);
	driver.findElement(By.id("id_full_name")).clear();
	driver.findElement(By.id("id_full_name")).sendKeys(localUsername);
	driver.findElement(By.id("id_secondary_emails")).clear();
	driver.findElement(By.id("id_secondary_emails")).sendKeys("secondary@gmail.com");
	
	Thread.sleep(2000);
	driver.findElement(By.name("submit-save")).click();
	assertEquals("Settings for the local user "+localUsername+" were saved successfully!", driver.findElement(By.cssSelector("li.success")).getText());
	System.out.println("The local user was created successfully!!");
	
	driver.findElement(By.id("logout")).click();
Thread.sleep(2000);
}// end of createLocalUser method






public static void TestLDAPauthTemplate(WebDriver driver,Selenium selenium,String baseUrl,String template)throws Exception{
	//This is a self-clean script where ldap authenticators are created , tested on and then deleted
	String auth_name="Testauthenticator";
	driver.get(baseUrl);
	/*System.out.println("Now navigating to http://192.168.1.129");
	System.out.println("Now logging in as admin");
	driver.findElement(By.id("id_username")).clear();
	driver.findElement(By.id("id_username")).sendKeys("admin");
	driver.findElement(By.id("id_password")).clear();
	driver.findElement(By.id("id_password")).sendKeys("abc123");
	driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	*/
	
	Functions.Adminlogin(driver,"admin", pwd_allusers);
	
	driver.findElement(By.id("users")).click();

	driver.findElement(By.id("ldap")).click();
	assertEquals("LDAP Authenticator List", driver.findElement(By.id("heading")).getText());
	driver.findElement(By.id("id_new_auth")).clear();
	driver.findElement(By.id("id_new_auth")).sendKeys(auth_name);
	driver.findElement(By.name("submit-new")).click();
	assertEquals(auth_name+" LDAP Authentication Wizard", driver.findElement(By.id("heading")).getText());
	assertEquals("Which LDAP server name best fits your setup?",driver.findElement(By.cssSelector("h2")).getText());
	System.out.println("Testing step 1: Select server brand");
	WebElement select = driver.findElement(By.xpath("//*[@id='id_server_brand']"));
	List<WebElement> options = select.findElements(By.tagName("option"));
	   for (WebElement option : options) {
	 		    if ("Microsoft Active Directory".equalsIgnoreCase(option.getText())){
	     option.click();
	     
	     break;
	    }
	   }
	Thread.sleep(2000);
	   driver.findElement(By.id("submit-next")).click();
	   System.out.println("Testing step 2: Set LDAP record attribs");
	   assertEquals("Choose your LDAP attribute names (records' keys).", driver.findElement(By.cssSelector("h2")).getText());
	   System.out.println("Making sure that step 2 text boxes ahve the correct default values");
	   System.out.println(" "+driver.findElement(By.id("id_full_name_attr")).getAttribute("value"));
	   assertEquals("cn", driver.findElement(By.id("id_full_name_attr")).getAttribute("value"));
	   System.out.println(" "+driver.findElement(By.id("id_dist_name_attr")).getAttribute("value"));
	  assertEquals("distinguishedName", driver.findElement(By.id("id_dist_name_attr")).getAttribute("value"));
	   
		assertEquals("sAMAccountName", driver.findElement(By.id("id_username_attr")).getAttribute("value"));
		assertEquals("mail", driver.findElement(By.id("id_email_addr_attr")).getAttribute("value"));
		assertEquals("proxyAddresses", driver.findElement(By.id("id_alt_email_attr")).getAttribute("value"));
		driver.findElement(By.id("submit-next")).click();
		System.out.println("Testing step 3: Set Primary bind data");
		assertEquals("", driver.findElement(By.id("id_server")).getAttribute("value"));
		assertEquals("389", driver.findElement(By.id("id_port")).getAttribute("value"));
		assertEquals("on", driver.findElement(By.id("id_ssl_used")).getAttribute("value"));
		assertEquals("", driver.findElement(By.id("id_bind_pwd")).getAttribute("value"));
		assertEquals("30", driver.findElement(By.id("id_timeout")).getAttribute("value"));
		assertEquals("on", driver.findElement(By.id("id_active")).getAttribute("value"));
		
		driver.findElement(By.id("id_server")).clear();
		driver.findElement(By.id("id_server")).sendKeys("qa-dcc01.qa.sparkweave.com");
		driver.findElement(By.id("id_bind_name")).clear();
		driver.findElement(By.id("id_bind_name")).sendKeys("qa-sparkweave\\userno1");
		driver.findElement(By.id("id_bind_pwd")).clear();
		driver.findElement(By.id("id_bind_pwd")).sendKeys("123");
		driver.findElement(By.id("submit-next")).click();
		
		System.out.println("Testing Step 4: Compose search query");
		driver.findElement(By.id("id_base_dns")).clear();
		driver.findElement(By.id("id_base_dns")).sendKeys("ou=test-users,dc=qa,dc=sparkweave,dc=com");
		//assertEquals("(|(cn=UserNo1)(cn=UserNo2)(cn=UserNo3)(cn=UserNo4))", driver.findElement(By.id("id_search_filter")).getAttribute("value"));
		driver.findElement(By.id("id_search_filter")).clear();
		driver.findElement(By.id("id_search_filter")).sendKeys("(|(cn=UserNo10)(cn=UserNo11)(cn=UserNo12)(cn=UserNo13))");
		assertEquals("on",driver.findElement(By.xpath("//*[@id='id_addrs_required']")).getAttribute("value"));
		assertEquals("true",driver.findElement(By.id("id_addrs_required")).getAttribute("disabled"));
		driver.findElement(By.id("submit-next")).click();
		System.out.println("Testing step 5: User settings");
		assertEquals("Choose the settings that will apply to LDAP users when they are first retrieved from the LDAP server.", driver.findElement(By.cssSelector("h2")).getText());
		assertEquals("on", driver.findElement(By.id("id_delivery_notify")).getAttribute("value"));
		assertEquals("domain user", driver.findElement(By.linkText("domain user")).getText());
		
		driver.findElement(By.id("add_template")).click();
		driver.findElement(By.xpath("//*[@id='addable_"+template+"']")).click();
		
		
		
		driver.findElement(By.id("submit-finish")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("The wizard was successfully completed and the \""+auth_name+"\" object was created.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("LDAP authenticator created successfully");
		System.out.println("Creating a clone");
		
		driver.findElement(By.cssSelector("img[alt=\"Copy\"]")).click();
		driver.findElement(By.id("id_new_clone")).clear();
		driver.findElement(By.id("id_new_clone")).sendKeys(auth_name+"_clone");
		driver.findElement(By.xpath("//button[@type='button']")).click();
		driver.findElement(By.cssSelector("#"+auth_name+"_clone > td")).click();
		// ERROR: Caught exception [ERROR: Unsupported command [isTextPresent]]
		assertEquals(auth_name+"_clone", driver.findElement(By.cssSelector("#"+auth_name+"_clone > td")).getText());
		System.out.println("Clone successfully created!");
		
		
		
		System.out.println("Testing health test for authenticator created");
		
		
		driver.findElement(By.linkText("Home")).click();
		driver.findElement(By.id("reports")).click();
		driver.findElement(By.id("health")).click();
		assertEquals(auth_name, driver.findElement(By.linkText(auth_name)).getText());
		driver.findElement(By.linkText(auth_name)).click();
		driver.findElement(By.id("showDetailsLink")).click();
		driver.findElement(By.id("startTest")).click();
		Thread.sleep(2000);
		assertEquals("The host qa-dcc01.qa.sparkweave.com and port 389 were reached successfully.", driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div/ul/li/div[2]")).getText());
		assertEquals("Successfully logged in (binded) to the LDAP server with username \"qa-sparkweave\\userno1\" and the password provided.", driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div/ul/li[2]/div[2]")).getText());
		System.out.println(" "+driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div/ul/li[3]/div[2]")).getText());
		if(driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div/ul/li[3]/div[2]")).getText().contains("The LDAP search found"))
			
			assertEquals("true","true");
		else
			assertEquals("false","true");
		
	
		assertEquals("No users were missing usernames in the LDAP search results.", driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div/ul/li[4]/div[2]")).getText());
		System.out.println("Health test successfull!");
		
		Functions.Logout(driver);
		
}



public static void TestLDAPauth(WebDriver driver,Selenium selenium,String baseUrl)throws Exception{
	//This is a self-clean script where ldap authenticators are created , tested on and then deleted
	String auth_name="Testauthenticator";
	driver.get(baseUrl);
	/*System.out.println("Now navigating to http://192.168.1.129");
	System.out.println("Now logging in as admin");
	driver.findElement(By.id("id_username")).clear();
	driver.findElement(By.id("id_username")).sendKeys("admin");
	driver.findElement(By.id("id_password")).clear();
	driver.findElement(By.id("id_password")).sendKeys("abc123");
	driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	*/
	
	Functions.Adminlogin(driver,"admin", pwd_allusers);
	
	driver.findElement(By.id("users")).click();

	driver.findElement(By.id("ldap")).click();
	assertEquals("LDAP Authenticator List", driver.findElement(By.id("heading")).getText());
	driver.findElement(By.id("id_new_auth")).clear();
	driver.findElement(By.id("id_new_auth")).sendKeys(auth_name);
	driver.findElement(By.name("submit-new")).click();
	assertEquals(auth_name+" LDAP Authentication Wizard", driver.findElement(By.id("heading")).getText());
	assertEquals("Which LDAP server name best fits your setup?",driver.findElement(By.cssSelector("h2")).getText());
	System.out.println("Testing step 1: Select server brand");
	WebElement select = driver.findElement(By.xpath("//*[@id='id_server_brand']"));
	List<WebElement> options = select.findElements(By.tagName("option"));
	   for (WebElement option : options) {
	 		    if ("Microsoft Active Directory".equalsIgnoreCase(option.getText())){
	     option.click();
	     
	     break;
	    }
	   }
	Thread.sleep(2000);
	   driver.findElement(By.id("submit-next")).click();
	   System.out.println("Testing step 2: Set LDAP record attribs");
	   assertEquals("Choose your LDAP attribute names (records' keys).", driver.findElement(By.cssSelector("h2")).getText());
	   System.out.println("Making sure that step 2 text boxes ahve the correct default values");
	   System.out.println(" "+driver.findElement(By.id("id_full_name_attr")).getAttribute("value"));
	   assertEquals("cn", driver.findElement(By.id("id_full_name_attr")).getAttribute("value"));
	   System.out.println(" "+driver.findElement(By.id("id_dist_name_attr")).getAttribute("value"));
	  assertEquals("distinguishedName", driver.findElement(By.id("id_dist_name_attr")).getAttribute("value"));
	   
		assertEquals("sAMAccountName", driver.findElement(By.id("id_username_attr")).getAttribute("value"));
		assertEquals("mail", driver.findElement(By.id("id_email_addr_attr")).getAttribute("value"));
		assertEquals("proxyAddresses", driver.findElement(By.id("id_alt_email_attr")).getAttribute("value"));
		driver.findElement(By.id("submit-next")).click();
		System.out.println("Testing step 3: Set Primary bind data");
		assertEquals("", driver.findElement(By.id("id_server")).getAttribute("value"));
		assertEquals("389", driver.findElement(By.id("id_port")).getAttribute("value"));
		assertEquals("on", driver.findElement(By.id("id_ssl_used")).getAttribute("value"));
		assertEquals("", driver.findElement(By.id("id_bind_pwd")).getAttribute("value"));
		assertEquals("30", driver.findElement(By.id("id_timeout")).getAttribute("value"));
		assertEquals("on", driver.findElement(By.id("id_active")).getAttribute("value"));
		
		driver.findElement(By.id("id_server")).clear();
		driver.findElement(By.id("id_server")).sendKeys("qa-dcc01.qa.sparkweave.com");
		driver.findElement(By.id("id_bind_name")).clear();
		driver.findElement(By.id("id_bind_name")).sendKeys("qa-sparkweave\\userno1");
		driver.findElement(By.id("id_bind_pwd")).clear();
		driver.findElement(By.id("id_bind_pwd")).sendKeys("123");
		driver.findElement(By.id("submit-next")).click();
		
		System.out.println("Testing Step 4: Compose search query");
		driver.findElement(By.id("id_base_dns")).clear();
		driver.findElement(By.id("id_base_dns")).sendKeys("ou=test-users,dc=qa,dc=sparkweave,dc=com");
		//assertEquals("(|(cn=UserNo1)(cn=UserNo2)(cn=UserNo3)(cn=UserNo4))", driver.findElement(By.id("id_search_filter")).getAttribute("value"));
		driver.findElement(By.id("id_search_filter")).clear();
		driver.findElement(By.id("id_search_filter")).sendKeys("(|(cn=UserNo10)(cn=UserNo11)(cn=UserNo12)(cn=UserNo13))");
		assertEquals("on",driver.findElement(By.xpath("//*[@id='id_addrs_required']")).getAttribute("value"));
		assertEquals("true",driver.findElement(By.id("id_addrs_required")).getAttribute("disabled"));
		driver.findElement(By.id("submit-next")).click();
		System.out.println("Testing step 5: User settings");
		assertEquals("Choose the settings that will apply to LDAP users when they are first retrieved from the LDAP server.", driver.findElement(By.cssSelector("h2")).getText());
		assertEquals("on", driver.findElement(By.id("id_delivery_notify")).getAttribute("value"));
		assertEquals("domain user", driver.findElement(By.linkText("domain user")).getText());
		driver.findElement(By.id("submit-finish")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("The wizard was successfully completed and the \""+auth_name+"\" object was created.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("LDAP authenticator created successfully");
		System.out.println("Creating a clone");
		
		driver.findElement(By.cssSelector("img[alt=\"Copy\"]")).click();
		driver.findElement(By.id("id_new_clone")).clear();
		driver.findElement(By.id("id_new_clone")).sendKeys(auth_name+"_clone");
		driver.findElement(By.xpath("//button[@type='button']")).click();
		driver.findElement(By.cssSelector("#"+auth_name+"_clone > td")).click();
		// ERROR: Caught exception [ERROR: Unsupported command [isTextPresent]]
		assertEquals(auth_name+"_clone", driver.findElement(By.cssSelector("#"+auth_name+"_clone > td")).getText());
		System.out.println("Clone successfully created!");
		
		
		
		System.out.println("Testing health test for authenticator created");
		
		
		driver.findElement(By.linkText("Home")).click();
		driver.findElement(By.id("reports")).click();
		driver.findElement(By.id("health")).click();
		assertEquals(auth_name, driver.findElement(By.linkText(auth_name)).getText());
		driver.findElement(By.linkText(auth_name)).click();
		driver.findElement(By.id("showDetailsLink")).click();
		driver.findElement(By.id("startTest")).click();
		Thread.sleep(2000);
		assertEquals("The host qa-dcc01.qa.sparkweave.com and port 389 were reached successfully.", driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div/ul/li/div[2]")).getText());
		assertEquals("Successfully logged in (binded) to the LDAP server with username \"qa-sparkweave\\userno1\" and the password provided.", driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div/ul/li[2]/div[2]")).getText());
		System.out.println(" "+driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div/ul/li[3]/div[2]")).getText());
		if(driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div/ul/li[3]/div[2]")).getText().contains("The LDAP search found"))
			
			assertEquals("true","true");
		else
			assertEquals("false","true");
		
	
		assertEquals("No users were missing usernames in the LDAP search results.", driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div/ul/li[4]/div[2]")).getText());
		System.out.println("Health test successfull!");
		
		Functions.Logout(driver);
		
}








public static void TestLDAPauthName(WebDriver driver,Selenium selenium,String baseUrl,String name)throws Exception{
	//This is a self-clean script where ldap authenticators are created , tested on and then deleted
	String auth_name=name;
	driver.get(baseUrl);
	/*System.out.println("Now navigating to http://192.168.1.129");
	System.out.println("Now logging in as admin");
	driver.findElement(By.id("id_username")).clear();
	driver.findElement(By.id("id_username")).sendKeys("admin");
	driver.findElement(By.id("id_password")).clear();
	driver.findElement(By.id("id_password")).sendKeys("abc123");
	driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	*/
	
	Functions.Adminlogin(driver,"admin", pwd_allusers);
	
	driver.findElement(By.id("users")).click();

	driver.findElement(By.id("ldap")).click();
	assertEquals("LDAP Authenticator List", driver.findElement(By.id("heading")).getText());
	driver.findElement(By.id("id_new_auth")).clear();
	driver.findElement(By.id("id_new_auth")).sendKeys(auth_name);
	driver.findElement(By.name("submit-new")).click();
	assertEquals(auth_name+" LDAP Authentication Wizard", driver.findElement(By.id("heading")).getText());
	assertEquals("Which LDAP server name best fits your setup?",driver.findElement(By.cssSelector("h2")).getText());
	System.out.println("Testing step 1: Select server brand");
	WebElement select = driver.findElement(By.xpath("//*[@id='id_server_brand']"));
	List<WebElement> options = select.findElements(By.tagName("option"));
	   for (WebElement option : options) {
	 		    if ("Microsoft Active Directory".equalsIgnoreCase(option.getText())){
	     option.click();
	     
	     break;
	    }
	   }
	Thread.sleep(2000);
	   driver.findElement(By.id("submit-next")).click();
	   System.out.println("Testing step 2: Set LDAP record attribs");
	   assertEquals("Choose your LDAP attribute names (records' keys).", driver.findElement(By.cssSelector("h2")).getText());
	   System.out.println("Making sure that step 2 text boxes ahve the correct default values");
	   System.out.println(" "+driver.findElement(By.id("id_full_name_attr")).getAttribute("value"));
	   assertEquals("cn", driver.findElement(By.id("id_full_name_attr")).getAttribute("value"));
	   System.out.println(" "+driver.findElement(By.id("id_dist_name_attr")).getAttribute("value"));
	  assertEquals("distinguishedName", driver.findElement(By.id("id_dist_name_attr")).getAttribute("value"));
	   
		assertEquals("sAMAccountName", driver.findElement(By.id("id_username_attr")).getAttribute("value"));
		assertEquals("mail", driver.findElement(By.id("id_email_addr_attr")).getAttribute("value"));
		assertEquals("proxyAddresses", driver.findElement(By.id("id_alt_email_attr")).getAttribute("value"));
		driver.findElement(By.id("submit-next")).click();
		System.out.println("Testing step 3: Set Primary bind data");
		assertEquals("", driver.findElement(By.id("id_server")).getAttribute("value"));
		assertEquals("389", driver.findElement(By.id("id_port")).getAttribute("value"));
		assertEquals("on", driver.findElement(By.id("id_ssl_used")).getAttribute("value"));
		assertEquals("", driver.findElement(By.id("id_bind_pwd")).getAttribute("value"));
		assertEquals("30", driver.findElement(By.id("id_timeout")).getAttribute("value"));
		assertEquals("on", driver.findElement(By.id("id_active")).getAttribute("value"));
		
		driver.findElement(By.id("id_server")).clear();
		driver.findElement(By.id("id_server")).sendKeys("qa-dcc01.qa.sparkweave.com");
		driver.findElement(By.id("id_bind_name")).clear();
		driver.findElement(By.id("id_bind_name")).sendKeys("qa-sparkweave\\userno1");
		driver.findElement(By.id("id_bind_pwd")).clear();
		driver.findElement(By.id("id_bind_pwd")).sendKeys("123");
		driver.findElement(By.id("submit-next")).click();
		
		System.out.println("Testing Step 4: Compose search query");
		driver.findElement(By.id("id_base_dns")).clear();
		driver.findElement(By.id("id_base_dns")).sendKeys("ou=test-users,dc=qa,dc=sparkweave,dc=com");
		//assertEquals("(|(cn=UserNo1)(cn=UserNo2)(cn=UserNo3)(cn=UserNo4))", driver.findElement(By.id("id_search_filter")).getAttribute("value"));
		driver.findElement(By.id("id_search_filter")).clear();
		driver.findElement(By.id("id_search_filter")).sendKeys("(|(cn=UserNo10)(cn=UserNo11)(cn=UserNo12)(cn=UserNo13))");
		assertEquals("on",driver.findElement(By.xpath("//*[@id='id_addrs_required']")).getAttribute("value"));
		assertEquals("true",driver.findElement(By.id("id_addrs_required")).getAttribute("disabled"));
		driver.findElement(By.id("submit-next")).click();
		System.out.println("Testing step 5: User settings");
		assertEquals("Choose the settings that will apply to LDAP users when they are first retrieved from the LDAP server.", driver.findElement(By.cssSelector("h2")).getText());
		assertEquals("on", driver.findElement(By.id("id_delivery_notify")).getAttribute("value"));
		assertEquals("domain user", driver.findElement(By.linkText("domain user")).getText());
		driver.findElement(By.id("submit-finish")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("The wizard was successfully completed and the \""+auth_name+"\" object was created.", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("LDAP authenticator created successfully");
		/*System.out.println("Creating a clone");
		
		driver.findElement(By.cssSelector("img[alt=\"Copy\"]")).click();
		driver.findElement(By.id("id_new_clone")).clear();
		driver.findElement(By.id("id_new_clone")).sendKeys(auth_name+"_clone");
		driver.findElement(By.xpath("//button[@type='button']")).click();
		driver.findElement(By.cssSelector("#"+auth_name+"_clone > td")).click();
		// ERROR: Caught exception [ERROR: Unsupported command [isTextPresent]]
		assertEquals(auth_name+"_clone", driver.findElement(By.cssSelector("#"+auth_name+"_clone > td")).getText());
		System.out.println("Clone successfully created!");
		
		*/
		
		System.out.println("Testing health test for authenticator created");
		
		
		driver.findElement(By.linkText("Home")).click();
		driver.findElement(By.id("reports")).click();
		driver.findElement(By.id("health")).click();
		assertEquals(auth_name, driver.findElement(By.linkText(auth_name)).getText());
		driver.findElement(By.linkText(auth_name)).click();
		driver.findElement(By.id("showDetailsLink")).click();
		driver.findElement(By.id("startTest")).click();
		Thread.sleep(2000);
		assertEquals("The host qa-dcc01.qa.sparkweave.com and port 389 were reached successfully.", driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div/ul/li/div[2]")).getText());
		assertEquals("Successfully logged in (binded) to the LDAP server with username \"qa-sparkweave\\userno1\" and the password provided.", driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div/ul/li[2]/div[2]")).getText());
		System.out.println(" "+driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div/ul/li[3]/div[2]")).getText());
		if(driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div/ul/li[3]/div[2]")).getText().contains("The LDAP search found"))
			
			assertEquals("true","true");
		else
			assertEquals("false","true");
		
	
		assertEquals("No users were missing usernames in the LDAP search results.", driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div/ul/li[4]/div[2]")).getText());
		System.out.println("Health test successfull!");
		
		Functions.Logout(driver);
		
}






public static void DeleteAuth(WebDriver driver) throws InterruptedException{
		String auth_name="Testauthenticator";
		Functions.AdminLogin(driver);
		System.out.println("Deleting authenticator and clone");
		
		//driver.findElement(By.linkText("Home")).click();
		driver.findElement(By.id("users")).click();
		driver.findElement(By.id("ldap")).click();
		driver.findElement(By.cssSelector("input[type=\"image\"]")).click();
		driver.switchTo().alert().accept();
		Thread.sleep(2000);
		
		// ERROR: Caught exception [ERROR: Unsupported command [getConfirmation]]
		assertEquals("the LDAP authenticator "+auth_name+" was deleted", driver.findElement(By.cssSelector("li.success")).getText());
		driver.findElement(By.cssSelector("input[type=\"image\"]")).click();
		driver.switchTo().alert().accept();
		Thread.sleep(2000);
		// ERROR: Caught exception [ERROR: Unsupported command [getConfirmation]]
		assertEquals("the LDAP authenticator "+auth_name+"_clone was deleted", driver.findElement(By.cssSelector("li.success")).getText());
		
		System.out.println("Authenticator and its clone successfully deleted!");
		
		driver.findElement(By.id("logout")).click();
	
}//end of CreateLDAPUser method


public static void LftErrorMssg(WebDriver driver,String user)
{
	WebElement toDelete = null;
	WebElement select = driver.findElement(By.xpath("//*[@id='toaddrs']"));
	List<WebElement> options = select.findElements(By.tagName("li"));
	

	   for (WebElement option : options) {
		   if(option.getAttribute("addr").contains(user))
				   {
			   System.out.println(""+option.getAttribute("addr"));
			   List<WebElement> sub_options= option.findElements(By.tagName("div"));
			   for (WebElement sub_option : sub_options) 
			      {
				   
				   List<WebElement> sub_sub_options= sub_option.findElements(By.tagName("span"));
				 
				 for (WebElement sub_sub_option : sub_sub_options)   
				 {   
				  if(sub_sub_option.getAttribute("info")!=null)
				   {
					  
				   if(sub_sub_option.getAttribute("info").contains("This email is considered a file transfer.  Since you do not have a file transfer license, your recipients are required to."))
				   
				   { System.out.println(""+sub_sub_option.getAttribute("info"));
					assertEquals("true","true");System.out.println("PASS for "+user);
					}
					   
					   else
					   { System.out.println("FAIL: for "+user);assertEquals("true","false");}
				   }
				   
				   else
				   {
					   toDelete=sub_sub_option;
					   
					   
				  }  
				   }// end of innermost for
			      
			           }//end of middle for
			          
				   }// end of first if
			   
			   
				   }//end of outermost for
	 	
	   toDelete.click();	   
	   System.out.println("Removed user "+user+" from recipient list");
	   
	
	
}// end of LftErrorMssg

public static void SecureErrorMssg(WebDriver driver,String user)
{
	WebElement toDelete=null;
	WebElement select = driver.findElement(By.xpath("//*[@id='toaddrs']"));
	List<WebElement> options = select.findElements(By.tagName("li"));
	

	   for (WebElement option : options) {
		   if(option.getAttribute("addr").contains(user))
				   {
			   System.out.println(""+option.getAttribute("addr"));
			   List<WebElement> sub_options= option.findElements(By.tagName("div"));
			   for (WebElement sub_option : sub_options) 
			      {
				   
				   List<WebElement> sub_sub_options= sub_option.findElements(By.tagName("span"));
				 for (WebElement sub_sub_option : sub_sub_options)   
				 {
				   if(sub_sub_option.getAttribute("info")!=null)
				   {
				   if(sub_sub_option.getAttribute("info").contains("This email is considered a secure email.  Since you do not have a secure email license, your recipients are required to."))
				   
				   { System.out.println(""+sub_sub_option.getAttribute("info"));
					assertEquals("true","true");System.out.println("PASS for "+user);
					}
					   
					   else
					   { System.out.println("FAIL: for "+user);System.out.println(""+sub_sub_option.getAttribute("info"));assertEquals("true","false");}
				   }
				   
				   else
				   {
					   toDelete=sub_sub_option;
					   
					   
				  }  
				   
				   
				   }// end of innermost for
			      
			           }//end of middle for
			          
				   }// end of first if
			   
			   
				   }//end of outermost for	
	   
	   toDelete.click();	   
	   System.out.println("Removed user "+user+" from recipient list");
}// end of SecureErrorMssg


public static void LftAndSecureErrorMssg(WebDriver driver,String user)
{
	WebElement toDelete=null;
	WebElement select = driver.findElement(By.xpath("//*[@id='toaddrs']"));
	List<WebElement> options = select.findElements(By.tagName("li"));
	

	   for (WebElement option : options) {
		   if(option.getAttribute("addr").contains(user))
				   {
			   System.out.println(""+option.getAttribute("addr"));
			   List<WebElement> sub_options= option.findElements(By.tagName("div"));
			   for (WebElement sub_option : sub_options) 
			      {
				   
				   List<WebElement> sub_sub_options= sub_option.findElements(By.tagName("span"));
				 for (WebElement sub_sub_option : sub_sub_options)   
				 {
				   if(sub_sub_option.getAttribute("info")!=null)
				   {
					   System.out.println(""+sub_sub_option.getAttribute("info"));
				   if(sub_sub_option.getAttribute("info").contains("This email is considered a secure email.  Since you do not have a secure email license, your recipients are required to.  This email is considered a file transfer.  Since you do not have a file transfer license, your recipients are required to."))
				   
				   { System.out.println(""+sub_sub_option.getAttribute("info"));
					assertEquals("true","true");System.out.println("PASS for "+user);
					}
					   
					   else
					   { System.out.println("FAIL: for "+user);assertEquals("true","false");}
				   }
				   else
				   {
					   toDelete=sub_sub_option;
					   
					   
				  }  
				   
				   }// end of innermost for
			      
			           }//end of middle for
			          
				   }// end of first if
			   
			   
				   }//end of outermost for	
	   toDelete.click();	   
	   System.out.println("Removed user "+user+" from recipient list");
}// end of LftAndSecureerrorMssg


public static void NoErrorMssg(WebDriver driver,String user)
{

	WebElement select = driver.findElement(By.xpath("//*[@id='toaddrs']"));
	List<WebElement> options = select.findElements(By.tagName("li"));
	

	   for (WebElement option : options) {
		   if(option.getAttribute("addr").contains(user))
				   {
			   System.out.println(""+option.getAttribute("addr"));
			   List<WebElement> sub_options= option.findElements(By.tagName("div"));
			   for (WebElement sub_option : sub_options) 
			      {
				   
				   List<WebElement> sub_sub_options= sub_option.findElements(By.tagName("span"));
				 for (WebElement sub_sub_option : sub_sub_options)   
				 {
				   
				   if(sub_sub_option.getAttribute("info")==null)
				   
				   { 
					assertEquals("true","true");System.out.println("PASS for "+user);
					}
					   
					   else
					   { System.out.println("FAIL: for "+user);assertEquals("true","false");}
				   
				   }// end of innermost for
			      
			           }//end of middle for
			          
				   }// end of first if
			   
			   
				   }//end of outermost for	
}// end of NoErrorMssg


}//end of Functions class