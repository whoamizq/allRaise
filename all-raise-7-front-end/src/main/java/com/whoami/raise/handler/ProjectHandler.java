package com.whoami.raise.handler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.whoami.raise.api.MemberManagerRemoteService;
import com.whoami.raise.api.ProjectOperationRemoteService;
import com.whoami.raise.entity.ResultEntity;
import com.whoami.raise.entity.po.MemberLaunchInfoPO;
import com.whoami.raise.entity.vo.MemberSignSuccessVO;
import com.whoami.raise.entity.vo.ProjectVO;
import com.whoami.raise.util.RaiseConstant;
import com.whoami.raise.util.RaiseUtil;

/**
 * 项目
 * @author whoami
 *
 */
@Controller(value = "/project")
public class ProjectHandler {
	@Autowired
	private ProjectOperationRemoteService projectOperationRemoteService;
	@Autowired
	private MemberManagerRemoteService memberManagerRemoteService;
	// 目录
	@Value(value = "${oss.project.parent.folder}")
	private String ossProjectParentFolder;
	// 区域节点
	@Value(value = "${oss.endpoint}")
	private String endpoint;
	// 密钥
	@Value(value = "${oss.accessKeyId}")
	private String accessKeyId;
	@Value(value = "${oss.accessKeySecret}")
	private String accessKeySecret;
	// 空间名
	@Value(value = "${oss.bucketName}")
	private String bucketName;
	// 上传域名
	@Value(value = "${oss.bucket.domain}")
	private String bucketDomain;
	
	/**
	 * 保存项目图片详情
	 * @param session
	 * @param detailPictureList
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/upload/detail/picture")
	public ResultEntity<String> uploadDetailPicture(HttpSession session,
			@RequestParam("detailPicture") List<MultipartFile> detailPictureList) throws IOException{
		// 登录检查
		MemberSignSuccessVO signVO = (MemberSignSuccessVO) session.getAttribute(RaiseConstant.ATTR_NAME_LOGIN_MEMBER);
		
		if(Objects.isNull(signVO)) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_ACCESS_DENIED);
		}
		// 遍历用户上传的文件
		if(!RaiseUtil.collectionEffectiveCheck(detailPictureList)) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_UPLOAD_FILE_EMPTY);
		}
		List<String> detailPicturePathList = new ArrayList<String>();
		
		for(MultipartFile detailPicture:detailPictureList) {
			boolean empty = detailPicture.isEmpty();
			if(empty) {
				continue;
			}
			InputStream inputStream = detailPicture.getInputStream();
			String originalFileName = detailPicture.getOriginalFilename();
			String fileName = RaiseUtil.generateFileName(originalFileName);
			String folderName = RaiseUtil.generateFolderNameByDate(ossProjectParentFolder);
			RaiseUtil.uploadSingleFile(endpoint, accessKeyId, accessKeySecret, fileName, folderName, bucketName, inputStream);
			String detailPicturePath = bucketDomain+"/"+folderName+"/"+fileName;
			detailPicturePathList.add(detailPicturePath);
		}
		// 获取保存头图所需要的相关信息
		String memberSignToken = signVO.getToken();
		ProjectVO projectVO = (ProjectVO) session.getAttribute(RaiseConstant.ATTR_NAME_INITED_PROJECT);
		String projectTempToken = projectVO.getProjectTempToken();
		return projectOperationRemoteService.saveDetailPicturePathList(memberSignToken, projectTempToken, detailPicturePathList);
	}
	
	/**
	 * 上传文件到oss，handler方法
	 * @param headPicture
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/upload/head/picture")
	public ResultEntity<String> uploadHeadPicture(@RequestParam("headPicture")MultipartFile headPicture,
			HttpSession session) throws IOException{
		// 登录检查
		MemberSignSuccessVO signVO = (MemberSignSuccessVO) session.getAttribute(RaiseConstant.ATTR_NAME_LOGIN_MEMBER);
		if(Objects.isNull(signVO)) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_ACCESS_DENIED);
		}
		// 排除上传文件为空的情况
		if(headPicture.isEmpty()) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_UPLOAD_FILE_EMPTY);
		}
		// 准备 文件名
		String originalFileName = headPicture.getOriginalFilename();
		String fileName = RaiseUtil.generateFileName(originalFileName);
		// 目录
		String folderName = RaiseUtil.generateFolderNameByDate(ossProjectParentFolder);
		// io流
		InputStream inputStream = headPicture.getInputStream();
		// 执行上传
		RaiseUtil.uploadSingleFile(endpoint, accessKeyId, accessKeySecret, fileName, folderName, bucketName, inputStream);
		// 拼接headerPicturePath
		String headerPicturePath = bucketDomain+"/"+folderName+"/"+fileName;
		// 获取图片相关信息
		String memberSignToken = signVO.getToken();
		ProjectVO projectVO = (ProjectVO) session.getAttribute(RaiseConstant.ATTR_NAME_INITED_PROJECT);
		String projectTempToken = projectVO.getProjectTempToken();
		// 保存头图相关信息
		return projectOperationRemoteService.saveHeadPicturePath(memberSignToken, projectTempToken, headerPicturePath);
	}
	
	/**
	 * 执行peoject初始化操作
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/agree/protocol")
	public String agreeProtocol(HttpSession session,Model model) {
		// 登录检查
		MemberSignSuccessVO signVO = (MemberSignSuccessVO)session.getAttribute(RaiseConstant.ATTR_NAME_LOGIN_MEMBER);
		if(Objects.isNull(signVO)) {
			model.addAttribute(RaiseConstant.ATTR_NAME_MESSAGE,RaiseConstant.MESSAGE_ACCESS_DENIED);
			return "member-login";
		}
		// 从当前signVO对象中获取token,调用远程方法初始化project
		ResultEntity<ProjectVO> resultEntity = projectOperationRemoteService.initCreation(signVO.getToken());
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			throw new RuntimeException(resultEntity.getMessage());
		}
		// 补充操作：将初始化项目的信息存入session  
		ProjectVO projectVO = resultEntity.getData();
		session.setAttribute(RaiseConstant.ATTR_NAME_INITED_PROJECT, projectVO);
		return "redirect:/project/to/create/project/page";
	}
	
	/**
	 * 在页面查询项目发起人信息
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/to/create/project/page")
	public String toCreateProjectPage(HttpSession session,Model model) {
		// 获取当前登录用户
		MemberSignSuccessVO signVO = (MemberSignSuccessVO)session.getAttribute(RaiseConstant.ATTR_NAME_LOGIN_MEMBER);
		// 检查sign参数
		if(Objects.isNull(signVO)) {
			model.addAttribute(RaiseConstant.ATTR_NAME_MESSAGE,RaiseConstant.MESSAGE_ACCESS_DENIED);
		}
		// 根据token查询memberLaunchInfo信息
		ResultEntity<MemberLaunchInfoPO> resultEntity = 
				memberManagerRemoteService.retrieveMemberLaunchInfoByMemberToken(signVO.getToken());
		// 检查结果
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			throw new RuntimeException(resultEntity.getMessage());
		}
		// 获取查询结果
		MemberLaunchInfoPO memberLaunchInfoPO = resultEntity.getData();
		// 存入模型
		model.addAttribute("memberLaunchInfoPO", memberLaunchInfoPO);
		return "project-create";
	}
}
