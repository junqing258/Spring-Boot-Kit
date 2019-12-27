package org.inlighting.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.*;
import org.apache.shiro.subject.Subject;
import org.inlighting.entity.ResponseBean;
import org.inlighting.entity.UserEntity;
import org.inlighting.exception.UnauthorizedException;
import org.inlighting.service.UserService;
import org.inlighting.util.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "*", maxAge = 24*3600)
@RequestMapping("/api")
public class ApiController {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private UserService userService;

    @Autowired
    public void setService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value="/login", produces="application/json")
    public ResponseBean login(HttpServletRequest request, HttpServletResponse response) {
        //@RequestParam("username") String username, @RequestParam("password") String password
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserEntity userEntity = userService.getUserByName(username);
        if (userEntity.getPassword().equals(password)) {
            String token = JWTUtil.sign(username, password);
            Cookie cookie = new Cookie("Authorization", token);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            Map<String,Object> result = new HashMap<>();
            result.put("token", token);
            result.put("info", userEntity);
            return new ResponseBean(200, "Login success", result);
        } else {
            throw new UnauthorizedException();
        }
    }

    @GetMapping("/user/{id}")
    @RequiresAuthentication
    public ResponseBean user(@PathVariable("id")long id) {
        UserEntity userEntity = userService.getUserById(id);
        return new ResponseBean(200, "You are already logged in", userEntity);
    }

    @GetMapping("/article")
    public ResponseBean article() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return new ResponseBean(200, "You are already logged in", null);
        } else {
            return new ResponseBean(200, "You are guest", null);
        }
    }

    @GetMapping("/require_auth")
    @RequiresAuthentication
    public ResponseBean requireAuth() {
        return new ResponseBean(200, "You are authenticated", null);
    }

    @GetMapping("/require_role")
    @RequiresRoles("admin")
    public ResponseBean requireRole() {
        return new ResponseBean(200, "You are visiting require_role", null);
    }

    @GetMapping("/require_permission")
    @RequiresPermissions(logical = Logical.AND, value = {"view", "edit"})
    public ResponseBean requirePermission() {
        return new ResponseBean(200, "You are visiting permission require edit,view", null);
    }

    @RequestMapping(path = "/401")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseBean unauthorized() {
        return new ResponseBean(401, "Unauthorized", null);
    }
}
