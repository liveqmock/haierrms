package gateway.common.util;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 2010-6-9
 * Time: 13:45:06
 * To change this template use File | Settings | File Templates.
 */
public class ScreenMessageType {
    public static final ScreenMessageType INFORMATION =
        new ScreenMessageType(1);

    public static final ScreenMessageType CONFIRM =
        new ScreenMessageType(2);

    public static final ScreenMessageType COMPLETE =
        new ScreenMessageType(3);

    public static final ScreenMessageType WARNING =
        new ScreenMessageType(4);

    public static final ScreenMessageType ERROR =
        new ScreenMessageType(5);

    public static final ScreenMessageType FATAL =
        new ScreenMessageType(6);

    private int level;

    private ScreenMessageType(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
