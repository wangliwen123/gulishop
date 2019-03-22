package com.atguigu.gmall.pms.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
@Data
@ApiModel("商品二级分类提取属性")
public class ProductCategoryLevelTwoVo implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("商品二级分类id")
    private BigInteger id;
    @ApiModelProperty("商品二级分类name")
    private String name;
    @ApiModelProperty("商品二级分类的父级分类id")
    private BigInteger parentId;

}
