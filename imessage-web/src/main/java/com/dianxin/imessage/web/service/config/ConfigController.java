package com.dianxin.imessage.web.service.config;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dianxin.imessage.biz.IUserBiz;
import com.dianxin.imessage.biz.chat.IUserStatBiz;
import com.dianxin.imessage.biz.config.IFeedbackBiz;
import com.dianxin.imessage.biz.config.IFileUpload;
import com.dianxin.imessage.biz.config.IUserBackgroundimgBiz;
import com.dianxin.imessage.biz.config.IUserImageBiz;
import com.dianxin.imessage.common.annotation.DESParam;
import com.dianxin.imessage.common.util.ResultModel;
import com.dianxin.imessage.common.util.UserManageUtil;
import com.dianxin.imessage.dao.dataobject.DxFeedbackModel;
import com.dianxin.imessage.dao.dataobject.DxUserBackgroundimgModel;
import com.dianxin.imessage.dao.dataobject.DxUserStatModel;

@Controller
@RequestMapping(value = "/config")
public class ConfigController {

    @Autowired
    private IUserBiz userBiz;

    @Autowired
    private IUserImageBiz userImageBiz;
    
    @Autowired
	private IFileUpload fileUpload;
    
    @Autowired
    private IUserBackgroundimgBiz userBackgroundimgBiz;
    
    @Autowired
    private IFeedbackBiz feedbackBiz;
    
    @Autowired
    private IUserStatBiz userStatBiz;

    @RequestMapping(value = "/changePWD/{uid}/{phone}/{password}", produces = "text/html;charset=UTF-8", method = RequestMethod.PUT)
    public @ResponseBody String changePWD(@DESParam Integer uid,@DESParam Long phone, @DESParam String password) throws Exception {

    	UserManageUtil.checkUid(uid);
    	
        ResultModel result = new ResultModel();

        userBiz.changePwd(phone, password);

        return result.toJSON();
    }

    /**
     * 
     * 功能描述: 设置用户头像<br>
     * 设置用户头像
     *
     * @param uid
     * @param photoPath
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    @RequestMapping(value = "/myPhoto/uid/photoPath", method = RequestMethod.PUT, produces = "text/html;charset=UTF-8")
    public String updateMyPhoto(@DESParam String uid, @PathVariable String photoPath) {

        ResultModel<String> result = new ResultModel<String>();
        String setResult = userImageBiz.setUserPhoto(Integer.valueOf(uid), photoPath);
        if ("1".equals(setResult)) {
            result.setData(setResult);
        } else {
            result.setResult(ResultModel.RESULT_FAIL_NUM);
            result.setErrCode(setResult);
        }
        return result.toJSON();
    }
    
    /**
     * 设置聊天背景
     * @throws Exception 
     */
    @RequestMapping(value="/background/{uid}",produces = "text/html;charset=UTF-8",method = RequestMethod.POST)
    public @ResponseBody String uploadBackground(@DESParam Integer uid , MultipartFile background) throws Exception{
    	
    	UserManageUtil.checkUid(uid);
    	
    	//将表情文件保存到oss
    	String path = fileUpload.uploadFile(background);
    	
    	DxUserBackgroundimgModel model = new DxUserBackgroundimgModel();
    	model.setBackgroundPath(path);
    	model.setCreateDate(new Timestamp(System.currentTimeMillis()));
    	model.setModifyDate(new Timestamp(System.currentTimeMillis()));
    	model.setUid(uid);
    	
    	Integer backgroundId = userBackgroundimgBiz.saveBackground(model);
    	
    	ResultModel<Map> result = new ResultModel<Map>();
    	Map<String,Object> map = new HashMap<String, Object>();
    	map.put("backgroundId", backgroundId);
    	map.put("path", path);
    	
    	return result.toJSON();
    }
    
    /**
     * 清除聊天背景
     * @throws Exception 
     */
    @RequestMapping(value="/background/{uid}/{backgroundId}",produces = "text/html;charset=UTF-8",method = RequestMethod.DELETE)
    public @ResponseBody String delBackground(@DESParam Integer uid , @DESParam Integer backgroundId) throws Exception{
    	
    	UserManageUtil.checkUid(uid);
    	
    	userBackgroundimgBiz.delBackground(uid, backgroundId);
    	
    	ResultModel result = new ResultModel();
    	
    	return result.toJSON();
    }
    
    /**
     * 提交反馈信息
     * @throws Exception 
     */
    @RequestMapping(value="/feedback/{uid}",produces = "text/html;charset=UTF-8",method = RequestMethod.POST)
    public @ResponseBody String createrFeedback(@DESParam Integer uid,DxFeedbackModel feedback) throws Exception{
    	
    	UserManageUtil.checkUid(uid);
    	
    	feedback.setCreateDate(new Timestamp(System.currentTimeMillis()));
    	feedback.setModifyDate(new Timestamp(System.currentTimeMillis()));
    	feedback.setStatus(1);
    	feedback.setUid(uid);
    	
    	feedbackBiz.insert(feedback);
    	
    	ResultModel result = new ResultModel();
    	
    	return result.toJSON();
    }
    
    /**
     * 详情
     */
    @RequestMapping(value="/stat/{uid}",produces = "text/html;charset=UTF-8",method = RequestMethod.GET)
    public @ResponseBody String getStat(@DESParam Integer uid) throws Exception{
    	
    	Integer sendCount = 0;
    	Integer revCount = 0;
    	Integer dayCount = 0;
    	
    	List<DxUserStatModel> stats = userStatBiz.getUserStatByUid(uid);
    	
    	if(stats!=null){
    		for(DxUserStatModel stat : stats){
    			sendCount = sendCount + stat.getSendCount();
    			revCount = revCount + stat.getRevCount();
    			dayCount = stat.getDayCount();
    		}
    	}
    	
    	ResultModel<Map> result = new ResultModel<Map>();
    	Map<String,Object> map = new HashMap<String, Object>();
    	map.put("sendCount", sendCount);
    	map.put("revCount", revCount);
    	map.put("dayCount", dayCount);
    	
    	return result.toJSON();
    }
}
