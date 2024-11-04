package rootcode.roaddamagedetectionserver.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rootcode.roaddamagedetectionserver.pothole.entity.Pothole;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ImageService {
    private final ImageJpaRepository imageRepository;

    public Image createOrGetImage(String imageUrl, Pothole pothole) {
        return imageRepository.findByUrl(imageUrl)
                .orElseGet(() -> createImage(imageUrl, pothole));
    }


    private Image createImage(String imageUrl, Pothole pothole) {
        var newImage = new Image(imageUrl, pothole);
        return imageRepository.save(newImage);
    }

    public List<Image> findAllUnverifiedPotholeImages() {
        return imageRepository.findAllUnverifiedPotholeImages();

    }
}
