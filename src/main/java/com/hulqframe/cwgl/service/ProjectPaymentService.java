package com.hulqframe.cwgl.service;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.base.BaseService;
import com.hulqframe.cwgl.mapper.ProjectPaymentMapper;
import com.hulqframe.cwgl.model.ProjectPayment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectPaymentService extends BaseService<ProjectPayment,Integer> {
@Autowired
private ProjectPaymentMapper projectPaymentMapper;
@Override
public BaseMapper<ProjectPayment, Integer> getBaseMapper() {
return projectPaymentMapper;
}
}
