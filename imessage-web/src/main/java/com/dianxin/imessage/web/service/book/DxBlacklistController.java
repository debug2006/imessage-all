package com.dianxin.imessage.web.service.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dianxin.imessage.biz.book.IBlacklistBiz;
import com.dianxin.imessage.common.annotation.DESParam;
import com.dianxin.imessage.common.constant.ErrCodeEnum;
import com.dianxin.imessage.common.util.ResultModel;
import com.dianxin.imessage.dao.dataobject.book.MyBlackList;

/**
 * 黑名单管理
 * 
 * @author kai.fantasy
 *
 */
@Controller
@RequestMapping("/book/")
public class DxBlacklistController {

    @Autowired
    private IBlacklistBiz blacklistBiz;

    /**
     * 添加到黑名单
     * 
     * @param uid
     * @param blackUid
     * @return
     */
    @RequestMapping(value = "blacklist", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public @ResponseBody String setBlacklist(@DESParam("uid") String uid, @DESParam("blackUid") String blackUid,
            Boolean isSet) {

        ResultModel<Boolean> result = new ResultModel<Boolean>();
        Boolean addResult = blacklistBiz.setBlacklist(Integer.valueOf(uid), blackUid, isSet);
        if (addResult) {
            result.setData(addResult);
        } else {
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(ErrCodeEnum.USER_NOT_EXIST.value);

        }
        return result.toJSON();
    }

    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param uid
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "blackList/{uid}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    public @ResponseBody String getBlackList(@DESParam("uid") String uid) {

        ResultModel<MyBlackList> result = new ResultModel<MyBlackList>();

        MyBlackList myBlackList = blacklistBiz.getBlackList(uid);

        result.setData(myBlackList);

        return result.toJSON();
    }

    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     *
     * @param myBlackList
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "updateBlacklist", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String updateBlackList(@RequestBody MyBlackList myBlackList) {
        ResultModel<String> result = new ResultModel<String>();
        String updateResult = blacklistBiz.updateBlackList(myBlackList);
        result.setData(updateResult);
        return result.toJSON();
    }

}
