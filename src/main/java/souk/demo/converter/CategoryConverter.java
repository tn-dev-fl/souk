package souk.demo.converter;

import org.springframework.stereotype.Component;
import souk.demo.dto.CategoryDTO;
import souk.demo.model.Category;

@Component
public class CategoryConverter extends GenericConverter<Category, CategoryDTO> {

    public CategoryConverter() {
        super(
                // toDTO
                category -> new CategoryDTO(
                        category.getId(),
                        category.getName()),
                // toEntity
                dto -> {
                    Category category = new Category();
                    category.setName(dto.getName());
                    return category;
                });
    }
}