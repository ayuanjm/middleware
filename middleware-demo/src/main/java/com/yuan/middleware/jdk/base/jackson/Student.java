package com.yuan.middleware.jdk.base.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * @author yuan
 */
@Data
public class Student {
    private int id;
    private String name;
    private Date birthDay;
    private Map<String, Object> body;

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        // Convert object to JSON string
        String Json = "{\"body\":{\"address\":\"北京市北京市丰台区丰台街道\",\"addressId\":\"3653198951550685184\",\"consignee\":\"陈冠希•哈哈\",\"detailedAddress\":\"育菲园东里5号楼303\",\"isDefault\":\"1\",\"phone\":\"13831531111\"}}";
        System.out.println(Json);
        // Convert Json string to Object
        Student student2 = mapper.readValue(Json, Student.class);
        System.out.println(student2);

    }
}
