package cn.edu.tju.service.impl;

import cn.edu.tju.mapper.BillMapper;
import cn.edu.tju.entity.Bill;
import cn.edu.tju.entity.Payway;
import cn.edu.tju.service.BillService;
import cn.edu.tju.utils.PageModel;
import cn.edu.tju.utils.Result;
import cn.edu.tju.utils.ResultUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unchecked", "rawtypes"})
@Service
public class BillServiceImpl implements BillService {

    @Resource
    private BillMapper mapper;

    @Override
    public int add(Bill bill) {
        return mapper.add(bill);
    }

    @Override
    public int update(Bill bill) {
        return mapper.update(bill);
    }

    @Override
    public int del(int id) {
        return mapper.del(id);
    }

    @Override
    public Result<Bill> findByWhere(PageModel model) {
        try {
            List<Bill> bills = mapper.findByWhere(model);
            Result<Bill> result = ResultUtil.success(bills);
            result.setTotal(mapper.getTotalByWhere(model));
            if (result.getTotal() == 0) {
                result.setMsg("没有查到相关数据");
            } else {
                result.setMsg("数据获取成功");
            }
            return result;
        } catch (Exception e) {
            return ResultUtil.error(e);
        }
    }

    @Override
    public Result<Bill> findByWhereNoPage(Bill bill) {
        try {
            List<Bill> bills = mapper.findByWhereNoPage(bill);
            Result<Bill> result = ResultUtil.success(bills);
            result.setMsg("数据获取成功");
            return result;
        } catch (Exception e) {
            return ResultUtil.error(e);
        }
    }

    @Override
    public List<Payway> getAllPayways() {
        return mapper.getAllPayways();
    }

    @Override
    public List<Map<String, Float>> getMonthlyInfo(PageModel<Bill> model) {
        return mapper.getMonthlyInfo(model);
    }
}
