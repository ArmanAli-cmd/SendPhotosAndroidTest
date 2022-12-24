import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.MalformedParameterizedTypeException;

public class SendingPhotosAndroidTest {
    public androidDriver driver;

    private static By backupBtn = By.id("auto_backup_switch");
    private static By touchOutSideBtn = By.id("touch_outside");
    private static By keepOffBtn = By.xpath("//*[@text='KEEP OFF']");
    private static By photo = By.xpath("//android.view.ViewGroup[contains(@content-desc,'Photo taken')]");
    File classPath, imageDir, img;
    @BeforeTest
    public void setUp() throws MalformedParameterizedTypeException

    {
        capabilities.setCapability(capabilityName:"platformName", value:"Android");
        capabilities.setCapability(capabilityName:"platformVersion", value:"8.0");
        capabilities.setCapability(capabilityName:"deviceName", value:"Android Emulator");
        capabilities.setCapability(capabilityName:"appPackage", value:"com.google.android.apps.photos");
        capabilities.setCapability(capabilityName:"appActivity", value:"Android");

        driver = new AndroidDriver<>(new URL(spec:"http://localhost:4723/wd/hub"), capabilities);
    }
    @Test
    public void send_Photo() throws IOException
    {
        classPath = new File(System.getProperty("user.dir"));
        imageDir = new File(classPath, child:"/resourrces/images");
        img = new File(imageDir.getCanonicalFile(), child:"image-info.png");

        WebDriverWait wait = new WebDriverWait(driver, timeOutlnSeconds:10);
        wait.until(ExceptionConditions.presenceOfElementLocated(backupBtn)).click();
        wait.until(ExceptionConditions.presenceOfElementLocated(touchOutsideBtn)).click();
        wait.until(ExceptionConditions.presenceOfElementLocated(keepOfBtn)).click();

        String Android_Photo_Path = "mnt/sdcard/Pictures";
        driver.pushFile(remotePath: Android_Photo_Path+"/"+img.getName(), img);
        ExceptedCondition condition = ExceptedConditions.numberOfElementsToBe(photo, numbetr:1);
        wait.until(condition);
    }

    @AfterTest
    public void testDown(){
        if(null != driver){
            driver.quit();
        }
    }
}

