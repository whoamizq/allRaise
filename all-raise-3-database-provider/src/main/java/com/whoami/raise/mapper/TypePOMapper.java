package com.whoami.raise.mapper;

import com.whoami.raise.entity.po.TypePO;
import com.whoami.raise.entity.po.TypePOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TypePOMapper {
    int countByExample(TypePOExample example);

    int deleteByExample(TypePOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TypePO record);

    int insertSelective(TypePO record);

    List<TypePO> selectByExample(TypePOExample example);

    TypePO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TypePO record, @Param("example") TypePOExample example);

    int updateByExample(@Param("record") TypePO record, @Param("example") TypePOExample example);

    int updateByPrimaryKeySelective(TypePO record);

    int updateByPrimaryKey(TypePO record);

    /**
     * 批量保存类型id数据
     * @param projectId
     * @param typeIdList
     */
	void insertRelationshipBatch(@Param("projectId")Integer projectId,@Param("typeIdList") List<Integer> typeIdList);
}