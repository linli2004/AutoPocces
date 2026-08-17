package com.autoprocess.controller;


import com.autoprocess.common.response.ApiResponse;
import com.autoprocess.dto.LoginRequest;
import com.autoprocess.dto.LoginResponse;
import com.autoprocess.dto.UserResponse;
import com.autoprocess.entity.SysUser;
import com.autoprocess.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService=authService;
    }
    /**
     * 登录。
     *
     * POST /auth/login
     */
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        AuthService.LoginResult loginResult = authService.login(request.username(),request.password());
        UserResponse user = UserResponse.from(loginResult.user());
        return ApiResponse.ok(
                LoginResponse.of(
                        loginResult.token(),
                        user
                )
        );

    }

    /**
     * 获取当前登录用户。
     *
     * GET /auth/me
     */
    @GetMapping("/me")
    public ApiResponse<UserResponse> currentUser() {

        SysUser user = authService.currentUser();

        return ApiResponse.ok(
                UserResponse.from(user)
        );
    }
}
