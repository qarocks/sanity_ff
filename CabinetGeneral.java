package sanity_ff;

import java.util.concurrent.TimeUnit;
import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;



import com.thoughtworks.selenium.Selenium;

public class CabinetGeneral {
private WebDriver driver;
	
	private String baseUrl=Functions.baseUrl;
	private String composeUrl=Functions.compose_url;
	private String inboxUrl=Functions.inbox_url;
	private String sentUrl=Functions.sent_url;
	private String outboxUrl=Functions.outbox_url;
	private String draftsUrl=Functions.drafts_url;
	
	Selenium selenium;
	private StringBuffer verificationErrors = new StringBuffer();
	String user_allperms="userno1@qa.sparkweave.com",user_noperms="userno2@qa.sparkweave.com",user_onlysecure="userno3@qa.sparkweave.com",user_onlylft="userno4@qa.sparkweave.com";
	
	String pwd_allusers="123";
	String pathAutoItScript=sanity_ff.Functions.pathToLessthan25MbFilesScript;
	String pathAutoItScript_greaterthan25MB=sanity_ff.Functions.pathToGreaterthan25MbFilesScript;
	String pathToDownloadHandle=sanity_ff.Functions.pathToDownloadFileHandler;
	String subject,emailBody;
	WebElement ele;
	@Before
	public void setUp() throws Exception {
		
		System.setProperty("webdriver.firefox.bin","C://Firefox6.0//firefox.exe");
		driver = new FirefoxDriver();
		
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
		
		
		
		
	}
	
	
	
	@Test
	public void InitialSanityMain() throws Exception {
		System.out.println("Navigating to base URL");
		
		driver.get(baseUrl);
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys(user_allperms);
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys(pwd_allusers);
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Inbox", driver.findElement(By.id("heading")).getText());
		driver.get(baseUrl+"/filesync/browse/filesync/");
		//actions on regular files contained inside regular folders.
		//move to regular folder
		
		/*
		driver.findElement(By.id("newfolder")).click();
		driver.findElement(By.id("new_folder_name")).clear();
		driver.findElement(By.id("new_folder_name")).sendKeys("test folder 001");
		driver.findElement(By.cssSelector("#create_folder_html > div.formcontainer > form > div.submit-row > input[name=\"submit-mkdir\"]")).click();
		Thread.sleep(4000);
		driver.findElement(By.id("newfolder")).click();
		driver.findElement(By.id("new_folder_name")).clear();
		driver.findElement(By.id("new_folder_name")).sendKeys("move to folder");
		driver.findElement(By.cssSelector("#create_folder_html > div.formcontainer > form > div.submit-row > input[name=\"submit-mkdir\"]")).click();
		Thread.sleep(4000);
		driver.findElement(By.linkText("test folder 001")).click();
		Thread.sleep(4000);
        Runtime.getRuntime().exec(pathAutoItScript);
		
		Thread.sleep(3000);
		
		Functions.MyWaitfunc(driver,"//*[@id='newfile']");
		ele=driver.findElement(By.xpath("//*[@id='newfile']"));
		ele.click();
         Thread.sleep(3000);
		
		Functions.MyWaitfunc(driver,"//html/body/table/tbody/tr[2]/td[2]/div[2]/div[2]/div/div/div/div[2]/table[2]/tbody/tr/td/div/a/span[2]");
		ele=driver.findElement(By.xpath("//html/body/table/tbody/tr[2]/td[2]/div[2]/div[2]/div/div/div/div[2]/table[2]/tbody/tr/td/div/a/span[2]"));
		ele.click();
		Thread.sleep(2000);
		Thread.sleep(10000);
		driver.findElement(By.linkText("[close]")).click();
		
		Thread.sleep(5000);
		
		
		
		driver.findElement(By.id("jqg_FolderBrowse_L3Rlc3QgZm9sZGVyIDAwMS9Pb3BzLmpwZy5qcGc")).click();
		driver.findElement(By.id("move")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//html/body/div[6]/div[2]/div[2]/form/div[2]/div/ul/li/ul/li/a")).click(); // selecting "move to folder"
		driver.findElement(By.cssSelector("#move-dialog > div.formcontainer > form > div.submit-row > input[name=\"submit-move\"]")).click();
		Thread.sleep(5000);
		driver.findElement(By.linkText("Up One Level")).click();
		Thread.sleep(5000);
		driver.findElement(By.linkText("move to folder")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//tr[@id='L21vdmUgdG8gZm9sZGVyL09vcHMuanBnLmpwZw']/td[3]")).click();
		System.out.println("File moved to regular folder successfully");
		
		//move file to shared folder
		//create a shared folder first and then move file from 'move to folder' to 'shared folder'
		
		driver.get(baseUrl + "/filesync/browse/filesync//");
		driver.findElement(By.id("newfolder")).click();
		driver.findElement(By.id("new_folder_name")).clear();
		driver.findElement(By.id("new_folder_name")).sendKeys("shared folder");
		driver.findElement(By.cssSelector("#create_folder_html > div.formcontainer > form > div.submit-row > input[name=\"submit-mkdir\"]")).click();
		Thread.sleep(5000);
		
		
		
		
		
		driver.findElement(By.xpath("//*[@id='jqg_FolderBrowse_L3NoYXJlZCBmb2xkZXI']")).click();
		//driver.findElement(By.id("jqg_FolderBrowseWithDel_L3NoYXJlZCBmb2xkZXI")).click();
		driver.findElement(By.cssSelector("a.jqTransformSelectOpen")).click();
		Thread.sleep(5000);
		driver.findElement(By.linkText("Share Folder")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("(//button[@type='button'])[14]")).click();
		driver.findElement(By.linkText("move to folder")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//*[@id='jqg_FolderBrowse_L21vdmUgdG8gZm9sZGVyL09vcHMuanBnLmpwZw']")).click();
		driver.findElement(By.id("move")).click();
		driver.findElement(By.xpath("//html/body/div[6]/div[2]/div[2]/form/div[2]/div/ul/li/ul/li[2]/a")).click();
		driver.findElement(By.cssSelector("#move-dialog > div.formcontainer > form > div.submit-row > input[name=\"submit-move\"]")).click();
		Thread.sleep(5000);
		driver.findElement(By.linkText("Browse My Sync Cabinet")).click();
		driver.findElement(By.linkText("shared folder")).click();
		driver.findElement(By.xpath("//tr[@id='L3NoYXJlZCBmb2xkZXIvT29wcy5qcGcuanBn']/td[3]")).click();
		
		
	System.out.println("File moved to shared folder successfully");
	
	*/
	
	
	
		// to move file to container folder (folder that its contained in)
	
	driver.get(baseUrl+"/filesync/browse/filesync/");
	
	Thread.sleep(4000);
	driver.findElement(By.linkText("test folder 001")).click();
	Thread.sleep(4000);
    Runtime.getRuntime().exec(pathAutoItScript);
	
	Thread.sleep(3000);
	
	Functions.MyWaitfunc(driver,"//*[@id='newfile']");
	ele=driver.findElement(By.xpath("//*[@id='newfile']"));
	ele.click();
     Thread.sleep(3000);
	
	Functions.MyWaitfunc(driver,"//html/body/table/tbody/tr[2]/td[2]/div[2]/div[2]/div/div/div/div[2]/table[2]/tbody/tr/td/div/a/span[2]");
	ele=driver.findElement(By.xpath("//html/body/table/tbody/tr[2]/td[2]/div[2]/div[2]/div/div/div/div[2]/table[2]/tbody/tr/td/div/a/span[2]"));
	ele.click();
	Thread.sleep(2000);
	Thread.sleep(10000);
	driver.findElement(By.linkText("[close]")).click();
	
	Thread.sleep(5000);
	
	
	
	driver.findElement(By.id("jqg_FolderBrowse_L3Rlc3QgZm9sZGVyIDAwMS9Pb3BzLmpwZy5qcGc")).click();
	driver.findElement(By.id("move")).click();
	Thread.sleep(5000);
	
	driver.findElement(By.xpath("//html/body/div[6]/div[2]/div[2]/form/div[2]/div/ul/li/ul/li[3]/a")).click(); // selecting "move to folder"
	driver.findElement(By.cssSelector("#move-dialog > div.formcontainer > form > div.submit-row > input[name=\"submit-move\"]")).click();
	Thread.sleep(5000);
	selenium.refresh();
	assertEquals("View 1 - 2 of 2", driver.findElement(By.cssSelector("div.ui-paging-info")).getText());
	
	System.out.println(driver.findElement(By.cssSelector("div.ui-paging-info")).getText());
	
	System.out.println("File moved to its container folder; No change in the file count");
	
	// move file to one folder up and one down
	
	
	
		
		//Thread.sleep(50000);
		
		
		
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
	
	
	