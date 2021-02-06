package com.hulqframe.system.service;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.base.BaseService;
import com.hulqframe.system.mapper.OrgMapper;
import com.hulqframe.system.model.Org;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrgService extends BaseService<Org,Integer> {
    @Autowired
    private OrgMapper orgMapper;

    @Override
    public BaseMapper getBaseMapper() {
        return orgMapper;
    }
}
