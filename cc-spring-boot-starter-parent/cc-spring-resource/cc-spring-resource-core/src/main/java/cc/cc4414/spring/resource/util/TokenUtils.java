package cc.cc4414.spring.resource.util;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;

import cc.cc4414.spring.resource.core.ResourceConsts;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.experimental.UtilityClass;

/**
 * token工具类
 * 
 * @author cc 2019年10月21日
 */
@UtilityClass
public class TokenUtils {
	/**
	 * 判断token是否失效<br>
	 * jwt是无状态的，所以注销问题是个痛点，这里采取黑名单的形式解决这个问题<br>
	 * 每个jwt生成的时候，里面包含用户的id以及创建时间iat<br>
	 * 当想要强制让某些jwt失效时，在redis中以id为key，iat为value保存一条数据<br>
	 * 每次请求都校验redis中id对应的iat，如果redis中存在对应的iat，并且晚于jwt中的iat，则jwt无效<br>
	 * 
	 * @param value         token值，包含bearer
	 * @param redisTemplate StringRedisTemplate
	 * @return true为失效，false为有效
	 */
	public boolean isExpires(String value, StringRedisTemplate redisTemplate) {
		if (value != null && value.toLowerCase().startsWith(ResourceConsts.BEARER_TYPE)) {
			String token = value.substring(ResourceConsts.BEARER_TYPE.length()).trim();
			Jwt jwt = JwtHelper.decode(token);
			String claims = jwt.getClaims();
			JSON json = JSONUtil.parse(claims);
			String id = json.getByPath(ResourceConsts.ID, String.class);
			String date = redisTemplate.opsForValue().get(ResourceConsts.EXPIRES + id);
			if (date != null) {
				long iat = json.getByPath(ResourceConsts.IAT, Long.class);
				if (iat < Long.parseLong(date)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 根据用户信息和秘钥生成令牌
	 * 
	 * @param user 用户，其中权限字段为authorities，是个Collection
	 * @param key  秘钥
	 * @return 生成的令牌
	 */
	public String createAccessToken(Object user, String key) {
		JSONObject obj = JSONUtil.parseObj(user);
		// 有效期12小时
		obj.put("exp", System.currentTimeMillis() / 1000 + 3600 * 12);
		// 创建时间为当前
		obj.put(ResourceConsts.IAT, System.currentTimeMillis());
		return JwtHelper.encode(obj.toString(), new MacSigner(key)).getEncoded();
	}
}
