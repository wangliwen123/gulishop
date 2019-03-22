package com.atguigu.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.contains.RedisCacheContains;
import com.atguigu.gmall.pms.entity.ProductCategory;
import com.atguigu.gmall.pms.mapper.ProductCategoryMapper;
import com.atguigu.gmall.pms.service.ProductCategoryService;
import com.atguigu.gmall.to.PmsProductCategoryWithChildrenItem;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 产品分类 服务实现类
 * </p>
 *
 * @author Lfy
 * @since 2019-03-19
 */
@Service
@Component
public class ProductCategoryServiceImpl extends ServiceImpl<ProductCategoryMapper, ProductCategory> implements ProductCategoryService {

    @Autowired
    StringRedisTemplate redisTemplate;
    @Override
    public Map<String, Object> pageProductCategory(Integer pageNum, Integer pageSize) {
        ProductCategoryMapper productCategoryMapper = getBaseMapper();
        IPage<ProductCategory> iPage = productCategoryMapper.selectPage(new Page(pageNum, pageSize), null);
        Map<String,Object> map = new HashMap<>();
        map.put("list",iPage.getRecords());
        map.put("total",iPage.getTotal());
        map.put("pageNum",pageNum);
        map.put("pageSize",pageSize);
        map.put("totalPage",iPage.getPages());
        return map;
    }
    //查询所有一级分类及子分类
    @Override
    public List<PmsProductCategoryWithChildrenItem> listWithChildren() {
        //加缓存
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        //先判断缓存中是否存在此数据
        String cache = ops.get(RedisCacheContains.PRODUCT_CATEGORY_CACHE_KEY);
        if(!StringUtils.isEmpty(cache)){
          //redis缓存中有此数据，直接解析获取
            List<PmsProductCategoryWithChildrenItem> items = JSON.parseArray(cache, PmsProductCategoryWithChildrenItem.class);
            return items;
        }
  //缓存中没有数据，去数据库获取，然后保存到缓存中
        ProductCategoryMapper baseMapper = getBaseMapper();
        List<PmsProductCategoryWithChildrenItem> items = baseMapper.listWithChildren(0);

        //将存储数据转化成JSON字符串，进行存储
        String jsonString = JSON.toJSONString(items);
        //存储数据给一个过期时间
        ops.set(RedisCacheContains.PRODUCT_CATEGORY_CACHE_KEY,jsonString,3 ,TimeUnit.DAYS);
        //查询某个菜单的所有子菜单
        return items;
    }

}
