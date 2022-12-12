package org.luvsa.sys;

/**
 * 用户信息
 *
 * @author Dale
 * @create 2022/10/18 11:10
 */
public class Userinfo {

    /**
     * 用户id
     */
    private String guid;

    /**
     * 用户名称
     */
    private String name;

    private String phone;

    private String email;

    /**
     * 是否过期
     */
    private boolean expired;

    /**
     * 是否被锁定
     */
    private boolean locked;

	/**
	 * 是否为系统管理员
	 */
    private boolean admin;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
