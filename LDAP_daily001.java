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

public class LDAP_daily001 {

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
		
		System.setProperty("webdriver.firefox.bin","C://Firefox6.0//firefox.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		selenium = new WebDriverBackedSelenium(driver, baseUrl);
		
	}
	
	
	
	@Test
	public void test() throws Exception {
		
		
		
		
		//create an ldap auth with users 10,11,12,13
		//make sure that user no13 cannot be synced and assert the error message on process status page
		
	
		
	Functions.TestLDAPauth(driver, selenium, baseUrl);	
	//sync authenticator to on board AD users
	Functions.AdminLogin(driver);
	driver.findElement(By.id("users")).click();
	driver.findElement(By.id("ldap")).click();
	assertEquals("LDAP Authenticator List", driver.findElement(By.id("heading")).getText());
	driver.findElement(By.partialLinkText("sync local accounts to LDAP")).click();
    Thread.sleep(2000);
    driver.findElement(By.partialLinkText("sync now")).click();
	Thread.sleep(5000);
	Functions.Logout(driver);
	Functions.AdminLogin(driver);
	Thread.sleep(10000);
	driver.get(baseUrl+"/admin/processes/jobs");
	assertEquals("4/4 users", driver.findElement(By.cssSelector("div.progress")).getText());
    
	//making sure that process status page shows error message
	
	driver.get(baseUrl+"/admin/processes/job/ldap_sync");
	Thread.sleep(3000);
	assertEquals("...This user record cannot be synced due to invalid account data: The following secondary email addresses are not valid: ['x400:c=us;a= ;p=bjenlab;s=simpson;g=homer;']", driver.findElement(By.cssSelector("div.joberror")).getText());
	
	Functions.Logout(driver);
	//log in with a user who was synced successfully and test lft ansd secure email sending.
	
	Functions.SecureSendGreaterthan25MB(selenium, driver, "userno10@qa.sparkweave.com", "userno500@qa.sparkweave.com", "ldap test with awesome results!", "ldap test with awesome results!", "123", baseUrl);
	
	
	
	//Edit the auth. Change ldap admin pwd and set it to the wrong pwd. run health test and assert failures
	
		
		
		Functions.Adminlogin(driver, "admin", "123abc");
		
		
		
		
		driver.get(baseUrl + "/admin/auth/ldap/list");
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//img[@alt='Edit'])[1]")).click();
		driver.findElement(By.linkText("Set Primary Bind Data")).click();
		driver.findElement(By.id("id_bind_pwd")).clear();
		driver.findElement(By.id("id_bind_pwd")).sendKeys("blah");
		driver.findElement(By.id("submit")).click();
		Thread.sleep(3000);
		driver.findElement(By.linkText("Home")).click();
		driver.findElement(By.id("reports")).click();
		driver.findElement(By.id("health")).click();
		Thread.sleep(3000);
		assertEquals("Testauthenticator", driver.findElement(By.linkText("Testauthenticator")).getText());
		driver.findElement(By.linkText("Testauthenticator")).click();
		driver.findElement(By.id("showDetailsLink")).click();
		driver.findElement(By.id("startTest")).click();
		Thread.sleep(2000);
		
		assertEquals("Could not log in (bind) to the LDAP server with username \"qa-sparkweave\\userno1\" and the password provided. Many LDAP server require a full binddn string or a DOMAIN\\username to be used during the primary login. If you are not already doing so, please ensure the username follows these guidelines.", driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div/ul/li[2]/div[2]")).getText());
	

		//Enter invalid data in every step (one at a time) and run health test. Assert for error messages
		
		//step 1
		
		driver.get(baseUrl + "/admin/auth/ldap/list");
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//img[@alt='Edit'])[1]")).click();
		driver.findElement(By.linkText("Set Primary Bind Data")).click();
		driver.findElement(By.id("id_bind_pwd")).clear();
		driver.findElement(By.id("id_bind_pwd")).sendKeys("123"); //set password back to original
		driver.findElement(By.id("submit")).click();
		Thread.sleep(3000);
		
		//edit step 1
		
		driver.get(baseUrl + "/admin/auth/ldap/list");
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//img[@alt='Edit'])[1]")).click();
		
		driver.findElement(By.linkText("Set LDAP Record Attributes")).click();
		driver.findElement(By.id("id_full_name_attr")).clear();
		driver.findElement(By.id("id_full_name_attr")).sendKeys("blah");
		driver.findElement(By.id("id_dist_name_attr")).clear();
		driver.findElement(By.id("id_dist_name_attr")).sendKeys("blah");
		driver.findElement(By.id("id_username_attr")).clear();
		driver.findElement(By.id("id_username_attr")).sendKeys("blah");
		driver.findElement(By.id("id_email_addr_attr")).clear();
		driver.findElement(By.id("id_email_addr_attr")).sendKeys("blah");
		driver.findElement(By.id("id_alt_email_attr")).clear();
		driver.findElement(By.id("id_alt_email_attr")).sendKeys("blah");
		driver.findElement(By.id("submit")).click();
		
		Thread.sleep(3000);
		driver.findElement(By.linkText("Home")).click();
		driver.findElement(By.id("reports")).click();
		driver.findElement(By.id("health")).click();
		Thread.sleep(3000);
		assertEquals("Testauthenticator", driver.findElement(By.linkText("Testauthenticator")).getText());
		driver.findElement(By.linkText("Testauthenticator")).click();
		driver.findElement(By.id("showDetailsLink")).click();
		driver.findElement(By.id("startTest")).click();
		Thread.sleep(2000);
		
		assertEquals("No valid authenticatable users were found in the LDAP search results.", driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div/ul/li[3]/div[2]")).getText());
		
		
		//edit step 2 
		
		//reset correct values in step1
		driver.get(baseUrl + "/admin/auth/ldap/list");
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//img[@alt='Edit'])[1]")).click();
		driver.findElement(By.linkText("Set LDAP Record Attributes")).click();
		driver.findElement(By.id("id_full_name_attr")).clear();
		driver.findElement(By.id("id_full_name_attr")).sendKeys("cn");
		driver.findElement(By.id("id_dist_name_attr")).clear();
		driver.findElement(By.id("id_dist_name_attr")).sendKeys("distinguishedName");
		driver.findElement(By.id("id_username_attr")).clear();
		driver.findElement(By.id("id_username_attr")).sendKeys("sAMAccountName");
		driver.findElement(By.id("id_email_addr_attr")).clear();
		driver.findElement(By.id("id_email_addr_attr")).sendKeys("mail");
		driver.findElement(By.id("id_alt_email_attr")).clear();
		driver.findElement(By.id("id_alt_email_attr")).sendKeys("proxyAddresses");
		driver.findElement(By.id("submit")).click();
		
		Thread.sleep(3000);
		
		//enter wrong values in step 2
		
		driver.get(baseUrl + "/admin/auth/ldap/edit/Testauthenticator/bind");
		Thread.sleep(2000);
		driver.findElement(By.linkText("Set Primary Bind Data")).click();
		driver.findElement(By.id("id_server")).clear();
		driver.findElement(By.id("id_server")).sendKeys("blah");
		driver.findElement(By.id("id_port")).clear();
		driver.findElement(By.id("id_port")).sendKeys("111");
		driver.findElement(By.id("id_bind_name")).clear();
		driver.findElement(By.id("id_bind_name")).sendKeys("blah");
		driver.findElement(By.id("submit")).click();
		
		
		Thread.sleep(3000);
		driver.findElement(By.linkText("Home")).click();
		driver.findElement(By.id("reports")).click();
		driver.findElement(By.id("health")).click();
		Thread.sleep(3000);
		assertEquals("Testauthenticator", driver.findElement(By.linkText("Testauthenticator")).getText());
		driver.findElement(By.linkText("Testauthenticator")).click();
		driver.findElement(By.id("showDetailsLink")).click();
		driver.findElement(By.id("startTest")).click();
		Thread.sleep(2000);
		
		
		assertEquals("The host blah and port 111 could not be reached", driver.findElement(By.cssSelector("div.test-details")).getText());
		
		
		// reset step 2 details
		
		driver.get(baseUrl + "/admin/auth/ldap/edit/Testauthenticator/bind");
		Thread.sleep(2000);
		driver.findElement(By.linkText("Set Primary Bind Data")).click();
		driver.findElement(By.id("id_server")).clear();
		driver.findElement(By.id("id_server")).sendKeys("qa-dcc01.qa.sparkweave.com");
		driver.findElement(By.id("id_port")).clear();
		driver.findElement(By.id("id_port")).sendKeys("389");
		driver.findElement(By.id("id_bind_name")).clear();
		driver.findElement(By.id("id_bind_name")).sendKeys("qa-sparkweave\\userno1");
		driver.findElement(By.id("id_bind_pwd")).clear();
		driver.findElement(By.id("id_bind_pwd")).sendKeys("123");
		driver.findElement(By.id("submit")).click();
		
		//modify step 3 details
		
		driver.get(baseUrl + "/admin/auth/ldap/edit/Testauthenticator/search");
		driver.findElement(By.linkText("Compose Search Query")).click();
		
		driver.findElement(By.id("id_base_dns")).clear();
		driver.findElement(By.id("id_base_dns")).sendKeys("blah");
		driver.findElement(By.id("id_search_filter")).clear();
		driver.findElement(By.id("id_search_filter")).sendKeys("blah");
		assertEquals("on",driver.findElement(By.xpath("//*[@id='id_addrs_required']")).getAttribute("value"));
		assertEquals("true",driver.findElement(By.id("id_addrs_required")).getAttribute("disabled"));
		driver.findElement(By.id("submit")).click();
		
		Thread.sleep(3000);
		driver.findElement(By.linkText("Home")).click();
		driver.findElement(By.id("reports")).click();
		driver.findElement(By.id("health")).click();
		Thread.sleep(3000);
		assertEquals("Testauthenticator", driver.findElement(By.linkText("Testauthenticator")).getText());
		driver.findElement(By.linkText("Testauthenticator")).click();
		driver.findElement(By.id("showDetailsLink")).click();
		driver.findElement(By.id("startTest")).click();
		Thread.sleep(2000);
		
		
		
		assertEquals("Invalid filter string in your LDAP search query.", driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div/ul/li[3]/div[2]")).getText());
		
		
		//setting values back to original
		
		driver.get(baseUrl + "/admin/auth/ldap/edit/Testauthenticator/search");
		driver.findElement(By.linkText("Compose Search Query")).click();
		
		driver.findElement(By.id("id_base_dns")).clear();
		driver.findElement(By.id("id_base_dns")).sendKeys("ou=test-users,dc=qa,dc=sparkweave,dc=com");
		driver.findElement(By.id("id_search_filter")).clear();
		driver.findElement(By.id("id_search_filter")).sendKeys("(|(cn=UserNo10)(cn=UserNo11)(cn=UserNo12)(cn=UserNo13))");
		assertEquals("on",driver.findElement(By.xpath("//*[@id='id_addrs_required']")).getAttribute("value"));
		assertEquals("true",driver.findElement(By.id("id_addrs_required")).getAttribute("disabled"));
		driver.findElement(By.id("submit")).click();
		Thread.sleep(3000);
		
		Functions.Logout(driver);
		Functions.DeleteAuth(driver);
		
		
		
		Functions.TestLDAPauthName(driver, selenium, baseUrl, "Testauth");
		
		
		
		//Enter invalid data in every step (one at a time) and run manual sync. Make sure there are no crashes
		Functions.Adminlogin(driver, "admin", "123abc");
		
		driver.get(baseUrl + "/admin/auth/ldap/list");
		Thread.sleep(3000);
		driver.findElement(By.xpath("(//img[@alt='Edit'])[1]")).click();
		
		driver.findElement(By.linkText("Set LDAP Record Attributes")).click();
		driver.findElement(By.id("id_full_name_attr")).clear();
		driver.findElement(By.id("id_full_name_attr")).sendKeys("blah");
		driver.findElement(By.id("id_dist_name_attr")).clear();
		driver.findElement(By.id("id_dist_name_attr")).sendKeys("blah");
		driver.findElement(By.id("id_username_attr")).clear();
		driver.findElement(By.id("id_username_attr")).sendKeys("blah");
		driver.findElement(By.id("id_email_addr_attr")).clear();
		driver.findElement(By.id("id_email_addr_attr")).sendKeys("blah");
		driver.findElement(By.id("id_alt_email_attr")).clear();
		driver.findElement(By.id("id_alt_email_attr")).sendKeys("blah");
		driver.findElement(By.id("submit")).click();
		
		Thread.sleep(3000);
		
	
		
		driver.get(baseUrl+"/admin");
		driver.findElement(By.id("users")).click();
		driver.findElement(By.id("ldap")).click();
		assertEquals("LDAP Authenticator List", driver.findElement(By.id("heading")).getText());
		driver.findElement(By.partialLinkText("sync local accounts to LDAP")).click();
	    Thread.sleep(2000);
	    driver.findElement(By.partialLinkText("sync now")).click();
		Thread.sleep(5000);
		Functions.Logout(driver);
		Functions.AdminLogin(driver);
		Thread.sleep(10000);
		driver.get(baseUrl+"/admin/processes/jobs");
		assertEquals("0/0 users", driver.findElement(By.cssSelector("div.progress")).getText());
	    
		
		
		
		//edit step 2 
		
				//reset correct values in step1
				driver.get(baseUrl + "/admin/auth/ldap/list");
				Thread.sleep(3000);
				driver.findElement(By.xpath("(//img[@alt='Edit'])[1]")).click();
				driver.findElement(By.linkText("Set LDAP Record Attributes")).click();
				driver.findElement(By.id("id_full_name_attr")).clear();
				driver.findElement(By.id("id_full_name_attr")).sendKeys("cn");
				driver.findElement(By.id("id_dist_name_attr")).clear();
				driver.findElement(By.id("id_dist_name_attr")).sendKeys("distinguishedName");
				driver.findElement(By.id("id_username_attr")).clear();
				driver.findElement(By.id("id_username_attr")).sendKeys("sAMAccountName");
				driver.findElement(By.id("id_email_addr_attr")).clear();
				driver.findElement(By.id("id_email_addr_attr")).sendKeys("mail");
				driver.findElement(By.id("id_alt_email_attr")).clear();
				driver.findElement(By.id("id_alt_email_attr")).sendKeys("proxyAddresses");
				driver.findElement(By.id("submit")).click();
				
				Thread.sleep(3000);
				
				//enter wrong values in step 2
				
				driver.get(baseUrl + "/admin/auth/ldap/edit/Testauth/bind");
				Thread.sleep(2000);
				driver.findElement(By.linkText("Set Primary Bind Data")).click();
				driver.findElement(By.id("id_server")).clear();
				driver.findElement(By.id("id_server")).sendKeys("blah");
				driver.findElement(By.id("id_port")).clear();
				driver.findElement(By.id("id_port")).sendKeys("111");
				driver.findElement(By.id("id_bind_name")).clear();
				driver.findElement(By.id("id_bind_name")).sendKeys("blah");
				driver.findElement(By.id("submit")).click();
				
				
				Thread.sleep(3000);
				driver.get(baseUrl+"/admin");
				driver.findElement(By.id("users")).click();
				driver.findElement(By.id("ldap")).click();
				assertEquals("LDAP Authenticator List", driver.findElement(By.id("heading")).getText());
				driver.findElement(By.partialLinkText("sync local accounts to LDAP")).click();
			    Thread.sleep(2000);
			    driver.findElement(By.partialLinkText("sync now")).click();
				Thread.sleep(5000);
				Functions.Logout(driver);
				Functions.AdminLogin(driver);
				Thread.sleep(10000);
				driver.get(baseUrl+"/admin/processes/jobs");
				Thread.sleep(3000);
				driver.findElement(By.partialLinkText("ldap_sync")).click();
				assertEquals("...Unable to connect to or search under the Testauth LDAP Authenticator. Try running the health test on this authenticator for complete troubleshooting.", driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div[2]/div[3]")).getText());
				driver.get(baseUrl + "/admin/processes/job/ldap_sync");
				Thread.sleep(3000);
				driver.findElement(By.linkText("Running Jobs")).click();
				driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
				Thread.sleep(3000);
				//assertEquals("0/0 users", driver.findElement(By.cssSelector("div.progress")).getText());
				
				// reset step 2 details
				
				driver.get(baseUrl + "/admin/auth/ldap/edit/Testauth/bind");
				Thread.sleep(2000);
				driver.findElement(By.linkText("Set Primary Bind Data")).click();
				driver.findElement(By.id("id_server")).clear();
				driver.findElement(By.id("id_server")).sendKeys("qa-dcc01.qa.sparkweave.com");
				driver.findElement(By.id("id_port")).clear();
				driver.findElement(By.id("id_port")).sendKeys("389");
				driver.findElement(By.id("id_bind_name")).clear();
				driver.findElement(By.id("id_bind_name")).sendKeys("qa-sparkweave\\userno1");
				driver.findElement(By.id("id_bind_pwd")).clear();
				driver.findElement(By.id("id_bind_pwd")).sendKeys("123");
				driver.findElement(By.id("submit")).click();
				
				//modify step 3 details
				
				driver.get(baseUrl + "/admin/auth/ldap/edit/Testauth/search");
				driver.findElement(By.linkText("Compose Search Query")).click();
				
				driver.findElement(By.id("id_base_dns")).clear();
				driver.findElement(By.id("id_base_dns")).sendKeys("blah");
				driver.findElement(By.id("id_search_filter")).clear();
				driver.findElement(By.id("id_search_filter")).sendKeys("blah");
				assertEquals("on",driver.findElement(By.xpath("//*[@id='id_addrs_required']")).getAttribute("value"));
				assertEquals("true",driver.findElement(By.id("id_addrs_required")).getAttribute("disabled"));
				driver.findElement(By.id("submit")).click();
				
				Thread.sleep(3000);
				driver.get(baseUrl+"/admin");
				driver.findElement(By.id("users")).click();
				driver.findElement(By.id("ldap")).click();
				assertEquals("LDAP Authenticator List", driver.findElement(By.id("heading")).getText());
				driver.findElement(By.partialLinkText("sync local accounts to LDAP")).click();
			    Thread.sleep(2000);
			    driver.findElement(By.partialLinkText("sync now")).click();
				Thread.sleep(5000);
				Functions.Logout(driver);
				Functions.AdminLogin(driver);
				Thread.sleep(10000);
				driver.get(baseUrl+"/admin/processes/jobs");
				
				Thread.sleep(3000);
				driver.findElement(By.partialLinkText("ldap_sync")).click();
				assertEquals("...Unable to connect to or search under the Testauth LDAP Authenticator. Try running the health test on this authenticator for complete troubleshooting.", driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div[2]/div[3]")).getText());
				driver.get(baseUrl + "/admin/processes/job/ldap_sync");
				Thread.sleep(3000);
				driver.findElement(By.linkText("Running Jobs")).click();
				driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
				Thread.sleep(3000);
				
				//assertEquals("0/0 users", driver.findElement(By.cssSelector("div.progress")).getText());
				
				
				//setting values back to original
				
				driver.get(baseUrl + "/admin/auth/ldap/edit/Testauth/search");
				driver.findElement(By.linkText("Compose Search Query")).click();
				
				driver.findElement(By.id("id_base_dns")).clear();
				driver.findElement(By.id("id_base_dns")).sendKeys("ou=test-users,dc=qa,dc=sparkweave,dc=com");
				driver.findElement(By.id("id_search_filter")).clear();
				driver.findElement(By.id("id_search_filter")).sendKeys("(|(cn=UserNo10)(cn=UserNo11)(cn=UserNo12)(cn=UserNo13))");
				assertEquals("on",driver.findElement(By.xpath("//*[@id='id_addrs_required']")).getAttribute("value"));
				assertEquals("true",driver.findElement(By.id("id_addrs_required")).getAttribute("disabled"));
				driver.findElement(By.id("submit")).click();
				Thread.sleep(3000);
		
		
		
		
	//ldap scheduling has to be tested manually.
	
	//test all negative scenarios-
	
	// "add authenticator" enter illegal characters and test
		
		Thread.sleep(3000);
				
				driver.get(baseUrl + "/admin/auth/ldap/list");
				Thread.sleep(3000);
				driver.findElement(By.id("id_new_auth")).clear();
				driver.findElement(By.id("id_new_auth")).sendKeys("test-123");
				driver.findElement(By.name("submit-new")).click();
				assertEquals("Only letters (a-z), numbers (0-9), and underscores ( _ ) are allowed.", driver.findElement(By.cssSelector("ul.errorlist > li")).getText());			
				driver.findElement(By.id("id_new_auth")).clear();
				driver.findElement(By.id("id_new_auth")).sendKeys("test`!@123");
				driver.findElement(By.name("submit-new")).click();
				assertEquals("Only letters (a-z), numbers (0-9), and underscores ( _ ) are allowed.", driver.findElement(By.cssSelector("ul.errorlist > li")).getText());	
				driver.findElement(By.id("id_new_auth")).clear();
				driver.findElement(By.id("id_new_auth")).sendKeys("test$%^&^123");
				driver.findElement(By.name("submit-new")).click();
				assertEquals("Only letters (a-z), numbers (0-9), and underscores ( _ ) are allowed.", driver.findElement(By.cssSelector("ul.errorlist > li")).getText());	
				driver.findElement(By.id("id_new_auth")).clear();
				driver.findElement(By.id("id_new_auth")).sendKeys("test*()_+{}[]:\";'<>?,./\\|123");
				driver.findElement(By.name("submit-new")).click();
				assertEquals("Only letters (a-z), numbers (0-9), and underscores ( _ ) are allowed.", driver.findElement(By.cssSelector("ul.errorlist > li")).getText());	
				
	
	//while creating an authenticator leave compulsory fields blank and test for error messages
	
	
				
				
				driver.get(baseUrl + "/admin/auth/ldap/edit/Testauth/attrs");
				driver.findElement(By.linkText("Set LDAP Record Attributes")).click();
				driver.findElement(By.id("id_full_name_attr")).clear();
				driver.findElement(By.id("id_full_name_attr")).sendKeys("");
				driver.findElement(By.id("submit")).click();
				assertEquals("This field is required.", driver.findElement(By.cssSelector("ul.errorlist > li")).getText());
				driver.findElement(By.id("id_full_name_attr")).clear();
				driver.findElement(By.id("id_full_name_attr")).sendKeys("cn");
				driver.findElement(By.id("submit")).click();
				assertEquals("The changes to LDAP Authenticator Testauth were saved successfully.", driver.findElement(By.cssSelector("li.success")).getText());
				driver.findElement(By.linkText("Set Primary Bind Data")).click();
				driver.findElement(By.id("id_server")).clear();
				driver.findElement(By.id("id_server")).sendKeys("");
				driver.findElement(By.id("submit")).click();
				assertEquals("This field is required.", driver.findElement(By.cssSelector("ul.errorlist > li")).getText());
				driver.findElement(By.id("id_server")).clear();
				driver.findElement(By.id("id_server")).sendKeys("qa-dcc01.qa.sparkweave.com");
				driver.findElement(By.id("submit")).click();
				assertEquals("The changes to LDAP Authenticator Testauth were saved successfully.", driver.findElement(By.cssSelector("li.success")).getText());
				driver.findElement(By.linkText("Compose Search Query")).click();
				driver.findElement(By.id("id_base_dns")).clear();
				driver.findElement(By.id("id_base_dns")).sendKeys("");
				driver.findElement(By.id("submit")).click();
				assertEquals("This field is required.", driver.findElement(By.cssSelector("ul.errorlist > li")).getText());
				driver.findElement(By.id("id_base_dns")).clear();
				driver.findElement(By.id("id_base_dns")).sendKeys("ou=test-users,dc=qa,dc=sparkweave,dc=com");
				driver.findElement(By.id("submit")).click();
				driver.findElement(By.id("id_search_filter")).clear();
				driver.findElement(By.id("id_search_filter")).sendKeys("");
				driver.findElement(By.id("submit")).click();
				assertEquals("This field is required.", driver.findElement(By.cssSelector("ul.errorlist > li")).getText());
				driver.findElement(By.id("id_search_filter")).clear();
				driver.findElement(By.id("id_search_filter")).sendKeys("(|(cn=UserNo10)(cn=UserNo11)(cn=UserNo12)(cn=UserNo13))");
				driver.findElement(By.id("submit")).click();
				assertEquals("The changes to LDAP Authenticator Testauth were saved successfully.", driver.findElement(By.cssSelector("li.success")).getText());
				driver.findElement(By.linkText("User Settings")).click();
				
				
				
	//test that "cancel" does not create the ldap auth
				
				driver.get(baseUrl + "/admin/auth/ldap/list");
				driver.findElement(By.id("id_new_auth")).clear();
				driver.findElement(By.id("id_new_auth")).sendKeys("test");
				driver.findElement(By.name("submit-new")).click();
				driver.findElement(By.id("submit-cancel")).click();
				assertEquals("The wizard was cancelled. The \"test\" object was not created.", driver.findElement(By.cssSelector("li.error")).getText());
				
	// delete the auth and test to see that users that were synced earlier are no longer allowed to login
				
				driver.get(baseUrl + "/admin/auth/ldap/list");
				driver.findElement(By.cssSelector("input[type=\"image\"]")).click();
				driver.switchTo().alert().accept();
				Thread.sleep(2000);
				
				//check that userno10 is unable to log in.
				
				Functions.Logout(driver);
				
				
				driver.get(baseUrl);
				driver.findElement(By.id("id_username")).clear();
				driver.findElement(By.id("id_username")).sendKeys("userno10@qa.sparkweave.com");
				driver.findElement(By.id("id_password")).clear();
				driver.findElement(By.id("id_password")).sendKeys("123");
				driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
				assertEquals("Invalid Username or Password", driver.findElement(By.cssSelector("li")).getText());
				
	
	//
	
	
	
    
    
    
    
    
	
	
		
	}

	@After
	public void tearDown() throws Exception {
	
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
	
	}

	

}

}
