package cn.e3mall.sso.service.impl;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.sso.service.RegisterService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * 用户注册处理
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    public E3Result checkDate(String param, int type) {
        //根据不同的type生成不同的查询条件
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        //1用户名 2手机 3邮箱
        if (type == 1) {
            criteria.andUsernameEqualTo(param);
        }else if (type ==2){
            criteria.andPhoneEqualTo(param);
        }else if(type ==3){
            criteria.andEmailEqualTo(param);
        }else {
            return E3Result.build(400,"数据类型错误");
        }
        //执行查询
        List<TbUser> list = tbUserMapper.selectByExample(example);
        //判断结果中是否包含数据
        if (list !=null && list.size()>0){
            //如果有数据返回false
            return E3Result.ok(false);
        }
        //如果没有数据返回true
        return E3Result.ok(true);
    }

    @Override
    public E3Result register(TbUser user) {
        //数据有效性校验
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword()) || StringUtils.isBlank(user.getPhone())){
            return E3Result.build(400,"用户数据不完整，注册失败");
        }
        E3Result e3Result = checkDate(user.getUsername(), 1);
        if (!(boolean)e3Result.getData()){
            return E3Result.build(400,"此用户名已经被占用");
        }
        e3Result = checkDate(user.getPassword(), 2);
        if (!(boolean)e3Result.getData()){
            return E3Result.build(400,"手机号被占用");
        }
        //补全pojo的属性
        user.setCreated(new Date());
        user.setUpdated(new Date());
        //对密码进行md5加密
        String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Pass);
        //把用户数据插入到数据库
        tbUserMapper.insert(user);
        //返回成功
        return E3Result.ok();
    }
}
