package rootcode.roaddamagedetectionserver.pothole.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rootcode.roaddamagedetectionserver.common.ResponseCode;
import rootcode.roaddamagedetectionserver.image.Image;
import rootcode.roaddamagedetectionserver.image.ImageJpaRepository;
import rootcode.roaddamagedetectionserver.image.ImageService;
import rootcode.roaddamagedetectionserver.pothole.controller.PotholeListResponseDTO;
import rootcode.roaddamagedetectionserver.pothole.controller.PotholeMonthlyStatisticsResponseDTO;
import rootcode.roaddamagedetectionserver.pothole.controller.request.AddPotholeRequestDTO;
import rootcode.roaddamagedetectionserver.pothole.controller.response.PotholeDetail;
import rootcode.roaddamagedetectionserver.pothole.controller.response.PotholeDetailResponseDTO;
import rootcode.roaddamagedetectionserver.pothole.controller.response.PotholeStatusResponseDTO;
import rootcode.roaddamagedetectionserver.pothole.dto.converter.PotholeConverter;
import rootcode.roaddamagedetectionserver.pothole.entity.Position;
import rootcode.roaddamagedetectionserver.pothole.entity.Pothole;
import rootcode.roaddamagedetectionserver.pothole.entity.PotholeRegion;
import rootcode.roaddamagedetectionserver.pothole.exception.handler.PotholeHandler;
import rootcode.roaddamagedetectionserver.pothole.repository.PotholeJpaRepository;
import rootcode.roaddamagedetectionserver.region.service.RegionNameService;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static rootcode.roaddamagedetectionserver.pothole.dto.converter.PotholeConverter.convertToPotholeDto;

@RequiredArgsConstructor
@Service
public class PotholeService {
    private final ImageService imageService;
    private final ImageJpaRepository imageJpaRepository;
    private final PotholeJpaRepository potholeRepository;
    private final RegionNameService regionNameService;

    public void addPothole(AddPotholeRequestDTO request) {

        Pothole pothole = createPotholeData(request);
        imageService.createOrGetImage(request.imageUrl(), pothole);
    }

    private Pothole createPotholeData(AddPotholeRequestDTO request) {

        String regionName = regionNameService.getAddressFromCoordinates(request.latitude(), request.longitude());
        String parseRegionName = regionNameService.extractRegionBeforeSecondSpace(regionName);
        PotholeRegion potholeRegion = PotholeRegion.of(parseRegionName);

        Pothole pothole = Pothole.builder()
                .position(new Position(request.latitude(), request.longitude()))
                .region(potholeRegion)
                .address(regionName)
                .foundAt(LocalDateTime.now())
                .isFixed(false)
                .isAiVerified(false)
                .isValid(false)
                .geohash(request.geohash())
                .build();

        return potholeRepository.save(pothole);
    }

    public PotholeDetailResponseDTO getPothole(long potholeId) {
        Pothole pothole = potholeRepository.findById(potholeId)
                .orElseThrow(() -> new PotholeHandler(ResponseCode.POTHOLE_NOT_FOUND));

        Image image = imageJpaRepository.findByPotholeId(potholeId)
                .orElseThrow(() -> new IllegalArgumentException("이미지가 없습니다"));
        String imageUrl = image.getUrl();

        return new PotholeDetailResponseDTO(
                convertToPotholeDto(pothole, imageUrl)
        );
    }

    public PotholeStatusResponseDTO getStatus() {
        List<Pothole> potholes = potholeRepository.findAll();

        long fixedPotholes = potholes.stream()
                .filter(Pothole::isFixed)
                .filter(Pothole::isValid)
                .count();

        long validPotholes = potholes.stream()
                .filter(Pothole::isValid)
                .count();

        return new PotholeStatusResponseDTO(
                validPotholes,
                fixedPotholes,
                validPotholes - fixedPotholes
        );
    }

    public PotholeMonthlyStatisticsResponseDTO getMonthlyStatistics() {
        List<Pothole> potholes = potholeRepository.findAll();

        return new PotholeMonthlyStatisticsResponseDTO(
                countPotholesByMonth(potholes, Month.JANUARY),
                countPotholesByMonth(potholes, Month.FEBRUARY),
                countPotholesByMonth(potholes, Month.MARCH),
                countPotholesByMonth(potholes, Month.APRIL),
                countPotholesByMonth(potholes, Month.MAY),
                countPotholesByMonth(potholes, Month.JUNE),
                countPotholesByMonth(potholes, Month.JULY),
                countPotholesByMonth(potholes, Month.AUGUST),
                countPotholesByMonth(potholes, Month.SEPTEMBER),
                countPotholesByMonth(potholes, Month.OCTOBER),
                countPotholesByMonth(potholes, Month.NOVEMBER),
                countPotholesByMonth(potholes, Month.DECEMBER)
        );
    }

    private long countPotholesByMonth(List<Pothole> potholes, Month month) {
        return potholes.stream()
                .filter(pothole -> pothole.getFoundAt().getMonth() == month)
                .count();
    }

    public PotholeListResponseDTO getAll(Boolean fixed, int page) {
        List<Pothole> potholes = potholeRepository.findAllPotholes(fixed, 30, page);

        int potholeTotalCount = potholeRepository.countAllByIsFixed(fixed);
        int totalPage = (int) Math.ceil(potholeTotalCount / 30.0);

        List<Image> images = imageJpaRepository.findByPotholeIds(
                potholes.stream().map(Pothole::getId).toList()
        );


        List<PotholeDetail> dto = new ArrayList<>();
        for (int i = 0; i < potholes.size(); i++) {
            dto.add(PotholeConverter.convertToPotholeDto(potholes.get(i), images.get(i).getUrl()));
        }

        return new PotholeListResponseDTO(dto, page, totalPage);
    }

    //
    public PotholeListResponseDTO getValidAll(Boolean fixed, int page) {
        List<Pothole> potholes = potholeRepository.findAllValidPotholes(fixed, 30, page);

        int potholeTotalCount = potholeRepository.countAllValidByIsFixed(fixed);
        int totalPage = (int) Math.ceil(potholeTotalCount / 30.0);

        List<Image> images = imageJpaRepository.findByPotholeIds(
                potholes.stream().map(Pothole::getId).toList()
        );


        List<PotholeDetail> dto = new ArrayList<>();
        for (int i = 0; i < potholes.size(); i++) {
            dto.add(PotholeConverter.convertToPotholeDto(potholes.get(i), images.get(i).getUrl()));
        }

        return new PotholeListResponseDTO(dto, page, totalPage);
    }

    public void secondVerification(List<Long> validPotholeIds, List<Long> invalidPotholeIds, List<String> potholeUrl) {
        potholeRepository.validatePotholes(validPotholeIds);
        potholeRepository.invalidatePotholes(invalidPotholeIds);

        // Pothole ID와 URL 매핑하여 이미지 URL 업데이트
        for (int i = 0; i < validPotholeIds.size(); i++) {
            Long potholeId = validPotholeIds.get(i);
            String newUrl = potholeUrl.get(i);

            // Image URL 업데이트
            imageJpaRepository.updateImageUrls(potholeId, newUrl);
        }
    }

    public boolean checkPotholeExists(String geohash) {

        return potholeRepository.existsByGeohash(geohash);
    }
}
