package com.ning.home_admin.service.impl;


import com.ning.home_admin.bean.CartItem;
import com.ning.home_admin.bean.ov.ResultInfo;
import com.ning.home_admin.bean.ov.ShoppingCart;
import com.ning.home_admin.commons.utils.UUIDUtils;
import com.ning.home_admin.service.ShoppingCartService;
import com.ning.home_admin.sytem.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UUIDUtils uuidUtils;
    /**
     * 获得用户key
     *
     * 1.用户未登录情况下第一次进入购物车  -> 生成key 保存至cookie中
     * 2.用户未登录情况下第n进入购物车    -> 从cookie中取出key
     * 3.用户登录情况下                  -> 根据用户code生成key
     * 4.用户登录情况下并且cookie中存在key-> 从cookie取的的key从缓存取得购物车 合并至
     *  用户code生成key的购物车中去 ，这样后面才能根据用户code 取得正确的购物车
     *
     * @param req
     * @param resp
     * @param user
     * @return
     */
    @Override
    public String getKey(HttpServletRequest req, HttpServletResponse resp, User user) {
        String key = null;  //最终返回的key
        String tempKey = ""; //用来存储cookie中的临时key,

        Cookie cartCookie = WebUtils.getCookie(req, "shoopingCart");
        if(cartCookie!=null){
            //获取Cookie中的key
            key = cartCookie.getValue();
            tempKey = cartCookie.getValue();
        }
        if(StringUtils.isBlank(key)){
            key = ShoppingCart.unLoginKeyPrefix + uuidUtils.getUUID();
            if (user!=null)
                key = ShoppingCart.loginKeyPrefix + user.getId();
            Cookie cookie = new Cookie("shoopingCart",key);
            cookie.setMaxAge(-1);
            cookie.setPath("/");
            resp.addCookie(cookie);
        }else if (StringUtils.isNotBlank(key) && user!=null){//⑵
            key = ShoppingCart.loginKeyPrefix + user.getId();
            if (tempKey.startsWith(ShoppingCart.unLoginKeyPrefix)){//⑴
                //1.满足cookie中取得的key 为未登录时的key
                //2.满足当前用户已经登录
                //3.合并未登录时用户所添加的购物车商品⑷
                mergeCart(tempKey,user);//⑶
            }
        }
        return key;
    }

    /**
     * 合并购物车 返回最终购物车
     * @param tempKey
     */
    @Override
    public ShoppingCart mergeCart(String tempKey, User user) {
        ShoppingCart loginCart=null;
        String loginKey=null;
        // 从redis取出用户缓存购物车数据
        HashOperations<String, String, ShoppingCart> vos = redisTemplate.opsForHash();
        ShoppingCart unLoginCart = vos.get("CACHE_SHOPPINGCART", tempKey);
        if (unLoginCart == null){
            unLoginCart = new ShoppingCart(tempKey);
        }
        if (user != null && tempKey.startsWith(ShoppingCart.unLoginKeyPrefix)) {//⑵
            //如果用户登录 并且 当前是未登录的key
            loginKey = ShoppingCart.loginKeyPrefix + user.getId();
            loginCart = mergeCart(loginKey, user);
            if (null != unLoginCart.getCartItems()) {//⑴

                if (null != loginCart.getCartItems()) {
                    //满足未登录时的购物车不为空 并且 当前用户已经登录
                    //进行购物车合并
                    for (CartItem cv : unLoginCart.getCartItems()) {
                        long count = loginCart.getCartItems().stream().filter(it->it.getCartClassify().equals(cv.getCartClassify())).count();
                        if(count == 0 ){//没有重复的商品 则直接将商品加入购物车
                            loginCart.getCartItems().add(cv);
                        }else if(count == 1){//出现重复商品 修改数量
                            CartItem c = loginCart.getCartItems().stream().filter(it->it.getCartClassify().equals(cv.getCartClassify())).findFirst().orElse(null);
                            c.setCartCount(c.getCartCount()+1);
                        }
                    }
                } else {
                    //如果当前登录用户的购物车为空则 将未登录时的购物车合并
                    loginCart.setCartItems(unLoginCart.getCartItems());
                }
                unLoginCart = loginCart;
                //【删除临时key】
                vos.delete("CACHE_SHOPPINGCART",tempKey);
                //【将合并后的购物车数据 放入loginKey】//TMP_4369f86d-c026-4b1b-8fec-f3c69f6ffac5
                vos.put("CACHE_SHOPPINGCART",loginKey, unLoginCart);
            }
        }

        return unLoginCart;
    }
    /**
     * 添加购物车
     * @param req
     * @param resp
     * @param user 登陆用户信息
     * @param item  添加的购物车商品信息 包含商品code 商品加购数量
     * @return
     */
    @Override
    public ResultInfo addCart(HttpServletRequest req, HttpServletResponse resp, User user, CartItem item) {
        ResultInfo result = new ResultInfo();
        String key = getKey(req, resp,user);//得到最终key
        ShoppingCart cacheCart = mergeCart(key,user);//根据key取得最终购物车对象
        if(StringUtils.isNotBlank(item.getCartClassify()) && item.getCartCount()>0){
            //TODO 进行一系列 商品上架 商品code是否正确 最大购买数量....
            if(false){
                result.setFlag(false);
                return result;
            }
            long count = 0;
            if(cacheCart.getCartItems() !=null ) {
                count = cacheCart.getCartItems().stream().filter(it->it.getCartClassify().equals(item.getCartClassify())).count();
            }
            if (count==0){
                //之前购物车无该商品记录 则直接添加
                cacheCart.getCartItems().add(item);
            }else {
                //否则将同一商品数量相加
                CartItem c = cacheCart.getCartItems().stream().filter(it->it.getCartClassify().equals(item.getCartClassify())).findFirst().orElse(null);
                c.setCartCount(c.getCartCount()+item.getCartCount());
            }
        }
        //【将合并后的购物车数据 放入loginKey】
        HashOperations<String,String,ShoppingCart> vos = redisTemplate.opsForHash();
        vos.put("CACHE_SHOPPINGCART",key, cacheCart);
        result.setFlag(true);
        result.setMessage("购物车添加成功");
        return result;
    }
    /**
     * 移除购物车
     * @param req
     * @param resp
     * @param user
     * @return
     */
    @Override
    public ResultInfo removeCart(HttpServletRequest req, HttpServletResponse resp, User user, List<CartItem> Classifys) {
        ResultInfo result = new ResultInfo();
        for (int i = 0; i < Classifys.size(); i++) {
            String a=Classifys.get(i).getCartClassify();
            String key = getKey(req, resp, user);//得到最终key
            ShoppingCart cacheCart = mergeCart(key, user);//根据key取得最终购物车对象
            if (cacheCart != null && cacheCart.getCartItems() != null && cacheCart.getCartItems().size() > 0) {//⑴
                //(1)
                long count = cacheCart.getCartItems().stream().filter(it -> it.getCartid().equals(a)).count();
                if (count == 1) {//⑵
                    CartItem ci = cacheCart.getCartItems().stream().filter(it -> it.getCartid().equals(a)).findFirst().orElse(null);
                        cacheCart.getCartItems().remove(ci);
                    //1.满足缓存购物车中必须有商品才能减购物车
                    //2.满足缓存购物车中有该商品才能减购物车
                    //3.判断此次要减数量是否大于缓存购物车中数量 进行移除还是数量相减操作
                }
                HashOperations<String, String, ShoppingCart> vos = redisTemplate.opsForHash();
                vos.put("CACHE_SHOPPINGCART", key, cacheCart);
                result.setFlag(true);
                result.setMessage("购物车删除成功");
            } else {
                result.setFlag(false);
                result.setMessage("购物车删除失败");
            }
        }
        return result;
    }

    @Override
    public String removeByOne(HttpServletRequest req, HttpServletResponse resp, User user, CartItem item) {
            String key = getKey(req, resp, user);//得到最终key
            ShoppingCart cacheCart = mergeCart(key, user);//根据key取得最终购物车对象
            if (cacheCart != null && cacheCart.getCartItems() != null && cacheCart.getCartItems().size() > 0) {//⑴
                //(1)
                long count = cacheCart.getCartItems().stream().filter(it -> it.getCartid().equals(item.getCartid())).count();
                if (count == 1) {//⑵
                    CartItem ci = cacheCart.getCartItems().stream().filter(it -> it.getCartid().equals(item.getCartid())).findFirst().orElse(null);
                        cacheCart.getCartItems().remove(ci);
                    //1.满足缓存购物车中必须有商品才能减购物车
                    //2.满足缓存购物车中有该商品才能减购物车
                    //3.判断此次要减数量是否大于缓存购物车中数量 进行移除还是数量相减操作
                }
                HashOperations<String, String, ShoppingCart> vos = redisTemplate.opsForHash();
                vos.put("CACHE_SHOPPINGCART", key, cacheCart);

            }
            return "redirect:/cart";
    }

    /**
     *  【场景:我加购了一双40码的鞋子到购物车 现在我想换成41码的鞋子】
     *  【例如:原商品code ABCDEFG40   ->  ABCDEFG41】
     *
     * @param req
     * @param resp
     * @param user
     */
    @Override
    public ResultInfo updateCart(HttpServletRequest req, HttpServletResponse resp,User user,String classify,String type){

        ResultInfo result = new ResultInfo();
        //TODO 校验商品信息是否合法 是否上架 库存 最大购买数量....
        if(false){
            return null;
        }

        String key = getKey(req, resp,user);
        ShoppingCart cacheCart =  mergeCart(key , user);//TODO 待探讨
        //否则将同一商品数量相加
        CartItem c = cacheCart.getCartItems().stream().filter(it->it.getCartClassify().equals(classify)).findFirst().orElse(null);
        if (type.equals("add")){
            c.setCartCount(c.getCartCount()+1);
        }else if(type.equals("sub")){
            c.setCartCount(c.getCartCount()-1);
        }
        c.setCartSubtotal(c.getCartPrice()*c.getCartCount());
        //【将合并后的购物车数据 放入loginKey】
        HashOperations<String,String,ShoppingCart> vos = redisTemplate.opsForHash();
        vos.put("CACHE_SHOPPINGCART",key, cacheCart);
        result.setFlag(true);
        result.setMessage("购物车添加成功");
        return result;
    }

}
