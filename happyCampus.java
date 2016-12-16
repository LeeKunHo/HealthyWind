package dotax.dotaxTest;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class happyCampus
{
    public static void main( String[] args ) throws InterruptedException, IOException
    {
       String path = System.getProperty("user.dir");       
       System.setProperty("webdriver.chrome.driver",path+"\\chromedriver\\chromedriver.exe");
       
       WebDriver driver = new ChromeDriver();   //기본은 FireFox Driver이지만 파폭 설치하기 귀찮아서 크롬드라이버 씀
               
       
       //기사 제목과 내용과 주소 저장할 변수                  
       String title[] = new String[150];      
       String content[] = new String[20000];   
       String address[] = new String[150];
       String searchList[] = {"비타민", "마늘", "홍삼", "인삼"};
       String nextPage;
       SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy년 MM월 dd일  HH시 mm분 ss초", Locale.KOREA );
       String today = formatter.format ( new Date () );
       System.out.println ( today );
       FileWriter fileWriter = new FileWriter(new File("HappyCampus"+today+".txt"), true);
       BufferedWriter writer = new BufferedWriter(fileWriter);
       //네이버 건강정보 페이지 들어가기
        driver.get("http://www.happycampus.com/");
        new WebDriverWait(driver,500).until(ExpectedConditions.elementToBeClickable(By.id("searchSubmit")));
        driver.findElement(By.id("inputQu")).sendKeys(searchList[0]+" 효능");
        WebElement element = driver.findElement(By.id("searchSubmit"));
        element.submit();
                
        new WebDriverWait(driver, 500).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='btn_more_paper_0']/a[1]")));
        WebElement moreNonmun = driver.findElement(By.id("gnb_m2")); //왼쪽에 있는 논문 클릭
        moreNonmun.click();
        Thread.sleep(100);
        
        System.out.println("-------------"+searchList[0]+"에 대한 논문 리스트-----------");
        System.out.printf("\n\n");
        for(int k=1;k<5;k++) //k는 searchList 안에 있는 리스트 개수
        {
           try{
               for(int j=2; j<5; j++)
                {
                   for(int i=1;i<40;i++)
                    {   
                       try{
                          title[i-1] = driver.findElement(By.cssSelector("li:nth-child("+i+") dl dt a")).getText();
                          content[i-1] = driver.findElement(By.cssSelector("li:nth-child("+i+") dl dd.d_con")).getText();
                          address[i-1] = driver.findElement(By.cssSelector("li:nth-child("+i+") dl dt a")).getAttribute("href");
                                    
	                    	writer.write(title[i-1]);
	                  		writer.newLine();
	                  		writer.write(content[i-1]);
	                  		writer.newLine();
	                  		writer.write(address[i-1]);
	                  		writer.newLine();
	                  		writer.newLine();
	
	                  		
	                  		writer.flush();
                          System.out.println(title[i-1]);
                          System.out.println(content[i-1]);
                          System.out.println(address[i-1]);
                          System.out.printf("\n");
                       }catch(NoSuchElementException e){
                          //System.out.println("끝낫음 담 Page ㄱ");
                       }
                    }
                   //driver.findElement(By.cssSelector("#yw1 span:nth-child("+(j+1)+") a")).click();   //다음페이지 클릭
                   nextPage = driver.findElement(By.cssSelector("#yw1 span:nth-child("+(j+1)+") a")).getAttribute("href");
                   driver.get(nextPage);
                   new WebDriverWait(driver,500).until(ExpectedConditions.elementToBeClickable(By.cssSelector("li:nth-child(1) dl dt a")));
                    Thread.sleep(100);
                }
            }
            catch(NoSuchElementException e){
               if(k==4)
                {
                   System.out.print("\n\n");
                   System.out.println("<<<            수집 완료 !!!           >>>");
                }
                else
                {
                   driver.manage().deleteAllCookies();
                   driver.findElement(By.xpath("//*[@id='header']/div[2]/h1/a")).click();
                    new WebDriverWait(driver, 500).until(ExpectedConditions.presenceOfElementLocated(By.id("inputQu")));
                    //다시 해피캠퍼스 홈피 돌아옴...
                    driver.findElement(By.id("inputQu")).sendKeys(searchList[k]+" 효능");
                    WebElement element2 = driver.findElement(By.id("searchSubmit"));
                    element2.submit();
                            
                    new WebDriverWait(driver, 500).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='btn_more_paper_0']/a[1]")));
                    WebElement moreNonmun2 = driver.findElement(By.id("gnb_m2")); //왼쪽에 있는 논문 클릭
                    moreNonmun2.click();
                    Thread.sleep(100);
                    
                   System.out.println("-------------"+searchList[k]+"에 대한 논문 리스트-----------");
                   System.out.printf("\n\n");
                }
            }  
        }
    }
}