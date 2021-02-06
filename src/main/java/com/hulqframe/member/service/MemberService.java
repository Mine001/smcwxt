package com.hulqframe.member.service;

import com.hulqframe.base.BaseMapper;
import com.hulqframe.base.BaseService;
import com.hulqframe.member.mapper.MemberMapper;
import com.hulqframe.member.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService extends BaseService<Member,Integer> {
@Autowired
private MemberMapper memberMapper;
@Override
public BaseMapper<Member, Integer> getBaseMapper() {
return memberMapper;
}
}
