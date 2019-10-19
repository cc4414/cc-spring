package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.result.WebResultEnum;

import cc.cc4414.spring.common.result.ResultException;
import cc.cc4414.spring.common.result.ResultVO;
import cc.cc4414.spring.web.core.ResultAnnotation;

@RestController
public class WebController {
	@GetMapping("test")
	public ResultVO<Integer> test(int i) {
		ResultVO<Integer> resultVO = new ResultVO<>();
		resultVO.setCode("0");
		resultVO.setMessage("成功");
		resultVO.setData(i);
		return new ResultVO<>(i);
	}

	@GetMapping("test1")
	public ResultVO<Integer> test1(int i) {
		return new ResultVO<>(i);
	}

	@GetMapping("test2")
	@ResultAnnotation
	public Integer test2(int i) {
		return i;
	}

	@GetMapping("test3")
	@ResultAnnotation
	public void test3(int i) {
		if (i < 18) {
			throw new ResultException(WebResultEnum.XXX_ERROR);
		}
	}
}
