package com.whoami.raise.api;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.whoami.raise.entity.ResultEntity;
import com.whoami.raise.entity.vo.MemberConfirmInfoVO;
import com.whoami.raise.entity.vo.ProjectVO;
import com.whoami.raise.entity.vo.ReturnVO;

/**
 * 抽取项目接口方法
 * @author whoami
 *
 */
@FeignClient("project-manager")
public interface ProjectOperationRemoteService {
	
	/**
	 * 数据库保存-保存项目信息
	 * @param memberSignToken
	 * @param projectTempToken
	 * @return
	 */
	@RequestMapping(value = "/project/manager/save/whole/project")
	ResultEntity<String> saveWholeProject(
			@RequestParam("memberSignToken")String memberSignToken,
			@RequestParam("projectTempToken")String projectTempToken);
	
	/**
	 * 保存确认信息
	 * @param confirmInfoVO
	 * @return
	 */
	@RequestMapping(value = "/project/manager/save/confirm/information")
	ResultEntity<String> saveConfirmInfomation(
			@RequestBody MemberConfirmInfoVO confirmInfoVO);
	
	/**
	 * 保存回报信息
	 * @param returnVO
	 * @return
	 */
	@RequestMapping(value = "/project/manager/save/return/information")
	ResultEntity<String> saveReturnInformation(
			@RequestBody ReturnVO returnVO);
	
	/**
	 * 项目信息保存
	 * @param projectVOFront
	 * @return
	 */
	@RequestMapping(value = "/project/manager/save/project/information")
	ResultEntity<String> saveProjectInformation(
			@RequestBody ProjectVO projectVOFront);
	
	/**
	 * 保存图片-详情图片
	 * @param memberSignToken
	 * @param projectTempToken
	 * @param detailPicturePathList
	 * @return
	 */
	@RequestMapping(value = "/project/manager/save/detail/picture/path/list")
	ResultEntity<String> saveDetailPicturePathList(
			@RequestParam("memberSignToken") String memberSignToken,
			@RequestParam("projectTemoToken") String projectTempToken,
			@RequestParam("detailPicturePathList")List<String> detailPicturePathList);
	
	/**
	 * 保存图片-头图
	 * @param memberSignToken
	 * @param projectTempToken
	 * @param headerPicturePath
	 * @return
	 */
	@RequestMapping(value = "/project/manager/save/head/picture/path")
	ResultEntity<String> saveHeadPicturePath(
			@RequestParam("memberSignToken") String memberSignToken,
			@RequestParam("projectTemoToken") String projectTempToken,
			@RequestParam("headPicturePath")String headerPicturePath);
	
	/**
	 * 初始化项目
	 * @param memberSignToken
	 * @return
	 */
	@RequestMapping(value = "/project/manager/initCreation")
	ResultEntity<ProjectVO> initCreation(
			@RequestParam("memberSignToken") String memberSignToken);
}
