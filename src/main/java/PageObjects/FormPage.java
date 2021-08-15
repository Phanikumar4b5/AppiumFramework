package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class FormPage {
	AndroidDriver<AndroidElement> driver;
	
	public FormPage(AndroidDriver<AndroidElement> driver) {
		this.driver=driver;
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
    
    @AndroidFindBy(id="com.androidsample.generalstore:id/nameField") public WebElement nameField;
    @AndroidFindBy(xpath="//*[@text='Female']") public WebElement femaleOption;
    @AndroidFindBy(id="android:id/text1") public WebElement countrySelection;
   
    public void hideKeyboard() {
    	driver.hideKeyboard();
    }
    
    public WebElement nameField() {
    	return nameField;
    }
    
   
	
}
