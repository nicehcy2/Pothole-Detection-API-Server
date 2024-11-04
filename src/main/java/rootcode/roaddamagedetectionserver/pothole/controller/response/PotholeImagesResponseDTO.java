package rootcode.roaddamagedetectionserver.pothole.controller.response;

import rootcode.roaddamagedetectionserver.image.Image;

import java.util.List;

public record PotholeImagesResponseDTO(
        List<ImageInfo> images
) {
    record ImageInfo(
            long id,
            String url,
            long potholeId
    ) {
    }

    public static PotholeImagesResponseDTO of(List<Image> images) {
        return new PotholeImagesResponseDTO(
                images.stream()
                        .map(image -> new ImageInfo(
                                image.getId(),
                                image.getUrl(),
                                image.getPothole().getId()
                        ))
                        .toList()
        );
    }
}
