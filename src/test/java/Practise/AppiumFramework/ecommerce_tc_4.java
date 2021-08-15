package Practise.AppiumFramework;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.WebElement;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static java.time.Duration.ofSeconds;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.Test;

import Org.Base.Base;
import Org.HelperFunctions.Utilities;
import PageObjects.FormPage;

public class ecommerce_tc_4 extends Base {
	@BeforeTest
	public void killAllNodes() throws IOException, InterruptedException {
		Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		Thread.sleep(3000);
	}
	
	@BeforeMethod
	public void setup() {
		startServer();
	}
	@AfterMethod
	public void tearDOwn() {
		stopServer();
	}
	@Test
	public void totalValidation() throws InterruptedException, IOException {
		
		AndroidDriver<AndroidElement> driver = capabilities("NativeApp");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		FormPage f = new FormPage(driver);
		f.nameField().sendKeys("Hello");
		f.hideKeyboard();
		f.femaleOption.click();
		f.countrySelection.click();
		Utilities helper = new Utilities(driver);
		helper.scrollIntoView("Argentina");
		// driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new
		// UiSelector().scrollable(true).instance(0)).scrollIntoView(new
		// UiSelector().textMatches(\"" + containedText + "\").instance(0))"));
		driver.findElement(By.xpath("//*[@text='Argentina']")).click();
		driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();

		driver.findElements(By.xpath("//*[@text='ADD TO CART']")).get(0).click();
		driver.findElements(By.xpath("//*[@text='ADD TO CART']")).get(0).click();
		driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
		Thread.sleep(4000);
		int count = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice")).size();
		double sum = 0;
		for (int i = 0; i < count; i++) {
			String amount1 = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice")).get(i)
					.getText();
			double amount = getAmount(amount1);
			sum = sum + amount;// 280.97+116.97
		}
		System.out.println(sum + "sum of products");

		String total = driver.findElement(By.id("com.androidsample.generalstore:id/totalAmountLbl")).getText();

		total = total.substring(1);
		double totalValue = Double.parseDouble(total);
		System.out.println(totalValue + "Total value of products");
		Assert.assertEquals(sum, totalValue);

//Mobile Gestures
		WebElement checkbox = driver.findElement(By.className("android.widget.CheckBox"));
		TouchAction t = new TouchAction(driver);
		t.tap(tapOptions().withElement(element(checkbox))).perform();

		WebElement tc = driver.findElement(By.xpath("//*[@text='Please read our terms of conditions']"));
		t.longPress(longPressOptions().withElement(element(tc)).withDuration(ofSeconds(2))).release().perform();
		driver.findElement(By.id("android:id/button1")).click();
		driver.findElement(By.id("com.androidsample.generalstore:id/btnProceed")).click();

	}

	public static double getAmount(String value) {
		value = value.substring(1);
		double amount2value = Double.parseDouble(value);
		return amount2value;
	}
	

}
