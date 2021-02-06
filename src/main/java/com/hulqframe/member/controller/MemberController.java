package com.hulqframe.member.controller;

import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;
import com.hulqframe.member.model.Member;
import com.hulqframe.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/system/member")
public class MemberController extends BaseController<Member,Integer> {
    @Autowired
    private MemberService memberService;
    @Override
    public BaseService<Member, Integer> getService() {
    return memberService;
    }



}
