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

public class InitialSanitySteps {
	private WebDriver driver;
	private String baseUrl=Functions.baseUrl;
	Selenium selenium;
	private StringBuffer verificationErrors = new StringBuffer();
	
	@Before
	public void setUp() throws Exception {
		
		System.setProperty("webdriver.firefox.bin","C:\\11.0\\Mozilla Firefox\\firefox.exe");
		driver = new FirefoxDriver();
		
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
	}

	
	
	
	@Test
	public void InitialSanityMain() throws Exception {
		//System.out.println("Navigating to base URL");
		//System.out.println(baseUrl);
		//System.out.println("I am here!!");
		String dummy;
		
		for(int i=465;i<501;i++)
		{
		dummy="dummy";
		Integer num=i;
		dummy=dummy+(num.toString());
		
				System.out.println("Creating Local user");
				String adminPwd="abc123";
				String localUsername=dummy+"@dummy.com";
				String localUserPwd="123abc";
				Functions.CreateLocalUser(driver, baseUrl, selenium, adminPwd, localUsername, localUserPwd);
				System.out.println("PASS: Creating Local user");
				/*System.out.println("Creating and testing LDAP authenticators");
				Functions.TestLDAPauth(driver, selenium, baseUrl);
				System.out.println("PASS:Creating and testing LDAP authenticators ");
				System.out.println("Testing LFT send");
				String sender="sneha.qa.24@gmail.com";
				String senderPwd="123abc";
				String recipient="snehamtd001@gmail.com";
				String subject="This is the subject line for the email";
				String emailBody="This is the email body and its ind of lame!";
				Functions.LFTSend(selenium, driver, sender, recipient, subject, emailBody, senderPwd, baseUrl);
				System.out.println("PASS: LFT send successfull!");
				System.out.println("Testing Secure send");
				Functions.SecureSend(selenium, driver, sender, recipient, subject, emailBody, senderPwd, baseUrl);
				System.out.println("PASS: Secure send successfull!");
				
				*/
		}		
		
		
		/*
		System.out.println("Logging in as admin");
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys("admin");
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys("123abc");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		selenium.waitForPageToLoad("3000");
		
		assertEquals("Administrative User", driver.findElement(By.id("username")).getText());
		System.out.println("PASS: Welcome text verification successfull");
		System.out.println("Testing for first step of initial setup guide: <<Admin Password change>>");			
		assertEquals("Password Change Required", driver.getTitle());
		driver.findElement(By.id("id_old_pw")).clear();
		driver.findElement(By.id("id_old_pw")).sendKeys("123abc");
		driver.findElement(By.id("id_new_pw")).clear();
		driver.findElement(By.id("id_new_pw")).sendKeys("abc123");
		driver.findElement(By.id("id_verify_pw")).clear();
		driver.findElement(By.id("id_verify_pw")).sendKeys("abc123");
		
		try {
			assertEquals("You are required to change your password before you can use the SparkWeave system.", driver.findElement(By.id("explanation")).getText());
		} catch (Error e) {
			verificationErrors.append(e.toString());
		}
		System.out.println("To test: \"admin\" is the prepopulated username field");
		assertEquals("admin", driver.findElement(By.cssSelector("div.field-input")).getText());
		System.out.println("Test PASSED: \"admin\" is the prepopulated username field");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		selenium.waitForPageToLoad("3000");
		assertEquals("Your password was successfully changed!", driver.findElement(By.cssSelector("li.success")).getText());
		System.out.println("Admin password changed successfully");
		System.out.println("Testing second step of initial setup guide: <<Clock Configuration>>");	
		assertEquals("Clock Configuration", driver.findElement(By.id("heading")).getText());
		assertEquals("on", driver.findElement(By.id("id_syncmethod_1")).getAttribute("value"));
		System.out.println("PASS: NTP sync option ON by default");
		assertEquals("off", driver.findElement(By.id("id_syncmethod_0")).getAttribute("value"));
		System.out.println("PASS: Manual clock setting option not selected by default");
		assertEquals("", driver.findElement(By.id("id_ntpservers")).getText());
		System.out.println("PASS: NTP server text area is blank by default");
		
		
		WebElement select = driver.findElement(By.xpath("//*[@id='id_timezone']"));
		List<WebElement> options = select.findElements(By.tagName("option"));
		   for (WebElement option : options) {
		    //System.out.println(option.getText());
		    if ("-0600 America/Chicago".equalsIgnoreCase(option.getText())){
		     option.click();
		     
		     break;
		    }
		   }
		   
		   System.out.println("Successfully set the timezone to -0600 America/Chicago");
		   assertEquals("Regional NTP Servers", driver.findElement(By.cssSelector("h2")).getText());
		   System.out.println("PASS: Regional NTP Servers list visible");
		   driver.findElement(By.linkText("United States")).click();
		   System.out.println("Syncing to US NTP server pool");
		   driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		   assertEquals("Timezone is now America/Chicago.", driver.findElement(By.cssSelector("li.info")).getText());
		   assertEquals("Clock settings were saved.", driver.findElement(By.cssSelector("li.success")).getText());
		   System.out.println("PASS: Clock configuration step passed");
		   driver.findElement(By.linkText("Home")).click();
		   selenium.waitForPageToLoad("3000");
		   System.out.println("Testing third step of initial setup guide: <<External IP or Host>>");
		   assertEquals("External IP or Host", driver.findElement(By.id("heading")).getText());
		   
		   assertEquals("You must configure your SparkWeave's external IP or hostname through a third-party tool (e.g. DNS server). After doing so, inputting the hostname on this page will allow the SparkWeave to email its users hyperlinks that successfully lead back to this website.", driver.findElement(By.id("explanation")).getText());
		   assertEquals("", driver.findElement(By.id("id_host")).getAttribute("value"));
		   assertEquals("", driver.findElement(By.id("id_company")).getAttribute("value"));
		   System.out.println("PASS: Initial text box values are blank as expected");
		   driver.findElement(By.linkText("exact:?")).click();
		   assertEquals("This hostname or IP address will be used to generate URLs that will be sent out in emails to users redirecting them back to this site.", driver.findElement(By.xpath("//body/div[3]/div[2]")).getText());
		   System.out.println("PASS: Help options correct!");
		   driver.findElement(By.cssSelector("span.ui-icon.ui-icon-closethick")).click();
		   driver.findElement(By.id("id_host")).clear();
			driver.findElement(By.id("id_host")).sendKeys("192.168.1.130");
			driver.findElement(By.id("id_company")).clear();
			driver.findElement(By.id("id_company")).sendKeys("LutsenData, LLC");
			driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
			assertEquals("New data was saved successfully.", driver.findElement(By.cssSelector("li.success")).getText());
			System.out.println("PASS: External IP or Host step passed");
			driver.findElement(By.linkText("Home")).click();
			selenium.waitForPageToLoad("3000");
			System.out.println("Testing fourth step of initial setup guide: <<Email Relay Settings>>");
			assertEquals("Email Relay Settings", driver.findElement(By.id("heading")).getText());
			assertEquals("This field is required.", driver.findElement(By.cssSelector("ul.errorlist > li")).getText());
			assertEquals("This field is required.", driver.findElement(By.xpath("//div[@id='content-area']/div/form/fieldset/div[13]/div/ul/li")).getText());
			driver.findElement(By.id("id_host")).clear();
			driver.findElement(By.id("id_host")).sendKeys("relay.dnsexit.com");
			driver.findElement(By.id("id_port")).clear();
			driver.findElement(By.id("id_port")).sendKeys("2525");
			driver.findElement(By.id("id_username")).clear();
			driver.findElement(By.id("id_username")).sendKeys("bdbowlby@lutsendata.com");
			driver.findElement(By.id("id_password")).clear();
			driver.findElement(By.id("id_password")).sendKeys("beagle5");
			driver.findElement(By.id("id_default_address")).clear();
			driver.findElement(By.id("id_default_address")).sendKeys("sparkweave@lutsendata.com");
			
			
			select = driver.findElement(By.xpath("//*[@id='id_auth_type']"));
		
			options = select.findElements(By.tagName("option")); 
			   for (WebElement option : options) {
			    //System.out.println(option.getText());
			    if ("Login".equalsIgnoreCase(option.getText())){
			     option.click();
			     
			     break;
			    }
			   }
			
			   driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
			   selenium.waitForPageToLoad("3000");
			   assertEquals("New email relay settings were saved successfully.", driver.findElement(By.cssSelector("li.success")).getText());
			   System.out.println("PASS: Email Relay Settings step passed successfully");
			   driver.findElement(By.linkText("Home")).click();
				selenium.waitForPageToLoad("3000");
				
				System.out.println("Testing fifth step of initial setup guide: <<Domain User Default Settings>>");
				
				assertEquals("Domain User Default Settings", driver.findElement(By.id("heading")).getText());
				assertEquals("never expire sessions",driver.findElement(By.id("id_idle_timeout_0")).getAttribute("placeholder"));
				System.out.println("PASS: Default placeholder text for Idle timeout field is: "+driver.findElement(By.id("id_idle_timeout_0")).getAttribute("placeholder"));
				System.out.println("To make sure that Domain Users have end-user access enabled by default");
				assertEquals("checked",driver.findElement(By.id("id_is_enduser")).getAttribute("checked"));
				System.out.println("PASS:Domain users have end-user default access");
				driver.findElement(By.id("id_is_admin")).click();
				driver.findElement(By.id("id_permissions_0")).click();
				driver.findElement(By.id("id_permissions_1")).click();
				driver.findElement(By.id("id_web_cluster_accept_0")).click();
				driver.findElement(By.name("retain_fulcrum_0")).clear();
				driver.findElement(By.name("retain_fulcrum_0")).sendKeys("1");
				assertEquals("off", driver.findElement(By.id("id_web_cluster_default_0")).getAttribute("value"));
				assertEquals("on", driver.findElement(By.id("id_web_cluster_default_1")).getAttribute("value"));
				
				assertEquals("never forcibly reroute attachments",driver.findElement(By.name("reroute_size_0")).getAttribute("placeholder"));
				assertEquals("no limit",driver.findElement(By.name("account_cap_0")).getAttribute("placeholder"));
				assertEquals("no limit",driver.findElement(By.name("file_cap_0")).getAttribute("placeholder"));
				assertEquals("never expire",driver.findElement(By.id("id_retain_small_0")).getAttribute("placeholder"));
				assertEquals("never expire",driver.findElement(By.id("id_retain_large_0")).getAttribute("placeholder"));
				driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
				selenium.waitForPageToLoad("3000");
				assertEquals("Default value for domain users were saved successfully!", driver.findElement(By.cssSelector("li.success")).getText());
				System.out.println("PASS: Domain user settings saved successfully");
				driver.findElement(By.linkText("Home")).click();
				selenium.waitForPageToLoad("3000");
				
				System.out.println("Testing sixth step of initial setup guide: <<Guest User Default Settings>>");
				assertEquals("Guest User Default Settings", driver.findElement(By.id("heading")).getText());
				System.out.println("To make sure that Guest Users have end-user access enabled by default");
				assertEquals("checked",driver.findElement(By.id("id_is_enduser")).getAttribute("checked"));
				System.out.println("PASS:Guest users have end-user default access");
				assertEquals("never expire sessions",driver.findElement(By.id("id_idle_timeout_0")).getAttribute("placeholder"));
				assertEquals("off", driver.findElement(By.id("id_web_cluster_default_0")).getAttribute("value"));
				assertEquals("on", driver.findElement(By.id("id_web_cluster_default_1")).getAttribute("value"));
				
				assertEquals("never forcibly reroute attachments",driver.findElement(By.name("reroute_size_0")).getAttribute("placeholder"));
				assertEquals("no limit",driver.findElement(By.name("account_cap_0")).getAttribute("placeholder"));
				assertEquals("no limit",driver.findElement(By.name("file_cap_0")).getAttribute("placeholder"));
				assertEquals("never expire",driver.findElement(By.id("id_retain_small_0")).getAttribute("placeholder"));
				assertEquals("never expire",driver.findElement(By.id("id_retain_large_0")).getAttribute("placeholder"));
				
				
				driver.findElement(By.name("retain_fulcrum_0")).clear();
				driver.findElement(By.name("retain_fulcrum_0")).sendKeys("1");
				driver.findElement(By.id("id_web_cluster_accept_0")).click();
				driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
				selenium.waitForPageToLoad("3000");
				assertEquals("Default value for guest users (guests) were saved successfully!", driver.findElement(By.cssSelector("li.success")).getText());
				System.out.println("PASS: Guest user settings saved successfully");
				driver.findElement(By.linkText("Home")).click();
				
				selenium.waitForPageToLoad("3000");
				try {
					assertEquals("Admin Portal", driver.getTitle());
				} catch (Error e) {
					verificationErrors.append(e.toString());
				}
				
				System.out.println("PASS: Initial Setup Guide passes successfully. System now ready to use!");
				
				*/
		
	}// end of Test

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}//end of tearDown

}// end of InitialSanitySteps class