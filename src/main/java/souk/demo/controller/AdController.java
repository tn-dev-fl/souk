package souk.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import souk.demo.dto.AdDTO;
import souk.demo.service.AdService;

import java.util.List;

@RestController
@RequestMapping("/api/ads")
public class AdController {

    private final AdService adService;

    public AdController(AdService adService) {
        this.adService = adService;
    }

    // Get all ads
    @GetMapping
    public ResponseEntity<List<AdDTO>> getAllAds() {
        return ResponseEntity.ok(adService.getAllAds());
    }

    // Get ad by ID
    @GetMapping("/{id}")
    public ResponseEntity<AdDTO> getAdById(@PathVariable Long id) {
        AdDTO adDTO = adService.getAdById(id);
        return adDTO != null ? ResponseEntity.ok(adDTO) : ResponseEntity.notFound().build();
    }

    // Create new ad
    @PostMapping
    public ResponseEntity<AdDTO> createAd(@RequestBody AdDTO ad) {
        AdDTO createdAd = adService.createAd(ad);
        return ResponseEntity.ok(createdAd);
    }

    // Update ad
    @PutMapping("/{id}")
    public ResponseEntity<AdDTO> updateAd(@PathVariable Long id, @RequestBody AdDTO ad) {
        AdDTO updatedAd = adService.updateAd(id, ad);
        return updatedAd != null ? ResponseEntity.ok(updatedAd) : ResponseEntity.notFound().build();
    }

    // Delete ad
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAd(@PathVariable Long id) {
        adService.deleteAd(id);
        return ResponseEntity.noContent().build();
    }
}
