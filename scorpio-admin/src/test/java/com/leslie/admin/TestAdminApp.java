package com.leslie.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leslie.admin.mapper.PermissionMapper;
import com.leslie.admin.pojo.Permission;
import com.leslie.admin.service.PermissionService;
import com.leslie.utils.Result;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author 20110
 */
@SpringBootTest
public class TestAdminApp {

    @Resource
    PermissionMapper permissionMapper;

    @Resource
    PermissionService permissionService;

    @Test
    void testFindAll(){
        Result result = permissionService.findAll();
        System.out.println("**************");
        System.out.println(result);
        System.out.println("**************");
        System.out.println(result.getData());
    }

    @Test
    void testPage(){
        IPage<Permission> page = new Page<>(3,3);

        IPage<Permission> permissionIPage = permissionMapper.selectPage(page, null);
        System.out.println(page.getCurrent());
        System.out.println(page.getTotal());
        System.out.println(page.getRecords());
    }


}
