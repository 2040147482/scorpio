package com.leslie.admin.utils;


import com.leslie.admin.pojo.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * 构建树形结构帮助类
 * @author 20110
 */
public class PermissionHelper {

    /**
     * 构建树形结构
     *
     * @param permissionList
     * @return
     */
    public static List<Permission> bulidTree(List<Permission> permissionList) {
        //创建集合封装最终数据
        List<Permission> trees = new ArrayList<>();
        //遍历所有菜单集合
        for (Permission permission : permissionList) {
            //找到递归入口，parentid=0
            if (permission.getPid() == 0) {
                trees.add(findChildren(permission, permissionList));
            }
        }
        return trees;
    }

    /**
     * 从根节点进行递归查询，查询子节点
     * 判断 id =  parentid是否相同，如果相同是子节点，进行数据封装
     *
     * @param permission
     * @param treeNodes
     * @return
     */
    private static Permission findChildren(Permission permission, List<Permission> treeNodes) {
        //数据初始化
        permission.setChildren(new ArrayList<Permission>());
        //遍历递归查找
        for (Permission p : treeNodes) {
            if (permission.getId().equals(p.getPid())) {
                if (permission.getChildren() == null) {
                    permission.setChildren(new ArrayList<>());
                }
                permission.getChildren().add(findChildren(p, treeNodes));
            }
        }
        return permission;
    }
}
