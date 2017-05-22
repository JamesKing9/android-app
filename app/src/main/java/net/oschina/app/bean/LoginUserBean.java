package net.oschina.app.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 用来保存已登录用户的信息
 * @author FireAnt（http://my.oschina.net/LittleDY）
 * @version 创建时间：2014年9月27日 下午2:45:57 
 * 
 */

@SuppressWarnings("serial")
@XStreamAlias("oschina")
public class LoginUserBean extends Entity {
	
	@XStreamAlias("result")
	private Result result;
	
	@XStreamAlias("user")
	private User user;

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	/**
	 * 获取用户 user 实例
	 * @return
	 */
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}