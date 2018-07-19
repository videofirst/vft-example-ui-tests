/*
 * Copyright (c) 2017-present, Video First Software
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package co.videofirst.vft.uitests.configuration;

import static co.videofirst.vft.uitests.configuration.TestConfiguration.Browsers.chrome;
import static co.videofirst.vft.uitests.configuration.TestConfiguration.Browsers.firefox;

import com.codeborne.selenide.WebDriverRunner;
import com.tngtech.jgiven.integration.spring.EnableJGiven;
import io.github.bonigarcia.wdm.WebDriverManager;
import javax.annotation.PostConstruct;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Spring based configuration for project.
 *
 * This class also uses the WebDriverManager to download and run browsers.
 *
 * @author Bob Marks
 */
@Configuration
@EnableJGiven
@PropertySource(ignoreResourceNotFound = true,
    value = {"classpath:/test.properties", "classpath:/test-${test.env:}.properties"})
@ComponentScan(basePackages = {"co.videofirst.vft.uitests.stages",
    "co.videofirst.vft.uitests.pageobjects"})
public class TestConfiguration {

    enum Browsers {chrome, firefox}

    @Value("${selenium.web-browser}")
    private String seleniumWebBrowser;

    @PostConstruct
    public void setup() {
        // Let Selenide know which browser to use
        System.setProperty("selenide.browser", seleniumWebBrowser.toString());

        if (chrome.toString().equals(seleniumWebBrowser)) {
            WebDriverManager.chromedriver().setup();
            WebDriver driver = new ChromeDriver();
            WebDriverRunner.setWebDriver(driver);
        } else if (firefox.toString().equals(seleniumWebBrowser)) {
            WebDriverManager.firefoxdriver().setup();
            WebDriverRunner.setWebDriver(new FirefoxDriver());
        }
    }

}
