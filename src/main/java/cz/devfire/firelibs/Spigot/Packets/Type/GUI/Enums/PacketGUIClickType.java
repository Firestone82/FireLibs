package cz.devfire.firelibs.Spigot.Packets.Type.GUI.Enums;

public enum PacketGUIClickType {
    NONE                (-1,-1,-1),
    LEFT_CLICK          (0,0,-1),
    RIGHT_CLICK         (0,1,-1),
    LEFT_CLICK_OUT_DROP (0,0,-999),
    RIGHT_CLICK_OUT_DROP(0,1,-999),
    SHIFT_LEFT_CLICK    (1,0,-1),
    SHIFT_RIGHT_CLICK   (1,1,-1),
    NUMBER_KEY_1        (2,0,-1),
    NUMBER_KEY_2        (2,1,-1),
    NUMBER_KEY_3        (2,2,-1),
    NUMBER_KEY_4        (2,3,-1),
    NUMBER_KEY_5        (2,4,-1),
    NUMBER_KEY_6        (2,5,-1),
    NUMBER_KEY_7        (2,6,-1),
    NUMBER_KEY_8        (2,7,-1),
    NUMBER_KEY_9        (2,8,-1),
    MIDDLE_CLICK        (3,2,-1),
    MIDDLE_CLICK_OUT    (3,2,-999),
    DROP                (4,0,-1),
    CTRL_DROP           (4,1,-1),
    LEFT_CLICK_OUT      (4,0,-999),
    RIGHT_CLICK_OUT     (4,1,-999),
    DOUBLE_CLICK        (6,0,-1);

    private final int mode;
    private final int button;
    private final int slot;

    PacketGUIClickType(int mode, int button, int slot) {
        this.mode = mode;
        this.button = button;
        this.slot = slot;
    }

    public static PacketGUIClickType parse(int mode, int button, int slot) {
        if (slot != -999) {
            slot = -1;
        }

        for (PacketGUIClickType type : values()) {
            if (type.mode == mode && type.button == button && type.slot == slot) {
                return type;
            }
        }

        return NONE;
    }

    public int getMode() {
        return mode;
    }

    public int getButton() {
        return button;
    }

    public int getSlot() {
        return slot;
    }
}
