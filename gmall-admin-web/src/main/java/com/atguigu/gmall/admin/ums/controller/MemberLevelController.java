package com.atguigu.gmall.admin.ums.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@Api(tags = "MemberLevelController", description = "会员管理")
@RequestMapping("/memberLevel")
@Slf4j
public class MemberLevelController {

    @ApiOperation(value = "会员价格")
    @GetMapping(value="/list")
    public Object memberLevel(
            @RequestParam(value ="") Integer defaultStatus){
       return null;
    }
}
