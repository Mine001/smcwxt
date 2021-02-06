package com.hulqframe.cwgl.service;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.base.BaseService;
import com.hulqframe.cwgl.mapper.ReceiveMapper;
import com.hulqframe.cwgl.model.Receive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceiveService extends BaseService<Receive,Integer> {
@Autowired
private ReceiveMapper receiveMapper;
@Override
public BaseMapper<Receive, Integer> getBaseMapper() {
return receiveMapper;
}
}
