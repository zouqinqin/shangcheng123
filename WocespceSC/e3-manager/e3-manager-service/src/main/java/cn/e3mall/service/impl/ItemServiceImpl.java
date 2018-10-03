package cn.e3mall.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.pojo.TbItemExample.Criteria;
import cn.e3mall.service.ItemService;
/**
 * 商品服务类
 * @author think
 *
 */
@Service
public class ItemServiceImpl implements  ItemService {
	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper tbItemDescMapper;
	

	@Override
	public TbItem getItemById(long id) {
		//通过主键查询一条记录
//		TbItem item = tbItemMapper.selectByPrimaryKey(id);
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		List<TbItem> list = tbItemMapper.selectByExample(example);
		if(list != null & list.size()>0) {
			return list.get(0);
		}
		return null;
	}


	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//执行查询
		TbItemExample example =new TbItemExample(); 
		List<TbItem> list = tbItemMapper.selectByExample(example);
		//取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		long total = pageInfo.getTotal();
		result.setTotal(total);
		//创建返回结果对象
		return result;
	}


	@Override
	public E3Result addItem(TbItem tbItem, String desc) {
		//1.生成商品id
		long genItemId = IDUtils.genItemId();
		//2.补全TbItem属性
		tbItem.setId(genItemId);
		//'商品状态，1-正常，2-下架，3-删除',
		tbItem.setStatus((byte) 1);
		tbItem.setCreated(new Date());
		tbItem.setUpdated(new Date());
		//向TbItem表中插入数据
		tbItemMapper.insert(tbItem);
		//3.补全TbItemDesc属性
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemId(genItemId);
		tbItemDesc.setCreated(new Date());
		tbItemDesc.setUpdated(new Date());
		tbItemDesc.setItemDesc(desc);
		//向tbItemDesc表中插入数据
		tbItemDescMapper.insert(tbItemDesc);
		//返回结果
		E3Result ok = E3Result.ok();
		return ok;
	}

}
