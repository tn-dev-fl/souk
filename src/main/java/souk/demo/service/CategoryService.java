package souk.demo.service;

import org.springframework.stereotype.Service;
import souk.demo.dto.CategoryDTO;
import souk.demo.model.Category;
import souk.demo.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CategoryDTO getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(this::convertToDTO) // Fixed: Matches method name below
                .orElse(null);
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = convertToEntity(categoryDTO);
        return convertToDTO(categoryRepository.save(category));
    }

    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        return categoryRepository.findById(id).map(existingCategory -> {
            existingCategory.setName(categoryDTO.getName());
            return convertToDTO(categoryRepository.save(existingCategory)); // Fixed: Matches method name below
        }).orElse(null);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    private CategoryDTO convertToDTO(Category category) {
        return new CategoryDTO(category.getId(), category.getName());
    }

    private Category convertToEntity(CategoryDTO categoryDTO) {
        Category category = new Category();

        category.setName(categoryDTO.getName());
        return category;
    }
}