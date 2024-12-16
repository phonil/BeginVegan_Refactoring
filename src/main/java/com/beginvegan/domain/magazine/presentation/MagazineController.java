package com.beginvegan.domain.magazine.presentation;

import com.beginvegan.domain.magazine.application.MagazineService;
import com.beginvegan.domain.magazine.dto.response.MagazineDetailRes;
import com.beginvegan.domain.magazine.dto.response.MagazineListRes;
import com.beginvegan.global.config.security.token.CurrentUser;
import com.beginvegan.global.config.security.token.UserPrincipal;
import com.beginvegan.global.payload.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Tag(name = "Magazines", description = "Magazines API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/magazines")
public class MagazineController {

    private final MagazineService magazineService;

    // 매거진 상세 정보 조회
    @Operation(summary = "매거진 상세 정보 조회", description = "magazine_id를 통한 매거진 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "매거진 상세 정보 조회 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MagazineDetailRes.class)) } ),
            @ApiResponse(responseCode = "400", description = "매거진 상세 정보 조회 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @GetMapping("{id}")
    public ResponseEntity<?> findMagazineDetail(
            @CurrentUser UserPrincipal userPrincipal,
            @PathVariable Long id) {
        return magazineService.findMagazineDetail(userPrincipal, id);
    }

    //매거진 전체 목록 조회
    @Operation(summary = "전체 매거진 목록 조회", description = "전체 매거진 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전체 매거진 목록 조회 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MagazineListRes.class)) } ),
            @ApiResponse(responseCode = "400", description = "전체 매거진 목록 조회 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @GetMapping("/all")
    public ResponseEntity<?> findAllMagazines(
            @Parameter(description = "레시피 리스트를 조회합니다. **Page는 0부터 시작합니다!**", required = true)
            @CurrentUser UserPrincipal userPrincipal,
            @RequestParam(value = "page") Integer page){
        return magazineService.findAllMagazines(userPrincipal,page);
    }

    //매거진 랜덤 3가지 조회
    @Operation(summary = "3가지 매거진 목록 조회", description = "3가지 매거진 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "3가지 매거진 목록 조회 성공", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MagazineListRes.class)) } ),
            @ApiResponse(responseCode = "400", description = "3가지 매거진 목록 조회 실패", content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @GetMapping("/home/magazine")
    public ResponseEntity<?> findThreeMagazines(@CurrentUser UserPrincipal userPrincipal){
        return magazineService.findThreeMagazines(userPrincipal);
    }
}
