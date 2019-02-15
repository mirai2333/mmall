package com.mmall.common;

public class Const {
    public interface Role {
        int ROLE_ADMIN = 0;
        int ROLE_CUSTOMER = 1;
    }
    public interface RedisConst {
        //后面加冒号是为了redis客户端上方便查看
        //用户重置密码时设置
        String USER_TOKEN = "user_token:";
        //按秒算
        int USER_TOKEN_EXPIRE_TIME = 3600;
    }
    public interface ProductStatus{
        int ON_SELL = 1;
        int OFF_SELL = 2;
        int DELETE = 3;
    }

    /**前端产品列表搜索*/
    public interface PortalListStatus{
        int ALL = 0;
        int NONE = 1;
        int CATEGORY = 2;
        int KEYWORDS = 3;
    }

}
