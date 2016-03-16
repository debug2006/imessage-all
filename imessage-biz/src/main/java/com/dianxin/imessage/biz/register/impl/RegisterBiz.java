package com.dianxin.imessage.biz.register.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianxin.imessage.biz.config.IUserImageBiz;
import com.dianxin.imessage.biz.impl.BaseBiz;
import com.dianxin.imessage.biz.register.IRegisterBiz;
import com.dianxin.imessage.biz.register.IRegistrationProcessBiz;
import com.dianxin.imessage.biz.register.IUserAbilityBiz;
import com.dianxin.imessage.biz.register.IUserVersionBiz;
import com.dianxin.imessage.common.constant.ErrCodeEnum;
import com.dianxin.imessage.common.constant.UserManagerEnum;
import com.dianxin.imessage.common.util.ValidatorTools;
import com.dianxin.imessage.dao.base.BaseMapper;
import com.dianxin.imessage.dao.dataobject.DxUserAbilityModel;
import com.dianxin.imessage.dao.dataobject.DxUserImageModel;
import com.dianxin.imessage.dao.dataobject.DxUserModel;
import com.dianxin.imessage.dao.dataobject.DxUserVersionModel;
import com.dianxin.imessage.dao.dataobject.RegistrationFormModel;
import com.dianxin.imessage.dao.mapper.UserMapper;

@Service
public class RegisterBiz extends BaseBiz<DxUserModel> implements IRegisterBiz {

    private Logger logger = LoggerFactory.getLogger(RegisterBiz.class);

    @Autowired
    private UserMapper<DxUserModel> mapper;

    /**
     * @return
     */
    public BaseMapper<DxUserModel> getMapper() {
        return mapper;
    }

    @Autowired
    private IRegistrationProcessBiz registrationProcessBiz;

    @Autowired
    private IUserAbilityBiz userAbilityBiz;

    @Autowired
    private IUserVersionBiz userVersionBiz;

    @Autowired
    private IUserImageBiz userImageBiz;

    /**
     * 判断手机号是否已经注册
     * 
     * @param phone 手机号
     * @return 0：未注册 1： 已注册
     * 
     */
    @Override
    public String existPhone(String phone) {

        String exist = UserManagerEnum.PHONE_NOT_REGISTERED.value;
        Integer num;

        // 手机号码校验
        if (!ValidatorTools.isMobile(phone)) {
            return ErrCodeEnum.PHONE_FORMAT_ERR.value;
        }

        try {
            // 查询号码是否已经存在
            num = mapper.existPhone(Long.valueOf(phone));

        } catch (Exception e) {
            logger.error("{} existPhone error", phone, e);
            return ErrCodeEnum.SERVICE_ERROR.value;
        }

        // 如果数据库中已经存在手机号，则说明手机号码已经注册
        exist = null != num && num > 0 ? UserManagerEnum.PHONE_REGISTERED.value
                : UserManagerEnum.PHONE_NOT_REGISTERED.value;

        return exist;
    }

    /**
     * 用户注册
     * 
     * @param DxUserModel 用户信息
     * @return 1 注册成功 ;
     */
    @Override
    public String register(RegistrationFormModel registrationFormModel) {

        DxUserModel userModel = null;

        try {

            // TODO 校验注册信息（手机号，昵称，密码，超能力ID，。IMEI）
            registrationProcessBiz.validateRegistrationForm(registrationFormModel);

            String exits = existPhone(Long.toString(registrationFormModel.getTelphone()));

            // 手机已经注册,或返回错误码
            if (!UserManagerEnum.PHONE_NOT_REGISTERED.value.equals(exits)) {

                return UserManagerEnum.PHONE_REGISTERED.value.equals(exits) ? ErrCodeEnum.PHONE_IS_EXIST.value : exits;
            }

            /** ----入库操作--- */
            // 记录用户注册的基本信息
            userModel = registrationProcessBiz.getDxUserModel(registrationFormModel);
            // 获取二维码地址：
            userModel.setDimCode("XXXTTSSS");
            mapper.insert(userModel);
            // 记录用户超能力信息,超能力信息1.0版本取消暂时
            //DxUserAbilityModel userAbility = registrationProcessBiz.getDxUserAbility(registrationFormModel);

            //userAbilityBiz.insert(userAbility);

            // 记录用户版本信息表
            DxUserVersionModel userVersionModel = registrationProcessBiz.getDxUserVersion(registrationFormModel);

            userVersionBiz.insert(userVersionModel);

            // 设置头像
            DxUserImageModel userImageModel = registrationProcessBiz.getDxUserImageModel(registrationFormModel);

            if (null != userImageModel) {
                userImageBiz.insert(userImageModel);
            }

        } catch (Exception e) {
            logger.error("{} existPhone error", e);
            return ErrCodeEnum.REGISTRATION_FAILED.value;
        }
        return null != userModel ? userModel.getUid().toString() : ErrCodeEnum.REGISTRATION_FAILED.value;
    }

}
