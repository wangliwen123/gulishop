package com.atguigu.gmall.cms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.cms.entity.MemberReport;
import com.atguigu.gmall.cms.mapper.MemberReportMapper;
import com.atguigu.gmall.cms.service.MemberReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 用户举报表 服务实现类
 * </p>
 *
 * @author Lfy
 * @since 2019-03-19
 */
@Service
@Component
public class MemberReportServiceImpl extends ServiceImpl<MemberReportMapper, MemberReport> implements MemberReportService {

}
