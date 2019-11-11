package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import cc.cc4414.spring.mybatis.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("mybatis_test")
@ApiModel(value = "Test对象", description = "测试表")
public class Test extends BaseEntity {
	private static final long serialVersionUID = 1L;
}
