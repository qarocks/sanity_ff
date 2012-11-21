
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
import org.openqa.selenium.NoSuchElementException;
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
public class BasicFormValidations {

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
	public void BasicFormValidations() throws Exception {
		
		//this class basically does form validations
		
		//login page
		
		driver.get(baseUrl + "/");
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys("admin");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("This field is required.", driver.findElement(By.cssSelector("li")).getText());
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys("");
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys("123abc");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("This field is required.", driver.findElement(By.cssSelector("li")).getText());
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys("admin");
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys("123");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Invalid Username or Password", driver.findElement(By.cssSelector("li")).getText());
		
		
		//forgot password link
		
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("password_reminder")).click();
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("This field is required.", driver.findElement(By.cssSelector("li")).getText());
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys("scdcdcdc");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Unrecognized account email address", driver.findElement(By.cssSelector("li")).getText());
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys("userno1@qa.sparkweave.com");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Please check your email for password reset instructions.", driver.findElement(By.cssSelector("li.success")).getText());
		
		
		//create local users page
		
		
		
		Functions.Adminlogin(driver, "admin", "123abc");
		driver.get(baseUrl + "/admin/auth/local/list");
		driver.findElement(By.id("id_new_user")).clear();
		driver.findElement(By.id("id_new_user")).sendKeys("mylocaluser@dummy.com");
		driver.findElement(By.name("submit-new")).click();
		driver.findElement(By.name("submit-save")).click();
		assertEquals("Form is invalid!", driver.findElement(By.cssSelector("li.error")).getText());
		assertEquals("This field is required.", driver.findElement(By.cssSelector("ul.errorlist > li")).getText());
		assertEquals("This field is required.", driver.findElement(By.xpath("//form[@id='submit_form']/fieldset/div[5]/div/ul/li")).getText());
		assertEquals("This field is required.", driver.findElement(By.xpath("//form[@id='submit_form']/fieldset/div[7]/div/ul/li")).getText());
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys("");
		driver.findElement(By.name("submit-save")).click();
		assertEquals("This field is required.", driver.findElement(By.cssSelector("ul.errorlist > li")).getText());
		
		
		//edit local user
		driver.get(baseUrl + "/admin/auth/local/edit/mylocaluser@dummy.com");
		driver.findElement(By.id("id_username")).clear();
		driver.findElement(By.id("id_username")).sendKeys("mylocaluser@dummy.com");
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys("123abc");
		driver.findElement(By.id("id_verify_password")).clear();
		driver.findElement(By.id("id_verify_password")).sendKeys("123abc");
		driver.findElement(By.id("id_full_name")).clear();
		driver.findElement(By.id("id_full_name")).sendKeys("mylocaluser@dummy.com");
		driver.findElement(By.name("submit-save")).click();
		driver.findElement(By.cssSelector("img[alt=\"Edit\"]")).click();
		driver.findElement(By.name("submit-save")).click();
		driver.findElement(By.cssSelector("img[alt=\"Edit\"]")).click();
		driver.findElement(By.id("id_full_name")).clear();
		driver.findElement(By.id("id_full_name")).sendKeys("");
		driver.findElement(By.name("submit-save")).click();
		driver.findElement(By.id("id_full_name")).clear();
		driver.findElement(By.id("id_full_name")).sendKeys("mylocaluser@dummy.com");
		assertEquals("This field is required.", driver.findElement(By.cssSelector("ul.errorlist > li")).getText());
		driver.findElement(By.name("submit-save")).click();
		assertEquals("Settings for the local user mylocaluser@dummy.com were saved successfully!", driver.findElement(By.cssSelector("li.success")).getText());
		driver.findElement(By.cssSelector("img[alt=\"Edit\"]")).click();
		driver.findElement(By.id("id_password")).clear();
		driver.findElement(By.id("id_password")).sendKeys("1");
		driver.findElement(By.name("submit-save")).click();
		assertEquals("Password and confirmation password did not match.", driver.findElement(By.cssSelector("ul.errorlist > li")).getText());
		
		
	//domain and guest users edit settings 
		
		driver.get(baseUrl + "/admin/users");
		driver.findElement(By.id("base-temp")).click();
		driver.findElement(By.id("internal-hash")).click();
		driver.findElement(By.name("retain_fulcrum_0")).clear();
		driver.findElement(By.name("retain_fulcrum_0")).sendKeys("");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Form is invalid!", driver.findElement(By.cssSelector("li.error")).getText());
		assertEquals("This field is required.", driver.findElement(By.cssSelector("ul.errorlist > li")).getText());
		driver.findElement(By.name("retain_fulcrum_0")).clear();
		driver.findElement(By.name("retain_fulcrum_0")).sendKeys("1");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Default value for domain users were saved successfully!", driver.findElement(By.cssSelector("li.success")).getText());
		driver.findElement(By.linkText("Default User Settings")).click();
		driver.findElement(By.id("external-hash")).click();
		driver.findElement(By.name("retain_fulcrum_0")).clear();
		driver.findElement(By.name("retain_fulcrum_0")).sendKeys("");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Form is invalid!", driver.findElement(By.cssSelector("li.error")).getText());
		assertEquals("This field is required.", driver.findElement(By.cssSelector("ul.errorlist > li")).getText());
		driver.findElement(By.name("retain_fulcrum_0")).clear();
		driver.findElement(By.name("retain_fulcrum_0")).sendKeys("1");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("Default value for guest users (guests) were saved successfully!", driver.findElement(By.cssSelector("li.success")).getText());
		
		//account settings
		
		driver.get(baseUrl + "/admin/auth/local/list");
		driver.findElement(By.id("account-settings")).click();
		driver.findElement(By.name("submit-password")).click();
		assertEquals("This field is required.", driver.findElement(By.cssSelector("li")).getText());
		assertEquals("This field is required.", driver.findElement(By.xpath("//div[@id='content-area']/div[2]/form/fieldset/div[5]/div/ul/li")).getText());
		assertEquals("This field is required.", driver.findElement(By.xpath("//div[@id='content-area']/div[2]/form/fieldset/div[7]/div/ul/li")).getText());
		driver.findElement(By.id("id_old_pw")).clear();
		driver.findElement(By.id("id_old_pw")).sendKeys("1");
		driver.findElement(By.name("submit-password")).click();
		assertEquals("This field is required.", driver.findElement(By.cssSelector("li")).getText());
		assertEquals("This field is required.", driver.findElement(By.xpath("//div[@id='content-area']/div[2]/form/fieldset/div[7]/div/ul/li")).getText());
		driver.findElement(By.id("id_old_pw")).clear();
		driver.findElement(By.id("id_old_pw")).sendKeys("werf");
		driver.findElement(By.id("id_new_pw")).clear();
		driver.findElement(By.id("id_new_pw")).sendKeys("1");
		driver.findElement(By.id("id_verify_pw")).clear();
		driver.findElement(By.id("id_verify_pw")).sendKeys("1");
		driver.findElement(By.name("submit-password")).click();
		assertEquals("Old password did not match the password for this account.", driver.findElement(By.cssSelector("li")).getText());
		driver.findElement(By.id("id_old_pw")).clear();
		driver.findElement(By.id("id_old_pw")).sendKeys("123abc");
		driver.findElement(By.id("id_new_pw")).clear();
		driver.findElement(By.id("id_new_pw")).sendKeys("1");
		driver.findElement(By.id("id_verify_pw")).clear();
		driver.findElement(By.id("id_verify_pw")).sendKeys("123");
		driver.findElement(By.name("submit-password")).click();
		assertEquals("Password and confirmation password did not match.", driver.findElement(By.cssSelector("li")).getText());
		driver.findElement(By.xpath("(//input[@name='submit-cancel'])[2]")).click();
		assertEquals("No account information was changed.", driver.findElement(By.cssSelector("li.warning")).getText());
		driver.findElement(By.id("account-settings")).click();
		driver.findElement(By.name("submit-cancel")).click();
		assertEquals("No account information was changed.", driver.findElement(By.cssSelector("li.warning")).getText());
		
		//network settings
		//email relay setings
		
		driver.get(baseUrl + "/admin");
		driver.findElement(By.id("network")).click();
		driver.findElement(By.id("relay")).click();
		driver.findElement(By.id("id_host")).clear();
		driver.findElement(By.id("id_host")).sendKeys("");
		driver.findElement(By.id("id_port")).clear();
		driver.findElement(By.id("id_port")).sendKeys("");
		driver.findElement(By.id("id_default_address")).clear();
		driver.findElement(By.id("id_default_address")).sendKeys("");
		driver.findElement(By.name("submit-primary")).click();
		assertEquals("This field is required.", driver.findElement(By.cssSelector("ul.errorlist > li")).getText());
		assertEquals("This field is required.", driver.findElement(By.xpath("//div[@id='content-area']/div/form/fieldset/div[3]/div/ul/li")).getText());
		assertEquals("This field is required.", driver.findElement(By.xpath("//div[@id='content-area']/div/form/fieldset/div[13]/div/ul/li")).getText());
		driver.findElement(By.id("id_host")).clear();
		driver.findElement(By.id("id_host")).sendKeys("mail-relay.hq.sparkweave.com");
		driver.findElement(By.name("submit-primary")).click();
		assertEquals("This field is required.", driver.findElement(By.cssSelector("ul.errorlist > li")).getText());
		assertEquals("This field is required.", driver.findElement(By.xpath("//div[@id='content-area']/div/form/fieldset/div[13]/div/ul/li")).getText());
		driver.findElement(By.id("id_port")).clear();
		driver.findElement(By.id("id_port")).sendKeys("25");
		driver.findElement(By.name("submit-primary")).click();
		assertEquals("This field is required.", driver.findElement(By.cssSelector("ul.errorlist > li")).getText());
		driver.findElement(By.id("id_default_address")).clear();
		driver.findElement(By.id("id_default_address")).sendKeys("smotadoo@sparkweave.com");
		driver.findElement(By.name("submit-primary")).click();
		assertEquals("New email relay settings were saved successfully.", driver.findElement(By.cssSelector("li.success")).getText());
		
		
		//external IP address
		
		driver.get(baseUrl + "/admin");
		driver.findElement(By.id("network")).click();
		driver.findElement(By.id("appear")).click();
		driver.findElement(By.id("id_host")).clear();
		driver.findElement(By.id("id_host")).sendKeys("");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("This field is required.", driver.findElement(By.cssSelector("ul.errorlist > li")).getText());
		driver.findElement(By.id("id_host")).clear();
		driver.findElement(By.id("id_host")).sendKeys("10.21.7.179");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("New data was saved successfully.", driver.findElement(By.cssSelector("li.success")).getText());
		
		// network settings -- need to manually test this
		
		
		//dates & times
		// bug here add code after bug fix - 
		
		//branding basic test cases
		
		
		driver.get(baseUrl + "/admin");
		driver.findElement(By.id("branding")).click();
		driver.findElement(By.name("submit-new")).click();
		assertEquals("This field is required.", driver.findElement(By.cssSelector("ul.errorlist > li")).getText());
		driver.findElement(By.xpath("//tr[@id='null']/td[2]")).click();
		assertEquals("SparkWeave (factory default)", driver.findElement(By.xpath("//tr[@id='null']/td[2]")).getText());
		assertEquals("", driver.findElement(By.cssSelector("img[title=\"This brand is the active brand\"]")).getText());
		driver.findElement(By.id("id_new_brand")).clear();
		driver.findElement(By.id("id_new_brand")).sendKeys("test");
		driver.findElement(By.name("submit-new")).click();
		driver.findElement(By.id("id_brand_name")).clear();
		driver.findElement(By.id("id_brand_name")).sendKeys("");
		driver.findElement(By.id("id_pcolor")).clear();
		driver.findElement(By.id("id_pcolor")).sendKeys("#");
		driver.findElement(By.id("images-fieldset")).click();
		driver.findElement(By.id("id_scolor")).clear();
		driver.findElement(By.id("id_scolor")).sendKeys("#");
		driver.findElement(By.id("images-fieldset")).click();
		driver.findElement(By.name("submit")).click();
		assertEquals("This field is required.", driver.findElement(By.cssSelector("ul.errorlist > li")).getText());
		assertEquals("This field is required.", driver.findElement(By.cssSelector("#text-fieldset > div.form-field.required > div.field-right > ul.errorlist > li")).getText());
		assertEquals("This field is required.", driver.findElement(By.xpath("//fieldset[@id='text-fieldset']/div[3]/div/ul/li")).getText());
		assertEquals("This field is required.", driver.findElement(By.xpath("//fieldset[@id='text-fieldset']/div[5]/div/ul/li")).getText());
		assertEquals("This field is required.", driver.findElement(By.cssSelector("#colors-fieldset > div.form-field.required > div.field-right > ul.errorlist > li")).getText());
		assertEquals("This field is required.", driver.findElement(By.xpath("//fieldset[@id='colors-fieldset']/div[3]/div/ul/li")).getText());
		driver.findElement(By.id("id_brand_name")).clear();
		driver.findElement(By.id("id_brand_name")).sendKeys("test");
		driver.findElement(By.id("id_product_name")).clear();
		driver.findElement(By.id("id_product_name")).sendKeys("test");
		driver.findElement(By.id("id_product_name_short")).clear();
		driver.findElement(By.id("id_product_name_short")).sendKeys("test");
		driver.findElement(By.id("id_company_name")).clear();
		driver.findElement(By.id("id_company_name")).sendKeys("test");
		driver.findElement(By.name("submit")).click();
		Thread.sleep(3000);
		assertEquals("New test brand created successfully. Brand must be activated for it to be visible.", driver.findElement(By.cssSelector("li.success")).getText());
		driver.findElement(By.id("id_new_brand")).clear();
		driver.findElement(By.id("id_new_brand")).sendKeys("test");
		driver.findElement(By.name("submit-new")).click();
		assertEquals("The value \"test\" is not allowed here. This brand name is already used.", driver.findElement(By.cssSelector("ul.errorlist > li")).getText());
		driver.findElement(By.id("id_new_brand")).clear();
		driver.findElement(By.id("id_new_brand")).sendKeys("test1");
		driver.findElement(By.name("submit-new")).click();
		driver.findElement(By.id("id_brand_name")).clear();
		driver.findElement(By.id("id_brand_name")).sendKeys("test");
		driver.findElement(By.id("id_product_name")).clear();
		driver.findElement(By.id("id_product_name")).sendKeys("test");
		driver.findElement(By.id("id_product_name_short")).clear();
		driver.findElement(By.id("id_product_name_short")).sendKeys("test");
		driver.findElement(By.id("id_company_name")).clear();
		driver.findElement(By.id("id_company_name")).sendKeys("test");
		
		Thread.sleep(2000);
		driver.findElement(By.xpath("//html/body/div/div[2]/div[4]/div/form/div[2]/input")).click();
		Thread.sleep(2000);
		assertEquals("The value \"test\" is not allowed here. That brand name is already taken by a existing brand.", driver.findElement(By.cssSelector("ul.errorlist > li")).getText());
		driver.findElement(By.name("submit-cancel")).click();
		Thread.sleep(3000);
		assertEquals("List of Brands", driver.findElement(By.id("heading")).getText());
		
		
		
		
		
		
		
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
	
	