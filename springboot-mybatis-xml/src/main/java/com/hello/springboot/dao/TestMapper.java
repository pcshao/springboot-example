package com.hello.springboot.dao;

import com.hello.springboot.entity.Test;
import java.util.List;

public interface TestMapper {
    int insert(Test record);

    List<Test> selectAll();
}