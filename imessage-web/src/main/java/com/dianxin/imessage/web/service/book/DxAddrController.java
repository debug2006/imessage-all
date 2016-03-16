package com.dianxin.imessage.web.service.book;

import com.dianxin.imessage.biz.config.IUserAddrBiz;
import com.dianxin.imessage.common.annotation.DESParam;
import com.dianxin.imessage.common.constant.ErrCodeEnum;
import com.dianxin.imessage.common.util.ResultModel;
import com.dianxin.imessage.dao.dataobject.DxUserAddrModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created with dianxin.com
 * User: chong.qin
 * Date: 2016/3/11
 * Time: 19:21
 */
@Controller
@RequestMapping("/book/")
public class DxAddrController {

    @Autowired
    private IUserAddrBiz iUserAddrBiz;

    /**
     * 逻辑：1、如果没有记录则插入记录  2、如果有记录则修改记录
     * @param uid
     * @param province
     * @param city
     * @param area
     * @param addrdetail
     * @param id
     * @return
     */
    @RequestMapping(value="addrinfo/{uid}",produces = "text/html;charset=UTF-8",method= RequestMethod.PUT)
    public @ResponseBody String addrinfo(@DESParam String uid,String province,String city,
                                         String area,String addrdetail, Integer id,Integer rank)
    {
        ResultModel<String> result = new ResultModel<String>();
        if(StringUtils.isBlank(String.valueOf(uid))||StringUtils.isBlank(String.valueOf(id))){
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(ErrCodeEnum.PARAMETER_VALIDATION_FAILURE.value);
            return result.toJSON();
        }

        boolean result_ = false;
        DxUserAddrModel dxUserAddrModel = new DxUserAddrModel();
        dxUserAddrModel.setUid(Integer.valueOf(uid));
        dxUserAddrModel.setProvince(province);
        dxUserAddrModel.setCity(city);
        dxUserAddrModel.setArea(area);
        dxUserAddrModel.setAddrDetail(addrdetail);
        if(0==id){
            //如果id为0 则表示为插入操作
            dxUserAddrModel.setRank(rank);
            result_ = iUserAddrBiz.addRecord(dxUserAddrModel);
        }else{
            //如果id不为0  则表示编辑操作
            dxUserAddrModel.setAddrId(id);
            result_ = iUserAddrBiz.updateRecord(dxUserAddrModel);
        }
        if(result_){
            return result.toJSON();
        }
        result.setResult(ResultModel.RESULT_FAIL_NUM);
        result.setErrCode(ErrCodeEnum.SERVICE_ERROR.value);
        return result.toJSON();
    }


    /**
     * 获取三个地址数据
     * @param uid
     * @return
     */
    @RequestMapping(value="addrinfo/{uid}",produces = "text/html;charset=UTF-8",method= RequestMethod.GET)
    public @ResponseBody String addrinfo(@DESParam Integer uid) {
        ResultModel<List<DxUserAddrModel>> result = new ResultModel<List<DxUserAddrModel>>();
        if(StringUtils.isBlank(String.valueOf(uid))){
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(ErrCodeEnum.PARAMETER_VALIDATION_FAILURE.value);
            return result.toJSON();
        }

        List<DxUserAddrModel> list = iUserAddrBiz.queryList(uid);
        if(null!=list&&list.size()>0)
        {
            result.setData(list);
        }else
        {
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(ErrCodeEnum.ADDR_NOTHING.value);
        }
        return result.toJSON();
    }
}
