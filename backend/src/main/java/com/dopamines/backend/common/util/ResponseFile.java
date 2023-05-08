package  com.dopamines.backend.common.util;

import java.util.HashMap;

public class ResponseFile {
    public HashMap<String, Object> makeSimpleRes(Object result){
        HashMap<String, Object> v = new HashMap<>();
        v.put("result",result);
        return v;
    }
}
