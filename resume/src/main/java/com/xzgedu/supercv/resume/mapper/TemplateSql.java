package com.xzgedu.supercv.resume.mapper;

import java.util.List;
import java.util.Map;

public class TemplateSql {
    public String selectTemplatesByIds(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder("select * from resume_template where 1=1 ");
        List<Long> ids = (List<Long>) params.get("ids");
        sb.append(" and id in " + buildInFilter(ids));
        return sb.toString();
    }

    private String buildInFilter(List<Long> ids) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        if (ids != null && !ids.isEmpty()) {
            for (int i = 0; i < ids.size() - 1; ++i) {
                sb.append(ids.get(i) + ", ");
            }
            sb.append(ids.get(ids.size() - 1));
        }
        sb.append(")");
        return sb.toString();
    }
}
