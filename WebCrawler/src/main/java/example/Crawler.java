package example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;

public class Crawler {
    private HashSet<String> urlset;
    private int maxdepth=2;
    private static Connection connection=null;
    public Crawler(){
        urlset=new HashSet<>();
        connection=DatabaseConnection.getConnection();
    }

    //recursive function
    public void getPageAndLinks(String url,int depth) {
        if (urlset.contains(url))
            return;
        if (depth >= maxdepth)
            return;

        if (urlset.add(url))
            System.out.println(url);
        depth++;

        try {
            //connect to the web app and get document object
        Document document = Jsoup.connect(url).timeout(90000000).get(); //if url doesnot get open the we wait for 5 minutes
        String title = document.title();
        // System.out.println(title);  to print title of the web page.
        String text = document.text().length() < 500 ? document.text() : document.text().substring(0, 499);
        String link = url;

        //save data to database
        PreparedStatement preparedStatement=connection.prepareStatement("Insert into page values(?,?,?);");
        preparedStatement.setString(1,title);
        preparedStatement.setString(2,link);
        preparedStatement.setString(3,text);
        preparedStatement.executeUpdate();

        //Recursively call this method to available links on page
        Elements availableLinksOnPage = document.select("a[href]");//we are getting all hyper links url
            for (Element currentLink : availableLinksOnPage) {
               getPageAndLinks(currentLink.attr("abs:href"), depth);
            }
        }
        catch (IOException ioException){
            ioException.printStackTrace();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public static void main(String[] args) {
            Crawler webcrawler=new Crawler();
            webcrawler.getPageAndLinks("https://www.javatpoint.com",0);
    }
}

