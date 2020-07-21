package ua.leonidius.trdinterface.models;

import cn.nukkit.item.Item;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import ua.leonidius.trdinterface.Trading;

import java.io.IOException;

@DatabaseTable(tableName = "buyable_items")
public class BuyableItem {

    public BuyableItem() {}

    @DatabaseField(generatedId = true, columnName = "record_id")
    int recordId;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "shop_id")
    Shop shop;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "category_id")
    Category category;

    @DatabaseField(canBeNull = false, columnName = "item_id")
    String itemId;

    @DatabaseField(canBeNull = false)
    public double price;

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    byte[] nbt;

    public Item toGameItem() {
        Item item = Item.fromString(itemId);
        if (nbt != null) {
            try {
                CompoundTag tag = NBTIO.read(nbt);
                item.setCompoundTag(tag);
            } catch (IOException e) {
                // TODO: translate the error message
                Trading.getPlugin().getLogger().error("Error reading NBT Tag on item with record_id " + recordId);
                if (Trading.settings.debugMode) {
                    Trading.getPlugin().getLogger().error(e.getMessage());
                }
            }
        }
        return item;
    }

}