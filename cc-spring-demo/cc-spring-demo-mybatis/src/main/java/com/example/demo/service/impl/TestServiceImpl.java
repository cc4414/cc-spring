package com.example.demo.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Test;
import com.example.demo.mapper.TestMapper;
import com.example.demo.service.ITestService;

@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements ITestService {

}
