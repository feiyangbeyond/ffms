package cn.edu.tju.service;

import cn.edu.tju.entity.Bill;
import cn.edu.tju.entity.Payway;
import cn.edu.tju.utils.PageModel;
import cn.edu.tju.utils.Result;

import java.util.List;
import java.util.Map;

public interface BillService {

    int add(Bill bill);

    int update(Bill bill);

    int del(int id);

    Result<Bill> findByWhere(PageModel model);

    Result<Bill> findByWhereNoPage(Bill bill);

    List<Payway> getAllPayways();

    List<Map<String, Float>> getMonthlyInfo(PageModel<Bill> model);

}
