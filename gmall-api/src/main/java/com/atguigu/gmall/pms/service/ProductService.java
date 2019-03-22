package com.atguigu.gmall.pms.service;

import com.atguigu.gmall.pms.entity.Product;
import com.atguigu.gmall.pms.vo.PmsProductParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 *
 * @author Lfy
 * @since 2019-03-19
 */
public interface ProductService extends IService<Product> {
   //分页查询出来的商品信息
    Map<String,Object> pageProduct(Integer pageNum, Integer pageSize);
    // 根据商品名称或货号模糊查询
    List<Product> selectByKeyword(String keyword);

    void updatePublishStatus(List<Long> ids, Integer publishStatus);

    void updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    void updateNewStatus(List<Long> ids, Integer newStatus);
    //根据商品id获取商品编辑信息
    void updateDeleteStatus(List<Long> ids, Integer deleteStatus);
 // 更新商品
    void updateProductParam(Long id, PmsProductParam productParam);
}
