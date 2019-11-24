package cc.cc4414.spring.auth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cc.cc4414.spring.auth.service.IAuthService;
import cc.cc4414.spring.web.core.ResultAnnotation;
import lombok.RequiredArgsConstructor;

/**
 * 认证服务控制层
 * 
 * @author cc 2019年11月24日
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final IAuthService iAuthService;

	/**
	 * 退出登录<br>
	 * 
	 * 使当前用户在当前时间之前生成的token全部失效<br>
	 * 只有token意外泄露才使用此方法<br>
	 * 一般情况下退出登录只需要前端清除保存的token<br>
	 */
	@PostMapping("logout")
	@PreAuthorize("isAuthenticated()")
	@ResultAnnotation
	public void logout() {
		iAuthService.logout();
	}
}
