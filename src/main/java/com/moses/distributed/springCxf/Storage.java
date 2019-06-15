package com.moses.distributed.springCxf;

import java.util.Arrays;
import java.util.List;

public class Storage {
	//数据仓库
    public static List<User> users= Arrays.asList(
            new User(1,"james"),
            new User(2,"mic"),
            new User(3,"sam")
    );
}
