package com.beginvegan.domain.magazine.application;

import com.beginvegan.domain.block.dto.BlockDto;
import com.beginvegan.domain.bookmark.domain.repository.BookmarkRepository;
import com.beginvegan.domain.bookmark.domain.repository.ContentType;
import com.beginvegan.domain.magazine.domain.Magazine;
import com.beginvegan.domain.magazine.domain.repository.MagazineRepository;
import com.beginvegan.domain.magazine.dto.response.MagazineDetailRes;
import com.beginvegan.domain.magazine.dto.response.MagazineListRes;
import com.beginvegan.domain.magazine.exception.MagazineNotFoundException;
import com.beginvegan.domain.user.application.UserService;
import com.beginvegan.domain.user.domain.User;
import com.beginvegan.global.DefaultAssert;
import com.beginvegan.global.config.security.token.UserPrincipal;
import com.beginvegan.global.payload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MagazineService {

    private final MagazineRepository magazineRepository;
    private final UserService userService;
    private final BookmarkRepository bookmarkRepository;

    // 2가지 매거진 조회 : 메인 페이지
    public ResponseEntity<?> findTwoMagazines() {
        List<Magazine> magazines = magazineRepository.findAll();

        List<MagazineListRes> magazineList = new ArrayList<>();

        for (Magazine magazine : magazines) {
            MagazineListRes magazineListRes = MagazineListRes.builder()
                    .id(magazine.getId())
                    .title(magazine.getTitle())
                    .editor(magazine.getEditor())
                    .build();
            magazineList.add(magazineListRes);
        }

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information(magazineList)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    // 매거진 상세 조회 : id를 통해 조회
    public ResponseEntity<?> findMagazineDetail(UserPrincipal userPrincipal, Long magazineId) {
        Optional<Magazine> magazineOptional = magazineRepository.findMagazineById(magazineId);
        Magazine magazine = magazineOptional.orElseThrow(() -> new MagazineNotFoundException("해당 아이디를 가진 매거진을 찾을 수 없습니다. ID: " + magazineId));

        List<BlockDto> blockDtos = magazine.getMagazineBlocks().stream()
                .map(block -> BlockDto.builder()
                        .content(block.getContent())
                        .sequence(block.getSequence())
                        .isBold(block.getIsBold())
                        .build())
                .sorted(Comparator.comparing(BlockDto::getSequence))
                .collect(Collectors.toList());

        MagazineDetailRes magazineDetailRes = MagazineDetailRes.builder()
                .id(magazine.getId())
                .title(magazine.getTitle())
                .thumbnail(magazine.getThumbnail())
                .editor(magazine.getEditor())
                .createdDate(magazine.getCreatedDate())
                .magazineContents(blockDtos) // magazineBlocks
                .isBookmarked(isBookMarked(userPrincipal, magazine.getId()))
                .build();

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information(magazineDetailRes)
                .build();

        return ResponseEntity.ok(apiResponse);

    }

    public Magazine validateMagazineById(Long magazineId) {
        Optional<Magazine> findMagazine = magazineRepository.findById(magazineId);
        DefaultAssert.isTrue(findMagazine.isPresent(), "잘못된 매거진 정보입니다.");
        return findMagazine.get();
    }


    public ResponseEntity<?> findAllMagazines(UserPrincipal userPrincipal, Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        List<Magazine> magazines = magazineRepository.findAll(pageable).getContent();
        List<MagazineListRes> magazineList = new ArrayList<>();

        for (Magazine magazine : magazines) {
            MagazineListRes magazineListRes = MagazineListRes.builder()
                    .id(magazine.getId())
                    .title(magazine.getTitle())
                    .thumbnail(magazine.getThumbnail())
                    .editor(magazine.getEditor())
                    .createdDate(magazine.getCreatedDate())
                    .isBookmarked(isBookMarked(userPrincipal, magazine.getId()))
                    .build();
            magazineList.add(magazineListRes);
        }

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information(magazineList)
                .build();

        return ResponseEntity.ok(apiResponse);
    }

    public ResponseEntity<?> findThreeMagazines(UserPrincipal userPrincipal) {
        List<Magazine> magazines = magazineRepository.findAll();
        List<MagazineListRes> magazineList = new ArrayList<>();

        // 매거진 리스트가 3개 미만인 경우 모든 매거진을 반환
        if (magazines.size() <= 3) {
            for (Magazine magazine : magazines) {
                MagazineListRes magazineListRes = MagazineListRes.builder()
                        .id(magazine.getId())
                        .title(magazine.getTitle())
                        .thumbnail(magazine.getThumbnail())
                        .editor(magazine.getEditor())
                        .createdDate(magazine.getCreatedDate())
                        .isBookmarked(isBookMarked(userPrincipal, magazine.getId()))
                        .build();
                magazineList.add(magazineListRes);
            }
        } else {
            // 랜덤하게 3개의 매거진 선택
            Set<Integer> randomNums = new HashSet<>();
            while (randomNums.size() < 3) {
                randomNums.add((int) (Math.random() * magazines.size()));
            }
            for (int num : randomNums) {
                MagazineListRes magazineListRes = MagazineListRes.builder()
                        .id(magazines.get(num).getId())
                        .title(magazines.get(num).getTitle())
                        .thumbnail(magazines.get(num).getThumbnail())
                        .editor(magazines.get(num).getEditor())
                        .createdDate(magazines.get(num).getCreatedDate())
                        .isBookmarked(isBookMarked(userPrincipal, magazines.get(num).getId()))
                        .build();
                magazineList.add(magazineListRes);
            }
        }

        ApiResponse apiResponse = ApiResponse.builder()
                .check(true)
                .information(magazineList)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    private Boolean isBookMarked(UserPrincipal userPrincipal, Long magazineId) {
        User user = userService.validateUserById(userPrincipal.getId());
        Boolean isBookmarked = bookmarkRepository.existsByUserAndContentIdAndContentType(user, magazineId, ContentType.MAGAZINE);
        if(isBookmarked == null) {
            isBookmarked = false;
        }
        return isBookmarked;
    }

}
