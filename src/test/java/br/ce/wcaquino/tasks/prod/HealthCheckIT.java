package br.ce.wcaquino.tasks.prod;


import br.ce.wcaquino.core.DriverFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static br.ce.wcaquino.core.DriverFactory.driver;


public class HealthCheckIT {

    @Before
    public void setup(){
        DriverFactory.getDriver();
    }

    @Test
    public void healthCheck() {
        driver.navigate().to("http://host.docker.internal:9999/tasks/");
        String version = driver.findElement(By.id("version")).getText();
        Assert.assertTrue(version.startsWith("build"));

    }

    @After
    public void tearDown() {
        DriverFactory.killDriver();
    }


}
