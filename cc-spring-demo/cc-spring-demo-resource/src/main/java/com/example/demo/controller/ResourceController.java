package com.example.demo.controller;

import java.util.Arrays;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cc.cc4414.spring.resource.core.CommonUser;
import cc.cc4414.spring.resource.util.TokenUtils;
import cc.cc4414.spring.resource.util.UserUtils;
import cc.cc4414.spring.web.core.ResultAnnotation;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

@RestController
public class ResourceController {
	@PreAuthorize("hasAuthority('xxx')")
	@GetMapping("test0")
	@ResultAnnotation
	public void test0() {
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("test1")
	@ResultAnnotation
	public CommonUser test1() {
		return UserUtils.getUser();
	}

	@PreAuthorize("permitAll")
	@GetMapping("test2")
	@ResultAnnotation
	public void test2() {
	}

	@PreAuthorize("denyAll")
	@GetMapping("test3")
	@ResultAnnotation
	public void test3() {
	}

	@PreAuthorize("@pms.inner() || hasAuthority('xxx')")
	@GetMapping("test4")
	@ResultAnnotation
	public void test4() {
	}

	@GetMapping("test5")
	@ResultAnnotation
	public void test5() {
	}

	public static void main(String[] args) {
		JSONObject obj = JSONUtil.parseObj(CommonUser.getSys());
		obj.put("authorities", Arrays.asList("xxx"));
		System.out.println(TokenUtils.createAccessToken(obj, "cc-spring"));
	}
}
