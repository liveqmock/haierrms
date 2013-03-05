package gateway.common.util;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-6-7
 * Time: 15:47:26
 * To change this template use File | Settings | File Templates.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessageException extends RuntimeException {
    private final List messageList;

    public MessageException() {
        this.messageList = Collections.EMPTY_LIST;
    }

    public MessageException(Message message) {
        super(message.toString());
        List list = new ArrayList();
        list.add(message);
        this.messageList = Collections.unmodifiableList(list);
    }

    public MessageException(List messageList) {
        super(messageList.toString());
        List list = new ArrayList(messageList);
        this.messageList = Collections.unmodifiableList(list);
    }

    public List getMessageList() {
        return this.messageList;
    }
}
