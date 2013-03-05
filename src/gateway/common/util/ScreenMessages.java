package gateway.common.util;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-6-9
 * Time: 11:56:50
 * To change this template use File | Settings | File Templates.
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ScreenMessages implements Serializable {

    private static final int MESSAGE_TYPE = 9;

    private List<ScreenMessage> list;

    private int level;

    public ScreenMessages() {
        list = new ArrayList<ScreenMessage>();
        level = 0;
    }

    public void clear() {
        list.clear();
        level = 0;
    }

    public void add(Message msg) {
        add(msg, null);
    }

    public void add(Message msg, String fieldName) {
        ScreenMessage sm = new ScreenMessage(msg, fieldName);
        list.add(sm);

        char c = msg.getKey().charAt(MESSAGE_TYPE);

        int addedLevel = 0;
        if (c == 'I') {
            addedLevel = ScreenMessageType.INFORMATION.getLevel();
        } else if (c == 'W') {
            addedLevel = ScreenMessageType.WARNING.getLevel();
        } else if (c == 'E') {
            addedLevel = ScreenMessageType.ERROR.getLevel();
        }

        if (level < addedLevel) {
            level = addedLevel;
        }
    }

    public boolean hasError() {
        if (level == 0) {
            return false;
        }
        if (level >= ScreenMessageType.ERROR.getLevel()) {
            return true;
        }
        return false;
    }

    public Iterator<ScreenMessage> getMessages() {
        return list.iterator();
    }

    public int getLevel() {
        return level;
    }
}
