package com.tian.my_qa.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Util {
    private static Integer calPageOffset(Integer pageNum, Integer pageSize) {
        return (pageNum > 1 ? pageNum - 1 : 0) * pageSize;
    }
    private static Map wrapData(List list, Integer total) {
        Map<String, Object> res = new HashMap<>();
        res.put("list", list);
        res.put("total", total);
        return res;
    }
    public static BiFunction<Integer, Integer, Integer> getPageOffset = Util::calPageOffset;
    public static Function<String, String> getUserIdByToken = JwtUtil::getMemberIdByJwtToken;
    public static BiFunction<List, Integer, Map> wrapPageData = Util::wrapData;
}
