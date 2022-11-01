/*
 * ========================================================================
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ========================================================================
 */
package al.selenium.in.rush.suite;

import al.selenium.in.rush.pages.Homepage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverService;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

public class LoginTest {
    private Homepage homepage;
    private WebDriver webDriver;
    public static Collection<WebDriver> getBrowserVersions() {
        ChromeDriverService chromeDriverService = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File("drivers/chromedriver"))
                .usingAnyFreePort()
                .build();
//        GeckoDriverService geckoDriverService = new GeckoDriverService.Builder()
//                .usingDriverExecutable(new File("drivers/geckodriver"))
//                .usingAnyFreePort()
//                .build();
        return Arrays.asList(new WebDriver[]{new ChromeDriver(chromeDriverService)});
    }
    @ParameterizedTest
    @MethodSource("getBrowserVersions")
    public void loginWithValidCredentials(WebDriver webDriver) {
        this.webDriver = webDriver;
        homepage = new Homepage(webDriver);
        homepage
                .openFormAuthentication()
                .loginWith("tomsmith", "SuperSecretPassword!")
                .thenLoginSuccessful();
    }
    @ParameterizedTest
    @MethodSource("getBrowserVersions")
    public void loginWithInvalidCredentials(WebDriver webDriver) {
        this.webDriver = webDriver;
        homepage = new Homepage(webDriver);
        homepage
                .openFormAuthentication()
                .loginWith("tomsmith", "SuperSecretPassword")
                .thenLoginUnsuccessful();
    }
    @AfterEach
    void tearDown() {
        webDriver.quit();
    }
}
