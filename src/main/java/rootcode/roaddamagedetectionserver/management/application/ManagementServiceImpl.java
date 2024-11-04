package rootcode.roaddamagedetectionserver.management.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rootcode.roaddamagedetectionserver.common.ResponseCode;
import rootcode.roaddamagedetectionserver.image.Image;
import rootcode.roaddamagedetectionserver.image.ImageJpaRepository;
import rootcode.roaddamagedetectionserver.management.Management;
import rootcode.roaddamagedetectionserver.management.ManagementJpaRepository;
import rootcode.roaddamagedetectionserver.management.controller.request.UpdatePotholeManagementRequestDTO;
import rootcode.roaddamagedetectionserver.management.controller.response.RepairPotholesResponseDTO;
import rootcode.roaddamagedetectionserver.pothole.entity.Pothole;
import rootcode.roaddamagedetectionserver.pothole.entity.RoadType;
import rootcode.roaddamagedetectionserver.pothole.exception.handler.PotholeHandler;
import rootcode.roaddamagedetectionserver.pothole.repository.PotholeJpaRepository;
import rootcode.roaddamagedetectionserver.user.User;
import rootcode.roaddamagedetectionserver.user.UserJpaRepository;
import rootcode.roaddamagedetectionserver.user.controller.exception.handler.UserHandler;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ManagementServiceImpl implements ManagementService {
    private final PotholeJpaRepository potholeRepository;
    private final ManagementJpaRepository managementRepository;
    private final ImageJpaRepository imageJpaRepository;
    private final UserJpaRepository userJpaRepository;

    @Transactional
    @Override
    public void updatePotholeManagementInfo(
            long potholeId,
            UpdatePotholeManagementRequestDTO request
    ) {
        Pothole pothole = potholeRepository.findById(potholeId)
                .orElseThrow(() -> new IllegalArgumentException("Pothole not found"));

        pothole.applyManagementInfo(
                request.isFixed(),
                RoadType.of(request.roadType())
        );

        Management management = request.toDomain(pothole);
        managementRepository.save(management);
    }

    //
    @Override
    public List<RepairPotholesResponseDTO> lookUpPotholes(long userId, int page) {
        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ResponseCode.USER_NOT_FOUND));

        if (!user.isAdmin()) {
            throw new AccessDeniedException("관리자 권한이 필요합니다.");
        }

        List<Pothole> potholes = potholeRepository.findByIsFixed(false, 30, page);
        List<Image> images = imageJpaRepository.findByPotholeIds(
                potholes.stream().map(Pothole::getId).toList()
        );

        List<RepairPotholesResponseDTO> dto = new ArrayList<>();
        for (int i = 0; i < potholes.size(); i++) {
            dto.add(RepairPotholesResponseDTO.of(potholes.get(i), images.get(i)));
        }

        return dto;
    }

    @Override
    @Transactional
    public long repairPothole(long potholeId, long userId) {
        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ResponseCode.USER_NOT_FOUND));

        if (!user.isAdmin()) {
            throw new AccessDeniedException("관리자 권한이 필요합니다.");
        }

        Pothole pothole = potholeRepository.findById(potholeId)
                .orElseThrow(() -> new PotholeHandler(ResponseCode.POTHOLE_NOT_FOUND));

        pothole.setFixed(true);

        return potholeRepository.save(pothole).getId();
    }
}
