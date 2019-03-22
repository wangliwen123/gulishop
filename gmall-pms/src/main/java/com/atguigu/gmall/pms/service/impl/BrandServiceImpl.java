package com.atguigu.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.pms.entity.Brand;
import com.atguigu.gmall.pms.mapper.BrandMapper;
import com.atguigu.gmall.pms.service.BrandService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author Lfy
 * @since 2019-03-19
 */
@Service
@Component
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {

    //商品品牌查新列表
    @Override
    public Map<String, Object> pageBrand(String keyword, Integer pageNum, Integer pageSize) {
        BrandMapper brandMapper = getBaseMapper();
        QueryWrapper<Brand> queryWrapper =null;
        if(!StringUtils.isEmpty(keyword)){
            queryWrapper = new QueryWrapper<>();
            queryWrapper.like("name",keyword).or().eq("first_letter",keyword);
        };
        IPage iPage = brandMapper.selectPage(new Page(pageNum, pageSize), queryWrapper);
        Map<String, Object> map = new HashMap<>();
        map.put("list",iPage.getRecords());
        map.put("total",iPage.getTotal());
        map.put("pageNum",pageNum);
        map.put("pageSize",pageSize);
        map.put("totalPage",iPage.getPages());
        return map;

    }
}
