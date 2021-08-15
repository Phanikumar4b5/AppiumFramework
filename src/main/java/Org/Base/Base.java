package Org.Base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class Base {
	public static AppiumDriverLocalService service;
	public static AndroidDriver driver;

	public void startServer() {
		boolean flag = checkIfServerRunning(4723);
		// if flag returns true in checkIfserverRunning method then it won't start the
		// server again
		// if false then negation(!) of false is true then it will go into if block and
		// starts server
		if (!flag) {
			service = AppiumDriverLocalService.buildDefaultService();
			service.start();
		}
	}

	public static boolean checkIfServerRunning(int port) {
		boolean isServerRUnning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.close();
		} catch (IOException e) {
			// if control comes here, then port is in use
			isServerRUnning = true;
		} finally {
			serverSocket = null;
		}
		return isServerRUnning;
	}

	public static void startEmulator() throws IOException, InterruptedException {
		String path = System.getProperty("user.dir") + "\\src\\main\\java\\Resources\\StartEmulator.bat";
		Runtime.getRuntime().exec(path);
		// Emulator may takes time to boot up
		Thread.sleep(6000);
	}

	public static AndroidDriver<AndroidElement> capabilities(String appName) throws IOException, InterruptedException {
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\global.properties");
		Properties pro = new Properties();
		pro.load(fis);
		File appDir = new File(System.getProperty("user.dir"));
		File app = new File(appDir, (String) pro.get(appName));

		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
		if(pro.getProperty("device").contains("emulator")) {
			startEmulator();
		}
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, pro.getProperty("device"));

		cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "20");
		cap.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		driver= new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), cap);
		return driver;

	}

	public void stopServer() {
		service.stop();
	}
	
	public void takeScreenshot(String methodName) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source=ts.getScreenshotAs(OutputType.FILE);
		String destinationPath = System.getProperty("user.dir") + "//reports//"+methodName+".png";
		FileUtils.copyFile(source, new File(destinationPath));
	}
}
