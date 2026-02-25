package souk.demo.dto;

import lombok.Data;

@Data
public class CategoryDTO {

    private String name;

    private Long id;
    public CategoryDTO() {
    }

    public CategoryDTO(Long id,String name) {

        this.name = name;
        this.id=id;
    }

}
