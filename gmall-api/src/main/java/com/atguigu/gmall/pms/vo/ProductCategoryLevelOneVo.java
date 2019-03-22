package com.atguigu.gmall.pms.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel
public class ProductCategoryLevelOneVo implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("商品一级分类id")
    private BigInteger id;
    @ApiModelProperty("商品一级分类name")
    private String name;
    @ApiModelProperty("商品一级分类的二级子分类")
    private List<ProductCategoryLevelTwoVo> children = new ArrayList<ProductCategoryLevelTwoVo>();
}
