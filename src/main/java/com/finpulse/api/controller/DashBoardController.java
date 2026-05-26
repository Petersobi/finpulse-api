package com.finpulse.api.controller;

import com.finpulse.api.dto.DashboardResponse;
import com.finpulse.api.entity.User;
import com.finpulse.api.service.DashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/dashboard")
@RequiredArgsConstructor
public class DashBoardController {
    private final DashBoardService dashBoardService;
    @GetMapping
    public ResponseEntity<DashboardResponse> getDashBoard(
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(dashBoardService.getDashBoard(user));
    }

}
