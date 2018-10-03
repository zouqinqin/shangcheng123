package cn.e3mall.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.EasyUITreeNode;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import cn.e3mall.pojo.TbContentCategoryExample.Criteria;

/**
 * 内容分类管理Service
 * <p>Title: ContentCategoryServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	@Override
	public List<EasyUITreeNode> getContentCategoryList(long parentId) {
		// 1、取查询参数id，parentId
				// 2、根据parentId查询tb_content_category，查询子节点列表。
				TbContentCategoryExample example = new TbContentCategoryExample();
				//设置查询条件
				Criteria criteria = example.createCriteria();
				criteria.andParentIdEqualTo(parentId);
				// 3、得到List<TbContentCategory>
				List<TbContentCategory> selectByExample = contentCategoryMapper.selectByExample(example);
				List<EasyUITreeNode> resultList  =new ArrayList<>(); 
				 for (TbContentCategory category : selectByExample) {
					 EasyUITreeNode node  = new EasyUITreeNode();
					 node.setId(category.getId());
					 node.setText(category.getName());
					 node.setState(category.getIsParent()?"closed":"open");
					 //添加列表
					 resultList.add(node); 
				 }
				return resultList;
	}

	

}
