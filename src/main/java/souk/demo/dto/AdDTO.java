package souk.demo.dto;

public class AdDTO {
    private Long id;
    private String title;
    private String description;
    private Double price;
    private Long categoryID;

    private String location;
    private Long userID;

    // Constructors
    public AdDTO() {
    }

    public AdDTO(Long id, String title, String description, Double price, Long categoryID,
            String location, Long userID) {
        this.id = id;

        this.title = title;
        this.description = description;
        this.price = price;
        this.categoryID = categoryID;

        this.location = location;
        this.userID = userID;

    }

    public String getTitle() {
        return title;
    }

    public void test() {

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

    public Long getUserId() {
        return userID;
    }

    public void setUserId(Long userID) {
        this.userID = userID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
