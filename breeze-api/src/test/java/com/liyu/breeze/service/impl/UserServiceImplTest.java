package com.liyu.breeze.service.impl;

import com.liyu.breeze.BreezeApplication;
import com.liyu.breeze.common.enums.UserStatusEnum;
import com.liyu.breeze.service.UserService;
import com.liyu.breeze.service.dto.UserDTO;
import com.liyu.breeze.service.vo.DictVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

@Slf4j
@SpringBootTest(classes = BreezeApplication.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void simpleTest() {
        UserDTO user = new UserDTO();
        user.setUserName("test");
        user.setEmail("55@5");
        user.setPassword("123456");
        user.setUserStatus(new DictVO(UserStatusEnum.BIND_EMAIL.getValue(), UserStatusEnum.BIND_EMAIL.getLabel()));
        user.setRegisterChannel(new DictVO("15", "abc"));
        this.userService.insert(user);
        UserDTO dto = this.userService.selectOne("test");
        log.info(dto.getUserName());
    }

    @Test
    public void deleteByTypeTest() {
        this.userService.deleteBatch(new HashMap<Integer, Integer>() {{
            put(0, 1);
            put(1, 2);
        }});
    }

}
