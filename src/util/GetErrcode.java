package util;

public class GetErrcode {
	public static String get(int errcode){
		String rs="";
		switch (errcode) {
		case -1:
			rs="系统繁忙";
			break;
		case 0:
			rs="请求成功";
			break;
		case 40001:
			rs="获取access_token时CorpSecret错误，或者access_token无效";
			break;
		case 40003:
			rs="不合法的UserID";
			break;
		case 40013:
			rs="不合法的corpid";
			break;
		case 40014:
			rs="不合法的access_token";
			break;
		case 40057:
			rs="不合法的callbackurl或者callbackurl验证失败";
			break;
		case 40091:
			rs="无效secert";
			break;
		case 45009:
			rs="接口调用超过限制";
			break;
		case 45024:
			rs="企业已禁用";
			break;
		case 60001:
			rs="部门长度不符合限制";
			break;
		case 60002:
			rs="部门层级深度超过限制";
			break;
		case 60003:
			rs="部门不存在";
			break;
		case 60004:
			rs="父部门不存在";
			break;
		case 60005:
			rs="不允许删除有成员的部门";
			break;
		case 60006:
			rs="不允许删除有子部门的部门";
			break;
		case 60007:
			rs="不允许删除根部门";
			break;
		case 60008:
			rs="部门名称已存在";
			break;
		case 60009:
			rs="部门名称含有非法字符";
			break;
		case 60010:
			rs="部门存在循环关系";
			break;
		case 60102:
			rs="UserID已存在";
			break;
		case 60103:
			rs="手机号码不合法";
			break;
		case 60111:
			rs="UserID不存在";
			break;
		case 60112:
			rs="成员姓名不合法";
			break;
		case 60114:
			rs="性别不合法";
			break;
		case 60123:
			rs="无效的部门id";
			break;
		case 60124:
			rs="无效的父部门id";
			break;
		case 60125:
			rs="非法部门名字，长度超过限制、重名等，重名包括与csv文件中同级部门重名或者与旧组织架构包含成员的同级部门重名";
			break;
		case 60126:
			rs="创建部门失败";
			break;
		case 60127:
			rs="缺少部门id";
			break;
		case 600001:
			rs="Userid与别名冲突";
			break;
		case 600002:
			rs="Userid与Groupid冲突";
			break;
		case 600003:
			rs="无效密码或者是弱密码";
			break;
		case 600004:
			rs="别名无效";
			break;
		case 600005:
			rs="别名与userid或者Groupid冲突";
			break;
		case 600006:
			rs="别名数量达到上限";
			break;
		case 600007:
			rs="Groupid无效";
			break;
		case 600008:
			rs="邮件群组不存在";
			break;
		case 600009:
			rs="群组成员为空";
			break;
		case 600010:
			rs="Userlist无效，可能是个别成员无效";
			break;
		case 600011:
			rs="Grouplist无效，可能是个别成员无效";
			break;
		case 600012:
			rs="Partylist无效，可能是个别成员无效";
			break;
		case 600013:
			rs="群发权限类型无效";
			break;
		case 600014:
			rs="群发权限成员无效";
			break;
		case 600015:
			rs="邮件群组已存在";
			break;
		case 600016:
			rs="Userlist部分成员未找到";
			break;
		case 600017:
			rs="Partylist部分成员未找到";
			break;
		case 600018:
			rs="Grouplist部分成员未找到";
			break;
		case 600019:
			rs="邮件群组名称含有非法字符";
			break;
		case 600020:
			rs="邮件群组存在循环";
			break;
		case 600021:
			rs="邮件群组嵌套超过层数";
			break;
		case 600023:
			rs="群发权限成员缺失";
			break;
		case 600024:
			rs="Groupid与userid或者别名冲突";
			break;
		case 600025:
			rs="座机号码无效";
			break;
		case 600026:
			rs="编号无效";
			break;
		case 600027:
			rs="批量检查的成员数超过限额";
			break;
		case 601001:
			rs="日志查询的时间无效";
			break;
		case 601002:
			rs="日志查询的时间超过限制";
			break;
		case 601003:
			rs="日志查询的域名无效";
			break;
		case 601004:
			rs="日志查询的域名不存在";
			break;
		case 602005:
			rs="应用没有访问此API的权限";
			break;
		default:
			break;
		}
		return rs;
	}
}
