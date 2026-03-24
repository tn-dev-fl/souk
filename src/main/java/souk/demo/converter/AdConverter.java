package souk.demo.converter;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;
import souk.demo.dto.AdDTO;
import souk.demo.model.AdModel;
import souk.demo.model.Category;
import souk.demo.model.UserModel;
import souk.demo.repository.CategoryRepository;
import souk.demo.repository.UserRepository;

@Component
public class AdConverter extends GenericConverter<AdModel, AdDTO> {

    public AdConverter(CategoryRepository categoryRepository, UserRepository userRepository) {
        super(

                ad -> new AdDTO(
                        ad.getId(),
                        ad.getTitle(),
                        ad.getDescription(),
                        ad.getPrice(),
                        ad.getCategory().getId(),
                        ad.getLocation(),
                        ad.getUser().getId()),

                dto -> {
                    AdModel ad = new AdModel();
                    ad.setTitle(dto.getTitle());
                    ad.setDescription(dto.getDescription());
                    ad.setPrice(dto.getPrice());
                    ad.setLocation(dto.getLocation());

                    if (dto.getCategoryId() != null) {
                        Category category = categoryRepository.findById(dto.getCategoryId())
                                .orElseThrow(() -> new EntityNotFoundException(
                                        "Category not found with id: " + dto.getCategoryId()));
                        ad.setCategory(category);
                    }

                    if (dto.getUserId() != null) {
                        UserModel user = userRepository.findById(dto.getUserId())
                                .orElseThrow(() -> new EntityNotFoundException(
                                        "User not found with id: " + dto.getUserId()));
                        ad.setUser(user);
                    }

                    return ad;
                });
    }
}