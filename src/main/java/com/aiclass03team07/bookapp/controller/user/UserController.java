package com.aiclass03team07.bookapp.controller.user;

import com.aiclass03team07.bookapp.dto.user.UserJoinRequestDto;
import com.aiclass03team07.bookapp.dto.user.UserLoginRequestDto;
import com.aiclass03team07.bookapp.entity.UserEntity;
import com.aiclass03team07.bookapp.exception.LoginFailedException;
import com.aiclass03team07.bookapp.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService userService;

    //아이디 중복 체크 API
    @GetMapping("/check-id")
    public ResponseEntity<Map<String, Object>> checkIdDuplicate(@RequestParam("userId") String userId){
        Map<String, Object> response = new HashMap<>();

        boolean isDuplicate = userService.checkUserIdDuplicate(userId);

        response.put("isDuplicate", isDuplicate);
        if(isDuplicate){
            response.put("message", "이미 사용 중인 아이디입니다.");
        }
        else{
            response.put("message", "사용 가능한 아이디입니다.");
        }
        return ResponseEntity.ok(response);
    }

    //회원가입 가입 실행 API

    @PostMapping("/join")
    public ResponseEntity<Map<String, String>> joinUser(@RequestBody UserJoinRequestDto dto){
        Map<String, String> response = new HashMap<>();
        try{
            userService.join(dto);
            response.put("status", "success");
            response.put("message", "회원가입이 정상적으로 완료되었습니다.");
            return ResponseEntity.ok(response);
        } catch(IllegalArgumentException e){
            response.put("status", "fail");
            response.put("message", e.getMessage());
            return ResponseEntity.status(400).body(response);
        } catch( Exception e){
            response.put("status", "error");
            response.put("message", "서버 내부 오류가 발생했습니다.");
            return ResponseEntity.status(500).body(response);
        }
    }

    //로그인 API + 세션 도장 찍기
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(
            @RequestBody UserLoginRequestDto dto,
            HttpServletRequest request){
        Map<String, Object> response = new HashMap<>();
        String userId = dto.getUserId();
        String password = dto.getPassword();

        try{
            UserEntity loginUser = userService.login(userId, password);

            HttpSession session = request.getSession();
            session.setAttribute("loginUser", loginUser.getId());
            session.setAttribute("role", loginUser.getRole());

            response.put("status", "success");
            response.put("message", "로그인에 성공했습니다.");
            response.put("userId", loginUser.getUserId());
            response.put("role", loginUser.getRole());

            return ResponseEntity.ok(response);
        }
        catch (LoginFailedException e){
            response.put("status", "fail");
            response.put("message", e.getMessage());
            return ResponseEntity.status(400).body(response);
        }
        catch (Exception e){
            response.put("status", "error");
            response.put("message", "서버 오류가 발생했습니다.");
            return ResponseEntity.status(500).body(response);
        }
    }

    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);

        if(session != null){
            session.invalidate();
        }
        return ResponseEntity.ok().build();
    }

    //상태유지
// 현재 로그인 유저 권한 (프론트에 권한 전달용)
    @GetMapping("/me")
    public ResponseEntity<?> me(HttpSession session) {
        Long id = (Long) session.getAttribute("loginUser");
        if (id == null) {
            // 비로그인도 정상 상태 → 200 (브라우저 콘솔 빨간 줄 방지)
            return ResponseEntity.ok(Map.of("loggedIn", false));
        }
        String role = (String) session.getAttribute("role");
        Map<String, Object> body = new HashMap<>();
        body.put("loggedIn", true);
        body.put("userId", id);
        body.put("role", role);
        return ResponseEntity.ok(body);
    }
}
