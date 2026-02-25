package souk.demo.dto;

public class AdDTO {

    private String title;
    private String description;
    private Double price;
    private Long categoryID;
    private Long AdID;
    private String categoryName;
    private String location;

    // Constructors
    public AdDTO() {
    }

    public AdDTO(String title, String description, Double price, Long categoryID, String categoryName,
            String location) {

        this.title = title;
        this.description = description;
        this.price = price;
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getCategoryId() {
        return categoryID;
    }

    public void setCategoryId(Long categoryID) {
        this.categoryID = categoryID;
    }

    public Long getAdId() {
        return AdID;
    }

    public void setAdId(Long AdID) {
        this.AdID = AdID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
