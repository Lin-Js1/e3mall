package cn.e3mall.service.impl;

import cn.e3mall.common.pojo.EasyUIDataGridResult;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 商品管理Service
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Override
    public TbItem getItemById(long itemId) {
        //根据主键查询
        //return tbItemMapper.selectByPrimaryKey(itemId);

        //设置查询条件
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(itemId);
        List<TbItem> tbItems = tbItemMapper.selectByExample(example);
        if (tbItems != null && tbItems.size()>0){
            return tbItems.get(0);
        }
        return null;
    }

    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page,rows);
        //执行查询
        TbItemExample example = new TbItemExample();
        List<TbItem> list = tbItemMapper.selectByExample(example);
        //创建一个返回对象
        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setRows(list);
        //取分页结果
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        //取总记录数
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    @Override
    public E3Result addItem(TbItem item, String desc) {
        //生成商品id
        long itemId = IDUtils.genItemId();
        //补全item属性
        item.setId(itemId);
        //1正常 2下架 3删除
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        //向商品表插入数据
        tbItemMapper.insert(item);
        //创建一个商品描述表对应的pojo对象
        TbItemDesc tbItemDesc = new TbItemDesc();
        //补全属性
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setCreated(new Date());
        tbItemDesc.setUpdated(new Date());
        //向商品描述表插入数据
        tbItemDescMapper.insert(tbItemDesc);
        //返回成功
        return E3Result.ok();
    }

    @Override
    public E3Result deleteItem(String ids) {
        String[] split = ids.split(",");
        for (String id :
                split) {
            tbItemMapper.deleteByPrimaryKey(Long.parseLong(id));
            tbItemDescMapper.deleteByPrimaryKey(Long.parseLong(id));
        }
        return E3Result.ok();
    }

    /**
     * 下架
     * @param ids
     * @return
     */
    @Override
    public E3Result updateStatus(String ids) {
        if (StringUtils.isNoneBlank(ids)){
            String[] split = ids.split(",");
            for (String id :
                    split) {
                TbItem tbItem = tbItemMapper.selectByPrimaryKey(Long.parseLong(id));
                tbItem.setStatus((byte) 2);
                tbItemMapper.updateByPrimaryKey(tbItem);
            }
            return E3Result.ok();
        }
        return null;
    }
    /**
     * 上架
     * @param ids
     * @return
     */
    @Override
    public E3Result updateStatus2(String ids) {
        if (StringUtils.isNoneBlank(ids)){
            String[] split = ids.split(",");
            for (String id :
                    split) {
                TbItem tbItem = tbItemMapper.selectByPrimaryKey(Long.parseLong(id));
                tbItem.setStatus((byte) 1);
                tbItemMapper.updateByPrimaryKey(tbItem);
            }
            return E3Result.ok();
        }
        return null;
    }
}
