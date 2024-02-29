package br.ce.wcaquino.tasks.functional;

import br.ce.wcaquino.core.DriverFactory;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


import java.sql.Driver;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static br.ce.wcaquino.core.DriverFactory.driver;


public class TasksTest {

    @Before
    public void setup(){
       DriverFactory.setupDriver();
    }

    @Test
    public void deveSalvarTarefaComSucesso()  {

        driver.navigate().to("http://localhost:8001/tasks/");
        Assert.assertTrue(driver.findElement(By.xpath("//h1[text()='Tasks']")).getText().contains("Tasks"));
        driver.findElement(By.id("addTodo")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("add"));
        driver.findElement(By.id("task")).sendKeys("Task automatizada no " +
                "selenium");
        driver.findElement(By.id("dueDate")).sendKeys(LocalDate.now().plusDays(new Random().nextInt(30) + 1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        driver.findElement(By.id("saveButton")).click();
        Assert.assertEquals("Sucess!",
                driver.findElement(By.id("message")).getText());

    }

    @Test
    public void deveFalharAoSalvarComDataPassada(){
        driver.navigate().to("http://localhost:8001/tasks/");
        Assert.assertTrue(driver.findElement(By.xpath("//h1[text()='Tasks']")).getText().contains("Tasks"));
        driver.findElement(By.id("addTodo")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("add"));
        driver.findElement(By.id("task")).sendKeys("Task automatizada no " +
                "selenium");
        driver.findElement(By.id("dueDate")).sendKeys("10/10/2020");
        driver.findElement(By.id("saveButton")).click();
        Assert.assertEquals("Due date must not be in past",
                driver.findElement(By.id("message")).getText());

    }

    @Test
    public void deveFalharAoSalvarSemDescricao(){
        driver.navigate().to("http://localhost:8001/tasks/");
        Assert.assertTrue(driver.findElement(By.xpath("//h1[text()='Tasks']")).getText().contains("Tasks"));
        driver.findElement(By.id("addTodo")).click();
        Assert.assertTrue(driver.getCurrentUrl().contains("add"));
        driver.findElement(By.id("dueDate")).sendKeys("10/10/2020");
        driver.findElement(By.id("saveButton")).click();
        Assert.assertEquals("Fill the task description",
                driver.findElement(By.id("message")).getText());

    }

    @After
    public void tearDown(){

        DriverFactory.killDriver();
    }
}
