package dataAccess;
import java.io.File;
import java.util.Date;

public class CreateSaleData {
    private String title;
    private String description;
    private int status;
    private float price;
    private Date pubDate;
    private String sellerEmail;
    private File file;

    
    public CreateSaleData(String title, String description, int status, float price, Date pubDate, String sellerEmail, File file) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.price = price;
        this.pubDate = pubDate;
        this.sellerEmail = sellerEmail;
        this.file = file;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getStatus() { return status; }
    public float getPrice() { return price; }
    public Date getPubDate() { return pubDate; }
    public String getSellerEmail() { return sellerEmail; }
    public File getFile() { return file; }
}
