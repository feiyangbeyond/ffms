package cn.edu.tju.mapper;

import cn.edu.tju.entity.Bill;
import cn.edu.tju.entity.Payway;
import cn.edu.tju.utils.PageModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BillMapper {

    int add(Bill bill);

    int update(Bill bill);

    int del(int id);

    List<Bill> findByWhere(PageModel<Bill> model);

    List<Bill> findByWhereNoPage(Bill model);

    int getTotalByWhere(PageModel<Bill> model);

    List<Map<String, Float>> getMonthlyInfo(PageModel<Bill> model);

    List<Payway> getAllPayways();
}
