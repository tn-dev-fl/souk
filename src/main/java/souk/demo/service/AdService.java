package souk.demo.service;

import org.springframework.stereotype.Service;
import souk.demo.converter.AdConverter;
import souk.demo.dto.AdDTO;
import souk.demo.model.Category;
import souk.demo.repository.AdRepository;
import souk.demo.repository.CategoryRepository;

import java.util.List;

@Service
public class AdService {

    private final AdRepository adRepository;
    private final AdConverter adConverter;
    private final CategoryRepository categoryRepository;

    public AdService(AdRepository adRepository, AdConverter adConverter, CategoryRepository categoryRepository) {
        this.adRepository = adRepository;
        this.adConverter = adConverter;
        this.categoryRepository = categoryRepository;
    }

    public List<AdDTO> getAllAds() {
        return adConverter.toDTOList(adRepository.findAll());
    }

    public AdDTO getAdById(Long id) {
        return adRepository.findById(id)
                .map(adConverter::toDTO)
                .orElse(null);
    }

    public AdDTO createAd(AdDTO adDTO) {
        return adConverter.toDTO(
                adRepository.save(adConverter.toEntity(adDTO)));
    }

    public AdDTO updateAd(Long id, AdDTO adDTO) {
        return adRepository.findById(id).map(ad -> {
            ad.setTitle(adDTO.getTitle());
            ad.setDescription(adDTO.getDescription());
            ad.setPrice(adDTO.getPrice());
            ad.setLocation(adDTO.getLocation());

            if (adDTO.getCategoryId() != null) {
                Category category = categoryRepository.findById(adDTO.getCategoryId())
                        .orElseThrow(() -> new RuntimeException(
                                "Category not found with id: " + adDTO.getCategoryId()));
                ad.setCategory(category);
            }

            return adConverter.toDTO(adRepository.save(ad));
        }).orElse(null);
    }

    public void deleteAd(Long id) {
        adRepository.deleteById(id);
    }
}