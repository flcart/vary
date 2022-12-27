package org.luvsa.sys;

import lombok.Data;
import org.springframework.lang.Nullable;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户信息
 *
 * @author Dale
 * @create 2022/10/18 11:10
 */
@Data
public class Userinfo implements Serializable {
    @Serial
    private static final long serialVersionUID = 6398678505579131105L;
    /**
     * 用户id
     */
    private String guid;
    /**
     * 用户账号名称， 注意不是用户名
     */
    private String name;
    /**
     * 账号头像
     */
    private String avatar;
    /**
     * 电话
     */
    private String phone;
    /**
     * 邮箱
     */
    @Nullable
    private String email;

    /**
     * 性别， 需用到数据字典， 可以考虑使用 Boolean 值来标识
     */
    private Integer sex;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 用户简介
     */
    private String description;
    /**
     * 微信小程序登录的 session 唯一标识码
     */
    private String union;
    /**
     * 微信小程序登录的 session 的 appid
     */
    private String appid;

    /**
     * 微信小程序登录的 session 的 openid
     */
    private String openid;
    /**
     * 是否过期
     */
    private boolean expired;

    /**
     * 是否被锁定
     */
    private boolean locked;

    private boolean enabled = true;

    private boolean valid;

    /**
     * 是否为系统管理员
     */
    private boolean admin;
}
