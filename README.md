# FireLibs

## Includes
- PacketListener
- Reflections
- Dynamic Command Registration
- Dynamic Placeholder Registration
- Objects
    - CustomItemStack
    - Database (SqLite, MySQL)
    - Unsorted List, Map, Set
    - PercentageList
- Utils
    - ItemUtils
    - LocationUtils
    - PacketUtils
    - PlaceholderUtils
    - ServerUtils
    - StringUtils
    - MapUtils
    - SystemUtils

## TODO
- [x] PacketGUI
- [ ] PacketArmorStand
- [ ] PacketBlock
- [ ] PacketBossbar
- [ ] PacketHologram
- [ ] PacketItem
- [ ] PacketSign

## Usage
```java
CommandHandler commandHandler = FireLibs.getCommandHandler();
PacketHandler packetHandler = FireLibs.getPacketHandler();
PacketGUIHandler packetGUIHandler = FireLibs.getPacketGUIHandler();
```