package souk.demo.service;

import org.springframework.stereotype.Service;
import souk.demo.converter.CategoryConverter;
import souk.demo.dto.CategoryDTO;
import souk.demo.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;

    public CategoryService(CategoryRepository categoryRepository, CategoryConverter categoryConverter) {
        this.categoryRepository = categoryRepository;
        this.categoryConverter = categoryConverter;
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryConverter.toDTOList(categoryRepository.findAll());
    }

    public CategoryDTO getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryConverter::toDTO)
                .orElse(null);
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        return categoryConverter.toDTO(
                categoryRepository.save(categoryConverter.toEntity(categoryDTO)));
    }

    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        return categoryRepository.findById(id).map(existing -> {
            existing.setName(categoryDTO.getName());
            return categoryConverter.toDTO(categoryRepository.save(existing));
        }).orElse(null);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}