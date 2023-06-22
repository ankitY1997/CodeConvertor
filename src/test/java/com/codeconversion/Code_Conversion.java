package com.codeconversion;

import java.io.File;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class Code_Conversion {

	/** from language dropdown **/
	@FindBy(xpath = "//img[contains(@alt,'input')]/..//select")
	private WebElement fromLanguage;

	/** to language drop down **/
	@FindBy(xpath = "//img[contains(@alt,'output')]/..//select")
	private WebElement toLanguage;

	/** attach file **/

	@FindBy(xpath = "//input[@type='file']")
	private WebElement attach;

	/** InputPage **/
	@FindBy(xpath = "//div[@id='input-text']//div[@class='ace_layer ace_text-layer']/div")
	private List<WebElement> inputPage;

	/** convert button **/
	@FindBy(xpath = "//button[@id='convert-btn']")
	private WebElement convertButton;

	/** Output Page **/
	@FindBy(xpath = "//div[@id='output-text']//div[@class='ace_layer ace_text-layer']/div/span")
	private List<WebElement> outPageText;

	public static WebDriver driver;

	/** constructor **/

	private Code_Conversion(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(this.driver, this);
	}

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "./Driver/chromedriver.exe");
		driver = new ChromeDriver();
		Code_Conversion cc = new Code_Conversion(driver);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get("https://www.codeconvert.ai/free-converter");
		cc.setLanguage("Java", "JavaScript");

		// cc.attach.sendKeys("D:\\Project\\AutomationProject\\src\\main\\resources\\Program_1.java");

		cc.codeConvert("D:\\Project\\AutomationProject\\src\\main\\resources\\");
	}

	public void setLanguage(String fromLanguage, String toLanguage) {

		Select from_language = new Select(this.fromLanguage);

		Select to_language = new Select(this.toLanguage);

		from_language.selectByVisibleText(fromLanguage);

		to_language.selectByVisibleText(toLanguage);

	}

	public void codeConvert(String path) throws InterruptedException {
		File file = new File(path);

		File[] fileList = file.listFiles();
		LinkedList<String> allPath = new LinkedList<String>();
		for (File name : fileList) {
			allPath.add(name.getAbsolutePath().toString());
		}

		for (int i = 0; i < allPath.size(); i++) {

			// System.out.println("path =>"+allPath.get(i).toString());
			attach.sendKeys(allPath.get(i));
			Thread.sleep(5000);
			convertButton.click();
			clearInputTextPage();

		}
		driver.quit();

	}

	public void clearInputTextPage() {
		for (WebElement e : inputPage) {

			try {
				e.clear();
			} catch (Exception g) {
				// do nothing
			}

		}

	}

	/**
	 * to check outText field is Empty or Not
	 * 
	 * @return
	 */
	public boolean isOutputTextFieldIsEmpty() {
		String actualText = "";
		for (int i = 0; i < outPageText.size(); i++) {
			actualText = actualText + outPageText.get(i).getText();

		}
		return actualText.isEmpty();

	}
}
