package com.c9Pay.userservice.web.docs;

import com.c9Pay.userservice.data.dto.user.UserDto;
import com.c9Pay.userservice.data.dto.user.UserResponse;
import com.c9Pay.userservice.data.dto.user.UserUpdateParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Tag(name = "사용자 관리")
public interface UserControllerDocs {

    @Operation(summary = "회원가입 요청")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "회원가입"),
                    @ApiResponse(responseCode = "400", description = "회원가입 실패")
            }
    )
    ResponseEntity<?> signUp(UserDto form);

    @Operation(summary = "사용자 개인 정보 요청")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "회원 정보", content = @Content(schema = @Schema(implementation = UserResponse.class))),
                    @ApiResponse(responseCode = "400", description = "로그인 필요")
            }
    )
    ResponseEntity<?> getUserDetail(String token);

    @Operation(summary = "회원 탈퇴 요청")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "회원 삭제" ),
                    @ApiResponse(responseCode = "400", description = "로그인 필요")
            }
    )
    ResponseEntity<?> deleteUser(String token, HttpServletResponse response);

    @Operation(summary = "회원 정보 수정 요청")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "회원 정보 수정", content = @Content(schema = @Schema(implementation = UserUpdateParam.class))),
                    @ApiResponse(responseCode = "400", description = "로그인 필요")
            }
    )
    ResponseEntity<?> updateUserInfo(String token, UserUpdateParam param, HttpServletResponse response);

    @Operation(summary = "회원 개체식별번호 요청")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "회원 개체식별번호 요청",
                            content = @Content(schema = @Schema(type=MediaType.TEXT_PLAIN_VALUE, example = "Random UUID"))),
                    @ApiResponse(responseCode = "400", description = "로그인 필요")
            }
    )
    ResponseEntity<?> getSerialNumber(String token);

    @Operation(summary = "아이디 중복 확인 요청")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "아이디 중복 안됨"),
                    @ApiResponse(responseCode = "400", description = "아이디 중복 됨")
            }
    )
    ResponseEntity<?> checkDuplicated(String userId);
}
