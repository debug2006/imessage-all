package com.dianxin.imessage.biz.config.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.dianxin.imessage.biz.config.IUserImageBiz;
import com.dianxin.imessage.biz.impl.BaseBiz;
import com.dianxin.imessage.common.constant.ErrCodeEnum;
import com.dianxin.imessage.dao.base.BaseMapper;
import com.dianxin.imessage.dao.dataobject.DxUserImageModel;
import com.dianxin.imessage.dao.mapper.UserImageMapper;

/**
 * 
 * <br>
 * <b>功能：</b>用于事物处理<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Sat Jan 30 15:22:21 GMT+08:00 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
@Service
public class UserImageBiz extends BaseBiz<DxUserImageModel> implements IUserImageBiz {

    private Logger logger = LoggerFactory.getLogger(UserImageBiz.class);

    @Autowired
    private UserImageMapper<DxUserImageModel> mapper;

    /**
     * @return
     */
    public BaseMapper<DxUserImageModel> getMapper() {
        return mapper;
    }

    @Override
    public DxUserImageModel getUserImage(Integer uid) throws Exception {

        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("uid", uid);
        List<DxUserImageModel> list = mapper.selectByMap(map);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public String setUserPhoto(Integer uid, String photoPath) {

        String setResult = "1";

        if (StringUtils.isEmpty(photoPath)) {
            return ErrCodeEnum.PARAMETER_VALIDATION_FAILURE.value;
        }

        DxUserImageModel userImageModel = getDxUserImageModel(uid, photoPath);

        try {

            List<DxUserImageModel> userImageModels = mapper.selectByEntiry(userImageModel);

            if (null != userImageModels && userImageModels.size() > 0) {

                mapper.updateByPrimaryKey(userImageModel);
            } else {
                mapper.insert(userImageModel);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("setUserPhoto set failure ", e);
            return ErrCodeEnum.DATA_VIOLATIO_NEXCEPTION.value;
        }

        return setResult;
    }

    private DxUserImageModel getDxUserImageModel(Integer uid, String photoPath) {

        DxUserImageModel userImageModel = null;

        if (null != uid && null != photoPath) {
            userImageModel = new DxUserImageModel();
            userImageModel.setUid(uid);
            userImageModel.setFilePath(photoPath);
        }

        return userImageModel;
    }
}
