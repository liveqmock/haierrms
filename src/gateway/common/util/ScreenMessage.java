package gateway.common.util;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-6-9
 * Time: 11:55:44
 * To change this template use File | Settings | File Templates.
 */
public class ScreenMessage {

    private Message msg;

    private String fieldName;

    protected ScreenMessage(Message msg, String fieldName) {
        this.msg = msg;
        this.fieldName = fieldName;
    }

    public String getMessage() {
        return msg.toString();
    }

    public String getKey() {
        return msg.getKey();
    }

    public String getFieldName() {
        return fieldName;
    }
}
