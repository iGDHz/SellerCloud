package com.hz.sellcloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hz.sellcloud.domain.vo.company.CompanyListVo;
import com.hz.sellcloud.domain.vo.company.CompanyVo;
import com.hz.sellcloud.entity.Companies;
import com.hz.sellcloud.mapper.CompaniesMapper;
import com.hz.sellcloud.service.ICompaniesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hz
 * @since 2022-10-27
 */
@Service
public class CompaniesServiceImpl extends ServiceImpl<CompaniesMapper, Companies> implements ICompaniesService {


    private final CompaniesMapper companiesMapper;

    @Autowired
    public CompaniesServiceImpl(CompaniesMapper companiesMapper){
        this.companiesMapper = companiesMapper;
    }

    public boolean isExist(int id){
        QueryWrapper<Companies> queryWrapper = new QueryWrapper<Companies>().eq("company_id", id);
        Companies company = this.getOne(queryWrapper);
        return company!=null;
    }

    // @param: id 公司id
    // @retrun: 查找到的公司信息
    @Override
    public Companies getById(int id){
        QueryWrapper<Companies> queryWrapper = new QueryWrapper<Companies>().eq("company_id", id);
        Companies company = this.getOne(queryWrapper);
        return company;
    }

    // @description: 获取公司列表
    @Override
    public List<CompanyVo> listCompany(){
        QueryWrapper<Companies> queryWrapper = new QueryWrapper<Companies>().select("company_id", "company_name");
        List<Companies> list = this.list(queryWrapper);
        List<CompanyVo> companyVoList = list.stream().map(this::CompanyToVo).collect(Collectors.toList());
        return companyVoList;
    }

    @Override
    public String GetMail(int id) {
        String mail = companiesMapper.getCompanyMail(id);
        return mail;
    }


    /*
            @params: Companies companies DTO层的company
            @descrip： 将DTO结构转化为VO结构
            @return: CompanyVo VO结构
         */
    public CompanyVo CompanyToVo(Companies companies){
        CompanyVo companyVo = new CompanyVo();
        companyVo.setId(companies.getCompanyId());
        companyVo.setName(companies.getCompanyName());
        return companyVo;
    }


}
