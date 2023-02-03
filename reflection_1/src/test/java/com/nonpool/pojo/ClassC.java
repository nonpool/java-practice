package com.nonpool.pojo;

import com.nonpool.ToJsonConfig;

@ToJsonConfig(includeNullField = false)
public class ClassC {

    private String id;
    private String name;

    public ClassC(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
