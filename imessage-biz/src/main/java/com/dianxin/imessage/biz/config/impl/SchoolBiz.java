package com.dianxin.imessage.biz.config.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dianxin.imessage.biz.config.ISchoolBiz;
import com.dianxin.imessage.biz.impl.BaseBiz;
import com.dianxin.imessage.dao.base.BaseMapper;
import com.dianxin.imessage.dao.dataobject.DxSchoolModel;
import com.dianxin.imessage.dao.dataobject.config.SchoolNameList;
import com.dianxin.imessage.dao.mapper.SchoolMapper;
import com.dianxin.imessage.dao.util.page.PageInfo;
import com.dianxin.imessage.dao.util.page.PageUtil;

/**
 * 
 * <br>
 * <b>功能：</b>用于事物处理<br>
 * <b>作者：</b>kai.fantasy<br>
 * <b>日期：</b> Thu Mar 10 00:42:04 GMT+08:00 2016 <br>
 * <b>版权所有：<b>dianxin.com(C) 2016<br>
 */
@Service
public class SchoolBiz extends BaseBiz<DxSchoolModel> implements ISchoolBiz {

	@Autowired
	private SchoolMapper<DxSchoolModel> mapper;

	/**
	 * @return
	 */
	public BaseMapper<DxSchoolModel> getMapper() {
		return mapper;
	}

	@Override
	public SchoolNameList querySchools(String inputDesc, Integer pageId, Integer pageSize) {

		inputDesc = "侯集";

		SchoolNameList SchoolNameList = null;

		DxSchoolModel schoolModel = getDxSchoolModel(inputDesc, pageId, pageSize);

		try {
			List<DxSchoolModel> shcoolModels = selectByModel(schoolModel);

			SchoolNameList = createSchoolNameList(schoolModel, shcoolModels);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return SchoolNameList;
	}

	/**
	 * 
	 * 构建 SchoolNameList
	 * 
	 * @param schoolModelPageInfo
	 *            分页信息
	 * @param shcoolModels
	 *            查询结果
	 * @return
	 * 
	 */
	private SchoolNameList createSchoolNameList(DxSchoolModel schoolModelPageInfo, List<DxSchoolModel> shcoolModels) {
		SchoolNameList schoolNameList = new SchoolNameList();

		List<String> schoolNames = null;

		if (null != shcoolModels && !shcoolModels.isEmpty()) {

			schoolNames = new ArrayList<String>();

			PageInfo pageInfo = getPageInfo(schoolModelPageInfo.getPageUtil());

			schoolNameList.setPageInfo(pageInfo);

			for (DxSchoolModel schoolModel : shcoolModels) {
				schoolNames.add(schoolModel.getSchoolname());

			}
			schoolNameList.setSchoolNames(schoolNames);
		}

		return schoolNameList;
	}

	/**
	 * 
	 * 分页信息
	 * 
	 * @param pageUtil
	 * @return
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	private PageInfo getPageInfo(PageUtil pageUtil) {

		PageInfo pageInfo = new PageInfo();

		pageInfo.setPageCount(pageUtil.getPageCount());
		pageInfo.setPageId(pageUtil.getPageId());
		pageInfo.setPageSize(pageUtil.getPageSize());
		pageInfo.setRowCount(pageUtil.getRowCount());

		return pageInfo;
	}

	/**
	 * 
	 * 构建查询学校，的实体 DxSchoolModel
	 * 
	 * @param inputDesc
	 * @param pageId
	 * @param pageSize
	 * @return
	 */
	private DxSchoolModel getDxSchoolModel(String inputDesc, Integer pageId, Integer pageSize) {

		DxSchoolModel schoolModel = new DxSchoolModel();

		schoolModel.getPageUtil().setPageId(pageId);

		schoolModel.getPageUtil().setPageSize(pageSize);

		schoolModel.getPageUtil().setPaging(true);

		schoolModel.getPageUtil().setLike(true);

		schoolModel.setSchoolname(inputDesc);

		return schoolModel;
	}
}
