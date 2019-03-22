package com.atguigu.gmall.pms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.pms.entity.*;
import com.atguigu.gmall.pms.mapper.ProductMapper;
import com.atguigu.gmall.pms.service.*;
import com.atguigu.gmall.pms.vo.PmsProductParam;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author Lfy
 * @since 2019-03-19
 */
@Service
@Component
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Override
    public Map<String, Object> pageProduct(Integer pageNum, Integer pageSize) {
        ProductMapper productMapper = getBaseMapper();
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort","id");
        IPage iPage = productMapper.selectPage(new Page(pageNum, pageSize), queryWrapper);
        //封装数据
        Map<String,Object> map = new HashMap<>();
        map.put("total",iPage.getTotal()); //总页数
        map.put("pageNum",pageNum);  //当前页
        map.put("totalPage",iPage.getPages()); //总条数
        map.put("list",iPage.getRecords()); //数据列表
        map.put("pageSize",pageSize); //每页显示数量
        return map;
    }
    // 根据商品名称或货号模糊查询
    @Override
    public List<Product> selectByKeyword(String keyword) {
        ProductMapper baseMapper = getBaseMapper();
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("keywords",keyword).or().eq("product_sn",keyword);
        List<Product> productList = baseMapper.selectList(queryWrapper);
        return productList;
    }
    //批量上下架
    @Override
    public void updatePublishStatus(List<Long> ids, Integer publishStatus) {
        ProductMapper baseMapper = getBaseMapper();
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",ids);
        Product product = new Product();
        product.setPublishStatus(publishStatus);
        baseMapper.update(product,queryWrapper);
    }
    // 批量推荐商品
    @Override
    public void updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        ProductMapper baseMapper = getBaseMapper();
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",ids);
        Product product = new Product();
        product.setRecommandStatus(recommendStatus);
        baseMapper.update(product,queryWrapper);
    }
    // 批量设为新品
    @Override
    public void updateNewStatus(List<Long> ids, Integer newStatus) {
        ProductMapper baseMapper = getBaseMapper();
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",ids);
        Product product = new Product();
        product.setNewStatus(newStatus);
        baseMapper.update(product,queryWrapper);
    }
    //批量修改删除状态
    @Override
    public void updateDeleteStatus(List<Long> ids, Integer deleteStatus) {
        ProductMapper baseMapper = getBaseMapper();
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",ids);
        Product product = new Product();
        product.setDeleteStatus(deleteStatus);
        baseMapper.update(product,queryWrapper);
    }

    @Override
    public void updateProductParam(Long id, PmsProductParam productParam) {

    }

 /*   @Autowired
    private ProductLadderService productLadderService;
    @Autowired
    private ProductFullReductionService productFullReductionService;
    @Autowired
    private ProductAttributeValueService productAttributeValueService;
    @Autowired
    private MemberPriceService memberPriceService;
    @Autowired
    private SkuStockService skuStockService;
    @Reference
    private SubjectProductRelationService subjectProductRelationService;
    @Reference
    private PrefrenceAreaProductRelationService prefrenceAreaProductRelationService;
    // 更新商品
    @Override
    public void updateProductParam(Long id, PmsProductParam productParam) {
      //更新商品阶梯价格设置
        QueryWrapper<ProductLadder> productLadderQueryWrapper = new QueryWrapper<>();
        productLadderQueryWrapper.eq("product_id",id);
        ProductLadder ladder = new ProductLadder();
        BeanUtils.copyProperties(productParam.getProductLadderList(),ladder);
        BeanUtils.copyProperties(productParam.getProductLadderList(),ladder);
        productLadderService.update(ladder,productLadderQueryWrapper);

        //商品满减价格设置
        QueryWrapper<ProductFullReduction> productFullReductionQueryWrapper = new QueryWrapper<>();
        productFullReductionQueryWrapper.eq("product_id",id);
        ProductFullReduction productFullReduction = new ProductFullReduction();
        BeanUtils.copyProperties(productParam.getProductFullReductionList(),productFullReduction);
        productFullReductionService.update(productFullReduction,productFullReductionQueryWrapper);

        //商品会员价格设置
        QueryWrapper<MemberPrice> memberPriceQueryWrapper = new QueryWrapper<>();
        memberPriceQueryWrapper.eq("product_id",id);
        MemberPrice memberPrice = new MemberPrice();
        BeanUtils.copyProperties(productParam.getMemberPriceList(),memberPrice);
        memberPriceService.update(memberPrice,memberPriceQueryWrapper);

        //商品的sku库存信息
        QueryWrapper<SkuStock> skuStockQueryWrapper = new QueryWrapper<>();
        skuStockQueryWrapper.eq("product_id",id);
        SkuStock skuStock = new SkuStock();
        BeanUtils.copyProperties(productParam.getSkuStockList(),skuStock);
        skuStockService.update(skuStock,skuStockQueryWrapper);

        //商品参数及自定义规格属性
        QueryWrapper<ProductAttributeValue> productAttributeValueQueryWrapper = new QueryWrapper<>();
        productAttributeValueQueryWrapper.eq("product_id",id);
        ProductAttributeValue productAttributeValue = new ProductAttributeValue();
        BeanUtils.copyProperties(productParam.getProductAttributeValueList(),productAttributeValue);
        productAttributeValueService.update(productAttributeValue,productAttributeValueQueryWrapper);

        //专题和商品关系
        QueryWrapper<SubjectProductRelation> subjectProductRelationQueryWrapper = new QueryWrapper<>();
        subjectProductRelationQueryWrapper.eq("product_id",id);
        SubjectProductRelation subjectProductRelation = new SubjectProductRelation();
        BeanUtils.copyProperties(productParam.getSubjectProductRelationList(),subjectProductRelation);
        subjectProductRelationService.update(subjectProductRelation,subjectProductRelationQueryWrapper);

        //优选专区和商品的关系
        QueryWrapper<PrefrenceAreaProductRelation> prefrenceAreaProductRelationQueryWrapper = new QueryWrapper<>();
        prefrenceAreaProductRelationQueryWrapper.eq("product_id",id);
        PrefrenceAreaProductRelation prefrenceAreaProductRelation = new PrefrenceAreaProductRelation();
        BeanUtils.copyProperties(productParam.getPrefrenceAreaProductRelationList(),prefrenceAreaProductRelation);
        prefrenceAreaProductRelationService.update(prefrenceAreaProductRelation,prefrenceAreaProductRelationQueryWrapper);
    }*/
}
