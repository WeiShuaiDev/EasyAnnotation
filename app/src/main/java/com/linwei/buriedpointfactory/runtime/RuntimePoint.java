package com.linwei.buriedpointfactory.runtime;

import com.linwei.buriedpointfactory.R;
import com.linwei.buriedpointfactory.annotation.AddPoint;

/**
 * @Author: WS
 * @Time: 2020/4/27
 * @Description:
 */
public class RuntimePoint {

    @AddPoint(key = "00", message = R.string.send_message_point)
    private void sendMessage() {
        System.out.println("发送数据..");
    }

    @AddPoint(key = "11", message = R.string.receive_message_point)
    private void receiveMessage() {
        System.out.println("接受数据..");
    }

}
