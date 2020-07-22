package ua.leonidius.trdinterface.models;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "sellable_items")
public class SellableItem extends ShopItem {

    @DatabaseField(generatedId = true, columnName = "record_id")
    private int recordId;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "shop_id")
    Shop shop;

    @DatabaseField(canBeNull = false, columnName = "item_id")
    private String itemId;

    @DatabaseField(canBeNull = false)
    private double price;

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] nbt;

    @Override
    protected int getRecordId() {
        return recordId;
    }

    @Override
    public String getItemId() {
        return itemId;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public byte[] getNbt() {
        return nbt;
    }

    @Override
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public void setNbt(byte[] nbt) {
        this.nbt = nbt;
    }

}
