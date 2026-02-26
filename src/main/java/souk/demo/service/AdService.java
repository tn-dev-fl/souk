package souk.demo.service;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import souk.demo.dto.AdDTO;

import souk.demo.model.AdModel;
import souk.demo.model.Category;

import souk.demo.model.UserModel;
import souk.demo.repository.AdRepository;
import souk.demo.repository.CategoryRepository;
import souk.demo.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdService {

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;
    private final AdRepository adRepository;

    public AdService(AdRepository adRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.adRepository = adRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public List<AdDTO> getAllAds() {
        return adRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

    }

    public AdDTO getAdById(Long id) {
        return adRepository.findById(id).map(this::convertToDTO).orElse(null);
    }

    public AdDTO createAd(AdDTO ad) {
        AdModel adEntity = convertToEntity(ad);
        return convertToDTO(adRepository.save(adEntity));
    }

    public AdDTO updateAd(Long id, AdDTO adDTO) {

        return adRepository.findById(id).map(ad -> {

            ad.setTitle(adDTO.getTitle());
            ad.setDescription(adDTO.getDescription());
            ad.setPrice(adDTO.getPrice());
            ad.setLocation(adDTO.getLocation());

            // Fix: Fetch the category entity from the DB before setting it
            if (adDTO.getCategoryId() != null) {
                Category category = categoryRepository.findById(adDTO.getCategoryId())
                        .orElseThrow(
                                () -> new RuntimeException("Category not found with id: " + adDTO.getCategoryId()));
                ad.setCategory(category);
            }

            return convertToDTO(adRepository.save(ad));
        }).orElse(null);
    }

    public void deleteAd(Long id) {
        adRepository.deleteById(id);
    }

    private AdDTO convertToDTO(AdModel ad) {
        // You must convert the Category entity into a CategoryDTO
        return new AdDTO(
                ad.getId(),
                ad.getTitle(),
                ad.getDescription(),
                ad.getPrice(),
                ad.getCategory().getId(),
                ad.getLocation(),
                ad.getUser().getId());

    }

    private AdModel convertToEntity(AdDTO adDTO) {
        AdModel ad = new AdModel();

        ad.setTitle(adDTO.getTitle());
        ad.setDescription(adDTO.getDescription());
        ad.setPrice(adDTO.getPrice());
        ad.setLocation(adDTO.getLocation());

        // Handle Category safely
        if (adDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(adDTO.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Category not found with id " + adDTO.getCategoryId()));
            ad.setCategory(category);
        }

        // Handle User safely
        if (adDTO.getUserId() != null) {
            UserModel user = userRepository.findById(adDTO.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "User not found with id " + adDTO.getUserId()));
            ad.setUser(user);
        }

        return ad;
    }
}
