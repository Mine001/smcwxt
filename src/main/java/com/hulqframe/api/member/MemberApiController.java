package com.hulqframe.api.member;

import com.hulqframe.api.vo.MemberVo;
import com.hulqframe.base.BaseController;
import com.hulqframe.base.BaseService;

import com.hulqframe.base.asp.CurrentMember;
import com.hulqframe.base.asp.RequireLogin;
import com.hulqframe.member.model.Member;
import com.hulqframe.member.service.MemberService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageInfo;
import com.hulqframe.base.ApiResult;
import java.util.List;
import java.util.UUID;

import com.hulqframe.base.common.SearchBean;
import com.hulqframe.base.asp.SearchBeanFilter;
import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/member")
@Api
public class MemberApiController{
    @Autowired
    private MemberService memberService;

    public BaseService<Member, Integer> getService() {
        return memberService;
    }

    @RequestMapping("list")
    @RequireLogin(isPermission = true)
    @ApiOperation(value = "获取会员管理列表",notes = "获取会员管理列表",httpMethod = "POST")
    public ApiResult  list(PageInfo<Member> page,  @SearchBeanFilter List<SearchBean> searchBeans,
        String orderBy){
        page = memberService.page(page,searchBeans,orderBy);
        return ApiResult.toSuccess(page);
    }
    @ApiOperation(value = "获取会员管理详情",notes = "获取会员管理详情",httpMethod = "POST")
    @RequestMapping("detail")
    public  ApiResult  detail(Integer id){
        Member member=memberService.findById(id);
        return ApiResult.toSuccess(member);
    }

    @RequestMapping("delete")
    @ApiOperation(value = "删除会员管理",notes = "删除会员管理",httpMethod = "POST")
    public  ApiResult  delete(Integer id){
        memberService.delete(id);
        return ApiResult.toSuccess(null);
    }
    @RequestMapping("getLoignInfo")
    @RequireLogin(isPermission = false)
    @ApiOperation(value = "获取登录用户信息",notes = "获取登录用户信息",httpMethod = "POST")
    public ApiResult  getLoignInfo(@CurrentMember MemberVo member){

        return ApiResult.toSuccess(member);
    }
}
