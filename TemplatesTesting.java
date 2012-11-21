package sanity_ff;

import static org.junit.Assert.*;

import java.util.List;

import java.util.concurrent.TimeUnit;

//import org.apache.bcel.generic.Select;
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
import org.openqa.selenium.support.ui.Select;

import sanity_ff.Functions;

import com.thoughtworks.selenium.Selenium;
import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
public class TemplatesTesting {

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
	
		
		 
		Functions.Adminlogin(driver,"admin", Functions.pwd_allusers);
		Thread.sleep(5000);
	
		
		
		
		driver.get(baseUrl + "/admin");
		driver.findElement(By.id("users")).click();
		driver.findElement(By.id("override-temp")).click();
		//make sure that system has no templates configured
		//test the above
		assertEquals("No user templates are configured yet.", driver.findElement(By.cssSelector("td.no-data")).getText());
		//entering a blank name and assert error message
		driver.findElement(By.id("submit-new")).click();
		assertEquals("This field is required.", driver.findElement(By.cssSelector("ul.errorlist > li")).getText());
		assertEquals("New template name is invalid!", driver.findElement(By.cssSelector("li.error")).getText());
		driver.findElement(By.id("id_new_temp")).clear();
		driver.findElement(By.id("id_new_temp")).sendKeys("abcd");
		driver.findElement(By.id("submit-new")).click();
		//making sure that at least one filedset has to be selected.
		assertEquals("Choose at least one other group of fields for this template to define.", driver.findElement(By.id("empty-warning")).getText());
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		//making sure that compulsory fields omission throws error message
		assertEquals("Templates must define settings for at least one fieldset other than the \"required\" one.", driver.findElement(By.cssSelector("li.error")).getText());
		//Thread.sleep(5000);
		
		
		
		driver.findElement(By.xpath("//*[@id='add-fieldset']")).click();
		driver.findElement(By.xpath("//html/body/div/div[2]/div[3]/div[2]/form/div[2]/select/option[8]")).click();
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		Thread.sleep(5000);
		assertEquals("Form is invalid!", driver.findElement(By.cssSelector("li.error")).getText());
		Thread.sleep(5000);
		
		
		//test negative template name field characters
		
		driver.get(baseUrl + "/admin");
		driver.findElement(By.id("users")).click();
		driver.findElement(By.id("override-temp")).click();
		driver.findElement(By.id("id_new_temp")).clear();
		driver.findElement(By.id("id_new_temp")).sendKeys("~`!@#$%^&*()_+-={}|[]\\:\";'<>?,./123abcAbcs");
		driver.findElement(By.id("submit-new")).click();
		assertEquals("Edit the \"~`!@#$%^&*()_+-={}|[]\\:\";'<>?,./123abcAbcs\" User Template", driver.findElement(By.id("heading")).getText());
		
		
	
		
		
		
		//create a new valid template
		
		driver.get(baseUrl + "/admin/users");
		driver.findElement(By.id("override-temp")).click();
		driver.findElement(By.id("id_new_temp")).clear();
		driver.findElement(By.id("id_new_temp")).sendKeys("abc");
		driver.findElement(By.id("submit-new")).click();
		driver.findElement(By.xpath("//*[@id='add-fieldset']")).click();
		driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div[2]/form/div[2]/select/option[6]")).click();
		
		
		
		//new Select(driver.findElement(By.id("add-fieldset"))).selectByVisibleText("--choose a field--");
		//giving all permissions
		driver.findElement(By.id("id_permissions_0")).click();
		driver.findElement(By.id("id_permissions_1")).click();
		driver.findElement(By.id("id_permissions_2")).click();
		driver.findElement(By.id("id_is_admin")).click();
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		
	Functions.Logout(driver);
		
		
		
		//create some local users, apply template, check if template applied correctly
		
		Functions.CreateLocalUser(driver, baseUrl, selenium, Functions.pwd_allusers, "userno1@qa.sparkweave.com", "123");
		Functions.Adminlogin(driver,"admin", Functions.pwd_allusers);
		driver.get(baseUrl+"/admin/auth/local/edit/userno1@qa.sparkweave.com");
		driver.findElement(By.id("add_template")).click();
		driver.findElement(By.xpath("//*[@id='addable_abc']")).click();
		driver.findElement(By.name("submit-save")).click();
	
	
		Functions.Logout(driver);
		
			
		//now check if this user can send lft+secure mails
		
		//Functions.LFTGreaterThan25mbNeverExpire(selenium, driver, "userno1@qa.sparkweave.com", "userno2@qa.sparkweave.com", "test for template", "test for template body", "123", baseUrl);
		Functions.LFTSendGreaterThan25mb(selenium, driver, "userno1@qa.sparkweave.com", "userno2@qa.sparkweave.com", "test for template", "test for template body", "123", baseUrl);
		Functions.SecureSend(selenium, driver, "usersno1@qa.sparkweave.com", "userno2@qa.sparkweave.com", "test for template-secure", "test for template-secure", "123", baseUrl);
		
		
		//create ldap users, apply template, check if template applied correctly
		
		Functions.TestLDAPauthTemplate(driver, selenium, baseUrl,"abc"); //creates an LDAP auth with teplate "abc"
		
		//login as userno10/11/12/14 test send lft and secure email
		
		
		
		
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
	    Functions.Logout(driver);
	    
		
		
		Functions.LFTSendGreaterThan25mb(selenium, driver, "userno10@qa.sparkweave.com", "userno11@qa.sparkweave.com", "test for ldap user template", "test for ldap user template", "123", baseUrl);
		Functions.SecureSendGreaterthan25MB(selenium, driver, "userno10@qa.sparkweave.com", "userno11@qa.sparkweave.com", "test for ldap user template-secure", "test for ldap user template-secure", "123", baseUrl);
		Functions.DeleteAuth(driver);
		
		Functions.DeleteAuth(driver);
		
		
		
		//edit templates delete all field sets and try to save should see error message
		
		Functions.Adminlogin(driver,"admin", Functions.pwd_allusers);
		// go to templates page
		
		
		driver.get(baseUrl+"/admin/template/list");
		driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/table/tbody/tr/td[3]/a/img")).click();
		Thread.sleep(5000);
		
		//driver.get(baseUrl+"/admin/template/edit/abc#");
	    driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div[2]/form/fieldset[2]/a")).click();
	    //saving after deleting field set
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("Templates must define settings for at least one fieldset.", driver.findElement(By.cssSelector("li.error")).getText());
	    
	   
		
		//edit the template name and make sure that changes are saved correctly
		
		driver.get(baseUrl+"/admin/template/list");
		driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/table/tbody/tr/td[3]/a/img")).click();
		Thread.sleep(5000);
		
	    //change template name from abc to abcrenamed
	    
		
		
		//driver.get(baseUrl + "/admin/template/edit/11");
		driver.findElement(By.id("id_name")).clear();
		driver.findElement(By.id("id_name")).sendKeys("abcrenamed");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		Thread.sleep(5000);
		assertEquals("abcrenamed", driver.findElement(By.cssSelector("td")).getText());
	    
		
	     
	    
		//edit the template delete only some of the field sets. Visit the edit page again and check to see if changes are saved correctly.
		
		driver.get(baseUrl+"/admin/template/list");
		driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/table/tbody/tr/td[3]/a/img")).click();
	    Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@id='add-fieldset']")).click();
		driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div[2]/form/div[2]/select/option[2]")).click();
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		Thread.sleep(3000);
		
		
		
		// deleting the newly added filedset
		
		driver.get(baseUrl+"/admin/template/list");
		driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/table/tbody/tr/td[3]/a/img")).click();
	    Thread.sleep(3000);
		driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div[2]/form/fieldset[4]/a")).click();
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		Thread.sleep(3000);
		assertTrue(isElementPresent(By.cssSelector("li.success")));
		
		
		
		
		Functions.Logout(driver);
		//Override local user settings, apply template to user, delete the template and make sure that default settings are applied to user.
		
		
		
		
		
		Functions.CreateLocalUser(driver, baseUrl, selenium, "123abc", "userno1@qa.sparkweave.com", "123abc");
		
		
		//creating two templates with different permissions
		
		
		
		
		
		Functions.Adminlogin(driver, "admin", "123abc");
		
		driver.get(baseUrl + "/admin/users");
		driver.findElement(By.id("override-temp")).click();
		driver.findElement(By.id("id_new_temp")).clear();
		driver.findElement(By.id("id_new_temp")).sendKeys("no perms");
		driver.findElement(By.id("submit-new")).click();
		driver.findElement(By.xpath("//*[@id='add-fieldset']")).click();
		Thread.sleep(2000);
		//driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div[2]/form/div[2]/select/option[6]")).click();
		driver.findElement(By.cssSelector("option[value=\"roles_perms\"]")).click();
	
		
		
		
		
		//new Select(driver.findElement(By.id("add-fieldset"))).selectByVisibleText("--choose a field--");
		//giving all permissions
		
		driver.findElement(By.id("id_is_admin")).click();
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		Thread.sleep(3000);
		driver.get(baseUrl + "/admin/users");
		driver.findElement(By.id("override-temp")).click();
		driver.findElement(By.id("id_new_temp")).clear();
		driver.findElement(By.id("id_new_temp")).sendKeys("only lft");
		driver.findElement(By.id("submit-new")).click();
		driver.findElement(By.xpath("//*[@id='add-fieldset']")).click();
		Thread.sleep(2000);
		//driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div[2]/form/div[2]/select/option[6]")).click();
		driver.findElement(By.cssSelector("option[value=\"roles_perms\"]")).click();
		
		
		//new Select(driver.findElement(By.id("add-fieldset"))).selectByVisibleText("--choose a field--");
		//giving all permissions
		driver.findElement(By.id("id_permissions_0")).click();
		
		driver.findElement(By.id("id_is_admin")).click();
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		
		Thread.sleep(3000);
		
		//more than one template applied to a user- make sure that the most recent template is the one that should be reflected.
		
		driver.get(baseUrl+"/admin/auth/local/edit/userno1@qa.sparkweave.com");
		
		driver.findElement(By.xpath("//*[@id='add_template']")).click();
		Thread.sleep(2000);
		driver.findElement(By.cssSelector("option[value=\"only%20lft\"]")).click();
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		Thread.sleep(3000);
		
        driver.get(baseUrl+"/admin/auth/local/edit/userno1@qa.sparkweave.com");
		
		driver.findElement(By.xpath("//*[@id='add_template']")).click();
		Thread.sleep(2000);
		driver.findElement(By.cssSelector("option[value=\"no%20perms\"]")).click();
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		Thread.sleep(3000);
		
		Functions.Logout(driver);
		// to make sure that userno1@qa.sparkweave.com has NO PERMS
		
		// try to send secure mail he should see an error message
		
		
		Functions.login(driver, "userno1@qa.sparkweave.com", "123abc");
		driver.get(baseUrl + "/transfer/inbox");
		driver.findElement(By.cssSelector("a.icon")).click();
		driver.findElement(By.id("addrin")).clear();
		driver.findElement(By.id("addrin")).sendKeys("userno500@qa.sparkweave.com");
		driver.findElement(By.id("addrsubmit")).click();
		driver.findElement(By.id("secure")).click();
		Thread.sleep(2000);
		Functions.SecureErrorMssg(driver, "userno500@qa.sparkweave.com");
		Functions.Logout(driver);
		
		
		//adding a template to user on edit local users page. Delete it.
		
		Functions.Adminlogin(driver, "admin", "123abc");
		
		driver.get(baseUrl+"/admin/auth/local/edit/userno1@qa.sparkweave.com");
		
		//deleting the no perms template
		
		driver.findElement(By.id("delete_no%20perms")).click();
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		
		//now check id userno1@qa.sparkweave.com has lft perms
		Functions.Logout(driver);
		
		
		
		
		Functions.LFTSendGreaterThan25mb(selenium, driver, "userno1@qa.sparkweave.com", "userno500@qa.sparkweave.com", "test for template deletion", "test for template deletion", "123abc", baseUrl);
		
		
		//Unicode characters testing for template name
		
		Functions.Adminlogin(driver, "admin", "123abc");
		driver.get(baseUrl + "/admin");
		driver.findElement(By.id("users")).click();
		driver.findElement(By.id("override-temp")).click();
		driver.findElement(By.id("id_new_temp")).clear();
		driver.findElement(By.id("id_new_temp")).sendKeys("ǝǞǟǠǡǢǣǤǥǦǧǨǩǪǫǬǭǮǯǰǱǲǳǴǵǶ");
		driver.findElement(By.id("submit-new")).click();
		assertEquals("Edit the ǝǞǟǠǡǢǣǤǥǦǧǨǩǪǫǬǭǮǯǰǱǲǳǴǵǶ User Template", driver.findElement(By.id("heading")).getText());
		
		
		
		
		
		//edit ldap auth- apply template, delete template, change template
		
		driver.get(baseUrl + "/admin");
		Thread.sleep(3000);
		driver.findElement(By.id("users")).click();
		driver.findElement(By.id("ldap")).click();
		driver.findElement(By.cssSelector("img[alt=\"Edit\"]")).click();
		driver.findElement(By.linkText("User Settings")).click();
		driver.findElement(By.id("add_template")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='addable_no%20perms']")).click();
		driver.findElement(By.id("submit")).click();
		
		//sync again
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
		assertEquals("4/4 users", driver.findElement(By.cssSelector("div.progress")).getText());
		Functions.Logout(driver);
		
		
		//now test that ldap user cannot send secure /lft emails
		
		Functions.login(driver, "userno10@qa.sparkweave.com", "123");
		driver.get(baseUrl + "/transfer/inbox");
		driver.findElement(By.cssSelector("a.icon")).click();
		driver.findElement(By.id("addrin")).clear();
		driver.findElement(By.id("addrin")).sendKeys("userno500@qa.sparkweave.com");
		driver.findElement(By.id("addrsubmit")).click();
		driver.findElement(By.id("secure")).click();
		Thread.sleep(2000);
		Functions.SecureErrorMssg(driver, "userno500@qa.sparkweave.com");
		Functions.Logout(driver);
	
		
		//try creating template with names that already exist--should return error message
		
		driver.get(baseUrl + "/admin/template/list");
		driver.findElement(By.id("id_new_temp")).clear();
		driver.findElement(By.id("id_new_temp")).sendKeys("abcrenamed");
		driver.findElement(By.id("submit-new")).click();
		assertEquals("The value \"abcrenamed\" is not allowed here. This template name is already taken.", driver.findElement(By.cssSelector("ul.errorlist > li")).getText());
		
		
		
		//try renaming template to template name that alreday exists
		
		driver.get(baseUrl + "/admin/template/list");
		driver.findElement(By.xpath("(//img[@alt='Edit'])[2]")).click();
		driver.findElement(By.id("id_name")).clear();
		driver.findElement(By.id("id_name")).sendKeys("abcrenamed");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("The value \"abcrenamed\" is not allowed here. This template name is already taken.", driver.findElement(By.cssSelector("ul.errorlist > li")).getText());
		
		
		//match number of users to number that displays on template list page //
		//create a new template apply it to one user and one ldap auth & check. 
		//Now delete this template and make sure that the user has the correct base template applied and that the ldap auth users also have the correct base template applied.
		
		
		driver.get(baseUrl + "/admin/users");
		driver.findElement(By.id("override-temp")).click();
		driver.findElement(By.id("id_new_temp")).clear();
		driver.findElement(By.id("id_new_temp")).sendKeys("TestTemplateUserCount");
		driver.findElement(By.id("submit-new")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@id='add-fieldset']")).click();
		driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div[2]/form/div[2]/select/option[6]")).click();
		
		
		
		//new Select(driver.findElement(By.id("add-fieldset"))).selectByVisibleText("--choose a field--");
		//giving no permissions
		//driver.findElement(By.id("id_permissions_0")).click();
		//driver.findElement(By.id("id_permissions_1")).click();
		//driver.findElement(By.id("id_permissions_2")).click();
		driver.findElement(By.id("id_is_admin")).click();
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		
		Thread.sleep(3000);
		
		Functions.Logout(driver);
		
		//create local users
		
		Functions.CreateLocalUser(driver, baseUrl, selenium, "123abc", "userno400@qa.sparkweave.com", "123abc");
		
		//now apply template to "userno400@qa.sparkweave.com"
		
		
		Functions.Adminlogin(driver,"admin", Functions.pwd_allusers);
		driver.get(baseUrl+"/admin/auth/local/edit/userno400@qa.sparkweave.com");
		driver.findElement(By.id("add_template")).click();
		driver.findElement(By.xpath("//*[@id='addable_TestTemplateUserCount']")).click();
		driver.findElement(By.name("submit-save")).click();
	
	Thread.sleep(3000);
	
	Functions.Logout(driver);
	//now create auth & apply template to ldap auth
	
Functions.TestLDAPauthTemplate(driver, selenium, baseUrl, "TestTemplateUserCount");


	//check if the template count is correct on templates list page

Functions.Adminlogin(driver, "admin", "123abc");

driver.get(baseUrl + "/admin/template/list");
assertEquals("1", driver.findElement(By.xpath("//div[@id='content-area']/table/tbody/tr[4]/td[2]")).getText());
		
		
		
		Functions.Logout(driver);
		

		//now delete the template from local users page
		
		Functions.Adminlogin(driver, "admin", "123abc");
		Thread.sleep(3000);
	    driver.get(baseUrl+"/admin/auth/local/edit/userno400@qa.sparkweave.com");
	    Thread.sleep(3000);
	    driver.findElement(By.id("delete_TestTemplateUserCount")).click();
	    Thread.sleep(3000);
	    driver.findElement(By.name("submit-save")).click();
	    Thread.sleep(3000);
	    driver.get(baseUrl + "/admin/template/list");
	    assertEquals("0", driver.findElement(By.xpath("//div[@id='content-area']/table/tbody/tr[4]/td[2]")).getText());
	    
	    Thread.sleep(3000);
	    Functions.Logout(driver);
	    
	    Functions.DeleteAuth(driver);
	
	}
	
	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
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