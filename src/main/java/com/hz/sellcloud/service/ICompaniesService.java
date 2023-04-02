package com.hz.sellcloud.service;

import com.hz.sellcloud.domain.vo.company.CompanyVo;
import com.hz.sellcloud.entity.Companies;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hz
 * @since 2022-10-27
 */
public interface ICompaniesService extends IService<Companies> {
    boolean isExist(int id);
    Companies getById(int id);
    List<CompanyVo> listCompany();

    String GetMail(int id);
}
