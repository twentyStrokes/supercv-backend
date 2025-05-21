package com.xzgedu.supercv.admin.controller.llm;

import com.xzgedu.supercv.llm.domain.LLMLog;
import com.xzgedu.supercv.llm.domain.LLMLogFilter;
import com.xzgedu.supercv.llm.enums.PromptType;
import com.xzgedu.supercv.llm.enums.SortType;
import com.xzgedu.supercv.llm.service.LLMLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/admin/llm/log")
@RestController
public class AdminLLMLogController {

    @Autowired
    private LLMLogService llmLogService;


    @GetMapping("/detail")
    public LLMLog getLLMLogById(@RequestParam("id") long id) {
        return llmLogService.selectLLMLogById(id);
    }

    @GetMapping("/list")
    public Map<String, Object> listLLMLogs(@RequestParam(value = "filter_uid", required = false) Long filterUid,
                                           @RequestParam(value = "model_id", required = false) Long modelId,
                                           @RequestParam(value = "prompt_type", required = false) Integer promptType,
                                           @RequestParam(value = "sort_type", required = false) Integer sortType,
                                           @RequestParam("page_no") int pageNo,
                                           @RequestParam("page_size") int pageSize) {
        int limitOffset = (pageNo - 1) * pageSize;
        int limitSize = pageSize;
        LLMLogFilter filter = new LLMLogFilter();
        filter.setUid(filterUid);
        filter.setModelId(modelId);
        filter.setPromptType(PromptType.of(promptType));
        SortType sortTypeEnum = SortType.of(sortType);
        List<LLMLog> llmLogs = llmLogService.selectLLMLogs(filter, sortTypeEnum, limitOffset, limitSize);
        int totalCount = llmLogService.countLLMLogs(filter);
        Map<String, Object> resp = new HashMap<>();
        resp.put("llmLogs", llmLogs);
        resp.put("count", totalCount);
        return resp;
    }
}
