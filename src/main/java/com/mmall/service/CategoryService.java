package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;
import com.mmall.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public ServerResponse addCategory(String categoryName, Integer parentID) {
        Category category = new Category();
        category.setCategoryName(categoryName);
        category.setParentID(parentID);
        category.setCategoryStatus(true);
        category.setCreateTime(new Date());
        category.setUpdateTime(new Date());
        categoryRepository.save(category);
        return ServerResponse.createBySuccess();
    }
    public ServerResponse updateCategoryName(Integer categoryID,String categoryName){
        categoryRepository.findById(categoryID).ifPresent(category -> {
            category.setCategoryName(categoryName);
            category.setUpdateTime(new Date());
            categoryRepository.save(category);
        });
        return ServerResponse.createBySuccess();
    }
    public ServerResponse<List<Category>> getParallelChildrenNode(Integer categoryID){
        List<Category> categoryList = categoryRepository.findByParentID(categoryID);
        if (CollectionUtils.isEmpty(categoryList)){
            log.info("分类{}的子分类数目为零！",categoryID);
        }
        return ServerResponse.createBySuccess(categoryList);
    }
    public ServerResponse<Set<Integer>> getAllChildren(Integer categoryID){
        HashSet<Integer> childrenIDs = new HashSet<>();
        categoryRepository.findById(categoryID).ifPresent(category -> this.findAllChildren(category,childrenIDs));
        childrenIDs.remove(categoryID);
        return ServerResponse.createBySuccess(childrenIDs);
    }

    /**
     * 根据节点ID查找所有子节点
     * @param category 节点
     * @param categorySet 存放节点ID的容器
     */
    private void findAllChildren(Category category,Set<Integer> categorySet){
        int categoryID = category.getCategoryID();
        categorySet.add(categoryID);
        List<Category> children = categoryRepository.findByParentID(categoryID);
        for (Category child : children) {
            this.findAllChildren(child,categorySet);
        }
    }
}
