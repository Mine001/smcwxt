package com.hulqframe.system.service;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.base.BaseService;
import com.hulqframe.system.mapper.BannerMapper;
import com.hulqframe.system.model.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BannerService extends BaseService<Banner,Integer> {
@Autowired
private BannerMapper bannerMapper;
@Override
public BaseMapper<Banner, Integer> getBaseMapper() {
return bannerMapper;
}
}
