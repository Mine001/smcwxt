package com.hulqframe.api;

import com.alibaba.fastjson.JSONObject;
import com.hulqframe.api.vo.MemberVo;
import com.hulqframe.base.ApiResult;
import com.hulqframe.base.common.Constants;
import com.hulqframe.config.shiro.jwt.JwtUtil;
import com.hulqframe.member.model.Member;
import com.hulqframe.member.model.MemberPermission;
import com.hulqframe.member.service.MemberPermissionService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.hulqframe.member.service.MemberService;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class IndexApiController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberPermissionService permissionService;

    @RequestMapping("member/register")
    @ApiOperation(value = "会员注册",notes = "会员注册",httpMethod = "POST")
    public ApiResult register(Member member){
        Member member1=new Member();
        member1.setAccount(member.getAccount());
        member1.setPassword(member.getPassword());
        member1.setBirthday(member.getBirthday());
        member1.setEmail(member1.getEmail());
        //将uuid设置为密码盐值
        String salt = UUID.randomUUID().toString().replaceAll("-","");
        SimpleHash simpleHash = new SimpleHash("MD5", member1.getPassword(), salt, 1024);
        member1.setPassword(simpleHash.toHex());
        member1.setSalt(salt);
        memberService.save(member1);

        return ApiResult.toSuccess(null);
    }
    @RequestMapping("member/login")
    @ApiOperation(value = "会员登录",notes = "会员登录",httpMethod = "POST")
    public ApiResult login(@RequestParam String account,@RequestParam String password) throws Exception {
        Member user=memberService.findByUnique("account",account);
        if (user == null) {
            return ApiResult.toFaild(null,"该用户不存在");
        }

        //我的密码是使用uuid作为盐值加密的，所以这里登陆时候还需要做一次对比
        SimpleHash simpleHash = new SimpleHash("MD5", password,  user.getSalt(), 1024);
        if(!simpleHash.toHex().equals(user.getPassword())){
            return ApiResult.toFaild(null,"密码不正确");
        }
        List<MemberPermission> permissions=permissionService.listByUserId(user.getId());
        String apis="";
        if(permissions!=null){
            for(MemberPermission permission:permissions){
                if(!permission.getType().equals(Constants.MEMBER_PERMISSION_TYPE.CATALOG.getValue())){
                    apis=apis+permission.getUrl()+";";
                }
            }
        }

        // 生成token
        String token = JwtUtil.createJWT(user.getUserName(), account,apis);
        JSONObject obj = new JSONObject();
        obj.put("token", token);
        MemberVo memberVo=new MemberVo();
        PropertyUtils.copyProperties(memberVo,user);
        obj.put("userInfo", memberVo);
        ApiResult result=ApiResult.toSuccess(obj);
        result.setMessage("登录成功");
        return result;
    }
}
