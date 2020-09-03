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
 * ��Ŀ
 * @author whoami
 *
 */
@Controller(value = "/project")
public class ProjectHandler {
	@Autowired
	private ProjectOperationRemoteService projectOperationRemoteService;
	@Autowired
	private MemberManagerRemoteService memberManagerRemoteService;
	// Ŀ¼
	@Value(value = "${oss.project.parent.folder}")
	private String ossProjectParentFolder;
	// ����ڵ�
	@Value(value = "${oss.endpoint}")
	private String endpoint;
	// ��Կ
	@Value(value = "${oss.accessKeyId}")
	private String accessKeyId;
	@Value(value = "${oss.accessKeySecret}")
	private String accessKeySecret;
	// �ռ���
	@Value(value = "${oss.bucketName}")
	private String bucketName;
	// �ϴ�����
	@Value(value = "${oss.bucket.domain}")
	private String bucketDomain;
	
	/**
	 * ������ĿͼƬ����
	 * @param session
	 * @param detailPictureList
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/upload/detail/picture")
	public ResultEntity<String> uploadDetailPicture(HttpSession session,
			@RequestParam("detailPicture") List<MultipartFile> detailPictureList) throws IOException{
		// ��¼���
		MemberSignSuccessVO signVO = (MemberSignSuccessVO) session.getAttribute(RaiseConstant.ATTR_NAME_LOGIN_MEMBER);
		
		if(Objects.isNull(signVO)) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_ACCESS_DENIED);
		}
		// �����û��ϴ����ļ�
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
		// ��ȡ����ͷͼ����Ҫ�������Ϣ
		String memberSignToken = signVO.getToken();
		ProjectVO projectVO = (ProjectVO) session.getAttribute(RaiseConstant.ATTR_NAME_INITED_PROJECT);
		String projectTempToken = projectVO.getProjectTempToken();
		return projectOperationRemoteService.saveDetailPicturePathList(memberSignToken, projectTempToken, detailPicturePathList);
	}
	
	/**
	 * �ϴ��ļ���oss��handler����
	 * @param headPicture
	 * @param session
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/upload/head/picture")
	public ResultEntity<String> uploadHeadPicture(@RequestParam("headPicture")MultipartFile headPicture,
			HttpSession session) throws IOException{
		// ��¼���
		MemberSignSuccessVO signVO = (MemberSignSuccessVO) session.getAttribute(RaiseConstant.ATTR_NAME_LOGIN_MEMBER);
		if(Objects.isNull(signVO)) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_ACCESS_DENIED);
		}
		// �ų��ϴ��ļ�Ϊ�յ����
		if(headPicture.isEmpty()) {
			return ResultEntity.failed(RaiseConstant.MESSAGE_UPLOAD_FILE_EMPTY);
		}
		// ׼�� �ļ���
		String originalFileName = headPicture.getOriginalFilename();
		String fileName = RaiseUtil.generateFileName(originalFileName);
		// Ŀ¼
		String folderName = RaiseUtil.generateFolderNameByDate(ossProjectParentFolder);
		// io��
		InputStream inputStream = headPicture.getInputStream();
		// ִ���ϴ�
		RaiseUtil.uploadSingleFile(endpoint, accessKeyId, accessKeySecret, fileName, folderName, bucketName, inputStream);
		// ƴ��headerPicturePath
		String headerPicturePath = bucketDomain+"/"+folderName+"/"+fileName;
		// ��ȡͼƬ�����Ϣ
		String memberSignToken = signVO.getToken();
		ProjectVO projectVO = (ProjectVO) session.getAttribute(RaiseConstant.ATTR_NAME_INITED_PROJECT);
		String projectTempToken = projectVO.getProjectTempToken();
		// ����ͷͼ�����Ϣ
		return projectOperationRemoteService.saveHeadPicturePath(memberSignToken, projectTempToken, headerPicturePath);
	}
	
	/**
	 * ִ��peoject��ʼ������
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/agree/protocol")
	public String agreeProtocol(HttpSession session,Model model) {
		// ��¼���
		MemberSignSuccessVO signVO = (MemberSignSuccessVO)session.getAttribute(RaiseConstant.ATTR_NAME_LOGIN_MEMBER);
		if(Objects.isNull(signVO)) {
			model.addAttribute(RaiseConstant.ATTR_NAME_MESSAGE,RaiseConstant.MESSAGE_ACCESS_DENIED);
			return "member-login";
		}
		// �ӵ�ǰsignVO�����л�ȡtoken,����Զ�̷�����ʼ��project
		ResultEntity<ProjectVO> resultEntity = projectOperationRemoteService.initCreation(signVO.getToken());
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			throw new RuntimeException(resultEntity.getMessage());
		}
		// �������������ʼ����Ŀ����Ϣ����session  
		ProjectVO projectVO = resultEntity.getData();
		session.setAttribute(RaiseConstant.ATTR_NAME_INITED_PROJECT, projectVO);
		return "redirect:/project/to/create/project/page";
	}
	
	/**
	 * ��ҳ���ѯ��Ŀ��������Ϣ
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/to/create/project/page")
	public String toCreateProjectPage(HttpSession session,Model model) {
		// ��ȡ��ǰ��¼�û�
		MemberSignSuccessVO signVO = (MemberSignSuccessVO)session.getAttribute(RaiseConstant.ATTR_NAME_LOGIN_MEMBER);
		// ���sign����
		if(Objects.isNull(signVO)) {
			model.addAttribute(RaiseConstant.ATTR_NAME_MESSAGE,RaiseConstant.MESSAGE_ACCESS_DENIED);
		}
		// ����token��ѯmemberLaunchInfo��Ϣ
		ResultEntity<MemberLaunchInfoPO> resultEntity = 
				memberManagerRemoteService.retrieveMemberLaunchInfoByMemberToken(signVO.getToken());
		// �����
		if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
			throw new RuntimeException(resultEntity.getMessage());
		}
		// ��ȡ��ѯ���
		MemberLaunchInfoPO memberLaunchInfoPO = resultEntity.getData();
		// ����ģ��
		model.addAttribute("memberLaunchInfoPO", memberLaunchInfoPO);
		return "project-create";
	}
}
