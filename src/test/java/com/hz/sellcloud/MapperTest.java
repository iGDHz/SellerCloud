package com.hz.sellcloud;

import com.hz.sellcloud.service.ICompaniesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MapperTest {
    @Autowired
    ICompaniesService companiesService;

    @Test
    public void CompanyEmailTest(){
        System.out.println(companiesService.GetMail(6));
    }
}
