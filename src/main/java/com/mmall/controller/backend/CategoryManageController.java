package com.mmall.controller.backend;

import com.mmall.common.ServerResponse;
import com.mmall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/manage/category")
public class CategoryManageController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryManageController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/add")
    public ServerResponse addCategory(@RequestParam(defaultValue = "0") Integer parentID,
                                      @RequestParam String categoryName) {
        if (StringUtils.isEmpty(categoryName) || StringUtils.isEmpty(parentID)) {
            return ServerResponse.createByErrorMsg("参数错误");
        }
        return categoryService.addCategory(categoryName, parentID);
    }

    @PutMapping("/categoryName")
    public ServerResponse updateCategoryName(@RequestParam Integer categoryID,
                                             @RequestParam String categoryName) {
        if (StringUtils.isEmpty(categoryName) || StringUtils.isEmpty(categoryID)) {
            return ServerResponse.createByErrorMsg("参数错误");
        }
        return categoryService.updateCategoryName(categoryID, categoryName);
    }

    @GetMapping("/children")
    public ServerResponse getParallelChildrenNode(@RequestParam(defaultValue = "0") Integer categoryID){
        return categoryService.getParallelChildrenNode(categoryID);
    }

    /**
     * 获取某分类的所有子分类ID
     * @param categoryID 要获取子分类的分类的ID
     * @return 所有子分类的ID集合
     */
    @GetMapping("/allChildren")
    public ServerResponse getAllChildren(@RequestParam Integer categoryID){
        return categoryService.getAllChildren(categoryID);
    }
}
