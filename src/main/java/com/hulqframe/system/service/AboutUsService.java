package com.hulqframe.system.service;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.base.BaseService;
import com.hulqframe.system.mapper.AboutUsMapper;
import com.hulqframe.system.model.AboutUs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AboutUsService extends BaseService<AboutUs,Integer> {
@Autowired
private AboutUsMapper aboutUsMapper;
@Override
public BaseMapper<AboutUs, Integer> getBaseMapper() {
return aboutUsMapper;
}
}
