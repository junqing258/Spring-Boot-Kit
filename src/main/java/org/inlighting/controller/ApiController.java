package org.inlighting.controller;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.inlighting.entity.ResponseBean;
import org.inlighting.entity.UserEntity;
import org.inlighting.exception.UnauthorizedException;
import org.inlighting.service.RedisService;
import org.inlighting.service.UserService;
import org.inlighting.util.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisService redisService;

    private UserService userService;

    @Autowired
    public void setService(UserService userService) {
        this.userService = userService;
    }


    /**
     * 向redis存储值
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
    @RequestMapping("/set")
    public String set(String key, String value) throws Exception{

        redisService.set(key, value);
        return "success";
    }

    /**
     * 获取redis中的值
     * @param key
     * @return
     */
    @RequestMapping("/get")
    public String get(String key){
        try {
            return redisService.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    @PostMapping(value="/login")
    public ResponseBean login(@RequestBody UserEntity loginUser, HttpServletResponse response) {
        //@RequestParam("username") String username, @RequestParam("password") String password
        String username = loginUser.getUsername();
        String password = loginUser.getPassword();

        UserEntity user = userService.getUserByName(username);
        String salt = user.getSalt();
        String secret = new Sha256Hash(password, salt).toHex();

        if (user.getPassword().equals(secret)) {
            String token = JWTUtil.sign(username, secret);
            Cookie cookie = new Cookie("Authorization", token);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            Map<String,Object> result = new HashMap<>();
            result.put("token", token);
            result.put("info", user);
            return new ResponseBean(200, "Login success", result);
        } else {
            throw new UnauthorizedException();
        }
    }

    @RequestMapping("/user_info")
    @RequiresAuthentication
    public ResponseBean userInfo() { /*@PathVariable("id")long id*/
        String principals = (String) SecurityUtils.getSubject().getPrincipal();
        String username = JWTUtil.getUsername(principals);
        UserEntity userEntity = userService.getUserByName(username);
        return new ResponseBean(200, "success", userEntity);
    }

    @RequestMapping(value="/add_user")
    public ResponseBean addUser(@RequestBody UserEntity user) {
        userService.saveUser(user);
        return new ResponseBean(200, "success", null);
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
