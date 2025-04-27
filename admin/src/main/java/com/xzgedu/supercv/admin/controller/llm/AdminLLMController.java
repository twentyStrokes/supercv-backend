package com.xzgedu.supercv.admin.controller.llm;

import com.xzgedu.supercv.llm.enums.ModelType;
import com.xzgedu.supercv.llm.enums.PromptType;
import com.xzgedu.supercv.llm.enums.SortType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/admin/llm")
@RestController
public class AdminLLMController {

    @GetMapping("/model/type/list")
    public List<ModelType.ModelTypeBo> listAllModelTypes() {
        return ModelType.getAllModelTypeBo();
    }

    @GetMapping("/prompt/type/list")
    public List<PromptType.PromptTypeBo> listAllPromptTypes() {
        return PromptType.getAllPromptTypeBo();
    }

    @GetMapping("/sort/type/list")
    public List<SortType.SortTypeBo> listAllSortTypes() {
        return SortType.getAllSortTypeBo();
    }
}
