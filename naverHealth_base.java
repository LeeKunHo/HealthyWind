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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class naverHealth 
{
    public static void main( String[] args ) throws InterruptedException, IOException
    {
       String path = System.getProperty("user.dir");       
       System.setProperty("webdriver.chrome.driver",path+"\\chromedriver\\chromedriver.exe");       
       WebDriver driver = new ChromeDriver();   //기본은 FireFox Driver이지만 파폭 설치하기 귀찮아서 크롬드라이버 씀
        //FileWriter fw = new FileWriter("C:\\Users\\master\\Desktop\\a.txt");
       
       //기사 제목과 내용 저장할 변수                  //한 페이지에 기사 10+10 총 20개
       String title[] = new String[150];      //기사 제목 10개
       String content[] = new String[20000];   //기사 내용 
       String title2[] = new String[150];      //기사 제목 10개
       String content2[] = new String[20000];   //기사 내용
       SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy년 MM월 dd일  HH시 mm분 ss초", Locale.KOREA );
       String today = formatter.format ( new Date () );
       System.out.println ( today );
       FileWriter fileWriter = new FileWriter(new File("NaverHelath"+today+".txt"), true);
       BufferedWriter writer = new BufferedWriter(fileWriter);
       //네이버 건강정보 페이지 들어가기
        driver.get("http://news.naver.com/main/list.nhn?mode=LS2D&mid=shm&sid1=103&sid2=241");
        new WebDriverWait(driver,500).until(ExpectedConditions.elementToBeClickable(By.id("main_content")));
                
       
        //기사 제목과 내용 따오기
        for(int k=1;k<4;k++)
        {
        try{                     //최종 페이지, 최종 글까지 끝나면 더이상 Element가 검색이 안되서 그런지 에러가 나더라. 그래서 NoSuchElementException으로 예외처리했음.
           for(int j=2; j<12; j++)
            {
               for(int i=1; i<11; i++)
                {
                   driver.findElement(By.cssSelector("ul.type06_headline li:nth-child("+i+") dl a")).click();
                    
                   title[i-1+(j-2)*10] = driver.findElement(By.id("articleTitle")).getText();
                    content[i-1+(j-2)*10] = driver.findElement(By.id("articleBodyContents")).getText();
                   
                    System.out.printf("---------------- "+(j-1)+" 번째 페이지 "+i+" 번째 글 "+"----------------\n\n");
                    System.out.printf("%s\n%s\n", title[i-1+(j-2)*10],content[i-1+(j-2)*10]);
                    writer.write("---------------- "+(j-1)+" 번째 페이지 "+i+" 번째 글 "+"----------------");
                    writer.newLine();
                    writer.newLine();
                   writer.write(title[i-1+(j-2)*10]);
                   writer.newLine();
                   writer.write(content[i-1+(j-2)*10]);
                   writer.newLine();
			//이것은 작동 잘됨.
                   
                   writer.flush();
                   
                    //
                    //fw.write(title[i-1+(j-2)*10]);
                    //
                    Thread.sleep(100);
                    driver.navigate().back();   //뒤로가기
                    Thread.sleep(100);         //페이지 로딩 기다리기(이거 wait for element해도 될듯?)
                    
                    new WebDriverWait(driver, 50).until(ExpectedConditions.elementToBeClickable(By.cssSelector("ul.type06_headline li:nth-child(1) dl a")));
                    
                }
                
                for(int i=1; i<11; i++)
                {
			//엘리먼트 가져오기, 페이지넘기기 미구현 이건 다음페이지에 해당함
                }                
                        
                driver.findElement(By.cssSelector("div.paging a:nth-child("+j+")")).click();   //다음페이지 클릭
                new WebDriverWait(driver,500).until(ExpectedConditions.elementToBeClickable(By.cssSelector("ul.type06_headline li:nth-child(1) dl a")));   //다음페이지 첫 번째 아티클 로드 될때까지 기다리기  
            }         
        }
        
        }
    }
}