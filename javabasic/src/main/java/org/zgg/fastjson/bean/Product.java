package org.zgg.fastjson.bean;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Product {
    private Long pid;
    private String pname;
    private String pbrand;
    private String pcolor;
    private Date pdate;
    private List<String> pattrs;
}
