package ua.leonidius.trdinterface.controllers.sell;

import cn.nukkit.item.Item;
import me.onebone.economyapi.EconomyAPI;
import ua.leonidius.trdinterface.Message;
import ua.leonidius.trdinterface.Trading;
import ua.leonidius.trdinterface.controllers.AmountSelectorController;
import ua.leonidius.trdinterface.controllers.InfoController;
import ua.leonidius.trdinterface.models.SellableItem;
import ua.leonidius.trdinterface.models.ShopItem;
import ua.leonidius.trdinterface.views.ScreenManager;
import ua.leonidius.trdinterface.views.screens.AmountSelectorScreen;

public class SellAmountSelectorController extends AmountSelectorController {

    private final SellableItem item;
    private final int maxAmount;

    public SellAmountSelectorController(ScreenManager manager,
                                        SellableItem item, int maxAmount) {
        super(manager);
        this.item = item;
        this.maxAmount = maxAmount;
    }

    @Override
    public void showScreen() {
        manager.addAndShow(new AmountSelectorScreen(this,
                Message.WDW_SELL_TITLE.getText(item.getName()),
                buildItemDescription(), maxAmount), true);
    }

    @Override
    public void selectAmount(int amount) {
        Item gameItem = item.toGameItem();
        gameItem.setCount(amount);

        if (!manager.getPlayer().getInventory().contains(gameItem)) {
            showErrorScreen(Message.WDW_SELL_NOTHING.getText());
            return;
        }

        double cost = item.getPrice() * amount;

        manager.getPlayer().getInventory().removeItem(gameItem);
        EconomyAPI.getInstance().addMoney(manager.getPlayer(), cost);

        // Success
        if (Trading.getSettings().logTransactions()) {
            Message.LOG_SOLD.log(manager.getPlayer().getName(), amount,
                    item.getName(), item.getItemId(),
                    cost, Trading.getSettings().getCurrency());
        }

        new InfoController(manager, Message.WDW_SUCCESS_TITLE.getText(),
                Message.SELL_SUCCESS.getText(amount, item.getName(),
                        cost, Trading.getSettings().getCurrency())).showScreen();
    }

    @Override
    protected ShopItem getItem() {
        return item;
    }

}
